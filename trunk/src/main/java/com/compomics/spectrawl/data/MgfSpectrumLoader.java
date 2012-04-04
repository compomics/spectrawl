/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.data;

import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.io.massspectrometry.MgfIndex;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;

/**
 *
 * @author niels
 */
public interface MgfSpectrumLoader extends SpectrumLoader {

    /**
     * Gets the mgf indexes for the given mgf files
     *
     * @return the indexes map (key: mgf file name, value: MgfIndex file)
     */
    Map<String, MgfIndex> getMgfIndexes(Map<String, File> mgfFiles);

    /**
     * Gets the spectrum by index
     *
     * @param index the index of the spectrum in the file
     * @param mfgFileName the name of the mgf file
     * @return the spectrum
     */
    SpectrumImpl getSpectrumByIndex(long index, String mgfFileName);
}
