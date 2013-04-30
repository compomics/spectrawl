package com.compomics.spectrawl.data.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.data.MsLimsExperimentLoader;
import com.compomics.spectrawl.data.MsLimsSpectrumLoader;
import com.compomics.spectrawl.logic.filter.mzratio.Filter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA. User: niels Date: 1/03/12 Time: 9:48 To change this
 * template use File | Settings | File Templates.
 */
@Repository("msLimsExperimentLoader")
public class MsLimsExperimentLoaderImpl implements MsLimsExperimentLoader {

    private static final Logger LOGGER = Logger.getLogger(MsLimsExperimentLoaderImpl.class);
    @Autowired
    @Qualifier("filterChain")
    private Filter<SpectrumImpl> spectrumFilter;
    @Autowired
    private MsLimsSpectrumLoader msLimsSpectrumLoader;
    @Autowired
    private SpectrumBinner spectrumBinner;
    @Autowired
    private ExecutorService taskExecutor;   

    @Override
    public MsLimsSpectrumLoader getMsLimsSpectrumLoader() {
        return msLimsSpectrumLoader;
    }    

    @Override
    public Experiment loadExperiment(String experimentId) {
        return loadExperiment(experimentId, -1);
    }

    @Override
    public Experiment loadExperiment(String experimentId, int numberOfSpectra) {
        Experiment experiment = new Experiment(experimentId);
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();

        //retrieve set of spectrum IDs from spectrum loader
        Set<Long> spectraIds = null;
        if (numberOfSpectra == -1) {
            spectraIds = msLimsSpectrumLoader.getSpectraIdsByExperimentId(Long.parseLong(experimentId));
        } else {
            spectraIds = msLimsSpectrumLoader.getSpectraIdsByExperimentId(Long.parseLong(experimentId), numberOfSpectra);
        }

        //set number of initial spectra
        experiment.setNumberOfSpectra(spectraIds.size());

        LOGGER.info("start loading experiment with " + spectraIds.size() + " spectra before filtering");

        //submit a job for each spectrum
        List<Future<SpectrumImpl>> futureList = new ArrayList<Future<SpectrumImpl>>();
        for (Long spectrumId : spectraIds) {
            Future<SpectrumImpl> future = taskExecutor.submit(new SpectrumLoader(spectrumId));
            futureList.add(future);
        }
        
        //run over the list of Futures and
        for (Future<SpectrumImpl> future : futureList) {
            try {
                SpectrumImpl spectrum = future.get();
                if(spectra != null){
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

    private class SpectrumLoader implements Callable<SpectrumImpl> {

        private Long spectrumId;

        public SpectrumLoader(Long spectrumId) {
            this.spectrumId = spectrumId;
        }

        @Override
        public SpectrumImpl call() throws Exception {
            SpectrumImpl spectrum = msLimsSpectrumLoader.getSpectrumBySpectrumId(spectrumId);

            //bin the spectrum
            spectrumBinner.binSpectrum(spectrum);

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
