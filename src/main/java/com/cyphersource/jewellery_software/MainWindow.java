/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyphersource.jewellery_software;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class MainWindow extends javax.swing.JFrame {

    Connection con;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql;
    String defaultPath = null;
    String date_UI = null,
            dateDB = null,
            chase_no = null,
            ornament_type = "0",
            ornament_name = "0",
            quality = null,
            mc = null,
            wt = null,
            was = null,
            qty = null,
            buy = null,
            barcode = null;

    public MainWindow() {

        try {
            JFileChooser fr = new JFileChooser();
            FileSystemView fw = fr.getFileSystemView();
            System.out.println(fw.getDefaultDirectory());
            String dir_path = fw.getDefaultDirectory().toString();
            defaultPath = dir_path + "\\JP";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Oops! Problem in getting default directory");
        }

        initComponents();

        // Database Connectivity
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Oops! Sorry, Connection Lost! kindly Check it");
        }

        getLocalDate();
        DropDown_from_DB();
        AutoComplete();
        groupButton();
    }

    // to get current date
    private void getLocalDate() {
        LocalDate l_date = LocalDate.now();
        dateDB = l_date.toString();
        date_UI = l_date.format(DateTimeFormatter.ofPattern("dd-MM-yy"));
        this.Entry_DateNoValue_Label.setText(this.date_UI);
    }

    // RadioButton group
    private void groupButton() {

        ButtonGroup bg = new ButtonGroup();
        bg.add(Entry_22CT_RadioButton);
        bg.add(Entry_961HM_RadioButton);
    }

    // Combobox dropdown display from database
    private void DropDown_from_DB() {
        try {
            sql = "select * from Ornament_type";
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                Entry_OrnamentType_jComboBox.addItem(type);
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Oops! Sorry, Connection Lost! kindly Check it");
        }
    }

    // DropDown with items from dataBase and AutoComplete Text
    private void AutoComplete() {
//        try {
//            Entry_OrnamentName_jCombobox.removeAllItems();
//        }
//        catch (NullPointerException e){
//            System.out.println(e);
//        }

        try {
            sql = "select * from ornament_name";
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String ornt_name = rs.getString("ornament_name");
                Entry_OrnamentName_jCombobox.addItem(ornt_name);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        // this is a class to autocomplete text.
        AutoCompleteDecorator.decorate(Entry_OrnamentName_jCombobox);
    }

    // getting selected Ornament Type form DataBase and generating chaseNo according the selected type's keyword and total_count
    private void ChaseNo() {
        try {
            sql = "select * from Ornament_type where type=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ornament_type);
            rs = ps.executeQuery();

            while (rs.next()) {
                // conditions to check limit of chase_no and increase total_count by 1.
                if (rs.getInt(4) < 9) {
                    this.chase_no = rs.getString(3) + "" + "0" + "0" + (rs.getInt(4) + 1);
                } else if (rs.getInt(4) < 99) {
                    this.chase_no = rs.getString(3) + "" + "0" + (rs.getInt(4) + 1);
                } else {
                    this.chase_no = rs.getString(3) + "" + (rs.getInt(4) + 1);
                }
            }
            this.Entry_ChaseNoValue_Label.setText(this.chase_no);
            JOptionPane.showMessageDialog(null, "Chase Number Generated successfully!");
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Oops! Sorry, try again");
        }
    }

    // to store the quality of Radiobutton text
    private void QualityRadioBTN() {
        if (Entry_22CT_RadioButton.isSelected()) {
            quality = Entry_22CT_RadioButton.getText();
        } else {
            quality = Entry_961HM_RadioButton.getText();
        }
    }

    // To clear all entered data
    public void ClearData() {
        Entry_OrnamentType_jComboBox.setSelectedIndex(0);
        Entry_OrnamentName_jCombobox.setSelectedIndex(0);
        Entry_22CT_RadioButton.setSelected(false);
        Entry_961HM_RadioButton.setSelected(false);
        Entry_MC_TextField.setText("");
        Entry_WT_TextField.setText("");
        Entry_WAS_TextField.setText("");
        Entry_QTY_TextField.setText("");
        Entry_BUY_TextField.setText("");
        Entry_Check_jCheckBox.setSelected(false);
        Entry_ChaseNoValue_Label.setText("");
        chase_no = null;
        ornament_type = "0";
        ornament_name = "0";
        quality = null;
        mc = null;
        wt = null;
        was = null;
        qty = null;
        buy = null;
        barcode = null;
    }

    private boolean Limit_of_OrnamentNameChar(String name) {
        if (name.length() <= 16) {
            return true;
        } else {
            return false;
        }
    }

    private void PrintBarcode() {
        System.out.println(mc);
        System.out.println(mc);
        System.out.println(mc);
        System.out.println(mc);
        System.out.println(mc);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        entryPage = new javax.swing.JPanel();
        EntryPage_jScrollPane = new javax.swing.JScrollPane();
        EntryPage_Wrapper = new javax.swing.JPanel();
        Entry_ShopnameJ_Label = new javax.swing.JLabel();
        Entry_ShopnameJ2_Label = new javax.swing.JLabel();
        Entry_ShopNameA_Label = new javax.swing.JLabel();
        Entry_NavEntry_Label = new javax.swing.JLabel();
        Entry_NavView_Label = new javax.swing.JLabel();
        Entry_NavSell_Label = new javax.swing.JLabel();
        Entry_ChaseNo_Label = new javax.swing.JLabel();
        Entry_ChaseNoValue_Label = new javax.swing.JLabel();
        Entry_Date_Label = new javax.swing.JLabel();
        Entry_DateNoValue_Label = new javax.swing.JLabel();
        Entry_InputFields_Panel = new javax.swing.JPanel();
        Entry_Quality_TextField = new javax.swing.JLabel();
        Entry_961HM_RadioButton = new javax.swing.JRadioButton();
        Entry_22CT_RadioButton = new javax.swing.JRadioButton();
        Entry_Quality_Label = new javax.swing.JLabel();
        Entry_MC_Label = new javax.swing.JLabel();
        Entry_MC_TextField = new javax.swing.JTextField();
        Entry_MC_Label_Icon = new javax.swing.JLabel();
        Entry_WT_Lable = new javax.swing.JLabel();
        Entry_WT_TextField = new javax.swing.JTextField();
        Entry_WAS_Label = new javax.swing.JLabel();
        Entry_WT_Label_Icon = new javax.swing.JLabel();
        Entry_WAS_TextField = new javax.swing.JTextField();
        Entry_WAS_Label_Icon = new javax.swing.JLabel();
        Entry_QTY_Label = new javax.swing.JLabel();
        Entry_QTY_TextField = new javax.swing.JTextField();
        Entry_QTY_Label_Icon = new javax.swing.JLabel();
        Entry_BUY_Label = new javax.swing.JLabel();
        Entry_BUY_TextField = new javax.swing.JTextField();
        Entry_BUY_Label_Icon = new javax.swing.JLabel();
        Entry_OrnamentType_jComboBox = new javax.swing.JComboBox<>();
        Entry_OrnamentName_jCombobox = new javax.swing.JComboBox<>();
        Entry_CheckText_Label = new javax.swing.JLabel();
        Entry_EnterButton_Label = new javax.swing.JLabel();
        Entry_Check_jCheckBox = new javax.swing.JCheckBox();
        Entry_Reset_jLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        entryPage.setBackground(new java.awt.Color(255, 255, 255));
        entryPage.setAutoscrolls(true);
        entryPage.setPreferredSize(new java.awt.Dimension(1280, 900));

        // Code to reduce ScrollPane width
        EntryPage_jScrollPane.getVerticalScrollBar().setPreferredSize(
            new Dimension(0, Integer.MAX_VALUE));
        EntryPage_jScrollPane.getHorizontalScrollBar().setPreferredSize(
            new Dimension(Integer.MAX_VALUE, 0));

        EntryPage_Wrapper.setBackground(new java.awt.Color(255, 255, 255));
        EntryPage_Wrapper.setAutoscrolls(true);
        EntryPage_Wrapper.setPreferredSize(new java.awt.Dimension(1220, 1690));

        Entry_ShopnameJ_Label.setBackground(new java.awt.Color(250, 250, 250));
        Entry_ShopnameJ_Label.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        Entry_ShopnameJ_Label.setForeground(new java.awt.Color(0, 0, 0));
        Entry_ShopnameJ_Label.setText("J");

        Entry_ShopnameJ2_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ShopnameJ2_Label.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        Entry_ShopnameJ2_Label.setForeground(new java.awt.Color(0, 0, 0));
        Entry_ShopnameJ2_Label.setText("J");

        Entry_ShopNameA_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ShopNameA_Label.setFont(new java.awt.Font("Ubuntu", 1, 38)); // NOI18N
        Entry_ShopNameA_Label.setForeground(new java.awt.Color(0, 0, 0));
        Entry_ShopNameA_Label.setText("A");

        Entry_NavEntry_Label.setBackground(new java.awt.Color(250, 250, 250));
        Entry_NavEntry_Label.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        Entry_NavEntry_Label.setForeground(new java.awt.Color(0, 0, 0));
        Entry_NavEntry_Label.setText("Entry");

        Entry_NavView_Label.setBackground(new java.awt.Color(250, 250, 250));
        Entry_NavView_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_NavView_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_NavView_Label.setText("View");

        Entry_NavSell_Label.setBackground(new java.awt.Color(250, 250, 250));
        Entry_NavSell_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_NavSell_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_NavSell_Label.setText("Sell");

        Entry_ChaseNo_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ChaseNo_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_ChaseNo_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_ChaseNo_Label.setText("Chase No :");

        Entry_ChaseNoValue_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ChaseNoValue_Label.setFont(new java.awt.Font("Ubuntu", 1, 27)); // NOI18N
        Entry_ChaseNoValue_Label.setForeground(new java.awt.Color(0, 0, 0));

        Entry_Date_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_Date_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_Date_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_Date_Label.setText("Date :");

        Entry_DateNoValue_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_DateNoValue_Label.setFont(new java.awt.Font("Ubuntu", 1, 27)); // NOI18N
        Entry_DateNoValue_Label.setForeground(new java.awt.Color(0, 0, 0));

        Entry_InputFields_Panel.setBackground(new java.awt.Color(251, 251, 251));
        Entry_InputFields_Panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Entry_InputFields_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Entry_Quality_TextField.setBackground(new java.awt.Color(255, 255, 255));
        Entry_Quality_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_Quality_TextField.setForeground(new java.awt.Color(98, 98, 98));
        Entry_Quality_TextField.setText("Quality :");
        Entry_InputFields_Panel.add(Entry_Quality_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, -1, -1));

        Entry_961HM_RadioButton.setBackground(new java.awt.Color(255, 255, 255));
        Entry_961HM_RadioButton.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        Entry_961HM_RadioButton.setForeground(new java.awt.Color(98, 98, 98));
        Entry_961HM_RadioButton.setText("916 HM");
        Entry_961HM_RadioButton.setToolTipText("");
        Entry_InputFields_Panel.add(Entry_961HM_RadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, -1, -1));

        Entry_22CT_RadioButton.setBackground(new java.awt.Color(255, 255, 255));
        Entry_22CT_RadioButton.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        Entry_22CT_RadioButton.setForeground(new java.awt.Color(98, 98, 98));
        Entry_22CT_RadioButton.setText("22 CT");
        Entry_22CT_RadioButton.setBorder(null);
        Entry_InputFields_Panel.add(Entry_22CT_RadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 290, -1, -1));

        Entry_Quality_Label.setBackground(new java.awt.Color(255, 255, 255));
        // Entry_InputBox_Label Image
        Entry_Quality_Label.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        Entry_InputFields_Panel.add(Entry_Quality_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, -1, -1));

        Entry_MC_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_MC_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_MC_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_MC_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_MC_Label.setText("MC (/G) :");
        Entry_InputFields_Panel.add(Entry_MC_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, -1, -1));

        Entry_MC_TextField.setBackground(new java.awt.Color(255, 255, 255));
        Entry_MC_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_MC_TextField.setForeground(new java.awt.Color(0, 0, 0));
        Entry_MC_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_MC_TextField.setBorder(null);
        Entry_MC_TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Entry_MC_TextFieldKeyPressed(evt);
            }
        });
        Entry_InputFields_Panel.add(Entry_MC_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, 460, -1));

        Entry_MC_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_MC_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_MC_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, -1, -1));

        Entry_WT_Lable.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WT_Lable.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_WT_Lable.setForeground(new java.awt.Color(98, 98, 98));
        Entry_WT_Lable.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_WT_Lable.setText("WT (g) :");
        Entry_InputFields_Panel.add(Entry_WT_Lable, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 510, -1, -1));

        Entry_WT_TextField.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WT_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_WT_TextField.setForeground(new java.awt.Color(0, 0, 0));
        Entry_WT_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_WT_TextField.setBorder(null);
        Entry_WT_TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Entry_WT_TextFieldKeyPressed(evt);
            }
        });
        Entry_InputFields_Panel.add(Entry_WT_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 510, 470, -1));

        Entry_WAS_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WAS_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_WAS_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_WAS_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_WAS_Label.setText("WAS (%) :");
        Entry_InputFields_Panel.add(Entry_WAS_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 620, -1, -1));

        Entry_WT_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WT_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_WT_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 490, -1, -1));

        Entry_WAS_TextField.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WAS_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_WAS_TextField.setForeground(new java.awt.Color(0, 0, 0));
        Entry_WAS_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_WAS_TextField.setBorder(null);
        Entry_WAS_TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Entry_WAS_TextFieldKeyPressed(evt);
            }
        });
        Entry_InputFields_Panel.add(Entry_WAS_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 620, 450, -1));

        Entry_WAS_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WAS_Label_Icon.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        Entry_WAS_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_WAS_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 600, -1, -1));

        Entry_QTY_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_QTY_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_QTY_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_QTY_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_QTY_Label.setText("QTY :");
        Entry_InputFields_Panel.add(Entry_QTY_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 730, -1, -1));

        Entry_QTY_TextField.setBackground(new java.awt.Color(255, 255, 255));
        Entry_QTY_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_QTY_TextField.setForeground(new java.awt.Color(0, 0, 0));
        Entry_QTY_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_QTY_TextField.setBorder(null);
        Entry_InputFields_Panel.add(Entry_QTY_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 730, 500, -1));

        Entry_QTY_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_QTY_Label_Icon.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        Entry_QTY_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_QTY_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 710, -1, -1));

        Entry_BUY_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_BUY_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_BUY_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_BUY_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_BUY_Label.setText("BUY :");
        Entry_InputFields_Panel.add(Entry_BUY_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 840, -1, -1));

        Entry_BUY_TextField.setBackground(new java.awt.Color(255, 255, 255));
        Entry_BUY_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_BUY_TextField.setForeground(new java.awt.Color(0, 0, 0));
        Entry_BUY_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_BUY_TextField.setBorder(null);
        Entry_InputFields_Panel.add(Entry_BUY_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 840, 500, -1));

        Entry_BUY_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_BUY_Label_Icon.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        Entry_BUY_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_BUY_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 820, -1, -1));

        Entry_OrnamentType_jComboBox.setBackground(new java.awt.Color(255, 255, 255));
        Entry_OrnamentType_jComboBox.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        Entry_OrnamentType_jComboBox.setForeground(new java.awt.Color(98, 98, 98));
        Entry_OrnamentType_jComboBox.setMaximumRowCount(30);
        Entry_OrnamentType_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Ornament Type" }));
        Entry_OrnamentType_jComboBox.setBorder(null);
        Entry_OrnamentType_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Entry_OrnamentType_jComboBoxActionPerformed(evt);
            }
        });
        Entry_InputFields_Panel.add(Entry_OrnamentType_jComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 580, 70));

        Entry_OrnamentName_jCombobox.setBackground(new java.awt.Color(255, 255, 255));
        Entry_OrnamentName_jCombobox.setEditable(true);
        Entry_OrnamentName_jCombobox.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        Entry_OrnamentName_jCombobox.setForeground(new java.awt.Color(98, 98, 98));
        Entry_OrnamentName_jCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Ornament Name" }));
        Entry_OrnamentName_jCombobox.setBorder(null);
        Entry_OrnamentName_jCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Entry_OrnamentName_jComboboxActionPerformed(evt);
            }
        });
        Entry_InputFields_Panel.add(Entry_OrnamentName_jCombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 580, 70));

        Entry_CheckText_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_CheckText_Label.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        Entry_CheckText_Label.setForeground(new java.awt.Color(0, 0, 0));
        Entry_CheckText_Label.setText("Click to Generate BarCode and Print");

        // Entry_EnterBtn_Label
        Entry_EnterButton_Label.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_EnterBtn_Label.png")); // NOI18N
        Entry_EnterButton_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Entry_EnterButton_LabelMouseClicked(evt);
            }
        });

        // Entry_CheckBox_Label
        Entry_Check_jCheckBox.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_checkBox_Label.png")); // NOI18N
        // Entry_CheckedBox_Label
        Entry_Check_jCheckBox.setPressedIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_CheckedBox_Label.png")); // NOI18N
        Entry_Check_jCheckBox.setSelectedIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_checkBox_Label.png")); // NOI18N
        Entry_Check_jCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Entry_Check_jCheckBoxActionPerformed(evt);
            }
        });

        // Entry_ResetBtn_Label
        Entry_Reset_jLabel.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_ResetBtn_Label.png")); // NOI18N
        Entry_Reset_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Entry_Reset_jLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout EntryPage_WrapperLayout = new javax.swing.GroupLayout(EntryPage_Wrapper);
        EntryPage_Wrapper.setLayout(EntryPage_WrapperLayout);
        EntryPage_WrapperLayout.setHorizontalGroup(
            EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EntryPage_WrapperLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                        .addGap(521, 521, 521)
                        .addComponent(Entry_ShopnameJ_Label)
                        .addGap(0, 0, 0)
                        .addComponent(Entry_ShopNameA_Label)
                        .addGap(0, 0, 0)
                        .addComponent(Entry_ShopnameJ2_Label))
                    .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                        .addComponent(Entry_ChaseNo_Label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Entry_ChaseNoValue_Label)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                        .addComponent(Entry_NavEntry_Label)
                        .addGap(55, 55, 55)
                        .addComponent(Entry_NavView_Label)
                        .addGap(55, 55, 55)
                        .addComponent(Entry_NavSell_Label))
                    .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                        .addComponent(Entry_Date_Label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Entry_DateNoValue_Label)))
                .addGap(70, 70, 70))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EntryPage_WrapperLayout.createSequentialGroup()
                .addContainerGap(376, Short.MAX_VALUE)
                .addComponent(Entry_Check_jCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Entry_CheckText_Label)
                .addGap(374, 374, 374))
            .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(Entry_InputFields_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(Entry_Reset_jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Entry_EnterButton_Label)
                .addGap(161, 161, 161))
        );
        EntryPage_WrapperLayout.setVerticalGroup(
            EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Entry_ShopNameA_Label)
                            .addComponent(Entry_ShopnameJ2_Label)
                            .addComponent(Entry_ShopnameJ_Label)))
                    .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Entry_NavSell_Label)
                            .addComponent(Entry_NavView_Label)
                            .addComponent(Entry_NavEntry_Label))))
                .addGap(77, 77, 77)
                .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Entry_ChaseNoValue_Label)
                    .addComponent(Entry_ChaseNo_Label)
                    .addComponent(Entry_Date_Label)
                    .addComponent(Entry_DateNoValue_Label))
                .addGap(52, 52, 52)
                .addComponent(Entry_InputFields_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Entry_Check_jCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Entry_CheckText_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Entry_EnterButton_Label)
                    .addComponent(Entry_Reset_jLabel))
                .addGap(0, 249, Short.MAX_VALUE))
        );

        EntryPage_jScrollPane.setViewportView(EntryPage_Wrapper);

        javax.swing.GroupLayout entryPageLayout = new javax.swing.GroupLayout(entryPage);
        entryPage.setLayout(entryPageLayout);
        entryPageLayout.setHorizontalGroup(
            entryPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EntryPage_jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        entryPageLayout.setVerticalGroup(
            entryPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EntryPage_jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1617, Short.MAX_VALUE)
        );

        EntryPage_jScrollPane.getVerticalScrollBar().setUnitIncrement(100);

        jLayeredPane1.add(entryPage, "entryPage");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1220, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void Entry_EnterButton_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Entry_EnterButton_LabelMouseClicked

        // quality 
        QualityRadioBTN();

        mc = Entry_MC_TextField.getText();
        wt = Entry_WT_TextField.getText();
        was = Entry_WAS_TextField.getText();
        qty = Entry_QTY_TextField.getText();
        buy = Entry_BUY_TextField.getText();
        barcode = chase_no;

        // to Insert newly entered Ornament Name in Database in(ornament_name Table)
        if (Limit_of_OrnamentNameChar(ornament_name)) {
            try {
                // Query
                sql = "INSERT INTO ornament_name (ornament_name) VALUES (?)";

                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, ornament_name);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "Oops! Sorry, unable to update Ornament Name, check whether the entered name is within 16 characters");
        }

        // condition to check all datas are entered if not show MessageBox orelse Insert Data.
        if ("".equals(mc) || "".equals(barcode) || "".equals(dateDB) || "".equals(chase_no) || ornament_type == "0" || ornament_name == "0" || "".equals(quality) || "".equals(wt) || "".equals(was) || "".equals(qty) || "".equals(buy)) {

            JOptionPane.showMessageDialog(null, "Sorry, You missed some fields, Please do fill it");
        } else if (!Limit_of_OrnamentNameChar(ornament_name)) {
            JOptionPane.showMessageDialog(null, "\"Oops! Sorry, check whether the entered Ornament name is within 16 characters");
        } else {
            // to Insert all Datas in DB in(jewellery_entry Table).
            try {

                // Query
                sql = "INSERT INTO jewellery_entry (date, chase_no, ornament_type, ornament_name, quality, making_charge, weight, wastage, quantity, buy, barcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, dateDB);
                statement.setString(2, chase_no);
                statement.setString(3, ornament_type);
                statement.setString(4, ornament_name);
                statement.setString(5, quality);
                statement.setString(6, mc);
                statement.setString(7, wt);
                statement.setString(8, was);
                statement.setString(9, qty);
                statement.setString(10, buy);
                statement.setString(11, barcode);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, " updation successful!");
                }

                boolean flag = true;
                for (int i = 0; i < Entry_OrnamentName_jCombobox.getItemCount(); i++) {
                    if ((Entry_OrnamentName_jCombobox.getItemAt(i).equals(ornament_name))) {
                        flag = false;
                    }
                }
                if (flag) {
                    Entry_OrnamentName_jCombobox.addItem(ornament_name);
                }

                ClearData(); // to clear Data
