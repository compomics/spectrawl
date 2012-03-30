/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.view;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.controller.SpectrawlController;
import com.compomics.spectrawl.model.Experiment.ExperimentType;
import com.compomics.spectrawl.util.GuiUtils;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import org.apache.log4j.Logger;

/**
 *
 * @author niels
 */
public class ExperimentLoaderPanel extends javax.swing.JPanel {
    
    JFileChooser fileChooser;
    
    /**
     * Creates new form ExperimentLoaderPanel
     */
    public ExperimentLoaderPanel() {
        initComponents();
    }

    public JTextField getBinCeilingTextField() {
        return binCeilingTextField;
    }

    public JTextField getBinFloorTextField() {
        return binFloorTextField;
    }

    public JTextField getBinSizeTextField() {
        return binSizeTextField;
    }

    public JComboBox getExperimentTypeComboBox() {
        return experimentTypeComboBox;
    }

    public JButton getFileChooseButton() {
        return fileChooseButton;
    }

    public JButton getLoadExperimentButton() {
        return loadExperimentButton;
    }

    public JTextField getMgfFileTextField() {
        return mgfFileTextField;
    }

    public JTextField getMsLimsExperimentIdTextField() {
        return msLimsExperimentIdTextField;
    }

    public JProgressBar getSpectrawlProgressBar() {
        return spectrawlProgressBar;
    }

    public JLabel getSpectrawlProgressBarLabel() {
        return spectrawlProgressBarLabel;
    }
        
    public JPanel getExperimentSelectionPanel() {
        return experimentSelectionPanel;
    }

    public JPanel getMgfPanel() {
        return mgfPanel;
    }

    public JPanel getMsLimsPanel() {
        return msLimsPanel;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        experimentSelectionPanel = new javax.swing.JPanel();
        experimentTypeComboBox = new javax.swing.JComboBox();
        msLimsPanel = new javax.swing.JPanel();
        experimentIdLabel = new javax.swing.JLabel();
        msLimsExperimentIdTextField = new javax.swing.JTextField();
        mgfPanel = new javax.swing.JPanel();
        mgfFileLabel = new javax.swing.JLabel();
        mgfFileTextField = new javax.swing.JTextField();
        fileChooseButton = new javax.swing.JButton();
        binConfigurationPanel = new javax.swing.JPanel();
        binFloorLabel = new javax.swing.JLabel();
        binFloorTextField = new javax.swing.JTextField();
        binCeilingLabel = new javax.swing.JLabel();
        binCeilingTextField = new javax.swing.JTextField();
        binSizeLabel = new javax.swing.JLabel();
        binSizeTextField = new javax.swing.JTextField();
        experimentButtonPanel = new javax.swing.JPanel();
        loadExperimentButton = new javax.swing.JButton();
        progressBarPanel = new javax.swing.JPanel();
        spectrawlProgressBarLabel = new javax.swing.JLabel();
        spectrawlProgressBar = new javax.swing.JProgressBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setMinimumSize(new java.awt.Dimension(4, 4));
        setPreferredSize(new java.awt.Dimension(4, 4));
        setLayout(new java.awt.GridBagLayout());

        experimentSelectionPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        experimentSelectionPanel.add(experimentTypeComboBox);

        msLimsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        experimentIdLabel.setText("MSLims experiment ID");
        msLimsPanel.add(experimentIdLabel);

        msLimsExperimentIdTextField.setPreferredSize(new java.awt.Dimension(100, 25));
        msLimsPanel.add(msLimsExperimentIdTextField);

        experimentSelectionPanel.add(msLimsPanel);

        mgfPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        mgfFileLabel.setText("MGF file");
        mgfPanel.add(mgfFileLabel);

        mgfFileTextField.setEnabled(false);
        mgfFileTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        mgfPanel.add(mgfFileTextField);

        fileChooseButton.setText("choose file");
        mgfPanel.add(fileChooseButton);

        experimentSelectionPanel.add(mgfPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        add(experimentSelectionPanel, gridBagConstraints);

        java.awt.GridBagLayout binConfigurationPanelLayout = new java.awt.GridBagLayout();
        binConfigurationPanelLayout.columnWidths = new int[] {0, 5, 0};
        binConfigurationPanelLayout.rowHeights = new int[] {0, 10, 0, 10, 0};
        binConfigurationPanel.setLayout(binConfigurationPanelLayout);

        binFloorLabel.setText("bin floor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        binConfigurationPanel.add(binFloorLabel, gridBagConstraints);

        binFloorTextField.setMinimumSize(new java.awt.Dimension(6, 25));
        binFloorTextField.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        binConfigurationPanel.add(binFloorTextField, gridBagConstraints);

        binCeilingLabel.setText("bin ceiling");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        binConfigurationPanel.add(binCeilingLabel, gridBagConstraints);

        binCeilingTextField.setMinimumSize(new java.awt.Dimension(6, 25));
        binCeilingTextField.setPreferredSize(new java.awt.Dimension(6, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        binConfigurationPanel.add(binCeilingTextField, gridBagConstraints);

        binSizeLabel.setText("bin size");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        binConfigurationPanel.add(binSizeLabel, gridBagConstraints);

        binSizeTextField.setMinimumSize(new java.awt.Dimension(6, 25));
        binSizeTextField.setPreferredSize(new java.awt.Dimension(6, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        binConfigurationPanel.add(binSizeTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(binConfigurationPanel, gridBagConstraints);

        loadExperimentButton.setText("load");

        javax.swing.GroupLayout experimentButtonPanelLayout = new javax.swing.GroupLayout(experimentButtonPanel);
        experimentButtonPanel.setLayout(experimentButtonPanelLayout);
        experimentButtonPanelLayout.setHorizontalGroup(
            experimentButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, experimentButtonPanelLayout.createSequentialGroup()
                .addGap(0, 211, Short.MAX_VALUE)
                .addComponent(loadExperimentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        experimentButtonPanelLayout.setVerticalGroup(
            experimentButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, experimentButtonPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(loadExperimentButton))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE;
        add(experimentButtonPanel, gridBagConstraints);

        progressBarPanel.setLayout(new java.awt.GridLayout(2, 0));
        progressBarPanel.add(spectrawlProgressBarLabel);
        progressBarPanel.add(spectrawlProgressBar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        add(progressBarPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        add(filler2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel binCeilingLabel;
    private javax.swing.JTextField binCeilingTextField;
    private javax.swing.JPanel binConfigurationPanel;
    private javax.swing.JLabel binFloorLabel;
    private javax.swing.JTextField binFloorTextField;
    private javax.swing.JLabel binSizeLabel;
    private javax.swing.JTextField binSizeTextField;
    private javax.swing.JPanel experimentButtonPanel;
    private javax.swing.JLabel experimentIdLabel;
    private javax.swing.JPanel experimentSelectionPanel;
    private javax.swing.JComboBox experimentTypeComboBox;
    private javax.swing.JButton fileChooseButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton loadExperimentButton;
    private javax.swing.JLabel mgfFileLabel;
    private javax.swing.JTextField mgfFileTextField;
    private javax.swing.JPanel mgfPanel;
    private javax.swing.JTextField msLimsExperimentIdTextField;
    private javax.swing.JPanel msLimsPanel;
    private javax.swing.JPanel progressBarPanel;
    private javax.swing.JProgressBar spectrawlProgressBar;
    private javax.swing.JLabel spectrawlProgressBarLabel;
    // End of variables declaration//GEN-END:variables
}
