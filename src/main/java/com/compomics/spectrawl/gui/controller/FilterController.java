/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.logic.filter.analyze.FilterChain;
import com.compomics.spectrawl.logic.filter.analyze.impl.SpectrumBinFilter;
import com.compomics.spectrawl.logic.filter.analyze.impl.SpectrumMzRatioFilter;
import com.compomics.spectrawl.model.FilterParams;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.spectrawl.gui.view.AnalyzeFilterPanel;
import com.compomics.spectrawl.gui.view.ProcessFilterPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
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
    //view
    private AnalyzeFilterPanel analyzeFilterPanel;
    private ProcessFilterPanel processFilterPanel;
    //services
    private FilterChain<SpectrumImpl> spectrumFilterChain;

    public FilterChain<SpectrumImpl> getSpectrumFilterChain() {
        return spectrumFilterChain;
    }

    public void setSpectrumFilterChain(FilterChain<SpectrumImpl> spectrumFilterChain) {
        this.spectrumFilterChain = spectrumFilterChain;
    }

    /**
     * Get the analyze filter view.
     *
     * @return the view
     */
    public AnalyzeFilterPanel getAnalyzeFilterPanel() {
        return analyzeFilterPanel;
    }

    /**
     * Get the process filter view.
     *
     * @return
     */
    public ProcessFilterPanel getProcessFilterPanel() {
        return processFilterPanel;
    }

    /**
     * Init the controller.
     */
    public void init() {
        //init filters
        spectrumBinFilter = new SpectrumBinFilter(0.0, new ArrayList<Double>());
        spectrumMzRatioFilter = new SpectrumMzRatioFilter(0.0, new ArrayList<Double>());
        spectrumFilterChain.setFilterChainType(FilterChain.FilterChainType.AND);
        //add filters to chain
        spectrumFilterChain.addFilter(spectrumBinFilter);
        spectrumFilterChain.addFilter(spectrumMzRatioFilter);

        initAnalyzeFilterPanel();
        initProcessFilterPanel();
    }

    /**
     * Update the filter chain; the combined spectrumBinFilter and
     * spectrumMzRatioFilter.
     *
     */
    public void updateFilterChain() {
        updateFilters();
    }

    /**
     * Check if the winsorization checkbox is selected.
     *
     * @return the filter enabled boolean
     */
    public boolean isWinsorCheckBoxSelected() {
        return processFilterPanel.getWinsorFilterCheckBox().isSelected();
    }

    /**
     * Update the winsorisation parameters.
     */
    public void updateWinsorisationParameters() {
        FilterParams.WINSOR_CONSTANT.setValue(Double.parseDouble(processFilterPanel.getWinsorConstantTextField().getText()));
        FilterParams.WINSOR_CONVERGENCE_CRITERION.setValue(Double.parseDouble(processFilterPanel.getWinsorConvergenceCriterionTextField().getText()));
        FilterParams.WINSOR_OUTLIER_LIMIT.setValue(Double.parseDouble(processFilterPanel.getWinsorOutlierLimitTextField().getText()));
    }

    /**
     * Validate the user input and compose a String with possible validation
     * error messages.
     *
     * @return the validation message list
     */
    public List<String> validateUserInput() {
        List<String> validationMessages = new ArrayList<String>();
        if (!peakFilterListModel.isEmpty()) {
            if (analyzeFilterPanel.getMassToleranceTextField().getText().equals("")) {
                validationMessages.add("mass tolerance is empty");
            }
        }
        if (!binFilterListModel.isEmpty()) {
            if (analyzeFilterPanel.getFilterThresholdTextField().getText().equals("")) {
                validationMessages.add("bin threshold is empty");
            }
        }
        return validationMessages;
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
     * Init the analyze filter view.
     *
     */
    private void initAnalyzeFilterPanel() {
        //init the panel
        analyzeFilterPanel = new AnalyzeFilterPanel();

        //init list models
        peakFilterListModel = new DefaultListModel();
        analyzeFilterPanel.getPeakFilterList().setModel(peakFilterListModel);
        binFilterListModel = new DefaultListModel();
        analyzeFilterPanel.getBinFilterList().setModel(binFilterListModel);

        //add action listeners
        analyzeFilterPanel.getAddPeakButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!analyzeFilterPanel.getAddPeakTextField().getText().isEmpty()) {
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
                if (!analyzeFilterPanel.getAddBinTextField().getText().isEmpty()) {
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
     * Init the process filter view.
     *
     */
    private void initProcessFilterPanel() {
        //init the panel
        processFilterPanel = new ProcessFilterPanel();

        //init winsorisation configuration
        processFilterPanel.getWinsorFilterCheckBox().setSelected(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));
        processFilterPanel.getWinsorConstantTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONSTANT"));
        processFilterPanel.getWinsorConvergenceCriterionTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONVERGENCE_CRITERION"));
        processFilterPanel.getWinsorOutlierLimitTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.OUTLIER_LIMIT"));
        enableWinsorTextFields(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));

        //add action listeners
        processFilterPanel.getWinsorFilterCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (processFilterPanel.getWinsorFilterCheckBox().isSelected()) {
                    enableWinsorTextFields(Boolean.TRUE);
                } else {
                    enableWinsorTextFields(Boolean.FALSE);
                }
            }
        });
    }

    /**
     * Enable or disable the winsorization text fields.
     *
     * @param enable the enable or disable boolean
     */
    private void enableWinsorTextFields(boolean enable) {
        processFilterPanel.getWinsorConstantTextField().setEnabled(enable);
        processFilterPanel.getWinsorConvergenceCriterionTextField().setEnabled(enable);
        processFilterPanel.getWinsorOutlierLimitTextField().setEnabled(enable);
    }
}
