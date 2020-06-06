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
        sell_scroll_scrollpanel = new javax.swing.JScrollPane();
        sell_wrapper_panel = new javax.swing.JPanel();
        sell_shopeName_label = new java.awt.Label();
        sell_entryNavigation_label = new java.awt.Label();
        sell_viewNavigation_label = new java.awt.Label();
        sell_sellNavigation_label = new java.awt.Label();
        sell_Welcome_label = new java.awt.Label();
        sell_refresh_label = new javax.swing.JLabel();
        sell_innerWindow_panel = new javax.swing.JPanel();
        sell_qty_label = new javax.swing.JLabel();
        sell_chaseNo_label = new javax.swing.JLabel();
        sell_ornamentName_label = new javax.swing.JLabel();
        sell_wt_label = new javax.swing.JLabel();
        sell_was_label = new javax.swing.JLabel();
        sell_mc_label = new javax.swing.JLabel();
        sell_semicolon1_label = new javax.swing.JLabel();
        sell_semicolon2_label = new javax.swing.JLabel();
        sell_semicolon3_label = new javax.swing.JLabel();
        sell_semicolon5_label = new javax.swing.JLabel();
        sell_semicolon6_label = new javax.swing.JLabel();
        sell_chaseNoDetail_label = new javax.swing.JLabel();
        sell_ornamentNameDetail_label = new javax.swing.JLabel();
        sell_wtDetail_label = new javax.swing.JLabel();
        sell_wasDetail_label = new javax.swing.JLabel();
        sell_mcDetail_label = new javax.swing.JLabel();
        sell_semicolon4_label = new javax.swing.JLabel();
        sell_verify_checkbox = new javax.swing.JCheckBox();
        sell_qtyInput_panel = new javax.swing.JPanel();
        sell_qtyInput_textField = new javax.swing.JTextField();
        sell_qtyInput_label = new javax.swing.JLabel();
        sell_shopeName_label1 = new java.awt.Label();
        sell_shopeName_label2 = new java.awt.Label();
        sell_confirm_label = new javax.swing.JLabel();
        sell_return_label1 = new javax.swing.JLabel();
        sell_barcodeInput_panel = new javax.swing.JPanel();
        sell_barcodeInput_textField = new javax.swing.JTextField();
        sell_barcodeInput_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        sellPage.setBackground(java.awt.Color.white);
        sellPage.setAlignmentX(0.0F);
        sellPage.setAlignmentY(0.0F);
        sellPage.setDoubleBuffered(false);
        sellPage.setPreferredSize(new java.awt.Dimension(1280, 820));

        sell_scroll_scrollpanel.setAutoscrolls(true);
        sell_scroll_scrollpanel.setMinimumSize(new java.awt.Dimension(0, 0));
        sell_scroll_scrollpanel.setPreferredSize(new java.awt.Dimension(1280, 1489));

        sell_wrapper_panel.setBackground(java.awt.Color.white);
        sell_wrapper_panel.setAlignmentX(0.0F);
        sell_wrapper_panel.setAlignmentY(0.0F);
        sell_wrapper_panel.setAutoscrolls(true);
        sell_wrapper_panel.setDoubleBuffered(false);
        sell_wrapper_panel.setPreferredSize(new java.awt.Dimension(1280, 1000));

        sell_shopeName_label.setAlignment(java.awt.Label.CENTER);
        sell_shopeName_label.setFont(new java.awt.Font("Ubuntu", 1, 50)); // NOI18N
        sell_shopeName_label.setText("J");

        sell_entryNavigation_label.setAlignment(java.awt.Label.CENTER);
        sell_entryNavigation_label.setFont(new java.awt.Font("Ubuntu", 0, 19)); // NOI18N
        sell_entryNavigation_label.setForeground(java.awt.Color.gray);
        sell_entryNavigation_label.setText("Entry");

        sell_viewNavigation_label.setFont(new java.awt.Font("Ubuntu", 0, 19)); // NOI18N
        sell_viewNavigation_label.setForeground(java.awt.Color.gray);
        sell_viewNavigation_label.setText("View");

        sell_sellNavigation_label.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        sell_sellNavigation_label.setText("Sell");

        sell_Welcome_label.setAlignment(java.awt.Label.CENTER);
        sell_Welcome_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_Welcome_label.setForeground(new java.awt.Color(124, 119, 119));
        sell_Welcome_label.setText("Welcome,  Please  Scan  OR  Code.");

        sell_refresh_label.setIcon(new javax.swing.ImageIcon("/home/ramya/Desktop/Jewellery project/refresh-24px 1 (1).png")); // NOI18N

        sell_innerWindow_panel.setBackground(new java.awt.Color(250, 250, 250));
        sell_innerWindow_panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, new java.awt.Color(245, 245, 245), java.awt.Color.gray, new java.awt.Color(230, 230, 230)));
        sell_innerWindow_panel.setForeground(new java.awt.Color(130, 130, 130));

        sell_qty_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_qty_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_qty_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_qty_label.setText("QTY");

        sell_chaseNo_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_chaseNo_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_chaseNo_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_chaseNo_label.setText("Chase No");

        sell_ornamentName_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_ornamentName_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_ornamentName_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_ornamentName_label.setText("Ornament Name");

        sell_wt_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_wt_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_wt_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_wt_label.setText("WT");

        sell_was_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_was_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_was_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_was_label.setText("WAS");

        sell_mc_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_mc_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_mc_label.setText("MC");

        sell_semicolon1_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_semicolon1_label.setForeground(java.awt.Color.gray);
        sell_semicolon1_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sell_semicolon1_label.setText(":");

        sell_semicolon2_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_semicolon2_label.setForeground(java.awt.Color.gray);
        sell_semicolon2_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sell_semicolon2_label.setText(":");

        sell_semicolon3_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_semicolon3_label.setForeground(java.awt.Color.gray);
        sell_semicolon3_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sell_semicolon3_label.setText(":");

        sell_semicolon5_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_semicolon5_label.setForeground(java.awt.Color.gray);
        sell_semicolon5_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sell_semicolon5_label.setText(":");

        sell_semicolon6_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_semicolon6_label.setForeground(java.awt.Color.gray);
        sell_semicolon6_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sell_semicolon6_label.setText(" :");

        sell_chaseNoDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_chaseNoDetail_label.setForeground(java.awt.Color.black);
        sell_chaseNoDetail_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_chaseNoDetail_label.setText("CH123456");

        sell_ornamentNameDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_ornamentNameDetail_label.setForeground(java.awt.Color.black);
        sell_ornamentNameDetail_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_ornamentNameDetail_label.setText("Chain");

        sell_wtDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_wtDetail_label.setForeground(java.awt.Color.black);
        sell_wtDetail_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_wtDetail_label.setText("12.170");

        sell_wasDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_wasDetail_label.setForeground(java.awt.Color.black);
        sell_wasDetail_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_wasDetail_label.setText("13.00 %");

        sell_mcDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_mcDetail_label.setForeground(java.awt.Color.black);
        sell_mcDetail_label.setText("50.00 /G");

        sell_semicolon4_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_semicolon4_label.setForeground(java.awt.Color.gray);
        sell_semicolon4_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sell_semicolon4_label.setText(":");

        sell_verify_checkbox.setBackground(java.awt.Color.white);
        sell_verify_checkbox.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        sell_verify_checkbox.setForeground(java.awt.Color.gray);
        sell_verify_checkbox.setText(" Verify  and  Checkout !");
        sell_verify_checkbox.setBorder(null);
        sell_verify_checkbox.setBorderPainted(true);
        sell_verify_checkbox.setBorderPaintedFlat(true);
        sell_verify_checkbox.setContentAreaFilled(false);
        sell_verify_checkbox.setPreferredSize(new java.awt.Dimension(205, 25));
        sell_verify_checkbox.setSelectedIcon(new javax.swing.ImageIcon("/home/ramya/Desktop/Jewellery project/Rectangle 22 (1).png")); // NOI18N
        sell_verify_checkbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_verify_checkboxActionPerformed(evt);
            }
        });

        sell_qtyInput_panel.setBackground(new java.awt.Color(250, 250, 250));
        sell_qtyInput_panel.setForeground(java.awt.Color.white);
        sell_qtyInput_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sell_qtyInput_textField.setBackground(new java.awt.Color(250, 250, 250));
        sell_qtyInput_textField.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_qtyInput_textField.setForeground(java.awt.Color.black);
        sell_qtyInput_textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sell_qtyInput_textField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sell_qtyInput_textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_qtyInput_textFieldActionPerformed(evt);
            }
        });
        sell_qtyInput_panel.add(sell_qtyInput_textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 30));

        sell_qtyInput_label.setBackground(java.awt.Color.white);
        sell_qtyInput_label.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        sell_qtyInput_label.setForeground(java.awt.Color.white);
        sell_qtyInput_label.setIcon(new javax.swing.ImageIcon("/home/ramya/Desktop/Jewellery project/Rectangle 19 (1).png")); // NOI18N
        sell_qtyInput_panel.add(sell_qtyInput_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 50));

        javax.swing.GroupLayout sell_innerWindow_panelLayout = new javax.swing.GroupLayout(sell_innerWindow_panel);
        sell_innerWindow_panel.setLayout(sell_innerWindow_panelLayout);
        sell_innerWindow_panelLayout.setHorizontalGroup(
            sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sell_was_label, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(sell_mc_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(130, 130, 130)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sell_semicolon5_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_semicolon6_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_semicolon4_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sell_ornamentName_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_chaseNo_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_qty_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_wt_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(130, 130, 130)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sell_semicolon3_label, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(sell_semicolon2_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_semicolon1_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sell_chaseNoDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_ornamentNameDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_wtDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_wasDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_mcDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(sell_qtyInput_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(231, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_innerWindow_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sell_verify_checkbox, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(438, 438, 438))
        );
        sell_innerWindow_panelLayout.setVerticalGroup(
            sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sell_qty_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_semicolon1_label))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_innerWindow_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sell_qtyInput_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_semicolon2_label)
                    .addComponent(sell_chaseNo_label, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_chaseNoDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sell_semicolon3_label)
                        .addComponent(sell_ornamentNameDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sell_ornamentName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sell_wt_label)
                    .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sell_wtDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sell_semicolon4_label)))
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sell_was_label, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_semicolon5_label)))
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(sell_wasDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_mc_label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_semicolon6_label)
                    .addComponent(sell_mcDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(sell_verify_checkbox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        sell_shopeName_label1.setAlignment(java.awt.Label.CENTER);
        sell_shopeName_label1.setFont(new java.awt.Font("Ubuntu", 1, 32)); // NOI18N
        sell_shopeName_label1.setText("A");

        sell_shopeName_label2.setAlignment(java.awt.Label.CENTER);
        sell_shopeName_label2.setFont(new java.awt.Font("Ubuntu", 1, 50)); // NOI18N
        sell_shopeName_label2.setText("J");

        sell_confirm_label.setIcon(new javax.swing.ImageIcon("/home/ramya/Desktop/Jewellery project/Group 29.png")); // NOI18N

        sell_return_label1.setBackground(java.awt.Color.white);
        sell_return_label1.setIcon(new javax.swing.ImageIcon("/home/ramya/Desktop/Jewellery project/Group 28.png")); // NOI18N

        sell_barcodeInput_panel.setBackground(java.awt.Color.white);
        sell_barcodeInput_panel.setForeground(java.awt.Color.white);
        sell_barcodeInput_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sell_barcodeInput_textField.setBackground(new java.awt.Color(250, 250, 250));
        sell_barcodeInput_textField.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        sell_barcodeInput_textField.setForeground(java.awt.Color.black);
        sell_barcodeInput_textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sell_barcodeInput_textField.setText("893712");
        sell_barcodeInput_textField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sell_barcodeInput_textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_barcodeInput_textFieldActionPerformed(evt);
            }
        });
        sell_barcodeInput_panel.add(sell_barcodeInput_textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 150, 40));

        sell_barcodeInput_label.setBackground(java.awt.Color.white);
        sell_barcodeInput_label.setForeground(java.awt.Color.white);
        sell_barcodeInput_label.setIcon(new javax.swing.ImageIcon("/home/ramya/Desktop/Jewellery project/Rectangle 18 (2).png")); // NOI18N
        sell_barcodeInput_panel.add(sell_barcodeInput_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 170, 70));

        javax.swing.GroupLayout sell_wrapper_panelLayout = new javax.swing.GroupLayout(sell_wrapper_panel);
        sell_wrapper_panel.setLayout(sell_wrapper_panelLayout);
        sell_wrapper_panelLayout.setHorizontalGroup(
            sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_wrapper_panelLayout.createSequentialGroup()
                .addGroup(sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(sell_wrapper_panelLayout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(sell_return_label1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sell_confirm_label))
                    .addGroup(sell_wrapper_panelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sell_shopeName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(sell_shopeName_label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(sell_shopeName_label2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(280, 280, 280)
                        .addComponent(sell_entryNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(sell_viewNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)))
                .addGap(0, 0, 0)
                .addComponent(sell_sellNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(sell_wrapper_panelLayout.createSequentialGroup()
                .addGroup(sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_wrapper_panelLayout.createSequentialGroup()
                        .addGap(560, 560, 560)
                        .addComponent(sell_barcodeInput_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(sell_refresh_label, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sell_wrapper_panelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(sell_innerWindow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_wrapper_panelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sell_Welcome_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(444, 444, 444))
        );
        sell_wrapper_panelLayout.setVerticalGroup(
            sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sell_wrapper_panelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(sell_shopeName_label2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sell_shopeName_label1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sell_shopeName_label, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(sell_sellNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sell_viewNavigation_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sell_entryNavigation_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(sell_Welcome_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sell_barcodeInput_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_wrapper_panelLayout.createSequentialGroup()
                        .addComponent(sell_refresh_label, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)))
                .addGap(30, 30, 30)
                .addComponent(sell_innerWindow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(sell_wrapper_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sell_return_label1)
                    .addComponent(sell_confirm_label))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        sell_scroll_scrollpanel.setViewportView(sell_wrapper_panel);

        javax.swing.GroupLayout sellPageLayout = new javax.swing.GroupLayout(sellPage);
        sellPage.setLayout(sellPageLayout);
        sellPageLayout.setHorizontalGroup(
            sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellPageLayout.createSequentialGroup()
                .addComponent(sell_scroll_scrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        sellPageLayout.setVerticalGroup(
            sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellPageLayout.createSequentialGroup()
                .addComponent(sell_scroll_scrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        sell_scroll_scrollpanel.getAccessibleContext().setAccessibleName("");

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

    private void sell_verify_checkboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_verify_checkboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sell_verify_checkboxActionPerformed

    private void sell_barcodeInput_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_barcodeInput_textFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sell_barcodeInput_textFieldActionPerformed

    private void sell_qtyInput_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_qtyInput_textFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sell_qtyInput_textFieldActionPerformed

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
    private java.awt.Label sell_Welcome_label;
    private javax.swing.JLabel sell_barcodeInput_label;
    private javax.swing.JPanel sell_barcodeInput_panel;
    private javax.swing.JTextField sell_barcodeInput_textField;
    private javax.swing.JLabel sell_chaseNoDetail_label;
    private javax.swing.JLabel sell_chaseNo_label;
    private javax.swing.JLabel sell_confirm_label;
    private java.awt.Label sell_entryNavigation_label;
    private javax.swing.JPanel sell_innerWindow_panel;
    private javax.swing.JLabel sell_mcDetail_label;
    private javax.swing.JLabel sell_mc_label;
    private javax.swing.JLabel sell_ornamentNameDetail_label;
    private javax.swing.JLabel sell_ornamentName_label;
    private javax.swing.JLabel sell_qtyInput_label;
    private javax.swing.JPanel sell_qtyInput_panel;
    private javax.swing.JTextField sell_qtyInput_textField;
    private javax.swing.JLabel sell_qty_label;
    private javax.swing.JLabel sell_refresh_label;
    private javax.swing.JLabel sell_return_label1;
    private javax.swing.JScrollPane sell_scroll_scrollpanel;
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
    private javax.swing.JPanel sell_wrapper_panel;
    private javax.swing.JLabel sell_wtDetail_label;
    private javax.swing.JLabel sell_wt_label;
    // End of variables declaration//GEN-END:variables
}
