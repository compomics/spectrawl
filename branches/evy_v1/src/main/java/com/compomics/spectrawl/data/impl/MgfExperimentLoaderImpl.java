/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.data.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.data.MgfExperimentLoader;
import com.compomics.spectrawl.data.MgfSpectrumLoader;
import com.compomics.spectrawl.logic.filter.mzratio.Filter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Spectrum;
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

    public void setSpectrumFilter(Filter<SpectrumImpl> spectrumFilter) {
        this.spectrumFilter = spectrumFilter;
    }

    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
    }

    @Override
    public MgfSpectrumLoader getMgfSpectrumLoader() {
        return mgfSpectrumLoader;
    }

    public void setMgfSpectrumLoader(MgfSpectrumLoader mgfSpectrumLoader) {
        this.mgfSpectrumLoader = mgfSpectrumLoader;
    }

    @Override
    public Experiment loadExperiment(Map<String, File> mgfFiles) {
        Experiment experiment = new Experiment("");
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();

        //retrieve map of spectrum titles from the spectrum loader
        Map<String, List<String>> spectrumTitlesMap = mgfSpectrumLoader.getSpectrumTitles(mgfFiles);
                        
        //iterate over spectrum titles
        for (String mgfFileName : spectrumTitlesMap.keySet()) {
            List<String> spectrumTitles = spectrumTitlesMap.get(mgfFileName);
                        
            LOGGER.info("start loading mgf file with " + spectrumTitles.size() + " spectra before filtering");
            
            //set number of initial spectra
            experiment.setNumberOfSpectra(experiment.getNumberOfSpectra() + spectrumTitles.size());
            
            //iterate over spectra
            for (String spectrumTitle : spectrumTitles) {
                SpectrumImpl spectrum = mgfSpectrumLoader.getSpectrumByKey(Spectrum.getSpectrumKey(mgfFileName, spectrumTitle));

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
        
        LOGGER.info("dibe loading experiment with " + spectra.size() + " spectra after filtering");
        
        //set experiment spectra
        experiment.setSpectra(spectra);

        return experiment;
    }
}
