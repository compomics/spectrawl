/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author niels
 */
public class AnalyzeFilterPanel extends javax.swing.JPanel {

    private SpectrawlGui spectrawlGui;
    private DefaultListModel peakFilterListModel;
    private DefaultListModel binFilterListModel;

    /**
     * Creates new form AnalyzeFilterPanel
     */
    public AnalyzeFilterPanel(SpectrawlGui spectrawlGui) {
        this.spectrawlGui = spectrawlGui;

        initPanel();
    }

    private void initPanel() {
        initComponents();

        //initialize list models
        peakFilterListModel = new DefaultListModel();
        peakFilterList.setModel(peakFilterListModel);
        binFilterListModel = new DefaultListModel();
        binFilterList.setModel(binFilterListModel);

        //add action listeners
        addPeakButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (addPeakTextField.getText() != "") {
                    peakFilterListModel.addElement(Double.parseDouble(addPeakTextField.getText()));
                    addPeakTextField.setText("");
                }
            }
        });

        removePeakButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (peakFilterList.getSelectedIndex() != -1) {
                    peakFilterListModel.removeElementAt(peakFilterList.getSelectedIndex());
                }
            }
        });

        addBinButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (addBinTextField.getText() != "") {
                    binFilterListModel.addElement(Double.parseDouble(addBinTextField.getText()));
                    addBinTextField.setText("");
                }
            }
        });

        removeBinButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (binFilterList.getSelectedIndex() != -1) {
                    binFilterListModel.removeElementAt(binFilterList.getSelectedIndex());
                }
            }
        });
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

        peakFilterPanel = new javax.swing.JPanel();
        peakFilterScrollPane = new javax.swing.JScrollPane();
        peakFilterList = new javax.swing.JList();
        addPeakLabel = new javax.swing.JLabel();
        addPeakTextField = new javax.swing.JTextField();
        addPeakButton = new javax.swing.JButton();
        removePeakButton = new javax.swing.JButton();
        binFilterPanel = new javax.swing.JPanel();
        binFilterScrollPane = new javax.swing.JScrollPane();
        binFilterList = new javax.swing.JList();
        addBinLabel = new javax.swing.JLabel();
        addBinTextField = new javax.swing.JTextField();
        addBinButton = new javax.swing.JButton();
        removeBinButton = new javax.swing.JButton();

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 5, 0};
        layout.rowHeights = new int[] {0};
        setLayout(layout);

        peakFilterPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("peak filter")); // NOI18N
        java.awt.GridBagLayout peakFilterPanelLayout = new java.awt.GridBagLayout();
        peakFilterPanelLayout.columnWidths = new int[] {0, 5, 0};
        peakFilterPanelLayout.rowHeights = new int[] {0, 10, 0, 10, 0};
        peakFilterPanel.setLayout(peakFilterPanelLayout);

        peakFilterScrollPane.setViewportView(peakFilterList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        peakFilterPanel.add(peakFilterScrollPane, gridBagConstraints);

        addPeakLabel.setText("filter value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        peakFilterPanel.add(addPeakLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        peakFilterPanel.add(addPeakTextField, gridBagConstraints);

        addPeakButton.setText("add");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        peakFilterPanel.add(addPeakButton, gridBagConstraints);

        removePeakButton.setText("remove");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        peakFilterPanel.add(removePeakButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        add(peakFilterPanel, gridBagConstraints);

        binFilterPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("bin filter")); // NOI18N
        java.awt.GridBagLayout binFilterPanelLayout = new java.awt.GridBagLayout();
        binFilterPanelLayout.columnWidths = new int[] {0, 5, 0};
        binFilterPanelLayout.rowHeights = new int[] {0, 10, 0, 10, 0};
        binFilterPanel.setLayout(binFilterPanelLayout);

        binFilterScrollPane.setViewportView(binFilterList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        binFilterPanel.add(binFilterScrollPane, gridBagConstraints);

        addBinLabel.setText("filter value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        binFilterPanel.add(addBinLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        binFilterPanel.add(addBinTextField, gridBagConstraints);

        addBinButton.setText("add");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        binFilterPanel.add(addBinButton, gridBagConstraints);

        removeBinButton.setText("remove");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        binFilterPanel.add(removeBinButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        add(binFilterPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBinButton;
    private javax.swing.JLabel addBinLabel;
    private javax.swing.JTextField addBinTextField;
    private javax.swing.JButton addPeakButton;
    private javax.swing.JLabel addPeakLabel;
    private javax.swing.JTextField addPeakTextField;
    private javax.swing.JList binFilterList;
    private javax.swing.JPanel binFilterPanel;
    private javax.swing.JScrollPane binFilterScrollPane;
    private javax.swing.JList peakFilterList;
    private javax.swing.JPanel peakFilterPanel;
    private javax.swing.JScrollPane peakFilterScrollPane;
    private javax.swing.JButton removeBinButton;
    private javax.swing.JButton removePeakButton;
    // End of variables declaration//GEN-END:variables
}
