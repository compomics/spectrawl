package com.compomics.spectrawl.gui.view;

import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Niels Hulstaert
 */
public class MzFilterDialog extends javax.swing.JDialog {

    /**
     * Creates new form FilterConfigDialog
     */
    public MzFilterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);        
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(parent);
    }

    public JButton getAddMzButton() {
        return addMzButton;
    }

    public JTextField getAddMzTextField() {
        return addMzTextField;
    }

    public JButton getAddPrecRelMzButton() {
        return addPrecRelMzButton;
    }

    public JTextField getAddPrecRelMzTextField() {
        return addPrecRelMzTextField;
    }

    public JRadioButton getAndRadioButton() {
        return andRadioButton;
    }

    public ButtonGroup getFilterTypeRadioButtonGroup() {
        return filterTypeRadioButtonGroup;
    }

    public JList getMzFilterList() {
        return mzFilterList;
    }

    public JTextField getMzToleranceTextField() {
        return mzToleranceTextField;
    }

    public JRadioButton getOrRadioButton() {
        return orRadioButton;
    }

    public JRadioButton getPrecRelAndRadioButton() {
        return precRelAndRadioButton;
    }

    public ButtonGroup getPrecRelFilterTypeRadioButtonGroup() {
        return precRelFilterTypeRadioButtonGroup;
    }

    public JList getPrecRelMzFilterList() {
        return precRelMzFilterList;
    }

    public JTextField getPrecRelMzToleranceTextField() {
        return precRelMzToleranceTextField;
    }

    public JRadioButton getPrecRelOrRadioButton() {
        return precRelOrRadioButton;
    }

    public JButton getRemoveMzButton() {
        return removeMzButton;
    }

    public JButton getRemovePrecRelMzButton() {
        return removePrecRelMzButton;
    }

    public JTextField getWinsorConstantTextField() {
        return winsorConstantTextField;
    }

    public JTextField getWinsorConvergenceCriterionTextField() {
        return winsorConvergenceCriterionTextField;
    }

    public JCheckBox getWinsorFilterCheckBox() {
        return winsorFilterCheckBox;
    }

    public JTextField getWinsorOutlierLimitTextField() {
        return winsorOutlierLimitTextField;
    }          

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filterTypeRadioButtonGroup = new javax.swing.ButtonGroup();
        precRelFilterTypeRadioButtonGroup = new javax.swing.ButtonGroup();
        FilteSettingsTabbedPane = new javax.swing.JTabbedPane();
        noiseFilterPanel = new javax.swing.JPanel();
        winsorFilterCheckBox = new javax.swing.JCheckBox();
        winsorConstantLabel = new javax.swing.JLabel();
        winsorConstantTextField = new javax.swing.JTextField();
        winsorConvergenceCriterionLabel = new javax.swing.JLabel();
        winsorConvergenceCriterionTextField = new javax.swing.JTextField();
        winsorOutlierLimitLabel = new javax.swing.JLabel();
        winsorOutlierLimitTextField = new javax.swing.JTextField();
        mzFilterPanel = new javax.swing.JPanel();
        mzFilterScrollPane = new javax.swing.JScrollPane();
        mzFilterList = new javax.swing.JList();
        addMzLabel = new javax.swing.JLabel();
        addMzTextField = new javax.swing.JTextField();
        addMzButton = new javax.swing.JButton();
        removeMzButton = new javax.swing.JButton();
        mzToleranceLabel = new javax.swing.JLabel();
        mzToleranceTextField = new javax.swing.JTextField();
        filterTypeLabel = new javax.swing.JLabel();
        andRadioButton = new javax.swing.JRadioButton();
        orRadioButton = new javax.swing.JRadioButton();
        precRelMzFilterPanel = new javax.swing.JPanel();
        precRelMzFilterScrollPane = new javax.swing.JScrollPane();
        precRelMzFilterList = new javax.swing.JList();
        addPrecRelMzLabel = new javax.swing.JLabel();
        addPrecRelMzTextField = new javax.swing.JTextField();
        addPrecRelMzButton = new javax.swing.JButton();
        removePrecRelMzButton = new javax.swing.JButton();
        precRelMzToleranceLabel = new javax.swing.JLabel();
        precRelMzToleranceTextField = new javax.swing.JTextField();
        precRelFilterTypeLabel = new javax.swing.JLabel();
        precRelAndRadioButton = new javax.swing.JRadioButton();
        precRelOrRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(650, 350));

        noiseFilterPanel.setOpaque(false);

        winsorFilterCheckBox.setText("do winsorisation filtering");

        winsorConstantLabel.setText("winsorisation constant");

        winsorConstantTextField.setEnabled(false);
        winsorConstantTextField.setPreferredSize(new java.awt.Dimension(60, 25));

        winsorConvergenceCriterionLabel.setText("winsorisation convergence criterion");

        winsorConvergenceCriterionTextField.setEnabled(false);
        winsorConvergenceCriterionTextField.setMinimumSize(new java.awt.Dimension(6, 25));
        winsorConvergenceCriterionTextField.setPreferredSize(new java.awt.Dimension(60, 25));

        winsorOutlierLimitLabel.setText("winsorisation outlier limit");

        winsorOutlierLimitTextField.setEnabled(false);
        winsorOutlierLimitTextField.setMinimumSize(new java.awt.Dimension(6, 25));
        winsorOutlierLimitTextField.setPreferredSize(new java.awt.Dimension(60, 25));

        javax.swing.GroupLayout noiseFilterPanelLayout = new javax.swing.GroupLayout(noiseFilterPanel);
        noiseFilterPanel.setLayout(noiseFilterPanelLayout);
        noiseFilterPanelLayout.setHorizontalGroup(
            noiseFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, noiseFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(noiseFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(winsorFilterCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(noiseFilterPanelLayout.createSequentialGroup()
                        .addGroup(noiseFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(winsorConvergenceCriterionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                            .addComponent(winsorConstantLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(winsorOutlierLimitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(noiseFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(winsorConvergenceCriterionTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(winsorConstantTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(winsorOutlierLimitTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        noiseFilterPanelLayout.setVerticalGroup(
            noiseFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(noiseFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(winsorFilterCheckBox)
                .addGap(18, 18, 18)
                .addGroup(noiseFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winsorConstantLabel)
                    .addComponent(winsorConstantTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(noiseFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winsorConvergenceCriterionLabel)
                    .addComponent(winsorConvergenceCriterionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(noiseFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winsorOutlierLimitLabel)
                    .addComponent(winsorOutlierLimitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(151, Short.MAX_VALUE))
        );

        FilteSettingsTabbedPane.addTab("Noise filter settings", noiseFilterPanel);

        mzFilterPanel.setOpaque(false);

        mzFilterScrollPane.setViewportView(mzFilterList);

        addMzLabel.setText("M/Z");

        addMzTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        addMzButton.setText("add");
        addMzButton.setMaximumSize(new java.awt.Dimension(97, 23));
        addMzButton.setMinimumSize(new java.awt.Dimension(97, 23));
        addMzButton.setPreferredSize(new java.awt.Dimension(97, 23));

        removeMzButton.setText("remove");
        removeMzButton.setMaximumSize(new java.awt.Dimension(97, 23));
        removeMzButton.setMinimumSize(new java.awt.Dimension(97, 23));
        removeMzButton.setPreferredSize(new java.awt.Dimension(97, 23));

        mzToleranceLabel.setText("M/Z tolerance");

        mzToleranceTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        filterTypeLabel.setText("Filter type");

        filterTypeRadioButtonGroup.add(andRadioButton);
        andRadioButton.setText("and");

        filterTypeRadioButtonGroup.add(orRadioButton);
        orRadioButton.setText("or");

        javax.swing.GroupLayout mzFilterPanelLayout = new javax.swing.GroupLayout(mzFilterPanel);
        mzFilterPanel.setLayout(mzFilterPanelLayout);
        mzFilterPanelLayout.setHorizontalGroup(
            mzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mzFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mzFilterScrollPane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mzFilterPanelLayout.createSequentialGroup()
                        .addComponent(mzToleranceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mzToleranceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mzFilterPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(mzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mzFilterPanelLayout.createSequentialGroup()
                                .addComponent(filterTypeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(andRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(orRadioButton))
                            .addGroup(mzFilterPanelLayout.createSequentialGroup()
                                .addComponent(addMzButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeMzButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(mzFilterPanelLayout.createSequentialGroup()
                        .addComponent(addMzLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addMzTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mzFilterPanelLayout.setVerticalGroup(
            mzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mzFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mzToleranceLabel)
                    .addComponent(mzToleranceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mzFilterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMzLabel)
                    .addComponent(addMzTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMzButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeMzButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterTypeLabel)
                    .addComponent(andRadioButton)
                    .addComponent(orRadioButton))
                .addContainerGap())
        );

        FilteSettingsTabbedPane.addTab("M/Z ratio filter settings", mzFilterPanel);

        precRelMzFilterPanel.setOpaque(false);

        precRelMzFilterScrollPane.setViewportView(precRelMzFilterList);

        addPrecRelMzLabel.setText("Mass");

        addPrecRelMzTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        addPrecRelMzButton.setText("add");
        addPrecRelMzButton.setMaximumSize(new java.awt.Dimension(97, 23));
        addPrecRelMzButton.setMinimumSize(new java.awt.Dimension(97, 23));
        addPrecRelMzButton.setPreferredSize(new java.awt.Dimension(97, 23));

        removePrecRelMzButton.setText("remove");
        removePrecRelMzButton.setMaximumSize(new java.awt.Dimension(97, 23));
        removePrecRelMzButton.setMinimumSize(new java.awt.Dimension(97, 23));
        removePrecRelMzButton.setPreferredSize(new java.awt.Dimension(97, 23));

        precRelMzToleranceLabel.setText("M/Z tolerance");

        precRelMzToleranceTextField.setPreferredSize(new java.awt.Dimension(6, 25));

        precRelFilterTypeLabel.setText("Filter type");

        filterTypeRadioButtonGroup.add(precRelAndRadioButton);
        precRelAndRadioButton.setText("and");

        filterTypeRadioButtonGroup.add(precRelOrRadioButton);
        precRelOrRadioButton.setText("or");

        javax.swing.GroupLayout precRelMzFilterPanelLayout = new javax.swing.GroupLayout(precRelMzFilterPanel);
        precRelMzFilterPanel.setLayout(precRelMzFilterPanelLayout);
        precRelMzFilterPanelLayout.setHorizontalGroup(
            precRelMzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(precRelMzFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(precRelMzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(precRelMzFilterScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, precRelMzFilterPanelLayout.createSequentialGroup()
                        .addComponent(precRelMzToleranceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precRelMzToleranceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, precRelMzFilterPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(precRelMzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, precRelMzFilterPanelLayout.createSequentialGroup()
                                .addComponent(addPrecRelMzButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removePrecRelMzButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, precRelMzFilterPanelLayout.createSequentialGroup()
                                .addComponent(precRelFilterTypeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(precRelAndRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(precRelOrRadioButton))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, precRelMzFilterPanelLayout.createSequentialGroup()
                        .addComponent(addPrecRelMzLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPrecRelMzTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        precRelMzFilterPanelLayout.setVerticalGroup(
            precRelMzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(precRelMzFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(precRelMzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precRelMzToleranceLabel)
                    .addComponent(precRelMzToleranceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(precRelMzFilterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(precRelMzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPrecRelMzLabel)
                    .addComponent(addPrecRelMzTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(precRelMzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPrecRelMzButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removePrecRelMzButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(precRelMzFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precRelAndRadioButton)
                    .addComponent(precRelOrRadioButton)
                    .addComponent(precRelFilterTypeLabel))
                .addContainerGap())
        );

        FilteSettingsTabbedPane.addTab("Precursor relative mass filter settings", precRelMzFilterPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 659, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FilteSettingsTabbedPane))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FilteSettingsTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane FilteSettingsTabbedPane;
    private javax.swing.JButton addMzButton;
    private javax.swing.JLabel addMzLabel;
    private javax.swing.JTextField addMzTextField;
    private javax.swing.JButton addPrecRelMzButton;
    private javax.swing.JLabel addPrecRelMzLabel;
    private javax.swing.JTextField addPrecRelMzTextField;
    private javax.swing.JRadioButton andRadioButton;
    private javax.swing.JLabel filterTypeLabel;
    private javax.swing.ButtonGroup filterTypeRadioButtonGroup;
    private javax.swing.JList mzFilterList;
    private javax.swing.JPanel mzFilterPanel;
    private javax.swing.JScrollPane mzFilterScrollPane;
    private javax.swing.JLabel mzToleranceLabel;
    private javax.swing.JTextField mzToleranceTextField;
    private javax.swing.JPanel noiseFilterPanel;
    private javax.swing.JRadioButton orRadioButton;
    private javax.swing.JRadioButton precRelAndRadioButton;
    private javax.swing.JLabel precRelFilterTypeLabel;
    private javax.swing.ButtonGroup precRelFilterTypeRadioButtonGroup;
    private javax.swing.JList precRelMzFilterList;
    private javax.swing.JPanel precRelMzFilterPanel;
    private javax.swing.JScrollPane precRelMzFilterScrollPane;
    private javax.swing.JLabel precRelMzToleranceLabel;
    private javax.swing.JTextField precRelMzToleranceTextField;
    private javax.swing.JRadioButton precRelOrRadioButton;
    private javax.swing.JButton removeMzButton;
    private javax.swing.JButton removePrecRelMzButton;
    private javax.swing.JLabel winsorConstantLabel;
    private javax.swing.JTextField winsorConstantTextField;
    private javax.swing.JLabel winsorConvergenceCriterionLabel;
    private javax.swing.JTextField winsorConvergenceCriterionTextField;
    private javax.swing.JCheckBox winsorFilterCheckBox;
    private javax.swing.JLabel winsorOutlierLimitLabel;
    private javax.swing.JTextField winsorOutlierLimitTextField;
    // End of variables declaration//GEN-END:variables
}
