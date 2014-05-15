/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.service;

import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.repository.MsLimsExperimentRepository;
import java.util.List;

/**
 *
 * @author Niels Hulstaert
 */
public interface MsLimsExperimentService extends ExperimentService {

    /**
     * Load the experiment by experiment ID with all spectra.
     *
     * @param experimentId the experiment ID
     * @return the experiment
     */
    Experiment loadExperiment(String experimentId);

    /**
     * Load an artificial experiment by spectrum IDs with all spectra.
     *
     * @param spectrumIds the experiment ID
     * @return the experiment
     */
    Experiment loadExperiment(List<Long> spectrumIds);
}
