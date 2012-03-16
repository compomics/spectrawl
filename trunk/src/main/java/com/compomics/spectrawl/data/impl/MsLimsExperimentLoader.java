package com.compomics.spectrawl.data.impl;

import com.compomics.spectrawl.bin.SpectrumBinner;
import com.compomics.spectrawl.data.ExperimentLoader;
import com.compomics.spectrawl.data.SpectrumLoader;
import com.compomics.spectrawl.filter.analyze.Filter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 1/03/12
 * Time: 9:48
 * To change this template use File | Settings | File Templates.
 */
public class MsLimsExperimentLoader implements ExperimentLoader {

    private static final Logger LOGGER = Logger.getLogger(MsLimsExperimentLoader.class);

    private Filter<SpectrumImpl> spectrumFilter;
    private SpectrumLoader spectrumLoader;
    private SpectrumBinner spectrumBinner;

    @Override
    public void setSpectrumFilter(Filter<SpectrumImpl> spectrumFilter) {
        this.spectrumFilter = spectrumFilter;
    }

    @Override
    public void setSpectrumLoader(SpectrumLoader spectrumLoader) {
        this.spectrumLoader = spectrumLoader;
    }

    @Override
    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
    }

    @Override
    public Experiment loadExperiment(long experimentId) {
        return loadExperiment(experimentId, -1);
    }

    @Override
    public Experiment loadExperiment(long experimentId, int numberOfSpectra) {
        Experiment experiment = new Experiment(experimentId);
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();

        //retrieve set of spectrum IDs from spectrumloader
        Set<Long> spectraIds = null;
        if (numberOfSpectra == -1) {
            spectraIds = spectrumLoader.getSpectraIdsByExperimentId(experimentId);
        } else {
            spectraIds = spectrumLoader.getSpectraIdsByExperimentId(experimentId, numberOfSpectra);
        }

        LOGGER.debug("loading experiment with " + spectraIds.size() + " spectra before filtering.");

        for (Long spectrumId : spectraIds) {
            SpectrumImpl spectrum = spectrumLoader.getSpectrumBySpectrumId(spectrumId);

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
