/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.data.MgfExperimentLoader;
import com.compomics.spectrawl.data.MsLimsExperimentLoader;
import com.compomics.spectrawl.gui.event.UnexpectedErrorMessageEvent;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.gui.view.ExperimentLoaderPanel;
import com.compomics.spectrawl.logic.bin.ExperimentBinner;
import com.compomics.util.io.filefilters.MgfFileFilter;
import com.google.common.eventbus.EventBus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
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
    private Experiment.ExperimentType experimentType;
    private ExperimentLoaderSwingWorker experimentLoaderSwingWorker;
    //view
    private ExperimentLoaderPanel experimentLoaderPanel;
    //parent controller
    private MainController mainController;
    //child controllers
    private ProgressController progressController;
    //services
    private EventBus eventBus;
    private MsLimsExperimentLoader msLimsExperimentLoader;
    private MgfExperimentLoader mgfExperimentLoader;
    private ExperimentBinner experimentBinner;

    public MsLimsExperimentLoader getMsLimsExperimentLoader() {
        return msLimsExperimentLoader;
    }

    public void setMsLimsExperimentLoader(MsLimsExperimentLoader msLimsExperimentLoader) {
        this.msLimsExperimentLoader = msLimsExperimentLoader;
    }

    public MgfExperimentLoader getMgfExperimentLoader() {
        return mgfExperimentLoader;
    }

    public void setMgfExperimentLoader(MgfExperimentLoader mgfExperimentLoader) {
        this.mgfExperimentLoader = mgfExperimentLoader;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public ExperimentBinner getExperimentBinner() {
        return experimentBinner;
    }

    public void setExperimentBinner(ExperimentBinner experimentBinner) {
        this.experimentBinner = experimentBinner;
    }

    public DefaultListModel getMgfFilesListModel() {
        return mgfFilesListModel;
    }

    public void setMgfFilesListModel(DefaultListModel mgfFilesListModel) {
        this.mgfFilesListModel = mgfFilesListModel;
    }

    public ProgressController getProgressController() {
        return progressController;
    }

    public void setProgressController(ProgressController progressController) {
        this.progressController = progressController;
    }

    public ExperimentLoaderPanel getExperimentLoaderPanel() {
        return experimentLoaderPanel;
    }

    /**
     * Init the controller.
     */
    public void init() {
        //init the panel
        experimentLoaderPanel = new ExperimentLoaderPanel();

        //init child controllers
        progressController.init();

        //register to event bus
        eventBus.register(this);

        //init model for list
        mgfFilesListModel = new DefaultListModel();
        getExperimentLoaderPanel().getMgfFilesList().setModel(mgfFilesListModel);

        //init bin configuration
        experimentLoaderPanel.getBinFloorTextField().setText(PropertiesConfigurationHolder.getInstance().getString("BINS_FLOOR"));
        experimentLoaderPanel.getBinCeilingTextField().setText(PropertiesConfigurationHolder.getInstance().getString("BINS_CEILING"));
        experimentLoaderPanel.getBinSizeTextField().setText(PropertiesConfigurationHolder.getInstance().getString("BIN_SIZE"));

        //get file chooser
        JFileChooser fileChooser = experimentLoaderPanel.getFileChooser();
        //select only files
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //select multiple file
        fileChooser.setMultiSelectionEnabled(Boolean.TRUE);
        //set MGF file filter
        fileChooser.setFileFilter(new MgfFileFilter());

        experimentLoaderPanel.getFileChooseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //in response to the button click, show open dialog 
                int returnVal = experimentLoaderPanel.getFileChooser().showOpenDialog(experimentLoaderPanel);
                //clear list
                mgfFilesListModel.clear();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    for (int i = 0; i < experimentLoaderPanel.getFileChooser().getSelectedFiles().length; i++) {
                        mgfFilesListModel.add(i, experimentLoaderPanel.getFileChooser().getSelectedFiles()[i].getName());
                    }
                }
            }
        });

        experimentLoaderPanel.getLoadMsLimsExperimentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                experimentType = Experiment.ExperimentType.MSLIMS;
                mainController.onLoadExperiment();
            }
        });

        experimentLoaderPanel.getLoadMgfExperimentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                experimentType = Experiment.ExperimentType.MGF;
                mainController.onLoadExperiment();
            }
        });
    }

    /**
     * On canceled method.
     */
    public void onCanceled() {
        LOGGER.debug("experiment loading canceled.");
        //hide progress bar
        progressController.hideProgressDialog();
        experimentLoaderSwingWorker.cancel(true);
        mainController.onCanceled();
    }

    /**
     * Load the experiment.
     */
    public void loadExperiment() {
        experimentLoaderSwingWorker = new ExperimentLoaderSwingWorker(experimentType);
        experimentLoaderSwingWorker.execute();
    }

    /**
     * Validate the user input and compose a String with possible validation
     * error messages.
     *
     * @return the validation message String
     */
    public List<String> validateUserInput() {
        List<String> validationMessages = new ArrayList<String>();
        if (experimentType.equals(Experiment.ExperimentType.MSLIMS)) {
            if (experimentLoaderPanel.getMsLimsExperimentIdTextField().getText().equals("")) {
                validationMessages.add("mslims experiment id is empty");
            }
        } else if (experimentType.equals(Experiment.ExperimentType.MGF)) {
            if (experimentLoaderPanel.getFileChooser().getSelectedFiles().length == 0) {
                validationMessages.add("no mgf files are selected");
            }
        }
        if (experimentLoaderPanel.getBinFloorTextField().getText().equals("")) {
            validationMessages.add("bin floor is empty");
        }
        if (experimentLoaderPanel.getBinCeilingTextField().getText().equals("")) {
            validationMessages.add("bin ceiling is empty");
        }
        if (experimentLoaderPanel.getBinSizeTextField().getText().equals("")) {
            validationMessages.add("bin size is empty");
        }
        if (Double.parseDouble(experimentLoaderPanel.getBinCeilingTextField().getText()) <= Double.parseDouble(experimentLoaderPanel.getBinFloorTextField().getText())) {
            validationMessages.add("bins ceiling is smaller than bins floor");
        }
        return validationMessages;
    }

    /**
     * Update the bin constants with user input.
     *
     */
    private void updateBinConstants() {
        BinParams.BINS_FLOOR.setValue(Double.parseDouble(experimentLoaderPanel.getBinFloorTextField().getText()));
        BinParams.BINS_CEILING.setValue(Double.parseDouble(experimentLoaderPanel.getBinCeilingTextField().getText()));
        BinParams.BIN_SIZE.setValue(Double.parseDouble(experimentLoaderPanel.getBinSizeTextField().getText()));
    }

    /**
     * Get the selected mgf files.
     *
     * @return the mgf file (key: mgf file name, value: the mgf file)
     */
    private Map<String, File> getMgfFiles() {
        Map<String, File> mgfFiles = new HashMap<String, File>();
        for (File mgfFile : experimentLoaderPanel.getFileChooser().getSelectedFiles()) {
            mgfFiles.put(mgfFile.getName(), mgfFile);
        }
        return mgfFiles;
    }

    //SwingWorker
    private class ExperimentLoaderSwingWorker extends SwingWorker<Experiment, Void> {

        private Experiment.ExperimentType experimentType;

        public ExperimentLoaderSwingWorker(Experiment.ExperimentType experimentType) {
            this.experimentType = experimentType;
        }

        @Override
        protected Experiment doInBackground() throws Exception {
            //show progress bar
            progressController.showProgressBar(6, "Processing.");

            //update bin constants
            updateBinConstants();

            //update filters if winsorization checkbox is selected
            if (mainController.getFilterController().isWinsorCheckBoxSelected()) {                
                mainController.getFilterController().updateFilterChain();
            }
            mainController.getFilterController().updateWinsorisationParameters();

            Experiment experiment = null;
            if (experimentType.equals(Experiment.ExperimentType.MSLIMS)) {

                String msLimsExperimentId = experimentLoaderPanel.getMsLimsExperimentIdTextField().getText();

                LOGGER.info("start loading MsLims experiment " + msLimsExperimentId);

                //load experiment
                msLimsExperimentLoader.getMsLimsSpectrumLoader().setDoNoiseFiltering(mainController.getFilterController().isWinsorCheckBoxSelected());
                experiment = msLimsExperimentLoader.loadExperiment(msLimsExperimentId);

                LOGGER.info("done loading MsLims experiment " + msLimsExperimentId);

            } else if (experimentType.equals(Experiment.ExperimentType.MGF)) {

                LOGGER.info("start loading MGF file(s)");

                //load experiment with selected mgf files
                mgfExperimentLoader.getMgfSpectrumLoader().setDoNoiseFiltering(mainController.getFilterController().isWinsorCheckBoxSelected());
                experiment = mgfExperimentLoader.loadExperiment(getMgfFiles());

                LOGGER.info("done loading MGF file(s)");
            }

            //bin experiment
            if (experiment != null) {
                experimentBinner.binExperiment(experiment);
            }

            return experiment;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            try {
                Experiment experiment = this.get();
                //show experiment bins
                mainController.getResultController().showExperimentInfo("initial number of spectra:" + experiment.getNumberOfSpectra() + ", number of spectra after filtering: " + experiment.getNumberOfFilteredSpectra());
                mainController.getResultController().updateResultPanel(experiment);
            } catch (InterruptedException ex) {
                LOGGER.error(ex.getMessage(), ex);
                eventBus.post(new UnexpectedErrorMessageEvent(ex.getMessage()));
            } catch (ExecutionException ex) {
                LOGGER.error(ex.getMessage(), ex);
                eventBus.post(new UnexpectedErrorMessageEvent(ex.getMessage()));
            } catch (CancellationException ex) {
                LOGGER.info("loading experiment cancelled");                
            } finally {
                //hide progress bar
                progressController.hideProgressDialog();
            }
        }
    }
}
