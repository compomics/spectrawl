/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.gui.view.AdvancedMassDeltaFilterDialog;
import com.compomics.spectrawl.gui.view.MassDeltaFilterDialog;
import com.compomics.spectrawl.gui.view.MzFilterDialog;
import com.compomics.spectrawl.logic.filter.FilterChain;
import com.compomics.spectrawl.logic.filter.impl.BasicMassDeltaFilter;
import com.compomics.spectrawl.logic.filter.impl.BasicMzFilter;
import com.compomics.spectrawl.logic.filter.impl.FilterChainImpl;
import com.compomics.spectrawl.logic.filter.impl.FixedCombMassDeltaFilter;
import com.compomics.spectrawl.logic.filter.impl.PrecRelMassFilter;
import com.compomics.spectrawl.logic.filter.impl.VariableCombMassDeltaFilter;
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
 * @author Niels Hulstaert
 */
public class FilterConfigController {

    private static final Logger LOGGER = Logger.getLogger(FilterConfigController.class);
    //model
    private DefaultListModel mzFilterListModel;
    private DefaultListModel precRelMassFilterListModel;
    private DefaultListModel massDeltaFilterListModel;
    private DefaultListModel varCombMassDeltaFilterListModel;
    //view
    private MzFilterDialog mzFilterDialog;
    private MassDeltaFilterDialog massDeltaFilterDialog;
    private AdvancedMassDeltaFilterDialog advancedMassDeltaFilterDialog;
    //parent controller
    private MainController mainController;
    //services
    private FilterChain<SpectrumImpl> filterChain;
    private FixedCombMassDeltaFilter fixedCombMassDeltaFilter;
    private VariableCombMassDeltaFilter variableCombMassDeltaFilter;

    public MzFilterDialog getMzRatioFilterDialog() {
        return mzFilterDialog;
    }

    public MassDeltaFilterDialog getMzDeltaFilterDialog() {
        return massDeltaFilterDialog;
    }