//                AutoComplete(); // to show newly added item in dropdown;
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "OOPS! Sorry, try again");
            }

        }


    }//GEN-LAST:event_Entry_EnterButton_LabelMouseClicked

    // Ornament_type Action
    private void Entry_OrnamentType_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Entry_OrnamentType_jComboBoxActionPerformed

        this.ornament_type = (String) Entry_OrnamentType_jComboBox.getSelectedItem();

        // if nothing is selected in Ornament_type don't show ChaseValue else call chaseNo() to generate chaseNo.
        if (!"Select Ornament Type".equals(ornament_type)) {
            ChaseNo();
        } else {
            Entry_ChaseNoValue_Label.setText("");
        }
    }//GEN-LAST:event_Entry_OrnamentType_jComboBoxActionPerformed

    // CheckBox Action
    private void Entry_Check_jCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Entry_Check_jCheckBoxActionPerformed

        // if fields are empty it should not generate barcode 
        if ("".equals(mc) || "".equals(barcode) || "".equals(dateDB) || "".equals(chase_no) || ornament_type == "0" || ornament_name == "0" || "".equals(quality) || "".equals(wt) || "".equals(was) || "".equals(qty) || "".equals(buy)) {
            Entry_Check_jCheckBox.setSelected(false);
            JOptionPane.showMessageDialog(null, "Sorry, You have missed some fields, Please fill it");
        } else {
            // if checkBox is selected generate barcode orelse no
            if (Entry_Check_jCheckBox.isSelected()) {
                this.barcode = chase_no;
            } else {
                this.barcode = null;
            }
        }
    }//GEN-LAST:event_Entry_Check_jCheckBoxActionPerformed

    // Reset Button to clear Data
    private void Entry_Reset_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Entry_Reset_jLabelMouseClicked

        ClearData();
    }//GEN-LAST:event_Entry_Reset_jLabelMouseClicked

    // Entry_MC_TextField Keypressed event to type limit num of characters.
    private void Entry_MC_TextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Entry_MC_TextFieldKeyPressed

        if (!(Entry_MC_TextField.getText().length() <= 5)) {
            Entry_MC_TextField.setEditable(false);
        } else {
            Entry_MC_TextField.setEditable(true);
        }
        if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            Entry_MC_TextField.setEditable(true);
        }
    }//GEN-LAST:event_Entry_MC_TextFieldKeyPressed

    // Entry_WT_TextField Keypressed event to type limit num of characters.
    private void Entry_WT_TextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Entry_WT_TextFieldKeyPressed

        if (!(Entry_WT_TextField.getText().length() <= 6)) {
            Entry_WT_TextField.setEditable(false);
        } else {
            Entry_WT_TextField.setEditable(true);
        }
        if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            Entry_WT_TextField.setEditable(true);
        }

    }//GEN-LAST:event_Entry_WT_TextFieldKeyPressed

    // Entry_WAS_TextField Keypressed event to type limit num of characters.
    private void Entry_WAS_TextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Entry_WAS_TextFieldKeyPressed

        if (!(Entry_WAS_TextField.getText().length() <= 5)) {
            Entry_WAS_TextField.setEditable(false);
        } else {
            Entry_WAS_TextField.setEditable(true);
        }
        if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            Entry_WAS_TextField.setEditable(true);
        }

    }//GEN-LAST:event_Entry_WAS_TextFieldKeyPressed

    // Ornament_name Action
    private void Entry_OrnamentName_jComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Entry_OrnamentName_jComboboxActionPerformed

        this.ornament_name = (String) Entry_OrnamentName_jCombobox.getSelectedItem();
        System.out.println(this.ornament_name);

        if (!"Select Ornament Name".equals(ornament_name)) {
            if (!Limit_of_OrnamentNameChar(ornament_name)) {
                JOptionPane.showMessageDialog(null, "\"Oops! Sorry, check whether the entered name is within 16 characters");
            }
        }
    }//GEN-LAST:event_Entry_OrnamentName_jComboboxActionPerformed

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

            // Database Connection class
            Class.forName("com.mysql.cj.jdbc.Driver");

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
        } catch (Exception e) {
            System.out.println(e);
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
    private javax.swing.JPanel EntryPage_Wrapper;
    private javax.swing.JScrollPane EntryPage_jScrollPane;
    private javax.swing.JRadioButton Entry_22CT_RadioButton;
    private javax.swing.JRadioButton Entry_961HM_RadioButton;
    private javax.swing.JLabel Entry_BUY_Label;
    private javax.swing.JLabel Entry_BUY_Label_Icon;
    private javax.swing.JTextField Entry_BUY_TextField;
    private javax.swing.JLabel Entry_ChaseNoValue_Label;
    private javax.swing.JLabel Entry_ChaseNo_Label;
    private javax.swing.JLabel Entry_CheckText_Label;
    private javax.swing.JCheckBox Entry_Check_jCheckBox;
    private javax.swing.JLabel Entry_DateNoValue_Label;
    private javax.swing.JLabel Entry_Date_Label;
    private javax.swing.JLabel Entry_EnterButton_Label;
    private javax.swing.JPanel Entry_InputFields_Panel;
    private javax.swing.JLabel Entry_MC_Label;
    private javax.swing.JLabel Entry_MC_Label_Icon;
    private javax.swing.JTextField Entry_MC_TextField;
    private javax.swing.JLabel Entry_NavEntry_Label;
    private javax.swing.JLabel Entry_NavSell_Label;
    private javax.swing.JLabel Entry_NavView_Label;
    private javax.swing.JComboBox<String> Entry_OrnamentName_jCombobox;
    private javax.swing.JComboBox<String> Entry_OrnamentType_jComboBox;
    private javax.swing.JLabel Entry_QTY_Label;
    private javax.swing.JLabel Entry_QTY_Label_Icon;
    private javax.swing.JTextField Entry_QTY_TextField;
    private javax.swing.JLabel Entry_Quality_Label;
    private javax.swing.JLabel Entry_Quality_TextField;
    private javax.swing.JLabel Entry_Reset_jLabel;
    private javax.swing.JLabel Entry_ShopNameA_Label;
    private javax.swing.JLabel Entry_ShopnameJ2_Label;
    private javax.swing.JLabel Entry_ShopnameJ_Label;
    private javax.swing.JLabel Entry_WAS_Label;
    private javax.swing.JLabel Entry_WAS_Label_Icon;
    private javax.swing.JTextField Entry_WAS_TextField;
    private javax.swing.JLabel Entry_WT_Label_Icon;
    private javax.swing.JLabel Entry_WT_Lable;
    private javax.swing.JTextField Entry_WT_TextField;
    private javax.swing.JPanel entryPage;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables

}