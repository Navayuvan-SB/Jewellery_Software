/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyphersource.jewellery_software;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class MainWindow extends javax.swing.JFrame {

    Connection con;
    CardLayout mainLayout;
    PreparedStatement ps=null, st=null;
    ResultSet rs=null;
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
        // image path
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

        //initIcons();

        // Initialize MainLayout
        mainLayout = (CardLayout) jLayeredPane1.getLayout();

        // Database Connectivity
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Oops! Sorry, Connection Lost! kindly Check it");
        } 

         //Initially Labels Disabled
         sell_confirm_label.setEnabled(false);
         sell_return_label1.setEnabled(false);
         
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

    // to set limit num of character for ornament name
    private boolean Limit_of_OrnamentNameChar(String name) {
        if (name.length() <= 16) {
            return true;
        } else {
            return false;
        }
    }
    
    // To get values of input
    private void GetValues(){
        mc = Entry_MC_TextField.getText();
        wt = Entry_WT_TextField.getText();
        was = Entry_WAS_TextField.getText();
        qty = Entry_QTY_TextField.getText();
        buy = Entry_BUY_TextField.getText();
        barcode = chase_no;
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
        Entry_WT_Label_Icon = new javax.swing.JLabel();
        Entry_WAS_Label = new javax.swing.JLabel();
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
        sellPage = new javax.swing.JPanel();
        sell_scroll_scrollpanel = new javax.swing.JScrollPane();
        sell_ShopNameA_Label1 = new javax.swing.JPanel();
        sell_ShopnameJ_Label1 = new javax.swing.JLabel();
        sell_ShopnameA_Label2 = new javax.swing.JLabel();
        sell_ShopnameJ2_Label3 = new javax.swing.JLabel();
        sell_entryNavigation_label = new java.awt.Label();
        sell_viewNavigation_label = new java.awt.Label();
        sell_sellNavigation_label = new java.awt.Label();
        sell_Welcome_label = new java.awt.Label();
        sell_refresh_label = new javax.swing.JLabel();
        sell_confirm_label = new javax.swing.JLabel();
        sell_return_label1 = new javax.swing.JLabel();
        sell_barcodeInput_panel = new javax.swing.JPanel();
        sell_barcodeInput_textField = new javax.swing.JTextField();
        sell_barcodeInput_label = new javax.swing.JLabel();
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
        Entry_ShopnameJ_Label.setText("J");

        Entry_ShopnameJ2_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ShopnameJ2_Label.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        Entry_ShopnameJ2_Label.setText("J");

        Entry_ShopNameA_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ShopNameA_Label.setFont(new java.awt.Font("Ubuntu", 1, 38)); // NOI18N
        Entry_ShopNameA_Label.setText("A");

        Entry_NavEntry_Label.setBackground(new java.awt.Color(250, 250, 250));
        Entry_NavEntry_Label.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        Entry_NavEntry_Label.setText("Entry");

        Entry_NavView_Label.setBackground(new java.awt.Color(250, 250, 250));
        Entry_NavView_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_NavView_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_NavView_Label.setText("View");

        Entry_NavSell_Label.setBackground(new java.awt.Color(250, 250, 250));
        Entry_NavSell_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_NavSell_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_NavSell_Label.setText("Sell");
        Entry_NavSell_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Entry_NavSell_LabelMouseClicked(evt);
            }
        });

        Entry_ChaseNo_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ChaseNo_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_ChaseNo_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_ChaseNo_Label.setText("Chase No :");

        Entry_ChaseNoValue_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ChaseNoValue_Label.setFont(new java.awt.Font("Ubuntu", 1, 27)); // NOI18N

        Entry_Date_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_Date_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_Date_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_Date_Label.setText("Date :");

        Entry_DateNoValue_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_DateNoValue_Label.setFont(new java.awt.Font("Ubuntu", 1, 27)); // NOI18N

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
        Entry_InputFields_Panel.add(Entry_Quality_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, -1, -1));

        Entry_MC_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_MC_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_MC_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_MC_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_MC_Label.setText("MC (/G) :");
        Entry_InputFields_Panel.add(Entry_MC_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, -1, -1));

        Entry_MC_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_MC_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_MC_TextField.setBorder(null);
        Entry_MC_TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Entry_MC_TextFieldKeyPressed(evt);
            }
        });
        Entry_InputFields_Panel.add(Entry_MC_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, 460, -1));

        Entry_MC_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_MC_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, -1, -1));

        Entry_WT_Lable.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WT_Lable.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_WT_Lable.setForeground(new java.awt.Color(98, 98, 98));
        Entry_WT_Lable.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_WT_Lable.setText("WT (g) :");
        Entry_InputFields_Panel.add(Entry_WT_Lable, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 510, -1, -1));

        Entry_WT_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_WT_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_WT_TextField.setBorder(null);
        Entry_WT_TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Entry_WT_TextFieldKeyPressed(evt);
            }
        });
        Entry_InputFields_Panel.add(Entry_WT_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 510, 470, -1));

        Entry_WT_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_InputFields_Panel.add(Entry_WT_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 490, -1, -1));

        Entry_WAS_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WAS_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_WAS_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_WAS_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_WAS_Label.setText("WAS (%) :");
        Entry_InputFields_Panel.add(Entry_WAS_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 620, -1, -1));

        Entry_WAS_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_WAS_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_WAS_TextField.setBorder(null);
        Entry_WAS_TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Entry_WAS_TextFieldKeyPressed(evt);
            }
        });
        Entry_InputFields_Panel.add(Entry_WAS_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 620, 470, -1));

        Entry_WAS_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_WAS_Label_Icon.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_WAS_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 600, -1, -1));

        Entry_QTY_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_QTY_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_QTY_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_QTY_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_QTY_Label.setText("QTY :");
        Entry_InputFields_Panel.add(Entry_QTY_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 730, -1, -1));

        Entry_QTY_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_QTY_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_QTY_TextField.setBorder(null);
        Entry_InputFields_Panel.add(Entry_QTY_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 730, 500, -1));

        Entry_QTY_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_QTY_Label_Icon.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_QTY_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 710, -1, -1));

        Entry_BUY_Label.setBackground(new java.awt.Color(255, 255, 255));
        Entry_BUY_Label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_BUY_Label.setForeground(new java.awt.Color(98, 98, 98));
        Entry_BUY_Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Entry_BUY_Label.setText("BUY :");
        Entry_InputFields_Panel.add(Entry_BUY_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 840, -1, -1));

        Entry_BUY_TextField.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        Entry_BUY_TextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Entry_BUY_TextField.setBorder(null);
        Entry_InputFields_Panel.add(Entry_BUY_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 840, 500, -1));

        Entry_BUY_Label_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Entry_BUY_Label_Icon.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        //Entry_InputBox_Label Image
        Entry_InputFields_Panel.add(Entry_BUY_Label_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 820, -1, -1));

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
        Entry_CheckText_Label.setText("Click to Generate BarCode and Print");

        // Entry_EnterBtn_Label
        Entry_EnterButton_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Entry_EnterButton_LabelMouseClicked(evt);
            }
        });

        // Entry_CheckBox_Label
        // Entry_CheckedBox_Label
        Entry_Check_jCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Entry_Check_jCheckBoxActionPerformed(evt);
            }
        });

        // Entry_ResetBtn_Label
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
            .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(Entry_InputFields_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(Entry_Reset_jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Entry_EnterButton_Label)
                .addGap(164, 164, 164))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EntryPage_WrapperLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Entry_Check_jCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Entry_CheckText_Label)
                .addGap(386, 386, 386))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(Entry_InputFields_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addGroup(EntryPage_WrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                        .addComponent(Entry_CheckText_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(Entry_EnterButton_Label))
                    .addGroup(EntryPage_WrapperLayout.createSequentialGroup()
                        .addComponent(Entry_Check_jCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(Entry_Reset_jLabel)))
                .addGap(201, 201, 201))
        );

        EntryPage_jScrollPane.setViewportView(EntryPage_Wrapper);

        javax.swing.GroupLayout entryPageLayout = new javax.swing.GroupLayout(entryPage);
        entryPage.setLayout(entryPageLayout);
        entryPageLayout.setHorizontalGroup(
            entryPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EntryPage_jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1220, Short.MAX_VALUE)
        );
        entryPageLayout.setVerticalGroup(
            entryPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EntryPage_jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );

        EntryPage_jScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        jLayeredPane1.add(entryPage, "entryPage");

        sellPage.setBackground(java.awt.Color.white);
        sellPage.setAlignmentX(0.0F);
        sellPage.setAlignmentY(0.0F);
        sellPage.setDoubleBuffered(false);
        sellPage.setPreferredSize(new java.awt.Dimension(1280, 1000));

        sell_scroll_scrollpanel.setAutoscrolls(true);
        sell_scroll_scrollpanel.setHorizontalScrollBar(null);
        sell_scroll_scrollpanel.setMinimumSize(new java.awt.Dimension(0, 0));
        sell_scroll_scrollpanel.setPreferredSize(new java.awt.Dimension(1280, 1489));

        sell_ShopNameA_Label1.setBackground(java.awt.Color.white);
        sell_ShopNameA_Label1.setAlignmentX(0.0F);
        sell_ShopNameA_Label1.setAlignmentY(0.0F);
        sell_ShopNameA_Label1.setAutoscrolls(true);
        sell_ShopNameA_Label1.setDoubleBuffered(false);
        sell_ShopNameA_Label1.setPreferredSize(new java.awt.Dimension(1280, 1000));

        sell_ShopnameJ_Label1.setBackground(new java.awt.Color(250, 250, 250));
        sell_ShopnameJ_Label1.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        sell_ShopnameJ_Label1.setText("J");

        sell_ShopnameA_Label2.setBackground(new java.awt.Color(255, 255, 255));
        sell_ShopnameA_Label2.setFont(new java.awt.Font("Ubuntu", 1, 38)); // NOI18N
        sell_ShopnameA_Label2.setText("A");

        sell_ShopnameJ2_Label3.setBackground(new java.awt.Color(255, 255, 255));
        sell_ShopnameJ2_Label3.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        sell_ShopnameJ2_Label3.setText("J");

        sell_entryNavigation_label.setAlignment(java.awt.Label.CENTER);
        sell_entryNavigation_label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        sell_entryNavigation_label.setForeground(new java.awt.Color(98, 98, 8));
        sell_entryNavigation_label.setText("Entry");
        sell_entryNavigation_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_entryNavigation_labelMouseClicked(evt);
            }
        });

        sell_viewNavigation_label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        sell_viewNavigation_label.setForeground(new java.awt.Color(98, 98, 98));
        sell_viewNavigation_label.setText("View");

        sell_sellNavigation_label.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        sell_sellNavigation_label.setText("Sell");

        sell_Welcome_label.setAlignment(java.awt.Label.CENTER);
        sell_Welcome_label.setFont(new java.awt.Font("Ubuntu", 0, 25)); // NOI18N
        sell_Welcome_label.setForeground(new java.awt.Color(124, 119, 119));
        sell_Welcome_label.setText("Welcome,  Please  Scan  OR  Code.");

        //sell page image : sell_refresh.png
        sell_refresh_label.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ramya\\Documents\\JP\\sell_refresh.png")); // NOI18N
        sell_refresh_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_refresh_labelMouseClicked(evt);
            }
        });

        //sell page image : sell_confirm.png
        sell_confirm_label.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ramya\\Documents\\JP\\sell_confirm.png")); // NOI18N
        sell_confirm_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_confirm_labelMouseClicked(evt);
            }
        });

        sell_return_label1.setBackground(java.awt.Color.white);
        //sell page image : sell_return.png
        sell_return_label1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ramya\\Documents\\JP\\sell_return.png")); // NOI18N
        sell_return_label1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_return_label1MouseClicked(evt);
            }
        });

        sell_barcodeInput_panel.setBackground(java.awt.Color.white);
        sell_barcodeInput_panel.setForeground(java.awt.Color.white);
        sell_barcodeInput_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sell_barcodeInput_textField.setBackground(new java.awt.Color(250, 250, 250));
        sell_barcodeInput_textField.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        sell_barcodeInput_textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sell_barcodeInput_textField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sell_barcodeInput_textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_barcodeInput_textFieldActionPerformed(evt);
            }
        });
        sell_barcodeInput_panel.add(sell_barcodeInput_textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 40));

        sell_barcodeInput_label.setBackground(java.awt.Color.white);
        sell_barcodeInput_label.setForeground(java.awt.Color.white);
        //sell page image : sell_barcode.png
        sell_barcodeInput_label.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ramya\\Documents\\JP\\sell_barcode.png")); // NOI18N
        sell_barcodeInput_panel.add(sell_barcodeInput_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 70));

        sell_innerWindow_panel.setBackground(new java.awt.Color(251, 251, 251));
        sell_innerWindow_panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, new java.awt.Color(245, 245, 245), java.awt.Color.gray, new java.awt.Color(230, 230, 230)));
        sell_innerWindow_panel.setForeground(new java.awt.Color(130, 130, 130));

        sell_qty_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_qty_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_qty_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_qty_label.setText("QTY");

        sell_chaseNo_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_chaseNo_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_chaseNo_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_chaseNo_label.setText("Chase No");

        sell_ornamentName_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_ornamentName_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_ornamentName_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_ornamentName_label.setText("Ornament Name");

        sell_wt_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_wt_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_wt_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_wt_label.setText("WT");

        sell_was_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_was_label.setForeground(new java.awt.Color(130, 130, 130));
        sell_was_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_was_label.setText("WAS");

        sell_mc_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
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

        sell_chaseNoDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_chaseNoDetail_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        sell_ornamentNameDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_ornamentNameDetail_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        sell_wtDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_wtDetail_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        sell_wasDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_wasDetail_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        sell_mcDetail_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N

        sell_semicolon4_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        sell_semicolon4_label.setForeground(java.awt.Color.gray);
        sell_semicolon4_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sell_semicolon4_label.setText(":");

        sell_verify_checkbox.setBackground(java.awt.Color.white);
        sell_verify_checkbox.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_verify_checkbox.setForeground(new java.awt.Color(121, 121, 121));
        sell_verify_checkbox.setText(" Verify  and  Checkout !");
        sell_verify_checkbox.setBorder(null);
        sell_verify_checkbox.setBorderPainted(true);
        sell_verify_checkbox.setBorderPaintedFlat(true);
        sell_verify_checkbox.setContentAreaFilled(false);
        //sell page image : sell_unChecked_checkbox.jpg
        sell_verify_checkbox.setPreferredSize(new java.awt.Dimension(205, 25));
        //sell page image : sell_Checked_checkbox.jpg
        sell_verify_checkbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_verify_checkboxActionPerformed(evt);
            }
        });

        sell_qtyInput_panel.setBackground(new java.awt.Color(250, 250, 250));
        sell_qtyInput_panel.setForeground(java.awt.Color.white);
        sell_qtyInput_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sell_qtyInput_textField.setBackground(new java.awt.Color(250, 250, 250));
        sell_qtyInput_textField.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_qtyInput_textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sell_qtyInput_textField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sell_qtyInput_textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sell_qtyInput_textFieldKeyPressed(evt);
            }
        });
        sell_qtyInput_panel.add(sell_qtyInput_textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 30));

        sell_qtyInput_label.setBackground(java.awt.Color.white);
        sell_qtyInput_label.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        sell_qtyInput_label.setForeground(java.awt.Color.white);
        //sell page image : sell_qtyInput.png
        sell_qtyInput_label.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ramya\\Documents\\JP\\sell_qtyInput.png")); // NOI18N
        sell_qtyInput_panel.add(sell_qtyInput_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 50));

        javax.swing.GroupLayout sell_innerWindow_panelLayout = new javax.swing.GroupLayout(sell_innerWindow_panel);
        sell_innerWindow_panel.setLayout(sell_innerWindow_panelLayout);
        sell_innerWindow_panelLayout.setHorizontalGroup(
            sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sell_was_label, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                            .addComponent(sell_mc_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(140, 140, 140)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sell_semicolon5_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_semicolon6_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sell_ornamentName_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_chaseNo_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_qty_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_wt_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(140, 140, 140)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sell_semicolon2_label, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(sell_semicolon3_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sell_semicolon1_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(sell_semicolon4_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(116, 116, 116)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sell_mcDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(sell_wasDetail_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sell_wtDetail_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sell_ornamentNameDetail_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sell_chaseNoDetail_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                            .addComponent(sell_qtyInput_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 55, Short.MAX_VALUE))))
                .addContainerGap(101, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_innerWindow_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sell_verify_checkbox, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(335, 335, 335))
        );
        sell_innerWindow_panelLayout.setVerticalGroup(
            sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_innerWindow_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sell_qty_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_semicolon1_label))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_innerWindow_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sell_qtyInput_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_semicolon2_label)
                    .addComponent(sell_chaseNo_label, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_chaseNoDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sell_ornamentName_label, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(sell_ornamentNameDetail_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sell_semicolon3_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_wtDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_semicolon4_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_wt_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sell_was_label, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sell_semicolon5_label))
                    .addComponent(sell_wasDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(sell_innerWindow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sell_mc_label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_semicolon6_label)
                    .addComponent(sell_mcDetail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(sell_verify_checkbox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sell_ShopNameA_Label1Layout = new javax.swing.GroupLayout(sell_ShopNameA_Label1);
        sell_ShopNameA_Label1.setLayout(sell_ShopNameA_Label1Layout);
        sell_ShopNameA_Label1Layout.setHorizontalGroup(
            sell_ShopNameA_Label1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_ShopNameA_Label1Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(sell_return_label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sell_confirm_label)
                .addGap(220, 220, 220))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_ShopNameA_Label1Layout.createSequentialGroup()
                .addGroup(sell_ShopNameA_Label1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                        .addGap(555, 555, 555)
                        .addComponent(sell_barcodeInput_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(sell_refresh_label)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_ShopNameA_Label1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sell_ShopnameJ_Label1)
                        .addGap(0, 0, 0)
                        .addComponent(sell_ShopnameA_Label2)
                        .addGap(0, 0, 0)
                        .addComponent(sell_ShopnameJ2_Label3)
                        .addGap(192, 192, 192)
                        .addComponent(sell_entryNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(sell_viewNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addComponent(sell_sellNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sell_ShopNameA_Label1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sell_Welcome_label, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(393, 393, 393))
            .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(sell_innerWindow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );
        sell_ShopNameA_Label1Layout.setVerticalGroup(
            sell_ShopNameA_Label1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                .addGroup(sell_ShopNameA_Label1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(sell_ShopNameA_Label1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sell_viewNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_entryNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_sellNavigation_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(sell_ShopNameA_Label1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sell_ShopnameJ_Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sell_ShopnameA_Label2)
                            .addComponent(sell_ShopnameJ2_Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50)
                .addComponent(sell_Welcome_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(sell_ShopNameA_Label1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(sell_barcodeInput_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(sell_refresh_label, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(sell_ShopNameA_Label1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(sell_innerWindow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(sell_confirm_label))
                    .addGroup(sell_ShopNameA_Label1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sell_return_label1)))
                .addGap(40, 40, 40))
        );

        sell_scroll_scrollpanel.setViewportView(sell_ShopNameA_Label1);

        javax.swing.GroupLayout sellPageLayout = new javax.swing.GroupLayout(sellPage);
        sellPage.setLayout(sellPageLayout);
        sellPageLayout.setHorizontalGroup(
            sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellPageLayout.createSequentialGroup()
                .addComponent(sell_scroll_scrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        sellPageLayout.setVerticalGroup(
            sellPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellPageLayout.createSequentialGroup()
                .addComponent(sell_scroll_scrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        sell_scroll_scrollpanel.getAccessibleContext().setAccessibleName("");
        sell_scroll_scrollpanel.getVerticalScrollBar().setUnitIncrement(60);
        sell_scroll_scrollpanel.getVerticalScrollBar().setPreferredSize(new Dimension(0, Integer.MAX_VALUE));

        jLayeredPane1.add(sellPage, "sellPage");

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

    private void initIcons() {
        Entry_Quality_Label.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png"));
        Entry_MC_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        Entry_WT_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        Entry_WAS_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        Entry_QTY_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        Entry_BUY_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_InputBox_Label.png")); // NOI18N
        Entry_EnterButton_Label.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_EnterBtn_Label.png")); // NOI18N
        Entry_Reset_jLabel.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_ResetBtn_Label.png")); // NOI18N
        sell_refresh_label.setIcon(new javax.swing.ImageIcon(defaultPath+"\\sell_refresh.png")); // NOI18N
        sell_confirm_label.setIcon(new javax.swing.ImageIcon(defaultPath+"\\sell_confirm.png")); // NOI18N
        sell_return_label1.setIcon(new javax.swing.ImageIcon(defaultPath+"\\sell_return.png")); // NOI18N
        sell_barcodeInput_label.setIcon(new javax.swing.ImageIcon(defaultPath+"\\sell_barcode.png")); // NOI18N
        sell_verify_checkbox.setIcon(new javax.swing.ImageIcon(defaultPath+"\\sell_unChecked_checkbox.jpg")); // NOI18N
        sell_qtyInput_label.setIcon(new javax.swing.ImageIcon(defaultPath+"\\sell_qtyInput.png")); // NOI18N
        Entry_Check_jCheckBox.setIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_checkBox_Label.png")); // NOI18N
        Entry_Check_jCheckBox.setPressedIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_CheckedBox_Label.png")); // NOI18N
        Entry_Check_jCheckBox.setSelectedIcon(new javax.swing.ImageIcon(defaultPath+"\\Entry_checkBox_Label.png")); 
        sell_verify_checkbox.setSelectedIcon(new javax.swing.ImageIcon(defaultPath+"\\sell_Checked_checkbox.jpg"));
        sell_verify_checkbox.setPressedIcon(new javax.swing.ImageIcon(defaultPath+"\\sell_unChecked_checkbox.jpg"));
    }

    
    
        
    // Navigate to Sell Page
    private boolean navToSell(){
     
        try {
            mainLayout.show(jLayeredPane1, "sellPage");
            return true;
        }
        catch (Exception e){
            return false;
        }
        
    }
    // Navigate to Entry Page
    private boolean navToEntry(){
     
        try {
            mainLayout.show(jLayeredPane1, "entryPage");
            return true;
        }
        catch (Exception e){
            return false;
        }
        
    }

    private void Entry_EnterButton_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Entry_EnterButton_LabelMouseClicked

        // quality 
        QualityRadioBTN();

        // funtion to get imput values
        GetValues();

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

        // funtion to get imput values
        GetValues();
        
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
     
   
    
    private void Entry_WAS_TextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Entry_WAS_TextFieldKeyPressed
        // TODO add your handling code here:
        if (!(Entry_WAS_TextField.getText().length() <= 5)) {
            Entry_WAS_TextField.setEditable(false);
        } else {
            Entry_WAS_TextField.setEditable(true);
        }
        if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            Entry_WAS_TextField.setEditable(true);
        }
    }//GEN-LAST:event_Entry_WAS_TextFieldKeyPressed

    // Navigation to sell page from entry page
    private void Entry_NavSell_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Entry_NavSell_LabelMouseClicked
        
        if (!this.navToSell()){
            JOptionPane.showMessageDialog(null, "There is a problem in Navigation. Kindly close the software, reopen it and try again!");
        }
    }//GEN-LAST:event_Entry_NavSell_LabelMouseClicked

    //KeyPressed Event for Quantity Text Field to enter only numbers
    private void sell_qtyInput_textFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sell_qtyInput_textFieldKeyPressed

        //gets the key press
        char c = evt.getKeyChar();

        //Characters not allowed
        if(Character.isLetter(c)){
            //Can't type as it is a character
            sell_qtyInput_textField.setEditable(false);
            //Error Message
            JOptionPane.showMessageDialog(null, "Please Enter Number Only");
        }

        //Number only can be entered
        else{
            sell_qtyInput_textField.setEditable(true);
        }

    }//GEN-LAST:event_sell_qtyInput_textFieldKeyPressed

    //Fetches the Barcode Details after the action is performed
    private void sell_barcodeInput_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_barcodeInput_textFieldActionPerformed

        try {

            //Fetching Operation
            Barcode f = new Barcode();
            rs = f.find(sell_barcodeInput_textField.getText());

            //Displays the barcode details
            if(rs.next()){
                sell_chaseNoDetail_label.setText(rs.getString("chase_no"));
                sell_ornamentNameDetail_label.setText(rs.getString("ornament_name"));
                sell_wtDetail_label.setText((rs.getString("weight"))+" g");
                sell_wasDetail_label.setText((rs.getString("wastage"))+" %");
                sell_mcDetail_label.setText((rs.getString("making_charge"))+" /G");
            }

            //Incorrect barcode
            else{
                JOptionPane.showMessageDialog(null, "Barcode is incorrect, Please do check it");
            }

        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Re-Scan or Re-Enter the Barcode");
        }

    }//GEN-LAST:event_sell_barcodeInput_textFieldActionPerformed

    //Returns the entry
    private void sell_return_label1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_return_label1MouseClicked

        try{
            //Return Operations
            Return r = new Return();
            rs = r.find(sell_barcodeInput_textField.getText());

            //Resets the page once it returns the entry
            sell_barcodeInput_textField.setText("");
            sell_qtyInput_textField.setText("");
            sell_chaseNoDetail_label.setText("");
            sell_ornamentNameDetail_label.setText("");
            sell_wtDetail_label.setText("");
            sell_wasDetail_label.setText("");
            sell_mcDetail_label.setText("");

            sell_verify_checkbox.setSelected(false);
            sell_confirm_label.setEnabled(false);
            sell_return_label1.setEnabled(false);

        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try again");
        }
    }//GEN-LAST:event_sell_return_label1MouseClicked

    //Confirms the entry
    private void sell_confirm_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_confirm_labelMouseClicked

        try{
            //Confirmation Oprations
            Confirm c = new Confirm();
            rs = c.find(sell_barcodeInput_textField.getText());

            //Resets the page once it confirms the entry
            sell_barcodeInput_textField.setText("");
            sell_qtyInput_textField.setText("");
            sell_chaseNoDetail_label.setText("");
            sell_ornamentNameDetail_label.setText("");
            sell_wtDetail_label.setText("");
            sell_wasDetail_label.setText("");
            sell_mcDetail_label.setText("");

            sell_verify_checkbox.setSelected(false);
            sell_confirm_label.setEnabled(false);
            sell_return_label1.setEnabled(false);

        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try again");
        }
    }//GEN-LAST:event_sell_confirm_labelMouseClicked

    //Refreshes the Page 
    private void sell_refresh_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_refresh_labelMouseClicked

        //Resets the Text Fields and Labels
        sell_barcodeInput_textField.setText("");
        sell_qtyInput_textField.setText("");
        sell_chaseNoDetail_label.setText("");
        sell_ornamentNameDetail_label.setText("");
        sell_wtDetail_label.setText("");
        sell_wasDetail_label.setText("");
        sell_mcDetail_label.setText("");

        //Unchecks the checkbox and disables the confirm, Return Labels
        sell_verify_checkbox.setSelected(false);
        sell_confirm_label.setEnabled(false);
        sell_return_label1.setEnabled(false);

    }//GEN-LAST:event_sell_refresh_labelMouseClicked

    // Navigation to entry page from sell page
    private void sell_entryNavigation_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_entryNavigation_labelMouseClicked

        if (!this.navToEntry()){
            JOptionPane.showMessageDialog(null, "There is a problem in Navigation. Kindly close the software, reopen it and try again!");
        }
    }//GEN-LAST:event_sell_entryNavigation_labelMouseClicked

    private void sell_verify_checkboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_verify_checkboxActionPerformed
     //If the check box is checked in
      if(sell_verify_checkbox.isSelected()){
        
        //gets quatity inut data
        String input = sell_qtyInput_textField.getText();
       
        //checks whether the details are filled
        if(input.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter the Quantity needed");
            sell_confirm_label.setEnabled(false);
            sell_return_label1.setEnabled(false);
            sell_verify_checkbox.setSelected(false);     
        }
        
        //checks whether the input is correct
        else{
            QuantityVerification qv = new QuantityVerification();
            rs = qv.find(sell_barcodeInput_textField.getText());
        }
        
      }
      
      //If the check box is checked out
      else{    
        sell_confirm_label.setEnabled(false);
        sell_return_label1.setEnabled(false);    
      }
    }//GEN-LAST:event_sell_verify_checkboxActionPerformed

    //Barcode Fetching Operation
    public class Barcode{
        
       //Method to fetch the data
       public ResultSet find(String s){
           try{
            Class.forName("com.mysql.cj.jdbc.Driver");     
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC","root","");
            st = con.prepareStatement("SELECT chase_no, ornament_type, ornament_name, quality, making_charge, weight, wastage, buy, barcode, status, quantity FROM balance WHERE barcode = ?");
            st.setString(1,s);
            rs = st.executeQuery();
           }catch(Exception ex){
              JOptionPane.showMessageDialog(null, "Some Errors, Please do try again");
           }
           return rs;
       }
       
   }
    
   
   //Confirmation Operation
   public class Confirm{

       //Method to Confirm the Entry
       public ResultSet find(String s){
           
          //Fetching Operation
          Barcode f = new Barcode();
          rs = f.find(sell_barcodeInput_textField.getText());
           
           try{
               int Quantity;
               int quantity=Integer.parseInt(sell_qtyInput_textField.getText());
               if(rs.next()){
             
                    String chaseNo = rs.getString("chase_no");
                    String ornament_type = rs.getString("ornament_type");
                    String ornament_name = rs.getString("ornament_name");
                    String quality = rs.getString("quality");
                    String making_charge = rs.getString("making_charge");
                    String weight = rs.getString("weight");
                    String wastage = rs.getString("wastage");
                    String buy = rs.getString("buy");
                    String barcode = rs.getString("barcode"); 
                    String status = rs.getString("status");
                    int qty = rs.getInt("quantity");
                                        
                    //Inserts into sold from the balance
                    st = con.prepareStatement("INSERT INTO sold (date, chase_no, ornament_type, ornament_name, quality, making_charge, weight, wastage, quantity, buy, barcode, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                    st.setString(1,dateDB);
                    st.setString(2,chaseNo );
                    st.setString(3,ornament_type);
                    st.setString(4,ornament_name);
                    st.setString(5,quality);
                    st.setString(6,making_charge);   
                    st.setString(7,weight);
                    st.setString(8,wastage);
                    st.setInt(9,quantity);
                    st.setString(10,buy);
                    st.setString(11,barcode);
                    st.setString(12,status);
                    st.executeUpdate();
                    
                    //Entered_quantity subtracted from DB_quantity
                    Quantity = qty - quantity;
                    
                    //If the decremented quantity (Entered_quantity subtracted from DB_quantity) is greater than zero then it updates the balance table quantity with the decremented value
                    if(Quantity > 0 ){
                        st = con.prepareStatement("UPDATE balance SET quantity=? WHERE barcode = ?");
                        st.setInt(1,Quantity);
                        st.setString(2,s);
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Confirmed Successfully");
                    }
                        
                        //If the decremented quantity (Entered_quantity subtracted from DB_quantity) is equal to zero then it deletes the data from balance table 
                    else{
                        st = con.prepareStatement("DELETE FROM balance WHERE barcode =?");
                        st.setString(1,s);
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Confirmed Successfully");

                    }
               }
               
           }
           catch(Exception ex){
               JOptionPane.showMessageDialog(null,"Some error in confirming the item");
           }     
          return rs;     
       }      
   }
   
   //Return Operation
   public class Return{

       //Method to Return the Entry
       public ResultSet find(String s){
           
          //Fetching Operation
          Barcode f = new Barcode();
          rs = f.find(sell_barcodeInput_textField.getText());
           
           try{
               int Quantity;
               int quantity=Integer.parseInt(sell_qtyInput_textField.getText());
               if(rs.next()){
             
                    String chaseNo = rs.getString("chase_no");
                    String ornament_type = rs.getString("ornament_type");
                    String ornament_name = rs.getString("ornament_name");
                    String quality = rs.getString("quality");
                    String making_charge = rs.getString("making_charge");
                    String weight = rs.getString("weight");
                    String wastage = rs.getString("wastage");
                    String buy = rs.getString("buy");
                    String barcode = rs.getString("barcode"); 
                    String status = rs.getString("status");
                    int qty = rs.getInt("quantity");
                                        
                    //Inserts into return_table from the balance
                    st = con.prepareStatement("INSERT INTO return_table (date, chase_no, ornament_type, ornament_name, quality, making_charge, weight, wastage, quantity, buy, barcode, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                    st.setString(1,dateDB);
                    st.setString(2,chaseNo );
                    st.setString(3,ornament_type);
                    st.setString(4,ornament_name);
                    st.setString(5,quality);
                    st.setString(6,making_charge);   
                    st.setString(7,weight);
                    st.setString(8,wastage);
                    st.setInt(9,quantity);
                    st.setString(10,buy);
                    st.setString(11,barcode);
                    st.setString(12,status);
                    st.executeUpdate();
                    
                    //Entered_quantity subtracted from DB_quantity
                    Quantity = qty - quantity;
                    
                    //If the decremented quantity (Entered_quantity subtracted from DB_quantity) is greater than zero then it updates the balance table quantity with the decremented value
                    if(Quantity > 0 ){
                        st = con.prepareStatement("UPDATE balance SET quantity=? WHERE barcode = ?");
                        st.setInt(1,Quantity);
                        st.setString(2,s);
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Returned Successfully");
                    }
                        
                        //If the decremented quantity (Entered_quantity subtracted from DB_quantity) is equal to zero then it deletes the data from balance table 
                    else{
                        st = con.prepareStatement("DELETE FROM balance WHERE barcode =?");
                        st.setString(1,s);
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Returned Successfully");

                    }
               }
               
           }
           catch(Exception ex){
               JOptionPane.showMessageDialog(null,ex.getMessage());
           }     
          return rs;     
       }      
   }
   
   //Quantity Verification
   public class QuantityVerification{
       
       //Method to verify the entered Quantity in the Quantity input
        public ResultSet find(String s){
                    
                    //Verification
                    try{
                    int quantity=Integer.parseInt(sell_qtyInput_textField.getText()); 
                    Class.forName("com.mysql.cj.jdbc.Driver");     
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC","root","");
                    st = con.prepareStatement("SELECT quantity FROM balance WHERE barcode = ?");
                    st.setString(1,s);
                    rs = st.executeQuery();
                    while(rs.next()){
                        int qty = rs.getInt("quantity");
                    
                        //If the Entered Quantity input is greater than the DB quantity then a error message popups
                        if(quantity > qty ){
                            JOptionPane.showMessageDialog(null, "Please do check the available quantity");
                            sell_confirm_label.setEnabled(false);
                            sell_return_label1.setEnabled(false);
                            sell_verify_checkbox.setSelected(false);
                        }
                        
                        //If the quantity input entered is available
                        else{
                            //Verifies and enables the Confirm, Return Labels
                            sell_confirm_label.setEnabled(true);
                            sell_return_label1.setEnabled(true); 
                        }
                    }
                 }
                 catch(Exception ex){
                      JOptionPane.showMessageDialog(null, "Try again");
                   }
            return rs;
        }
   }
      
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
    private javax.swing.JPanel sellPage;
    private javax.swing.JPanel sell_ShopNameA_Label1;
    private javax.swing.JLabel sell_ShopnameA_Label2;
    private javax.swing.JLabel sell_ShopnameJ2_Label3;
    private javax.swing.JLabel sell_ShopnameJ_Label1;
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
    private javax.swing.JCheckBox sell_verify_checkbox;
    private java.awt.Label sell_viewNavigation_label;
    private javax.swing.JLabel sell_wasDetail_label;
    private javax.swing.JLabel sell_was_label;
    private javax.swing.JLabel sell_wtDetail_label;
    private javax.swing.JLabel sell_wt_label;
    // End of variables declaration//GEN-END:variables
    
}
