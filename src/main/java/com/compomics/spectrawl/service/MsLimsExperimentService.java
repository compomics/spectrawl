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
import com.compomics.spectrawl.repository.MsLimsExperimentRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

/**
 *
 * @author Niels Hulstaert
 */
public class MsLimsExperimentService implements ExperimentService {

    private static final Logger LOGGER = Logger.getLogger(MsLimsExperimentService.class);
    private Filter<SpectrumImpl> filterChain;
    private MsLimsExperimentRepository msLimsSpectrumRepository;
    private SpectrumBinner spectrumBinner;
    private ExecutorService taskExecutor;

    public Filter<SpectrumImpl> getFilterChain() {
        return filterChain;
    }

    public void setFilterChain(Filter<SpectrumImpl> filterChain) {
        this.filterChain = filterChain;
    }

    public MsLimsExperimentRepository getMsLimsSpectrumRepository() {
        return msLimsSpectrumRepository;
    }

    public void setMsLimsSpectrumRepository(MsLimsExperimentRepository msLimsSpectrumRepository) {
        this.msLimsSpectrumRepository = msLimsSpectrumRepository;
    }

    public SpectrumBinner getSpectrumBinner() {
        return spectrumBinner;
    }

    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
    }

    public ExecutorService getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(ExecutorService taskExecutor) {
        this.taskExecutor = taskExecutor;
    }    
    
    /**
     * Load the experiment by experiment ID with all spectra.
     *
     * @param experimentId the experiment ID
     * @return the experiment
     */
    public Experiment loadExperiment(String experimentId) {
        Experiment experiment = new Experiment(experimentId);

        int numberOfSpectra = msLimsSpectrumRepository.loadSpectraByExperimentId(Long.parseLong(experimentId));

        //set number of initial spectra
        experiment.setNumberOfSpectra(numberOfSpectra);

        LOGGER.info("start loading experiment with " + numberOfSpectra + " spectra before filtering");

        //submit a job for each spectrum
        List<Future<SpectrumImpl>> futureList = new ArrayList<Future<SpectrumImpl>>();
        for (int i = 0; i < numberOfSpectra; i++) {
            Future<SpectrumImpl> future = taskExecutor.submit(new SpectrumLoader());
            futureList.add(future);
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

    /**
     * Load an artificial experiment by spectrum IDs with all spectra.
     *
     * @param spectrumIds the experiment ID
     * @return the experiment
     */
    public Experiment loadExperiment(List<Long> spectrumIds) {
        Experiment experiment = new Experiment("stubExperiment");

        int numberOfSpectra = msLimsSpectrumRepository.loadSpectraBySpectrumIds(spectrumIds);

        //set number of initial spectra
        experiment.setNumberOfSpectra(numberOfSpectra);

        LOGGER.info("start loading experiment with " + numberOfSpectra + " spectra before filtering");

        //submit a job for each spectrum
        List<Future<SpectrumImpl>> futureList = new ArrayList<Future<SpectrumImpl>>();
        for (int i = 0; i < numberOfSpectra; i++) {
            Future<SpectrumImpl> future = taskExecutor.submit(new SpectrumLoader());
            futureList.add(future);
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
        msLimsSpectrumRepository.setDoNoiseFiltering(doNoiseFiltering);
    }

    private class SpectrumLoader implements Callable<SpectrumImpl> {

        @Override
        public SpectrumImpl call() throws Exception {
            SpectrumImpl spectrum = msLimsSpectrumRepository.getSpectrum();
            LOGGER.debug("spectrum " + spectrum.getSpectrumId() + " is processed by " + Thread.currentThread().getName());

            //bin the spectrum
            spectrumBinner.binSpectrum(spectrum, BinParams.BINS_FLOOR.getValue(), BinParams.BINS_CEILING.getValue(), BinParams.BIN_SIZE.getValue());

            //add the spectrum to the spectra
            //if the spectrum passes the filter
            if (filterChain.passesFilter(spectrum, false)) {
                LOGGER.debug("spectrum " + spectrum.getSpectrumId() + " passed the filter.");
                return spectrum;
            } else {
                LOGGER.debug("spectrum " + spectrum.getSpectrumId() + " didn't pass the filter.");
                return null;
            }
        }
    }
}
