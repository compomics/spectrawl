/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.repository.impl;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.repository.MgfExperimentRepository;
import com.compomics.spectrawl.logic.filter.noise.NoiseFilter;
import com.compomics.spectrawl.logic.filter.noise.NoiseThresholdFinder;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.spectrawl.util.PeakUtils;
import com.compomics.util.experiment.massspectrometry.MSnSpectrum;
import com.compomics.util.experiment.massspectrometry.Peak;
import com.compomics.util.experiment.massspectrometry.SpectrumFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

/**
 *
 * @author Niels Hulstaert
 */
@Repository("mgfExperimentRepository")
public class MgfExperimentRepositoryImpl implements MgfExperimentRepository {

    private static final Logger LOGGER = Logger.getLogger(MgfExperimentRepositoryImpl.class);
    private boolean doNoiseFiltering;
    @Autowired
    private NoiseThresholdFinder noiseThresholdFinder;
    @Autowired
    //@Qualifier("spectrumNoiseFilter")
    private NoiseFilter<HashMap<Double, Peak>> spectrumNoiseFilter;

    public MgfExperimentRepositoryImpl() {
        doNoiseFiltering = PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER");
    }

    @Override
    public void setDoNoiseFiltering(boolean doNoiseFiltering) {
        this.doNoiseFiltering = doNoiseFiltering;
    }

    @Override
    public Map<String, List<String>> getSpectrumTitles(Map<String, File> mgfFiles) {
        Map<String, List<String>> spectrumTitles = new HashMap<String, List<String>>();
        
        //first, clear factory
        SpectrumFactory.getInstance().clearFactory();

        for (String mgfFileName : mgfFiles.keySet()) {
            try {
                //add spectra of current mgf file to spectrum factory
                SpectrumFactory.getInstance().addSpectra(mgfFiles.get(mgfFileName));
                spectrumTitles.put(mgfFileName, SpectrumFactory.getInstance().getSpectrumTitles(mgfFileName));
            } catch (FileNotFoundException ex) {
                LOGGER.error(ex.getMessage(), ex);
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
            } catch (ClassNotFoundException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return spectrumTitles;
    }

    @Override
    public SpectrumImpl getSpectrumByKey(String spectrumKey) {
        SpectrumImpl spectrum = null;
        try {
            MSnSpectrum mSnSpectrum = (MSnSpectrum) SpectrumFactory.getInstance().getSpectrum(spectrumKey);            
            
            double noiseThreshold = 0.0;            
            //filter the spectrum if necessary
            if (doNoiseFiltering) {
                //check if noise threshold finder and noise filter are set
                if (spectrumNoiseFilter != null && noiseThresholdFinder != null) {
                    HashMap<Double, Peak> peaks = mSnSpectrum.getPeakMap();
                    noiseThreshold = noiseThresholdFinder.findNoiseThreshold(PeakUtils.getIntensitiesArrayFromPeakList(peaks));
                    peaks = spectrumNoiseFilter.filter(peaks, noiseThreshold);
                    mSnSpectrum.setPeakList(peaks);
                } else {
                    throw new IllegalArgumentException("NoiseFilter and/or ThresholdFinder not set");
                }
            }
                        
            spectrum = new SpectrumImpl(mSnSpectrum, noiseThreshold);            
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (MzMLUnmarshallerException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return spectrum;
    }
}
