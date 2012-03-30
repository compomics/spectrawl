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
    
    public AnalyzeFilterPanel getAnalyzeFilterPanel() {
        return analyzeFilterPanel;
    }
    
    public ProcessFilterPanel getProcessFilterPanel() {
        return processFilterPanel;
    }
    
    public boolean isWinsorCheckBoxSelected() {
        return processFilterPanel.getWinsorFilterCheckBox().isSelected();
    }
    
    public void updateWinsorNoiseThresholdFinder(WinsorNoiseThresholdFinder winsorNoiseThresholdFinder) {
        winsorNoiseThresholdFinder.setWinsorConstant(Double.parseDouble(processFilterPanel.getWinsorConstantTextField().getText()));
        winsorNoiseThresholdFinder.setWinsorConvergenceCriterion(Double.parseDouble(processFilterPanel.getWinsorConvergenceCriterionTextField().getText()));
        winsorNoiseThresholdFinder.setWinsorOutlierLimit(Double.parseDouble(processFilterPanel.getWinsorOutlierLimitTextField().getText()));
    }
    
    public FilterChain<SpectrumImpl> getFilterChain(){        
        return spectrumFilterChain;
    }
    
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
                    spectrumMzRatioFilter.getMzRatioFilterValues().add(value);
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
                    spectrumMzRatioFilter.getMzRatioFilterValues().remove(index);
                }
            }
        });
        
        analyzeFilterPanel.getAddBinButton().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (analyzeFilterPanel.getAddBinTextField().getText() != "") {
                    Double value = Double.parseDouble(analyzeFilterPanel.getAddBinTextField().getText());
                    binFilterListModel.addElement(value);
                    spectrumBinFilter.getIntensitySumFilterValues().add(value);
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
                    spectrumBinFilter.getIntensitySumFilterValues().remove(index);
                }
            }
        });
    }
    
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
    
    private void doEnableWinsorTextFields(boolean doEnable) {
        processFilterPanel.getWinsorConstantTextField().setEnabled(doEnable);
        processFilterPanel.getWinsorConvergenceCriterionTextField().setEnabled(doEnable);
        processFilterPanel.getWinsorOutlierLimitTextField().setEnabled(doEnable);
    }
}
