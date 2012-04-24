/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.controller;

import com.compomics.spectrawl.bin.ExperimentBinner;
import com.compomics.spectrawl.bin.impl.ExperimentBinnerImpl;
import com.compomics.spectrawl.bin.impl.SpectrumBinnerImpl;
import com.compomics.spectrawl.bin.impl.SpectrumBinnerWithSortingImpl;
import com.compomics.spectrawl.data.*;
import com.compomics.spectrawl.data.impl.MgfExperimentLoaderImpl;
import com.compomics.spectrawl.data.impl.MgfSpectrumLoaderImpl;
import com.compomics.spectrawl.data.impl.MsLimsExperimentLoaderImpl;
import com.compomics.spectrawl.data.impl.MsLimsSpectrumLoaderImpl;
import com.compomics.spectrawl.filter.process.NoiseFilter;
import com.compomics.spectrawl.filter.process.NoiseThresholdFinder;
import com.compomics.spectrawl.filter.process.impl.NoiseFilterImpl;
import com.compomics.spectrawl.filter.process.impl.WinsorNoiseThresholdFinder;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.view.SpectrawlFrame;
import java.awt.GridBagConstraints;
import java.io.File;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author niels
 */
public class SpectrawlController {

    private static final Logger LOGGER = Logger.getLogger(SpectrawlController.class);
    //model
    private Experiment experiment;
    //view
    private SpectrawlFrame spectrawlFrame;
    //child controllers
    private ExperimentLoaderController experimentLoaderController;
    private FilterController filterController;
    private ExperimentBinsController experimentBinsController;
    //services
    private MsLimsExperimentLoader msLimsExperimentLoader;
    private MsLimsSpectrumLoader msLimsSpectrumLoader;
    private MgfExperimentLoader mgfExperimentLoader;
    private MgfSpectrumLoader mgfSpectrumLoader;
    private ExperimentBinner experimentBinner;
    private NoiseFilter noiseFilter;
    private NoiseThresholdFinder noiseThresholdFinder;
    
    /**
     * Constructor
     * 
     * @param spectrawlFrame the main gui
     */
    public SpectrawlController(SpectrawlFrame spectrawlFrame) {
        //init child controllers
        experimentLoaderController = new ExperimentLoaderController(this);
        filterController = new FilterController(this);
        experimentBinsController = new ExperimentBinsController(this);

        this.spectrawlFrame = spectrawlFrame;
        initGui();

        //init services
        msLimsExperimentLoader = new MsLimsExperimentLoaderImpl();
        mgfExperimentLoader = new MgfExperimentLoaderImpl();
        //set spectrum binner
        msLimsExperimentLoader.setSpectrumBinner(new SpectrumBinnerWithSortingImpl());
        mgfExperimentLoader.setSpectrumBinner(new SpectrumBinnerWithSortingImpl());
        
        experimentBinner = new ExperimentBinnerImpl();
        noiseFilter = new NoiseFilterImpl();
        noiseThresholdFinder = new WinsorNoiseThresholdFinder();
    }
    
    /**
     * Inits the main view
     * 
     */
    private void initGui() {
        //add panel components                        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        spectrawlFrame.getExperimentLoaderParentPanel().add(experimentLoaderController.getExperimentLoaderPanel(), gridBagConstraints);
        spectrawlFrame.getProcessFilterParentPanel().add(filterController.getProcessFilterPanel(), gridBagConstraints);
        spectrawlFrame.getAnalyzeFilterParentPanel().add(filterController.getAnalyzeFilterPanel(), gridBagConstraints);
        spectrawlFrame.getExperimentBinsParentPanel().add(experimentBinsController.getExperimentBinsPanel(), gridBagConstraints);
    }
    
    /**
     * Gets the experiment
     * 
     * @return the experiment
     */
    public Experiment getExperiment() {
        return experiment;
    }
    
    /**
     * Sets the experiment
     * 
     * @param experiment the experiment
     */
    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }
    
    /**
     * Loads an mslims experiment
     * 
     * @param experimentId the mslims experiment id
     */
    public void loadMsLimsExperiment(String experimentId) {        
        //check if msLimsSpectrumLoader is initialized
        if (msLimsSpectrumLoader == null) {
            msLimsSpectrumLoader = new MsLimsSpectrumLoaderImpl(ConnectionLoader.getConnection());
            msLimsSpectrumLoader.setNoiseFilter(noiseFilter);
            msLimsSpectrumLoader.setNoiseThresholdFinder(noiseThresholdFinder);

            msLimsExperimentLoader.setMsLimsSpectrumLoader(msLimsSpectrumLoader);
        }

        //check if filtering checkbox is selected
        if (filterController.isWinsorCheckBoxSelected()) {
            filterController.updateWinsorizationFilterConstants();
        }
        msLimsSpectrumLoader.setDoNoiseFiltering(filterController.isWinsorCheckBoxSelected());

        //add filters
        msLimsExperimentLoader.setSpectrumFilter(filterController.getFilterChain());

        //load experiment
        experiment = msLimsExperimentLoader.loadExperiment(experimentId);
        
        //bin experiment
        binExperiment();
    }
    
    /**
     * Loads mgf files
     * 
     * @param mgfFiles the mgf files 
     */
    public void loadMgfExperiment(File[] mgfFiles) {
        
        //check if mgfSpectrumLoader is initialized
        if (mgfSpectrumLoader == null) {
            mgfSpectrumLoader = new MgfSpectrumLoaderImpl();
            mgfSpectrumLoader.setNoiseFilter(noiseFilter);
            mgfSpectrumLoader.setNoiseThresholdFinder(noiseThresholdFinder);
            mgfExperimentLoader.setMgfSpectrumLoader(mgfSpectrumLoader);
        }
        
        //check if filtering checkbox is selected
        if (filterController.isWinsorCheckBoxSelected()) {
            filterController.updateWinsorizationFilterConstants();
        }
        mgfSpectrumLoader.setDoNoiseFiltering(filterController.isWinsorCheckBoxSelected());

        //add filters
        mgfExperimentLoader.setSpectrumFilter(filterController.getFilterChain());
        
        //load experiment with selected mgf files
        experiment = mgfExperimentLoader.loadExperiment(experimentLoaderController.getMgfFiles());
        
        //bin experiment
        binExperiment();
    }
    
    /**
     * Bins the experiment and displays the bins
     * 
     */
    public void binExperiment() {
        //bin experiment
        experimentBinner.binExperiment(experiment);
        
        //show experiment bins
        experimentBinsController.showExperimentInfo("initial number of spectra:" + experiment.getNumberOfSpectra() + ", number of spectra after filtering: " + experiment.getNumberOfFilteredSpectra());
        experimentBinsController.viewExperimentBins(experiment);
    }
    
    /**
     * Removes the current chart from the chart panel
     * and resets the info label
     * 
     */
    public void resetChartPanel() {
        experimentBinsController.resetChartPanel();
    }
    
    /**
     * Shows or hides the progress bar
     * 
     * @param doShow the show or hide boolean
     * @param message the progress message 
     */
    public void showSpectrawlProgressBar(boolean doShow, String message) {
        experimentLoaderController.showSpectrawlProgressBar(doShow, message);
    }
     
    public String validateUserInput(){
        String message = "";
        message += experimentLoaderController.validateUserInput();
        message += filterController.validateUserInput();
        return message;        
    }

    public void showMessageDialog(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(spectrawlFrame.getContentPane(), message, title, messageType);
    }
}