/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.data;

import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.Set;

/**
 *
 * @author niels
 */
public interface MsLimsSpectrumLoader extends SpectrumLoader {

    /**
     * Get the spectrum by spectrum ID.
     *
     * @param spectrumId the spectrum ID
     * @return the spectra
     */
    SpectrumImpl getSpectrumBySpectrumId(long spectrumId);

    /**
     * Get the spectra IDs by experiment ID.
     *
     * @param experimentId the experiment ID
     * @return the spectra IDs
     */
    Set<Long> getSpectraIdsByExperimentId(long experimentId);

    /**
     * Get the spectra IDs by experiment ID.
     *
     * @param experimentId the experiment ID
     * @param numberOfSpectra the number of spectra to load
     * @return the spectra IDs
     */
    Set<Long> getSpectraIdsByExperimentId(long experimentId, int numberOfSpectra);
}
