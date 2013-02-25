/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.data;

import com.compomics.spectrawl.model.Experiment;

/**
 *
 * @author niels
 */
public interface MsLimsExperimentLoader {
       
    /**
     * Load the experiment by experiment ID with all spectra.
     *
     * @param experimentId the experiment ID
     * @return the experiment
     */
    Experiment loadExperiment(String experimentId);

    /**
     * Load the experiment by experiment ID, restricting the loaded number of spectra.
     *
     * @param experimentId    the experiment ID
     * @param numberOfSpectra the number of spectra to retrieve
     * @return the experiment
     */
    Experiment loadExperiment(String experimentId, int numberOfSpectra);
    
    /**
     * Get the MsLimsSpectrumLoader
     * 
     * @return MsLimsSpectrumLoader
     */
    MsLimsSpectrumLoader getMsLimsSpectrumLoader();
    
}
