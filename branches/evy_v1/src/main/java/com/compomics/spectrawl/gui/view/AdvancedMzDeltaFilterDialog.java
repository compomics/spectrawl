package com.compomics.spectrawl.gui.view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author niels
 */
public class AdvancedMzDeltaFilterDialog extends javax.swing.JDialog {

    /**
     * Creates new form FilterConfigDialog
     */
    public AdvancedMzDeltaFilterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);        
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(parent);
    }   

    public JButton getAddMzDeltaButton() {
        return addMzDeltaButton;
    }

    public JTextField getAddMzDeltaTextField() {
        return addMzDeltaTextField;
    }

    public JTextField getFixedCombIntThresholdTextField() {
        return fixedCombIntThresholdTextField;
    }

    public JTextField getMaxConsecMzDeltasTextField() {
        return maxConsecMzDeltasTextField;
    }

    public JTextField getMinConsecMzDeltasTextField() {
        return minConsecMzDeltasTextField;
    }

    public JList getMzDeltaFilterList() {
        return mzDeltaFilterList;
    }

    public JTextField getMzDeltaTextField() {
        return mzDeltaTextField;
    }

    public JButton getRemoveMzDeltaButton() {
        return removeMzDeltaButton;
    }

    public JTextField getVarCombIntThresholdTextField() {
        return varCombIntThresholdTextField;
    }       

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FilterConfigTabbedPane = new javax.swing.JTabbedPane();
        fixedCombMzDeltaFilterPanel = new javax.swing.JPanel();
        mzDeltaLabel = new javax.swing.JLabel();
        mzDeltaTextField = new javax.swing.JTextField();
        fixedCombIntThresholdLabel = new javax.swing.JLabel();
        fixedCombIntThresholdTextField = new javax.swing.JTextField();
        minConsecMzDeltasLabel = new javax.swing.JLabel();
        minConsecMzDeltasTextField = new javax.swing.JTextField();
        maxConsecMzDeltasLabel = new javax.swing.JLabel();
        maxConsecMzDeltasTextField = new javax.swing.JTextField();
        variableCombMzDeltaFilterPanel = new javax.swing.JPanel();
        varCombMzDeltaFilterScrollPane = new javax.swing.JScrollPane();
        mzDeltaFilterList = new javax.swing.JList();
        addMzDeltaLabel = new javax.swing.JLabel();
        addMzDeltaTextField = new javax.swing.JTextField();
        addMzDeltaButton = new javax.swing.JButton();
        removeMzDeltaButton = new javax.swing.JButton();
        varCombIntThresholdLabel = new javax.swing.JLabel();
        varCombIntThresholdTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        fixedCombMzDeltaFilterPanel.setMinimumSize(new java.awt.Dimension(4, 4));
        fixedCombMzDeltaFilterPanel.setOpaque(false);

        mzDeltaLabel.setText("M/Z delta");

        mzDeltaTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        fixedCombIntThresholdLabel.setText("Intensity threshold");

        fixedCombIntThresholdTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        minConsecMzDeltasLabel.setText("Min consecutive M/Z deltas");

        minConsecMzDeltasTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        maxConsecMzDeltasLabel.setText("Max consecutive M/Z deltas");

        maxConsecMzDeltasTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        javax.swing.GroupLayout fixedCombMzDeltaFilterPanelLayout = new javax.swing.GroupLayout(fixedCombMzDeltaFilterPanel);
        fixedCombMzDeltaFilterPanel.setLayout(fixedCombMzDeltaFilterPanelLayout);
        fixedCombMzDeltaFilterPanelLayout.setHorizontalGroup(
            fixedCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fixedCombMzDeltaFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fixedCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(fixedCombMzDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(fixedCombIntThresholdLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fixedCombIntThresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fixedCombMzDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(mzDeltaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mzDeltaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fixedCombMzDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(maxConsecMzDeltasLabel)
                        .addGap(18, 18, 18)
                        .addComponent(maxConsecMzDeltasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fixedCombMzDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(minConsecMzDeltasLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minConsecMzDeltasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(235, Short.MAX_VALUE))
        );
        fixedCombMzDeltaFilterPanelLayout.setVerticalGroup(
            fixedCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fixedCombMzDeltaFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fixedCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fixedCombIntThresholdLabel)
                    .addComponent(fixedCombIntThresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fixedCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mzDeltaLabel)
                    .addComponent(mzDeltaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fixedCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minConsecMzDeltasLabel)
                    .addComponent(minConsecMzDeltasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fixedCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxConsecMzDeltasLabel)
                    .addComponent(maxConsecMzDeltasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(157, Short.MAX_VALUE))
        );

        FilterConfigTabbedPane.addTab("Fixed comb M/Z delta filter settings", fixedCombMzDeltaFilterPanel);

        variableCombMzDeltaFilterPanel.setMinimumSize(new java.awt.Dimension(4, 4));
        variableCombMzDeltaFilterPanel.setOpaque(false);

        varCombMzDeltaFilterScrollPane.setViewportView(mzDeltaFilterList);

        addMzDeltaLabel.setText("M/Z delta");

        addMzDeltaTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        addMzDeltaButton.setText("add");
        addMzDeltaButton.setMaximumSize(new java.awt.Dimension(97, 23));
        addMzDeltaButton.setMinimumSize(new java.awt.Dimension(97, 23));
        addMzDeltaButton.setPreferredSize(new java.awt.Dimension(97, 23));

        removeMzDeltaButton.setText("remove");
        removeMzDeltaButton.setMaximumSize(new java.awt.Dimension(97, 23));
        removeMzDeltaButton.setMinimumSize(new java.awt.Dimension(97, 23));
        removeMzDeltaButton.setPreferredSize(new java.awt.Dimension(97, 23));

        varCombIntThresholdLabel.setText("Intensity threshold");

        varCombIntThresholdTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        javax.swing.GroupLayout variableCombMzDeltaFilterPanelLayout = new javax.swing.GroupLayout(variableCombMzDeltaFilterPanel);
        variableCombMzDeltaFilterPanel.setLayout(variableCombMzDeltaFilterPanelLayout);
        variableCombMzDeltaFilterPanelLayout.setHorizontalGroup(
            variableCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(variableCombMzDeltaFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(variableCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(variableCombMzDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(varCombIntThresholdLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(varCombIntThresholdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, variableCombMzDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(addMzDeltaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addMzDeltaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(varCombMzDeltaFilterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, variableCombMzDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(addMzDeltaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeMzDeltaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(285, Short.MAX_VALUE))
        );
        variableCombMzDeltaFilterPanelLayout.setVerticalGroup(
            variableCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(variableCombMzDeltaFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(variableCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(varCombIntThresholdLabel)
                    .addComponent(varCombIntThresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(varCombMzDeltaFilterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(variableCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMzDeltaLabel)
                    .addComponent(addMzDeltaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(variableCombMzDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeMzDeltaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addMzDeltaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        FilterConfigTabbedPane.addTab("Variable comb M/Z delta filter settings", variableCombMzDeltaFilterPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FilterConfigTabbedPane))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FilterConfigTabbedPane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane FilterConfigTabbedPane;
    private javax.swing.JButton addMzDeltaButton;
    private javax.swing.JLabel addMzDeltaLabel;
    private javax.swing.JTextField addMzDeltaTextField;
    private javax.swing.JLabel fixedCombIntThresholdLabel;
    private javax.swing.JTextField fixedCombIntThresholdTextField;
    private javax.swing.JPanel fixedCombMzDeltaFilterPanel;
    private javax.swing.JLabel maxConsecMzDeltasLabel;
    private javax.swing.JTextField maxConsecMzDeltasTextField;
    private javax.swing.JLabel minConsecMzDeltasLabel;
    private javax.swing.JTextField minConsecMzDeltasTextField;
    private javax.swing.JList mzDeltaFilterList;
    private javax.swing.JLabel mzDeltaLabel;
    private javax.swing.JTextField mzDeltaTextField;
    private javax.swing.JButton removeMzDeltaButton;
    private javax.swing.JLabel varCombIntThresholdLabel;
    private javax.swing.JTextField varCombIntThresholdTextField;
    private javax.swing.JScrollPane varCombMzDeltaFilterScrollPane;
    private javax.swing.JPanel variableCombMzDeltaFilterPanel;
    // End of variables declaration//GEN-END:variables
}
