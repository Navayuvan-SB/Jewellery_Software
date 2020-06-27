/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyphersource.jewellery_software;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;



/**
 *
 * @author ghost
 */
public class MainWindow extends javax.swing.JFrame {
    Connection con=null,con1=null,con2=null;
    PreparedStatement pat = null;
    Statement stmt=null;
    ResultSet rs = null;
    
    String default_from_date;
    String default_to_date;
    String new_normal_display_format;
    
    //Getting selectedItem from dropdown
    String view_ornament_type1_data;
    String view_ornament_type2_data = "Select the Ornament";
    String view_ornament_type3_data;
    String view_ornament_type4_data;
     
    //Date obtained after formating
    String view_from1_date = default_from_date;
    String view_to1_date = default_to_date;
    String view_from2_date = default_from_date;
    String view_to2_date = default_to_date;
    String view_from3_date = default_from_date;
    String view_to3_date = default_to_date;
    String view_from4_date = default_from_date;
    String view_to4_date = default_to_date;
    
    //  Image Icon instance
    private ImageIcon imageIcon,returnIcon; 
      
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        

//     Initialize Image Icon
         imageIcon = new ImageIcon("/home/logida/jewel/Jewellery_Software/src/main/java/image/p2.jpg");
         returnIcon= new ImageIcon("/home/logida/jewel/Jewellery_Software/src/main/java/image/return1.png");
 

//      Override Cell render of Image column
          view_table2_table.getColumn("RT").setCellRenderer(new MyCellRenderer());
          view_table3_table.getColumn("RP").setCellRenderer(new MyCellRenderer());
       
        //method for displaying dropdown elements from database
        view_dropdown1_display();
        view_dropdown2_display();
        view_dropdown3_display();
        view_dropdown4_display();
        
        //method for default display of from and to date
        view_default_date();
        
        //method for default display of contents from database
        view_default1_display();
        view_default2_display();
        view_default3_display();
        view_default4_display();
          
        //To change the color of the vertical gridlines of tables
        
        view_table1_table.setShowVerticalLines(true);
        view_table1_table.setGridColor(Color.LIGHT_GRAY);
        
        
        view_table2_table.setShowVerticalLines(true);
        view_table2_table.setGridColor(Color.LIGHT_GRAY);
        
        view_table3_table.setShowVerticalLines(true);
        view_table3_table.setGridColor(Color.LIGHT_GRAY);
        
        view_table4_table.setShowVerticalLines(true);
        view_table4_table.setGridColor(Color.LIGHT_GRAY);
        
        // T0 scroll the table contents without using scrollbar for tables
        
