/*
Copyright 2024 omennia
Licensed under no license lol
*/

package org.gephi.ui.filters.plugin.graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import org.gephi.filters.plugin.graph.DirectedEgoBuilder.DirectedEgoFilter;

/**
 * Panel for the Directed Ego Network filter configuration.
 * 
 * @author omennia
 */
public class DirectedEgoPanel extends javax.swing.JPanel {

    private DirectedEgoFilter directedEgoFilter;
    // Variables declaration
    private javax.swing.JComboBox depthComboBox;
    private javax.swing.JLabel labelDepth;
    private javax.swing.JLabel labelNodeId;
    private javax.swing.JTextField nodeIdTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JCheckBox withSelfCheckbox;
    private javax.swing.JCheckBox downStreamBox;

    public DirectedEgoPanel() {
        initComponents();

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                directedEgoFilter.getProperties()[0].setValue(nodeIdTextField.getText());
            }
        });

        depthComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int depth = -1;
                int index = depthComboBox.getSelectedIndex();
                if (index == depthComboBox.getModel().getSize() - 1) {
                    depth = Integer.MAX_VALUE;
                } else {
                    depth = index + 1;
                }
                if (!directedEgoFilter.getDepth().equals(depth)) {
                    directedEgoFilter.getProperties()[1].setValue(depth);
                }
            }
        });

        withSelfCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!directedEgoFilter.isSelf() == withSelfCheckbox.isSelected()) {
                    directedEgoFilter.getProperties()[2].setValue(withSelfCheckbox.isSelected());
                }
            }
        });


        downStreamBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!directedEgoFilter.isOnlyDownstream() == downStreamBox.isSelected()) {
                    directedEgoFilter.getProperties()[3].setValue(downStreamBox.isSelected());
                }
            }
        });
    }

    public void setup(DirectedEgoFilter directedEgoFilter) {
        this.directedEgoFilter = directedEgoFilter;
        nodeIdTextField.setText(directedEgoFilter.getPattern());

        int depth = directedEgoFilter.getDepth();
        if (depth == Integer.MAX_VALUE) {
            depthComboBox.setSelectedIndex(depthComboBox.getModel().getSize() - 1);
        } else {
            depthComboBox.setSelectedIndex(depth - 1);
        }

        withSelfCheckbox.setSelected(directedEgoFilter.isSelf());
        downStreamBox.setSelected(directedEgoFilter.isOnlyDownstream());
    }

    private void initComponents() {
        labelNodeId = new javax.swing.JLabel();
        nodeIdTextField = new javax.swing.JTextField();
        labelDepth = new javax.swing.JLabel();
        depthComboBox = new javax.swing.JComboBox();
        okButton = new javax.swing.JButton();
        withSelfCheckbox = new javax.swing.JCheckBox();
        downStreamBox = new javax.swing.JCheckBox();

        labelNodeId.setText(org.openide.util.NbBundle.getMessage(DirectedEgoPanel.class, "DirectedEgoPanel.labelNodeId.text"));

        nodeIdTextField.setText(org.openide.util.NbBundle.getMessage(DirectedEgoPanel.class, "DirectedEgoPanel.nodeIdTextField.text"));
        nodeIdTextField.setToolTipText(org.openide.util.NbBundle.getMessage(DirectedEgoPanel.class, "DirectedEgoPanel.nodeIdTextField.toolTipText"));

        labelDepth.setText(org.openide.util.NbBundle.getMessage(DirectedEgoPanel.class, "DirectedEgoPanel.labelDepth.text"));

        depthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"1", "2", "3", "Max"}));

        okButton.setText(org.openide.util.NbBundle.getMessage(DirectedEgoPanel.class, "DirectedEgoPanel.okButton.text"));
        okButton.setMargin(new java.awt.Insets(2, 7, 2, 7));

        withSelfCheckbox.setText(org.openide.util.NbBundle.getMessage(DirectedEgoPanel.class, "DirectedEgoPanel.withSelfCheckbox.text"));
        // TODO: Use the NbBundle for this later
        downStreamBox.setText("Only downstream nodes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelNodeId)
                        .addComponent(labelDepth))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(withSelfCheckbox)
                        .addComponent(downStreamBox)
                        .addComponent(depthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(nodeIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(okButton)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelNodeId)
                        .addComponent(nodeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(okButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(depthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelDepth))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(withSelfCheckbox)
                    .addComponent(downStreamBox)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
