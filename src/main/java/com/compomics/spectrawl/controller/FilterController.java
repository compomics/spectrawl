/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.filter.analyze.FilterChain;
import com.compomics.spectrawl.filter.analyze.impl.FilterChainImpl;
import com.compomics.spectrawl.filter.analyze.impl.SpectrumBinFilter;
import com.compomics.spectrawl.filter.analyze.impl.SpectrumMzRatioFilter;
import com.compomics.spectrawl.filter.process.impl.WinsorNoiseThresholdFinder;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.spectrawl.view.AnalyzeFilterPanel;
import com.compomics.spectrawl.view.ProcessFilterPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import org.apache.log4j.Logger;

/**
 *
 * @author niels
 */
public class FilterController {

    private static final Logger LOGGER = Logger.getLogger(FilterController.class);
    //model
    private DefaultListModel peakFilterListModel;
    private DefaultListModel binFilterListModel;
    private SpectrumBinFilter spectrumBinFilter;
    private SpectrumMzRatioFilter spectrumMzRatioFilter;
    private FilterChain<SpectrumImpl> spectrumFilterChain;
    //view
    private AnalyzeFilterPanel analyzeFilterPanel;
    private ProcessFilterPanel processFilterPanel;
    //parent controller
    private SpectrawlController spectrawlController;
    
    /**
     * Constructor
     * 
     * @param spectrawlController the parent controller
     */
    public FilterController(SpectrawlController spectrawlController) {
        //init filters
        spectrumBinFilter = new SpectrumBinFilter(0.0, new ArrayList<Double>());
        spectrumMzRatioFilter = new SpectrumMzRatioFilter(0.0, new ArrayList<Double>());
        spectrumFilterChain = new FilterChainImpl<SpectrumImpl>(FilterChain.FilterChainType.AND);
        //add filters to chain
        spectrumFilterChain.addFilter(spectrumBinFilter);
        spectrumFilterChain.addFilter(spectrumMzRatioFilter);

        analyzeFilterPanel = new AnalyzeFilterPanel();
        processFilterPanel = new ProcessFilterPanel();

        this.spectrawlController = spectrawlController;

        initAnalyzeFilterPanel();
        initProcessFilterPanel();
    }
    
    /**
     * Gets the analyze filter view
     * 
     * @return the view
     */
    public AnalyzeFilterPanel getAnalyzeFilterPanel() {
        return analyzeFilterPanel;
    }
    
    /**
     * Gets the process filter view
     * 
     * @return 
     */
    public ProcessFilterPanel getProcessFilterPanel() {
        return processFilterPanel;
    }
    
    /**
     * Checks if the winsorization checkbox is selected
     * 
     * @return the filter enabled boolean
     */    
    public boolean isWinsorCheckBoxSelected() {
        return processFilterPanel.getWinsorFilterCheckBox().isSelected();
    }
    
    /**
     * Gets the filter chain; the combined spectrumBinFilter and spectrumMzRatioFilter
     * 
     * @return the filter chain
     */
    public FilterChain<SpectrumImpl> getFilterChain() {
        updateFilters();
        return spectrumFilterChain;
    }
    
    /**
     * Updates the winsorization filter with the user input
     * 
     * @param winsorNoiseThresholdFinder the winsorization threshold finder
     */
    public void updateWinsorNoiseThresholdFinder(WinsorNoiseThresholdFinder winsorNoiseThresholdFinder) {
        winsorNoiseThresholdFinder.setWinsorConstant(Double.parseDouble(processFilterPanel.getWinsorConstantTextField().getText()));
        winsorNoiseThresholdFinder.setWinsorConvergenceCriterion(Double.parseDouble(processFilterPanel.getWinsorConvergenceCriterionTextField().getText()));
        winsorNoiseThresholdFinder.setWinsorOutlierLimit(Double.parseDouble(processFilterPanel.getWinsorOutlierLimitTextField().getText()));
    }
    
