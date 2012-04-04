/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.data;

import com.compomics.spectrawl.model.Experiment;
import java.io.File;
import java.util.Map;

/**
 *
 * @author niels
 */
public interface MgfExperimentLoader extends ExperimentLoader {
    
    /**
     * Gets the mgf spectrum loader
     * 
     * @return the mgf spectrum loader
     */
    MgfSpectrumLoader getMgfSpectrumLoader();
    
    /**
     * Sets the mgf spectrum loader
     *
     * @param mgfSpectrumLoader the mgf spectrum loader
     */
    void setMgfSpectrumLoader(MgfSpectrumLoader mgfSpectrumLoader);
       
    /**
     * Loads the experiment
     *
     * @param experimentId the experiment ID
     * @return the experiment
     */
    Experiment loadExperiment(Map<String, File> mgfFiles);
    
}
