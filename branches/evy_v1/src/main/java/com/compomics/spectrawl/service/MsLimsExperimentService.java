/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.service;

import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.repository.MsLimsExperimentRepository;

/**
 *
 * @author niels
 */
public interface MsLimsExperimentService {
       
    /**
     * Load the experiment by experiment ID with all spectra.
     *
     * @param experimentId the experiment ID
     * @return the experiment
     */
    Experiment loadExperiment(String experimentId);
    
    /**
     * Get the MsLimsSpectrumLoader
     * 
     * @return MsLimsSpectrumLoader
     */
    MsLimsExperimentRepository getMsLimsSpectrumRepository();
    
}
