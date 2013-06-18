/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.gui.view.AdvancedMzDeltaFilterDialog;
import com.compomics.spectrawl.gui.view.MzDeltaFilterDialog;
import com.compomics.spectrawl.gui.view.MzRatioFilterDialog;
import com.compomics.spectrawl.logic.filter.mzratio.FilterChain;
import com.compomics.spectrawl.logic.filter.mzratio.impl.BasicMzDeltaFilter;
import com.compomics.spectrawl.logic.filter.mzratio.impl.BasicMzRatioFilter;
import com.compomics.spectrawl.model.FilterParams;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author niels
 */
@Component("filterConfigController")
public class FilterConfigController {

    private static final Logger LOGGER = Logger.getLogger(FilterConfigController.class);
    //model
    private DefaultListModel mzRatioFilterListModel;
    private DefaultListModel mzDeltaFilterListModel;
    private DefaultListModel varCombMzDeltaFilterListModel;
    //view
    private MzRatioFilterDialog mzRatioFilterDialog;
    private MzDeltaFilterDialog mzDeltaFilterDialog;
    private AdvancedMzDeltaFilterDialog advancedMzDeltaFilterDialog;
    //parent controller
    @Autowired
    private MainController mainController;
    //services
    @Autowired
    private FilterChain<SpectrumImpl> spectrumFilterChain;

    public MzRatioFilterDialog getMzRatioFilterDialog() {
        return mzRatioFilterDialog;
    }

    public MzDeltaFilterDialog getMzDeltaFilterDialog() {
        return mzDeltaFilterDialog;
    }

    public AdvancedMzDeltaFilterDialog getAdvancedMzDeltaFilterDialog() {
        return advancedMzDeltaFilterDialog;
    }

    /**
     * Init the controller.
     */
    public void init() {
        //init dialogs
        mzRatioFilterDialog = new MzRatioFilterDialog(mainController.getMainFrame(), true);
        mzDeltaFilterDialog = new MzDeltaFilterDialog(mainController.getMainFrame(), true);
        advancedMzDeltaFilterDialog = new AdvancedMzDeltaFilterDialog(mainController.getMainFrame(), true);

        //init filter chain        
        spectrumFilterChain.setFilterChainType(FilterChain.FilterChainType.AND);

        initMzRatioFilterDialog();
        initMzDeltaFilterDialog();
        initAdvancedMzDeltaFilterDialog();
    }

    /**
     * Check if the winsorization checkbox is selected.
     *
     * @return the filter enabled boolean
     */
    public boolean isWinsorCheckBoxSelected() {
        return mzRatioFilterDialog.getWinsorFilterCheckBox().isSelected();
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
     * Update the winsorization parameters.
     */
    public void updateWinsorizationParameters() {
        FilterParams.WINSOR_CONSTANT.setValue(Double.parseDouble(mzRatioFilterDialog.getWinsorConstantTextField().getText()));
        FilterParams.WINSOR_CONVERGENCE_CRITERION.setValue(Double.parseDouble(mzRatioFilterDialog.getWinsorConvergenceCriterionTextField().getText()));
        FilterParams.WINSOR_OUTLIER_LIMIT.setValue(Double.parseDouble(mzRatioFilterDialog.getWinsorOutlierLimitTextField().getText()));
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
            if (mzRatioFilterDialog.getMzToleranceTextField().getText().equals("")) {
                validationMessages.add("M/Z tolerance is empty");
            }
        }
        
        if (!mzDeltaFilterListModel.isEmpty()) {
            if (mzDeltaFilterDialog.getIntensityThresholdTextField().getText().equals("")) {
                validationMessages.add("Basic M/Z delta intensity threshold is empty");
            }
        }                
        
        if(!varCombMzDeltaFilterListModel.isEmpty()){
            if(advancedMzDeltaFilterDialog.getVarCombIntThresholdTextField().getText().equals("")){
                validationMessages.add("Variable comb M/Z delta intensity threshold is empty");
            }
        }
        
        return validationMessages;
    }

    /**
     * Updates the analyze filters with the user input
     *
     */
    private void updateFilters() {
        //update BasicMzRatioFilter        
        if (!mzRatioFilterListModel.isEmpty()) {
            BasicMzRatioFilter basicMzRatioFilter = new BasicMzRatioFilter();
            basicMzRatioFilter.setMzRatioTolerance(Double.parseDouble(mzRatioFilterDialog.getMzToleranceTextField().getText()));
            spectrumFilterChain.addFilter(basicMzRatioFilter);
            basicMzRatioFilter.setMzRatioFilterValues(new ArrayList<Double>());
            for (Object value : mzRatioFilterListModel.toArray()) {
                basicMzRatioFilter.getMzRatioFilterValues().add((Double) value);
            }
        }

        //update BasicMzDeltaFilter
        if (!mzDeltaFilterListModel.isEmpty()) {
            BasicMzDeltaFilter basicMzDeltaFilter = new BasicMzDeltaFilter();
            basicMzDeltaFilter.setIntensityThreshold(Double.parseDouble(mzDeltaFilterDialog.getIntensityThresholdTextField().getText()));
            spectrumFilterChain.addFilter(basicMzDeltaFilter);
            basicMzDeltaFilter.setIntensitySumFilterValues(new ArrayList<Double>());
            for (Object value : mzDeltaFilterListModel.toArray()) {
                basicMzDeltaFilter.getIntensitySumFilterValues().add((Double) value);
            }
        }

        //update FixedCombMzDeltaFilter
        
        
        
        //update VariableCombMzDeltaFilter
        
    }

