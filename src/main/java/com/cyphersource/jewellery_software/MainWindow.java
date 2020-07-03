/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyphersource.jewellery_software;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
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
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MainWindow extends javax.swing.JFrame {

    Connection con;
    CardLayout mainLayout;
    PreparedStatement ps = null, st = null, pat = null;
    ResultSet rs = null;
    String sql;
    Statement stmt = null;
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

    String default_from_date;
    String default_to_date;

    //Getting selectedItem from dropdown
    String view_overall_ornament_type_data;
    String view_sold_ornament_type_data = "Select the Ornament";
    String view_balance_ornament_type_data;
    String view_return_ornament_type_data;

    //Date obtained after formating
    String view_overall_from_date;
    String view_overall_to_date;

    String view_sold_from_date;
    String view_sold_to_date;

    String view_balance_from_date;
    String view_balance_to_date;

    String view_return_from_date;
    String view_return_to_date;

    String[][] view_sold_raw_data, view_balance_raw_data;

    //  Image Icon instance
    private ImageIcon imageIcon, returnIcon;

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

        initIcons();

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

        setVisible(true);
        setLocationRelativeTo(null);

        // Initialize Image Icon
        // view page print image: view_print.jpg 
        imageIcon = new ImageIcon(defaultPath + "\\view_print.jpg");

        // view page print image: view_return.jpg 
        returnIcon = new ImageIcon(defaultPath + "\\view_return.png");

//      Override Cell render of Image column
        view_soldTable_table.getColumn("RT").setCellRenderer(new view_myCellRenderer());
        view_balanceTable_table.getColumn("RP").setCellRenderer(new view_myCellRenderer());

        //method for displaying dropdown elements from database
        view_overall_dropdown_display();
        view_sold_dropdown_display();
        view_balance_dropdown_display();
        view_return_dropdown_display();

        //method for default display of from and to date
        view_default_date();

        //method for default display of contents from database
        view_overall_default_display();
        view_sold_default_display();
        view_balance_default_display();
        view_return_default_display();

        //To change the color of the vertical gridlines of tables
        view_overallTable_table.setShowVerticalLines(true);
        view_overallTable_table.setGridColor(Color.LIGHT_GRAY);

        view_soldTable_table.setShowVerticalLines(true);
        view_soldTable_table.setGridColor(Color.LIGHT_GRAY);

        view_balanceTable_table.setShowVerticalLines(true);
        view_balanceTable_table.setGridColor(Color.LIGHT_GRAY);

        view_returnTable_table.setShowVerticalLines(true);
        view_returnTable_table.setGridColor(Color.LIGHT_GRAY);

        // T0 scroll the table contents without using scrollbar for tables
        view_overall_tablearea_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0, 0));
        view_overall_tablearea_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0, 0));

        view_sold_tablearea_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0, 0));
        view_sold_tablearea_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0, 0));

        view_balance_tablearea_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0, 0));
        view_balance_tablearea_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0, 0));

        view_return_tablearea_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0, 0));
        view_return_tablearea_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0, 0));

        //For increasing the speed of the scrollpane
        view_overall_tablearea_scrollpane.getVerticalScrollBar().setUnitIncrement(100);
        view_sold_tablearea_scrollpane.getVerticalScrollBar().setUnitIncrement(100);
        view_balance_tablearea_scrollpane.getVerticalScrollBar().setUnitIncrement(100);
        view_return_tablearea_scrollpane.getVerticalScrollBar().setUnitIncrement(100);

        //Styling table header
        JTableHeader Theader = view_overallTable_table.getTableHeader();
        JTableHeader Theaderone = view_soldTable_table.getTableHeader();
        JTableHeader Theadertwo = view_balanceTable_table.getTableHeader();
        JTableHeader Theaderthree = view_returnTable_table.getTableHeader();

        //Tableheader font styling
        Theader.setFont(new Font("Ubuntu", Font.BOLD, 18));
        Theaderone.setFont(new Font("Ubuntu", Font.BOLD, 18));
        Theadertwo.setFont(new Font("Ubuntu", Font.BOLD, 18));
        Theaderthree.setFont(new Font("Ubuntu", Font.BOLD, 18));

        //tableheader text color
        Theader.setForeground(Color.black);
        Theaderone.setForeground(Color.black);
        Theadertwo.setForeground(Color.black);
        Theaderthree.setForeground(Color.black);

        //Tableheader bg color,header thickness, dimension ( ht& wth ), text alignment
        Theader.setDefaultRenderer(new view_headerColor());
        Theader.setPreferredSize(new Dimension(50, 50));
        ((DefaultTableCellRenderer) Theader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        Theaderone.setDefaultRenderer(new view_headerColor());
        Theaderone.setPreferredSize(new Dimension(50, 50));
        ((DefaultTableCellRenderer) Theaderone.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        Theadertwo.setDefaultRenderer(new view_headerColor());
        Theadertwo.setPreferredSize(new Dimension(50, 50));
        ((DefaultTableCellRenderer) Theadertwo.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        Theaderthree.setDefaultRenderer(new view_headerColor());
        Theaderthree.setPreferredSize(new Dimension(50, 50));
        ((DefaultTableCellRenderer) Theaderthree.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // For setting all the values in  the column to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        //For table 1
        for (int x = 0; x < 10; x++) {
            view_overallTable_table.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }
        //For table 2 & 3
        for (int x = 0; x < 7; x++) {
            view_soldTable_table.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
            view_balanceTable_table.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }
        //For table 4
        for (int x = 0; x < 7; x++) {
            view_returnTable_table.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }

        // Balance table - Getting the values of the selected rows for printing
        view_balanceTable_table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int col = view_balanceTable_table.columnAtPoint(evt.getPoint());
                if (col == 7) {
                    int setRow = view_balanceTable_table.getSelectedRow();
                    int view_serial_no = Integer.parseInt(view_balanceTable_table.getModel().getValueAt(setRow, 0).toString());
                    
                    String[] printData = view_balance_raw_data[view_serial_no - 1];
                    String[] demo = new String[] {printData[6], printData[8], printData[7], printData[5], printData[2], printData[3]};
                    System.out.println(Arrays.toString(demo));
                    PrintBarcode(printData[6], printData[8], printData[7], printData[5], printData[2], printData[3]);
                }

            }
        });

        // Sold table - Getting the values of the selected rows for printing
        view_soldTable_table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = view_soldTable_table.columnAtPoint(evt.getPoint());
                if (column == 7) {
                    int setRow = view_soldTable_table.getSelectedRow();
                    int view_serial_no = Integer.parseInt(view_soldTable_table.getModel().getValueAt(setRow, 0).toString());

                    System.out.println(Arrays.toString(view_sold_raw_data[view_serial_no - 1]));
                    view_sold_return(view_serial_no);

                }
            }
        });
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
    private void GetValues() {
        mc = Entry_MC_TextField.getText();
        wt = Entry_WT_TextField.getText();
        was = Entry_WAS_TextField.getText();
        qty = Entry_QTY_TextField.getText();
        buy = Entry_BUY_TextField.getText();
        barcode = chase_no;
    }

    private void PrintBarcode() {

        TSCPrint print = new TSCPrint(mc, was, wt, quality, chase_no, ornament_name);
        if (print.print_barcode()) {
            JOptionPane.showMessageDialog(null, "Please check your printer for the printed tag!");
        } else {
            JOptionPane.showMessageDialog(null, "Oops! Sorry, Something is wrong. Please check your printer connection");
        }

    }
    
    private void PrintBarcode(String mc, String was, String wt, String quality, String chase_no, String ornament_name) {

        TSCPrint print = new TSCPrint(mc, was, wt, quality, chase_no, ornament_name);
        if (print.print_barcode()) {
            JOptionPane.showMessageDialog(null, "Please check your printer for the printed tag!");
        } else {
            JOptionPane.showMessageDialog(null, "Oops! Sorry, Something is wrong. Please check your printer connection");
        }

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
        sell_entryNavigation_label = new java.awt.Label();
        sell_viewNavigation_label = new java.awt.Label();
        sell_sellNavigation_label = new java.awt.Label();
        sell_ShopnameJ_Label1 = new javax.swing.JLabel();
        sell_ShopnameA_Label2 = new javax.swing.JLabel();
        sell_ShopnameJ2_Label3 = new javax.swing.JLabel();
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
        viewPage = new javax.swing.JPanel();
        view_total_panel = new javax.swing.JPanel();
        view_areaOne_panel = new javax.swing.JPanel();
        view_entry_label = new javax.swing.JLabel();
        view_view_label = new javax.swing.JLabel();
        view_sell_label = new javax.swing.JLabel();
        Entry_ShopnameJ_Label1 = new javax.swing.JLabel();
        Entry_ShopNameA_Label1 = new javax.swing.JLabel();
        Entry_ShopnameJ2_Label1 = new javax.swing.JLabel();
        view_areaTwo_panel = new javax.swing.JPanel();
        view_contOne_panel = new javax.swing.JPanel();
        view_overall_panel = new javax.swing.JPanel();
        view_overall_label = new javax.swing.JLabel();
        view_sold_panel = new javax.swing.JPanel();
        view_sold_label = new javax.swing.JLabel();
        view_balance_panel = new javax.swing.JPanel();
        view_balance_label = new javax.swing.JLabel();
        view_return_panel = new javax.swing.JPanel();
        view_return_label = new javax.swing.JLabel();
        view_contTwo_panel = new javax.swing.JPanel();
        view_overallCont_panel = new javax.swing.JPanel();
        view_overall_totWt_label = new javax.swing.JLabel();
        view_overall_totWtInp_label = new javax.swing.JLabel();
        view_overall_totItem_label = new javax.swing.JLabel();
        view_overall_totItemInp_label = new javax.swing.JLabel();
        view_overall_selOrnament_dropdown = new javax.swing.JComboBox<>();
        view_overall_itemColon_label = new javax.swing.JLabel();
        view_overall_wtColon_label = new javax.swing.JLabel();
        view_overall_tablearea_scrollpane = new javax.swing.JScrollPane();
        view_overallTable_table = new javax.swing.JTable();
        view_overall_datelimit_panel = new javax.swing.JPanel();
        view_overall_from_datechooser = new com.toedter.calendar.JDateChooser();
        view_overall_from_label = new javax.swing.JLabel();
        view_overall_to_datechooser = new com.toedter.calendar.JDateChooser();
        view_overall_to_label = new javax.swing.JLabel();
        view_overall_datelimitIcon_label = new javax.swing.JLabel();
        view_soldCont_panel = new javax.swing.JPanel();
        view_sold_totWt_label = new javax.swing.JLabel();
        view_sold_totWtInp_label = new javax.swing.JLabel();
        view_sold_totItem_label = new javax.swing.JLabel();
        view_sold_totItemInp_label = new javax.swing.JLabel();
        view_sold_selOrnament_dropdown = new javax.swing.JComboBox<>();
        view_sold_itemColon_label = new javax.swing.JLabel();
        view_sold_wtColon_label = new javax.swing.JLabel();
        view_sold_tablearea_scrollpane = new javax.swing.JScrollPane();
        view_soldTable_table = new javax.swing.JTable();
        view_sold_datelimit_panel = new javax.swing.JPanel();
        view_sold_from_datechooser = new com.toedter.calendar.JDateChooser();
        view_sold_from_label = new javax.swing.JLabel();
        view_sold_to_datechooser = new com.toedter.calendar.JDateChooser();
        view_sold_to_label = new javax.swing.JLabel();
        view_sold_datelimitIcon_label = new javax.swing.JLabel();
        view_balanceCont_panel = new javax.swing.JPanel();
        view_balance_totWt_label = new javax.swing.JLabel();
        view_balance_totWtInp_label = new javax.swing.JLabel();
        view_balance_totItem_label = new javax.swing.JLabel();
        view_balance_totItemInp_label = new javax.swing.JLabel();
        view_balance_selOrnament_dropdown = new javax.swing.JComboBox<>();
        view_balance_itemColon_label = new javax.swing.JLabel();
        view_balance_wtColon_label = new javax.swing.JLabel();
        view_balance_tablearea_scrollpane = new javax.swing.JScrollPane();
        view_balanceTable_table = new javax.swing.JTable();
        view_balance_datelimit_panel = new javax.swing.JPanel();
        view_balance_from_datechooser = new com.toedter.calendar.JDateChooser();
        view_balance_from_label = new javax.swing.JLabel();
        view_balance_to_datechooser = new com.toedter.calendar.JDateChooser();
        view_balance_to_label = new javax.swing.JLabel();
        view_balance_datelimitIcon_label = new javax.swing.JLabel();
        view_returnCont_panel = new javax.swing.JPanel();
        view_return_totWt_label = new javax.swing.JLabel();
        view_return_totWtInp_label = new javax.swing.JLabel();
        view_return_totItem_label = new javax.swing.JLabel();
        view_return_totItemInp_label = new javax.swing.JLabel();
        view_return_selOrnament_dropdown = new javax.swing.JComboBox<>();
        view_return_itemColon_label = new javax.swing.JLabel();
        view_return_wtColon_label = new javax.swing.JLabel();
        view_return_tablearea_scrollpane = new javax.swing.JScrollPane();
        view_returnTable_table = new javax.swing.JTable();
        view_return_datelimit_panel = new javax.swing.JPanel();
        view_return_from_datechooser = new com.toedter.calendar.JDateChooser();
        view_return_from_label = new javax.swing.JLabel();
        view_return_to_datechooser = new com.toedter.calendar.JDateChooser();
        view_return_to_label = new javax.swing.JLabel();
        view_return_datelimitIcon_label = new javax.swing.JLabel();

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
        Entry_NavView_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Entry_NavView_LabelMouseClicked(evt);
            }
        });

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
        Entry_InputFields_Panel.add(Entry_WAS_TextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 620, 300, -1));

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
                .addContainerGap(107, Short.MAX_VALUE))
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
            .addComponent(EntryPage_jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        entryPageLayout.setVerticalGroup(
            entryPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EntryPage_jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
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

        sell_entryNavigation_label.setAlignment(java.awt.Label.CENTER);
        sell_entryNavigation_label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        sell_entryNavigation_label.setForeground(new java.awt.Color(98, 98, 98));
        sell_entryNavigation_label.setText("Entry");
        sell_entryNavigation_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_entryNavigation_labelMouseClicked(evt);
            }
        });

        sell_viewNavigation_label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        sell_viewNavigation_label.setForeground(new java.awt.Color(98, 98, 98));
        sell_viewNavigation_label.setText("View");
        sell_viewNavigation_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_viewNavigation_labelMouseClicked(evt);
            }
        });

        sell_sellNavigation_label.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        sell_sellNavigation_label.setText("Sell");

        sell_ShopnameJ_Label1.setBackground(new java.awt.Color(250, 250, 250));
        sell_ShopnameJ_Label1.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        sell_ShopnameJ_Label1.setText("J");

        sell_ShopnameA_Label2.setBackground(new java.awt.Color(255, 255, 255));
        sell_ShopnameA_Label2.setFont(new java.awt.Font("Ubuntu", 1, 38)); // NOI18N
        sell_ShopnameA_Label2.setText("A");

        sell_ShopnameJ2_Label3.setBackground(new java.awt.Color(255, 255, 255));
        sell_ShopnameJ2_Label3.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        sell_ShopnameJ2_Label3.setText("J");

        sell_Welcome_label.setAlignment(java.awt.Label.CENTER);
        sell_Welcome_label.setFont(new java.awt.Font("Ubuntu", 0, 25)); // NOI18N
        sell_Welcome_label.setForeground(new java.awt.Color(51, 51, 51));
        sell_Welcome_label.setText("Welcome,  Please  Scan  OR  Code.");

        //sell page image : sell_refresh.png
        sell_refresh_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_refresh_labelMouseClicked(evt);
            }
        });

        //sell page image : sell_confirm.png
        sell_confirm_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_confirm_labelMouseClicked(evt);
            }
        });

        sell_return_label1.setBackground(java.awt.Color.white);
        //sell page image : sell_return.png
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
        sell_barcodeInput_panel.add(sell_barcodeInput_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 70));

        sell_innerWindow_panel.setBackground(new java.awt.Color(251, 251, 251));
        sell_innerWindow_panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, new java.awt.Color(245, 245, 245), java.awt.Color.gray, new java.awt.Color(230, 230, 230)));
        sell_innerWindow_panel.setForeground(new java.awt.Color(130, 130, 130));

        sell_qty_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_qty_label.setForeground(new java.awt.Color(51, 51, 51));
        sell_qty_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_qty_label.setText("QTY");

        sell_chaseNo_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_chaseNo_label.setForeground(new java.awt.Color(51, 51, 51));
        sell_chaseNo_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_chaseNo_label.setText("Chase No");

        sell_ornamentName_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_ornamentName_label.setForeground(new java.awt.Color(51, 51, 51));
        sell_ornamentName_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_ornamentName_label.setText("Ornament Name");

        sell_wt_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_wt_label.setForeground(new java.awt.Color(51, 51, 51));
        sell_wt_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_wt_label.setText("WT");

        sell_was_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_was_label.setForeground(new java.awt.Color(51, 51, 51));
        sell_was_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sell_was_label.setText("WAS");

        sell_mc_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        sell_mc_label.setForeground(new java.awt.Color(51, 51, 51));
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
        sell_verify_checkbox.setForeground(new java.awt.Color(102, 102, 102));
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
                        .addContainerGap(626, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
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

        viewPage.setBackground(new java.awt.Color(255, 255, 255));
        viewPage.setAutoscrolls(true);
        viewPage.setPreferredSize(new java.awt.Dimension(1280, 900));

        view_total_panel.setBackground(java.awt.Color.white);
        view_total_panel.setLayout(new java.awt.BorderLayout());

        view_areaOne_panel.setBackground(java.awt.Color.white);

        view_entry_label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        view_entry_label.setForeground(new java.awt.Color(98, 98, 98));
        view_entry_label.setText("Entry");
        view_entry_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_entry_labelMouseClicked(evt);
            }
        });

        view_view_label.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        view_view_label.setText("View");

        view_sell_label.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        view_sell_label.setForeground(new java.awt.Color(98, 98, 98));
        view_sell_label.setText("Sell");
        view_sell_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_sell_labelMouseClicked(evt);
            }
        });

        Entry_ShopnameJ_Label1.setBackground(new java.awt.Color(250, 250, 250));
        Entry_ShopnameJ_Label1.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        Entry_ShopnameJ_Label1.setText("J");

        Entry_ShopNameA_Label1.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ShopNameA_Label1.setFont(new java.awt.Font("Ubuntu", 1, 38)); // NOI18N
        Entry_ShopNameA_Label1.setText("A");

        Entry_ShopnameJ2_Label1.setBackground(new java.awt.Color(255, 255, 255));
        Entry_ShopnameJ2_Label1.setFont(new java.awt.Font("Ubuntu", 1, 64)); // NOI18N
        Entry_ShopnameJ2_Label1.setText("J");

        javax.swing.GroupLayout view_areaOne_panelLayout = new javax.swing.GroupLayout(view_areaOne_panel);
        view_areaOne_panel.setLayout(view_areaOne_panelLayout);
        view_areaOne_panelLayout.setHorizontalGroup(
            view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_areaOne_panelLayout.createSequentialGroup()
                .addContainerGap(593, Short.MAX_VALUE)
                .addComponent(Entry_ShopnameJ_Label1)
                .addGap(0, 0, 0)
                .addComponent(Entry_ShopNameA_Label1)
                .addGap(0, 0, 0)
                .addComponent(Entry_ShopnameJ2_Label1)
                .addGap(193, 193, 193)
                .addComponent(view_entry_label)
                .addGap(51, 51, 51)
                .addComponent(view_view_label)
                .addGap(55, 55, 55)
                .addComponent(view_sell_label)
                .addGap(76, 76, 76))
        );
        view_areaOne_panelLayout.setVerticalGroup(
            view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_areaOne_panelLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_areaOne_panelLayout.createSequentialGroup()
                        .addGroup(view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Entry_ShopNameA_Label1)
                            .addComponent(Entry_ShopnameJ2_Label1)
                            .addComponent(Entry_ShopnameJ_Label1))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_areaOne_panelLayout.createSequentialGroup()
                        .addGroup(view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_entry_label)
                            .addComponent(view_view_label)
                            .addComponent(view_sell_label))
                        .addGap(31, 31, 31))))
        );

        view_total_panel.add(view_areaOne_panel, java.awt.BorderLayout.PAGE_START);

        view_areaTwo_panel.setBackground(java.awt.Color.white);

        view_contOne_panel.setBackground(new java.awt.Color(247, 247, 247));
        view_contOne_panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.lightGray, java.awt.Color.lightGray));

        view_overall_panel.setBackground(new java.awt.Color(247, 247, 247));
        view_overall_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_mouseclicked(evt);
            }
        });

        view_overall_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        view_overall_label.setText("Overall");

        javax.swing.GroupLayout view_overall_panelLayout = new javax.swing.GroupLayout(view_overall_panel);
        view_overall_panel.setLayout(view_overall_panelLayout);
        view_overall_panelLayout.setHorizontalGroup(
            view_overall_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_overall_panelLayout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(view_overall_label, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        view_overall_panelLayout.setVerticalGroup(
            view_overall_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_overall_panelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(view_overall_label, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        view_sold_panel.setBackground(new java.awt.Color(247, 247, 247));
        view_sold_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_mouseclicked(evt);
            }
        });

        view_sold_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        view_sold_label.setForeground(new java.awt.Color(158, 154, 144));
        view_sold_label.setText("Sold");

        javax.swing.GroupLayout view_sold_panelLayout = new javax.swing.GroupLayout(view_sold_panel);
        view_sold_panel.setLayout(view_sold_panelLayout);
        view_sold_panelLayout.setHorizontalGroup(
            view_sold_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_sold_panelLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(view_sold_label, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        view_sold_panelLayout.setVerticalGroup(
            view_sold_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_sold_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(view_sold_label)
                .addGap(21, 21, 21))
        );

        view_balance_panel.setBackground(new java.awt.Color(247, 247, 247));
        view_balance_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_mouseclicked(evt);
            }
        });

        view_balance_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        view_balance_label.setForeground(new java.awt.Color(158, 154, 154));
        view_balance_label.setText("Balance");

        javax.swing.GroupLayout view_balance_panelLayout = new javax.swing.GroupLayout(view_balance_panel);
        view_balance_panel.setLayout(view_balance_panelLayout);
        view_balance_panelLayout.setHorizontalGroup(
            view_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_balance_panelLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(view_balance_label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        view_balance_panelLayout.setVerticalGroup(
            view_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_balance_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(view_balance_label)
                .addGap(22, 22, 22))
        );

        view_return_panel.setBackground(new java.awt.Color(247, 247, 247));
        view_return_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view_mouseclicked(evt);
            }
        });

        view_return_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        view_return_label.setForeground(new java.awt.Color(158, 154, 154));
        view_return_label.setText("Return");

        javax.swing.GroupLayout view_return_panelLayout = new javax.swing.GroupLayout(view_return_panel);
        view_return_panel.setLayout(view_return_panelLayout);
        view_return_panelLayout.setHorizontalGroup(
            view_return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_return_panelLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(view_return_label, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        view_return_panelLayout.setVerticalGroup(
            view_return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_return_panelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(view_return_label)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout view_contOne_panelLayout = new javax.swing.GroupLayout(view_contOne_panel);
        view_contOne_panel.setLayout(view_contOne_panelLayout);
        view_contOne_panelLayout.setHorizontalGroup(
            view_contOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_contOne_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(view_overall_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88)
                .addComponent(view_sold_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(view_balance_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(view_return_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
        view_contOne_panelLayout.setVerticalGroup(
            view_contOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view_overall_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(view_sold_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(view_balance_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(view_return_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        view_contTwo_panel.setBackground(java.awt.Color.white);
        view_contTwo_panel.setLayout(new java.awt.CardLayout());

        view_overallCont_panel.setBackground(java.awt.Color.white);

        view_overall_totWt_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_totWt_label.setForeground(java.awt.Color.gray);
        view_overall_totWt_label.setText("Total Weight    ");

        view_overall_totWtInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_totWtInp_label.setName(""); // NOI18N

        view_overall_totItem_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_totItem_label.setForeground(java.awt.Color.gray);
        view_overall_totItem_label.setText("Total no of Items");

        view_overall_totItemInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_overall_selOrnament_dropdown.setBackground(new java.awt.Color(247, 247, 247));
        view_overall_selOrnament_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_overall_selOrnament_dropdown.setForeground(java.awt.Color.gray);
        view_overall_selOrnament_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_overall_selOrnament_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_overall_selOrnament_ActionPerformed(evt);
            }
        });

        view_overall_itemColon_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_itemColon_label.setText(":");

        view_overall_wtColon_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_wtColon_label.setText(":");

        view_overallTable_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        view_overallTable_table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        view_overallTable_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Date", "Chase No", "Ornament Name", "WT", "WAS", "MC", "QTY", "QLTY", "BUY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_overallTable_table.setRequestFocusEnabled(false);
        view_overallTable_table.setRowHeight(48);
        view_overallTable_table.setRowMargin(0);
        view_overallTable_table.setShowHorizontalLines(false);
        view_overallTable_table.getTableHeader().setReorderingAllowed(false);
        view_overall_tablearea_scrollpane.setViewportView(view_overallTable_table);
        view_overallTable_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (view_overallTable_table.getColumnModel().getColumnCount() > 0) {
            view_overallTable_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            view_overallTable_table.getColumnModel().getColumn(1).setPreferredWidth(120);
            view_overallTable_table.getColumnModel().getColumn(2).setPreferredWidth(130);
            view_overallTable_table.getColumnModel().getColumn(3).setPreferredWidth(195);
            view_overallTable_table.getColumnModel().getColumn(4).setPreferredWidth(73);
            view_overallTable_table.getColumnModel().getColumn(5).setPreferredWidth(73);
            view_overallTable_table.getColumnModel().getColumn(6).setPreferredWidth(130);
            view_overallTable_table.getColumnModel().getColumn(7).setPreferredWidth(45);
            view_overallTable_table.getColumnModel().getColumn(8).setPreferredWidth(50);
        }

        view_overall_datelimit_panel.setBackground(java.awt.Color.white);
        view_overall_datelimit_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_overall_from_datechooser.setBackground(java.awt.Color.white);
        view_overall_from_datechooser.setDateFormatString("dd-MM-yyyy");
        view_overall_from_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_overall_from_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_overall_fromDate_propertyChange(evt);
            }
        });
        view_overall_datelimit_panel.add(view_overall_from_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        view_overall_from_label.setBackground(java.awt.Color.white);
        view_overall_from_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_from_label.setText("From");
        view_overall_datelimit_panel.add(view_overall_from_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 30));

        view_overall_to_datechooser.setBackground(java.awt.Color.white);
        view_overall_to_datechooser.setDateFormatString("dd-MM-yyyy");
        view_overall_to_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_overall_to_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_overall_todate_propertChange(evt);
            }
        });
        view_overall_datelimit_panel.add(view_overall_to_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_overall_to_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_to_label.setText("To");
        view_overall_datelimit_panel.add(view_overall_to_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        //view page images : view_datelimiticon.png
        view_overall_datelimit_panel.add(view_overall_datelimitIcon_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_overallCont_panelLayout = new javax.swing.GroupLayout(view_overallCont_panel);
        view_overallCont_panel.setLayout(view_overallCont_panelLayout);
        view_overallCont_panelLayout.setHorizontalGroup(
            view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(view_overall_totWt_label, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_overall_totItem_label, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(view_overall_wtColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_overall_itemColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_overall_totWtInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(view_overall_totItemInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36)
                        .addComponent(view_overall_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(view_overall_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addContainerGap(64, Short.MAX_VALUE)
                        .addComponent(view_overall_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        view_overallCont_panelLayout.setVerticalGroup(
            view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(view_overall_totItemInp_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_overallCont_panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(view_overall_totItem_label)
                                    .addComponent(view_overall_itemColon_label))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(view_overall_totWt_label)
                                .addComponent(view_overall_wtColon_label))
                            .addComponent(view_overall_totWtInp_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(view_overall_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(view_overall_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_overall_tablearea_scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addContainerGap())
        );

        view_contTwo_panel.add(view_overallCont_panel, "card2");

        view_soldCont_panel.setBackground(java.awt.Color.white);

        view_sold_totWt_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_sold_totWt_label.setForeground(java.awt.Color.gray);
        view_sold_totWt_label.setText("Total Weight    ");

        view_sold_totWtInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_sold_totWtInp_label.setName(""); // NOI18N

        view_sold_totItem_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_sold_totItem_label.setForeground(java.awt.Color.gray);
        view_sold_totItem_label.setText("Total no of Items");

        view_sold_totItemInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_sold_selOrnament_dropdown.setBackground(new java.awt.Color(247, 247, 247));
        view_sold_selOrnament_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_sold_selOrnament_dropdown.setForeground(java.awt.Color.gray);
        view_sold_selOrnament_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_sold_selOrnament_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_sold_selOrnament_ActionPerformed(evt);
            }
        });

        view_sold_itemColon_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_sold_itemColon_label.setText(":");

        view_sold_wtColon_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_sold_wtColon_label.setText(":");

        view_soldTable_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        view_soldTable_table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        view_soldTable_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Date", "Chase No", "Ornament Name", "WT", "QTY", "BARCODE", "RT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_soldTable_table.setColumnSelectionAllowed(true);
        view_soldTable_table.setRequestFocusEnabled(false);
        view_soldTable_table.setRowHeight(48);
        view_soldTable_table.setRowMargin(0);
        view_soldTable_table.setShowHorizontalLines(false);
        view_soldTable_table.getTableHeader().setReorderingAllowed(false);
        view_sold_tablearea_scrollpane.setViewportView(view_soldTable_table);
        view_soldTable_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (view_soldTable_table.getColumnModel().getColumnCount() > 0) {
            view_soldTable_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            view_soldTable_table.getColumnModel().getColumn(1).setPreferredWidth(120);
            view_soldTable_table.getColumnModel().getColumn(2).setPreferredWidth(130);
            view_soldTable_table.getColumnModel().getColumn(3).setPreferredWidth(195);
            view_soldTable_table.getColumnModel().getColumn(4).setPreferredWidth(73);
            view_soldTable_table.getColumnModel().getColumn(5).setPreferredWidth(73);
            view_soldTable_table.getColumnModel().getColumn(6).setPreferredWidth(130);
            view_soldTable_table.getColumnModel().getColumn(7).setPreferredWidth(45);
        }

        view_sold_datelimit_panel.setBackground(java.awt.Color.white);
        view_sold_datelimit_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_sold_from_datechooser.setBackground(java.awt.Color.white);
        view_sold_from_datechooser.setDateFormatString("dd-MM-yyyy");
        view_sold_from_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_sold_from_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_sold_fromDate_propertyChange(evt);
            }
        });
        view_sold_datelimit_panel.add(view_sold_from_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        view_sold_from_label.setBackground(java.awt.Color.white);
        view_sold_from_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_sold_from_label.setText("From");
        view_sold_datelimit_panel.add(view_sold_from_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 30));

        view_sold_to_datechooser.setBackground(java.awt.Color.white);
        view_sold_to_datechooser.setDateFormatString("dd-MM-yyyy");
        view_sold_to_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_sold_to_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_sold_todate_propertChange(evt);
            }
        });
        view_sold_datelimit_panel.add(view_sold_to_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_sold_to_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_sold_to_label.setText("To");
        view_sold_datelimit_panel.add(view_sold_to_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        //view page images : view_datelimiticon.png
        view_sold_datelimit_panel.add(view_sold_datelimitIcon_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_soldCont_panelLayout = new javax.swing.GroupLayout(view_soldCont_panel);
        view_soldCont_panel.setLayout(view_soldCont_panelLayout);
        view_soldCont_panelLayout.setHorizontalGroup(
            view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(view_sold_totWt_label, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_sold_totItem_label, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(view_sold_wtColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_sold_itemColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_sold_totWtInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(view_sold_totItemInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(34, 34, 34)
                        .addComponent(view_sold_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(view_sold_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addContainerGap(64, Short.MAX_VALUE)
                        .addComponent(view_sold_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        view_soldCont_panelLayout.setVerticalGroup(
            view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(view_sold_totItemInp_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_soldCont_panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(view_sold_totItem_label)
                                    .addComponent(view_sold_itemColon_label))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(view_sold_totWt_label)
                                .addComponent(view_sold_wtColon_label))
                            .addComponent(view_sold_totWtInp_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(view_sold_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(view_sold_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_sold_tablearea_scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addContainerGap())
        );

        view_contTwo_panel.add(view_soldCont_panel, "card2");

        view_balanceCont_panel.setBackground(java.awt.Color.white);

        view_balance_totWt_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_balance_totWt_label.setForeground(java.awt.Color.gray);
        view_balance_totWt_label.setText("Total Weight    ");

        view_balance_totWtInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_balance_totWtInp_label.setName(""); // NOI18N

        view_balance_totItem_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_balance_totItem_label.setForeground(java.awt.Color.gray);
        view_balance_totItem_label.setText("Total no of Items");

        view_balance_totItemInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_balance_selOrnament_dropdown.setBackground(new java.awt.Color(247, 247, 247));
        view_balance_selOrnament_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_balance_selOrnament_dropdown.setForeground(java.awt.Color.gray);
        view_balance_selOrnament_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_balance_selOrnament_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_balance_selOrnament_ActionPerformed(evt);
            }
        });

        view_balance_itemColon_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_balance_itemColon_label.setText(":");

        view_balance_wtColon_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_balance_wtColon_label.setText(":");

        view_balanceTable_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        view_balanceTable_table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        view_balanceTable_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Date", "Chase No", "Ornament Name", "WT", "QTY", "BARCODE", "RP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_balanceTable_table.setColumnSelectionAllowed(true);
        view_balanceTable_table.setRequestFocusEnabled(false);
        view_balanceTable_table.setRowHeight(48);
        view_balanceTable_table.setRowMargin(0);
        view_balanceTable_table.setShowHorizontalLines(false);
        view_balanceTable_table.getTableHeader().setReorderingAllowed(false);
        view_balance_tablearea_scrollpane.setViewportView(view_balanceTable_table);
        view_balanceTable_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (view_balanceTable_table.getColumnModel().getColumnCount() > 0) {
            view_balanceTable_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            view_balanceTable_table.getColumnModel().getColumn(1).setPreferredWidth(120);
            view_balanceTable_table.getColumnModel().getColumn(2).setPreferredWidth(130);
            view_balanceTable_table.getColumnModel().getColumn(3).setPreferredWidth(195);
            view_balanceTable_table.getColumnModel().getColumn(4).setPreferredWidth(73);
            view_balanceTable_table.getColumnModel().getColumn(5).setPreferredWidth(73);
            view_balanceTable_table.getColumnModel().getColumn(6).setPreferredWidth(130);
        }

        view_balance_datelimit_panel.setBackground(java.awt.Color.white);
        view_balance_datelimit_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_balance_from_datechooser.setBackground(java.awt.Color.white);
        view_balance_from_datechooser.setDateFormatString("dd-MM-yyyy");
        view_balance_from_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_balance_from_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_balance_fromDate_propertyChange(evt);
            }
        });
        view_balance_datelimit_panel.add(view_balance_from_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        view_balance_from_label.setBackground(java.awt.Color.white);
        view_balance_from_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_balance_from_label.setText("From");
        view_balance_datelimit_panel.add(view_balance_from_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 30));

        view_balance_to_datechooser.setBackground(java.awt.Color.white);
        view_balance_to_datechooser.setDateFormatString("dd-MM-yyyy");
        view_balance_to_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_balance_to_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_balance_todate_propertChange(evt);
            }
        });
        view_balance_datelimit_panel.add(view_balance_to_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_balance_to_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_balance_to_label.setText("To");
        view_balance_datelimit_panel.add(view_balance_to_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        //view page images : view_datelimiticon.png
        view_balance_datelimit_panel.add(view_balance_datelimitIcon_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_balanceCont_panelLayout = new javax.swing.GroupLayout(view_balanceCont_panel);
        view_balanceCont_panel.setLayout(view_balanceCont_panelLayout);
        view_balanceCont_panelLayout.setHorizontalGroup(
            view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(view_balance_totWt_label, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_balance_totItem_label, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(view_balance_wtColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_balance_itemColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_balance_totWtInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(view_balance_totItemInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39)
                        .addComponent(view_balance_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(view_balance_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addContainerGap(64, Short.MAX_VALUE)
                        .addComponent(view_balance_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        view_balanceCont_panelLayout.setVerticalGroup(
            view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(view_balance_totItemInp_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_balanceCont_panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(view_balance_totItem_label)
                                    .addComponent(view_balance_itemColon_label))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(view_balance_totWt_label)
                                .addComponent(view_balance_wtColon_label))
                            .addComponent(view_balance_totWtInp_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(view_balance_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(view_balance_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_balance_tablearea_scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addContainerGap())
        );

        view_contTwo_panel.add(view_balanceCont_panel, "card5");

        view_returnCont_panel.setBackground(java.awt.Color.white);

        view_return_totWt_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_return_totWt_label.setForeground(java.awt.Color.gray);
        view_return_totWt_label.setText("Total Weight    ");

        view_return_totWtInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_return_totWtInp_label.setName(""); // NOI18N

        view_return_totItem_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_return_totItem_label.setForeground(java.awt.Color.gray);
        view_return_totItem_label.setText("Total no of Items");

        view_return_totItemInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_return_selOrnament_dropdown.setBackground(new java.awt.Color(247, 247, 247));
        view_return_selOrnament_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_return_selOrnament_dropdown.setForeground(java.awt.Color.gray);
        view_return_selOrnament_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_return_selOrnament_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_return_selOrnament_dropdownview_return_selOrnament_ActionPerformed(evt);
            }
        });

        view_return_itemColon_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_return_itemColon_label.setText(":");

        view_return_wtColon_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_return_wtColon_label.setText(":");

        view_returnTable_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        view_returnTable_table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        view_returnTable_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Date", "Chase No", "Ornament Name", "WT", "QTY", "BUY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_returnTable_table.setColumnSelectionAllowed(true);
        view_returnTable_table.setRequestFocusEnabled(false);
        view_returnTable_table.setRowHeight(48);
        view_returnTable_table.setRowMargin(0);
        view_returnTable_table.setShowHorizontalLines(false);
        view_returnTable_table.getTableHeader().setReorderingAllowed(false);
        view_return_tablearea_scrollpane.setViewportView(view_returnTable_table);
        view_returnTable_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (view_returnTable_table.getColumnModel().getColumnCount() > 0) {
            view_returnTable_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            view_returnTable_table.getColumnModel().getColumn(1).setPreferredWidth(120);
            view_returnTable_table.getColumnModel().getColumn(2).setPreferredWidth(130);
            view_returnTable_table.getColumnModel().getColumn(3).setPreferredWidth(195);
            view_returnTable_table.getColumnModel().getColumn(4).setPreferredWidth(73);
            view_returnTable_table.getColumnModel().getColumn(5).setPreferredWidth(73);
        }

        view_return_datelimit_panel.setBackground(java.awt.Color.white);
        view_return_datelimit_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_return_from_datechooser.setBackground(java.awt.Color.white);
        view_return_from_datechooser.setDateFormatString("dd-MM-yyyy");
        view_return_from_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_return_from_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_return_from_datechooserview_return_fromDate_propertyChange(evt);
            }
        });
        view_return_datelimit_panel.add(view_return_from_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        view_return_from_label.setBackground(java.awt.Color.white);
        view_return_from_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_return_from_label.setText("From");
        view_return_datelimit_panel.add(view_return_from_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 30));

        view_return_to_datechooser.setBackground(java.awt.Color.white);
        view_return_to_datechooser.setDateFormatString("dd-MM-yyyy");
        view_return_to_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_return_to_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_return_to_datechooserview_return_todate_propertChange(evt);
            }
        });
        view_return_datelimit_panel.add(view_return_to_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_return_to_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_return_to_label.setText("To");
        view_return_datelimit_panel.add(view_return_to_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        //view page images : view_datelimiticon.png
        view_return_datelimit_panel.add(view_return_datelimitIcon_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_returnCont_panelLayout = new javax.swing.GroupLayout(view_returnCont_panel);
        view_returnCont_panel.setLayout(view_returnCont_panelLayout);
        view_returnCont_panelLayout.setHorizontalGroup(
            view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(view_return_totWt_label, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_return_totItem_label, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(view_return_wtColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_return_itemColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_return_totWtInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(view_return_totItemInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(43, 43, 43)
                        .addComponent(view_return_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(view_return_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addContainerGap(64, Short.MAX_VALUE)
                        .addComponent(view_return_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        view_returnCont_panelLayout.setVerticalGroup(
            view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(view_return_totItemInp_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_returnCont_panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(view_return_totItem_label)
                                    .addComponent(view_return_itemColon_label))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(view_return_totWt_label)
                                .addComponent(view_return_wtColon_label))
                            .addComponent(view_return_totWtInp_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(view_return_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(view_return_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_return_tablearea_scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addContainerGap())
        );

        view_contTwo_panel.add(view_returnCont_panel, "card5");

        javax.swing.GroupLayout view_areaTwo_panelLayout = new javax.swing.GroupLayout(view_areaTwo_panel);
        view_areaTwo_panel.setLayout(view_areaTwo_panelLayout);
        view_areaTwo_panelLayout.setHorizontalGroup(
            view_areaTwo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view_contOne_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(view_contTwo_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
        );
        view_areaTwo_panelLayout.setVerticalGroup(
            view_areaTwo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_areaTwo_panelLayout.createSequentialGroup()
                .addComponent(view_contOne_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_contTwo_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        view_total_panel.add(view_areaTwo_panel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout viewPageLayout = new javax.swing.GroupLayout(viewPage);
        viewPage.setLayout(viewPageLayout);
        viewPageLayout.setHorizontalGroup(
            viewPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view_total_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        viewPageLayout.setVerticalGroup(
            viewPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view_total_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLayeredPane1.add(viewPage, "viewPage");

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
        Entry_Quality_Label.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_InputBox_Label.png"));
        Entry_MC_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_InputBox_Label.png")); // NOI18N
        Entry_WT_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_InputBox_Label.png")); // NOI18N
        Entry_WAS_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_InputBox_Label.png")); // NOI18N
        Entry_QTY_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_InputBox_Label.png")); // NOI18N
        Entry_BUY_Label_Icon.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_InputBox_Label.png")); // NOI18N
        Entry_EnterButton_Label.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_EnterBtn_Label.png")); // NOI18N
        Entry_Reset_jLabel.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_ResetBtn_Label.png")); // NOI18N
        sell_refresh_label.setIcon(new javax.swing.ImageIcon(defaultPath + "\\sell_refresh.png")); // NOI18N
        sell_confirm_label.setIcon(new javax.swing.ImageIcon(defaultPath + "\\sell_confirm.png")); // NOI18N
        sell_return_label1.setIcon(new javax.swing.ImageIcon(defaultPath + "\\sell_return.png")); // NOI18N
        sell_barcodeInput_label.setIcon(new javax.swing.ImageIcon(defaultPath + "\\sell_barcode.png")); // NOI18N
        sell_verify_checkbox.setIcon(new javax.swing.ImageIcon(defaultPath + "\\sell_unChecked_checkbox.jpg")); // NOI18N
        sell_qtyInput_label.setIcon(new javax.swing.ImageIcon(defaultPath + "\\sell_qtyInput.png")); // NOI18N
        Entry_Check_jCheckBox.setIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_checkBox_Label.png")); // NOI18N
        Entry_Check_jCheckBox.setPressedIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_CheckBox_Label.png")); // NOI18N
        Entry_Check_jCheckBox.setSelectedIcon(new javax.swing.ImageIcon(defaultPath + "\\Entry_CheckedBox_Label.png"));
        sell_verify_checkbox.setSelectedIcon(new javax.swing.ImageIcon(defaultPath + "\\sell_Checked_checkbox.jpg"));
        sell_verify_checkbox.setPressedIcon(new javax.swing.ImageIcon(defaultPath + "\\sell_unChecked_checkbox.jpg"));
    }

    // Navigate to Sell Page
    private boolean navToSell() {

        try {
            mainLayout.show(jLayeredPane1, "sellPage");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    // Navigate to Entry Page
    private boolean navToEntry() {

        try {
            mainLayout.show(jLayeredPane1, "entryPage");
            return true;
        } catch (Exception e) {
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

                if (Entry_Check_jCheckBox.isSelected()) {
                    PrintBarcode();
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
    private void Entry_WT_TextFieldKeyPressed(java.awt.event.KeyEvent evt) {

        if (!(Entry_WT_TextField.getText().length() <= 6)) {
            Entry_WT_TextField.setEditable(false);
        } else {
            Entry_WT_TextField.setEditable(true);
        }
        if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            Entry_WT_TextField.setEditable(true);
        }

    }

    // Ornament_name Action
    private void Entry_OrnamentName_jComboboxActionPerformed(java.awt.event.ActionEvent evt) {

        this.ornament_name = (String) Entry_OrnamentName_jCombobox.getSelectedItem();
        System.out.println(this.ornament_name);

        if (!"Select Ornament Name".equals(ornament_name)) {
            if (!Limit_of_OrnamentNameChar(ornament_name)) {
                JOptionPane.showMessageDialog(null, "\"Oops! Sorry, check whether the entered name is within 16 characters");
            }
        }
    }

    private void Entry_WAS_TextFieldKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (!(Entry_WAS_TextField.getText().length() <= 5)) {
            Entry_WAS_TextField.setEditable(false);
        } else {
            Entry_WAS_TextField.setEditable(true);
        }
        if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            Entry_WAS_TextField.setEditable(true);
        }
    }

    //KeyPressed Event for Quantity Text Field to enter only numbers
    private void sell_qtyInput_textFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sell_qtyInput_textFieldKeyPressed

        //gets the key press
        char c = evt.getKeyChar();

        //Characters not allowed
        if (Character.isLetter(c)) {
            //Can't type as it is a character
            sell_qtyInput_textField.setEditable(false);
            //Error Message
            JOptionPane.showMessageDialog(null, "Please Enter Number Only");
        } //Number only can be entered
        else {
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
            if (rs.next()) {
                sell_chaseNoDetail_label.setText(rs.getString("chase_no"));
                sell_ornamentNameDetail_label.setText(rs.getString("ornament_name"));
                sell_wtDetail_label.setText((rs.getString("weight")) + " g");
                sell_wasDetail_label.setText((rs.getString("wastage")) + " %");
                sell_mcDetail_label.setText((rs.getString("making_charge")) + " /G");
            } //Incorrect barcode
            else {
                JOptionPane.showMessageDialog(null, "Barcode is incorrect, Please do check it");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Re-Scan or Re-Enter the Barcode");
        }

    }//GEN-LAST:event_sell_barcodeInput_textFieldActionPerformed

    //Returns the entry
    private void sell_return_label1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_return_label1MouseClicked

        try {
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

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try again");
        }
    }//GEN-LAST:event_sell_return_label1MouseClicked

    //Confirms the entry
    private void sell_confirm_labelMouseClicked(java.awt.event.MouseEvent evt) {

        try {
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
        } catch (Exception e) {

        }
    }

    // Navigation to sell page from entry page
    private void Entry_NavSell_LabelMouseClicked(java.awt.event.MouseEvent evt) {

        try {
            if (!this.navToSell()) {
                JOptionPane.showMessageDialog(null, "There is a problem in Navigation. Kindly close the software, reopen it and try again!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try again");
        }
    }

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

    private void sell_verify_checkboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_verify_checkboxActionPerformed
        //If the check box is checked in
        if (sell_verify_checkbox.isSelected()) {

            //gets quatity input data
            String input = sell_qtyInput_textField.getText();

            //checks whether the details are filled
            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the Quantity needed");
                sell_confirm_label.setEnabled(false);
                sell_return_label1.setEnabled(false);
                sell_verify_checkbox.setSelected(false);
            } //checks whether the input is correct
            else {
                QuantityVerification qv = new QuantityVerification();
                rs = qv.find(sell_barcodeInput_textField.getText());
            }

        } //If the check box is checked out
        else {
            sell_confirm_label.setEnabled(false);
            sell_return_label1.setEnabled(false);
        }
    }//GEN-LAST:event_sell_verify_checkboxActionPerformed

    private boolean navToView() {

        try {
            mainLayout.show(jLayeredPane1, "viewPage");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Barcode Fetching Operation
    public class Barcode {

        //Method to fetch the data
        public ResultSet find(String s) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                st = con.prepareStatement("SELECT chase_no, ornament_name, making_charge, weight, wastage FROM balance WHERE barcode = ?");
                st.setString(1, s);
                rs = st.executeQuery();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Some Errors, Please do try again");
            }
            return rs;
        }

    }

    //Return Operation
    public class Return {

        //Method to Return the Entry 
        public ResultSet find(String s) {

            //Updates the overall table status as 2 (balance table status too changes and the retun table is also updated with the entry)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                st = con.prepareStatement("update overall set status=2 WHERE barcode = ?");
                st.setString(1, s);
                int updateOverall = st.executeUpdate();

                //Updates the return table quantity with the input entered
                try {
                    int quantity = Integer.parseInt(sell_qtyInput_textField.getText());
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                    st = con.prepareStatement("UPDATE return_table SET quantity=? WHERE barcode = ?");
                    st.setInt(1, quantity);
                    st.setString(2, s);
                    int updateReturn = st.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error., in Return updation");
                }

                //Updates the balance table quantity
                try {
                    int quantity = Integer.parseInt(sell_qtyInput_textField.getText());
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                    st = con.prepareStatement("SELECT quantity FROM balance WHERE barcode = ?");
                    st.setString(1, s);
                    rs = st.executeQuery();
                    while (rs.next()) {
                        int qty = rs.getInt("quantity");
                        int Quantity;

                        //Entered_quantity subtracted from DB_quantity
                        Quantity = qty - quantity;

                        //If the decremented quantity (Entered_quantity subtracted from DB_quantity) is greater than zero then it updates the balance table quantity with the decremented value
                        if (Quantity > 0) {
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                                st = con.prepareStatement("UPDATE balance SET quantity=? WHERE barcode = ?");
                                st.setInt(1, Quantity);
                                st.setString(2, s);
                                int updateReturnQty = st.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Returned Successfully");

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error., in Return updation");
                            }
                        } //If the decremented quantity (Entered_quantity subtracted from DB_quantity) is equal to zero then it deletes the data from balance table 
                        else {
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                                st = con.prepareStatement("DELETE FROM balance WHERE barcode =?");
                                st.setString(1, s);
                                int updateReturnQty = st.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Returned Successfully");

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error., in Return updation.");
                            }
                        }

                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Try again");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error., in Return. Please do Check your connection");
            }
            return rs;

        }
    }

    //Confirmation Operation
    public class Confirm {

        //Method to Confirm the Entry
        public ResultSet find(String s) {

            //Updates the overall table status as 1 (balance table status too changes and the sold table is also updated with the entry)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                st = con.prepareStatement("update overall set status=1 WHERE barcode = ?");
                st.setString(1, s);
                int updateOverall = st.executeUpdate();

                //Updates the sold table quantity with the input entered
                try {
                    int quantity = Integer.parseInt(sell_qtyInput_textField.getText());
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                    st = con.prepareStatement("UPDATE sold SET quantity=? WHERE barcode = ?");
                    st.setInt(1, quantity);
                    st.setString(2, s);
                    int updateReturn = st.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error., in Confirm updation");
                }

                //Updates the balance table quantity
                try {
                    int quantity = Integer.parseInt(sell_qtyInput_textField.getText());
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                    st = con.prepareStatement("SELECT quantity FROM balance WHERE barcode = ?");
                    st.setString(1, s);
                    rs = st.executeQuery();
                    while (rs.next()) {
                        int qty = rs.getInt("quantity");
                        int Quantity;

                        //Entered_quantity subtracted from DB_quantity
                        Quantity = qty - quantity;

                        //If the decremented quantity (Entered_quantity subtracted from DB_quantity) is greater than zero then it updates the balance table quantity with the decremented value
                        if (Quantity > 0) {
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                                st = con.prepareStatement("UPDATE balance SET quantity=? WHERE barcode = ?");
                                st.setInt(1, Quantity);
                                st.setString(2, s);
                                int updateReturnQty = st.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Confirmed Successfully");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error., in Confirm updation");
                            }
                        } //If the decremented quantity (Entered_quantity subtracted from DB_quantity) is equal to zero then it deletes the data from balance table 
                        else {
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                                st = con.prepareStatement("DELETE FROM balance WHERE barcode =?");
                                st.setString(1, s);
                                int updateReturnQty = st.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Confirmed Successfully");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error., in Confirm updation");
                            }
                        }

                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, " Try again");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error., in Confirmation. Please do Check your connection");
            }

            return rs;

        }

    }

    //Quantity Verification
    public class QuantityVerification {

        //Method to verify the entered Quantity in the Quantity input
        public ResultSet find(String s) {

            //Verification
            try {
                int quantity = Integer.parseInt(sell_qtyInput_textField.getText());
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ?serverTimezone=UTC", "root", "");
                st = con.prepareStatement("SELECT quantity FROM balance WHERE barcode = ?");
                st.setString(1, s);
                rs = st.executeQuery();
                while (rs.next()) {
                    int qty = rs.getInt("quantity");

                    //If the Entered Quantity input is greater than the DB quantity then a error message popups
                    if (quantity > qty) {
                        JOptionPane.showMessageDialog(null, "Please do check the available quantity");
                        sell_confirm_label.setEnabled(false);
                        sell_return_label1.setEnabled(false);
                        sell_verify_checkbox.setSelected(false);
                    } //If the quantity input entered is available
                    else {
                        //Verifies and enables the Confirm, Return Labels
                        sell_confirm_label.setEnabled(true);
                        sell_return_label1.setEnabled(true);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Try again");
            }
            return rs;
        }
    }

    private void view_mouseclicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_mouseclicked

        // Clicked panel visibility true and others are displayed false
        // When switched to next panel, previous panel label color change
        if (evt.getSource() == view_overall_panel) {
            view_overallCont_panel.setVisible(true);
            view_soldCont_panel.setVisible(false);
            view_balanceCont_panel.setVisible(false);
            view_returnCont_panel.setVisible(false);
            view_overall_label.setForeground(new Color(76, 76, 76));
            view_sold_label.setForeground(new Color(158, 154, 154));
            view_balance_label.setForeground(new Color(158, 154, 154));
            view_return_label.setForeground(new Color(158, 154, 154));
        }
        if (evt.getSource() == view_sold_panel) {
            view_overallCont_panel.setVisible(false);
            view_soldCont_panel.setVisible(true);
            view_balanceCont_panel.setVisible(false);
            view_returnCont_panel.setVisible(false);
            view_overall_label.setForeground(new Color(158, 154, 154));
            view_sold_label.setForeground(new Color(76, 76, 76));
            view_balance_label.setForeground(new Color(158, 154, 154));
            view_return_label.setForeground(new Color(158, 154, 154));
        }
        if (evt.getSource() == view_balance_panel) {
            view_overallCont_panel.setVisible(false);
            view_soldCont_panel.setVisible(false);
            view_balanceCont_panel.setVisible(true);
            view_returnCont_panel.setVisible(false);
            view_overall_label.setForeground(new Color(158, 154, 154));
            view_sold_label.setForeground(new Color(158, 154, 154));
            view_balance_label.setForeground(new Color(76, 76, 76));
            view_return_label.setForeground(new Color(158, 154, 154));
        }
        if (evt.getSource() == view_return_panel) {
            view_overallCont_panel.setVisible(false);
            view_soldCont_panel.setVisible(false);
            view_balanceCont_panel.setVisible(false);
            view_returnCont_panel.setVisible(true);
            view_overall_label.setForeground(new Color(158, 154, 154));
            view_sold_label.setForeground(new Color(158, 154, 154));
            view_balance_label.setForeground(new Color(158, 154, 154));
            view_return_label.setForeground(new Color(76, 76, 76));
        }
    }//GEN-LAST:event_view_mouseclicked

    private void view_return_selOrnament_dropdownview_return_selOrnament_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_return_selOrnament_dropdownview_return_selOrnament_ActionPerformed

        //For autoincrementing the no. of rows
        int count = 1;

        view_return_ornament_type_data = view_return_selOrnament_dropdown.getSelectedItem().toString();

        if (view_return_ornament_type_data != "Select the Ornament") {
            try {
                try {

                    // Getting table values acc. to selOrnament
                    String sql = "SELECT date, chase_no, ornament_name, quality,\n"
                            + "weight,buy FROM return_table\n"
                            + "WHERE ornament_type =" + "'" + view_return_ornament_type_data + "'";

                    pat = con.prepareStatement(sql);
                    rs = pat.executeQuery();
                    DefaultTableModel tm = (DefaultTableModel) view_returnTable_table.getModel();
                    tm.setRowCount(0);
                    while (rs.next()) {
                        Object o[] = {count, rs.getString("date"), rs.getString("chase_no"), rs.getString("ornament_name"), rs.getString("quality"), rs.getString("weight"), rs.getString("buy")};
                        tm.addRow(o);
                        count++;
                    }

                } catch (Exception e) {

                }

                try {

                    //Getting total weight of items acc. to selOrnament.
                    String sql1 = "SELECT SUM(weight) FROM return_table WHERE ornament_type = " + "'" + view_return_ornament_type_data + "'";

                    PreparedStatement pat1 = con.prepareStatement(sql1);
                    ResultSet rs1 = pat1.executeQuery();
                    while (rs1.next()) {
                        if (rs1.getString(1) == null) {
                            view_return_totWtInp_label.setText("0");
                        } else {
                            view_return_totWtInp_label.setText(rs1.getString(1));
                        }
                    }

                } catch (Exception e) {

                }

                try {

                    //Getting total number of items acc. to selOrnament.
                    String sql2 = "SELECT COUNT(id) FROM return_table WHERE ornament_type = " + "'" + view_return_ornament_type_data + "'";

                    PreparedStatement pat2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pat2.executeQuery();
                    while (rs2.next()) {
                        view_return_totItemInp_label.setText(rs2.getString(1));
                    }
                } catch (Exception e) {

                }

            } catch (Exception e) {

            }
            view_return_combined_display();
        } else if ((view_return_from_date != null) && (view_return_to_date != null)) {
            view_return_date_display();
        }
    }//GEN-LAST:event_view_return_selOrnament_dropdownview_return_selOrnament_ActionPerformed

    private void view_return_from_datechooserview_return_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_return_from_datechooserview_return_fromDate_propertyChange
        // TODO add your handling code here:
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            view_return_from_date = df.format(view_return_from_datechooser.getDate());
        } catch (Exception e) {

        }
        view_return_date_display();
    }//GEN-LAST:event_view_return_from_datechooserview_return_fromDate_propertyChange

    private void view_return_to_datechooserview_return_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_return_to_datechooserview_return_todate_propertChange
        // TODO add your handling code here:
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            view_return_to_date = df.format(view_return_to_datechooser.getDate());
        } catch (Exception e) {

        }
        view_return_date_display();
    }//GEN-LAST:event_view_return_to_datechooserview_return_todate_propertChange

    private void view_balance_selOrnament_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_balance_selOrnament_ActionPerformed

        //For autoincrementing the no. of rows 
        int count = 1;

        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);

        view_balance_ornament_type_data = view_balance_selOrnament_dropdown.getSelectedItem().toString();

        if (view_balance_ornament_type_data != "Select the Ornament") {

            try {
                try {

                    //Getting table values acc. to selOrnament
                    String sql = "SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM balance\n"
                            + "WHERE ornament_type =" + "'" + view_balance_ornament_type_data + "'";

                    pat = con.prepareStatement(sql);
                    rs = pat.executeQuery();
                    DefaultTableModel tm = (DefaultTableModel) view_balanceTable_table.getModel();
                    tm.setRowCount(0);
                    while (rs.next()) {
                        Object o[] = {count, rs.getString("date"), rs.getString("chase_no"), rs.getString("ornament_name"), rs.getString("weight"), rs.getString("quantity"), rs.getString("barcode"), imageLabel};
                        tm.addRow(o);
                        count++;
                    }

                } catch (Exception e) {

                }

                try {

                    //Getting total weight of items acc. to selOrnament.
                    String sql1 = "SELECT SUM(weight) FROM balance WHERE ornament_type = " + "'" + view_balance_ornament_type_data + "'";

                    PreparedStatement pat1 = con.prepareStatement(sql1);
                    ResultSet rs1 = pat1.executeQuery();
                    while (rs1.next()) {
                        if (rs1.getString(1) == null) {
                            view_balance_totWtInp_label.setText("0");
                        } else {
                            view_balance_totWtInp_label.setText(rs1.getString(1));
                        }
                    }

                } catch (Exception e) {

                }

                try {

                    //Getting total number of items acc. to selOrnament.
                    String sql2 = "SELECT COUNT(id) FROM balance WHERE ornament_type = " + "'" + view_balance_ornament_type_data + "'";

                    PreparedStatement pat2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pat2.executeQuery();
                    while (rs2.next()) {
                        view_balance_totItemInp_label.setText(rs2.getString(1));
                    }
                } catch (Exception e) {

                }

            } catch (Exception e) {

            }
            view_balance_combined_display();
        } else if ((view_balance_from_date != null) && (view_balance_to_date != null)) {
            view_balance_date_display();
        }
    }//GEN-LAST:event_view_balance_selOrnament_ActionPerformed

    private void view_balance_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_balance_fromDate_propertyChange
        // TODO add your handling code here:
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            view_balance_from_date = df.format(view_balance_from_datechooser.getDate());
        } catch (Exception e) {

        }
        view_balance_date_display();
    }//GEN-LAST:event_view_balance_fromDate_propertyChange

    private void view_balance_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_balance_todate_propertChange
        // TODO add your handling code here:
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            view_balance_to_date = df.format(view_balance_to_datechooser.getDate());
        } catch (Exception e) {

        }
        view_balance_date_display();
    }//GEN-LAST:event_view_balance_todate_propertChange

    private void view_sold_selOrnament_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_sold_selOrnament_ActionPerformed

        //For autoincrementing the no. of rows 
        int count = 1;

        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);

        view_sold_ornament_type_data = view_sold_selOrnament_dropdown.getSelectedItem().toString();

        if (view_sold_ornament_type_data != "Select the Ornament") {

            try {
                try {

                    //Getting table values acc. to selOrnament
                    String sql = "SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM sold\n"
                            + "WHERE ornament_type =" + "'" + view_sold_ornament_type_data + "'";

                    pat = con.prepareStatement(sql);
                    rs = pat.executeQuery();
                    DefaultTableModel tm = (DefaultTableModel) view_soldTable_table.getModel();
                    tm.setRowCount(0);
                    while (rs.next()) {
                        Object o[] = {count, rs.getString("date"), rs.getString("chase_no"), rs.getString("ornament_name"), rs.getString("weight"), rs.getString("quantity"), rs.getString("barcode"), returnLabel};
                        tm.addRow(o);
                        count++;
                    }

                } catch (Exception e) {

                }

                try {

                    //Getting total weight of items acc. to selOrnament.
                    String sql1 = "SELECT SUM(weight) FROM sold WHERE ornament_type = " + "'" + view_sold_ornament_type_data + "'";

                    PreparedStatement pat1 = con.prepareStatement(sql1);
                    ResultSet rs1 = pat1.executeQuery();
                    while (rs1.next()) {
                        if (rs1.getString(1) == null) {
                            view_sold_totWtInp_label.setText("0");
                        } else {
                            view_sold_totWtInp_label.setText(rs1.getString(1));
                        }
                    }

                } catch (Exception e) {

                }

                try {

                    //Getting total number of items acc. to selOrnament.
                    String sql2 = "SELECT COUNT(id) FROM sold WHERE ornament_type = " + "'" + view_sold_ornament_type_data + "'";

                    PreparedStatement pat2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pat2.executeQuery();
                    while (rs2.next()) {
                        view_sold_totItemInp_label.setText(rs2.getString(1));
                    }
                } catch (Exception e) {

                }

            } catch (Exception e) {

            }
            view_sold_combined_display();
        } else if ((view_sold_from_date != null) && (view_sold_to_date != null)) {
            view_sold_date_display();
        }

    }//GEN-LAST:event_view_sold_selOrnament_ActionPerformed

    private void view_sold_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_sold_fromDate_propertyChange
        // TODO add your handling code here:

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            view_sold_from_date = df.format(view_sold_from_datechooser.getDate());
        } catch (Exception e) {

        }
        view_sold_date_display();
    }//GEN-LAST:event_view_sold_fromDate_propertyChange

    private void view_sold_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_sold_todate_propertChange
        // TODO add your handling code here:

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            view_sold_to_date = df.format(view_sold_to_datechooser.getDate());
        } catch (Exception e) {

        }
        view_sold_date_display();
    }//GEN-LAST:event_view_sold_todate_propertChange

    private void view_overall_selOrnament_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_overall_selOrnament_ActionPerformed
        // TODO add your handling code here:
        //For autoincrementing the no. of rows 
        int count = 1;

        view_overall_ornament_type_data = view_overall_selOrnament_dropdown.getSelectedItem().toString();

        if (view_overall_ornament_type_data != "Select the Ornament") {

            try {
                try {
                    //Getting table values acc. to selOrnament
                    String sql = "SELECT date, chase_no, ornament_type, ornament_name, quality,\n"
                            + "making_charge, weight, wastage, quantity,buy, barcode, status FROM overall\n"
                            + "WHERE ornament_type =" + "'" + view_overall_ornament_type_data + "'";

                    pat = con.prepareStatement(sql);
                    rs = pat.executeQuery();
                    DefaultTableModel tm = (DefaultTableModel) view_overallTable_table.getModel();
                    tm.setRowCount(0);
                    while (rs.next()) {
                        Object o[] = {count, rs.getString("date"), rs.getString("chase_no"), rs.getString("ornament_name"), rs.getString("weight"), rs.getString("wastage"), rs.getString("making_charge"), rs.getString("quantity"), rs.getString("quality"), rs.getString("buy")};
                        tm.addRow(o);
                        count++;
                    }

                } catch (Exception e) {

                }

                try {

                    //Getting total weight of items acc. to selOrnament.
                    String sql1 = "SELECT SUM(weight) FROM overall WHERE ornament_type = " + "'" + view_overall_ornament_type_data + "'";

                    PreparedStatement pat1 = con.prepareStatement(sql1);
                    ResultSet rs1 = pat1.executeQuery();
                    while (rs1.next()) {
                        if (rs1.getString(1) == null) {
                            view_overall_totWtInp_label.setText("0");
                        } else {
                            view_overall_totWtInp_label.setText(rs1.getString(1));
                        }
                    }

                } catch (Exception e) {

                }

                try {

                    //Getting total number of items acc. to selOrnament.
                    String sql2 = "SELECT COUNT(id) FROM overall WHERE ornament_type = " + "'" + view_overall_ornament_type_data + "'";

                    PreparedStatement pat2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pat2.executeQuery();
                    while (rs2.next()) {
                        view_overall_totItemInp_label.setText(rs2.getString(1));
                    }
                } catch (Exception e) {

                }

            } catch (Exception e) {

            }
            view_overall_combined_display();
        } else {
            view_overall_date_display();
        }

    }//GEN-LAST:event_view_overall_selOrnament_ActionPerformed

    private void view_overall_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_overall_fromDate_propertyChange
        // TODO add your handling code here:
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            view_overall_from_date = df.format(view_overall_from_datechooser.getDate());
        } catch (Exception e) {

        }
        view_overall_date_display();
    }//GEN-LAST:event_view_overall_fromDate_propertyChange

    private void view_overall_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_overall_todate_propertChange
        // TODO add your handling code here:
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            view_overall_to_date = df.format(view_overall_to_datechooser.getDate());
        } catch (Exception e) {

        }
        view_overall_date_display();
    }//GEN-LAST:event_view_overall_todate_propertChange

    private void Entry_NavView_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Entry_NavView_LabelMouseClicked
        // TODO add your handling code here:
        try {
            if (!this.navToView()) {
                JOptionPane.showMessageDialog(null, "There is a problem in Navigation. Kindly close the software, reopen it and try again!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try again");
        }
    }//GEN-LAST:event_Entry_NavView_LabelMouseClicked

    private void sell_viewNavigation_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_viewNavigation_labelMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        try {
            if (!this.navToView()) {
                JOptionPane.showMessageDialog(null, "There is a problem in Navigation. Kindly close the software, reopen it and try again!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try again");
        }
    }//GEN-LAST:event_sell_viewNavigation_labelMouseClicked

    private void view_entry_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_entry_labelMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        try {
            if (!this.navToEntry()) {
                JOptionPane.showMessageDialog(null, "There is a problem in Navigation. Kindly close the software, reopen it and try again!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try again");
        }
    }//GEN-LAST:event_view_entry_labelMouseClicked

    private void view_sell_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_view_sell_labelMouseClicked
        // TODO add your handling code here:
        try {
            if (!this.navToSell()) {
                JOptionPane.showMessageDialog(null, "There is a problem in Navigation. Kindly close the software, reopen it and try again!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try again");
        }
    }//GEN-LAST:event_view_sell_labelMouseClicked

    // Navigation to entry page from sell page
    private void sell_entryNavigation_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_entryNavigation_labelMouseClicked

        if (!this.navToEntry()) {
            JOptionPane.showMessageDialog(null, "There is a problem in Navigation. Kindly close the software, reopen it and try again!");
        }
    }//GEN-LAST:event_sell_entryNavigation_labelMouseClicked

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
    private javax.swing.JLabel Entry_ShopNameA_Label1;
    private javax.swing.JLabel Entry_ShopnameJ2_Label;
    private javax.swing.JLabel Entry_ShopnameJ2_Label1;
    private javax.swing.JLabel Entry_ShopnameJ_Label;
    private javax.swing.JLabel Entry_ShopnameJ_Label1;
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
    private javax.swing.JPanel viewPage;
    private javax.swing.JPanel view_areaOne_panel;
    private javax.swing.JPanel view_areaTwo_panel;
    private javax.swing.JPanel view_balanceCont_panel;
    private javax.swing.JTable view_balanceTable_table;
    private javax.swing.JLabel view_balance_datelimitIcon_label;
    private javax.swing.JPanel view_balance_datelimit_panel;
    private com.toedter.calendar.JDateChooser view_balance_from_datechooser;
    private javax.swing.JLabel view_balance_from_label;
    private javax.swing.JLabel view_balance_itemColon_label;
    private javax.swing.JLabel view_balance_label;
    private javax.swing.JPanel view_balance_panel;
    private javax.swing.JComboBox<String> view_balance_selOrnament_dropdown;
    private javax.swing.JScrollPane view_balance_tablearea_scrollpane;
    private com.toedter.calendar.JDateChooser view_balance_to_datechooser;
    private javax.swing.JLabel view_balance_to_label;
    private javax.swing.JLabel view_balance_totItemInp_label;
    private javax.swing.JLabel view_balance_totItem_label;
    private javax.swing.JLabel view_balance_totWtInp_label;
    private javax.swing.JLabel view_balance_totWt_label;
    private javax.swing.JLabel view_balance_wtColon_label;
    private javax.swing.JPanel view_contOne_panel;
    private javax.swing.JPanel view_contTwo_panel;
    private javax.swing.JLabel view_entry_label;
    private javax.swing.JPanel view_overallCont_panel;
    private javax.swing.JTable view_overallTable_table;
    private javax.swing.JLabel view_overall_datelimitIcon_label;
    private javax.swing.JPanel view_overall_datelimit_panel;
    private com.toedter.calendar.JDateChooser view_overall_from_datechooser;
    private javax.swing.JLabel view_overall_from_label;
    private javax.swing.JLabel view_overall_itemColon_label;
    private javax.swing.JLabel view_overall_label;
    private javax.swing.JPanel view_overall_panel;
    private javax.swing.JComboBox<String> view_overall_selOrnament_dropdown;
    private javax.swing.JScrollPane view_overall_tablearea_scrollpane;
    private com.toedter.calendar.JDateChooser view_overall_to_datechooser;
    private javax.swing.JLabel view_overall_to_label;
    private javax.swing.JLabel view_overall_totItemInp_label;
    private javax.swing.JLabel view_overall_totItem_label;
    private javax.swing.JLabel view_overall_totWtInp_label;
    private javax.swing.JLabel view_overall_totWt_label;
    private javax.swing.JLabel view_overall_wtColon_label;
    private javax.swing.JPanel view_returnCont_panel;
    private javax.swing.JTable view_returnTable_table;
    private javax.swing.JLabel view_return_datelimitIcon_label;
    private javax.swing.JPanel view_return_datelimit_panel;
    private com.toedter.calendar.JDateChooser view_return_from_datechooser;
    private javax.swing.JLabel view_return_from_label;
    private javax.swing.JLabel view_return_itemColon_label;
    private javax.swing.JLabel view_return_label;
    private javax.swing.JPanel view_return_panel;
    private javax.swing.JComboBox<String> view_return_selOrnament_dropdown;
    private javax.swing.JScrollPane view_return_tablearea_scrollpane;
    private com.toedter.calendar.JDateChooser view_return_to_datechooser;
    private javax.swing.JLabel view_return_to_label;
    private javax.swing.JLabel view_return_totItemInp_label;
    private javax.swing.JLabel view_return_totItem_label;
    private javax.swing.JLabel view_return_totWtInp_label;
    private javax.swing.JLabel view_return_totWt_label;
    private javax.swing.JLabel view_return_wtColon_label;
    private javax.swing.JLabel view_sell_label;
    private javax.swing.JPanel view_soldCont_panel;
    private javax.swing.JTable view_soldTable_table;
    private javax.swing.JLabel view_sold_datelimitIcon_label;
    private javax.swing.JPanel view_sold_datelimit_panel;
    private com.toedter.calendar.JDateChooser view_sold_from_datechooser;
    private javax.swing.JLabel view_sold_from_label;
    private javax.swing.JLabel view_sold_itemColon_label;
    private javax.swing.JLabel view_sold_label;
    private javax.swing.JPanel view_sold_panel;
    private javax.swing.JComboBox<String> view_sold_selOrnament_dropdown;
    private javax.swing.JScrollPane view_sold_tablearea_scrollpane;
    private com.toedter.calendar.JDateChooser view_sold_to_datechooser;
    private javax.swing.JLabel view_sold_to_label;
    private javax.swing.JLabel view_sold_totItemInp_label;
    private javax.swing.JLabel view_sold_totItem_label;
    private javax.swing.JLabel view_sold_totWtInp_label;
    private javax.swing.JLabel view_sold_totWt_label;
    private javax.swing.JLabel view_sold_wtColon_label;
    private javax.swing.JPanel view_total_panel;
    private javax.swing.JLabel view_view_label;
    // End of variables declaration//GEN-END:variables

    public static class view_headerColor extends DefaultTableCellRenderer {

        public view_headerColor() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            setBackground(new Color(247, 247, 247));
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.LIGHT_GRAY));
            return this;
        }
    }

    class view_myCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            return (Component) o;

        }

    }

    // Displaying dropdown items from database
    private void view_overall_dropdown_display() {
        try {
            String sql = "SELECT type FROM Ornament_type";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                view_overall_selOrnament_dropdown.addItem(rs.getString("type"));
            }
        } catch (Exception e) {

        }
    }

    private void view_sold_dropdown_display() {
        try {
            String sql = "SELECT type FROM Ornament_type";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                view_sold_selOrnament_dropdown.addItem(rs.getString("type"));
            }
        } catch (Exception e) {

        }
    }

    private void view_balance_dropdown_display() {
        try {
            String sql = "SELECT type FROM Ornament_type";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                view_balance_selOrnament_dropdown.addItem(rs.getString("type"));
            }
        } catch (Exception e) {

        }
    }

    private void view_return_dropdown_display() {
        try {
            String sql = "SELECT type FROM Ornament_type";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                view_return_selOrnament_dropdown.addItem(rs.getString("type"));
            }
        } catch (Exception e) {

        }
    }

    // Displaying the default items in the table 
    private void view_overall_default_display() {

        // For autoincrementing the no. of rows 
        int count = 1;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            Date overall_from = sd.parse(this.view_overall_from_date);
            Date overall_to = sd.parse(this.view_overall_to_date);
            if (overall_from.compareTo(overall_to) < 0) {
                try {
                    //Getting default total weight of items.
                    String sql1 = "SELECT SUM(weight) FROM overall WHERE date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                    PreparedStatement pat1 = con.prepareStatement(sql1);
                    ResultSet rs1 = pat1.executeQuery();
                    while (rs1.next()) {
                        if (rs1.getString(1) == null) {
                            view_overall_totWtInp_label.setText("0");
                        } else {
                            view_overall_totWtInp_label.setText(rs1.getString(1));
                        }
                    }

                } catch (Exception e) {

                }

                try {
                    //Getting default overall table values. 
                    String sql2 = "SELECT date, chase_no, ornament_type, ornament_name, quality,\n"
                            + "making_charge, weight, wastage, quantity,buy, barcode, status FROM overall WHERE date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                    PreparedStatement pat2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pat2.executeQuery();

                    DefaultTableModel tm = (DefaultTableModel) view_overallTable_table.getModel();
                    tm.setRowCount(0);
                    while (rs2.next()) {
                        Object o[] = {count, rs2.getString("date"), rs2.getString("chase_no"), rs2.getString("ornament_name"), rs2.getString("weight"), rs2.getString("wastage"), rs2.getString("making_charge"), rs2.getString("quantity"), rs2.getString("quality"), rs2.getString("buy")};
                        tm.addRow(o);
                        count++;
                    }
                } catch (Exception e) {

                }

                try {
                    //Getting default total number of items.
                    String sql3 = "SELECT COUNT(id) FROM overall WHERE date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                    PreparedStatement pat3 = con.prepareStatement(sql3);
                    ResultSet rs3 = pat3.executeQuery();
                    while (rs3.next()) {
                        view_overall_totItemInp_label.setText(rs3.getString(1));
                    }
                } catch (Exception e) {

                }

            } else {
                JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
            }
        } catch (HeadlessException | ParseException e) {

        }

    }

    private void view_sold_default_display() {
        //For autoincrementing the no. of rows 
        int count = 1;

        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);

        if ((view_sold_from_date != null) && (view_sold_to_date != null)) {
            if (view_sold_ornament_type_data == "Select the Ornament") {
                try {
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    Date sold_from = sd.parse(this.view_sold_from_date);
                    Date sold_to = sd.parse(this.view_sold_to_date);
                    if (sold_from.compareTo(sold_to) < 0) {
                        try {
                            //Getting default total weight of items.
                            String sql1 = "SELECT SUM(weight) FROM sold WHERE date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            while (rs1.next()) {
                                if (rs1.getString(1) == null) {
                                    view_sold_totWtInp_label.setText("0");
                                } else {
                                    view_sold_totWtInp_label.setText(rs1.getString(1));
                                }
                            }

                        } catch (Exception e) {

                        }

                        try {
                            //Getting default sold table values. 
                            String sql2 = "SELECT * FROM sold WHERE date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                            PreparedStatement pat2 = con.prepareStatement(sql2);
                            ResultSet rs2 = pat2.executeQuery();
                            ResultSetMetaData rsmd = rs2.getMetaData();
                            rs2.last();
                            view_sold_raw_data = new String[rs2.getRow()][rsmd.getColumnCount()];
                            rs2.beforeFirst();

                            DefaultTableModel tm = (DefaultTableModel) view_soldTable_table.getModel();

                            tm.setRowCount(0);
                            while (rs2.next()) {
                                String obj[] = {Integer.toString(count), rs2.getString("date"), rs2.getString("chase_no"), rs2.getString("ornament_type"), rs2.getString("ornament_name"), rs2.getString("quality"), rs2.getString("making_charge"), rs2.getString("weight"), rs2.getString("wastage"), rs2.getString("quantity"), rs2.getString("buy"), rs2.getString("barcode"), rs2.getString("status"), rs2.getString("snapshot")};
                                view_sold_raw_data[count - 1] = obj;
                                Object o[] = {count, rs2.getString("date"), rs2.getString("chase_no"), rs2.getString("ornament_name"), rs2.getString("weight"), rs2.getString("quantity"), rs2.getString("barcode"), returnLabel};
                                tm.addRow(o);
                                count++;
                            }
                        } catch (Exception e) {
                            // JOptionPane.showMessageDialog(null,e);
                        }

                        try {
                            //Getting default total number of items.
                            String sql3 = "SELECT COUNT(id) FROM sold WHERE date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                            PreparedStatement pat3 = con.prepareStatement(sql3);
                            ResultSet rs3 = pat3.executeQuery();
                            while (rs3.next()) {
                                view_sold_totItemInp_label.setText(rs3.getString(1));
                            }
                        } catch (Exception e) {

                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                    }
                } catch (Exception e) {

                }
            } else {
                view_sold_date_display();
            }
        }

    }

    private void view_balance_default_display() {

        //For autoincrementing the no. of rows 
        int count = 1;

        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);

        if ((view_balance_from_date != null) && (view_balance_to_date != null)) {
            System.out.println("======================");
            try {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                Date balance_from;
                balance_from = sd.parse(this.view_balance_from_date);
                Date balance_to = sd.parse(this.view_balance_to_date);
                if (balance_from.compareTo(balance_to) < 0) {

                    try {
                        //Getting default total weight of items.
                        String sql1 = "SELECT SUM(weight) FROM balance WHERE date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        while (rs1.next()) {
                            if (rs1.getString(1) == null) {
                                view_balance_totWtInp_label.setText("0");
                            } else {
                                view_balance_totWtInp_label.setText(rs1.getString(1));
                            }
                        }

                    } catch (Exception e) {

                    }

                    try {
                        //Getting default balance table values. 
                        String sql2 = "SELECT * FROM balance WHERE date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                        PreparedStatement pat2 = con.prepareStatement(sql2);
                        ResultSet rs2 = pat2.executeQuery();
                        DefaultTableModel tm = (DefaultTableModel) view_balanceTable_table.getModel();
                        ResultSetMetaData rsmd = rs2.getMetaData();
                        rs2.last();
                        view_balance_raw_data = new String[rs2.getRow()][rsmd.getColumnCount()];
                        rs2.beforeFirst();

                        tm.setRowCount(0);
                        while (rs2.next()) {
                            String obj[] = {Integer.toString(count), rs2.getString("date"), rs2.getString("chase_no"), rs2.getString("ornament_type"), rs2.getString("ornament_name"), rs2.getString("quality"), rs2.getString("making_charge"), rs2.getString("weight"), rs2.getString("wastage"), rs2.getString("quantity"), rs2.getString("buy"), rs2.getString("barcode"), rs2.getString("status"), rs2.getString("snapshot")};
                            view_balance_raw_data[count - 1] = obj;
                            Object o[] = {count, rs2.getString("date"), rs2.getString("chase_no"), rs2.getString("ornament_name"), rs2.getString("weight"), rs2.getString("quantity"), rs2.getString("barcode"), imageLabel};
                            tm.addRow(o);
                            count++;
                        }
                    } catch (Exception e) {
                           System.out.println(e);
                    }

                    try {
                        //Getting default total number of items.
                        String sql3 = "SELECT COUNT(id) FROM balance WHERE date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                        PreparedStatement pat3 = con.prepareStatement(sql3);
                        ResultSet rs3 = pat3.executeQuery();
                        while (rs3.next()) {
                            view_balance_totItemInp_label.setText(rs3.getString(1));
                        }
                    } catch (Exception e) {

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                }
            } catch (Exception e) {

            }
        }
    }

    private void view_return_default_display() {

        //For autoincrementing the no. of rows 
        int count = 1;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            Date return_from;
            return_from = sd.parse(this.view_return_from_date);
            Date return_to = sd.parse(this.view_return_to_date);
            if (return_from.compareTo(return_to) < 0) {
                try {
                    //Getting default total weight of items.
                    String sql1 = "SELECT SUM(weight) FROM return_table WHERE date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                    PreparedStatement pat1 = con.prepareStatement(sql1);
                    ResultSet rs1 = pat1.executeQuery();
                    while (rs1.next()) {
                        if (rs1.getString(1) == null) {
                            view_return_totWtInp_label.setText("0");
                        } else {
                            view_return_totWtInp_label.setText(rs1.getString(1));
                        }
                    }

                } catch (Exception e) {

                }

                try {
                    //Getting default return table values. 
                    String sql2 = "SELECT date, chase_no, ornament_name, quality, weight, buy FROM return_table WHERE date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                    PreparedStatement pat2 = con.prepareStatement(sql2);
                    ResultSet rs2 = pat2.executeQuery();
                    DefaultTableModel tm = (DefaultTableModel) view_returnTable_table.getModel();
                    tm.setRowCount(0);
                    while (rs2.next()) {
                        Object o[] = {count, rs2.getString("date"), rs2.getString("chase_no"), rs2.getString("ornament_name"), rs2.getString("quality"), rs2.getString("weight"), rs2.getString("buy")};
                        tm.addRow(o);
                        count++;
                    }
                } catch (Exception e) {

                }

                try {
                    //Getting default total number of items.
                    String sql3 = "SELECT COUNT(id) FROM return_table WHERE date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                    PreparedStatement pat3 = con.prepareStatement(sql3);
                    ResultSet rs3 = pat3.executeQuery();
                    while (rs3.next()) {
                        view_return_totItemInp_label.setText(rs3.getString(1));
                    }
                } catch (Exception e) {

                }

            } else {
                JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
            }
        } catch (Exception e) {

        }

    }

    //Displaying contents according to the date alone
    private void view_overall_date_display() {

        //For autoincrementing the no. of rows 
        int count = 1;
        if ((view_overall_from_date != null) && (view_overall_to_date != null)) {
            try {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                Date overall_from;
                overall_from = sd.parse(this.view_overall_from_date);
                Date overall_to = sd.parse(this.view_overall_to_date);
                if (overall_from.compareTo(overall_to) < 0) {
                    try {

                        //Displaying table according to dates
                        String sql1 = "SELECT * FROM overall WHERE date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        DefaultTableModel tm = (DefaultTableModel) view_overallTable_table.getModel();
                        tm.setRowCount(0);
                        while (rs1.next()) {
                            Object o[] = {count, rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_name"), rs1.getString("weight"), rs1.getString("wastage"), rs1.getString("making_charge"), rs1.getString("quantity"), rs1.getString("quality"), rs1.getString("buy")};
                            tm.addRow(o);
                            count++;
                        }
                    } catch (Exception e) {

                    }
                    try {

                        //Getting total weight of items acc. to dates.
                        String sql1 = "SELECT SUM(weight) FROM overall WHERE date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        while (rs1.next()) {
                            if (rs1.getString(1) == null) {
                                view_overall_totWtInp_label.setText("0");
                            } else {
                                view_overall_totWtInp_label.setText(rs1.getString(1));
                            }
                        }
                    } catch (Exception e) {

                    }

                    try {

                        //Getting total number of items acc. to dates.
                        String sql2 = "SELECT COUNT(id) FROM overall WHERE date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                        PreparedStatement pat2 = con.prepareStatement(sql2);
                        ResultSet rs2 = pat2.executeQuery();
                        while (rs2.next()) {
                            view_overall_totItemInp_label.setText(rs2.getString(1));
                        }
                    } catch (Exception e) {

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                }
            } catch (Exception e) {

            }

        }

        view_overall_combined_display();
    }

    private void view_sold_date_display() {

        //For autoincrementing the no. of rows 
        int count = 1;

        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);

        if ((view_sold_from_date != null) && (view_sold_to_date != null)) {
            try {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                Date sold_from = sd.parse(this.view_sold_from_date);
                Date sold_to = sd.parse(this.view_sold_to_date);
                if (sold_from.compareTo(sold_to) < 0) {
                    try {

                        //Displaying table according to dates
                        String sql1 = "SELECT * FROM sold WHERE date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        DefaultTableModel tm = (DefaultTableModel) view_soldTable_table.getModel();
                        ResultSetMetaData rsmd = rs1.getMetaData();
                        rs1.last();
                        view_sold_raw_data = new String[rs1.getRow()][rsmd.getColumnCount()];
                        rs1.beforeFirst();
                        tm.setRowCount(0);
                        while (rs1.next()) {
                            String obj[] = {Integer.toString(count), rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_type"), rs1.getString("ornament_name"), rs1.getString("quality"), rs1.getString("making_charge"), rs1.getString("weight"), rs1.getString("wastage"), rs1.getString("quantity"), rs1.getString("buy"), rs1.getString("barcode"), rs1.getString("status"), rs1.getString("snapshot")};
                            view_sold_raw_data[count - 1] = obj;
                            Object o[] = {count, rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_name"), rs1.getString("weight"), rs1.getString("quantity"), rs1.getString("barcode"), returnLabel};
                            tm.addRow(o);
                            count++;
                        }
                    } catch (Exception e) {

                    }
                    try {

                        //Getting total weight of items acc. to dates.
                        String sql1 = "SELECT SUM(weight) FROM sold WHERE date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        while (rs1.next()) {
                            if (rs1.getString(1) == null) {
                                view_sold_totWtInp_label.setText("0");
                            } else {
                                view_sold_totWtInp_label.setText(rs1.getString(1));
                            }
                        }
                    } catch (Exception e) {

                    }

                    try {

                        //Getting total number of items acc. to dates.
                        String sql2 = "SELECT COUNT(id) FROM sold WHERE date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                        PreparedStatement pat2 = con.prepareStatement(sql2);
                        ResultSet rs2 = pat2.executeQuery();
                        while (rs2.next()) {
                            view_sold_totItemInp_label.setText(rs2.getString(1));
                        }
                    } catch (Exception e) {

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                }
            } catch (Exception e) {

            }
        }
        view_sold_combined_display();
    }

    private void view_balance_date_display() {

        //For autoincrementing the no. of rows 
        int count = 1;

        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);

        if ((view_balance_from_date != null) && (view_balance_to_date != null)) {
            try {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                Date balance_from = sd.parse(this.view_balance_from_date);
                Date balance_to = sd.parse(this.view_balance_to_date);
                if (balance_from.compareTo(balance_to) < 0) {
                    try {

                        //Displaying table according to dates
                        String sql1 = "SELECT * FROM balance WHERE date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        DefaultTableModel tm = (DefaultTableModel) view_balanceTable_table.getModel();
                        ResultSetMetaData rsmd = rs1.getMetaData();
                        rs1.last();
                        view_balance_raw_data = new String[rs1.getRow()][rsmd.getColumnCount()];
                        rs1.beforeFirst();

                        tm.setRowCount(0);
                        while (rs1.next()) {
                            String obj[] = {Integer.toString(count), rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_type"), rs1.getString("ornament_name"), rs1.getString("quality"), rs1.getString("making_charge"), rs1.getString("weight"), rs1.getString("wastage"), rs1.getString("quantity"), rs1.getString("buy"), rs1.getString("barcode"), rs1.getString("status"), rs1.getString("snapshot")};
                            view_balance_raw_data[count - 1] = obj;
                            Object o[] = {count, rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_name"), rs1.getString("weight"), rs1.getString("quantity"), rs1.getString("barcode"), imageLabel};
                            tm.addRow(o);
                            count++;
                        }
                    } catch (Exception e) {

                    }
                    try {

                        //Getting total weight of items acc. to dates.
                        String sql1 = "SELECT SUM(weight) FROM balance WHERE date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        while (rs1.next()) {
                            if (rs1.getString(1) == null) {
                                view_balance_totWtInp_label.setText("0");
                            } else {
                                view_balance_totWtInp_label.setText(rs1.getString(1));
                            }
                        }
                    } catch (Exception e) {

                    }

                    try {

                        //Getting total number of items acc. to dates.
                        String sql2 = "SELECT COUNT(id) FROM balance WHERE date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                        PreparedStatement pat2 = con.prepareStatement(sql2);
                        ResultSet rs2 = pat2.executeQuery();
                        while (rs2.next()) {
                            view_balance_totItemInp_label.setText(rs2.getString(1));
                        }
                    } catch (Exception e) {

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                }
            } catch (Exception e) {

            }
        }
        view_balance_combined_display();
    }

    private void view_return_date_display() {

        //For autoincrementing the no. of rows 
        int count = 1;

        if ((view_return_from_date != null) && (view_return_to_date != null)) {
            try {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                Date return_from = sd.parse(this.view_return_from_date);
                Date return_to = sd.parse(this.view_return_to_date);
                if (return_from.compareTo(return_to) < 0) {
                    try {

                        //Displaying table according to dates
                        String sql1 = "SELECT * FROM return_table WHERE date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        DefaultTableModel tm = (DefaultTableModel) view_returnTable_table.getModel();
                        tm.setRowCount(0);
                        while (rs1.next()) {
                            Object o[] = {count, rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_name"), rs1.getString("quality"), rs1.getString("weight"), rs1.getString("buy")};
                            tm.addRow(o);
                            count++;
                        }
                    } catch (Exception e) {

                    }
                    try {

                        //Getting total weight of items acc. to dates.
                        String sql1 = "SELECT SUM(weight) FROM return_table WHERE date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pat1.executeQuery();
                        while (rs1.next()) {
                            if (rs1.getString(1) == null) {
                                view_return_totWtInp_label.setText("0");
                            } else {
                                view_return_totWtInp_label.setText(rs1.getString(1));
                            }
                        }
                    } catch (Exception e) {

                    }

                    try {

                        //Getting total number of items acc. to dates.
                        String sql2 = "SELECT COUNT(id) FROM return_table WHERE date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                        PreparedStatement pat2 = con.prepareStatement(sql2);
                        ResultSet rs2 = pat2.executeQuery();
                        while (rs2.next()) {
                            view_return_totItemInp_label.setText(rs2.getString(1));
                        }
                    } catch (Exception e) {

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                }
            } catch (Exception e) {

            }
        }
        view_return_combined_display();
    }

    //Displaying contents according to both selOrnament and dates
    private void view_overall_combined_display() {

        //For autoincrementing the no. of rows 
        int count = 1;

        if ((view_overall_ornament_type_data != null) && (view_overall_from_date != null) && (view_overall_to_date != null)) {
            if (view_overall_ornament_type_data != "Select the Ornament") {
                try {
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    Date overall_from = sd.parse(this.view_overall_from_date);
                    Date overall_to = sd.parse(this.view_overall_to_date);
                    if (overall_from.compareTo(overall_to) < 0) {
                        try {

                            //Displaying table according to selOrnament and dates
                            String sql1 = "SELECT * FROM overall WHERE ornament_type = " + "'" + view_overall_ornament_type_data + "' AND date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            DefaultTableModel tm = (DefaultTableModel) view_overallTable_table.getModel();
                            tm.setRowCount(0);
                            while (rs1.next()) {
                                Object o[] = {count, rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_name"), rs1.getString("weight"), rs1.getString("wastage"), rs1.getString("making_charge"), rs1.getString("quantity"), rs1.getString("quality"), rs1.getString("buy")};
                                tm.addRow(o);
                                count++;
                            }
                        } catch (Exception e) {

                        }

                        try {

                            //Getting total weight of items acc. to selOrnament and dates.
                            String sql1 = "SELECT SUM(weight) FROM overall WHERE ornament_type = " + "'" + view_overall_ornament_type_data + "' AND date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            while (rs1.next()) {
                                if (rs1.getString(1) == null) {
                                    view_overall_totWtInp_label.setText("0");
                                } else {
                                    view_overall_totWtInp_label.setText(rs1.getString(1));
                                }
                            }
                        } catch (Exception e) {

                        }

                        try {

                            //Getting total number of items acc. to selOrnament and dates.
                            String sql2 = "SELECT COUNT(id) FROM overall WHERE ornament_type = " + "'" + view_overall_ornament_type_data + "' AND date >=" + "'" + view_overall_from_date + "'  AND date <= " + "'" + view_overall_to_date + "'";

                            PreparedStatement pat2 = con.prepareStatement(sql2);
                            ResultSet rs2 = pat2.executeQuery();
                            while (rs2.next()) {
                                view_overall_totItemInp_label.setText(rs2.getString(1));
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void view_sold_combined_display() {

        //For autoincrementing the no. of rows 
        int count = 1;

        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);

        if ((view_sold_ornament_type_data != null) && (view_sold_from_date != null) && (view_sold_to_date != null)) {

            if (view_sold_ornament_type_data != "Select the Ornament") {
                try {
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    Date sold_from = sd.parse(this.view_sold_from_date);
                    Date sold_to = sd.parse(this.view_sold_to_date);
                    if (sold_from.compareTo(sold_to) < 0) {
                        try {

                            //Displaying table according to selOrnament and dates
                            String sql1 = "SELECT * FROM sold WHERE ornament_type = " + "'" + view_sold_ornament_type_data + "' AND date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            DefaultTableModel tm = (DefaultTableModel) view_soldTable_table.getModel();
                            ResultSetMetaData rsmd = rs1.getMetaData();
                            rs1.last();
                            view_sold_raw_data = new String[rs1.getRow()][rsmd.getColumnCount()];
                            rs1.beforeFirst();
                            tm.setRowCount(0);
                            while (rs1.next()) {
                                String obj[] = {Integer.toString(count), rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_type"), rs1.getString("ornament_name"), rs1.getString("quality"), rs1.getString("making_charge"), rs1.getString("weight"), rs1.getString("wastage"), rs1.getString("quantity"), rs1.getString("buy"), rs1.getString("barcode"), rs1.getString("status"), rs1.getString("snapshot")};
                                view_sold_raw_data[count - 1] = obj;
                                Object o[] = {count, rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_name"), rs1.getString("weight"), rs1.getString("quantity"), rs1.getString("barcode"), returnLabel};
                                tm.addRow(o);
                                count++;
                            }
                        } catch (Exception e) {

                        }

                        try {

                            //Getting total weight of items acc. to selOrnament and dates.
                            String sql1 = "SELECT SUM(weight) FROM sold WHERE ornament_type = " + "'" + view_sold_ornament_type_data + "' AND date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            while (rs1.next()) {
                                if (rs1.getString(1) == null) {
                                    view_sold_totWtInp_label.setText("0");
                                } else {
                                    view_sold_totWtInp_label.setText(rs1.getString(1));
                                }
                            }
                        } catch (Exception e) {

                        }

                        try {

                            //Getting total number of items acc. to selOrnament and dates.
                            String sql2 = "SELECT COUNT(id) FROM sold WHERE ornament_type = " + "'" + view_sold_ornament_type_data + "' AND date >=" + "'" + view_sold_from_date + "'  AND date <= " + "'" + view_sold_to_date + "'";

                            PreparedStatement pat2 = con.prepareStatement(sql2);
                            ResultSet rs2 = pat2.executeQuery();
                            while (rs2.next()) {
                                view_sold_totItemInp_label.setText(rs2.getString(1));
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void view_balance_combined_display() {

        //For autoincrementing the no. of rows 
        int count = 1;

        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);

        if ((view_balance_ornament_type_data != null) && (view_balance_from_date != null) && (view_balance_to_date != null)) {
            if (view_balance_ornament_type_data != "Select the Ornament") {
                try {
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    Date balance_from = sd.parse(this.view_balance_from_date);
                    Date balance_to = sd.parse(this.view_balance_to_date);
                    if (balance_from.compareTo(balance_to) < 0) {
                        try {

                            //Displaying table according to selOrnament and dates
                            String sql1 = "SELECT * FROM balance WHERE ornament_type = " + "'" + view_balance_ornament_type_data + "' AND date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            DefaultTableModel tm = (DefaultTableModel) view_balanceTable_table.getModel();
                            ResultSetMetaData rsmd = rs1.getMetaData();
                            rs1.last();
                            view_balance_raw_data = new String[rs1.getRow()][rsmd.getColumnCount()];
                            rs1.beforeFirst();
                            tm.setRowCount(0);
                            while (rs1.next()) {
                                String obj[] = {Integer.toString(count), rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_type"), rs1.getString("ornament_name"), rs1.getString("quality"), rs1.getString("making_charge"), rs1.getString("weight"), rs1.getString("wastage"), rs1.getString("quantity"), rs1.getString("buy"), rs1.getString("barcode"), rs1.getString("status"), rs1.getString("snapshot")};
                                view_balance_raw_data[count - 1] = obj;
                                Object o[] = {count, rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_name"), rs1.getString("weight"), rs1.getString("quantity"), rs1.getString("barcode"), imageLabel};
                                tm.addRow(o);
                                count++;
                            }
                        } catch (Exception e) {

                        }

                        try {

                            //Getting total weight of items acc. to selOrnament and dates.
                            String sql1 = "SELECT SUM(weight) FROM balance WHERE ornament_type = " + "'" + view_balance_ornament_type_data + "' AND date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            while (rs1.next()) {
                                if (rs1.getString(1) == null) {
                                    view_balance_totWtInp_label.setText("0");
                                } else {
                                    view_balance_totWtInp_label.setText(rs1.getString(1));
                                }
                            }
                        } catch (Exception e) {

                        }

                        try {

                            //Getting total number of items acc. to selOrnament and dates.
                            String sql2 = "SELECT COUNT(id) FROM balance WHERE ornament_type = " + "'" + view_balance_ornament_type_data + "' AND date >=" + "'" + view_balance_from_date + "'  AND date <= " + "'" + view_balance_to_date + "'";

                            PreparedStatement pat2 = con.prepareStatement(sql2);
                            ResultSet rs2 = pat2.executeQuery();
                            while (rs2.next()) {
                                view_balance_totItemInp_label.setText(rs2.getString(1));
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void view_return_combined_display() {

        //For autoincrementing the no. of rows 
        int count = 1;

        if ((view_return_ornament_type_data != null) && (view_return_from_date != null) && (view_return_to_date != null)) {
            if (view_return_ornament_type_data != "Select the Ornament") {
                try {
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    Date return_from = sd.parse(this.view_return_from_date);
                    Date return_to = sd.parse(this.view_return_to_date);
                    if (return_from.compareTo(return_to) < 0) {
                        try {

                            //Displaying table according to selOrnament and dates
                            String sql1 = "SELECT * FROM return_table WHERE ornament_type = " + "'" + view_return_ornament_type_data + "' AND date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            DefaultTableModel tm = (DefaultTableModel) view_returnTable_table.getModel();
                            tm.setRowCount(0);
                            while (rs1.next()) {
                                Object o[] = {count, rs1.getString("date"), rs1.getString("chase_no"), rs1.getString("ornament_name"), rs1.getString("quality"), rs1.getString("weight"), rs1.getString("buy")};
                                tm.addRow(o);
                                count++;
                            }
                        } catch (Exception e) {

                        }

                        try {

                            //Getting total weight of items acc. to selOrnament and dates.
                            String sql1 = "SELECT SUM(weight) FROM return_table WHERE ornament_type = " + "'" + view_return_ornament_type_data + "' AND date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                            PreparedStatement pat1 = con.prepareStatement(sql1);
                            ResultSet rs1 = pat1.executeQuery();
                            while (rs1.next()) {
                                if (rs1.getString(1) == null) {
                                    view_return_totWtInp_label.setText("0");
                                } else {
                                    view_return_totWtInp_label.setText(rs1.getString(1));
                                }
                            }
                        } catch (Exception e) {

                        }

                        try {

                            //Getting total number of items acc. to selOrnament and dates.
                            String sql2 = "SELECT COUNT(id) FROM return_table WHERE ornament_type = " + "'" + view_return_ornament_type_data + "' AND date >=" + "'" + view_return_from_date + "'  AND date <= " + "'" + view_return_to_date + "'";

                            PreparedStatement pat2 = con.prepareStatement(sql2);
                            ResultSet rs2 = pat2.executeQuery();
                            while (rs2.next()) {
                                view_return_totItemInp_label.setText(rs2.getString(1));
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please check...From date is greater than To date");
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void view_default_date() {
        try {
            Date date = new Date();
            //Setting current date to the to datechooser
            view_overall_to_datechooser.setDate(date);
            view_sold_to_datechooser.setDate(date);
            view_balance_to_datechooser.setDate(date);
            view_return_to_datechooser.setDate(date);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            default_to_date = df.format(date);

            //Assigning values to the to dates
            view_overall_to_date = default_to_date;
            view_sold_to_date = default_to_date;
            view_balance_to_date = default_to_date;
            view_return_to_date = default_to_date;

            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            view_overall_from_datechooser.setDate(cal.getTime());
            view_sold_from_datechooser.setDate(cal.getTime());
            view_balance_from_datechooser.setDate(cal.getTime());
            view_return_from_datechooser.setDate(cal.getTime());

            default_from_date = simpleDate.format(cal.getTime());

            //Assigning values to the to dates
            view_overall_from_date = default_from_date;
            view_sold_from_date = default_from_date;
            view_balance_from_date = default_from_date;
            view_return_from_date = default_from_date;
        } catch (Exception e) {

        }
    }

    //  Function performed when return icon is clicked.
    private void view_sold_return(int view_serial_no) {
        String view_sold_date = view_sold_raw_data[view_serial_no - 1][1];
        String view_sold_chaseNo = view_sold_raw_data[view_serial_no - 1][2];
        String view_sold_ornament_type = view_sold_raw_data[view_serial_no - 1][3];
        String view_sold_ornamentName = view_sold_raw_data[view_serial_no - 1][4];
        String view_sold_quality = view_sold_raw_data[view_serial_no - 1][5];
        String view_sold_making_charge = view_sold_raw_data[view_serial_no - 1][6];
        String view_sold_weight = view_sold_raw_data[view_serial_no - 1][7];
        String view_sold_wastage = view_sold_raw_data[view_serial_no - 1][8];
        String view_sold_quantity = view_sold_raw_data[view_serial_no - 1][9];
        String view_sold_buy = view_sold_raw_data[view_serial_no - 1][10];
        String view_sold_barcode = view_sold_raw_data[view_serial_no - 1][11];
        String view_sold_status = view_sold_raw_data[view_serial_no - 1][12];
        String view_sold_snapshot = view_sold_raw_data[view_serial_no - 1][13];
        String view_sold_chasenum_check = null;

        if (view_sold_chaseNo != null) {
            try {
                String sql = "SELECT COUNT(chase_no) FROM balance WHERE chase_no = " + "'" + view_sold_chaseNo + "'";

                pat = con.prepareStatement(sql);
                ResultSet rs = pat.executeQuery();
                while (rs.next()) {
                    view_sold_chasenum_check = rs.getString(1);
                }
                if (view_sold_chasenum_check.equals("0")) {
                    try {
//                                String sql1="INSERT INTO balance"
//                                        + "(id,date, chase_no, ornament_type,ornament_name,quality,making_charge, weight,wastage, quantity,buy,barcode,status,snapshot)SELECT id,date, chase_no, ornament_type,ornament_name,quality,making_charge, weight,wastage, quantity,buy,barcode,status,snapshot FROM sold WHERE snapshot='" + view_sold_snapshot + "'";
//                                         pat=con.prepareStatement(sql1);
//                                         pat.executeQuery();
//                                        JOptionPane.showMessageDialog(null,"returned successfully");

                        String sql1 = "INSERT INTO balance"
                                + "(date, chase_no, ornament_type,ornament_name,quality,making_charge, weight,wastage, quantity,buy,barcode,status)" //snapshot
                                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
//                                          // + "VALUES(view_sold_date,view_sold_chaseNo,view_sold_ornament_type,view_sold_ornamentName,view_sold_quality,view_sold_making_charge,view_sold_weight,view_sold_wastage,view_sold_quantity,view_sold_buy,view_sold_barcode,view_sold_status,view_sold_snapshot)";
                        pat = con.prepareStatement(sql1);
                        pat.setString(1, view_sold_date);
                        pat.setString(2, view_sold_chaseNo);
                        pat.setString(3, view_sold_ornament_type);
                        pat.setString(4, view_sold_ornamentName);
                        pat.setString(5, view_sold_quality);
                        pat.setString(6, view_sold_making_charge);
                        pat.setString(7, view_sold_weight);
                        pat.setString(8, view_sold_wastage);
                        pat.setString(9, view_sold_quantity);
                        pat.setString(10, view_sold_buy);
                        pat.setString(11, view_sold_barcode);
                        pat.setInt(12, 0);

                        pat.executeUpdate();

                        String changeStatus = "UPDATE overall SET status = 0 WHERE chase_no=" + "'" + view_sold_chaseNo + "'";
                        pat = con.prepareStatement(changeStatus);
                        pat.executeUpdate();

                        String sql2 = "DELETE FROM sold  WHERE snapshot = " + "'" + view_sold_snapshot + "'";
                        PreparedStatement pat1 = con.prepareStatement(sql2);
                        pat1.executeUpdate();

                        //For refreshing after returning the item and deleting it from sold table       
                        DefaultTableModel dm = (DefaultTableModel) view_soldTable_table.getModel();
                        dm.setRowCount(0);
                        view_sold_default_display();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                } else if (view_sold_chasenum_check.equals("1")) {
                    try {
                        String sql2 = "UPDATE balance SET quantity = quantity + " + view_sold_quantity + " WHERE chase_no=" + "'" + view_sold_chaseNo + "'";
                        pat = con.prepareStatement(sql2);
                        pat.executeUpdate();

                        String sql1 = "DELETE FROM sold  WHERE snapshot = " + "'" + view_sold_snapshot + "'";
                        PreparedStatement pat1 = con.prepareStatement(sql1);
                        pat1.executeUpdate();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            } catch (Exception e) {
                //  JOptionPane.showMessageDialog(null,e);
            }
        }
    }

}
