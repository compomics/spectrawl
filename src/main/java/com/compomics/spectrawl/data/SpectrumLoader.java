package com.compomics.spectrawl.data;

import com.compomics.spectrawl.filter.process.NoiseFilter;
import com.compomics.spectrawl.filter.process.NoiseThresholdFinder;
import com.compomics.spectrawl.model.SpectrumImpl;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 3/02/12
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public interface SpectrumLoader {

    /**
     * Gets the spectrum by spectrum ID
     *
     * @param spectrumId the spectrum ID
     * @return the spectra
     */
    SpectrumImpl getSpectrumBySpectrumId(long spectrumId);

    /**
     * Gets the spectra IDs by experiment ID
     *
     * @param experimentId the experiment ID
     * @return the spectra IDs
     */
    Set<Long> getSpectraIdsByExperimentId(long experimentId);

    /**
     * Gets the spectra IDs by experiment ID
     *
     * @param experimentId    the experiment ID
     * @param numberOfSpectra the number of spectra to load
     * @return the spectra IDs
     */
    Set<Long> getSpectraIdsByExperimentId(long experimentId, int numberOfSpectra);

    /**
     * Sets the noise threshold finder for finding the spectrum noise threshold
     *
     * @param noiseThresholdFinder the noise threshold finder
     */
    void setNoiseThresholdFinder(NoiseThresholdFinder noiseThresholdFinder);

    /**
     * Sets the noise filter for filtering the spectrum
     *
     * @param noiseFilter the spectrum noise filter
     */
    void setNoiseFilter(NoiseFilter noiseFilter);

}
