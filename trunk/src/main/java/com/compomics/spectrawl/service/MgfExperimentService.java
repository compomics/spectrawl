/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.service;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.spectrawl.repository.MgfExperimentRepository;
import com.compomics.util.experiment.massspectrometry.Spectrum;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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
 * @author Niels Hulstaert
 */
@Service("mgfExperimentService")
public class MgfExperimentService implements ExperimentService {

    private static final Logger LOGGER = Logger.getLogger(MgfExperimentService.class);
    @Autowired
    @Qualifier("filterChain")
    private Filter<SpectrumImpl> spectrumFilter;
    @Autowired
    private MgfExperimentRepository mgfExperimentRepository;
    @Autowired
    private SpectrumBinner spectrumBinner;
    @Autowired
    private ExecutorService taskExecutor;

    /**
     * Load the experiment from the given MGF files.
     *
     * @param mgfFiles the MGF files
     * @return the experiment
     */
    public Experiment loadExperiment(Map<String, File> mgfFiles) {
        Experiment experiment = new Experiment("");
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();

        //retrieve map of spectrum titles from the spectrum loader
        Map<String, List<String>> spectrumTitlesMap = mgfExperimentRepository.getSpectrumTitles(mgfFiles);

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

        //iterate over Futures and remove them when finished
        Iterator<Future<SpectrumImpl>> iterator = futureList.iterator();

        while (iterator.hasNext()) {
            Future<SpectrumImpl> future = iterator.next();
            try {
                SpectrumImpl spectrum = future.get();
                if (spectrum != null) {
                    experiment.addSpectrum(spectrum);
                }
                iterator.remove();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (ExecutionException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        //set number of spectra after filtering
        experiment.setNumberOfFilteredSpectra(experiment.getSpectra().size());

        LOGGER.info("done loading experiment with " + experiment.getSpectra().size() + " spectra after filtering");

        return experiment;
    }

    @Override
    public void setDoNoiseFiltering(boolean doNoiseFiltering) {
        mgfExperimentRepository.setDoNoiseFiltering(doNoiseFiltering);
    }

    private class SpectrumLoader implements Callable<SpectrumImpl> {

        private String spectrumKey;

        public SpectrumLoader(String spectrumKey) {
            this.spectrumKey = spectrumKey;
        }

        @Override
        public SpectrumImpl call() throws Exception {
            SpectrumImpl spectrum = mgfExperimentRepository.getSpectrumByKey(spectrumKey);

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
