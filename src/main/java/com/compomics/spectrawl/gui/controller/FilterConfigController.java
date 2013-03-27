/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.gui.view.FilterConfigDialog;
import com.compomics.spectrawl.logic.filter.mzratio.FilterChain;
import com.compomics.spectrawl.logic.filter.mzratio.impl.SpectrumMzDeltaFilter;
import com.compomics.spectrawl.logic.filter.mzratio.impl.SpectrumMzRatioFilter;
import com.compomics.spectrawl.model.FilterParams;
import com.compomics.spectrawl.model.SpectrumImpl;
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
public class FilterConfigController {

    private static final Logger LOGGER = Logger.getLogger(FilterConfigController.class);
    //model
    private DefaultListModel mzRatioFilterListModel;
    private DefaultListModel mzDeltaFilterListModel;
    private SpectrumMzDeltaFilter spectrumMzDeltaFilter;
    private SpectrumMzRatioFilter spectrumMzRatioFilter;
    //view
    private FilterConfigDialog filterConfigDialog;
    //parent controller
    private MainController mainController;
    //services
    private FilterChain<SpectrumImpl> spectrumFilterChain;

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public FilterChain<SpectrumImpl> getSpectrumFilterChain() {
        return spectrumFilterChain;
    }

    public void setSpectrumFilterChain(FilterChain<SpectrumImpl> spectrumFilterChain) {
        this.spectrumFilterChain = spectrumFilterChain;
    }

    public FilterConfigDialog getFilterConfigDialog() {
        return filterConfigDialog;
    }        

    /**
     * Init the controller.
     */
    public void init() {
        //init dialog
        filterConfigDialog = new FilterConfigDialog(mainController.getMainFrame(), true);

        //init filters
        spectrumMzDeltaFilter = new SpectrumMzDeltaFilter(0.0, new ArrayList<Double>());
        spectrumMzRatioFilter = new SpectrumMzRatioFilter(0.0, new ArrayList<Double>());
        spectrumFilterChain.setFilterChainType(FilterChain.FilterChainType.AND);
        //add filters to chain
        spectrumFilterChain.addFilter(spectrumMzDeltaFilter);
        spectrumFilterChain.addFilter(spectrumMzRatioFilter);

        initMzRatioFilterPanel();
        initNoiseFilterPanel();
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
        return filterConfigDialog.getWinsorFilterCheckBox().isSelected();
    }

    /**
     * Update the winsorisation parameters.
     */
    public void updateWinsorisationParameters() {
        FilterParams.WINSOR_CONSTANT.setValue(Double.parseDouble(filterConfigDialog.getWinsorConstantTextField().getText()));
        FilterParams.WINSOR_CONVERGENCE_CRITERION.setValue(Double.parseDouble(filterConfigDialog.getWinsorConvergenceCriterionTextField().getText()));
        FilterParams.WINSOR_OUTLIER_LIMIT.setValue(Double.parseDouble(filterConfigDialog.getWinsorOutlierLimitTextField().getText()));
    }

    /**
     * Validate the user input and compose a String with possible validation
     * error messages.
     *
     * @return the validation message list
     */
    public List<String> validateUserInput() {
        List<String> validationMessages = new ArrayList<String>();
        if (!mzRatioFilterListModel.isEmpty()) {
            if (filterConfigDialog.getMzToleranceTextField().getText().equals("")) {
                validationMessages.add("M/Z tolerance is empty");
            }
        }
        if (!mzDeltaFilterListModel.isEmpty()) {
            if (filterConfigDialog.getIntensityThresholdTextField().getText().equals("")) {
                validationMessages.add("Intensity threshold is empty");
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
        if (!mzRatioFilterListModel.isEmpty()) {
            spectrumMzRatioFilter.setMzRatioTolerance(Double.parseDouble(filterConfigDialog.getMzToleranceTextField().getText()));
        }
        for (Object value : mzRatioFilterListModel.toArray()) {
            spectrumMzRatioFilter.getMzRatioFilterValues().add((Double) value);
        }
        //update spectrumBinFilter
        spectrumMzDeltaFilter.getIntensitySumFilterValues().clear();
        if (!mzDeltaFilterListModel.isEmpty()) {
            spectrumMzDeltaFilter.setIntensityThreshold(Double.parseDouble(filterConfigDialog.getIntensityThresholdTextField().getText()));
        }
        for (Object value : mzDeltaFilterListModel.toArray()) {
            spectrumMzDeltaFilter.getIntensitySumFilterValues().add((Double) value);
        }
    }

    /**
     * Init the M/Z ratio filter view (peakMzRatioFilterPanel and
     * mzDeltaFilterPanel).
     *
     */
    private void initMzRatioFilterPanel() {
        //init list models
        mzRatioFilterListModel = new DefaultListModel();
        filterConfigDialog.getMzRatioFilterList().setModel(mzRatioFilterListModel);
        mzDeltaFilterListModel = new DefaultListModel();
        filterConfigDialog.getMzDeltaFilterList().setModel(mzDeltaFilterListModel);

        //add action listeners
        filterConfigDialog.getAddMzRatioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!filterConfigDialog.getAddMzRatioTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(filterConfigDialog.getAddMzRatioTextField().getText());
                    mzRatioFilterListModel.addElement(value);
                    filterConfigDialog.getAddMzRatioTextField().setText("");
                }
            }
        });

        filterConfigDialog.getRemoveMzRatioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filterConfigDialog.getMzRatioFilterList().getSelectedIndex() != -1) {
                    int index = filterConfigDialog.getMzRatioFilterList().getSelectedIndex();
                    mzRatioFilterListModel.removeElementAt(index);
                }
            }
        });

        filterConfigDialog.getAddMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!filterConfigDialog.getAddMzDeltaTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(filterConfigDialog.getAddMzDeltaTextField().getText());
                    mzDeltaFilterListModel.addElement(value);
                    filterConfigDialog.getAddMzDeltaTextField().setText("");
                }
            }
        });

        filterConfigDialog.getRemoveMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filterConfigDialog.getMzDeltaFilterList().getSelectedIndex() != -1) {
                    int index = filterConfigDialog.getMzDeltaFilterList().getSelectedIndex();
                    mzDeltaFilterListModel.removeElementAt(index);
                }
            }
        });
    }

    /**
     * Init the noise filter panel.
     *
     */
    private void initNoiseFilterPanel() {
        //init winsorisation configuration
        filterConfigDialog.getWinsorFilterCheckBox().setSelected(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));
        filterConfigDialog.getWinsorConstantTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONSTANT"));
        filterConfigDialog.getWinsorConvergenceCriterionTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONVERGENCE_CRITERION"));
        filterConfigDialog.getWinsorOutlierLimitTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.OUTLIER_LIMIT"));
        enableWinsorTextFields(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));

        //add action listeners
        filterConfigDialog.getWinsorFilterCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filterConfigDialog.getWinsorFilterCheckBox().isSelected()) {
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
        filterConfigDialog.getWinsorConstantTextField().setEnabled(enable);
        filterConfigDialog.getWinsorConvergenceCriterionTextField().setEnabled(enable);
        filterConfigDialog.getWinsorOutlierLimitTextField().setEnabled(enable);
    }
}
