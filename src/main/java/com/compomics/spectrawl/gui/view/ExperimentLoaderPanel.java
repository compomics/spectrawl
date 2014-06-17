/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.view;

import javax.swing.*;

/**
 *
 * @author Niels Hulstaert
 */
public class ExperimentLoaderPanel extends javax.swing.JPanel {
    
    JFileChooser fileChooser;
    
    /**
     * Creates new form ExperimentLoaderPanel
     */
    public ExperimentLoaderPanel() {
        initComponents();
        fileChooser = new JFileChooser();
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

    public JButton getFileChooseButton() {
        return fileChooseButton;
    }    

    public JList getMgfFilesList() {
        return mgfFilesList;
    }

    public JTextField getMsLimsExperimentIdTextField() {
        return msLimsExperimentIdTextField;
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

    public JButton getLoadMgfExperimentButton() {
        return loadMgfExperimentButton;
    }

    public JButton getLoadMsLimsExperimentButton() {
        return loadMsLimsExperimentButton;
    }

    public JTabbedPane getExperimentSelectionTabbedPane() {
        return experimentSelectionTabbedPane;
    }        
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        binConfigurationPanel = new javax.swing.JPanel();
        binFloorLabel = new javax.swing.JLabel();
        binCeilingLabel = new javax.swing.JLabel();
        binSizeLabel = new javax.swing.JLabel();
        binFloorTextField = new javax.swing.JTextField();
        binCeilingTextField = new javax.swing.JTextField();
        binSizeTextField = new javax.swing.JTextField();
        experimentSelectionTabbedPane = new javax.swing.JTabbedPane();
        mgfPanel = new javax.swing.JPanel();
        mgfFileLabel = new javax.swing.JLabel();
        fileChooseButton = new javax.swing.JButton();
        loadMgfExperimentButton = new javax.swing.JButton();
        mgfFilesListScrollPane = new javax.swing.JScrollPane();
        mgfFilesList = new javax.swing.JList();
        msLimsPanel = new javax.swing.JPanel();
        experimentIdLabel = new javax.swing.JLabel();
        msLimsExperimentIdTextField = new javax.swing.JTextField();
        loadMsLimsExperimentButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(5, 5));
        setPreferredSize(new java.awt.Dimension(200, 200));

        binConfigurationPanel.setBackground(new java.awt.Color(255, 255, 255));
        binConfigurationPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        binConfigurationPanel.setPreferredSize(new java.awt.Dimension(20, 20));
        binConfigurationPanel.setRequestFocusEnabled(false);

        binFloorLabel.setText("bin floor");

        binCeilingLabel.setText("bin ceiling");

        binSizeLabel.setText("bin size");

        binFloorTextField.setMinimumSize(new java.awt.Dimension(6, 25));
        binFloorTextField.setPreferredSize(new java.awt.Dimension(60, 25));

        binCeilingTextField.setMinimumSize(new java.awt.Dimension(6, 25));
        binCeilingTextField.setPreferredSize(new java.awt.Dimension(60, 25));

        binSizeTextField.setMinimumSize(new java.awt.Dimension(6, 25));
        binSizeTextField.setPreferredSize(new java.awt.Dimension(60, 25));

        javax.swing.GroupLayout binConfigurationPanelLayout = new javax.swing.GroupLayout(binConfigurationPanel);
        binConfigurationPanel.setLayout(binConfigurationPanelLayout);
        binConfigurationPanelLayout.setHorizontalGroup(
            binConfigurationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(binConfigurationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(binFloorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(binFloorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(binCeilingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(binCeilingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(binSizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(binSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        binConfigurationPanelLayout.setVerticalGroup(
            binConfigurationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, binConfigurationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(binConfigurationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(binFloorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(binFloorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(binCeilingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(binCeilingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(binSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(binSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mgfPanel.setBackground(new java.awt.Color(255, 255, 255));
        mgfPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        mgfFileLabel.setText("MGF file(s)");
        mgfFileLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        mgfFileLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        mgfFileLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        fileChooseButton.setText("choose file(s)");

        loadMgfExperimentButton.setText("load");
        loadMgfExperimentButton.setMaximumSize(new java.awt.Dimension(97, 23));
        loadMgfExperimentButton.setMinimumSize(new java.awt.Dimension(97, 23));
        loadMgfExperimentButton.setPreferredSize(new java.awt.Dimension(97, 23));

        mgfFilesListScrollPane.setPreferredSize(new java.awt.Dimension(300, 60));

        mgfFilesList.setEnabled(false);
        mgfFilesListScrollPane.setViewportView(mgfFilesList);

        javax.swing.GroupLayout mgfPanelLayout = new javax.swing.GroupLayout(mgfPanel);
        mgfPanel.setLayout(mgfPanelLayout);
        mgfPanelLayout.setHorizontalGroup(
            mgfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mgfPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mgfFileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mgfFilesListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(mgfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mgfPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(fileChooseButton))
                    .addGroup(mgfPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(loadMgfExperimentButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mgfPanelLayout.setVerticalGroup(
            mgfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mgfPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mgfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mgfPanelLayout.createSequentialGroup()
                        .addComponent(mgfFileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(mgfPanelLayout.createSequentialGroup()
                        .addGroup(mgfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mgfPanelLayout.createSequentialGroup()
                                .addComponent(fileChooseButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(loadMgfExperimentButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mgfFilesListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        experimentSelectionTabbedPane.addTab("MGF", mgfPanel);

        msLimsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        msLimsPanel.setOpaque(false);

        experimentIdLabel.setText("Ms-lims experiment ID");

        msLimsExperimentIdTextField.setPreferredSize(new java.awt.Dimension(100, 25));

        loadMsLimsExperimentButton.setText("load");
        loadMsLimsExperimentButton.setMaximumSize(new java.awt.Dimension(97, 23));
        loadMsLimsExperimentButton.setMinimumSize(new java.awt.Dimension(97, 23));
        loadMsLimsExperimentButton.setPreferredSize(new java.awt.Dimension(97, 23));

        javax.swing.GroupLayout msLimsPanelLayout = new javax.swing.GroupLayout(msLimsPanel);
        msLimsPanel.setLayout(msLimsPanelLayout);
        msLimsPanelLayout.setHorizontalGroup(
            msLimsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(msLimsPanelLayout.createSequentialGroup()
                .addContainerGap(192, Short.MAX_VALUE)
                .addGroup(msLimsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, msLimsPanelLayout.createSequentialGroup()
                        .addComponent(experimentIdLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(msLimsExperimentIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(loadMsLimsExperimentButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        msLimsPanelLayout.setVerticalGroup(
            msLimsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(msLimsPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(msLimsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msLimsExperimentIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(experimentIdLabel))
                .addGap(18, 18, 18)
                .addComponent(loadMsLimsExperimentButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        experimentSelectionTabbedPane.addTab("Ms-lims", msLimsPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(binConfigurationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 482, Short.MAX_VALUE)
                    .addComponent(experimentSelectionTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(binConfigurationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(experimentSelectionTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel binCeilingLabel;
    private javax.swing.JTextField binCeilingTextField;
    private javax.swing.JPanel binConfigurationPanel;
    private javax.swing.JLabel binFloorLabel;
    private javax.swing.JTextField binFloorTextField;
    private javax.swing.JLabel binSizeLabel;
    private javax.swing.JTextField binSizeTextField;
    private javax.swing.JLabel experimentIdLabel;
    private javax.swing.JTabbedPane experimentSelectionTabbedPane;
    private javax.swing.JButton fileChooseButton;
    private javax.swing.JButton loadMgfExperimentButton;
    private javax.swing.JButton loadMsLimsExperimentButton;
    private javax.swing.JLabel mgfFileLabel;
    private javax.swing.JList mgfFilesList;
    private javax.swing.JScrollPane mgfFilesListScrollPane;
    private javax.swing.JPanel mgfPanel;
    private javax.swing.JTextField msLimsExperimentIdTextField;
    private javax.swing.JPanel msLimsPanel;
    // End of variables declaration//GEN-END:variables
}
