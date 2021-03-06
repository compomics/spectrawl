/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.view;

import java.awt.Color;
import java.awt.Frame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Niels Hulstaert
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form SpectrawlFrame
     */
    public MainFrame() {
        initComponents();
        
        this.getContentPane().setBackground(new Color(255, 255, 255));        
        setLocationRelativeTo(null);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public JPanel getExperimentBinsParentPanel() {
        return experimentBinsParentPanel;
    }

    public JPanel getExperimentLoaderParentPanel() {
        return experimentLoaderParentPanel;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public JMenuItem getAdvancedMassDeltaFilterSettingsMenuItem() {
        return advancedMassDeltaFilterSettingsMenuItem;
    }

    public JMenuItem getMassDeltaFilterSettingsMenuItem() {
        return massDeltaFilterSettingsMenuItem;
    }

    public JMenuItem getMzRatioFilterSettingsMenuItem() {
        return mzRatioFilterSettingsMenuItem;
    }           

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainSplitPane = new javax.swing.JSplitPane();
        experimentLoaderParentPanel = new javax.swing.JPanel();
        experimentBinsParentPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        filterMenu = new javax.swing.JMenu();
        massDeltaFilterMenu = new javax.swing.JMenu();
        massDeltaFilterSettingsMenuItem = new javax.swing.JMenuItem();
        advancedMassDeltaFilterSettingsMenuItem = new javax.swing.JMenuItem();
        mzRatioFilterSettingsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Spectrawl");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(10, 10));
        setPreferredSize(new java.awt.Dimension(900, 700));

        mainSplitPane.setDividerLocation(220);
        mainSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        experimentLoaderParentPanel.setBackground(new java.awt.Color(255, 255, 255));
        experimentLoaderParentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        experimentLoaderParentPanel.setLayout(new java.awt.GridBagLayout());
        mainSplitPane.setTopComponent(experimentLoaderParentPanel);

        experimentBinsParentPanel.setBackground(new java.awt.Color(255, 255, 255));
        experimentBinsParentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        experimentBinsParentPanel.setLayout(new java.awt.GridBagLayout());
        mainSplitPane.setBottomComponent(experimentBinsParentPanel);

        fileMenu.setText("File");

        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        filterMenu.setText("Filter");

        massDeltaFilterMenu.setText("Mass delta");

        massDeltaFilterSettingsMenuItem.setText("Basic");
        massDeltaFilterMenu.add(massDeltaFilterSettingsMenuItem);

        advancedMassDeltaFilterSettingsMenuItem.setText("Advanced");
        massDeltaFilterMenu.add(advancedMassDeltaFilterSettingsMenuItem);

        filterMenu.add(massDeltaFilterMenu);

        mzRatioFilterSettingsMenuItem.setText("M/Z ratio");
        filterMenu.add(mzRatioFilterSettingsMenuItem);

        menuBar.add(filterMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem advancedMassDeltaFilterSettingsMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPanel experimentBinsParentPanel;
    private javax.swing.JPanel experimentLoaderParentPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu filterMenu;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.JMenu massDeltaFilterMenu;
    private javax.swing.JMenuItem massDeltaFilterSettingsMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem mzRatioFilterSettingsMenuItem;
    // End of variables declaration//GEN-END:variables
}
