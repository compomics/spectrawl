/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.data.impl;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.data.MgfSpectrumLoader;
import com.compomics.spectrawl.data.SpectrumLoader;
import com.compomics.spectrawl.filter.process.NoiseFilter;
import com.compomics.spectrawl.filter.process.NoiseThresholdFinder;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.io.massspectrometry.MgfIndex;
import com.compomics.util.experiment.io.massspectrometry.MgfReader;
import com.compomics.util.experiment.massspectrometry.MSnSpectrum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author niels
 */
public class MgfSpectrumLoaderImpl implements MgfSpectrumLoader {

    private static final Logger LOGGER = Logger.getLogger(MgfSpectrumLoaderImpl.class);
    private boolean doNoiseFiltering;
    private NoiseThresholdFinder noiseThresholdFinder;
    private NoiseFilter noiseFilter;
    private Map<String, File> mgfFiles;
    //private Map<String, MgfIndex> mgfIndexes;

    public MgfSpectrumLoaderImpl() {        
        doNoiseFiltering = doNoiseFiltering = PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER");
    }

    @Override
    public void setDoNoiseFiltering(boolean doNoiseFiltering) {
        this.doNoiseFiltering = doNoiseFiltering;
    }

    @Override
    public void setNoiseThresholdFinder(NoiseThresholdFinder noiseThresholdFinder) {
        this.noiseThresholdFinder = noiseThresholdFinder;
    }

    @Override
    public void setNoiseFilter(NoiseFilter noiseFilter) {
        this.noiseFilter = noiseFilter;
    }

    @Override
    public Map<String, MgfIndex> getMgfIndexes(Map<String, File> mgfFiles) {
        this.mgfFiles = mgfFiles;
        Map<String, MgfIndex> mgfIndexes = new HashMap<String, MgfIndex>();
        for (String mgfFileName : mgfFiles.keySet()) {
            try {
                mgfIndexes.put(mgfFileName, MgfReader.getIndexMap(mgfFiles.get(mgfFileName)));
            } catch (FileNotFoundException ex) {
                LOGGER.error(ex.getMessage(), ex);
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return mgfIndexes;
    }

    @Override
    public SpectrumImpl getSpectrumByIndex(long index, String mgfFileName) {
        SpectrumImpl spectrum = null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(mgfFiles.get(mgfFileName), "r");
            spectrum = new SpectrumImpl(MgfReader.getSpectrum(randomAccessFile, index, mgfFileName));
        } catch (FileNotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return spectrum;
    }
}
