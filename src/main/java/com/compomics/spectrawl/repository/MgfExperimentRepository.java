/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.repository;

import com.compomics.spectrawl.model.SpectrumImpl;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author niels
 */
public interface MgfExperimentRepository extends ExperimentRepository {

    /**
     * Gets the spectrum titles for the given mgf files
     *
     * @return the spectrum titles map (key: the mgf file name, value: the
     * spectrum titles of the spectra in the mgf file)
     */
    Map<String, List<String>> getSpectrumTitles(Map<String, File> mgfFiles);

    /**
     * Gets the spectrum by spectrum key
     *
     * @return the spectrum
     */
    SpectrumImpl getSpectrumByKey(String spectrumKey);
}
