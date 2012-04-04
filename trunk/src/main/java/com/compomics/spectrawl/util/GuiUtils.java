/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.util;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JPanel;

/**
 *
 * @author niels
 */
public class GuiUtils {

    public static boolean isComponentPresent(Container parentContainer, Component childComponent) {
        boolean isPresent = Boolean.FALSE;

        for (Component presentComponent : parentContainer.getComponents()) {
            if (childComponent.equals(presentComponent)) {
                isPresent = Boolean.TRUE;
            }
        }

        return isPresent;
    }
    
    public static void switchChildPanels(JPanel parentPanel, JPanel panelToAdd, JPanel panelToRemove) {
        if (!GuiUtils.isComponentPresent(parentPanel, panelToAdd)) {
            parentPanel.add(panelToAdd);
        }
        if (GuiUtils.isComponentPresent(parentPanel, panelToRemove)) {
            parentPanel.remove(panelToRemove);
        }
        parentPanel.revalidate();
        parentPanel.repaint();
    }
}
