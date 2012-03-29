/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.controller;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.util.GuiUtils;
import com.compomics.spectrawl.view.ExperimentLoaderPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutionException;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import org.apache.log4j.Logger;

/**
 *
 * @author niels
 */
public class ExperimentLoaderController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentLoaderController.class);  
    //view
    private ExperimentLoaderPanel experimentLoaderPanel;
    //parent controller
    private SpectrawlController spectrawlController;

    public ExperimentLoaderController(SpectrawlController spectrawlController) {
        this.spectrawlController = spectrawlController;
        experimentLoaderPanel = new ExperimentLoaderPanel();

        initPanel();
    }
    
    public ExperimentLoaderPanel getExperimentLoaderPanel() {
        return experimentLoaderPanel;
    }
    
    public void showSpectrawlProgressBar(String message){
        experimentLoaderPanel.getSpectrawlProgressBar().setVisible(Boolean.TRUE);
        experimentLoaderPanel.getSpectrawlProgressBarLabel().setText(message);
    }
    
    public void hideSpectrawlProgressBar(){
        experimentLoaderPanel.getSpectrawlProgressBar().setVisible(Boolean.FALSE);
        experimentLoaderPanel.getSpectrawlProgressBarLabel().setText("");
    }

    private void initPanel() {
        //set progress bar invisible
        experimentLoaderPanel.getSpectrawlProgressBar().setVisible(Boolean.FALSE);

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

        //add actionlisteners
        experimentLoaderPanel.getExperimentTypeComboBox().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Experiment.ExperimentType selectedExperimentType = (Experiment.ExperimentType) experimentLoaderPanel.getExperimentTypeComboBox().getSelectedItem();
                switch (selectedExperimentType) {
                    case MsLims:
                        if (!GuiUtils.isComponentPresent(experimentLoaderPanel.getMsLimsPanel(), experimentLoaderPanel.getExperimentSelectionPanel())) {
                            experimentLoaderPanel.getExperimentSelectionPanel().add(experimentLoaderPanel.getMsLimsPanel());
                        }
                        if (GuiUtils.isComponentPresent(experimentLoaderPanel.getMgfPanel(), experimentLoaderPanel.getExperimentSelectionPanel())) {
                            experimentLoaderPanel.getExperimentSelectionPanel().remove(experimentLoaderPanel.getMgfPanel());
                        }
                        experimentLoaderPanel.getExperimentSelectionPanel().revalidate();
                        break;
                    case Mgf:
                        if (!GuiUtils.isComponentPresent(experimentLoaderPanel.getMgfPanel(), experimentLoaderPanel.getExperimentSelectionPanel())) {
                            experimentLoaderPanel.getExperimentSelectionPanel().add(experimentLoaderPanel.getMgfPanel());
                        }
                        if (GuiUtils.isComponentPresent(experimentLoaderPanel.getMsLimsPanel(), experimentLoaderPanel.getExperimentSelectionPanel())) {
                            experimentLoaderPanel.getExperimentSelectionPanel().remove(experimentLoaderPanel.getMsLimsPanel());
                        }
                        experimentLoaderPanel.getExperimentSelectionPanel().revalidate();
                        break;
                }
            }
        });

        experimentLoaderPanel.getFileChooseButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                //select only files
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                //in response to the button click, show open dialog 
                int returnVal = fileChooser.showOpenDialog(experimentLoaderPanel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File projectDirectory = fileChooser.getSelectedFile();
                    experimentLoaderPanel.getMgfFileTextField().setText(projectDirectory.getName());
                }
            }
        });

        experimentLoaderPanel.getLoadExperimentButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                spectrawlController.showSpectrawlProgressBar("loading experiment...");
                ExperimentLoaderSwingWorker experimentLoaderSwingWorker = new ExperimentLoaderSwingWorker();
                experimentLoaderSwingWorker.execute();
            }
        });
    }
    
    //swing worker
    private class ExperimentLoaderSwingWorker extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            if (GuiUtils.isComponentPresent(experimentLoaderPanel.getMsLimsPanel(), experimentLoaderPanel.getExperimentSelectionPanel())) {
                String msLimsExperimentId = experimentLoaderPanel.getMsLimsExperimentIdTextField().getText();

                LOGGER.debug("loading experiment " + msLimsExperimentId);

                long experimentId = Long.parseLong(msLimsExperimentId);
                spectrawlController.loadExperiment(experimentId);                                

                LOGGER.debug("finished loading experiment " + msLimsExperimentId);
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
                spectrawlController.hideSpectrawlProgressBar();
            }
        }
    }
}
