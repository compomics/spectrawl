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
public interface MsLimsExperimentLoader extends ExperimentLoader {
    
    /**
     * Gets the mslims spectrum loader
     * 
     * @return the mslims spectrum loader
     */
    MsLimsSpectrumLoader getMsLimsSpectrumLoader();
    
    /**
     * Sets the mslims spectrum loader
     *
     * @param msLimsSpectrumLoader the mslims spectrum loader
     */
    void setMsLimsSpectrumLoader(MsLimsSpectrumLoader msLimsSpectrumLoader);
       
    /**
     * Loads the experiment
     *
     * @param experimentId the experiment ID
     * @return the experiment
     */
    Experiment loadExperiment(String experimentId);

    /**
     * Loads the experiment
     *
     * @param experimentId    the experiment ID
     * @param numberOfSpectra the number of spectra to retrieve
     * @return the experiment
     */
    Experiment loadExperiment(String experimentId, int numberOfSpectra);
    
}
