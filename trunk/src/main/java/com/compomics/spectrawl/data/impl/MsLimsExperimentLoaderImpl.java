package com.compomics.spectrawl.data.impl;

import com.compomics.spectrawl.bin.SpectrumBinner;
import com.compomics.spectrawl.data.MsLimsExperimentLoader;
import com.compomics.spectrawl.data.MsLimsSpectrumLoader;
import com.compomics.spectrawl.filter.analyze.Filter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: niels Date: 1/03/12 Time: 9:48 To change this
 * template use File | Settings | File Templates.
 */
public class MsLimsExperimentLoaderImpl implements MsLimsExperimentLoader {

    private static final Logger LOGGER = Logger.getLogger(MsLimsExperimentLoaderImpl.class);
    
    private Filter<SpectrumImpl> spectrumFilter;
    private MsLimsSpectrumLoader msLimsSpectrumLoader;
    private SpectrumBinner spectrumBinner;

    @Override
    public void setSpectrumFilter(Filter<SpectrumImpl> spectrumFilter) {
        this.spectrumFilter = spectrumFilter;
    }
    
    @Override
    public MsLimsSpectrumLoader getMsLimsSpectrumLoader() {
        return msLimsSpectrumLoader;
    }
   
    @Override
    public void setMsLimsSpectrumLoader(MsLimsSpectrumLoader msLimsSpectrumLoader) {
        this.msLimsSpectrumLoader = msLimsSpectrumLoader;
    }

    @Override
    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
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

        LOGGER.debug("loading experiment with " + spectraIds.size() + " spectra before filtering.");

        for (Long spectrumId : spectraIds) {
            SpectrumImpl spectrum = msLimsSpectrumLoader.getSpectrumBySpectrumId(spectrumId);

            //bin the spectrum
            spectrumBinner.binSpectrum(spectrum);

            //add the spectrum to the spectra
            //if the spectrum passes the filter
            if (spectrumFilter.passesFilter(spectrum, Boolean.FALSE)) {
                spectra.add(spectrum);
            }
        }

        LOGGER.debug("loading experiment with " + spectra.size() + " spectra after filtering.");

        //set experiment spectra
        experiment.setSpectra(spectra);

        return experiment;
    }
}
