package com.compomics.spectrawl.data;

import com.compomics.spectrawl.bin.SpectrumBinner;
import com.compomics.spectrawl.filter.analyze.Filter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 1/03/12
 * Time: 9:45
 * To change this template use File | Settings | File Templates.
 */
public interface ExperimentLoader {

    /**
     * Sets the filter for retrieving the spectra.
     * This can be a chain of filters.
     * @see com.compomics.spectrawl.filter.analyze.FilterChain
     *
     * @param spectrumFilter the spectrum filter
     */
    void setSpectrumFilter(Filter<SpectrumImpl> spectrumFilter);
    
    /**
     * Gets the spectrum loader
     * 
     * @return the spectrum loader
     */
    SpectrumLoader getSpectrumLoader();
    
    /**
     * Sets the spectrum loader
     *
     * @param spectrumLoader the spectrum loader
     */
    void setSpectrumLoader(SpectrumLoader spectrumLoader);

    /**
     * Sets the spectrum binner
     *
     * @param spectrumBinner the experiment binner
     */
    void setSpectrumBinner(SpectrumBinner spectrumBinner);
    
    /**
     * Loads the experiment
     *
     * @param experimentId the experiment
     * @return the experiment
     */
    Experiment loadExperiment(long experimentId);

    /**
     * Loads the experiment
     *
     * @param experimentId    the experiment
     * @param numberOfSpectra the number of spectra to retrieve
     * @return the experiment
     */
    Experiment loadExperiment(long experimentId, int numberOfSpectra);

}
