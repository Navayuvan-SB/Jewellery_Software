/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyphersource.jewellery_software;

/**
 *
 * @author ghost
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        sellPage = new javax.swing.JPanel();
        sell_shopeName_label = new java.awt.Label();
        sell_entryNavigation_label = new java.awt.Label();
        sell_viewNavigation_label = new java.awt.Label();
        sell_sellNavigation_label = new java.awt.Label();
        sell_Welcome_label = new java.awt.Label();
        sell_Barcode_label = new java.awt.Label();
        sell_innerWindow_panel = new javax.swing.JPanel();
        sell_qty_label = new javax.swing.JLabel();
        sell_chaseNo_label = new javax.swing.JLabel();
        sell_ornamentName_label = new javax.swing.JLabel();
        sell_wt_label = new javax.swing.JLabel();
        sell_was_label = new javax.swing.JLabel();
        sell_mc_label = new javax.swing.JLabel();
        sell_verify_checkbox = new javax.swing.JCheckBox();
        sell_semicolon1_label = new javax.swing.JLabel();
        sell_semicolon2_label = new javax.swing.JLabel();
        sell_semicolon3_label = new javax.swing.JLabel();
        sell_semicolon4_label = new javax.swing.JLabel();
        sell_semicolon5_label = new javax.swing.JLabel();
        sell_semicolon6_label = new javax.swing.JLabel();
        sell_chaseNoDetail_label = new javax.swing.JLabel();
        sell_ornamentNameDetail_label = new javax.swing.JLabel();
        sell_wtDetail_label = new javax.swing.JLabel();
        sell_wasDetail_label = new javax.swing.JLabel();
        sell_mcDetail_label = new javax.swing.JLabel();
        sell_qtyInput_textfield = new javax.swing.JTextField();
        sell_return_button = new javax.swing.JButton();
        sell_confirm_button = new javax.swing.JButton();
        sell_refresh_label = new javax.swing.JLabel();
        sell_shopeName_label1 = new java.awt.Label();
        sell_shopeName_label2 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        sellPage.setBackground(java.awt.Color.white);
        sellPage.setAlignmentX(0.0F);
        sellPage.setAlignmentY(0.0F);
        sellPage.setDoubleBuffered(false);

        sell_shopeName_label.setAlignment(java.awt.Label.CENTER);
        sell_shopeName_label.setFont(new java.awt.Font("Ubuntu", 1, 50)); // NOI18N
        sell_shopeName_label.setText("J");

        sell_entryNavigation_label.setAlignment(java.awt.Label.CENTER);
        sell_entryNavigation_label.setFont(new java.awt.Font("Ubuntu", 0, 19)); // NOI18N
        sell_entryNavigation_label.setForeground(java.awt.Color.lightGray);
        sell_entryNavigation_label.setText("Entry");

        sell_viewNavigation_label.setFont(new java.awt.Font("Ubuntu", 0, 19)); // NOI18N
        sell_viewNavigation_label.setForeground(java.awt.Color.lightGray);
        sell_viewNavigation_label.setText("View");

        sell_sellNavigation_label.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        sell_sellNavigation_label.setText("Sell");

        sell_Welcome_label.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        sell_Welcome_label.setForeground(javax.swing.UIManager.getDefaults().getColor("ComboBox.disabledForeground"));
        sell_Welcome_label.setText("Welcome, Please Scan OR Code.");

        sell_Barcode_label.setBackground(java.awt.Color.lightGray);
        sell_Barcode_label.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        sell_innerWindow_panel.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBoxMenuItem.foreground"));

        sell_qty_label.setForeground(java.awt.Color.black);
        sell_qty_label.setText("QTY");

        sell_chaseNo_label.setForeground(java.awt.Color.black);
        sell_chaseNo_label.setText("Chase No");

        sell_ornamentName_label.setForeground(java.awt.Color.black);
        sell_ornamentName_label.setText("Ornament Name");

        sell_wt_label.setForeground(java.awt.Color.black);
        sell_wt_label.setText("WT");

        sell_was_label.setForeground(java.awt.Color.black);
        sell_was_label.setText("WAS");

        sell_mc_label.setForeground(java.awt.Color.black);
        sell_mc_label.setText("MC");

        sell_verify_checkbox.setBackground(java.awt.Color.white);
        sell_verify_checkbox.setForeground(java.awt.Color.black);
        sell_verify_checkbox.setText("Verify and Checkout!");
        sell_verify_checkbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_verify_checkboxActionPerformed(evt);
            }
        });

        sell_semicolon1_label.setForeground(java.awt.Color.black);
        sell_semicolon1_label.setText("       :");

        sell_semicolon2_label.setForeground(java.awt.Color.black);
        sell_semicolon2_label.setText("       :");

        sell_semicolon3_label.setForeground(java.awt.Color.black);
        sell_semicolon3_label.setText("   :");

        sell_semicolon4_label.setForeground(java.awt.Color.black);
        sell_semicolon4_label.setText("       :    ");

        sell_semicolon5_label.setForeground(java.awt.Color.black);
        sell_semicolon5_label.setText("    :");

        sell_semicolon6_label.setForeground(java.awt.Color.black);
        sell_semicolon6_label.setText(" :");

        sell_chaseNoDetail_label.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        sell_chaseNoDetail_label.setForeground(java.awt.Color.black);
        sell_chaseNoDetail_label.setText("CH123456");

        sell_ornamentNameDetail_label.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        sell_ornamentNameDetail_label.setForeground(java.awt.Color.black);
        sell_ornamentNameDetail_label.setText("Chain");

        sell_wtDetail_label.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        sell_wtDetail_label.setForeground(java.awt.Color.black);
        sell_wtDetail_label.setText("12.170");

        sell_wasDetail_label.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        sell_wasDetail_label.setForeground(java.awt.Color.black);
        sell_wasDetail_label.setText("13.00 %");

        sell_mcDetail_label.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        sell_mcDetail_label.setForeground(java.awt.Color.black);
        sell_mcDetail_label.setText("50.00 /G");

        sell_qtyInput_textfield.setBackground(java.awt.Color.white);

        javax.swing.GroupLayout sell_innerWindow_panelLayout = new javax.swing.GroupLayout(sell_innerWindow_panel);
        sell_innerWindow_panel.setLayout(sell_innerWindow_panelLayout);
        sell_innerWindow_panelLayout.setHorizontalGroup(
            sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                                .addComponent(sell_mc_label, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sell_semicolon6_label, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                                .addComponent(sell_was_label, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sell_semicolon5_label, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                                .addComponent(sell_ornamentName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                                .addComponent(sell_semicolon3_label, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_innerWindow_panelLayout.createSequentialGroup()
                                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sell_chaseNo_label, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sell_qty_label, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sell_wt_label, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(sell_semicolon4_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sell_semicolon1_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sell_semicolon2_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(162, 162, 162)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(sell_chaseNoDetail_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sell_ornamentNameDetail_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sell_wtDetail_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sell_mcDetail_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sell_wasDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sell_qtyInput_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGap(474, 474, 474)
                        .addComponent(sell_verify_checkbox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(285, Short.MAX_VALUE))
        );
        sell_innerWindow_panelLayout.setVerticalGroup(
            sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_qty_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_semicolon1_label)
                    .addComponent(sell_qtyInput_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_chaseNo_label, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_semicolon2_label)
                    .addComponent(sell_chaseNoDetail_label))
                .addGap(18, 18, 18)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_ornamentName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_semicolon3_label)
                    .addComponent(sell_ornamentNameDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sell_wtDetail_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sell_wt_label)
                        .addComponent(sell_semicolon4_label)))
                .addGap(18, 18, 18)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_was_label, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_semicolon5_label)
                    .addComponent(sell_wasDetail_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sell_mcDetail_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sell_mc_label, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sell_semicolon6_label)))
                .addGap(18, 18, 18)
                .addComponent(sell_verify_checkbox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        sell_return_button.setBackground(javax.swing.UIManager.getDefaults().getColor("white"));
        sell_return_button.setText("Return");
        sell_return_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_return_buttonActionPerformed(evt);
            }
        });

        sell_confirm_button.setBackground(java.awt.Color.white);
        sell_confirm_button.setText("Confirm");
        sell_confirm_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_confirm_buttonActionPerformed(evt);
            }
        });

        sell_refresh_label.setIcon(new javax.swing.ImageIcon("/home/ramya/Downloads/refresh-24px 1.png")); // NOI18N
        sell_refresh_label.setText("jLabel18");

        sell_shopeName_label1.setAlignment(java.awt.Label.CENTER);
        sell_shopeName_label1.setFont(new java.awt.Font("Ubuntu", 1, 32)); // NOI18N
        sell_shopeName_label1.setText("A");

        sell_shopeName_label2.setAlignment(java.awt.Label.CENTER);
        sell_shopeName_label2.setFont(new java.awt.Font("Ubuntu", 1, 50)); // NOI18N
        sell_shopeName_label2.setText("J");

        javax.swing.GroupLayout sellPageLayout = new javax.swing.GroupLayout(sellPage);
        sellPage.setLayout(sellPageLayout);
        sellPageLayout.setHorizontalGroup(
            sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sellPageLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(sell_return_button, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 684, Short.MAX_VALUE)
                .addComponent(sell_confirm_button, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sellPageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sellPageLayout.createSequentialGroup()
                        .addComponent(sell_shopeName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(sell_shopeName_label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(sell_shopeName_label2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(280, 280, 280)
                        .addComponent(sell_entryNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(sell_viewNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(sell_sellNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sellPageLayout.createSequentialGroup()
                        .addComponent(sell_innerWindow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125))))
            .addGroup(sellPageLayout.createSequentialGroup()
                .addGroup(sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sellPageLayout.createSequentialGroup()
                        .addGap(513, 513, 513)
                        .addComponent(sell_Welcome_label, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sellPageLayout.createSequentialGroup()
                        .addGap(610, 610, 610)
                        .addComponent(sell_Barcode_label, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(sell_refresh_label, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sellPageLayout.setVerticalGroup(
            sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellPageLayout.createSequentialGroup()
                .addGroup(sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sellPageLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(sell_shopeName_label2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_shopeName_label1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_shopeName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(sellPageLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sell_sellNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_viewNavigation_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_entryNavigation_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(57, 57, 57)
                .addComponent(sell_Welcome_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sell_refresh_label, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(sell_Barcode_label, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(sell_innerWindow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_return_button, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_confirm_button, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.add(sellPage, "sellPage");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sell_return_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_return_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sell_return_buttonActionPerformed

    private void sell_confirm_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_confirm_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sell_confirm_buttonActionPerformed

    private void sell_verify_checkboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_verify_checkboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sell_verify_checkboxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel sellPage;
    private java.awt.Label sell_Barcode_label;
    private java.awt.Label sell_Welcome_label;
    private javax.swing.JLabel sell_chaseNoDetail_label;
    private javax.swing.JLabel sell_chaseNo_label;
    private javax.swing.JButton sell_confirm_button;
    private java.awt.Label sell_entryNavigation_label;
    private javax.swing.JPanel sell_innerWindow_panel;
    private javax.swing.JLabel sell_mcDetail_label;
    private javax.swing.JLabel sell_mc_label;
    private javax.swing.JLabel sell_ornamentNameDetail_label;
    private javax.swing.JLabel sell_ornamentName_label;
    private javax.swing.JTextField sell_qtyInput_textfield;
    private javax.swing.JLabel sell_qty_label;
    private javax.swing.JLabel sell_refresh_label;
    private javax.swing.JButton sell_return_button;
    private java.awt.Label sell_sellNavigation_label;
    private javax.swing.JLabel sell_semicolon1_label;
    private javax.swing.JLabel sell_semicolon2_label;
    private javax.swing.JLabel sell_semicolon3_label;
    private javax.swing.JLabel sell_semicolon4_label;
    private javax.swing.JLabel sell_semicolon5_label;
    private javax.swing.JLabel sell_semicolon6_label;
    private java.awt.Label sell_shopeName_label;
    private java.awt.Label sell_shopeName_label1;
    private java.awt.Label sell_shopeName_label2;
    private javax.swing.JCheckBox sell_verify_checkbox;
    private java.awt.Label sell_viewNavigation_label;
    private javax.swing.JLabel sell_wasDetail_label;
    private javax.swing.JLabel sell_was_label;
    private javax.swing.JLabel sell_wtDetail_label;
    private javax.swing.JLabel sell_wt_label;
    // End of variables declaration//GEN-END:variables
}
