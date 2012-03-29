/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.controller;

import com.compomics.spectrawl.bin.ExperimentBinner;
import com.compomics.spectrawl.bin.impl.ExperimentBinnerImpl;
import com.compomics.spectrawl.bin.impl.SpectrumBinnerImpl;
import com.compomics.spectrawl.data.ConnectionLoader;
import com.compomics.spectrawl.data.ExperimentLoader;
import com.compomics.spectrawl.data.impl.ExperimentLoaderImpl;
import com.compomics.spectrawl.data.impl.MsLimsSpectrumLoader;
import com.compomics.spectrawl.filter.process.NoiseFilter;
import com.compomics.spectrawl.filter.process.impl.NoiseFilterImpl;
import com.compomics.spectrawl.filter.process.impl.WinsorNoiseThresholdFinder;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.view.SpectrawlFrame;
import java.awt.GridBagConstraints;
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
    private ExperimentLoader experimentLoader;
    private MsLimsSpectrumLoader msLimsSpectrumLoader;
    private ExperimentBinner experimentBinner;
    private NoiseFilter noiseFilter;
    private WinsorNoiseThresholdFinder winsorNoiseThresholdFinder;

    public SpectrawlController(SpectrawlFrame spectrawlFrame) {
        //init child controllers
        experimentLoaderController = new ExperimentLoaderController(this);
        filterController = new FilterController(this);
        experimentBinsController = new ExperimentBinsController(this);

        this.spectrawlFrame = spectrawlFrame;
        initGui();

        //init services
        experimentLoader = new ExperimentLoaderImpl();
        //set spectrum binner
        experimentLoader.setSpectrumBinner(new SpectrumBinnerImpl());
        experimentBinner = new ExperimentBinnerImpl();
        noiseFilter = new NoiseFilterImpl();
        winsorNoiseThresholdFinder = new WinsorNoiseThresholdFinder();
    }

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

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public void loadExperiment(long experimentId) {
        //check if msLimsSpectrumLoader is initialized
        if (msLimsSpectrumLoader == null) {
            msLimsSpectrumLoader = new MsLimsSpectrumLoader(ConnectionLoader.getConnection());
            msLimsSpectrumLoader.setNoiseFilter(noiseFilter);
            msLimsSpectrumLoader.setNoiseThresholdFinder(winsorNoiseThresholdFinder);
        }

        //check if msLimsSpectrumLoader is set as the experimentLoader spectrumLoader
        if (!(experimentLoader.getSpectrumLoader() instanceof MsLimsSpectrumLoader)) {
            experimentLoader.setSpectrumLoader(msLimsSpectrumLoader);
        }

        //check if filtering checkbox is selected
        if (filterController.isWinsorCheckBoxSelected()) {
            filterController.updateWinsorNoiseThresholdFinder(winsorNoiseThresholdFinder);
        }
        msLimsSpectrumLoader.setDoNoiseFiltering(filterController.isWinsorCheckBoxSelected());

        //add filters
        experimentLoader.setSpectrumFilter(filterController.getFilterChain());

        //load experiment
        experiment = experimentLoader.loadExperiment(experimentId, 100);

        //bin experiment
        experimentBinner.binExperiment(experiment);

        //show experiment bins
        experimentBinsController.viewExperimentBins(experiment);
    }
    
    public void showSpectrawlProgressBar(String message) {
        experimentLoaderController.showSpectrawlProgressBar(message);
    }

    public void hideSpectrawlProgressBar() {
        experimentLoaderController.hideSpectrawlProgressBar();
    }
        
    public void showMessageDialog(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(spectrawlFrame.getContentPane(), message, title, messageType);
    }
}