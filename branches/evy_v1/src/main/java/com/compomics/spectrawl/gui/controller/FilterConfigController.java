/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.gui.view.AdvancedMzDeltaFilterDialog;
import com.compomics.spectrawl.gui.view.MzDeltaFilterDialog;
import com.compomics.spectrawl.gui.view.MzFilterDialog;
import com.compomics.spectrawl.logic.filter.FilterChain;
import com.compomics.spectrawl.logic.filter.impl.BasicMzDeltaFilter;
import com.compomics.spectrawl.logic.filter.impl.BasicMzFilter;
import com.compomics.spectrawl.logic.filter.impl.FilterChainImpl;
import com.compomics.spectrawl.logic.filter.impl.FixedCombMzDeltaFilter;
import com.compomics.spectrawl.logic.filter.impl.PrecRelMzFilter;
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
 * @author Niels Hulstaert
 */
@Component("filterConfigController")
public class FilterConfigController {
    
    private static final Logger LOGGER = Logger.getLogger(FilterConfigController.class);
    //model
    private DefaultListModel mzFilterListModel;
    private DefaultListModel precRelMzFilterListModel;
    private DefaultListModel mzDeltaFilterListModel;
    private DefaultListModel varCombMzDeltaFilterListModel;
    //view
    private MzFilterDialog mzFilterDialog;
    private MzDeltaFilterDialog mzDeltaFilterDialog;
    private AdvancedMzDeltaFilterDialog advancedMzDeltaFilterDialog;
    //parent controller
    @Autowired
    private MainController mainController;
    //services
    @Autowired
    private FilterChain<SpectrumImpl> spectrumFilterChain;
    @Autowired
    private FixedCombMzDeltaFilter fixedCombMzDeltaFilter;
    
    public MzFilterDialog getMzRatioFilterDialog() {
        return mzFilterDialog;
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
        mzFilterDialog = new MzFilterDialog(mainController.getMainFrame(), true);
        mzDeltaFilterDialog = new MzDeltaFilterDialog(mainController.getMainFrame(), true);
        advancedMzDeltaFilterDialog = new AdvancedMzDeltaFilterDialog(mainController.getMainFrame(), true);

        //init filter chain        
        spectrumFilterChain.setFilterChainType(FilterChain.FilterChainType.AND);
        
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
        spectrumFilterChain.reset();
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
        List<String> validationMessages = new ArrayList<String>();
        if (!mzFilterListModel.isEmpty()) {
            if (mzFilterDialog.getMzToleranceTextField().getText().equals("")) {
                validationMessages.add("Basic mass filter: m/z tolerance is empty");
            }
        }
        
        if (!precRelMzFilterListModel.isEmpty()) {
            if (mzFilterDialog.getPrecRelMzToleranceTextField().getText().equals("")) {
                validationMessages.add("Precursor relative m/z filter: mass tolerance is empty");
            }
        }
        
        if (!mzDeltaFilterListModel.isEmpty()) {
            if (mzDeltaFilterDialog.getIntensityThresholdTextField().getText().equals("")) {
                validationMessages.add("Basic m/z delta filter: intensity threshold is empty");
            }
        }
        
        if (!varCombMzDeltaFilterListModel.isEmpty()) {
            if (advancedMzDeltaFilterDialog.getVarCombIntThresholdTextField().getText().equals("")) {
                validationMessages.add("Variable comb m/z delta filter: intensity threshold is empty");
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
                List<Double> massFilterValues = new ArrayList<Double>();
                for (Object value : mzFilterListModel.toArray()) {
                    massFilterValues.add((Double) value);
                }
                basicMzFilter.setMzFilterValues(massFilterValues);
                spectrumFilterChain.addFilter(basicMzFilter);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<SpectrumImpl>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : mzFilterListModel.toArray()) {
                    BasicMzFilter basicMzFilter = new BasicMzFilter();
                    basicMzFilter.setMzTolerance(Double.parseDouble(mzFilterDialog.getMzToleranceTextField().getText()));
                    List<Double> massFilterValues = new ArrayList<Double>();
                    massFilterValues.add((Double) value);
                    basicMzFilter.setMzFilterValues(massFilterValues);
                    orFilterChain.addFilter(basicMzFilter);
                }
                spectrumFilterChain.addFilter(orFilterChain);
            }
        }