    /**
     * Updates the analyze filters with the user input
     * 
     */
    private void updateFilters() {
        //update spectrumMzRatioFilter
        spectrumMzRatioFilter.getMzRatioFilterValues().clear();
        if (!peakFilterListModel.isEmpty()) {
            spectrumMzRatioFilter.setMzRatioTolerance(Double.parseDouble(analyzeFilterPanel.getMassToleranceTextField().getText()));
        }
        for (Object value : peakFilterListModel.toArray()) {
            spectrumMzRatioFilter.getMzRatioFilterValues().add((Double) value);
        }
        //update spectrumBinFilter
        spectrumBinFilter.getIntensitySumFilterValues().clear();
        if (!binFilterListModel.isEmpty()) {
            spectrumBinFilter.setIntensityThreshold(Double.parseDouble(analyzeFilterPanel.getFilterThresholdTextField().getText()));
        }
        for (Object value : binFilterListModel.toArray()) {
            spectrumBinFilter.getIntensitySumFilterValues().add((Double) value);
        }
    }
    
    /**
     * Validates the user input and composes a String with possible validation error messages
     * 
     * @return the validation message String
     */
    public String validateUserInput() {
        String message = "";
        if (!peakFilterListModel.isEmpty()) {
            if (analyzeFilterPanel.getMassToleranceTextField().getText().equals("")) {
                message += "\n mass tolerance is empty";
            }
        }
        if (!binFilterListModel.isEmpty()) {
            if (analyzeFilterPanel.getFilterThresholdTextField().getText().equals("")) {
                message += "\n bin threshold is empty";
            }
        }
        return message;
    }
    
    /**
     * Inits the analyze filter view
     * 
     */
    private void initAnalyzeFilterPanel() {
        //init list models
        peakFilterListModel = new DefaultListModel();
        analyzeFilterPanel.getPeakFilterList().setModel(peakFilterListModel);
        binFilterListModel = new DefaultListModel();
        analyzeFilterPanel.getBinFilterList().setModel(binFilterListModel);

        //add action listeners
        analyzeFilterPanel.getAddPeakButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (analyzeFilterPanel.getAddPeakTextField().getText() != "") {
                    Double value = Double.parseDouble(analyzeFilterPanel.getAddPeakTextField().getText());
                    peakFilterListModel.addElement(value);
                    analyzeFilterPanel.getAddPeakTextField().setText("");
                }
            }
        });

        analyzeFilterPanel.getRemovePeakButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (analyzeFilterPanel.getPeakFilterList().getSelectedIndex() != -1) {
                    int index = analyzeFilterPanel.getPeakFilterList().getSelectedIndex();
                    peakFilterListModel.removeElementAt(index);
                }
            }
        });

        analyzeFilterPanel.getAddBinButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (analyzeFilterPanel.getAddBinTextField().getText() != "") {
                    Double value = Double.parseDouble(analyzeFilterPanel.getAddBinTextField().getText());
                    binFilterListModel.addElement(value);
                    analyzeFilterPanel.getAddBinTextField().setText("");
                }
            }
        });

        analyzeFilterPanel.getRemoveBinButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (analyzeFilterPanel.getBinFilterList().getSelectedIndex() != -1) {
                    int index = analyzeFilterPanel.getBinFilterList().getSelectedIndex();
                    binFilterListModel.removeElementAt(index);
                }
            }
        });
    }
    
    /**
     * Inits the process filter view
     * 
     */
    private void initProcessFilterPanel() {
        //init winsorisation configuration
        processFilterPanel.getWinsorFilterCheckBox().setSelected(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));
        processFilterPanel.getWinsorConstantTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONSTANT"));
        processFilterPanel.getWinsorConvergenceCriterionTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONVERGENCE_CRITERION"));
        processFilterPanel.getWinsorOutlierLimitTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.OUTLIER_LIMIT"));
        doEnableWinsorTextFields(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));

        //add action listeners
        processFilterPanel.getWinsorFilterCheckBox().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (processFilterPanel.getWinsorFilterCheckBox().isSelected()) {
                    doEnableWinsorTextFields(Boolean.TRUE);
                } else {
                    doEnableWinsorTextFields(Boolean.FALSE);
                }
            }
        });
    }
    
    /**
     * Enables or disables the winsorization text fields
     * 
     * @param doEnable the enable or disable boolean
     */
    private void doEnableWinsorTextFields(boolean doEnable) {
        processFilterPanel.getWinsorConstantTextField().setEnabled(doEnable);
        processFilterPanel.getWinsorConvergenceCriterionTextField().setEnabled(doEnable);
        processFilterPanel.getWinsorOutlierLimitTextField().setEnabled(doEnable);
    }
}
