/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.service;

import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.repository.MgfExperimentRepository;
import java.io.File;
import java.util.Map;

/**
 *
 * @author niels
 */
public interface MgfExperimentService {
        
    /**
     * Load the experiment from the given MGF files.
     * 
     * @param mgfFiles the MGF files
     * @return the experiment
     */
    Experiment loadExperiment(Map<String, File> mgfFiles);
    
    /**
     * Get the MgfSpectrumLoader
     * 
     * @return MgfSpectrumLoader
     */
    MgfExperimentRepository getMgfSpectrumLoader();
    
}