        //update PrecRelMzFilter        
        if (!precRelMzFilterListModel.isEmpty()) {
            //check filter type
            if (mzFilterDialog.getPrecRelAndRadioButton().isSelected()) {
                //if the filtertype is "and", add all the values to the same filter                
                PrecRelMzFilter precRelMzFilter = new PrecRelMzFilter();
                precRelMzFilter.setMzTolerance(Double.parseDouble(mzFilterDialog.getPrecRelMzToleranceTextField().getText()));                
                List<Double> massFilterValues = new ArrayList<Double>();
                for (Object value : mzFilterListModel.toArray()) {
                    massFilterValues.add((Double) value);
                }
                precRelMzFilter.setPrecRelMzFilterValues(massFilterValues);
                spectrumFilterChain.addFilter(precRelMzFilter);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<SpectrumImpl>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : mzFilterListModel.toArray()) {
                    PrecRelMzFilter precRelMzFilter = new PrecRelMzFilter();
                    precRelMzFilter.setMzTolerance(Double.parseDouble(mzFilterDialog.getPrecRelMzToleranceTextField().getText()));
                    List<Double> massFilterValues = new ArrayList<Double>();
                    massFilterValues.add((Double) value);
                    precRelMzFilter.setPrecRelMzFilterValues(massFilterValues);
                    orFilterChain.addFilter(precRelMzFilter);
                }
                spectrumFilterChain.addFilter(orFilterChain);
            }
        }

        //update BasicMzDeltaFilter
        if (!mzDeltaFilterListModel.isEmpty()) {
            //check filter type
            if (mzDeltaFilterDialog.getAndRadioButton().isSelected()) {
                //if the filtertype is "and", add all the values to the same filter                
                BasicMzDeltaFilter basicMzDeltaFilter = new BasicMzDeltaFilter();
                basicMzDeltaFilter.setIntensityThreshold(Double.parseDouble(mzDeltaFilterDialog.getIntensityThresholdTextField().getText()));                
                List<Double> mzDeltaFilterValues = new ArrayList<Double>();
                for (Object value : mzDeltaFilterListModel.toArray()) {
                    mzDeltaFilterValues.add((Double) value);
                }
                basicMzDeltaFilter.setMzDeltaFilterValues(mzDeltaFilterValues);
                spectrumFilterChain.addFilter(basicMzDeltaFilter);
            } else {
                //if the filtertype is "or", add all the values to the different filters in the same filterchain            
                FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<SpectrumImpl>();
                orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
                for (Object value : mzDeltaFilterListModel.toArray()) {
                    BasicMzDeltaFilter basicMzDeltaFilter = new BasicMzDeltaFilter();
                    basicMzDeltaFilter.setIntensityThreshold(Double.parseDouble(mzDeltaFilterDialog.getIntensityThresholdTextField().getText()));
                    List<Double> massDeltaFilterValues = new ArrayList<Double>();
                    massDeltaFilterValues.add((Double) value);
                    basicMzDeltaFilter.setMzDeltaFilterValues(massDeltaFilterValues);
                    orFilterChain.addFilter(basicMzDeltaFilter);
                }
                spectrumFilterChain.addFilter(orFilterChain);
            }
        }

        //update FixedCombMzDeltaFilter
        if (!advancedMzDeltaFilterDialog.getFixedCombIntThresholdTextField().getText().equals("")) {            
            double intensityThreshold = Double.parseDouble(advancedMzDeltaFilterDialog.getFixedCombIntThresholdTextField().getText());
            int minConsecBins = Integer.parseInt(advancedMzDeltaFilterDialog.getMinConsecMzDeltasTextField().getText());
            int maxConsecBins = Integer.parseInt(advancedMzDeltaFilterDialog.getMaxConsecMzDeltasTextField().getText());
            double massDeltaFilterValue = Double.parseDouble(advancedMzDeltaFilterDialog.getMzDeltaTextField().getText());
            fixedCombMzDeltaFilter.init(intensityThreshold, minConsecBins, maxConsecBins, massDeltaFilterValue);
            spectrumFilterChain.addFilter(fixedCombMzDeltaFilter);
        }

        //update VariableCombMzDeltaFilter
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
        precRelMzFilterListModel = new DefaultListModel();
        mzFilterDialog.getPrecRelMzFilterList().setModel(precRelMzFilterListModel);

        //set filter type to and
        mzFilterDialog.getPrecRelFilterTypeRadioButtonGroup().setSelected(mzFilterDialog.getPrecRelAndRadioButton().getModel(), true);

        //add action listeners
        mzFilterDialog.getAddPrecRelMzButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mzFilterDialog.getAddPrecRelMzTextField().getText().isEmpty()) {
                    Double value = Double.parseDouble(mzFilterDialog.getAddPrecRelMzTextField().getText());
                    precRelMzFilterListModel.addElement(value);
                    mzFilterDialog.getAddPrecRelMzTextField().setText("");
                }
            }
        });
        
        mzFilterDialog.getRemovePrecRelMzButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mzFilterDialog.getPrecRelMzFilterList().getSelectedIndex() != -1) {
                    int index = mzFilterDialog.getPrecRelMzFilterList().getSelectedIndex();
                    precRelMzFilterListModel.removeElementAt(index);
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
        mzDeltaFilterListModel = new DefaultListModel();
        mzDeltaFilterDialog.getMzDeltaFilterList().setModel(mzDeltaFilterListModel);

        //add buttons to group        
        mzDeltaFilterDialog.getFilterTypeRadioButtonGroup().add(mzDeltaFilterDialog.getAndRadioButton());
        mzDeltaFilterDialog.getFilterTypeRadioButtonGroup().add(mzDeltaFilterDialog.getOrRadioButton());
        
        //set filter type to and
        mzDeltaFilterDialog.getAndRadioButton().setSelected(true);

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
    private void initAdvancedMassDeltaFilterDialog() {
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
        mzFilterDialog.getWinsorConstantTextField().setEnabled(enable);
        mzFilterDialog.getWinsorConvergenceCriterionTextField().setEnabled(enable);
        mzFilterDialog.getWinsorOutlierLimitTextField().setEnabled(enable);
    }
}
