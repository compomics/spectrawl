/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.gui.event.MessageEvent;
import com.compomics.spectrawl.service.MgfExperimentService;
import com.compomics.spectrawl.service.MsLimsExperimentService;
import com.compomics.spectrawl.gui.event.UnexpectedErrorMessageEvent;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.gui.view.ExperimentLoaderPanel;
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
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.log4j.Logger;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

/**
 *
 * @author Niels Hulstaert
 */
public class ExperimentLoaderController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentLoaderController.class);
    //model
    private DefaultListModel mgfFilesListModel;
    private Experiment.ExperimentType experimentType;
    private ExperimentLoaderSwingWorker experimentLoaderSwingWorker;
    private boolean msLimsEnabled;
    //view
    private ExperimentLoaderPanel experimentLoaderPanel;
    //parent controller
    private MainController mainController;
    //child controllers
    private ProgressController progressController;
    //services
    private EventBus eventBus;
    private MsLimsExperimentService msLimsExperimentService;
    private MgfExperimentService mgfExperimentService;

    public ExperimentLoaderPanel getExperimentLoaderPanel() {
        return experimentLoaderPanel;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public ProgressController getProgressController() {
        return progressController;
    }

    public void setProgressController(ProgressController progressController) {
        this.progressController = progressController;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public MsLimsExperimentService getMsLimsExperimentService() {
        return msLimsExperimentService;
    }

    public void setMsLimsExperimentService(MsLimsExperimentService msLimsExperimentService) {
        this.msLimsExperimentService = msLimsExperimentService;
    }

    public MgfExperimentService getMgfExperimentService() {
        return mgfExperimentService;
    }

    public void setMgfExperimentService(MgfExperimentService mgfExperimentService) {
        this.mgfExperimentService = mgfExperimentService;
    }

    public boolean isMsLimsEnabled() {
        return msLimsEnabled;
    }

    public void setMsLimsEnabled(boolean msLimsEnabled) {
        this.msLimsEnabled = msLimsEnabled;
    }

    /**
     * Init the controller.
     */
    public void init() {
        //init the panel
        experimentLoaderPanel = new ExperimentLoaderPanel();

        //check if the MSLims tab needs to be disabled
        if (!msLimsEnabled) {
            experimentLoaderPanel.getExperimentSelectionTabbedPane().setEnabledAt(1, false);
        }

        //init child controllers
        progressController.init();

        //register to event bus
        //eventBus.register(this);

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
        fileChooser.setMultiSelectionEnabled(true);
        //set MGF file filter
        fileChooser.setFileFilter(new MgfFileFilter());

        experimentLoaderPanel.getFileChooseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //in response to the button click, show open dialog 
                int returnVal = experimentLoaderPanel.getFileChooser().showOpenDialog(experimentLoaderPanel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    //clear list
                    mgfFilesListModel.clear();
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
        List<String> validationMessages = new ArrayList<>();
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
    public void updateBinConstants() {
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
        Map<String, File> mgfFiles = new HashMap<>();
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

            Experiment experiment = null;
            if (experimentType.equals(Experiment.ExperimentType.MSLIMS)) {

                String msLimsExperimentId = experimentLoaderPanel.getMsLimsExperimentIdTextField().getText();

                LOGGER.info("start loading MsLims experiment " + msLimsExperimentId);

                //load experiment
                msLimsExperimentService.setDoNoiseFiltering(mainController.doWinsorization());
                experiment = msLimsExperimentService.loadExperiment(msLimsExperimentId);

                LOGGER.info("done loading MsLims experiment " + msLimsExperimentId);

            } else if (experimentType.equals(Experiment.ExperimentType.MGF)) {

                LOGGER.info("start loading MGF file(s)");

                //load experiment with selected mgf files
                mgfExperimentService.setDoNoiseFiltering(mainController.doWinsorization());
                experiment = mgfExperimentService.loadExperiment(getMgfFiles());

                LOGGER.info("done loading MGF file(s)");
            }

            LOGGER.debug("Started to calculate quantiles.");
            if (experiment != null) {
                experiment.calculateQuantiles();
            }
            LOGGER.debug("Finished to calculate quantiles.");

            return experiment;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            try {
                Experiment experiment = this.get();
                mainController.onExperimentLoaded(experiment);
            } catch (InterruptedException | ExecutionException ex) {
                LOGGER.error(ex.getMessage(), ex);
                if (ex.getCause() instanceof CannotGetJdbcConnectionException) {
                    eventBus.post(new UnexpectedErrorMessageEvent("Ms-lims connection error", "Spectrawl could not connect to the ms-lims database"
                            + "\n" + "Please check your credentials and try again.", JOptionPane.ERROR_MESSAGE));
                } else {
                    eventBus.post(new UnexpectedErrorMessageEvent(ex.getMessage()));
                }
            } catch (CancellationException ex) {
                LOGGER.info("loading experiment cancelled");
            } finally {
                //hide progress bar
                progressController.hideProgressDialog();
            }
        }
    }
}
