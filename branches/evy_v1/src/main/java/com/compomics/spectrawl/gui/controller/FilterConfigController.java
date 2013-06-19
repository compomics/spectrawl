/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.gui.view.AdvancedMassDeltaFilterDialog;
import com.compomics.spectrawl.gui.view.MassDeltaFilterDialog;
import com.compomics.spectrawl.gui.view.MassFilterDialog;
import com.compomics.spectrawl.logic.filter.mzratio.FilterChain;
import com.compomics.spectrawl.logic.filter.mzratio.impl.BasicMassDeltaFilter;
import com.compomics.spectrawl.logic.filter.mzratio.impl.BasicMassFilter;
import com.compomics.spectrawl.logic.filter.mzratio.impl.FilterChainImpl;
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
    private DefaultListModel massFilterListModel;
    private DefaultListModel precRelMassFilterListModel;
    private DefaultListModel massDeltaFilterListModel;
    private DefaultListModel varCombMassDeltaFilterListModel;
    //view
    private MassFilterDialog massFilterDialog;
    private MassDeltaFilterDialog massDeltaFilterDialog;
    private AdvancedMassDeltaFilterDialog advancedMassDeltaFilterDialog;
    //parent controller
    @Autowired
    private MainController mainController;
    //services
    @Autowired
    private FilterChain<SpectrumImpl> spectrumFilterChain;

    public MassFilterDialog getMzRatioFilterDialog() {
        return massFilterDialog;
    }

    public MassDeltaFilterDialog getMzDeltaFilterDialog() {
        return massDeltaFilterDialog;
    }

    public AdvancedMassDeltaFilterDialog getAdvancedMzDeltaFilterDialog() {
        return advancedMassDeltaFilterDialog;
    }

    /**
     * Init the controller.
     */
    public void init() {
        //init dialogs
        massFilterDialog = new MassFilterDialog(mainController.getMainFrame(), true);
        massDeltaFilterDialog = new MassDeltaFilterDialog(mainController.getMainFrame(), true);
        advancedMassDeltaFilterDialog = new AdvancedMassDeltaFilterDialog(mainController.getMainFrame(), true);

        //init filter chain        
        spectrumFilterChain.setFilterChainType(FilterChain.FilterChainType.AND);

        initMassFilterDialog();
        initMassDeltaFilterDialog();
        initAdvancedMassDeltaFilterDialog();
    }

    /**
     * Check if the winsorization checkbox is selected.
     *
     * @return the filter enabled boolean
     */
    public boolean isWinsorCheckBoxSelected() {
        return massFilterDialog.getWinsorFilterCheckBox().isSelected();
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
        FilterParams.WINSOR_CONSTANT.setValue(Double.parseDouble(massFilterDialog.getWinsorConstantTextField().getText()));
        FilterParams.WINSOR_CONVERGENCE_CRITERION.setValue(Double.parseDouble(massFilterDialog.getWinsorConvergenceCriterionTextField().getText()));
        FilterParams.WINSOR_OUTLIER_LIMIT.setValue(Double.parseDouble(massFilterDialog.getWinsorOutlierLimitTextField().getText()));
    }

    /**
     * Validate the user input and compose a String with possible validation
     * error messages.
     *
     * @return the validation message list
     */
    public List<String> validateUserInput() {
        List<String> validationMessages = new ArrayList<String>();
        if (!massFilterListModel.isEmpty()) {
            if (massFilterDialog.getMassToleranceTextField().getText().equals("")) {
                validationMessages.add("M/Z tolerance is empty");
            }
        }

        if (!massDeltaFilterListModel.isEmpty()) {
            if (massDeltaFilterDialog.getIntensityThresholdTextField().getText().equals("")) {
                validationMessages.add("Basic M/Z delta intensity threshold is empty");
            }
        }

        if (!varCombMassDeltaFilterListModel.isEmpty()) {
            if (advancedMassDeltaFilterDialog.getVarCombIntThresholdTextField().getText().equals("")) {
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
        if (!massFilterListModel.isEmpty()) {
            //check filter type
            if (massFilterDialog.getFilterTypeRadioButtonGroup().isSelected(massFilterDialog.getAndRadioButton().getModel())) {
                //if the filtertype is "and", add all the values to the same filter                
                BasicMassFilter basicMzRatioFilter = new BasicMassFilter();
                basicMzRatioFilter.setMassTolerance(Double.parseDouble(massFilterDialog.getMassToleranceTextField().getText()));
                spectrumFilterChain.addFilter(basicMzRatioFilter);
                List<Double> mzRatioFilterValues = new ArrayList<Double>();
                for (Object value : massFilterListModel.toArray()) {
                    mzRatioFilterValues.add((Double) value);
                }
                basicMzRatioFilter.setMassFilterValues(mzRatioFilterValues);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<SpectrumImpl>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : massFilterListModel.toArray()) {
                    BasicMassFilter basicMzRatioFilter = new BasicMassFilter();
                    basicMzRatioFilter.setMassTolerance(Double.parseDouble(massFilterDialog.getMassToleranceTextField().getText()));
                    List<Double> massFilterValues = new ArrayList<Double>();
                    massFilterValues.add((Double) value);
                    orFilterChain.addFilter(basicMzRatioFilter);
                }
                spectrumFilterChain.addFilter(orFilterChain);
            }
        }
        
        //update PrecRelMzRatioFilter        
        if (!precRelMassFilterListModel.isEmpty()) {
            //check filter type
            if (massFilterDialog.getPrecRelFilterTypeRadioButtonGroup().isSelected(massFilterDialog.getPrecRelAndRadioButton().getModel())) {
                //if the filtertype is "and", add all the values to the same filter                
                BasicMassFilter basicMzRatioFilter = new BasicMassFilter();
                basicMzRatioFilter.setMassTolerance(Double.parseDouble(massFilterDialog.getMassToleranceTextField().getText()));
                spectrumFilterChain.addFilter(basicMzRatioFilter);
                List<Double> massFilterValues = new ArrayList<Double>();
                for (Object value : massFilterListModel.toArray()) {
                    massFilterValues.add((Double) value);
                }
                basicMzRatioFilter.setMassFilterValues(massFilterValues);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<SpectrumImpl>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : massFilterListModel.toArray()) {
                    BasicMassFilter basicMzRatioFilter = new BasicMassFilter();
                    basicMzRatioFilter.setMassTolerance(Double.parseDouble(massFilterDialog.getMassToleranceTextField().getText()));
                    List<Double> massFilterValues = new ArrayList<Double>();
                    massFilterValues.add((Double) value);
                    orFilterChain.addFilter(basicMzRatioFilter);
                }
                spectrumFilterChain.addFilter(orFilterChain);
            }
        }

        //update BasicMzDeltaFilter
        if (!massDeltaFilterListModel.isEmpty()) {
            //check filter type
            if (massDeltaFilterDialog.getFilterTypeRadioButtonGroup().isSelected(massDeltaFilterDialog.getAndRadioButton().getModel())) {
                //if the filtertype is "and", add all the values to the same filter                
                BasicMassDeltaFilter basicMzDeltaFilter = new BasicMassDeltaFilter();
                basicMzDeltaFilter.setIntensityThreshold(Double.parseDouble(massDeltaFilterDialog.getIntensityThresholdTextField().getText()));
                spectrumFilterChain.addFilter(basicMzDeltaFilter);
                List<Double> intensitySumFilterValues = new ArrayList<Double>();
                for (Object value : massFilterListModel.toArray()) {
                    intensitySumFilterValues.add((Double) value);
                }
                basicMzDeltaFilter.setIntensitySumFilterValues(intensitySumFilterValues);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<SpectrumImpl>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : massFilterListModel.toArray()) {
                    BasicMassDeltaFilter basicMzDeltaFilter = new BasicMassDeltaFilter();
                    basicMzDeltaFilter.setIntensityThreshold(Double.parseDouble(massDeltaFilterDialog.getIntensityThresholdTextField().getText()));
                    List<Double> intensitySumFilterValues = new ArrayList<Double>();
                    intensitySumFilterValues.add((Double) value);
                    orFilterChain.addFilter(basicMzDeltaFilter);
                }
                spectrumFilterChain.addFilter(orFilterChain);
            }
        }

        //update FixedCombMzDeltaFilter



        //update VariableCombMzDeltaFilter

    }

    /**
     * Init the M/Z ratio filter dialog.
     *
     */
    private void initMassFilterDialog() {
        //init list model
        massFilterListModel = new DefaultListModel();
        massFilterDialog.getMassFilterList().setModel(massFilterListModel);

        //set filter type to and
        massFilterDialog.getFilterTypeRadioButtonGroup().setSelected(massFilterDialog.getAndRadioButton().getModel(), true);

        //add action listeners
        massFilterDialog.getAddMassButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!massFilterDialog.getAddMassTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(massFilterDialog.getAddMassTextField().getText());
                    massFilterListModel.addElement(value);
                    massFilterDialog.getAddMassTextField().setText("");
                }
            }
        });

        massFilterDialog.getRemoveMassButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (massFilterDialog.getMassFilterList().getSelectedIndex() != -1) {
                    int index = massFilterDialog.getMassFilterList().getSelectedIndex();
                    massFilterListModel.removeElementAt(index);
                }
            }
        });
        
        //init list model
        precRelMassFilterListModel = new DefaultListModel();
        massFilterDialog.getPrecRelMassFilterList().setModel(massFilterListModel);

        //set filter type to and
        massFilterDialog.getPrecRelFilterTypeRadioButtonGroup().setSelected(massFilterDialog.getPrecRelAndRadioButton().getModel(), true);

        //add action listeners
        massFilterDialog.getAddPrecRelMassButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!massFilterDialog.getAddPrecRelMassTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(massFilterDialog.getAddPrecRelMassTextField().getText());
                    precRelMassFilterListModel.addElement(value);
                    massFilterDialog.getAddPrecRelMassTextField().setText("");
                }
            }
        });

        massFilterDialog.getRemovePrecRelMassButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (massFilterDialog.getPrecRelMassFilterList().getSelectedIndex() != -1) {
                    int index = massFilterDialog.getPrecRelMassFilterList().getSelectedIndex();
                    precRelMassFilterListModel.removeElementAt(index);
                }
            }
        });

        /**
         * Init the noise filter panel.
         */
        //init winsorisation configuration
        massFilterDialog.getWinsorFilterCheckBox().setSelected(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));
        massFilterDialog.getWinsorConstantTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONSTANT"));
        massFilterDialog.getWinsorConvergenceCriterionTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONVERGENCE_CRITERION"));
        massFilterDialog.getWinsorOutlierLimitTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.OUTLIER_LIMIT"));
        enableWinsorTextFields(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));

        //add action listeners
        massFilterDialog.getWinsorFilterCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (massFilterDialog.getWinsorFilterCheckBox().isSelected()) {
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
    private void initMassDeltaFilterDialog() {
        //init list model
        massDeltaFilterListModel = new DefaultListModel();
        massDeltaFilterDialog.getMzDeltaFilterList().setModel(massDeltaFilterListModel);

        //set filter type to and
        massDeltaFilterDialog.getFilterTypeRadioButtonGroup().setSelected(massFilterDialog.getAndRadioButton().getModel(), true);

        //add action listeners       
        massDeltaFilterDialog.getAddMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!massDeltaFilterDialog.getAddMzDeltaTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(massDeltaFilterDialog.getAddMzDeltaTextField().getText());
                    massDeltaFilterListModel.addElement(value);
                    massDeltaFilterDialog.getAddMzDeltaTextField().setText("");
                }
            }
        });

        massDeltaFilterDialog.getRemoveMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (massDeltaFilterDialog.getMzDeltaFilterList().getSelectedIndex() != -1) {
                    int index = massDeltaFilterDialog.getMzDeltaFilterList().getSelectedIndex();
                    massDeltaFilterListModel.removeElementAt(index);
                }
            }
        });
    }

    /**
     * Init the advanced M/Z delta filter dialog.
     *
     */
    private void initAdvancedMassDeltaFilterDialog() {
        //init list model
        varCombMassDeltaFilterListModel = new DefaultListModel();
        advancedMassDeltaFilterDialog.getMzDeltaFilterList().setModel(varCombMassDeltaFilterListModel);

        //add action listeners       
        advancedMassDeltaFilterDialog.getAddMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!advancedMassDeltaFilterDialog.getAddMzDeltaTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(advancedMassDeltaFilterDialog.getAddMzDeltaTextField().getText());
                    varCombMassDeltaFilterListModel.addElement(value);
                    advancedMassDeltaFilterDialog.getAddMzDeltaTextField().setText("");
                }
            }
        });

        advancedMassDeltaFilterDialog.getRemoveMzDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (advancedMassDeltaFilterDialog.getMzDeltaFilterList().getSelectedIndex() != -1) {
                    int index = advancedMassDeltaFilterDialog.getMzDeltaFilterList().getSelectedIndex();
                    varCombMassDeltaFilterListModel.removeElementAt(index);
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
        massFilterDialog.getWinsorConstantTextField().setEnabled(enable);
        massFilterDialog.getWinsorConvergenceCriterionTextField().setEnabled(enable);
        massFilterDialog.getWinsorOutlierLimitTextField().setEnabled(enable);
    }
}