        view_tablearea1_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0,0));
        view_tablearea1_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0,0));
        
        view_tablearea2_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0,0));
        view_tablearea2_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0,0));
        
        view_tablearea3_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0,0));
        view_tablearea3_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0,0));
        
        view_tablearea4_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0,0));
        view_tablearea4_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0,0));
        
        //Styling table header
        
        JTableHeader Theader=view_table1_table.getTableHeader();
        JTableHeader Theaderone=view_table2_table.getTableHeader();
        JTableHeader Theadertwo=view_table3_table.getTableHeader();
        JTableHeader Theaderthree=view_table4_table.getTableHeader();
        
        //Tableheader font styling
        
        Theader.setFont(new Font("Ubuntu",Font.BOLD,18));
        Theaderone.setFont(new Font("Ubuntu",Font.BOLD,18));
        Theadertwo.setFont(new Font("Ubuntu",Font.BOLD,18));
        Theaderthree.setFont(new Font("Ubuntu",Font.BOLD,18));
         
        //tableheader text color
        
        Theader.setForeground(Color.black);
        Theaderone.setForeground(Color.black);
        Theadertwo.setForeground(Color.black);
        Theaderthree.setForeground(Color.black);
         
        //Tableheader bg color,header thickness, dimension ( ht& wth ), text alignment
        
        Theader.setDefaultRenderer(new HeaderColor());
        Theader.setPreferredSize(new Dimension(50,50));
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
   
        Theaderone.setDefaultRenderer(new HeaderColor());
        Theaderone.setPreferredSize(new Dimension(50,50));
        ((DefaultTableCellRenderer)Theaderone.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
  
        Theadertwo.setDefaultRenderer(new HeaderColor());
        Theadertwo.setPreferredSize(new Dimension(50,50));
        ((DefaultTableCellRenderer)Theadertwo.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        Theaderthree.setDefaultRenderer(new HeaderColor());
        Theaderthree.setPreferredSize(new Dimension(50,50));
        ((DefaultTableCellRenderer)Theaderthree.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        // For setting all the values in  the column to center
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        
        //For table 1
        for(int x=0;x<10;x++){
            view_table1_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer ); 
        }
        //For table 2 & 3
        for(int x=0;x<7;x++){
            view_table2_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
            view_table3_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }
        //For table 4
        for(int x=0;x<7;x++){
            view_table4_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer ); 
        }
    
        // Balance table - Getting the values of the selected rows for printing
        
        view_table3_table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int col = view_table3_table.columnAtPoint(evt.getPoint());
                if (col ==7){
                                int setRow = view_table3_table.getSelectedRow();
                                String[] value=new String[col];
                                for(int i=0;i<value.length;i++){
                                        value[i] = view_table3_table.getModel().getValueAt(setRow,i).toString();
                                        System.out.println(value[i]);
                                }
                            }
            }
        });
        
        // Sold table - Getting the values of the selected rows for printing
        
        view_table2_table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = view_table2_table.columnAtPoint(evt.getPoint());
                if (column ==7){
                                int setRow = view_table2_table.getSelectedRow();                             
                                String view_return_value = view_table2_table.getModel().getValueAt(setRow,6).toString();
                                view_sold_return(view_return_value);//System.out.println(value);
                                
                }
            }
        });

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        view_total_panel = new javax.swing.JPanel();
        view_areaOne_panel = new javax.swing.JPanel();
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
        view_totWt1_label = new javax.swing.JLabel();
        view_totWtInp1_label = new javax.swing.JLabel();
        view_totItem1_label = new javax.swing.JLabel();
        view_totItemInp1_label = new javax.swing.JLabel();
        view_selOrnament1_dropdown = new javax.swing.JComboBox<>();
        view_colon11_label = new javax.swing.JLabel();
        view_colon12_label = new javax.swing.JLabel();
        view_tablearea1_scrollpane = new javax.swing.JScrollPane();
        view_table1_table = new javax.swing.JTable();
        view_datelimit1_panel = new javax.swing.JPanel();
        view_from1_datechooser = new com.toedter.calendar.JDateChooser();
        view_from1_label = new javax.swing.JLabel();
        view_to1_datechooser = new com.toedter.calendar.JDateChooser();
        view_to1_label = new javax.swing.JLabel();
        view_datelimitIcon1_label = new javax.swing.JLabel();
        view_soldCont_panel = new javax.swing.JPanel();
        view_totWt2_label = new javax.swing.JLabel();
        view_totWtInp2_label = new javax.swing.JLabel();
        view_totItem2_label = new javax.swing.JLabel();
        view_totItemInp2_label = new javax.swing.JLabel();
        view_selOrnament2_dropdown = new javax.swing.JComboBox<>();
        view_colon21_label = new javax.swing.JLabel();
        view_colon22_label = new javax.swing.JLabel();
        view_tablearea2_scrollpane = new javax.swing.JScrollPane();
        view_table2_table = new javax.swing.JTable();
        view_datelimit2_panel = new javax.swing.JPanel();
        view_from2_datechooser = new com.toedter.calendar.JDateChooser();
        view_from2_label = new javax.swing.JLabel();
        view_to2_datechooser = new com.toedter.calendar.JDateChooser();
        view_to2_label = new javax.swing.JLabel();
        view_datelimitIcon2_label = new javax.swing.JLabel();
        view_balanceCont_panel = new javax.swing.JPanel();
        view_totWt3_label = new javax.swing.JLabel();
        view_totWtInp3_label = new javax.swing.JLabel();
        view_totItem3_label = new javax.swing.JLabel();
        view_totItemInp3_label = new javax.swing.JLabel();
        view_selOrnament3_dropdown = new javax.swing.JComboBox<>();
        view_colon31_label = new javax.swing.JLabel();
        view_colon32_label = new javax.swing.JLabel();
        view_tablearea3_scrollpane = new javax.swing.JScrollPane();
        view_table3_table = new javax.swing.JTable();
        view_datelimit3_panel = new javax.swing.JPanel();
        view_from3_datechooser = new com.toedter.calendar.JDateChooser();
        view_from3_label = new javax.swing.JLabel();
        view_to3_datechooser = new com.toedter.calendar.JDateChooser();
        view_to3_label = new javax.swing.JLabel();
        view_datelimitIcon3_label = new javax.swing.JLabel();
        view_returnCont_panel = new javax.swing.JPanel();
        view_totWt4_label = new javax.swing.JLabel();
        view_totWtInp4_label = new javax.swing.JLabel();
        view_totItem4_label = new javax.swing.JLabel();
        view_totItemInp4_label = new javax.swing.JLabel();
        view_selOrnament4_dropdown = new javax.swing.JComboBox<>();
        view_colon41_label = new javax.swing.JLabel();
        view_colon42_label = new javax.swing.JLabel();
        view_tablearea4_scrollpane = new javax.swing.JScrollPane();
        view_table4_table = new javax.swing.JTable();
        view_datelimit4_panel = new javax.swing.JPanel();
        view_from4_datechooser = new com.toedter.calendar.JDateChooser();
        view_from4_label = new javax.swing.JLabel();
        view_to4_datechooser = new com.toedter.calendar.JDateChooser();
        view_to4_label = new javax.swing.JLabel();
        view_datelimitIcon4_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        view_total_panel.setBackground(java.awt.Color.white);
        view_total_panel.setLayout(new java.awt.BorderLayout());

        view_areaOne_panel.setBackground(java.awt.Color.white);

        javax.swing.GroupLayout view_areaOne_panelLayout = new javax.swing.GroupLayout(view_areaOne_panel);
        view_areaOne_panel.setLayout(view_areaOne_panelLayout);
        view_areaOne_panelLayout.setHorizontalGroup(
            view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 886, Short.MAX_VALUE)
        );
        view_areaOne_panelLayout.setVerticalGroup(
            view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        view_total_panel.add(view_areaOne_panel, java.awt.BorderLayout.PAGE_START);

        view_areaTwo_panel.setBackground(java.awt.Color.white);

        view_contOne_panel.setBackground(new java.awt.Color(247, 247, 247));
        view_contOne_panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.lightGray, java.awt.Color.lightGray));

        view_overall_panel.setBackground(new java.awt.Color(247, 247, 247));
        view_overall_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseclicked(evt);
            }
        });

        view_overall_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        view_overall_label.setForeground(java.awt.Color.black);
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
                mouseclicked(evt);
            }
        });

        view_sold_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        view_sold_label.setForeground(new java.awt.Color(158, 154, 154));
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
                mouseclicked(evt);
            }
        });

        view_balance_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
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
                mouseclicked(evt);
            }
        });

        view_return_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
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
                .addGap(64, 64, 64)
                .addComponent(view_overall_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_sold_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_balance_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_return_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        view_totWt1_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totWt1_label.setForeground(java.awt.Color.gray);
        view_totWt1_label.setText("Total Weight    ");

        view_totWtInp1_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totWtInp1_label.setName(""); // NOI18N

        view_totItem1_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totItem1_label.setForeground(java.awt.Color.gray);
        view_totItem1_label.setText("Total no of Items");

        view_totItemInp1_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_selOrnament1_dropdown.setBackground(new java.awt.Color(211, 211, 211));
        view_selOrnament1_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_selOrnament1_dropdown.setForeground(java.awt.Color.gray);
        view_selOrnament1_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_selOrnament1_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_selOrnament1_dropdownview_selOrnament_dropdownActionPerformed(evt);
            }
        });

        view_colon11_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_colon11_label.setText(":");

        view_colon12_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_colon12_label.setText(":");

        view_table1_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        view_table1_table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        view_table1_table.setModel(new javax.swing.table.DefaultTableModel(
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
                false, false, false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_table1_table.setColumnSelectionAllowed(true);
        view_table1_table.setRequestFocusEnabled(false);
        view_table1_table.setRowHeight(48);
        view_table1_table.setRowMargin(0);
        view_table1_table.setShowGrid(true);
        view_table1_table.setShowHorizontalLines(false);
        view_table1_table.getTableHeader().setReorderingAllowed(false);
        view_tablearea1_scrollpane.setViewportView(view_table1_table);
        view_table1_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (view_table1_table.getColumnModel().getColumnCount() > 0) {
            view_table1_table.getColumnModel().getColumn(0).setPreferredWidth(45);
            view_table1_table.getColumnModel().getColumn(1).setPreferredWidth(110);
            view_table1_table.getColumnModel().getColumn(2).setPreferredWidth(105);
            view_table1_table.getColumnModel().getColumn(3).setPreferredWidth(150);
            view_table1_table.getColumnModel().getColumn(4).setPreferredWidth(35);
            view_table1_table.getColumnModel().getColumn(5).setPreferredWidth(35);
            view_table1_table.getColumnModel().getColumn(6).setPreferredWidth(40);
            view_table1_table.getColumnModel().getColumn(7).setPreferredWidth(45);
            view_table1_table.getColumnModel().getColumn(8).setPreferredWidth(50);
        }

        view_datelimit1_panel.setBackground(java.awt.Color.white);
        view_datelimit1_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_from1_datechooser.setBackground(java.awt.Color.white);
        view_from1_datechooser.setDateFormatString("dd-MM-yyyy");
        view_from1_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_from1_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_fromDate_propertyChange(evt);
            }
        });
        view_datelimit1_panel.add(view_from1_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        view_from1_label.setBackground(java.awt.Color.white);
        view_from1_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_from1_label.setText("From");
        view_datelimit1_panel.add(view_from1_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 30));

        view_to1_datechooser.setBackground(java.awt.Color.white);
        view_to1_datechooser.setDateFormatString("dd-MM-yyyy");
        view_to1_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_to1_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_todate_propertChange(evt);
            }
        });
        view_datelimit1_panel.add(view_to1_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_to1_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_to1_label.setText("To");
        view_datelimit1_panel.add(view_to1_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        view_datelimitIcon1_label.setIcon(new javax.swing.ImageIcon("/home/logida/Documents/Rectangle 26 (1).png")); // NOI18N
        view_datelimitIcon1_label.setText("jLabel6");
        view_datelimit1_panel.add(view_datelimitIcon1_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_overallCont_panelLayout = new javax.swing.GroupLayout(view_overallCont_panel);
        view_overallCont_panel.setLayout(view_overallCont_panelLayout);
        view_overallCont_panelLayout.setHorizontalGroup(
            view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(view_tablearea1_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_totWt1_label, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(view_totItem1_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_colon11_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_colon12_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_totItemInp1_label, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(view_totWtInp1_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_selOrnament1_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_datelimit1_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        view_overallCont_panelLayout.setVerticalGroup(
            view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_totItem1_label)
                            .addComponent(view_colon11_label)
                            .addComponent(view_totItemInp1_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_totWt1_label)
                            .addComponent(view_colon12_label)
                            .addComponent(view_totWtInp1_label)))
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(view_selOrnament1_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(view_datelimit1_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(view_tablearea1_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        view_contTwo_panel.add(view_overallCont_panel, "card2");

        view_soldCont_panel.setBackground(java.awt.Color.white);

        view_totWt2_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totWt2_label.setForeground(java.awt.Color.gray);
        view_totWt2_label.setText("Total Weight    ");

        view_totWtInp2_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totWtInp2_label.setName(""); // NOI18N

        view_totItem2_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totItem2_label.setForeground(java.awt.Color.gray);
        view_totItem2_label.setText("Total no of Items");

        view_totItemInp2_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_selOrnament2_dropdown.setBackground(new java.awt.Color(211, 211, 211));
        view_selOrnament2_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_selOrnament2_dropdown.setForeground(java.awt.Color.gray);
        view_selOrnament2_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_selOrnament2_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_selOrnament2_dropdownview_selOrnament_dropdownActionPerformed(evt);
            }
        });

        view_colon21_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_colon21_label.setText(":");

        view_colon22_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_colon22_label.setText(":");

        view_table2_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        view_table2_table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        view_table2_table.setModel(new javax.swing.table.DefaultTableModel(
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
        view_table2_table.setColumnSelectionAllowed(true);
        view_table2_table.setRequestFocusEnabled(false);
        view_table2_table.setRowHeight(48);
        view_table2_table.setRowMargin(0);
        view_table2_table.setShowGrid(true);
        view_table2_table.setShowHorizontalLines(false);
        view_table2_table.getTableHeader().setReorderingAllowed(false);
        view_tablearea2_scrollpane.setViewportView(view_table2_table);
        view_table2_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (view_table2_table.getColumnModel().getColumnCount() > 0) {
            view_table2_table.getColumnModel().getColumn(0).setPreferredWidth(35);
            view_table2_table.getColumnModel().getColumn(1).setPreferredWidth(90);
            view_table2_table.getColumnModel().getColumn(2).setPreferredWidth(105);
            view_table2_table.getColumnModel().getColumn(3).setPreferredWidth(150);
            view_table2_table.getColumnModel().getColumn(4).setPreferredWidth(35);
            view_table2_table.getColumnModel().getColumn(5).setPreferredWidth(45);
            view_table2_table.getColumnModel().getColumn(6).setPreferredWidth(75);
        }

        view_datelimit2_panel.setBackground(java.awt.Color.white);
        view_datelimit2_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_from2_datechooser.setBackground(java.awt.Color.white);
        view_from2_datechooser.setDateFormatString("dd-MM-yyyy");
        view_from2_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_from2_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_from2_datechooserview_fromDate_propertyChange(evt);
            }
        });
        view_datelimit2_panel.add(view_from2_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        view_from2_label.setBackground(java.awt.Color.white);
        view_from2_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_from2_label.setText("From");
        view_datelimit2_panel.add(view_from2_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 30));

        view_to2_datechooser.setBackground(java.awt.Color.white);
        view_to2_datechooser.setDateFormatString("dd-MM-yyyy");
        view_to2_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_to2_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_to2_datechooserview_todate_propertChange(evt);
            }
        });
        view_datelimit2_panel.add(view_to2_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_to2_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_to2_label.setText("To");
        view_datelimit2_panel.add(view_to2_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        view_datelimitIcon2_label.setIcon(new javax.swing.ImageIcon("/home/logida/Documents/Rectangle 26 (1).png")); // NOI18N
        view_datelimitIcon2_label.setText("jLabel6");
        view_datelimit2_panel.add(view_datelimitIcon2_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_soldCont_panelLayout = new javax.swing.GroupLayout(view_soldCont_panel);
        view_soldCont_panel.setLayout(view_soldCont_panelLayout);
        view_soldCont_panelLayout.setHorizontalGroup(
            view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(view_tablearea2_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_totWt2_label, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(view_totItem2_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_colon21_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_colon22_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_totItemInp2_label, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(view_totWtInp2_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_selOrnament2_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_datelimit2_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        view_soldCont_panelLayout.setVerticalGroup(
            view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_totItem2_label)
                            .addComponent(view_colon21_label)
                            .addComponent(view_totItemInp2_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_totWt2_label)
                            .addComponent(view_colon22_label)
                            .addComponent(view_totWtInp2_label)))
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(view_selOrnament2_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(view_datelimit2_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(view_tablearea2_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        view_contTwo_panel.add(view_soldCont_panel, "card2");

        view_balanceCont_panel.setBackground(java.awt.Color.white);

        view_totWt3_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totWt3_label.setForeground(java.awt.Color.gray);
        view_totWt3_label.setText("Total Weight    ");

        view_totWtInp3_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totWtInp3_label.setName(""); // NOI18N

        view_totItem3_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totItem3_label.setForeground(java.awt.Color.gray);
        view_totItem3_label.setText("Total no of Items");

        view_totItemInp3_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_selOrnament3_dropdown.setBackground(new java.awt.Color(211, 211, 211));
        view_selOrnament3_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_selOrnament3_dropdown.setForeground(java.awt.Color.gray);
        view_selOrnament3_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_selOrnament3_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_selOrnament3_dropdownview_selOrnament_dropdownActionPerformed(evt);
            }
        });

        view_colon31_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_colon31_label.setText(":");

        view_colon32_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_colon32_label.setText(":");

        view_table3_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        view_table3_table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        view_table3_table.setModel(new javax.swing.table.DefaultTableModel(
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
                true, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_table3_table.setColumnSelectionAllowed(true);
        view_table3_table.setRequestFocusEnabled(false);
        view_table3_table.setRowHeight(45);
        view_table3_table.setRowMargin(0);
        view_table3_table.setShowGrid(true);
        view_table3_table.setShowHorizontalLines(false);
        view_table3_table.getTableHeader().setReorderingAllowed(false);
        view_tablearea3_scrollpane.setViewportView(view_table3_table);
        view_table3_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (view_table3_table.getColumnModel().getColumnCount() > 0) {
            view_table3_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            view_table3_table.getColumnModel().getColumn(1).setPreferredWidth(120);
            view_table3_table.getColumnModel().getColumn(2).setPreferredWidth(130);
            view_table3_table.getColumnModel().getColumn(3).setPreferredWidth(195);
            view_table3_table.getColumnModel().getColumn(4).setPreferredWidth(73);
            view_table3_table.getColumnModel().getColumn(5).setPreferredWidth(73);
            view_table3_table.getColumnModel().getColumn(6).setPreferredWidth(130);
        }

        view_datelimit3_panel.setBackground(java.awt.Color.white);
        view_datelimit3_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_from3_datechooser.setBackground(java.awt.Color.white);
        view_from3_datechooser.setDateFormatString("dd-MM-yyyy");
        view_from3_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_from3_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_from3_datechooserview_fromDate_propertyChange(evt);
            }
        });
        view_datelimit3_panel.add(view_from3_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        view_from3_label.setBackground(java.awt.Color.white);
        view_from3_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_from3_label.setText("From");
        view_datelimit3_panel.add(view_from3_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 30));

        view_to3_datechooser.setBackground(java.awt.Color.white);
        view_to3_datechooser.setDateFormatString("dd-MM-yyyy");
        view_to3_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_to3_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_to3_datechooserview_todate_propertChange(evt);
            }
        });
        view_datelimit3_panel.add(view_to3_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_to3_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_to3_label.setText("To");
        view_datelimit3_panel.add(view_to3_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        view_datelimitIcon3_label.setIcon(new javax.swing.ImageIcon("/home/logida/Documents/Rectangle 26 (1).png")); // NOI18N
        view_datelimitIcon3_label.setText("jLabel6");
        view_datelimit3_panel.add(view_datelimitIcon3_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_balanceCont_panelLayout = new javax.swing.GroupLayout(view_balanceCont_panel);
        view_balanceCont_panel.setLayout(view_balanceCont_panelLayout);
        view_balanceCont_panelLayout.setHorizontalGroup(
            view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(view_tablearea3_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_totWt3_label, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(view_totItem3_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_colon31_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_colon32_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_totItemInp3_label, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(view_totWtInp3_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_selOrnament3_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_datelimit3_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        view_balanceCont_panelLayout.setVerticalGroup(
            view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_totItem3_label)
                            .addComponent(view_colon31_label)
                            .addComponent(view_totItemInp3_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_totWt3_label)
                            .addComponent(view_colon32_label)
                            .addComponent(view_totWtInp3_label)))
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(view_selOrnament3_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(view_datelimit3_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(view_tablearea3_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        view_contTwo_panel.add(view_balanceCont_panel, "card5");

        view_returnCont_panel.setBackground(java.awt.Color.white);

        view_totWt4_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totWt4_label.setForeground(java.awt.Color.gray);
        view_totWt4_label.setText("Total Weight    ");

        view_totWtInp4_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totWtInp4_label.setName(""); // NOI18N

        view_totItem4_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_totItem4_label.setForeground(java.awt.Color.gray);
        view_totItem4_label.setText("Total no of Items");

        view_totItemInp4_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_selOrnament4_dropdown.setBackground(new java.awt.Color(211, 211, 211));
        view_selOrnament4_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_selOrnament4_dropdown.setForeground(java.awt.Color.gray);
        view_selOrnament4_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_selOrnament4_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_selOrnament4_dropdownview_selOrnament_dropdownActionPerformed(evt);
            }
        });

        view_colon41_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_colon41_label.setText(":");

        view_colon42_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_colon42_label.setText(":");

        view_table4_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        view_table4_table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        view_table4_table.setModel(new javax.swing.table.DefaultTableModel(
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
        view_table4_table.setColumnSelectionAllowed(true);
        view_table4_table.setRequestFocusEnabled(false);
        view_table4_table.setRowHeight(48);
        view_table4_table.setRowMargin(0);
        view_table4_table.setShowGrid(true);
        view_table4_table.setShowHorizontalLines(false);
        view_table4_table.getTableHeader().setReorderingAllowed(false);
        view_tablearea4_scrollpane.setViewportView(view_table4_table);
        view_table4_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (view_table4_table.getColumnModel().getColumnCount() > 0) {
            view_table4_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            view_table4_table.getColumnModel().getColumn(1).setPreferredWidth(120);
            view_table4_table.getColumnModel().getColumn(2).setPreferredWidth(130);
            view_table4_table.getColumnModel().getColumn(3).setPreferredWidth(195);
            view_table4_table.getColumnModel().getColumn(4).setPreferredWidth(73);
            view_table4_table.getColumnModel().getColumn(5).setPreferredWidth(73);
            view_table4_table.getColumnModel().getColumn(6).setPreferredWidth(130);
        }

        view_datelimit4_panel.setBackground(java.awt.Color.white);
        view_datelimit4_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_from4_datechooser.setBackground(java.awt.Color.white);
        view_from4_datechooser.setDateFormatString("dd-MM-yyyy");
        view_from4_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_from4_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_from4_datechooserview_fromDate_propertyChange(evt);
            }
        });
        view_datelimit4_panel.add(view_from4_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        view_from4_label.setBackground(java.awt.Color.white);
        view_from4_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_from4_label.setText("From");
        view_datelimit4_panel.add(view_from4_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 30));

        view_to4_datechooser.setBackground(java.awt.Color.white);
        view_to4_datechooser.setDateFormatString("dd-MM-yyyy");
        view_to4_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_to4_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_to4_datechooserview_todate_propertChange(evt);
            }
        });
        view_datelimit4_panel.add(view_to4_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_to4_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_to4_label.setText("To");
        view_datelimit4_panel.add(view_to4_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        view_datelimitIcon4_label.setIcon(new javax.swing.ImageIcon("/home/logida/Documents/Rectangle 26 (1).png")); // NOI18N
        view_datelimitIcon4_label.setText("jLabel6");
        view_datelimit4_panel.add(view_datelimitIcon4_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_returnCont_panelLayout = new javax.swing.GroupLayout(view_returnCont_panel);
        view_returnCont_panel.setLayout(view_returnCont_panelLayout);
        view_returnCont_panelLayout.setHorizontalGroup(
            view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(view_tablearea4_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_totWt4_label, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(view_totItem4_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_colon41_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_colon42_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_totItemInp4_label, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(view_totWtInp4_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_selOrnament4_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_datelimit4_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        view_returnCont_panelLayout.setVerticalGroup(
            view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_totItem4_label)
                            .addComponent(view_colon41_label)
                            .addComponent(view_totItemInp4_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_totWt4_label)
                            .addComponent(view_colon42_label)
                            .addComponent(view_totWtInp4_label)))
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(view_selOrnament4_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(view_datelimit4_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(view_tablearea4_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view_total_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view_total_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void view_selOrnament1_dropdownview_selOrnament_dropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_selOrnament1_dropdownview_selOrnament_dropdownActionPerformed
 
            //For autoincrementing the no. of rows 
           int count=1;
        
           view_ornament_type1_data=view_selOrnament1_dropdown.getSelectedItem().toString();
           
           if(view_ornament_type1_data!="Select the Ornament"){
 
                try{ 
                    try{
                        //Getting table values acc. to selOrnament
                        String sql="SELECT date, chase_no, ornament_type, ornament_name, quality,\n" +
                        "making_charge, weight, wastage, quantity,buy, barcode, status FROM overall\n" +
                        "WHERE ornament_type =" + "'"+  view_ornament_type1_data + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_table1_table.getModel();
                        tm.setRowCount(0);
                        while(rs.next()){
                            Object o[]={count,rs.getString("date"),rs.getString("chase_no"),rs.getString("ornament_name"),rs.getString("weight"),rs.getString("wastage"),rs.getString("making_charge"),rs.getString("quantity"),rs.getString("quality"),rs.getString("buy")};
                            tm.addRow(o);
                            count++;
                        }

                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                       //Getting total weight of items acc. to selOrnament.
                       String sql1="SELECT SUM(weight) FROM overall WHERE ornament_type = " + "'"+  view_ornament_type1_data + "'";
                       con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat1=con1.prepareStatement(sql1);
                       ResultSet rs1=pat1.executeQuery();
                       while(rs1.next()){
                            if(rs1.getString(1)==null){
                                view_totWtInp1_label.setText("0"); 
                            }
                            else{
                                view_totWtInp1_label.setText(rs1.getString(1)); 
                            }
                       } 
                      
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    } 

                    try{

                       //Getting total number of items acc. to selOrnament.
                       String sql2="SELECT COUNT(id) FROM overall WHERE ornament_type = " + "'"+  view_ornament_type1_data + "'";
                       con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat2=con2.prepareStatement(sql2);
                       ResultSet rs2=pat2.executeQuery();
                       while(rs2.next()){
                           view_totItemInp1_label.setText(rs2.getString(1)); 
                       }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    con.close();
                    con1.close();
                    con2.close();
                }
                catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                }
                view_combined1_display();
            }
            else{            
                         view_date1_display();
            }
           
    }//GEN-LAST:event_view_selOrnament1_dropdownview_selOrnament_dropdownActionPerformed

    //mouseclick event for switching between panels
    private void mouseclicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseclicked
        
        // Clicked panel visibility true and others are displayed false
        // When switched to next panel, previous panel label color change
        
        if(evt.getSource()==view_overall_panel){
            view_overallCont_panel.setVisible(true);
            view_soldCont_panel.setVisible(false);
            view_balanceCont_panel.setVisible(false);
            view_returnCont_panel.setVisible(false);
            view_overall_label.setForeground(new Color(76,76,76));
            view_sold_label.setForeground(new Color(158,154,154));
            view_balance_label.setForeground(new Color(158,154,154));
            view_return_label.setForeground(new Color(158,154,154));
        }
        if(evt.getSource()==view_sold_panel){
            view_overallCont_panel.setVisible(false);
            view_soldCont_panel.setVisible(true);
            view_balanceCont_panel.setVisible(false);
            view_returnCont_panel.setVisible(false);
            view_overall_label.setForeground(new Color(158,154,154));
            view_sold_label.setForeground(new Color(76,76,76));
            view_balance_label.setForeground(new Color(158,154,154));
            view_return_label.setForeground(new Color(158,154,154));
        }
        if(evt.getSource()==view_balance_panel){
            view_overallCont_panel.setVisible(false);
            view_soldCont_panel.setVisible(false);
            view_balanceCont_panel.setVisible(true);
            view_returnCont_panel.setVisible(false);
            view_overall_label.setForeground(new Color(158,154,154));
            view_sold_label.setForeground(new Color(158,154,154));
            view_balance_label.setForeground(new Color(76,76,76));
            view_return_label.setForeground(new Color(158,154,154));
        }
        if(evt.getSource()==view_return_panel){
            view_overallCont_panel.setVisible(false);
            view_soldCont_panel.setVisible(false);
            view_balanceCont_panel.setVisible(false);
            view_returnCont_panel.setVisible(true);
            view_overall_label.setForeground(new Color(158,154,154));
            view_sold_label.setForeground(new Color(158,154,154));
            view_balance_label.setForeground(new Color(158,154,154));
            view_return_label.setForeground(new Color(76,76,76));
        }
    }//GEN-LAST:event_mouseclicked

    private void view_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_fromDate_propertyChange

                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_from1_date=df.format(view_from1_datechooser.getDate());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    } 
                    view_date1_display();        
    }//GEN-LAST:event_view_fromDate_propertyChange

    private void view_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_todate_propertChange

                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_to1_date=df.format(view_to1_datechooser.getDate());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    view_date1_display();
    }//GEN-LAST:event_view_todate_propertChange

    private void view_selOrnament2_dropdownview_selOrnament_dropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_selOrnament2_dropdownview_selOrnament_dropdownActionPerformed
             
           //For autoincrementing the no. of rows 
           int count=1;
            
           //Masking the image into jLabel Object
           JLabel returnLabel = new JLabel(this.returnIcon);
        
           view_ornament_type2_data=view_selOrnament2_dropdown.getSelectedItem().toString();
           
           if(view_ornament_type2_data!="Select the Ornament"){
 
                try{ 
                    try{


                        //Getting table values acc. to selOrnament
                        String sql="SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM sold\n" +
                        "WHERE ornament_type =" + "'"+  view_ornament_type2_data + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_table2_table.getModel();
                        tm.setRowCount(0);
                        while(rs.next()){
                            Object o[]={count,rs.getString("date"),rs.getString("chase_no"),rs.getString("ornament_name"),rs.getString("weight"), rs.getString("quantity"), rs.getString("barcode"),returnLabel};
                            tm.addRow(o);
                            count++;
                        }

                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                       //Getting total weight of items acc. to selOrnament.
                       String sql1="SELECT SUM(weight) FROM sold WHERE ornament_type = " + "'"+  view_ornament_type2_data + "'";
                       con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat1=con1.prepareStatement(sql1);
                       ResultSet rs1=pat1.executeQuery();
                       while(rs1.next()){
                            if(rs1.getString(1)==null){
                                view_totWtInp2_label.setText("0"); 
                            }
                            else{
                                view_totWtInp2_label.setText(rs1.getString(1)); 
                            }
                       } 
                      
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    } 

                    try{

                       //Getting total number of items acc. to selOrnament.
                       String sql2="SELECT COUNT(id) FROM sold WHERE ornament_type = " + "'"+  view_ornament_type2_data + "'";
                       con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat2=con2.prepareStatement(sql2);
                       ResultSet rs2=pat2.executeQuery();
                       while(rs2.next()){
                           view_totItemInp2_label.setText(rs2.getString(1)); 
                       }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    con.close();
                    con1.close();
                    con2.close();
                }
                catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                }
                view_combined2_display();
            }
            else if((view_from2_date!=null)&&(view_to2_date!=null)){            
                         view_date2_display();
            }
           
    }//GEN-LAST:event_view_selOrnament2_dropdownview_selOrnament_dropdownActionPerformed

    private void view_from2_datechooserview_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_from2_datechooserview_fromDate_propertyChange
        // TODO add your handling code here:
        
                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_from2_date=df.format(view_from2_datechooser.getDate());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    } 
                    view_date2_display();  
    }//GEN-LAST:event_view_from2_datechooserview_fromDate_propertyChange

    private void view_to2_datechooserview_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_to2_datechooserview_todate_propertChange
        // TODO add your handling code here:
                    try{
                            SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                            view_to2_date=df.format(view_to2_datechooser.getDate());
                    }
                    catch(Exception e){
                            e.printStackTrace();
                    }
                    view_date2_display();
    }//GEN-LAST:event_view_to2_datechooserview_todate_propertChange

    private void view_selOrnament3_dropdownview_selOrnament_dropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_selOrnament3_dropdownview_selOrnament_dropdownActionPerformed
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);
        
        view_ornament_type3_data=view_selOrnament3_dropdown.getSelectedItem().toString();
           
           if(view_ornament_type3_data!="Select the Ornament"){
 
                try{ 
                    try{


                        //Getting table values acc. to selOrnament
                        String sql="SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM balance\n" +
                        "WHERE ornament_type =" + "'"+  view_ornament_type3_data + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_table3_table.getModel();
                        tm.setRowCount(0);
                        while(rs.next()){
                            Object o[]={count,rs.getString("date"),rs.getString("chase_no"),rs.getString("ornament_name"),rs.getString("weight"), rs.getString("quantity"), rs.getString("barcode"),imageLabel};
                            tm.addRow(o);
                            count++;
                        }

                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                       //Getting total weight of items acc. to selOrnament.
                       String sql1="SELECT SUM(weight) FROM balance WHERE ornament_type = " + "'"+  view_ornament_type3_data + "'";
                       con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat1=con1.prepareStatement(sql1);
                       ResultSet rs1=pat1.executeQuery();
                       while(rs1.next()){
                            if(rs1.getString(1)==null){
                                view_totWtInp3_label.setText("0"); 
                            }
                            else{
                                view_totWtInp3_label.setText(rs1.getString(1)); 
                            }
                       } 
                      
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    } 

                    try{

                       //Getting total number of items acc. to selOrnament.
                       String sql2="SELECT COUNT(id) FROM balance WHERE ornament_type = " + "'"+  view_ornament_type3_data + "'";
                       con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat2=con2.prepareStatement(sql2);
                       ResultSet rs2=pat2.executeQuery();
                       while(rs2.next()){
                           view_totItemInp3_label.setText(rs2.getString(1)); 
                       }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    con.close();
                    con1.close();
                    con2.close();
                }
                catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                }
                view_combined3_display();
            }
             else if((view_from3_date!=null)&&(view_to3_date!=null)){            
                         view_date3_display();
            }
           
    }//GEN-LAST:event_view_selOrnament3_dropdownview_selOrnament_dropdownActionPerformed

    private void view_from3_datechooserview_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_from3_datechooserview_fromDate_propertyChange
        // TODO add your handling code here:
                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_from3_date=df.format(view_from3_datechooser.getDate());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    } 
                    view_date3_display(); 
    }//GEN-LAST:event_view_from3_datechooserview_fromDate_propertyChange

    private void view_to3_datechooserview_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_to3_datechooserview_todate_propertChange
        // TODO add your handling code here:
                    try{
                            SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                            view_to3_date=df.format(view_to3_datechooser.getDate());
                    }
                    catch(Exception e){
                            e.printStackTrace();
                    }
                    view_date3_display();
    }//GEN-LAST:event_view_to3_datechooserview_todate_propertChange

    private void view_selOrnament4_dropdownview_selOrnament_dropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_selOrnament4_dropdownview_selOrnament_dropdownActionPerformed
        
        //For autoincrementing the no. of rows 
        int count=1;
        
         view_ornament_type4_data=view_selOrnament4_dropdown.getSelectedItem().toString();
           
           if(view_ornament_type4_data!="Select the Ornament"){
                try{ 
                    try{

                        //Getting table values acc. to selOrnament
                        String sql="SELECT date, chase_no, ornament_name, quality,\n" +
                        "weight,buy FROM return_table\n" +
                        "WHERE ornament_type =" + "'"+  view_ornament_type4_data + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_table4_table.getModel();
                        tm.setRowCount(0);
                        while(rs.next()){
                            Object o[]={count,rs.getString("date"),rs.getString("chase_no"),rs.getString("ornament_name"),rs.getString("quality"),rs.getString("weight"),rs.getString("buy")};
                            tm.addRow(o);
                            count++;
                        }

                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                       //Getting total weight of items acc. to selOrnament.
                       String sql1="SELECT SUM(weight) FROM return_table WHERE ornament_type = " + "'"+  view_ornament_type4_data + "'";
                       con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat1=con1.prepareStatement(sql1);
                       ResultSet rs1=pat1.executeQuery();
                       while(rs1.next()){
                            if(rs1.getString(1)==null){
                                view_totWtInp4_label.setText("0"); 
                            }
                            else{
                                view_totWtInp4_label.setText(rs1.getString(1)); 
                            }
                       } 
                      
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    } 

                    try{

                       //Getting total number of items acc. to selOrnament.
                       String sql2="SELECT COUNT(id) FROM return_table WHERE ornament_type = " + "'"+  view_ornament_type4_data + "'";
                       con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat2=con2.prepareStatement(sql2);
                       ResultSet rs2=pat2.executeQuery();
                       while(rs2.next()){
                           view_totItemInp4_label.setText(rs2.getString(1)); 
                       }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    con.close();
                    con1.close();
                    con2.close();
                }
                catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                }
                view_combined4_display();
            }
             else if((view_from4_date!=null)&&(view_to4_date!=null)){            
                         view_date4_display();
            }

    }//GEN-LAST:event_view_selOrnament4_dropdownview_selOrnament_dropdownActionPerformed

    private void view_from4_datechooserview_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_from4_datechooserview_fromDate_propertyChange
        // TODO add your handling code here:
                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_from4_date=df.format(view_from4_datechooser.getDate());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    } 
                    view_date4_display(); 
    }//GEN-LAST:event_view_from4_datechooserview_fromDate_propertyChange

    private void view_to4_datechooserview_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_to4_datechooserview_todate_propertChange
        // TODO add your handling code here:
                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_to4_date=df.format(view_to4_datechooser.getDate());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    view_date4_display();
    }//GEN-LAST:event_view_to4_datechooserview_todate_propertChange

    /**
     * @param args the command line arguments
     */
   
    public static void main(String args[]) throws ParseException{
        
       
       
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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel view_areaOne_panel;
    private javax.swing.JPanel view_areaTwo_panel;
    private javax.swing.JPanel view_balanceCont_panel;
    private javax.swing.JLabel view_balance_label;
    private javax.swing.JPanel view_balance_panel;
    private javax.swing.JLabel view_colon11_label;
    private javax.swing.JLabel view_colon12_label;
    private javax.swing.JLabel view_colon21_label;
    private javax.swing.JLabel view_colon22_label;
    private javax.swing.JLabel view_colon31_label;
    private javax.swing.JLabel view_colon32_label;
    private javax.swing.JLabel view_colon41_label;
    private javax.swing.JLabel view_colon42_label;
    private javax.swing.JPanel view_contOne_panel;
    private javax.swing.JPanel view_contTwo_panel;
    private javax.swing.JPanel view_datelimit1_panel;
    private javax.swing.JPanel view_datelimit2_panel;
    private javax.swing.JPanel view_datelimit3_panel;
    private javax.swing.JPanel view_datelimit4_panel;
    private javax.swing.JLabel view_datelimitIcon1_label;
    private javax.swing.JLabel view_datelimitIcon2_label;
    private javax.swing.JLabel view_datelimitIcon3_label;
    private javax.swing.JLabel view_datelimitIcon4_label;
    private com.toedter.calendar.JDateChooser view_from1_datechooser;
    private javax.swing.JLabel view_from1_label;
    private com.toedter.calendar.JDateChooser view_from2_datechooser;
    private javax.swing.JLabel view_from2_label;
    private com.toedter.calendar.JDateChooser view_from3_datechooser;
    private javax.swing.JLabel view_from3_label;
    private com.toedter.calendar.JDateChooser view_from4_datechooser;
    private javax.swing.JLabel view_from4_label;
    private javax.swing.JPanel view_overallCont_panel;
    private javax.swing.JLabel view_overall_label;
    private javax.swing.JPanel view_overall_panel;
    private javax.swing.JPanel view_returnCont_panel;
    private javax.swing.JLabel view_return_label;
    private javax.swing.JPanel view_return_panel;
    private javax.swing.JComboBox<String> view_selOrnament1_dropdown;
    private javax.swing.JComboBox<String> view_selOrnament2_dropdown;
    private javax.swing.JComboBox<String> view_selOrnament3_dropdown;
    private javax.swing.JComboBox<String> view_selOrnament4_dropdown;
    private javax.swing.JPanel view_soldCont_panel;
    private javax.swing.JLabel view_sold_label;
    private javax.swing.JPanel view_sold_panel;
    private javax.swing.JTable view_table1_table;
    private javax.swing.JTable view_table2_table;
    private javax.swing.JTable view_table3_table;
    private javax.swing.JTable view_table4_table;
    private javax.swing.JScrollPane view_tablearea1_scrollpane;
    private javax.swing.JScrollPane view_tablearea2_scrollpane;
    private javax.swing.JScrollPane view_tablearea3_scrollpane;
    private javax.swing.JScrollPane view_tablearea4_scrollpane;
    private com.toedter.calendar.JDateChooser view_to1_datechooser;
    private javax.swing.JLabel view_to1_label;
    private com.toedter.calendar.JDateChooser view_to2_datechooser;
    private javax.swing.JLabel view_to2_label;
    private com.toedter.calendar.JDateChooser view_to3_datechooser;
    private javax.swing.JLabel view_to3_label;
    private com.toedter.calendar.JDateChooser view_to4_datechooser;
    private javax.swing.JLabel view_to4_label;
    private javax.swing.JLabel view_totItem1_label;
    private javax.swing.JLabel view_totItem2_label;
    private javax.swing.JLabel view_totItem3_label;
    private javax.swing.JLabel view_totItem4_label;
    private javax.swing.JLabel view_totItemInp1_label;
    private javax.swing.JLabel view_totItemInp2_label;
    private javax.swing.JLabel view_totItemInp3_label;
    private javax.swing.JLabel view_totItemInp4_label;
    private javax.swing.JLabel view_totWt1_label;
    private javax.swing.JLabel view_totWt2_label;
    private javax.swing.JLabel view_totWt3_label;
    private javax.swing.JLabel view_totWt4_label;
    private javax.swing.JLabel view_totWtInp1_label;
    private javax.swing.JLabel view_totWtInp2_label;
    private javax.swing.JLabel view_totWtInp3_label;
    private javax.swing.JLabel view_totWtInp4_label;
    private javax.swing.JPanel view_total_panel;
    // End of variables declaration//GEN-END:variables
  
    
    //common class for changing TableHeader bg color and border thickness
    
     public static class HeaderColor extends DefaultTableCellRenderer{
        public HeaderColor(){
            //setOpaque(true);
            super();
        }
         @Override
        public Component getTableCellRendererComponent(JTable table,Object value,boolean selected,boolean focused,int row,int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            setBackground(new Color(247,247,247));
            setBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.LIGHT_GRAY));
            return this;
            //return (Component) value;
        } 
    }
     
      class MyCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            return (Component) o;

        }

    }
    
    // Displaying dropdown items from database
    
    private void view_dropdown1_display(){  
        try{
            String sql="SELECT type FROM Ornament_type";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                view_selOrnament1_dropdown.addItem(rs.getString("type"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }  
    }
    
    private void view_dropdown2_display(){  
        try{
            String sql="SELECT type FROM Ornament_type";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                view_selOrnament2_dropdown.addItem(rs.getString("type"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }  
    }
     
    private void view_dropdown3_display(){  
        try{
            String sql="SELECT type FROM Ornament_type";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                view_selOrnament3_dropdown.addItem(rs.getString("type"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }  
    }
    
    private void view_dropdown4_display(){
         try{
            String sql="SELECT type FROM Ornament_type";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                view_selOrnament4_dropdown.addItem(rs.getString("type"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } 
    }
    
    // Displaying the default items in the table 
    
    private void view_default1_display(){
       
        // For autoincrementing the no. of rows 
        int count=1;
        try{
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sd.parse(this.view_from1_date);
            Date date2 = sd.parse(this.view_to1_date);
            if(date1.compareTo(date2) < 0){
                try{
                    //Getting default total weight of items.
                    String sql1="SELECT SUM(weight) FROM overall WHERE date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat1=con.prepareStatement(sql1);
                    ResultSet rs1=pat1.executeQuery();
                    while(rs1.next()){
                         if(rs1.getString(1)==null){
                            view_totWtInp1_label.setText("0"); 
                         }
                         else{
                            view_totWtInp1_label.setText(rs1.getString(1)); 
                         }
                    }
                    
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                
                try{
                    //Getting default overall table values. 
                    String sql2="SELECT date, chase_no, ornament_type, ornament_name, quality,\n" +
                   "making_charge, weight, wastage, quantity,buy, barcode, status FROM overall WHERE date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat2=con1.prepareStatement(sql2);
                    ResultSet rs2=pat2.executeQuery();
                    
                    DefaultTableModel tm=(DefaultTableModel)view_table1_table.getModel();
                    tm.setRowCount(0);
                    while(rs2.next()){
                        Object o[]={count,rs2.getString("date"),rs2.getString("chase_no"),rs2.getString("ornament_name"),rs2.getString("weight"),rs2.getString("wastage"),rs2.getString("making_charge"),rs2.getString("quantity"),rs2.getString("quality"),rs2.getString("buy")};
                        tm.addRow(o);
                        count++;
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                
                try{
                    //Getting default total number of items.
                    String sql3="SELECT COUNT(id) FROM overall WHERE date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                    con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat3=con2.prepareStatement(sql3);
                    ResultSet rs3=pat3.executeQuery();
                    while(rs3.next()){
                        view_totItemInp1_label.setText(rs3.getString(1)); 
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                con.close(); 
                con1.close(); 
                con2.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }

    private void view_default2_display(){
        //For autoincrementing the no. of rows 
        int count=1;
        
        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);
        
            if((view_from2_date!=null)&&(view_to2_date!=null)){
                if(view_ornament_type2_data=="Select the ornament"){
                    try{
                        try{
                            //Getting default total weight of items.
                            String sql1="SELECT SUM(weight) FROM sold WHERE date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";;
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat1=con.prepareStatement(sql1);
                            ResultSet rs1=pat1.executeQuery();
                            while(rs1.next()){
                                 if(rs1.getString(1)==null){
                                    view_totWtInp2_label.setText("0"); 
                                 }
                                 else{
                                    view_totWtInp2_label.setText(rs1.getString(1)); 
                                 }
                            }

                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                        }

                        try{
                            //Getting default overall table values. 
                            String sql2="SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM sold WHERE date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";
                            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat2=con1.prepareStatement(sql2);
                            ResultSet rs2=pat2.executeQuery();
                            DefaultTableModel tm=(DefaultTableModel)view_table2_table.getModel();

                            tm.setRowCount(0);
                            while(rs2.next()){
                                Object o[]={count,rs2.getString("date"),rs2.getString("chase_no"),rs2.getString("ornament_name"),rs2.getString("weight"),rs2.getString("quantity"),rs2.getString("barcode"),returnLabel};
                                tm.addRow(o); 
                                count++;
                            }
                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                        }

                        try{
                            //Getting default total number of items.
                            String sql3="SELECT COUNT(id) FROM sold WHERE date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";
                            con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat3=con2.prepareStatement(sql3);
                            ResultSet rs3=pat3.executeQuery();
                            while(rs3.next()){
                                view_totItemInp2_label.setText(rs3.getString(1)); 
                            }
                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                        }
                        con.close(); 
                        con1.close(); 
                        con2.close(); 
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,e);
                    }
                }
                else{
                    view_date2_display();
                }
            }
        
    }
    
    private void view_default3_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
          //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);
        
        if((view_from3_date!=null)&&(view_to3_date!=null)){
            try{
                try{
                    //Getting default total weight of items.
                    String sql1="SELECT SUM(weight) FROM balance WHERE date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat1=con.prepareStatement(sql1);
                    ResultSet rs1=pat1.executeQuery();
                    while(rs1.next()){
                         if(rs1.getString(1)==null){
                            view_totWtInp3_label.setText("0"); 
                         }
                         else{
                            view_totWtInp3_label.setText(rs1.getString(1)); 
                         }
                    }
                    
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                
                try{
                    //Getting default overall table values. 
                    String sql2="SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM balance WHERE date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat2=con1.prepareStatement(sql2);
                    ResultSet rs2=pat2.executeQuery();
                    DefaultTableModel tm=(DefaultTableModel)view_table3_table.getModel();
                    tm.setRowCount(0);
                    while(rs2.next()){
                        Object o[]={count,rs2.getString("date"),rs2.getString("chase_no"),rs2.getString("ornament_name"),rs2.getString("weight"),rs2.getString("quantity"),rs2.getString("barcode"),imageLabel};
                        tm.addRow(o);
                        count++;
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                
                try{
                    //Getting default total number of items.
                    String sql3="SELECT COUNT(id) FROM balance WHERE date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                    con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat3=con2.prepareStatement(sql3);
                    ResultSet rs3=pat3.executeQuery();
                    while(rs3.next()){
                        view_totItemInp3_label.setText(rs3.getString(1)); 
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                con.close(); 
                con1.close(); 
                con2.close(); 
            }
            catch(Exception e){
                 JOptionPane.showMessageDialog(null,e);
            }
        }
    }
    
    private void view_default4_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        if((view_from4_date!=null)&&(view_to4_date!=null)){
            try{
                try{
                    //Getting default total weight of items.
                    String sql1="SELECT SUM(weight) FROM return_table WHERE date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat1=con.prepareStatement(sql1);
                    ResultSet rs1=pat1.executeQuery();
                    while(rs1.next()){
                         if(rs1.getString(1)==null){
                            view_totWtInp4_label.setText("0"); 
                         }
                         else{
                            view_totWtInp4_label.setText(rs1.getString(1)); 
                         }
                    }
                    
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                
                try{
                    //Getting default overall table values. 
                    String sql2="SELECT date, chase_no, ornament_name, quality, weight, buy FROM return_table WHERE date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat2=con1.prepareStatement(sql2);
                    ResultSet rs2=pat2.executeQuery();
                    DefaultTableModel tm=(DefaultTableModel)view_table4_table.getModel();
                    tm.setRowCount(0);
                    while(rs2.next()){
                        Object o[]={count,rs2.getString("date"),rs2.getString("chase_no"),rs2.getString("ornament_name"),rs2.getString("quality"),rs2.getString("weight"),rs2.getString("buy")};
                        tm.addRow(o);
                        count++;
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                
                try{
                    //Getting default total number of items.
                    String sql3="SELECT COUNT(id) FROM return_table WHERE date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                    con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat3=con2.prepareStatement(sql3);
                    ResultSet rs3=pat3.executeQuery();
                    while(rs3.next()){
                        view_totItemInp4_label.setText(rs3.getString(1)); 
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                con.close(); 
                con1.close(); 
                con2.close(); 
            }
            catch(Exception e){
                 JOptionPane.showMessageDialog(null,e);
            }
        }
        
    }
    
    //Displaying contents according to the date alone
    
    private void view_date1_display(){
            
        //For autoincrementing the no. of rows 
        int count=1;
        if((view_from1_date!=null)&&(view_to1_date!=null)){    
            try{
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = sd.parse(this.view_from1_date);
                Date date2 = sd.parse(this.view_to1_date);
                if(date1.compareTo(date2) < 0){
                    try{ 
                        
                        //Displaying table according to dates
                        String sql1="SELECT * FROM overall WHERE date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_table1_table.getModel();
                        tm.setRowCount(0);
                        while(rs1.next()){
                            Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"),rs1.getString("wastage"),rs1.getString("making_charge"),rs1.getString("quantity"),rs1.getString("quality"),rs1.getString("buy")};
                            tm.addRow(o);
                            count++;
                        } 
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    try{
                        
                        //Getting total weight of items acc. to dates.
                        String sql1="SELECT SUM(weight) FROM overall WHERE date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con1.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                    view_totWtInp1_label.setText("0"); 
                             }
                             else{
                                    view_totWtInp1_label.setText(rs1.getString(1));
                             }
                        }  
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,e);
                    }
                    
                    try{
                        
                        //Getting total number of items acc. to dates.
                        String sql2="SELECT COUNT(id) FROM overall WHERE date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con2.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        while(rs2.next()){
                            view_totItemInp1_label.setText(rs2.getString(1)); 
                        }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                }
                else{
                     JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                }
            } 
            catch(Exception e){
                    e.printStackTrace();
            }
    
        }
       
        view_combined1_display();
    }
    
    private void view_date2_display(){
                               
                //For autoincrementing the no. of rows 
                int count=1;
                
                //Masking the image into jLabel Object
                JLabel returnLabel = new JLabel(this.returnIcon);
                
                if((view_from2_date!=null) && (view_to2_date!=null)){
                    try{ 
                        
                        //Displaying table according to dates
                        String sql1="SELECT * FROM sold WHERE date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_table2_table.getModel();
                        tm.setRowCount(0);
                        while(rs1.next()){
                            Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"),rs1.getString("quantity"),rs1.getString("barcode"),returnLabel};
                            tm.addRow(o);
                            count++;
                        } 
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    try{
                        
                        //Getting total weight of items acc. to dates.
                        String sql1="SELECT SUM(weight) FROM sold WHERE date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con1.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                    view_totWtInp2_label.setText("0"); 
                             }
                             else{
                                    view_totWtInp2_label.setText(rs1.getString(1));
                             }
                        }  
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,e);
                    }
                    
                    try{
                        
                        //Getting total number of items acc. to dates.
                        String sql2="SELECT COUNT(id) FROM sold WHERE date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con2.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        while(rs2.next()){
                            view_totItemInp2_label.setText(rs2.getString(1)); 
                        }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                }
                view_combined2_display();
    }
    
    private void view_date3_display(){
                
                //For autoincrementing the no. of rows 
                int count=1;
                
                //Masking the image into jLabel Object
                JLabel imageLabel = new JLabel(this.imageIcon);
                
                if((view_from3_date!=null) && (view_to3_date!=null)){
                    try{ 
                        
                        //Displaying table according to dates
                        String sql1="SELECT * FROM balance WHERE date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_table3_table.getModel();
                        tm.setRowCount(0);
                        while(rs1.next()){
                            Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"),rs1.getString("quantity"),rs1.getString("barcode"),imageLabel};
                            tm.addRow(o);
                            count++;
                        } 
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    try{
                        
                        //Getting total weight of items acc. to dates.
                        String sql1="SELECT SUM(weight) FROM balance WHERE date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con1.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                    view_totWtInp3_label.setText("0"); 
                             }
                             else{
                                    view_totWtInp3_label.setText(rs1.getString(1));
                             }
                        }  
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,e);
                    }
                    
                    try{
                        
                        //Getting total number of items acc. to dates.
                        String sql2="SELECT COUNT(id) FROM balance WHERE date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con2.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        while(rs2.next()){
                            view_totItemInp3_label.setText(rs2.getString(1)); 
                        }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                }
                view_combined3_display();
    }
    
    private void  view_date4_display(){
                
                //For autoincrementing the no. of rows 
                int count=1;
                
                if((view_from4_date!=null) && (view_to4_date!=null)){
                    try{ 
                        
                        //Displaying table according to dates
                        String sql1="SELECT * FROM return_table WHERE date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_table4_table.getModel();
                        tm.setRowCount(0);
                        while(rs1.next()){
                            Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("quality"),rs1.getString("weight"),rs1.getString("buy")};
                            tm.addRow(o);
                            count++;
                        } 
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    try{
                        
                        //Getting total weight of items acc. to dates.
                        String sql1="SELECT SUM(weight) FROM return_table WHERE date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con1.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                    view_totWtInp4_label.setText("0"); 
                             }
                             else{
                                    view_totWtInp4_label.setText(rs1.getString(1));
                             }
                        }  
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,e);
                    }
                    
                    try{
                        
                        //Getting total number of items acc. to dates.
                        String sql2="SELECT COUNT(id) FROM return_table WHERE date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con2.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        while(rs2.next()){
                            view_totItemInp4_label.setText(rs2.getString(1)); 
                        }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                }
                view_combined4_display();       
    }
    
    //Displaying contents according to both selOrnament and dates
    
    private void view_combined1_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        if((view_ornament_type1_data!=null) &&(view_from1_date!=null) && (view_to1_date!=null)){ 
            if(view_ornament_type1_data!="Select the Ornament"){ 
                try{
                    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = sd.parse(this.view_from1_date);
                    Date date2 = sd.parse(this.view_to1_date);
                    if(date1.compareTo(date2) < 0){   
                        try{ 

                                //Displaying table according to selOrnament and dates
                                String sql1="SELECT * FROM overall WHERE ornament_type = " + "'"+  view_ornament_type1_data + "' AND date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                DefaultTableModel tm=(DefaultTableModel)view_table1_table.getModel();
                                tm.setRowCount(0);
                                while(rs1.next()){
                                    Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"),rs1.getString("wastage"),rs1.getString("making_charge"),rs1.getString("quantity"),rs1.getString("quality"),rs1.getString("buy")};
                                    tm.addRow(o);
                                    count++;
                                } 
                        }
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,e);
                        }

                        try{

                                //Getting total weight of items acc. to selOrnament and dates.
                                String sql1="SELECT SUM(weight) FROM overall WHERE ornament_type = " + "'"+  view_ornament_type1_data + "' AND date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                                con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con1.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                while(rs1.next()){
                                     if(rs1.getString(1)==null){
                                        view_totWtInp1_label.setText("0"); 
                                     }
                                     else{
                                        view_totWtInp1_label.setText(rs1.getString(1));
                                     }
                                } 
                        }
                        catch(Exception e){
                                 JOptionPane.showMessageDialog(null,e);
                        }

                        try{

                                //Getting total number of items acc. to selOrnament and dates.
                                String sql2="SELECT COUNT(id) FROM overall WHERE ornament_type = " + "'"+  view_ornament_type1_data + "' AND date >=" + "'"+  view_from1_date + "'  AND date <= " + "'"+  view_to1_date + "'";
                                con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat2=con2.prepareStatement(sql2);
                                ResultSet rs2=pat2.executeQuery();
                                while(rs2.next()){
                                    view_totItemInp1_label.setText(rs2.getString(1)); 
                                }
                        } 
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,e);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    } 
    
    private void view_combined2_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);
        
        if((view_ornament_type2_data!=null) &&(view_from2_date!=null) && (view_to2_date!=null)){
                
                if(view_ornament_type2_data!="Select the Ornament"){
                    try{ 

                            //Displaying table according to selOrnament and dates
                            String sql1="SELECT * FROM sold WHERE ornament_type = " + "'"+  view_ornament_type2_data + "' AND date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat1=con.prepareStatement(sql1);
                            ResultSet rs1=pat1.executeQuery();
                            DefaultTableModel tm=(DefaultTableModel)view_table2_table.getModel();
                            tm.setRowCount(0);
                            while(rs1.next()){
                                Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"), rs1.getString("quantity"),rs1.getString("barcode"),returnLabel};
                                tm.addRow(o);
                                count++;
                            } 
                    }
                    catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                            //Getting total weight of items acc. to selOrnament and dates.
                            String sql1="SELECT SUM(weight) FROM sold WHERE ornament_type = " + "'"+  view_ornament_type2_data + "' AND date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";
                            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat1=con1.prepareStatement(sql1);
                            ResultSet rs1=pat1.executeQuery();
                            while(rs1.next()){
                                 if(rs1.getString(1)==null){
                                    view_totWtInp2_label.setText("0"); 
                                 }
                                 else{
                                    view_totWtInp2_label.setText(rs1.getString(1));
                                 }
                            } 
                    }
                    catch(Exception e){
                             JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                            //Getting total number of items acc. to selOrnament and dates.
                            String sql2="SELECT COUNT(id) FROM sold WHERE ornament_type = " + "'"+  view_ornament_type2_data + "' AND date >=" + "'"+  view_from2_date + "'  AND date <= " + "'"+  view_to2_date + "'";
                            con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat2=con2.prepareStatement(sql2);
                            ResultSet rs2=pat2.executeQuery();
                            while(rs2.next()){
                                view_totItemInp2_label.setText(rs2.getString(1)); 
                            }
                    } 
                    catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                    }
                }
        }
    }
    
    private void view_combined3_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);
        
        if((view_ornament_type3_data!=null) &&(view_from3_date!=null) && (view_to3_date!=null)){
                
                if(view_ornament_type3_data!="Select the Ornament"){
                    try{ 

                            //Displaying table according to selOrnament and dates
                            String sql1="SELECT * FROM balance WHERE ornament_type = " + "'"+  view_ornament_type3_data + "' AND date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat1=con.prepareStatement(sql1);
                            ResultSet rs1=pat1.executeQuery();
                            DefaultTableModel tm=(DefaultTableModel)view_table3_table.getModel();
                            tm.setRowCount(0);
                            while(rs1.next()){
                                Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"), rs1.getString("quantity"),rs1.getString("barcode"),imageLabel};
                                tm.addRow(o);
                                count++;
                            } 
                    }
                    catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                            //Getting total weight of items acc. to selOrnament and dates.
                            String sql1="SELECT SUM(weight) FROM balance WHERE ornament_type = " + "'"+  view_ornament_type3_data + "' AND date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat1=con1.prepareStatement(sql1);
                            ResultSet rs1=pat1.executeQuery();
                            while(rs1.next()){
                                 if(rs1.getString(1)==null){
                                    view_totWtInp3_label.setText("0"); 
                                 }
                                 else{
                                    view_totWtInp3_label.setText(rs1.getString(1));
                                 }
                            } 
                    }
                    catch(Exception e){
                             JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                            //Getting total number of items acc. to selOrnament and dates.
                            String sql2="SELECT COUNT(id) FROM balance WHERE ornament_type = " + "'"+  view_ornament_type3_data + "' AND date >=" + "'"+  view_from3_date + "'  AND date <= " + "'"+  view_to3_date + "'";
                            con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat2=con2.prepareStatement(sql2);
                            ResultSet rs2=pat2.executeQuery();
                            while(rs2.next()){
                                view_totItemInp3_label.setText(rs2.getString(1)); 
                            }
                    } 
                    catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                    }
                }
        }
    }
    
    private void view_combined4_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        if((view_ornament_type4_data!=null) &&(view_from4_date!=null) && (view_to4_date!=null)){
                
                if(view_ornament_type4_data!="Select the Ornament"){
                    try{ 

                            //Displaying table according to selOrnament and dates
                            String sql1="SELECT * FROM return_table WHERE ornament_type = " + "'"+  view_ornament_type4_data + "' AND date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat1=con.prepareStatement(sql1);
                            ResultSet rs1=pat1.executeQuery();
                            DefaultTableModel tm=(DefaultTableModel)view_table4_table.getModel();
                            tm.setRowCount(0);
                            while(rs1.next()){
                                Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("quality"),rs1.getString("weight"),rs1.getString("buy")};
                                tm.addRow(o);
                                count++;
                            } 
                    }
                    catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                            //Getting total weight of items acc. to selOrnament and dates.
                            String sql1="SELECT SUM(weight) FROM return_table WHERE ornament_type = " + "'"+  view_ornament_type4_data + "' AND date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat1=con1.prepareStatement(sql1);
                            ResultSet rs1=pat1.executeQuery();
                            while(rs1.next()){
                                 if(rs1.getString(1)==null){
                                    view_totWtInp4_label.setText("0"); 
                                 }
                                 else{
                                    view_totWtInp4_label.setText(rs1.getString(1));
                                 }
                            } 
                    }
                    catch(Exception e){
                             JOptionPane.showMessageDialog(null,e);
                    }

                    try{

                            //Getting total number of items acc. to selOrnament and dates.
                            String sql2="SELECT COUNT(id) FROM return_table WHERE ornament_type = " + "'"+  view_ornament_type4_data + "' AND date >=" + "'"+  view_from4_date + "'  AND date <= " + "'"+  view_to4_date + "'";
                            con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat2=con2.prepareStatement(sql2);
                            ResultSet rs2=pat2.executeQuery();
                            while(rs2.next()){
                                view_totItemInp4_label.setText(rs2.getString(1)); 
                            }
                    } 
                    catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                    }
                }
        }
    }
   private void view_sold_return(String view_return_value){
        if(view_return_value!=null){
             try{ 
                String sql="UPDATE overall SET status= 0 WHERE barcode = " + "'"+  view_return_value + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                pat=con.prepareStatement(sql);
                pat.executeUpdate();
                String sql1="DELETE FROM sold WHERE barcode = " + "'"+  view_return_value + "'";
                con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                PreparedStatement pat1=con1.prepareStatement(sql1);
                pat1.executeUpdate();
                DefaultTableModel dm = (DefaultTableModel)view_table2_table.getModel();
                dm.setRowCount(0);   
                view_default2_display();
                
                JOptionPane.showMessageDialog(null,"Returned successfully");
 
                } 
                    
                    catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);
                    }
        }
    }
    
        private void view_default_date(){
            try{
                Date date = new Date();
                
                //Setting current date to the to datechooser
                view_to1_datechooser.setDate(date);
                view_to2_datechooser.setDate(date);
                view_to3_datechooser.setDate(date);
                view_to4_datechooser.setDate(date);
                
                SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                
                // ToDate to be checked through database
                default_to_date=df.format(date);
                view_to1_date=default_to_date;
                
                String[] change_date = default_to_date. split("-");
                int month=Integer.parseInt(change_date[1]); //1
             
                if(month!=1){
                            DecimalFormat de = new DecimalFormat("00");
                            String c = de.format(--month);

                            // FromDate to be checked through database
                            default_from_date=change_date[0]+"-"+c+"-"+change_date[2];
                            view_from1_date=default_from_date;
                            //System.out.println(default_from_date);
                            new_normal_display_format=change_date[2]+"-"+c+"-"+change_date[0];
                            Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(new_normal_display_format);
                            
                            //Setting from date if month !=1
                            view_from1_datechooser.setDate(date1);
                            view_from2_datechooser.setDate(date1);
                            view_from3_datechooser.setDate(date1);
                            view_from4_datechooser.setDate(date1);
                            
                            //System.out.println(date1);
                }
                else{
                            String c="12";
                            int year=Integer.parseInt(change_date[0]);
                            year=year-1;
                            String newYear=Integer.toString(year);
                            
                            // FromDate to be checked through database
                            default_from_date=newYear+"-"+c+"-"+change_date[2];
                            view_from1_date=default_from_date;
                            
                            //System.out.println(default_from_date);
                            new_normal_display_format=change_date[2]+"-"+c+"-"+newYear;
                            Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(new_normal_display_format);
                            
                            //Seeting from date if month=1
                            view_from1_datechooser.setDate(date1); 
                            view_from2_datechooser.setDate(date1);
                            view_from3_datechooser.setDate(date1); 
                            view_from4_datechooser.setDate(date1); 
                }
   
            }
            catch(Exception e){
               JOptionPane.showMessageDialog(null,e);
            }
        }
    
}

