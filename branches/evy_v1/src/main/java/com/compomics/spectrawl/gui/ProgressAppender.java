/*
 *

 */
package com.compomics.spectrawl.gui;

import com.compomics.spectrawl.gui.controller.ProgressController;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author Niels Hulstaert Hulstaert
 */
public class ProgressAppender extends WriterAppender {

    private static ProgressController progressController;
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void setProgressController(ProgressController progressController) {
        ProgressAppender.progressController = progressController;
    }

    @Override
    public void append(LoggingEvent event) {
        final String message = this.layout.format(event);

        //check if the controller is initialized
        if (progressController != null) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    progressController.setProgressInfoText(message);
                }
            });
        }

    }
}
