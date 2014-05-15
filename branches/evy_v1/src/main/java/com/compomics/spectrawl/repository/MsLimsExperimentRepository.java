/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.repository;

import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.List;

/**
 *
 * @author Niels Hulstaert
 */
public interface MsLimsExperimentRepository extends ExperimentRepository {

    /**
     * Load all spectra by experiment ID.
     *
     * @param experimentId the experiment ID
     * @return the number of spectra loaded
     */
    int loadSpectraByExperimentId(long experimentId);
    
    /**
     * Load all spectra by spectrum IDs
     *
     * @param spectrumIds the spectrum ID list
     * @return the number of spectra loaded
     */
    int loadSpectraBySpectrumIds(List<Long> spectrumIds);
    
    /**
     * Get a spectrum by experiment and spectrum ID
     * 
     * @param experimentId the experiment ID
     * @param spectrumId the spectrum ID
     * @return 
     */
    SpectrumImpl getSpectrum(long experimentId, long spectrumId);

    /**
     * Get the spectrum to process. This method is thread safe.
     *
     * @return the spectra
     */
    SpectrumImpl getSpectrum();
}
