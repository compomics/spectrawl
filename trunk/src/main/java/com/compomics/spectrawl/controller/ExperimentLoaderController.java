/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.model.BinConstants;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.util.GuiUtils;
import com.compomics.spectrawl.view.ExperimentLoaderPanel;
import com.compomics.util.io.filefilters.MgfFileFilter;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.log4j.Logger;

/**
 *
 * @author niels
 */
public class ExperimentLoaderController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentLoaderController.class);
    //model
    private DefaultListModel mgfFilesListModel;
    //view
    private ExperimentLoaderPanel experimentLoaderPanel;
    //parent controller
    private SpectrawlController spectrawlController;
    
    /**
     * Constructor
     * 
     * @param spectrawlController the parent controller
     */
    public ExperimentLoaderController(SpectrawlController spectrawlController) {
        this.spectrawlController = spectrawlController;
        experimentLoaderPanel = new ExperimentLoaderPanel();

        initPanel();
    }
    
    /**
     * Gets the view
     * 
     * @return the view
     */
    public ExperimentLoaderPanel getExperimentLoaderPanel() {
        return experimentLoaderPanel;
    }
    
    /**
     * Shows or hides the progress bar
     * 
     * @param doShow the show or hide boolean
     * @param message the progress message 
     */
    public void showSpectrawlProgressBar(boolean doShow, String message) {
        experimentLoaderPanel.getSpectrawlProgressBar().setVisible(doShow);
        experimentLoaderPanel.getSpectrawlProgressBarLabel().setText(message);
    }
    
    /**
     * Hides the progress bar and resets the progress bar label
     * 
     */
    public void hideSpectrawlProgressBar() {
        experimentLoaderPanel.getSpectrawlProgressBar().setVisible(Boolean.FALSE);
        experimentLoaderPanel.getSpectrawlProgressBarLabel().setText("");
    }
    
    /**
     * Gets the selected mgf files
     * 
     * @return the mgf file (key: mgf file name, value: the mgf file)
     */
    public Map<String, File> getMgfFiles() {
        Map<String, File> mgfFiles = new HashMap<String, File>();
        for (File mgfFile : experimentLoaderPanel.getFileChooser().getSelectedFiles()) {
            mgfFiles.put(mgfFile.getName(), mgfFile);
        }
        return mgfFiles;
    }
    
    /**
     * Updates the bin constants with user input
     * 
     */
    public void updateBinConstants() {
        BinConstants.BINS_FLOOR.setValue(Double.parseDouble(experimentLoaderPanel.getBinFloorTextField().getText()));
        BinConstants.BINS_CEILING.setValue(Double.parseDouble(experimentLoaderPanel.getBinCeilingTextField().getText()));
        BinConstants.BIN_SIZE.setValue(Double.parseDouble(experimentLoaderPanel.getBinSizeTextField().getText()));
    }
    
    /**
     * Validates the user input and composes a String with possible validation error messages
     * 
     * @return the validation message String
     */
    public String validateUserInput() {
        String message = "";
        if(experimentLoaderPanel.getExperimentTypeComboBox().getSelectedIndex() == -1){
            message += "\n no experiment type selected";
        }
        else if (experimentLoaderPanel.getExperimentTypeComboBox().getSelectedItem().equals(Experiment.ExperimentType.MSLIMS)) {
            if (experimentLoaderPanel.getMsLimsExperimentIdTextField().getText().equals("")) {
                message += "\n mslims experiment id is empty";
            }
        } 
        else if (experimentLoaderPanel.getExperimentTypeComboBox().getSelectedItem().equals(Experiment.ExperimentType.MGF)) {
            if (experimentLoaderPanel.getFileChooser().getSelectedFiles().length == 0) {
                message += "\n no mgf files are selected";
            }
        }
        if (experimentLoaderPanel.getBinFloorTextField().getText().equals("")) {
            message += "\n bin floor is empty";
        }
        if (experimentLoaderPanel.getBinCeilingTextField().getText().equals("")) {
            message += "\n bin ceiling is empty";
        }
        if (experimentLoaderPanel.getBinSizeTextField().getText().equals("")) {
            message += "\n bin size is empty";
        }
        if(Double.parseDouble(experimentLoaderPanel.getBinCeilingTextField().getText()) <= Double.parseDouble(experimentLoaderPanel.getBinFloorTextField().getText())){
            message += "\n bins ceiling is smaller than bins floor";
        }
        return message;
    }
    
    /**
     * Inits the view
     * 
     */
    private void initPanel() {
        //init model for list
        mgfFilesListModel = new DefaultListModel();
        getExperimentLoaderPanel().getMgfFilesList().setModel(mgfFilesListModel);
        
        //set progress bar invisible
        experimentLoaderPanel.getSpectrawlProgressBar().setVisible(Boolean.FALSE);
        experimentLoaderPanel.getSpectrawlProgressBar().setIndeterminate(Boolean.TRUE);

        //remove panels
        experimentLoaderPanel.getExperimentSelectionPanel().remove(experimentLoaderPanel.getMsLimsPanel());
        experimentLoaderPanel.getExperimentSelectionPanel().remove(experimentLoaderPanel.getMgfPanel());

        //init bin configuration
        experimentLoaderPanel.getBinFloorTextField().setText(PropertiesConfigurationHolder.getInstance().getString("BINS_FLOOR"));
        experimentLoaderPanel.getBinCeilingTextField().setText(PropertiesConfigurationHolder.getInstance().getString("BINS_CEILING"));
        experimentLoaderPanel.getBinSizeTextField().setText(PropertiesConfigurationHolder.getInstance().getString("BIN_SIZE"));
        
        //add items to combo box
        for (Experiment.ExperimentType experimentType : Experiment.ExperimentType.values()) {
            experimentLoaderPanel.getExperimentTypeComboBox().addItem(experimentType);
        }
        experimentLoaderPanel.getExperimentTypeComboBox().setSelectedIndex(-1);

        //get file chooser
        JFileChooser fileChooser = experimentLoaderPanel.getFileChooser();
        //select only files
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //select multiple file
        fileChooser.setMultiSelectionEnabled(Boolean.TRUE);
        //set MGF file filter
        fileChooser.setFileFilter(new MgfFileFilter());

        //add actionlisteners
        experimentLoaderPanel.getExperimentTypeComboBox().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Experiment.ExperimentType selectedExperimentType = (Experiment.ExperimentType) experimentLoaderPanel.getExperimentTypeComboBox().getSelectedItem();
                GridBagConstraints gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.fill = GridBagConstraints.BOTH;
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 1;        
                switch (selectedExperimentType) {
                    case MSLIMS:
                        GuiUtils.switchChildPanels(experimentLoaderPanel.getExperimentSelectionPanel(), experimentLoaderPanel.getMsLimsPanel(), experimentLoaderPanel.getMgfPanel(), gridBagConstraints);
                        break;
                    case MGF:
                        GuiUtils.switchChildPanels(experimentLoaderPanel.getExperimentSelectionPanel(), experimentLoaderPanel.getMgfPanel(), experimentLoaderPanel.getMsLimsPanel(), gridBagConstraints);
                        break;
                }
            }
        });

        experimentLoaderPanel.getFileChooseButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //in response to the button click, show open dialog 
                int returnVal = experimentLoaderPanel.getFileChooser().showOpenDialog(experimentLoaderPanel);
                //clear list
                mgfFilesListModel.clear();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    for(int i = 0 ; i < experimentLoaderPanel.getFileChooser().getSelectedFiles().length; i++){
                        mgfFilesListModel.add(i, experimentLoaderPanel.getFileChooser().getSelectedFiles()[i].getName());                                                
                    }
                }
            }
        });

        experimentLoaderPanel.getLoadExperimentButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String message = spectrawlController.validateUserInput();
                if (message.equals("")) {
                    //reset chart panel
                    spectrawlController.resetChartPanel();
                    //update bin constants
                    updateBinConstants();
                    ExperimentLoaderSwingWorker experimentLoaderSwingWorker = new ExperimentLoaderSwingWorker();
                    experimentLoaderSwingWorker.execute();
                } else {
                    spectrawlController.showMessageDialog("Validation errors", "Validation errors found: \n" + message, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //swing worker
    private class ExperimentLoaderSwingWorker extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            if (experimentLoaderPanel.getExperimentTypeComboBox().getSelectedItem().equals(Experiment.ExperimentType.MSLIMS)) {

                spectrawlController.showSpectrawlProgressBar(Boolean.TRUE, "loading experiment...");
                String msLimsExperimentId = experimentLoaderPanel.getMsLimsExperimentIdTextField().getText();

                LOGGER.debug("loading mslims experiment " + msLimsExperimentId);

                spectrawlController.loadMsLimsExperiment(msLimsExperimentId);

                LOGGER.debug("finished loading mslims experiment " + msLimsExperimentId);
            } else if (experimentLoaderPanel.getExperimentTypeComboBox().getSelectedItem().equals(Experiment.ExperimentType.MGF)) {

                spectrawlController.showSpectrawlProgressBar(Boolean.TRUE, "loading experiment...");
                LOGGER.debug("loading MGF file(s)");

                spectrawlController.loadMgfExperiment(experimentLoaderPanel.getFileChooser().getSelectedFiles());

                LOGGER.debug("finished loading MGF file(s)");
            }

            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            try {
                this.get();
            } catch (InterruptedException ex) {
                LOGGER.error(ex.getCause(), ex);
            } catch (ExecutionException ex) {
                LOGGER.error(ex.getCause(), ex);
            } finally {
                spectrawlController.showSpectrawlProgressBar(Boolean.FALSE, "");
            }
        }
    }
}