    /**
     * Init the M/Z ratio filter dialog.
     *
     */
    private void initMzRatioFilterDialog() {
        //init list model
        mzRatioFilterListModel = new DefaultListModel();
        mzRatioFilterDialog.getMzRatioFilterList().setModel(mzRatioFilterListModel);

        //add action listeners
        mzRatioFilterDialog.getAddMzRatioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mzRatioFilterDialog.getAddMzRatioTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(mzRatioFilterDialog.getAddMzRatioTextField().getText());
                    mzRatioFilterListModel.addElement(value);
                    mzRatioFilterDialog.getAddMzRatioTextField().setText("");
                }
            }
        });

        mzRatioFilterDialog.getRemoveMzRatioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mzRatioFilterDialog.getMzRatioFilterList().getSelectedIndex() != -1) {
                    int index = mzRatioFilterDialog.getMzRatioFilterList().getSelectedIndex();
                    mzRatioFilterListModel.removeElementAt(index);
                }
            }
        });

        /**
         * Init the noise filter panel.
         */
        //init winsorisation configuration
        mzRatioFilterDialog.getWinsorFilterCheckBox().setSelected(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));
        mzRatioFilterDialog.getWinsorConstantTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONSTANT"));
        mzRatioFilterDialog.getWinsorConvergenceCriterionTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONVERGENCE_CRITERION"));
        mzRatioFilterDialog.getWinsorOutlierLimitTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.OUTLIER_LIMIT"));
        enableWinsorTextFields(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));

        //add action listeners
        mzRatioFilterDialog.getWinsorFilterCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mzRatioFilterDialog.getWinsorFilterCheckBox().isSelected()) {
                    enableWinsorTextFields(Boolean.TRUE);
                } else {
                    enableWinsorTextFields(Boolean.FALSE);
                }
            }
        });
    }

    /**
     * Init the M/Z delta filter dialog.
     *
     */
    private void initMzDeltaFilterDialog() {
        //init list model
        mzDeltaFilterListModel = new DefaultListModel();
        mzDeltaFilterDialog.getMzDeltaFilterList().setModel(mzDeltaFilterListModel);

        //add action listeners       
        mzDeltaFilterDialog.getAddMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mzDeltaFilterDialog.getAddMzDeltaTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(mzDeltaFilterDialog.getAddMzDeltaTextField().getText());
                    mzDeltaFilterListModel.addElement(value);
                    mzDeltaFilterDialog.getAddMzDeltaTextField().setText("");
                }
            }
        });

        mzDeltaFilterDialog.getRemoveMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mzDeltaFilterDialog.getMzDeltaFilterList().getSelectedIndex() != -1) {
                    int index = mzDeltaFilterDialog.getMzDeltaFilterList().getSelectedIndex();
                    mzDeltaFilterListModel.removeElementAt(index);
                }
            }
        });
    }

    /**
     * Init the advanced M/Z delta filter dialog.
     *
     */
    private void initAdvancedMzDeltaFilterDialog() {
        //init list model
        varCombMzDeltaFilterListModel = new DefaultListModel();
        advancedMzDeltaFilterDialog.getMzDeltaFilterList().setModel(varCombMzDeltaFilterListModel);

        //add action listeners       
        advancedMzDeltaFilterDialog.getAddMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!advancedMzDeltaFilterDialog.getAddMzDeltaTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(advancedMzDeltaFilterDialog.getAddMzDeltaTextField().getText());
                    varCombMzDeltaFilterListModel.addElement(value);
                    advancedMzDeltaFilterDialog.getAddMzDeltaTextField().setText("");
                }
            }
        });

        advancedMzDeltaFilterDialog.getRemoveMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (advancedMzDeltaFilterDialog.getMzDeltaFilterList().getSelectedIndex() != -1) {
                    int index = advancedMzDeltaFilterDialog.getMzDeltaFilterList().getSelectedIndex();
                    varCombMzDeltaFilterListModel.removeElementAt(index);
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
        mzRatioFilterDialog.getWinsorConstantTextField().setEnabled(enable);
        mzRatioFilterDialog.getWinsorConvergenceCriterionTextField().setEnabled(enable);
        mzRatioFilterDialog.getWinsorOutlierLimitTextField().setEnabled(enable);
    }
}
