/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.service.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.service.MgfExperimentService;
import com.compomics.spectrawl.repository.MgfExperimentRepository;
import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Spectrum;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author niels
 */
@Service("mgfExperimentService")
public class MgfExperimentServiceImpl implements MgfExperimentService {

    private static final Logger LOGGER = Logger.getLogger(MgfExperimentServiceImpl.class);
    @Autowired
    @Qualifier("filterChain")
    private Filter<SpectrumImpl> spectrumFilter;
    @Autowired
    private MgfExperimentRepository mgfSpectrumLoader;
    @Autowired
    private SpectrumBinner spectrumBinner;
    @Autowired
    private ExecutorService taskExecutor;

    @Override
    public MgfExperimentRepository getMgfSpectrumLoader() {
        return mgfSpectrumLoader;
    }

    @Override
    public Experiment loadExperiment(Map<String, File> mgfFiles) {
        Experiment experiment = new Experiment("");
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();

        //retrieve map of spectrum titles from the spectrum loader
        Map<String, List<String>> spectrumTitlesMap = mgfSpectrumLoader.getSpectrumTitles(mgfFiles);        

        //submit a job for each spectrum
        List<Future<SpectrumImpl>> futureList = new ArrayList<Future<SpectrumImpl>>();

        //iterate over spectrum titles
        for (String mgfFileName : spectrumTitlesMap.keySet()) {
            List<String> spectrumTitles = spectrumTitlesMap.get(mgfFileName);

            LOGGER.info("start loading mgf file with " + spectrumTitles.size() + " spectra before filtering");

            //set number of initial spectra
            experiment.setNumberOfSpectra(experiment.getNumberOfSpectra() + spectrumTitles.size());

            //iterate over spectra
            for (String spectrumTitle : spectrumTitles) {
                Future<SpectrumImpl> future = taskExecutor.submit(new SpectrumLoader(Spectrum.getSpectrumKey(mgfFileName, spectrumTitle)));
                futureList.add(future);
            }
        }

        //run over the list of Futures and
        for (Future<SpectrumImpl> future : futureList) {
            try {
                SpectrumImpl spectrum = future.get();
                if (spectrum != null) {
                    spectra.add(spectrum);
                }
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (ExecutionException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        //set number of filtered spectra
        experiment.setNumberOfFilteredSpectra(spectra.size());

        LOGGER.info("done loading experiment with " + spectra.size() + " spectra after filtering");

        //set experiment spectra
        experiment.setSpectra(spectra);

        return experiment;
    }

    private class SpectrumLoader implements Callable<SpectrumImpl> {

        private String spectrumKey;

        public SpectrumLoader(String spectrumKey) {
            this.spectrumKey = spectrumKey;
        }

        @Override
        public SpectrumImpl call() throws Exception {
            SpectrumImpl spectrum = mgfSpectrumLoader.getSpectrumByKey(spectrumKey);

            //bin the spectrum
            spectrumBinner.binSpectrum(spectrum, BinParams.BINS_FLOOR.getValue(), BinParams.BINS_CEILING.getValue(), BinParams.BIN_SIZE.getValue());

            //add the spectrum to the spectra
            //if the spectrum passes the filter
            if (spectrumFilter.passesFilter(spectrum, Boolean.FALSE)) {
                return spectrum;
            } else {
                return null;
            }
        }
    }
}
