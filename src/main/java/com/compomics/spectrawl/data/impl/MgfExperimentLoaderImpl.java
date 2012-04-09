/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.data.impl;

import com.compomics.spectrawl.bin.SpectrumBinner;
import com.compomics.spectrawl.data.MgfExperimentLoader;
import com.compomics.spectrawl.data.MgfSpectrumLoader;
import com.compomics.spectrawl.filter.analyze.Filter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.io.massspectrometry.MgfIndex;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author niels
 */
public class MgfExperimentLoaderImpl implements MgfExperimentLoader {

    private static final Logger LOGGER = Logger.getLogger(MgfExperimentLoaderImpl.class);
    private Filter<SpectrumImpl> spectrumFilter;
    private MgfSpectrumLoader mgfSpectrumLoader;
    private SpectrumBinner spectrumBinner;

    @Override
    public void setSpectrumFilter(Filter<SpectrumImpl> spectrumFilter) {
        this.spectrumFilter = spectrumFilter;
    }

    @Override
    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
    }

    @Override
    public MgfSpectrumLoader getMgfSpectrumLoader() {
        return mgfSpectrumLoader;
    }

    @Override
    public void setMgfSpectrumLoader(MgfSpectrumLoader mgfSpectrumLoader) {
        this.mgfSpectrumLoader = mgfSpectrumLoader;
    }

    @Override
    public Experiment loadExperiment(Map<String, File> mgfFiles) {
        Experiment experiment = new Experiment("");
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();

        //retrieve map of spectrum indexes from spectrum loader
        Map<String, MgfIndex> mgfIndexes = mgfSpectrumLoader.getMgfIndexes(mgfFiles);
                        
        //iterate over indexes
        for (String mgfFileName : mgfIndexes.keySet()) {
            MgfIndex mgfIndex = mgfIndexes.get(mgfFileName);
                        
            LOGGER.debug("loading mgf file with " + mgfIndex.getNSpectra() + " spectra before filtering.");
            
            //set number of initial spectra
            experiment.setNumberOfSpectra(experiment.getNumberOfSpectra() + mgfIndex.getNSpectra());
            
            //iterate over spectra
            for (String spectrumTitle : mgfIndex.getSpectrumTitles()) {
                SpectrumImpl spectrum = mgfSpectrumLoader.getSpectrumByIndex(mgfIndex.getIndex(spectrumTitle), mgfFileName);

                //bin the spectrum
                spectrumBinner.binSpectrum(spectrum);

                //add the spectrum to the spectra
                //if the spectrum passes the filter
                if (spectrumFilter.passesFilter(spectrum, Boolean.FALSE)) {
                    spectra.add(spectrum);
                }
            }
        }
        
        //set number of filtered spectra
        experiment.setNumberOfFilteredSpectra(spectra.size());
        
        LOGGER.debug("loading experiment with " + spectra.size() + " spectra after filtering.");
        
        //set experiment spectra
        experiment.setSpectra(spectra);

        return experiment;
    }
}
