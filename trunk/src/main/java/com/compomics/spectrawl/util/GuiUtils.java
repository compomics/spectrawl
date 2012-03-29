/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.util;

import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author niels
 */
public class GuiUtils {

    public static boolean isComponentPresent(Component component, JPanel panel) {
        boolean isPresent = Boolean.FALSE;

        for (Component presentComponent : panel.getComponents()) {
            if (component.equals(presentComponent)) {
                isPresent = Boolean.TRUE;
            }
        }

        return isPresent;
    }
}