    public AdvancedMassDeltaFilterDialog getAdvancedMzDeltaFilterDialog() {
        return advancedMassDeltaFilterDialog;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public FilterChain<SpectrumImpl> getFilterChain() {
        return filterChain;
    }

    public void setFilterChain(FilterChain<SpectrumImpl> filterChain) {
        this.filterChain = filterChain;
    }

    public FixedCombMassDeltaFilter getFixedCombMassDeltaFilter() {
        return fixedCombMassDeltaFilter;
    }

    public void setFixedCombMassDeltaFilter(FixedCombMassDeltaFilter fixedCombMassDeltaFilter) {
        this.fixedCombMassDeltaFilter = fixedCombMassDeltaFilter;
    }

    public VariableCombMassDeltaFilter getVariableCombMassDeltaFilter() {
        return variableCombMassDeltaFilter;
    }

    public void setVariableCombMassDeltaFilter(VariableCombMassDeltaFilter variableCombMassDeltaFilter) {
        this.variableCombMassDeltaFilter = variableCombMassDeltaFilter;
    }        

    /**
     * Init the controller.
     */
    public void init() {
        //init dialogs
        mzFilterDialog = new MzFilterDialog(mainController.getMainFrame(), true);
        massDeltaFilterDialog = new MassDeltaFilterDialog(mainController.getMainFrame(), true);
        advancedMassDeltaFilterDialog = new AdvancedMassDeltaFilterDialog(mainController.getMainFrame(), true);

        //init filter chain        
        filterChain.setFilterChainType(FilterChain.FilterChainType.AND);

        initMzFilterDialog();
        initMassDeltaFilterDialog();
        initAdvancedMassDeltaFilterDialog();
    }

    /**
     * Check if the winsorization checkbox is selected.
     *
     * @return the filter enabled boolean
     */
    public boolean isWinsorCheckBoxSelected() {
        return mzFilterDialog.getWinsorFilterCheckBox().isSelected();
    }

    /**
     * Update the filter chain; the combined spectrumBinFilter and
     * spectrumMzRatioFilter.
     *
     */
    public void updateFilterChain() {
        //clear the chain
        filterChain.reset();
        updateFilters();
    }

    /**
     * Update the winsorization parameters.
     */
    public void updateWinsorizationParameters() {
        FilterParams.WINSOR_CONSTANT.setValue(Double.parseDouble(mzFilterDialog.getWinsorConstantTextField().getText()));
        FilterParams.WINSOR_CONVERGENCE_CRITERION.setValue(Double.parseDouble(mzFilterDialog.getWinsorConvergenceCriterionTextField().getText()));
        FilterParams.WINSOR_OUTLIER_LIMIT.setValue(Double.parseDouble(mzFilterDialog.getWinsorOutlierLimitTextField().getText()));
    }

    /**
     * Validate the user input and compose a String with possible validation
     * error messages.
     *
     * @return the validation message list
     */
    public List<String> validateUserInput() {
        List<String> validationMessages = new ArrayList<>();
        if (!mzFilterListModel.isEmpty()) {
            if (mzFilterDialog.getMzToleranceTextField().getText().equals("")) {
                validationMessages.add("Basic m/z filter: m/z tolerance is empty");
            }
        }

        if (!precRelMassFilterListModel.isEmpty()) {
            if (mzFilterDialog.getPrecRelMzToleranceTextField().getText().equals("")) {
                validationMessages.add("Precursor relative mass filter: mass tolerance is empty");
            }
        }

        if (!massDeltaFilterListModel.isEmpty()) {
            if (massDeltaFilterDialog.getIntensityThresholdTextField().getText().equals("")) {
                validationMessages.add("Basic mass delta filter: intensity threshold is empty");
            }
        }

        if (!varCombMassDeltaFilterListModel.isEmpty()) {
            if (advancedMassDeltaFilterDialog.getVarCombIntThresholdTextField().getText().equals("")) {
                validationMessages.add("Variable comb mass delta filter: intensity threshold is empty");
            }
        }

        return validationMessages;
    }

    /**
     * Updates the analyze filters with the user input
     *
     */
    private void updateFilters() {
        //update BasicMzFilter        
        if (!mzFilterListModel.isEmpty()) {
            //check filter type
            if (mzFilterDialog.getAndRadioButton().isSelected()) {
                //if the filtertype is "and", add all the values to the same filter                
                BasicMzFilter basicMzFilter = new BasicMzFilter();
                basicMzFilter.setMzTolerance(Double.parseDouble(mzFilterDialog.getMzToleranceTextField().getText()));
                List<Double> massFilterValues = new ArrayList<>();
                for (Object value : mzFilterListModel.toArray()) {
                    massFilterValues.add((Double) value);
                }
                basicMzFilter.setMzFilterValues(massFilterValues);
                filterChain.addFilter(basicMzFilter);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : mzFilterListModel.toArray()) {
                    BasicMzFilter basicMzFilter = new BasicMzFilter();
                    basicMzFilter.setMzTolerance(Double.parseDouble(mzFilterDialog.getMzToleranceTextField().getText()));
                    List<Double> massFilterValues = new ArrayList<>();
                    massFilterValues.add((Double) value);
                    basicMzFilter.setMzFilterValues(massFilterValues);
                    orFilterChain.addFilter(basicMzFilter);
                }
                filterChain.addFilter(orFilterChain);
            }
        }

        //update PrecRelMassFilter        
        if (!precRelMassFilterListModel.isEmpty()) {
            //check filter type
            if (mzFilterDialog.getPrecRelAndRadioButton().isSelected()) {
                //if the filtertype is "and", add all the values to the same filter                
                PrecRelMassFilter precRelMassFilter = new PrecRelMassFilter();
                precRelMassFilter.setMzTolerance(Double.parseDouble(mzFilterDialog.getPrecRelMzToleranceTextField().getText()));
                List<Double> massFilterValues = new ArrayList<>();
                for (Object value : precRelMassFilterListModel.toArray()) {
                    massFilterValues.add((Double) value);
                }
                precRelMassFilter.setPrecRelMassFilterValues(massFilterValues);
                filterChain.addFilter(precRelMassFilter);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : precRelMassFilterListModel.toArray()) {
                    PrecRelMassFilter precRelMassFilter = new PrecRelMassFilter();
                    precRelMassFilter.setMzTolerance(Double.parseDouble(mzFilterDialog.getPrecRelMzToleranceTextField().getText()));
                    List<Double> massFilterValues = new ArrayList<>();
                    massFilterValues.add((Double) value);
                    precRelMassFilter.setPrecRelMassFilterValues(massFilterValues);
                    orFilterChain.addFilter(precRelMassFilter);
                }
                filterChain.addFilter(orFilterChain);
            }
        }

