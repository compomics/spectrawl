package com.compomics.spectrawl.gui.event;

import javax.swing.JOptionPane;

/**
 *
 * @author Niels Hulstaert
 */
public class UnexpectedErrorMessageEvent extends MessageEvent {

    public UnexpectedErrorMessageEvent(String message) {
        super("Unexpected Error", "An expected error occured: "
                + "\n" + message
                + "\n" + "please try to rerun the application.", JOptionPane.ERROR_MESSAGE);
    }

    public UnexpectedErrorMessageEvent(String messageTitle, String message, int messageType) {
        super(messageTitle, message, messageType);
    }
        
}
