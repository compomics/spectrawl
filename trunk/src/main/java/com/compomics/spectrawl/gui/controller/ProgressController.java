package com.compomics.spectrawl.gui.controller;

import com.compomics.spectrawl.gui.ProgressAppender;
import com.compomics.spectrawl.util.GuiUtils;
import com.compomics.util.gui.waiting.waitinghandlers.ProgressDialogX;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Niels Hulstaert Hulstaert
 * @author Harald Barsnes
 */
@Component("progressController")
public class ProgressController extends WindowAdapter {

    //model
    private int progress;
    private boolean progressFinished;
    //view
    private ProgressDialogX progressDialog;
    //parent controller
    @Autowired
    private ExperimentLoaderController experimentLoaderController;        
      
    public void init() {
        progressFinished = false;
        //set this controller in PipelineProgressAppender
        ProgressAppender.setProgressController(this);
    }

    public void showProgressBar(int numberOfProgressSteps, String progressHeaderText) {
        progressDialog = new ProgressDialogX(experimentLoaderController.getMainController().getMainFrame(),
                Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/spectrawl.png")),
                Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/spectrawl-orange.png")),
                true);
        progressDialog.addWindowListener(this);

        progressDialog.getProgressBar().setMaximum(numberOfProgressSteps + 1);
        progressDialog.setTitle(progressHeaderText + " Please Wait...");
        progress = 1;
        GuiUtils.centerDialogOnFrame(experimentLoaderController.getMainController().getMainFrame(), progressDialog);
        progressFinished = false;

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    progressDialog.setVisible(true);
                } catch (IndexOutOfBoundsException e) {
                    // ignore
                }
            }
        }, "ProgressDialog").start();
    }
    
    public boolean isRunCancelled () {
        return progressDialog.isRunCanceled();
    }

    public void hideProgressDialog() {
        progressFinished = true;
        progressDialog.setRunFinished();
        progressDialog.setVisible(false);
    }

    public void setProgressInfoText(String progressInfoText) {     
        progressDialog.setString(progressInfoText);

        progressDialog.getProgressBar().setValue(progress);
        progress++;

        //repaint view
        progressDialog.validate();
        progressDialog.repaint();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        super.windowClosed(e);
        if (!progressFinished) {
            experimentLoaderController.onCanceled();
        } 
    }

    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        e.getWindow().dispose();
        if (!progressFinished) {
            experimentLoaderController.onCanceled();
        }
    }
}
