/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.gui.event.MessageEvent;
import com.compomics.spectrawl.gui.event.UnexpectedErrorMessageEvent;
import com.compomics.spectrawl.gui.view.MainFrame;
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
 * @author niels
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

    public MainFrame getMainFrame() {
        return mainFrame;
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
        mainFrame.getFilterSettingsMenuItem().addActionListener(this);
    }

    /**
     * This method is called when an experiment is loaded. The user input is
     * loaded and all result panels are resetted.
     */
    public void onLoadExperiment() {
        List<String> validationMessages = validateUserInput();
        if (validationMessages.isEmpty()) {
            resultController.clear();
            experimentLoaderController.loadExperiment();
        } else {
            validationMessages.add(0, "Validation errors found:");
            eventBus.post(new MessageEvent("Validation errors", validationMessages, JOptionPane.WARNING_MESSAGE));
        }
    }

    /**
     * On canceled method, clears the resources.
     */
    public void onCanceled() {
        resultController.clear();
    }

    /**
     * Listen to an MesaggeEvent.
     *
     * @param messageEvent the message event
     */
    @Subscribe
    public void onMessageEvent(MessageEvent messageEvent) {
        showMessageDialog(messageEvent.getMessageTitle(), messageEvent.getMessage(), messageEvent.getMessageType());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (((JMenuItem) actionEvent.getSource()).getText().equalsIgnoreCase("Filter Settings")) {
            filterConfigController.getFilterConfigDialog().setVisible(true);
        } else if (((JMenuItem) actionEvent.getSource()).getText().equalsIgnoreCase("Exit")) { // modification details
            mainFrame.dispose();
            System.exit(0);
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
        List<String> validationMessages = new ArrayList<String>();
        validationMessages.addAll(experimentLoaderController.validateUserInput());
        validationMessages.addAll(filterConfigController.validateUserInput());
        return validationMessages;
    }
}