        //update BasicMassDeltaFilter
        if (!massDeltaFilterListModel.isEmpty()) {
            //check filter type
            if (massDeltaFilterDialog.getAndRadioButton().isSelected()) {
                //if the filtertype is "and", add all the values to the same filter                
                BasicMassDeltaFilter basicMassDeltaFilter = new BasicMassDeltaFilter();
                basicMassDeltaFilter.setIntensityThreshold(Double.parseDouble(massDeltaFilterDialog.getIntensityThresholdTextField().getText()));
                List<Double> mzDeltaFilterValues = new ArrayList<>();
                for (Object value : massDeltaFilterListModel.toArray()) {
                    mzDeltaFilterValues.add((Double) value);
                }
                basicMassDeltaFilter.setMassDeltaFilterValues(mzDeltaFilterValues);
                filterChain.addFilter(basicMassDeltaFilter);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : massDeltaFilterListModel.toArray()) {
                    BasicMassDeltaFilter basicMassDeltaFilter = new BasicMassDeltaFilter();
                    basicMassDeltaFilter.setIntensityThreshold(Double.parseDouble(massDeltaFilterDialog.getIntensityThresholdTextField().getText()));
                    List<Double> massDeltaFilterValues = new ArrayList<>();
                    massDeltaFilterValues.add((Double) value);
                    basicMassDeltaFilter.setMassDeltaFilterValues(massDeltaFilterValues);
                    orFilterChain.addFilter(basicMassDeltaFilter);
                }
                filterChain.addFilter(orFilterChain);
            }
        }

        //update FixedCombMassDeltaFilter
        if (!advancedMassDeltaFilterDialog.getFixedCombIntThresholdTextField().getText().equals("")) {
            double intensityThreshold = Double.parseDouble(advancedMassDeltaFilterDialog.getFixedCombIntThresholdTextField().getText());
            int minConsecBins = Integer.parseInt(advancedMassDeltaFilterDialog.getMinConsecMassDeltasTextField().getText());
            int maxConsecBins = Integer.parseInt(advancedMassDeltaFilterDialog.getMaxConsecMassDeltasTextField().getText());
            double massDeltaFilterValue = Double.parseDouble(advancedMassDeltaFilterDialog.getMassDeltaTextField().getText());
            fixedCombMassDeltaFilter.init(intensityThreshold, minConsecBins, maxConsecBins, massDeltaFilterValue);
            filterChain.addFilter(fixedCombMassDeltaFilter);
        }

        //update VariableCombMassDeltaFilter
        if (!varCombMassDeltaFilterListModel.isEmpty()) {
            double intensityThreshold = Double.parseDouble(advancedMassDeltaFilterDialog.getVarCombIntThresholdTextField().getText());
            double[] massDeltaFilterValues = new double[varCombMassDeltaFilterListModel.size()];
            for (int i = 0; i < varCombMassDeltaFilterListModel.size(); i++) {
                massDeltaFilterValues[i] = (double) varCombMassDeltaFilterListModel.get(i);
            }
            variableCombMassDeltaFilter.init(intensityThreshold, massDeltaFilterValues);
            filterChain.addFilter(variableCombMassDeltaFilter);
        }

    }

    /**
     * Init the m/z ratio filter dialog.
     *
     */
    private void initMzFilterDialog() {
        //init list model
        mzFilterListModel = new DefaultListModel();
        mzFilterDialog.getMzFilterList().setModel(mzFilterListModel);

        //add buttons to group        
        mzFilterDialog.getFilterTypeRadioButtonGroup().add(mzFilterDialog.getAndRadioButton());
        mzFilterDialog.getFilterTypeRadioButtonGroup().add(mzFilterDialog.getOrRadioButton());

        //set filter type to and
        mzFilterDialog.getAndRadioButton().setSelected(true);

        //add action listeners
        mzFilterDialog.getAddMzButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mzFilterDialog.getAddMzTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(mzFilterDialog.getAddMzTextField().getText());
                    mzFilterListModel.addElement(value);
                    mzFilterDialog.getAddMzTextField().setText("");
                }
            }
        });

        mzFilterDialog.getRemoveMzButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mzFilterDialog.getMzFilterList().getSelectedIndex() != -1) {
                    int index = mzFilterDialog.getMzFilterList().getSelectedIndex();
                    mzFilterListModel.removeElementAt(index);
                }
            }
        });

        //init list model
        precRelMassFilterListModel = new DefaultListModel();
        mzFilterDialog.getPrecRelMzFilterList().setModel(precRelMassFilterListModel);

        //add buttons to group        
        mzFilterDialog.getPrecRelFilterTypeRadioButtonGroup().add(mzFilterDialog.getPrecRelAndRadioButton());
        mzFilterDialog.getPrecRelFilterTypeRadioButtonGroup().add(mzFilterDialog.getPrecRelOrRadioButton());

        //set filter type to and
        mzFilterDialog.getPrecRelAndRadioButton().setSelected(true);

        //add action listeners
        mzFilterDialog.getAddPrecRelMzButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mzFilterDialog.getAddPrecRelMzTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(mzFilterDialog.getAddPrecRelMzTextField().getText());
                    precRelMassFilterListModel.addElement(value);
                    mzFilterDialog.getAddPrecRelMzTextField().setText("");
                }
            }
        });

        mzFilterDialog.getRemovePrecRelMzButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mzFilterDialog.getPrecRelMzFilterList().getSelectedIndex() != -1) {
                    int index = mzFilterDialog.getPrecRelMzFilterList().getSelectedIndex();
                    precRelMassFilterListModel.removeElementAt(index);
                }
            }
        });

        /**
         * Init the noise filter panel.
         */
        //init winsorisation configuration
        mzFilterDialog.getWinsorFilterCheckBox().setSelected(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));
        mzFilterDialog.getWinsorConstantTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONSTANT"));
        mzFilterDialog.getWinsorConvergenceCriterionTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.CONVERGENCE_CRITERION"));
        mzFilterDialog.getWinsorOutlierLimitTextField().setText(PropertiesConfigurationHolder.getInstance().getString("WINSOR.OUTLIER_LIMIT"));
        enableWinsorTextFields(PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER"));

        //add action listeners
        mzFilterDialog.getWinsorFilterCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mzFilterDialog.getWinsorFilterCheckBox().isSelected()) {
                    enableWinsorTextFields(true);
                } else {
                    enableWinsorTextFields(false);
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

        //add buttons to group        
        massDeltaFilterDialog.getFilterTypeRadioButtonGroup().add(massDeltaFilterDialog.getAndRadioButton());
        massDeltaFilterDialog.getFilterTypeRadioButtonGroup().add(massDeltaFilterDialog.getOrRadioButton());

        //set filter type to and
        massDeltaFilterDialog.getAndRadioButton().setSelected(true);

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
        advancedMassDeltaFilterDialog.getMassDeltaFilterList().setModel(varCombMassDeltaFilterListModel);

        //add action listeners       
        advancedMassDeltaFilterDialog.getAddMassDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!advancedMassDeltaFilterDialog.getAddMzDeltaTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(advancedMassDeltaFilterDialog.getAddMzDeltaTextField().getText());
                    varCombMassDeltaFilterListModel.addElement(value);
                    advancedMassDeltaFilterDialog.getAddMzDeltaTextField().setText("");
                }
            }
        });

        advancedMassDeltaFilterDialog.getRemoveMassDeltaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (advancedMassDeltaFilterDialog.getMassDeltaFilterList().getSelectedIndex() != -1) {
                    int index = advancedMassDeltaFilterDialog.getMassDeltaFilterList().getSelectedIndex();
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
        mzFilterDialog.getWinsorConstantTextField().setEnabled(enable);
        mzFilterDialog.getWinsorConvergenceCriterionTextField().setEnabled(enable);
        mzFilterDialog.getWinsorOutlierLimitTextField().setEnabled(enable);
    }
}
