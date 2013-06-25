package com.compomics.spectrawl.gui.view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author niels
 */
public class AdvancedMassDeltaFilterDialog extends javax.swing.JDialog {

    /**
     * Creates new form FilterConfigDialog
     */
    public AdvancedMassDeltaFilterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);        
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(parent);
    }   

    public JButton getAddMassDeltaButton() {
        return addMassDeltaButton;
    }

    public JTextField getAddMassDeltaTextField() {
        return addMassDeltaTextField;
    }

    public JTextField getFixedCombIntThresholdTextField() {
        return fixedCombIntThresholdTextField;
    }

    public JTextField getMaxConsecMassDeltasTextField() {
        return maxConsecMassDeltasTextField;
    }

    public JTextField getMinConsecMassDeltasTextField() {
        return minConsecMassDeltasTextField;
    }

    public JList getMassDeltaFilterList() {
        return massDeltaFilterList;
    }

    public JTextField getMassDeltaTextField() {
        return massDeltaTextField;
    }

    public JButton getRemoveMassDeltaButton() {
        return removeMassDeltaButton;
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
        fixedCombMassDeltaFilterPanel = new javax.swing.JPanel();
        massDeltaLabel = new javax.swing.JLabel();
        massDeltaTextField = new javax.swing.JTextField();
        fixedCombIntThresholdLabel = new javax.swing.JLabel();
        fixedCombIntThresholdTextField = new javax.swing.JTextField();
        minConsecMassDeltasLabel = new javax.swing.JLabel();
        minConsecMassDeltasTextField = new javax.swing.JTextField();
        maxConsecMassDeltasLabel = new javax.swing.JLabel();
        maxConsecMassDeltasTextField = new javax.swing.JTextField();
        variableCombMassDeltaFilterPanel = new javax.swing.JPanel();
        varCombMassDeltaFilterScrollPane = new javax.swing.JScrollPane();
        massDeltaFilterList = new javax.swing.JList();
        addMassDeltaLabel = new javax.swing.JLabel();
        addMassDeltaTextField = new javax.swing.JTextField();
        addMassDeltaButton = new javax.swing.JButton();
        removeMassDeltaButton = new javax.swing.JButton();
        varCombIntThresholdLabel = new javax.swing.JLabel();
        varCombIntThresholdTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        fixedCombMassDeltaFilterPanel.setMinimumSize(new java.awt.Dimension(4, 4));
        fixedCombMassDeltaFilterPanel.setOpaque(false);

        massDeltaLabel.setText("Mass delta");

        massDeltaTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        fixedCombIntThresholdLabel.setText("Intensity threshold");

        fixedCombIntThresholdTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        minConsecMassDeltasLabel.setText("Min consecutive mass deltas");

        minConsecMassDeltasTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        maxConsecMassDeltasLabel.setText("Max consecutive mass deltas");

        maxConsecMassDeltasTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        javax.swing.GroupLayout fixedCombMassDeltaFilterPanelLayout = new javax.swing.GroupLayout(fixedCombMassDeltaFilterPanel);
        fixedCombMassDeltaFilterPanel.setLayout(fixedCombMassDeltaFilterPanelLayout);
        fixedCombMassDeltaFilterPanelLayout.setHorizontalGroup(
            fixedCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fixedCombMassDeltaFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fixedCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(fixedCombMassDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(fixedCombIntThresholdLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fixedCombIntThresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fixedCombMassDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(massDeltaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(massDeltaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fixedCombMassDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(maxConsecMassDeltasLabel)
                        .addGap(18, 18, 18)
                        .addComponent(maxConsecMassDeltasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fixedCombMassDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(minConsecMassDeltasLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minConsecMassDeltasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(229, Short.MAX_VALUE))
        );
        fixedCombMassDeltaFilterPanelLayout.setVerticalGroup(
            fixedCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fixedCombMassDeltaFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fixedCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fixedCombIntThresholdLabel)
                    .addComponent(fixedCombIntThresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fixedCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(massDeltaLabel)
                    .addComponent(massDeltaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fixedCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minConsecMassDeltasLabel)
                    .addComponent(minConsecMassDeltasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fixedCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxConsecMassDeltasLabel)
                    .addComponent(maxConsecMassDeltasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(157, Short.MAX_VALUE))
        );

        FilterConfigTabbedPane.addTab("Fixed comb mass delta filter settings", fixedCombMassDeltaFilterPanel);

        variableCombMassDeltaFilterPanel.setMinimumSize(new java.awt.Dimension(4, 4));
        variableCombMassDeltaFilterPanel.setOpaque(false);

        varCombMassDeltaFilterScrollPane.setViewportView(massDeltaFilterList);

        addMassDeltaLabel.setText("Mass delta");

        addMassDeltaTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        addMassDeltaButton.setText("add");
        addMassDeltaButton.setMaximumSize(new java.awt.Dimension(97, 23));
        addMassDeltaButton.setMinimumSize(new java.awt.Dimension(97, 23));
        addMassDeltaButton.setPreferredSize(new java.awt.Dimension(97, 23));

        removeMassDeltaButton.setText("remove");
        removeMassDeltaButton.setMaximumSize(new java.awt.Dimension(97, 23));
        removeMassDeltaButton.setMinimumSize(new java.awt.Dimension(97, 23));
        removeMassDeltaButton.setPreferredSize(new java.awt.Dimension(97, 23));

        varCombIntThresholdLabel.setText("Intensity threshold");

        varCombIntThresholdTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        javax.swing.GroupLayout variableCombMassDeltaFilterPanelLayout = new javax.swing.GroupLayout(variableCombMassDeltaFilterPanel);
        variableCombMassDeltaFilterPanel.setLayout(variableCombMassDeltaFilterPanelLayout);
        variableCombMassDeltaFilterPanelLayout.setHorizontalGroup(
            variableCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(variableCombMassDeltaFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(variableCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(variableCombMassDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(varCombIntThresholdLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(varCombIntThresholdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, variableCombMassDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(addMassDeltaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addMassDeltaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(varCombMassDeltaFilterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, variableCombMassDeltaFilterPanelLayout.createSequentialGroup()
                        .addComponent(addMassDeltaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeMassDeltaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(285, Short.MAX_VALUE))
        );
        variableCombMassDeltaFilterPanelLayout.setVerticalGroup(
            variableCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(variableCombMassDeltaFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(variableCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(varCombIntThresholdLabel)
                    .addComponent(varCombIntThresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(varCombMassDeltaFilterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(variableCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMassDeltaLabel)
                    .addComponent(addMassDeltaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(variableCombMassDeltaFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeMassDeltaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addMassDeltaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        FilterConfigTabbedPane.addTab("Variable comb mass delta filter settings", variableCombMassDeltaFilterPanel);

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
    private javax.swing.JButton addMassDeltaButton;
    private javax.swing.JLabel addMassDeltaLabel;
    private javax.swing.JTextField addMassDeltaTextField;
    private javax.swing.JLabel fixedCombIntThresholdLabel;
    private javax.swing.JTextField fixedCombIntThresholdTextField;
    private javax.swing.JPanel fixedCombMassDeltaFilterPanel;
    private javax.swing.JList massDeltaFilterList;
    private javax.swing.JLabel massDeltaLabel;
    private javax.swing.JTextField massDeltaTextField;
    private javax.swing.JLabel maxConsecMassDeltasLabel;
    private javax.swing.JTextField maxConsecMassDeltasTextField;
    private javax.swing.JLabel minConsecMassDeltasLabel;
    private javax.swing.JTextField minConsecMassDeltasTextField;
    private javax.swing.JButton removeMassDeltaButton;
    private javax.swing.JLabel varCombIntThresholdLabel;
    private javax.swing.JTextField varCombIntThresholdTextField;
    private javax.swing.JScrollPane varCombMassDeltaFilterScrollPane;
    private javax.swing.JPanel variableCombMassDeltaFilterPanel;
    // End of variables declaration//GEN-END:variables
}
