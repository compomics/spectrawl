package com.compomics.spectrawl.service.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.service.MsLimsExperimentService;
import com.compomics.spectrawl.repository.MsLimsExperimentRepository;
import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA. User: niels Date: 1/03/12 Time: 9:48 To change this
 * template use File | Settings | File Templates.
 */
@Service("msLimsExperimentService")
public class MsLimsExperimentServiceImpl implements MsLimsExperimentService {

    private static final Logger LOGGER = Logger.getLogger(MsLimsExperimentServiceImpl.class);
    @Autowired
    @Qualifier("filterChain")
    private Filter<SpectrumImpl> spectrumFilter;
    @Autowired
    private MsLimsExperimentRepository msLimsSpectrumRepository;
    @Autowired
    private SpectrumBinner spectrumBinner;
    @Autowired
    private ExecutorService taskExecutor;

    @Override
    public Experiment loadExperiment(String experimentId) {
        Experiment experiment = new Experiment(experimentId);
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();

        int numberOfSpectra = msLimsSpectrumRepository.loadSpectraByExperimentId(Long.parseLong(experimentId));

        //set number of initial spectra
        //@todo set this
        experiment.setNumberOfSpectra(numberOfSpectra);

        LOGGER.info("start loading experiment with " + numberOfSpectra + " spectra before filtering");

        //submit a job for each spectrum
        List<Future<SpectrumImpl>> futureList = new ArrayList<Future<SpectrumImpl>>();
        for (int i = 0; i < numberOfSpectra ; i++) {
            Future<SpectrumImpl> future = taskExecutor.submit(new SpectrumLoader());
            futureList.add(future);
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

        //set number of spectra after filtering
        experiment.setNumberOfFilteredSpectra(spectra.size());

        LOGGER.info("done loading experiment with " + spectra.size() + " spectra after filtering");

        //set experiment spectra
        experiment.setSpectra(spectra);

        return experiment;
    }
    
    @Override
    public Experiment loadExperiment(List<Long> spectrumIds) {
        Experiment experiment = new Experiment("stubExperiment");
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();

        int numberOfSpectra = msLimsSpectrumRepository.loadSpectraBySpectrumIds(spectrumIds);

        //set number of initial spectra
        //@todo set this
        experiment.setNumberOfSpectra(numberOfSpectra);

        LOGGER.info("start loading experiment with " + numberOfSpectra + " spectra before filtering");

        //submit a job for each spectrum
        List<Future<SpectrumImpl>> futureList = new ArrayList<Future<SpectrumImpl>>();
        for (int i = 0; i < numberOfSpectra ; i++) {
            Future<SpectrumImpl> future = taskExecutor.submit(new SpectrumLoader());
            futureList.add(future);
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

        //set number of spectra after filtering
        experiment.setNumberOfFilteredSpectra(spectra.size());

        LOGGER.info("done loading experiment with " + spectra.size() + " spectra after filtering");

        //set experiment spectra
        experiment.setSpectra(spectra);

        return experiment;
    }

    @Override
    public void setDoNoiseFiltering(boolean doNoiseFiltering) {
        msLimsSpectrumRepository.setDoNoiseFiltering(doNoiseFiltering);
    }    

    private class SpectrumLoader implements Callable<SpectrumImpl> {

        @Override
        public SpectrumImpl call() throws Exception {
            SpectrumImpl spectrum = msLimsSpectrumRepository.getSpectrum();
            //LOGGER.debug("spectrum " + spectrum.getSpectrumId() + " is processed by " + Thread.currentThread().getName());

            //bin the spectrum
            spectrumBinner.binSpectrum(spectrum, BinParams.BINS_FLOOR.getValue(), BinParams.BINS_CEILING.getValue(), BinParams.BIN_SIZE.getValue());

            //add the spectrum to the spectra
            //if the spectrum passes the filter
            if (spectrumFilter.passesFilter(spectrum, Boolean.FALSE)) {
                //LOGGER.debug("spectrum " + spectrum.getSpectrumId() + " passed the filter.");
                return spectrum;
            } else {
                //LOGGER.debug("spectrum " + spectrum.getSpectrumId() + " didn't pass the filter.");
                return null;
            }
        }
    }
}
