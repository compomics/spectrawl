/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.gui.event.MessageEvent;
import com.compomics.spectrawl.gui.event.UnexpectedErrorMessageEvent;
import com.compomics.spectrawl.gui.view.MainFrame;
import com.compomics.spectrawl.model.Experiment;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;

/**
 *
 * @author Niels Hulstaert
 */
public class MainController implements ActionListener {

    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    //model
    //private Experiment experiment;
    //view
    private MainFrame mainFrame;
    //child controllers
    private ExperimentLoaderController experimentLoaderController;
    private FilterConfigController filterConfigController;
    private ResultController resultController;
    //services
    private EventBus eventBus;    

    public MainFrame getMainFrame() {
        return mainFrame;
    }    

    public ExperimentLoaderController getExperimentLoaderController() {
        return experimentLoaderController;
    }

    public void setExperimentLoaderController(ExperimentLoaderController experimentLoaderController) {
        this.experimentLoaderController = experimentLoaderController;
    }

    public FilterConfigController getFilterConfigController() {
        return filterConfigController;
    }

    public void setFilterConfigController(FilterConfigController filterConfigController) {
        this.filterConfigController = filterConfigController;
    }

    public ResultController getResultController() {
        return resultController;
    }

    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }        

    /**
     * Init the controller.
     */
    public void init() {
        //set uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LOGGER.error(e.getMessage(), e);
                eventBus.post(new UnexpectedErrorMessageEvent(e.getMessage()));
            }
        });

        //init the frame
        mainFrame = new MainFrame();

        //register to event bus
        eventBus.register(this);

        //init child controllers
        experimentLoaderController.init();
        filterConfigController.init();
        resultController.init();

        //add panel components                        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        mainFrame.getExperimentLoaderParentPanel().add(experimentLoaderController.getExperimentLoaderPanel(), gridBagConstraints);
        mainFrame.getExperimentBinsParentPanel().add(resultController.getResultPanel(), gridBagConstraints);

        //add action listeners
        mainFrame.getExitMenuItem().addActionListener(this);
        mainFrame.getAdvancedMassDeltaFilterSettingsMenuItem().addActionListener(this);
        mainFrame.getMassDeltaFilterSettingsMenuItem().addActionListener(this);
        mainFrame.getMzRatioFilterSettingsMenuItem().addActionListener(this);
    }

    /**
     * This method is called when an user wants the load an experiment. The user
     * input is validated, filter configuration is updated and all result panels
     * are resetted.
     */
    public void onLoadExperiment() {
        List<String> validationMessages = validateUserInput();
        if (validationMessages.isEmpty()) {
            resultController.clear();

            //update bin constants
            experimentLoaderController.updateBinConstants();

            //update filters if winsorization checkbox is selected
            if (doWinsorization()) {
                filterConfigController.updateWinsorizationParameters();
            }
            filterConfigController.updateFilterChain();

            experimentLoaderController.loadExperiment();
        } else {
            validationMessages.add(0, "Validation errors found:");
            eventBus.post(new MessageEvent("validation errors", validationMessages, JOptionPane.WARNING_MESSAGE));
        }
    }

    /**
     * This method is called when the experiment has been loaded.
     *
     * @param experiment the loaded experiment
     */
    public void onExperimentLoaded(Experiment experiment) {
        //show experiment bins
        resultController.showExperimentInfo("initial number of spectra:" + experiment.getNumberOfSpectra() + ", number of spectra after filtering: " + experiment.getNumberOfFilteredSpectra());
        resultController.updateResultPanel(experiment);
    }

    /**
     * On canceled method, clears the resources.
     */
    public void onCanceled() {
        resultController.clear();
    }
    
    /**
     * Check if winsorization needs to be performed.
     *
     * @return 
     */
    public boolean doWinsorization() {
        return filterConfigController.isWinsorCheckBoxSelected();
    }

    /**
     * Listen to an MesaggeEvent.
     *
     * @param messageEvent the message event
     */
    @Subscribe
    public void onMessageEvent(MessageEvent messageEvent) {        
        showMessageDialog(messageEvent.getMessageTitle(), messageEvent.getMessage(), messageEvent.getMessageType());
        if(messageEvent instanceof UnexpectedErrorMessageEvent){
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (((JMenuItem) actionEvent.getSource()).getText().equalsIgnoreCase("Exit")) {
            mainFrame.dispose();
            System.exit(0);
        } else if (((JMenuItem) actionEvent.getSource()).getText().equalsIgnoreCase("M/Z ratio")) {
            filterConfigController.getMzRatioFilterDialog().setVisible(true);
        } else if (((JMenuItem) actionEvent.getSource()).getText().equalsIgnoreCase("Basic")) {
            filterConfigController.getMzDeltaFilterDialog().setVisible(true);
        } else if (((JMenuItem) actionEvent.getSource()).getText().equalsIgnoreCase("Advanced")) {
            filterConfigController.getAdvancedMzDeltaFilterDialog().setVisible(true);
        }
    }

    /**
     * Shows a message dialog.
     *
     * @param title the dialog title
     * @param message the dialog message
     * @param messageType the dialog message type
     */
    private void showMessageDialog(String title, String message, int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
            //add message to JTextArea
            JTextArea textArea = new JTextArea(message);
            //put JTextArea in JScrollPane
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 200));
            textArea.setEditable(false);

            JOptionPane.showMessageDialog(mainFrame.getContentPane(), scrollPane, title, messageType);
        } else {
            JOptionPane.showMessageDialog(mainFrame.getContentPane(), message, title, messageType);
        }
    }

    /**
     * Validate the user input in all child panels.
     *
     * @return the list of validation messages
     */
    private List<String> validateUserInput() {
        List<String> validationMessages = new ArrayList<>();
        validationMessages.addAll(experimentLoaderController.validateUserInput());
        validationMessages.addAll(filterConfigController.validateUserInput());
        return validationMessages;
    }
}