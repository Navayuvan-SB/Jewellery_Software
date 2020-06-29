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
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
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
    
    //Getting selectedItem from dropdown
    String view_overall_ornament_type_data;
    String view_sold_ornament_type_data = "Select the Ornament";
    String view_balance_ornament_type_data;
    String view_return_ornament_type_data;
     
    //Date obtained after formating
    String view_overall_from_date ;
    String view_overall_to_date;
    
    String view_sold_from_date ;
    String view_sold_to_date ;
    
    String view_balance_from_date ;
    String view_balance_to_date ;
    
    String view_return_from_date ;
    String view_return_to_date ;
    
    String[][] view_sold_raw_data;
    
    //  Image Icon instance
    private ImageIcon imageIcon,returnIcon; 
      
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        

//      Initialize Image Icon
        // view page print image: view_print.jpg 
        imageIcon = new ImageIcon("/home/logida/jewel/Jewellery_Software/src/main/java/image/view_print.jpg");
        
        // view page print image: view_return.jpg 
        returnIcon= new ImageIcon("/home/logida/jewel/Jewellery_Software/src/main/java/image/view_return.png");
 

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
                new Dimension(0,0));
        view_overall_tablearea_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0,0));
        
        view_sold_tablearea_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0,0));
        view_sold_tablearea_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0,0));
        
        view_balance_tablearea_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0,0));
        view_balance_tablearea_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0,0));
        
        view_return_tablearea_scrollpane.getVerticalScrollBar().setPreferredSize(
                new Dimension(0,0));
        view_return_tablearea_scrollpane.getHorizontalScrollBar().setPreferredSize(
                new Dimension(0,0));
        
        //For increasing the speed of the scrollpane
        
        view_overall_tablearea_scrollpane.getVerticalScrollBar().setUnitIncrement(100);
        view_sold_tablearea_scrollpane.getVerticalScrollBar().setUnitIncrement(100);
        view_balance_tablearea_scrollpane.getVerticalScrollBar().setUnitIncrement(100);
        view_return_tablearea_scrollpane.getVerticalScrollBar().setUnitIncrement(100);
        
        //Styling table header
        
        JTableHeader Theader=view_overallTable_table.getTableHeader();
        JTableHeader Theaderone=view_soldTable_table.getTableHeader();
        JTableHeader Theadertwo=view_balanceTable_table.getTableHeader();
        JTableHeader Theaderthree=view_returnTable_table.getTableHeader();
        
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
        
        Theader.setDefaultRenderer(new view_headerColor());
        Theader.setPreferredSize(new Dimension(50,50));
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
   
        Theaderone.setDefaultRenderer(new view_headerColor());
        Theaderone.setPreferredSize(new Dimension(50,50));
        ((DefaultTableCellRenderer)Theaderone.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
  
        Theadertwo.setDefaultRenderer(new view_headerColor());
        Theadertwo.setPreferredSize(new Dimension(50,50));
        ((DefaultTableCellRenderer)Theadertwo.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        Theaderthree.setDefaultRenderer(new view_headerColor());
        Theaderthree.setPreferredSize(new Dimension(50,50));
        ((DefaultTableCellRenderer)Theaderthree.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        // For setting all the values in  the column to center
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        
        //For table 1
        for(int x=0;x<10;x++){
            view_overallTable_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer ); 
        }
        //For table 2 & 3
        for(int x=0;x<7;x++){
            view_soldTable_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
            view_balanceTable_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }
        //For table 4
        for(int x=0;x<7;x++){
            view_returnTable_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer ); 
        }
    
        // Balance table - Getting the values of the selected rows for printing
        
        view_balanceTable_table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int col = view_balanceTable_table.columnAtPoint(evt.getPoint());
                if (col ==7){
                                int setRow = view_balanceTable_table.getSelectedRow();
                                String[] value=new String[col];
                                for(int i=0;i<value.length;i++){
                                        value[i] = view_balanceTable_table.getModel().getValueAt(setRow,i).toString();
                                        System.out.println(value[i]);
                                }
                            }
            }
        });
        
        // Sold table - Getting the values of the selected rows for printing
        
        view_soldTable_table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = view_soldTable_table.columnAtPoint(evt.getPoint());
                if (column ==7){
                                int setRow = view_soldTable_table.getSelectedRow();                             
//                                String view_return_value = view_soldTable_table.getModel().getValueAt(setRow,6).toString();
//                                view_sold_return(view_return_value);//System.out.println(value);
                                   int view_serial_no = Integer.parseInt(view_soldTable_table.getModel().getValueAt(setRow,0).toString());
                                   System.out.println(view_serial_no);
//                                view_trial_return(view_return_value,view_return);
                                  System.out.println(Arrays.toString(view_sold_raw_data[view_serial_no-1]));
                                  view_trial_return(view_serial_no);
                                
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
        view_JFirstname_label = new javax.swing.JLabel();
        view_AName_label = new javax.swing.JLabel();
        view_JLastname_label = new javax.swing.JLabel();
        view_entry_label = new javax.swing.JLabel();
        view_view_label = new javax.swing.JLabel();
        view_sell_label = new javax.swing.JLabel();
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

        view_total_panel.setBackground(java.awt.Color.white);
        view_total_panel.setLayout(new java.awt.BorderLayout());

        view_areaOne_panel.setBackground(java.awt.Color.white);

        view_JFirstname_label.setFont(new java.awt.Font("Ubuntu", 1, 55)); // NOI18N
        view_JFirstname_label.setForeground(java.awt.Color.black);
        view_JFirstname_label.setText("J");

        view_AName_label.setFont(new java.awt.Font("Ubuntu", 1, 32)); // NOI18N
        view_AName_label.setForeground(java.awt.Color.black);
        view_AName_label.setText("A");

        view_JLastname_label.setFont(new java.awt.Font("Ubuntu", 1, 55)); // NOI18N
        view_JLastname_label.setForeground(java.awt.Color.black);
        view_JLastname_label.setText("J");

        view_entry_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        view_entry_label.setForeground(new java.awt.Color(98, 98, 98));
        view_entry_label.setText("Entry");

        view_view_label.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        view_view_label.setForeground(java.awt.Color.black);
        view_view_label.setText("View");

        view_sell_label.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        view_sell_label.setForeground(new java.awt.Color(98, 98, 98));
        view_sell_label.setText("Sell");

        javax.swing.GroupLayout view_areaOne_panelLayout = new javax.swing.GroupLayout(view_areaOne_panel);
        view_areaOne_panel.setLayout(view_areaOne_panelLayout);
        view_areaOne_panelLayout.setHorizontalGroup(
            view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_areaOne_panelLayout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(view_JFirstname_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_AName_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_JLastname_label)
                .addGap(123, 123, 123)
                .addComponent(view_entry_label)
                .addGap(40, 40, 40)
                .addComponent(view_view_label)
                .addGap(40, 40, 40)
                .addComponent(view_sell_label)
                .addContainerGap(82, Short.MAX_VALUE))
        );
        view_areaOne_panelLayout.setVerticalGroup(
            view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_areaOne_panelLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, view_areaOne_panelLayout.createSequentialGroup()
                        .addGroup(view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_entry_label, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_view_label, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_sell_label))
                        .addGap(14, 14, 14))
                    .addGroup(view_areaOne_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(view_AName_label)
                        .addComponent(view_JFirstname_label, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(view_JLastname_label, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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

        view_overall_label.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
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
                mouseclicked(evt);
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
                mouseclicked(evt);
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

        view_overall_totWt_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_totWt_label.setForeground(java.awt.Color.gray);
        view_overall_totWt_label.setText("Total Weight    ");

        view_overall_totWtInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_totWtInp_label.setName(""); // NOI18N

        view_overall_totItem_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_overall_totItem_label.setForeground(java.awt.Color.gray);
        view_overall_totItem_label.setText("Total no of Items");

        view_overall_totItemInp_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N

        view_overall_selOrnament_dropdown.setBackground(new java.awt.Color(211, 211, 211));
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
                false, false, false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_overallTable_table.setColumnSelectionAllowed(true);
        view_overallTable_table.setRequestFocusEnabled(false);
        view_overallTable_table.setRowHeight(48);
        view_overallTable_table.setRowMargin(0);
        view_overallTable_table.setShowGrid(true);
        view_overallTable_table.setShowHorizontalLines(false);
        view_overallTable_table.getTableHeader().setReorderingAllowed(false);
        view_overall_tablearea_scrollpane.setViewportView(view_overallTable_table);
        view_overallTable_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (view_overallTable_table.getColumnModel().getColumnCount() > 0) {
            view_overallTable_table.getColumnModel().getColumn(0).setPreferredWidth(45);
            view_overallTable_table.getColumnModel().getColumn(1).setPreferredWidth(110);
            view_overallTable_table.getColumnModel().getColumn(2).setPreferredWidth(105);
            view_overallTable_table.getColumnModel().getColumn(3).setPreferredWidth(150);
            view_overallTable_table.getColumnModel().getColumn(4).setPreferredWidth(35);
            view_overallTable_table.getColumnModel().getColumn(5).setPreferredWidth(35);
            view_overallTable_table.getColumnModel().getColumn(6).setPreferredWidth(40);
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

        //view page image : view_datelimiticon.png
        view_overall_datelimitIcon_label.setIcon(new javax.swing.ImageIcon("/home/logida/Documents/view_datelimiticon.png"));
        view_overall_datelimit_panel.add(view_overall_datelimitIcon_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_overallCont_panelLayout = new javax.swing.GroupLayout(view_overallCont_panel);
        view_overallCont_panel.setLayout(view_overallCont_panelLayout);
        view_overallCont_panelLayout.setHorizontalGroup(
            view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(view_overall_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_overall_totWt_label, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(view_overall_totItem_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_overall_itemColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_overall_wtColon_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_overall_totItemInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(view_overall_totWtInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_overall_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_overall_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        view_overallCont_panelLayout.setVerticalGroup(
            view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_overall_totItem_label)
                            .addComponent(view_overall_itemColon_label)
                            .addComponent(view_overall_totItemInp_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_overallCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_overall_totWt_label)
                            .addComponent(view_overall_wtColon_label)
                            .addComponent(view_overall_totWtInp_label)))
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(view_overall_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_overallCont_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(view_overall_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(view_overall_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        view_sold_selOrnament_dropdown.setBackground(new java.awt.Color(211, 211, 211));
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
        view_soldTable_table.setShowGrid(true);
        view_soldTable_table.setShowHorizontalLines(false);
        view_soldTable_table.getTableHeader().setReorderingAllowed(false);
        view_sold_tablearea_scrollpane.setViewportView(view_soldTable_table);
        view_soldTable_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (view_soldTable_table.getColumnModel().getColumnCount() > 0) {
            view_soldTable_table.getColumnModel().getColumn(0).setPreferredWidth(35);
            view_soldTable_table.getColumnModel().getColumn(1).setPreferredWidth(90);
            view_soldTable_table.getColumnModel().getColumn(2).setPreferredWidth(105);
            view_soldTable_table.getColumnModel().getColumn(3).setPreferredWidth(150);
            view_soldTable_table.getColumnModel().getColumn(4).setPreferredWidth(35);
            view_soldTable_table.getColumnModel().getColumn(5).setPreferredWidth(45);
            view_soldTable_table.getColumnModel().getColumn(6).setPreferredWidth(75);
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

        // view page images : view_datelimiticon.png
        view_sold_datelimitIcon_label.setIcon(new javax.swing.ImageIcon("/home/logida/Documents/view_datelimiticon.png"));
        view_sold_datelimitIcon_label.setText("jLabel6");
        view_sold_datelimit_panel.add(view_sold_datelimitIcon_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_soldCont_panelLayout = new javax.swing.GroupLayout(view_soldCont_panel);
        view_soldCont_panel.setLayout(view_soldCont_panelLayout);
        view_soldCont_panelLayout.setHorizontalGroup(
            view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(view_sold_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_sold_totWt_label, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(view_sold_totItem_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_sold_itemColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_sold_wtColon_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_sold_totItemInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(view_sold_totWtInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_sold_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_sold_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        view_soldCont_panelLayout.setVerticalGroup(
            view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_sold_totItem_label)
                            .addComponent(view_sold_itemColon_label)
                            .addComponent(view_sold_totItemInp_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_soldCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_sold_totWt_label)
                            .addComponent(view_sold_wtColon_label)
                            .addComponent(view_sold_totWtInp_label)))
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(view_sold_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_soldCont_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(view_sold_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(view_sold_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        view_balance_selOrnament_dropdown.setBackground(new java.awt.Color(211, 211, 211));
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
                true, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_balanceTable_table.setColumnSelectionAllowed(true);
        view_balanceTable_table.setRequestFocusEnabled(false);
        view_balanceTable_table.setRowHeight(45);
        view_balanceTable_table.setRowMargin(0);
        view_balanceTable_table.setShowGrid(true);
        view_balanceTable_table.setShowHorizontalLines(false);
        view_balanceTable_table.getTableHeader().setReorderingAllowed(false);
        view_balance_tablearea_scrollpane.setViewportView(view_balanceTable_table);
        view_balanceTable_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        // view page images : view_datelimiticon.png
        view_balance_datelimitIcon_label.setIcon(new javax.swing.ImageIcon("/home/logida/Documents/view_datelimiticon.png"));
        view_balance_datelimitIcon_label.setText("jLabel6");
        view_balance_datelimit_panel.add(view_balance_datelimitIcon_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_balanceCont_panelLayout = new javax.swing.GroupLayout(view_balanceCont_panel);
        view_balanceCont_panel.setLayout(view_balanceCont_panelLayout);
        view_balanceCont_panelLayout.setHorizontalGroup(
            view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(view_balance_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_balance_totWt_label, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(view_balance_totItem_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_balance_itemColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_balance_wtColon_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_balance_totItemInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(view_balance_totWtInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_balance_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_balance_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        view_balanceCont_panelLayout.setVerticalGroup(
            view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_balance_totItem_label)
                            .addComponent(view_balance_itemColon_label)
                            .addComponent(view_balance_totItemInp_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_balanceCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_balance_totWt_label)
                            .addComponent(view_balance_wtColon_label)
                            .addComponent(view_balance_totWtInp_label)))
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(view_balance_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_balanceCont_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(view_balance_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(view_balance_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        view_return_selOrnament_dropdown.setBackground(new java.awt.Color(211, 211, 211));
        view_return_selOrnament_dropdown.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        view_return_selOrnament_dropdown.setForeground(java.awt.Color.gray);
        view_return_selOrnament_dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select the Ornament" }));
        view_return_selOrnament_dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_return_selOrnament_ActionPerformed(evt);
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
        view_returnTable_table.setShowGrid(true);
        view_returnTable_table.setShowHorizontalLines(false);
        view_returnTable_table.getTableHeader().setReorderingAllowed(false);
        view_return_tablearea_scrollpane.setViewportView(view_returnTable_table);
        view_returnTable_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (view_returnTable_table.getColumnModel().getColumnCount() > 0) {
            view_returnTable_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            view_returnTable_table.getColumnModel().getColumn(1).setPreferredWidth(120);
            view_returnTable_table.getColumnModel().getColumn(2).setPreferredWidth(130);
            view_returnTable_table.getColumnModel().getColumn(3).setPreferredWidth(195);
            view_returnTable_table.getColumnModel().getColumn(4).setPreferredWidth(73);
            view_returnTable_table.getColumnModel().getColumn(5).setPreferredWidth(73);
            view_returnTable_table.getColumnModel().getColumn(6).setPreferredWidth(130);
        }

        view_return_datelimit_panel.setBackground(java.awt.Color.white);
        view_return_datelimit_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view_return_from_datechooser.setBackground(java.awt.Color.white);
        view_return_from_datechooser.setDateFormatString("dd-MM-yyyy");
        view_return_from_datechooser.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        view_return_from_datechooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                view_return_fromDate_propertyChange(evt);
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
                view_return_todate_propertChange(evt);
            }
        });
        view_return_datelimit_panel.add(view_return_to_datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        view_return_to_label.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        view_return_to_label.setText("To");
        view_return_datelimit_panel.add(view_return_to_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 30));

        //view page images : view_datelimiticon.png
        view_return_datelimitIcon_label.setIcon(new javax.swing.ImageIcon("/home/logida/Documents/view_datelimiticon.png"));
        view_return_datelimitIcon_label.setText("jLabel6");
        view_return_datelimit_panel.add(view_return_datelimitIcon_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 314, -1));

        javax.swing.GroupLayout view_returnCont_panelLayout = new javax.swing.GroupLayout(view_returnCont_panel);
        view_returnCont_panel.setLayout(view_returnCont_panelLayout);
        view_returnCont_panelLayout.setHorizontalGroup(
            view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(view_return_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_return_totWt_label, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(view_return_totItem_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_return_itemColon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(view_return_wtColon_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(view_return_totItemInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(view_return_totWtInp_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view_return_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_return_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        view_returnCont_panelLayout.setVerticalGroup(
            view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_return_totItem_label)
                            .addComponent(view_return_itemColon_label)
                            .addComponent(view_return_totItemInp_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(view_returnCont_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(view_return_totWt_label)
                            .addComponent(view_return_wtColon_label)
                            .addComponent(view_return_totWtInp_label)))
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(view_return_selOrnament_dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(view_returnCont_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(view_return_datelimit_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(view_return_tablearea_scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        view_contTwo_panel.add(view_returnCont_panel, "card5");

        javax.swing.GroupLayout view_areaTwo_panelLayout = new javax.swing.GroupLayout(view_areaTwo_panel);
        view_areaTwo_panel.setLayout(view_areaTwo_panelLayout);
        view_areaTwo_panelLayout.setHorizontalGroup(
            view_areaTwo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view_contOne_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(view_contTwo_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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

    private void view_overall_selOrnament_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_overall_selOrnament_ActionPerformed
 
           //For autoincrementing the no. of rows 
           int count=1;
        
           view_overall_ornament_type_data=view_overall_selOrnament_dropdown.getSelectedItem().toString();
           
           if(view_overall_ornament_type_data!="Select the Ornament"){
 
                try{ 
                    try{
                        //Getting table values acc. to selOrnament
                        String sql="SELECT date, chase_no, ornament_type, ornament_name, quality,\n" +
                        "making_charge, weight, wastage, quantity,buy, barcode, status FROM overall\n" +
                        "WHERE ornament_type =" + "'"+  view_overall_ornament_type_data + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_overallTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs.next()){
                            Object o[]={count,rs.getString("date"),rs.getString("chase_no"),rs.getString("ornament_name"),rs.getString("weight"),rs.getString("wastage"),rs.getString("making_charge"),rs.getString("quantity"),rs.getString("quality"),rs.getString("buy")};
                            tm.addRow(o);
                            count++;
                        }

                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }

                    try{

                       //Getting total weight of items acc. to selOrnament.
                       String sql1="SELECT SUM(weight) FROM overall WHERE ornament_type = " + "'"+ view_overall_ornament_type_data + "'";
                       con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat1=con1.prepareStatement(sql1);
                       ResultSet rs1=pat1.executeQuery();
                       while(rs1.next()){
                            if(rs1.getString(1)==null){
                                view_overall_totWtInp_label.setText("0"); 
                            }
                            else{
                                view_overall_totWtInp_label.setText(rs1.getString(1)); 
                            }
                       } 
                      
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    } 

                    try{

                       //Getting total number of items acc. to selOrnament.
                       String sql2="SELECT COUNT(id) FROM overall WHERE ornament_type = " + "'"+  view_overall_ornament_type_data + "'";
                       con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat2=con2.prepareStatement(sql2);
                       ResultSet rs2=pat2.executeQuery();
                       while(rs2.next()){
                           view_overall_totItemInp_label.setText(rs2.getString(1)); 
                       }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    con.close();
                    con1.close();
                    con2.close();
                }
                catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
                }
                view_overall_combined_display();
            }
            else{            
                         view_overall_date_display();
            }
           
    }//GEN-LAST:event_view_overall_selOrnament_ActionPerformed

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

    private void view_overall_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_overall_fromDate_propertyChange

                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_overall_from_date=df.format(view_overall_from_datechooser.getDate());
                    }
                    catch(Exception e){
                      
                    } 
                    view_overall_date_display();        
    }//GEN-LAST:event_view_overall_fromDate_propertyChange

    private void view_overall_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_overall_todate_propertChange

                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_overall_to_date=df.format(view_overall_to_datechooser.getDate());
                    }
                    catch(Exception e){
                       
                    }
                    view_overall_date_display();
    }//GEN-LAST:event_view_overall_todate_propertChange

    private void view_sold_selOrnament_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_sold_selOrnament_ActionPerformed
             
           //For autoincrementing the no. of rows 
           int count=1;
            
           //Masking the image into jLabel Object
           JLabel returnLabel = new JLabel(this.returnIcon);
        
           view_sold_ornament_type_data =view_sold_selOrnament_dropdown.getSelectedItem().toString();
           
           if(view_sold_ornament_type_data !="Select the Ornament"){
 
                try{ 
                    try{


                        //Getting table values acc. to selOrnament
                        String sql="SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM sold\n" +
                        "WHERE ornament_type =" + "'"+  view_sold_ornament_type_data  + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_soldTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs.next()){
                            Object o[]={count,rs.getString("date"),rs.getString("chase_no"),rs.getString("ornament_name"),rs.getString("weight"), rs.getString("quantity"), rs.getString("barcode"),returnLabel};
                            tm.addRow(o);
                            count++;
                        }

                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }

                    try{

                       //Getting total weight of items acc. to selOrnament.
                       String sql1="SELECT SUM(weight) FROM sold WHERE ornament_type = " + "'"+  view_sold_ornament_type_data  + "'";
                       con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat1=con1.prepareStatement(sql1);
                       ResultSet rs1=pat1.executeQuery();
                       while(rs1.next()){
                            if(rs1.getString(1)==null){
                                view_sold_totWtInp_label.setText("0"); 
                            }
                            else{
                                view_sold_totWtInp_label.setText(rs1.getString(1)); 
                            }
                       } 
                      
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    } 

                    try{

                       //Getting total number of items acc. to selOrnament.
                       String sql2="SELECT COUNT(id) FROM sold WHERE ornament_type = " + "'"+  view_sold_ornament_type_data  + "'";
                       con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat2=con2.prepareStatement(sql2);
                       ResultSet rs2=pat2.executeQuery();
                       while(rs2.next()){
                           view_sold_totItemInp_label.setText(rs2.getString(1)); 
                       }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    con.close();
                    con1.close();
                    con2.close();
                }
                catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
                }
                view_sold_combined_display();
            }
            else if((view_sold_from_date !=null)&&(view_sold_to_date!=null)){            
                         view_sold_date_display();
            }
           
    }//GEN-LAST:event_view_sold_selOrnament_ActionPerformed

    private void view_sold_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_sold_fromDate_propertyChange
        // TODO add your handling code here:
        
                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_sold_from_date =df.format(view_sold_from_datechooser.getDate());
                    }
                    catch(Exception e){
                        
                    } 
                     view_sold_date_display();  
    }//GEN-LAST:event_view_sold_fromDate_propertyChange

    private void view_sold_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_sold_todate_propertChange
        // TODO add your handling code here:
                    try{
                            SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                            view_sold_to_date=df.format(view_sold_to_datechooser.getDate());
                    }
                    catch(Exception e){
                           
                    }
                     view_sold_date_display();
    }//GEN-LAST:event_view_sold_todate_propertChange

    private void view_balance_selOrnament_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_balance_selOrnament_ActionPerformed
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);
        
        view_balance_ornament_type_data=view_balance_selOrnament_dropdown.getSelectedItem().toString();
           
           if(view_balance_ornament_type_data!="Select the Ornament"){
 
                try{ 
                    try{


                        //Getting table values acc. to selOrnament
                        String sql="SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM balance\n" +
                        "WHERE ornament_type =" + "'"+  view_balance_ornament_type_data + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_balanceTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs.next()){
                            Object o[]={count,rs.getString("date"),rs.getString("chase_no"),rs.getString("ornament_name"),rs.getString("weight"), rs.getString("quantity"), rs.getString("barcode"),imageLabel};
                            tm.addRow(o);
                            count++;
                        }

                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }

                    try{

                       //Getting total weight of items acc. to selOrnament.
                       String sql1="SELECT SUM(weight) FROM balance WHERE ornament_type = " + "'"+ view_balance_ornament_type_data + "'";
                       con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat1=con1.prepareStatement(sql1);
                       ResultSet rs1=pat1.executeQuery();
                       while(rs1.next()){
                            if(rs1.getString(1)==null){
                                view_balance_totWtInp_label.setText("0"); 
                            }
                            else{
                                view_balance_totWtInp_label.setText(rs1.getString(1)); 
                            }
                       } 
                      
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    } 

                    try{

                       //Getting total number of items acc. to selOrnament.
                       String sql2="SELECT COUNT(id) FROM balance WHERE ornament_type = " + "'"+  view_balance_ornament_type_data + "'";
                       con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat2=con2.prepareStatement(sql2);
                       ResultSet rs2=pat2.executeQuery();
                       while(rs2.next()){
                           view_balance_totItemInp_label.setText(rs2.getString(1)); 
                       }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    con.close();
                    con1.close();
                    con2.close();
                }
                catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
                }
              view_balance_combined_display();
            }
             else if((view_balance_from_date!=null)&&(view_balance_to_date!=null)){            
                         view_balance_date_display();
            }
           
    }//GEN-LAST:event_view_balance_selOrnament_ActionPerformed

    private void view_balance_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_balance_fromDate_propertyChange
        // TODO add your handling code here:
                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_balance_from_date=df.format(view_balance_from_datechooser.getDate());
                    }
                    catch(Exception e){
                        
                    } 
                    view_balance_date_display(); 
    }//GEN-LAST:event_view_balance_fromDate_propertyChange

    private void view_balance_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_balance_todate_propertChange
        // TODO add your handling code here:
                    try{
                            SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                            view_balance_to_date=df.format(view_balance_to_datechooser.getDate());
                    }
                    catch(Exception e){
                       
                    }
                      view_balance_date_display();
    }//GEN-LAST:event_view_balance_todate_propertChange

    private void view_return_selOrnament_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_return_selOrnament_ActionPerformed
        
        //For autoincrementing the no. of rows 
        int count=1;
        
         view_return_ornament_type_data=view_return_selOrnament_dropdown.getSelectedItem().toString();
           
           if(view_return_ornament_type_data!="Select the Ornament"){
                try{ 
                    try{

                        //Getting table values acc. to selOrnament
                        String sql="SELECT date, chase_no, ornament_name, quality,\n" +
                        "weight,buy FROM return_table\n" +
                        "WHERE ornament_type =" + "'"+  view_return_ornament_type_data + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_returnTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs.next()){
                            Object o[]={count,rs.getString("date"),rs.getString("chase_no"),rs.getString("ornament_name"),rs.getString("quality"),rs.getString("weight"),rs.getString("buy")};
                            tm.addRow(o);
                            count++;
                        }

                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }

                    try{

                       //Getting total weight of items acc. to selOrnament.
                       String sql1="SELECT SUM(weight) FROM return_table WHERE ornament_type = " + "'"+  view_return_ornament_type_data + "'";
                       con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat1=con1.prepareStatement(sql1);
                       ResultSet rs1=pat1.executeQuery();
                       while(rs1.next()){
                            if(rs1.getString(1)==null){
                                view_return_totWtInp_label.setText("0"); 
                            }
                            else{
                                view_return_totWtInp_label.setText(rs1.getString(1)); 
                            }
                       } 
                      
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    } 

                    try{

                       //Getting total number of items acc. to selOrnament.
                       String sql2="SELECT COUNT(id) FROM return_table WHERE ornament_type = " + "'"+  view_return_ornament_type_data + "'";
                       con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                       PreparedStatement pat2=con2.prepareStatement(sql2);
                       ResultSet rs2=pat2.executeQuery();
                       while(rs2.next()){
                           view_return_totItemInp_label.setText(rs2.getString(1)); 
                       }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    con.close();
                    con1.close();
                    con2.close();
                }
                catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
                }
                view_return_combined_display();
            }
             else if((view_return_from_date!=null)&&( view_return_to_date!=null)){            
                         view_return_date_display();
            }

    }//GEN-LAST:event_view_return_selOrnament_ActionPerformed

    private void view_return_fromDate_propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_return_fromDate_propertyChange
        // TODO add your handling code here:
                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_return_from_date=df.format(view_return_from_datechooser.getDate());
                    }
                    catch(Exception e){
                        
                    } 
                     view_return_date_display();
    }//GEN-LAST:event_view_return_fromDate_propertyChange

    private void view_return_todate_propertChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_view_return_todate_propertChange
        // TODO add your handling code here:
                    try{
                         SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                         view_return_to_date=df.format(view_return_to_datechooser.getDate());
                    }
                    catch(Exception e){
                       
                    }
                     view_return_date_display();
    }//GEN-LAST:event_view_return_todate_propertChange

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
    private javax.swing.JLabel view_AName_label;
    private javax.swing.JLabel view_JFirstname_label;
    private javax.swing.JLabel view_JLastname_label;
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
  
    
    //common class for changing TableHeader bg color and border thickness
    
     public static class view_headerColor extends DefaultTableCellRenderer{
        public view_headerColor(){
            super();
        }
         @Override
        public Component getTableCellRendererComponent(JTable table,Object value,boolean selected,boolean focused,int row,int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            setBackground(new Color(247,247,247));
            setBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.LIGHT_GRAY));
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
    
    private void  view_overall_dropdown_display(){  
        try{
            String sql="SELECT type FROM Ornament_type";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                view_overall_selOrnament_dropdown.addItem(rs.getString("type"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't read data. Kindly check your database connection.");
        }  
    }
    
    private void view_sold_dropdown_display(){  
        try{
            String sql="SELECT type FROM Ornament_type";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                view_sold_selOrnament_dropdown.addItem(rs.getString("type"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't read data. Kindly check your database connection.");
        }  
    }
     
    private void view_balance_dropdown_display(){  
        try{
            String sql="SELECT type FROM Ornament_type";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                view_balance_selOrnament_dropdown.addItem(rs.getString("type"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't read data. Kindly check your database connection.");
        }  
    }
    
    private void view_return_dropdown_display(){
         try{
            String sql="SELECT type FROM Ornament_type";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
            stmt=con.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                view_return_selOrnament_dropdown.addItem(rs.getString("type"));
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't read data. Kindly check your database connection.");
        } 
    }
    
    // Displaying the default items in the table 
    
    private void view_overall_default_display(){
       
        // For autoincrementing the no. of rows 
        int count=1;
        try{
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
            Date overall_from = sd.parse(this.view_overall_from_date);
            Date overall_to = sd.parse(this.view_overall_to_date);
            if(overall_from.compareTo(overall_to) < 0){
                try{
                    //Getting default total weight of items.
                    String sql1="SELECT SUM(weight) FROM overall WHERE date >=" + "'"+  view_overall_from_date + "'  AND date <= " + "'"+  view_overall_to_date + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat1=con.prepareStatement(sql1);
                    ResultSet rs1=pat1.executeQuery();
                    while(rs1.next()){
                         if(rs1.getString(1)==null){
                            view_overall_totWtInp_label.setText("0"); 
                         }
                         else{
                            view_overall_totWtInp_label.setText(rs1.getString(1)); 
                         }
                    }
                    
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                }
                
                try{
                    //Getting default overall table values. 
                    String sql2="SELECT date, chase_no, ornament_type, ornament_name, quality,\n" +
                   "making_charge, weight, wastage, quantity,buy, barcode, status FROM overall WHERE date >=" + "'"+  view_overall_from_date + "'  AND date <= " + "'"+  view_overall_to_date + "'";
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat2=con1.prepareStatement(sql2);
                    ResultSet rs2=pat2.executeQuery();
                    
                    DefaultTableModel tm=(DefaultTableModel)view_overallTable_table.getModel();
                    tm.setRowCount(0);
                    while(rs2.next()){
                        Object o[]={count,rs2.getString("date"),rs2.getString("chase_no"),rs2.getString("ornament_name"),rs2.getString("weight"),rs2.getString("wastage"),rs2.getString("making_charge"),rs2.getString("quantity"),rs2.getString("quality"),rs2.getString("buy")};
                        tm.addRow(o);
                        count++;
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                }
                
                try{
                    //Getting default total number of items.
                    String sql3="SELECT COUNT(id) FROM overall WHERE date >=" + "'"+  view_overall_from_date + "'  AND date <= " + "'"+  view_overall_to_date + "'";
                    con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat3=con2.prepareStatement(sql3);
                    ResultSet rs3=pat3.executeQuery();
                    while(rs3.next()){
                        view_overall_totItemInp_label.setText(rs3.getString(1)); 
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                }
                con.close(); 
                con1.close(); 
                con2.close();
            }
            else{
                JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
            }
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
        }
        
    }

    private void  view_sold_default_display(){
        //For autoincrementing the no. of rows 
        int count=1;
        
        int num_rows;
        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);
        
        if((view_sold_from_date !=null)&&(view_sold_to_date!=null)){
            if(view_sold_ornament_type_data =="Select the Ornament"){
                try{
                    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                    Date sold_from = sd.parse(this.view_sold_from_date);
                    Date sold_to = sd.parse(this.view_sold_to_date);
                    if(sold_from.compareTo(sold_to) < 0){
                        try{
                            //Getting default total weight of items.
                            String sql1="SELECT SUM(weight) FROM sold WHERE date >=" + "'"+  view_sold_from_date  + "'  AND date <= " + "'"+ view_sold_to_date + "'";
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat1=con.prepareStatement(sql1);
                            ResultSet rs1=pat1.executeQuery();
                            while(rs1.next()){
                                 if(rs1.getString(1)==null){
                                    view_sold_totWtInp_label.setText("0"); 
                                 }
                                 else{
                                    view_sold_totWtInp_label.setText(rs1.getString(1)); 
                                 }
                            }

                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{
                            //Getting default overall table values. 
                            String sql2="SELECT * FROM sold WHERE date >=" + "'"+  view_sold_from_date  + "'  AND date <= " + "'"+  view_sold_to_date+ "'";
                            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat2=con1.prepareStatement(sql2);
                            ResultSet rs2=pat2.executeQuery();
                            ResultSetMetaData rsmd = rs2.getMetaData();
                            rs2.last();
                            view_sold_raw_data= new String[rs2.getRow()][rsmd.getColumnCount()];
//                            System.out.println(rs2.getRow());
//                            System.out.println(rsmd.getColumnCount());
                            rs2.beforeFirst();
                           
                            DefaultTableModel tm=(DefaultTableModel)view_soldTable_table.getModel();

                            tm.setRowCount(0);
                            while(rs2.next()){
                                String obj[]={Integer.toString(count),rs2.getString("date"),rs2.getString("chase_no"),rs2.getString("ornament_type"),rs2.getString("ornament_name"),rs2.getString("quality"),rs2.getString("making_charge"),rs2.getString("weight"),rs2.getString("wastage"),rs2.getString("quantity"),rs2.getString("buy"),rs2.getString("barcode"),rs2.getString("status")};
                              view_sold_raw_data[count-1]=obj;
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
                            String sql3="SELECT COUNT(id) FROM sold WHERE date >=" + "'"+  view_sold_from_date  + "'  AND date <= " + "'"+  view_sold_to_date + "'";
                            con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                            PreparedStatement pat3=con2.prepareStatement(sql3);
                            ResultSet rs3=pat3.executeQuery();
                            while(rs3.next()){
                                view_sold_totItemInp_label.setText(rs3.getString(1)); 
                            }
                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }
                        con.close(); 
                        con1.close(); 
                        con2.close(); 
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                    }
                }
                catch(Exception e){
                      JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
                }
            }
            else{
                    view_sold_date_display();
            }
        }
        
    }
    
    private void view_balance_default_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
          //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);
        
        if((view_balance_from_date!=null)&&(view_balance_to_date!=null)){
             try{
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                Date balance_from = sd.parse(this.view_balance_from_date);
                Date balance_to = sd.parse(this.view_balance_to_date);
                if(balance_from.compareTo(balance_to) < 0){ 
            
                    try{
                        //Getting default total weight of items.
                        String sql1="SELECT SUM(weight) FROM balance WHERE date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                view_balance_totWtInp_label.setText("0"); 
                             }
                             else{
                                view_balance_totWtInp_label.setText(rs1.getString(1)); 
                             }
                        }

                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }

                    try{
                        //Getting default overall table values. 
                        String sql2="SELECT date, chase_no, ornament_name, weight, quantity, barcode FROM balance WHERE date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con1.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_balanceTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs2.next()){
                            Object o[]={count,rs2.getString("date"),rs2.getString("chase_no"),rs2.getString("ornament_name"),rs2.getString("weight"),rs2.getString("quantity"),rs2.getString("barcode"),imageLabel};
                            tm.addRow(o);
                            count++;
                        }
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }

                    try{
                        //Getting default total number of items.
                        String sql3="SELECT COUNT(id) FROM balance WHERE date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat3=con2.prepareStatement(sql3);
                        ResultSet rs3=pat3.executeQuery();
                        while(rs3.next()){
                            view_balance_totItemInp_label.setText(rs3.getString(1)); 
                        }
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    con.close(); 
                    con1.close(); 
                    con2.close(); 
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                }
            }
            catch(Exception e){
                 JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
            }
        }
    }
    
    private void view_return_default_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        try{
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
            Date return_from = sd.parse(this.view_return_from_date);
            Date return_to = sd.parse(this.view_return_to_date);
            if(return_from.compareTo(return_to) < 0){    
                try{
                    //Getting default total weight of items.
                    String sql1="SELECT SUM(weight) FROM return_table WHERE date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat1=con.prepareStatement(sql1);
                    ResultSet rs1=pat1.executeQuery();
                    while(rs1.next()){
                         if(rs1.getString(1)==null){
                            view_return_totWtInp_label.setText("0"); 
                         }
                         else{
                            view_return_totWtInp_label.setText(rs1.getString(1)); 
                         }
                    }
                    
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                }
                
                try{
                    //Getting default overall table values. 
                    String sql2="SELECT date, chase_no, ornament_name, quality, weight, buy FROM return_table WHERE date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat2=con1.prepareStatement(sql2);
                    ResultSet rs2=pat2.executeQuery();
                    DefaultTableModel tm=(DefaultTableModel)view_returnTable_table.getModel();
                    tm.setRowCount(0);
                    while(rs2.next()){
                        Object o[]={count,rs2.getString("date"),rs2.getString("chase_no"),rs2.getString("ornament_name"),rs2.getString("quality"),rs2.getString("weight"),rs2.getString("buy")};
                        tm.addRow(o);
                        count++;
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                }
                
                try{
                    //Getting default total number of items.
                    String sql3="SELECT COUNT(id) FROM return_table WHERE date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                    con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    PreparedStatement pat3=con2.prepareStatement(sql3);
                    ResultSet rs3=pat3.executeQuery();
                    while(rs3.next()){
                        view_return_totItemInp_label.setText(rs3.getString(1)); 
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                }
                con.close(); 
                con1.close(); 
                con2.close(); 
            }
            else{
                     JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
            }
        }
        catch(Exception e){
             JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
        }
        
    }
    
    //Displaying contents according to the date alone
    
    private void  view_overall_date_display(){
            
        //For autoincrementing the no. of rows 
        int count=1;
        if((view_overall_from_date!=null)&&(view_overall_to_date!=null)){    
            try{
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                Date overall_from = sd.parse(this.view_overall_from_date);
                Date overall_to = sd.parse(this.view_overall_to_date);
                if(overall_from.compareTo(overall_to) < 0){
                    try{ 
                        
                        //Displaying table according to dates
                        String sql1="SELECT * FROM overall WHERE date >=" + "'"+  view_overall_from_date + "'  AND date <= " + "'"+  view_overall_to_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_overallTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs1.next()){
                            Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"),rs1.getString("wastage"),rs1.getString("making_charge"),rs1.getString("quantity"),rs1.getString("quality"),rs1.getString("buy")};
                            tm.addRow(o);
                            count++;
                        } 
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    try{
                        
                        //Getting total weight of items acc. to dates.
                        String sql1="SELECT SUM(weight) FROM overall WHERE date >=" + "'"+  view_overall_from_date+ "'  AND date <= " + "'"+  view_overall_to_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con1.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                    view_overall_totWtInp_label.setText("0"); 
                             }
                             else{
                                    view_overall_totWtInp_label.setText(rs1.getString(1));
                             }
                        }  
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    
                    try{
                        
                        //Getting total number of items acc. to dates.
                        String sql2="SELECT COUNT(id) FROM overall WHERE date >=" + "'"+ view_overall_from_date + "'  AND date <= " + "'"+  view_overall_to_date + "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con2.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        while(rs2.next()){
                            view_overall_totItemInp_label.setText(rs2.getString(1)); 
                        }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                }
                else{
                     JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                }
            } 
            catch(Exception e){
                   JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
            }
    
        }
       
        view_overall_combined_display();
    }
    
    private void  view_sold_date_display(){
                               
        //For autoincrementing the no. of rows 
        int count=1;
                
        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);
                
        if((view_sold_from_date !=null) && (view_sold_to_date!=null)){
            try{
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                Date sold_from = sd.parse(this.view_sold_from_date);
                Date sold_to = sd.parse(this.view_sold_to_date);
                if(sold_from.compareTo(sold_to) < 0){
                    try{ 
                        
                        //Displaying table according to dates
                        String sql1="SELECT * FROM sold WHERE date >=" + "'"+  view_sold_from_date+ "'  AND date <= " + "'"+  view_sold_to_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_soldTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs1.next()){
                            Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"),rs1.getString("quantity"),rs1.getString("barcode"),returnLabel};
                            tm.addRow(o);
                            count++;
                        } 
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    try{
                        
                        //Getting total weight of items acc. to dates.
                        String sql1="SELECT SUM(weight) FROM sold WHERE date >=" + "'"+  view_sold_from_date+ "'  AND date <= " + "'"+  view_sold_to_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con1.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                    view_sold_totWtInp_label.setText("0"); 
                             }
                             else{
                                    view_sold_totWtInp_label.setText(rs1.getString(1));
                             }
                        }  
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    
                    try{
                        
                        //Getting total number of items acc. to dates.
                        String sql2="SELECT COUNT(id) FROM sold WHERE date >=" + "'"+  view_sold_from_date + "'  AND date <= " + "'"+ view_sold_to_date+ "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con2.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        while(rs2.next()){
                            view_sold_totItemInp_label.setText(rs2.getString(1)); 
                        }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
            }
        }
         view_sold_combined_display();
    }
    
    private void   view_balance_date_display(){
                
        //For autoincrementing the no. of rows 
        int count=1;
                
        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);
                
        if((view_balance_from_date!=null) && (view_balance_to_date!=null)){
             try{
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                Date balance_from = sd.parse(this.view_balance_from_date);
                Date balance_to = sd.parse(this.view_balance_to_date);
                if(balance_from.compareTo(balance_to) < 0){ 
                    try{ 
                        
                        //Displaying table according to dates
                        String sql1="SELECT * FROM balance WHERE date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_balanceTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs1.next()){
                            Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"),rs1.getString("quantity"),rs1.getString("barcode"),imageLabel};
                            tm.addRow(o);
                            count++;
                        } 
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    try{
                        
                        //Getting total weight of items acc. to dates.
                        String sql1="SELECT SUM(weight) FROM balance WHERE date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con1.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                    view_balance_totWtInp_label.setText("0"); 
                             }
                             else{
                                    view_balance_totWtInp_label.setText(rs1.getString(1));
                             }
                        }  
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    
                    try{
                        
                        //Getting total number of items acc. to dates.
                        String sql2="SELECT COUNT(id) FROM balance WHERE date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con2.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        while(rs2.next()){
                            view_balance_totItemInp_label.setText(rs2.getString(1)); 
                        }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                }
            }
            catch(Exception e){
                 JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
            }
        }
       view_balance_combined_display();
    }
    
    private void view_return_date_display(){
                
                //For autoincrementing the no. of rows 
                int count=1;
                
        if((view_return_from_date!=null) && (view_return_to_date!=null)){
            try{
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                Date return_from = sd.parse(this.view_return_from_date);
                Date return_to = sd.parse(this.view_return_to_date);
                if(return_from.compareTo(return_to) < 0){   
                    try{ 
                        
                        //Displaying table according to dates
                        String sql1="SELECT * FROM return_table WHERE date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        DefaultTableModel tm=(DefaultTableModel)view_returnTable_table.getModel();
                        tm.setRowCount(0);
                        while(rs1.next()){
                            Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("quality"),rs1.getString("weight"),rs1.getString("buy")};
                            tm.addRow(o);
                            count++;
                        } 
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    try{
                        
                        //Getting total weight of items acc. to dates.
                        String sql1="SELECT SUM(weight) FROM return_table WHERE date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat1=con1.prepareStatement(sql1);
                        ResultSet rs1=pat1.executeQuery();
                        while(rs1.next()){
                             if(rs1.getString(1)==null){
                                    view_return_totWtInp_label.setText("0"); 
                             }
                             else{
                                    view_return_totWtInp_label.setText(rs1.getString(1));
                             }
                        }  
                    }
                    catch(Exception e){
                         JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                    
                    try{
                        
                        //Getting total number of items acc. to dates.
                        String sql2="SELECT COUNT(id) FROM return_table WHERE date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                        con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                        PreparedStatement pat2=con2.prepareStatement(sql2);
                        ResultSet rs2=pat2.executeQuery();
                        while(rs2.next()){
                            view_return_totItemInp_label.setText(rs2.getString(1)); 
                        }
                    } 
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                    }
                }
                else{
                     JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
            }
        }
        view_return_combined_display();       
    }
    
    //Displaying contents according to both selOrnament and dates
    
    private void  view_overall_combined_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        if((view_overall_ornament_type_data!=null) &&(view_overall_from_date!=null) && (view_overall_to_date!=null)){ 
            if(view_overall_ornament_type_data!="Select the Ornament"){ 
                try{
                    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                    Date overall_from = sd.parse(this.view_overall_from_date);
                    Date overall_to = sd.parse(this.view_overall_to_date);
                    if(overall_from.compareTo(overall_to) < 0){   
                        try{ 

                                //Displaying table according to selOrnament and dates
                                String sql1="SELECT * FROM overall WHERE ornament_type = " + "'"+  view_overall_ornament_type_data + "' AND date >=" + "'"+  view_overall_from_date + "'  AND date <= " + "'"+  view_overall_to_date+ "'";
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                DefaultTableModel tm=(DefaultTableModel)view_overallTable_table.getModel();
                                tm.setRowCount(0);
                                while(rs1.next()){
                                    Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"),rs1.getString("wastage"),rs1.getString("making_charge"),rs1.getString("quantity"),rs1.getString("quality"),rs1.getString("buy")};
                                    tm.addRow(o);
                                    count++;
                                } 
                        }
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{

                                //Getting total weight of items acc. to selOrnament and dates.
                                String sql1="SELECT SUM(weight) FROM overall WHERE ornament_type = " + "'"+  view_overall_ornament_type_data+ "' AND date >=" + "'"+  view_overall_from_date + "'  AND date <= " + "'"+  view_overall_to_date + "'";
                                con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con1.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                while(rs1.next()){
                                     if(rs1.getString(1)==null){
                                        view_overall_totWtInp_label.setText("0"); 
                                     }
                                     else{
                                        view_overall_totWtInp_label.setText(rs1.getString(1));
                                     }
                                } 
                        }
                        catch(Exception e){
                                 JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{

                                //Getting total number of items acc. to selOrnament and dates.
                                String sql2="SELECT COUNT(id) FROM overall WHERE ornament_type = " + "'"+  view_overall_ornament_type_data + "' AND date >=" + "'"+  view_overall_from_date + "'  AND date <= " + "'"+  view_overall_to_date + "'";
                                con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat2=con2.prepareStatement(sql2);
                                ResultSet rs2=pat2.executeQuery();
                                while(rs2.next()){
                                    view_overall_totItemInp_label.setText(rs2.getString(1)); 
                                }
                        } 
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
                }
            }
        }
    } 
    
    private void view_sold_combined_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        //Masking the image into jLabel Object
        JLabel returnLabel = new JLabel(this.returnIcon);
        
        if((view_sold_ornament_type_data !=null) &&(view_sold_from_date!=null) && (view_sold_to_date!=null)){
                
            if(view_sold_ornament_type_data !="Select the Ornament"){
                try{
                    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                    Date sold_from = sd.parse(this.view_sold_from_date);
                    Date sold_to = sd.parse(this.view_sold_to_date);
                    if(sold_from.compareTo(sold_to) < 0){      
                        try{ 

                                //Displaying table according to selOrnament and dates
                                String sql1="SELECT * FROM sold WHERE ornament_type = " + "'"+  view_sold_ornament_type_data  + "' AND date >=" + "'"+  view_sold_from_date + "'  AND date <= " + "'"+  view_sold_to_date + "'";
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                DefaultTableModel tm=(DefaultTableModel)view_soldTable_table.getModel();
                                tm.setRowCount(0);
                                while(rs1.next()){
                                    Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"), rs1.getString("quantity"),rs1.getString("barcode"),returnLabel};
                                    tm.addRow(o);
                                    count++;
                                } 
                        }
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{

                                //Getting total weight of items acc. to selOrnament and dates.
                                String sql1="SELECT SUM(weight) FROM sold WHERE ornament_type = " + "'"+  view_sold_ornament_type_data  + "' AND date >=" + "'"+  view_sold_from_date + "'  AND date <= " + "'"+  view_sold_to_date+ "'";
                                con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con1.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                while(rs1.next()){
                                     if(rs1.getString(1)==null){
                                        view_sold_totWtInp_label.setText("0"); 
                                     }
                                     else{
                                        view_sold_totWtInp_label.setText(rs1.getString(1));
                                     }
                                } 
                        }
                        catch(Exception e){
                                 JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{

                                //Getting total number of items acc. to selOrnament and dates.
                                String sql2="SELECT COUNT(id) FROM sold WHERE ornament_type = " + "'"+  view_sold_ornament_type_data  + "' AND date >=" + "'"+  view_sold_from_date + "'  AND date <= " + "'"+  view_sold_to_date + "'";
                                con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat2=con2.prepareStatement(sql2);
                                ResultSet rs2=pat2.executeQuery();
                                while(rs2.next()){
                                    view_sold_totItemInp_label.setText(rs2.getString(1)); 
                                }
                        } 
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
                }
            }
        }
    }

    private void view_balance_combined_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        //Masking the image into jLabel Object
        JLabel imageLabel = new JLabel(this.imageIcon);
        
        if((view_balance_ornament_type_data!=null) &&(view_balance_from_date!=null) && (view_balance_to_date!=null)){    
            if(view_balance_ornament_type_data!="Select the Ornament"){
                try{
                    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                    Date balance_from = sd.parse(this.view_balance_from_date);
                    Date balance_to = sd.parse(this.view_balance_to_date);
                    if(balance_from.compareTo(balance_to) < 0){ 
                        try{ 

                                //Displaying table according to selOrnament and dates
                                String sql1="SELECT * FROM balance WHERE ornament_type = " + "'"+  view_balance_ornament_type_data + "' AND date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                DefaultTableModel tm=(DefaultTableModel)view_balanceTable_table.getModel();
                                tm.setRowCount(0);
                                while(rs1.next()){
                                    Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("weight"), rs1.getString("quantity"),rs1.getString("barcode"),imageLabel};
                                    tm.addRow(o);
                                    count++;
                                } 
                        }
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{

                                //Getting total weight of items acc. to selOrnament and dates.
                                String sql1="SELECT SUM(weight) FROM balance WHERE ornament_type = " + "'"+  view_balance_ornament_type_data + "' AND date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                                con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con1.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                while(rs1.next()){
                                     if(rs1.getString(1)==null){
                                        view_balance_totWtInp_label.setText("0"); 
                                     }
                                     else{
                                        view_balance_totWtInp_label.setText(rs1.getString(1));
                                     }
                                } 
                        }
                        catch(Exception e){
                                 JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{

                                //Getting total number of items acc. to selOrnament and dates.
                                String sql2="SELECT COUNT(id) FROM balance WHERE ornament_type = " + "'"+  view_balance_ornament_type_data + "' AND date >=" + "'"+  view_balance_from_date + "'  AND date <= " + "'"+  view_balance_to_date + "'";
                                con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat2=con2.prepareStatement(sql2);
                                ResultSet rs2=pat2.executeQuery();
                                while(rs2.next()){
                                    view_balance_totItemInp_label.setText(rs2.getString(1)); 
                                }
                        } 
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                    }
                }
                catch(Exception e){
                       JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
                }
            }
        }
    }
    
    private void view_return_combined_display(){
        
        //For autoincrementing the no. of rows 
        int count=1;
        
        if((view_return_ornament_type_data!=null) &&(view_return_from_date!=null) && (view_return_to_date!=null)){               
            if(view_return_ornament_type_data!="Select the Ornament"){
                try{
                    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                    Date return_from = sd.parse(this.view_return_from_date);
                    Date return_to = sd.parse(this.view_return_to_date);
                    if(return_from.compareTo(return_to) < 0){  
                        try{ 

                                //Displaying table according to selOrnament and dates
                                String sql1="SELECT * FROM return_table WHERE ornament_type = " + "'"+  view_return_ornament_type_data + "' AND date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                DefaultTableModel tm=(DefaultTableModel)view_returnTable_table.getModel();
                                tm.setRowCount(0);
                                while(rs1.next()){
                                    Object o[]={count,rs1.getString("date"),rs1.getString("chase_no"),rs1.getString("ornament_name"),rs1.getString("quality"),rs1.getString("weight"),rs1.getString("buy")};
                                    tm.addRow(o);
                                    count++;
                                } 
                        }
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{

                                //Getting total weight of items acc. to selOrnament and dates.
                                String sql1="SELECT SUM(weight) FROM return_table WHERE ornament_type = " + "'"+  view_return_ornament_type_data + "' AND date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                                con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat1=con1.prepareStatement(sql1);
                                ResultSet rs1=pat1.executeQuery();
                                while(rs1.next()){
                                     if(rs1.getString(1)==null){
                                        view_return_totWtInp_label.setText("0"); 
                                     }
                                     else{
                                        view_return_totWtInp_label.setText(rs1.getString(1));
                                     }
                                } 
                        }
                        catch(Exception e){
                                 JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }

                        try{

                                //Getting total number of items acc. to selOrnament and dates.
                                String sql2="SELECT COUNT(id) FROM return_table WHERE ornament_type = " + "'"+  view_return_ornament_type_data + "' AND date >=" + "'"+  view_return_from_date + "'  AND date <= " + "'"+  view_return_to_date + "'";
                                con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                                PreparedStatement pat2=con2.prepareStatement(sql2);
                                ResultSet rs2=pat2.executeQuery();
                                while(rs2.next()){
                                    view_return_totItemInp_label.setText(rs2.getString(1)); 
                                }
                        } 
                        catch(Exception e){
                                JOptionPane.showMessageDialog(null,"Can't display data from database. Kindly check the database connection or contact the software vendor");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please check...From date is greater than To date");
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Oops..! Please turn on the database");
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
                    DefaultTableModel dm = (DefaultTableModel)view_soldTable_table.getModel();
                    dm.setRowCount(0);   
                    view_sold_default_display();

                    JOptionPane.showMessageDialog(null,"The item is returned successfully");

                    } 
                    
            catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Couldn't return data. Please check the database connection. If not, contact the software vendor");
            }
        }
    }
  
        private void view_default_date(){
            try{
                Date date = new Date();
                //Setting current date to the to datechooser
                view_overall_to_datechooser.setDate(date);
                view_sold_to_datechooser.setDate(date);
                view_balance_to_datechooser.setDate(date);
                view_return_to_datechooser.setDate(date);
                SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                
                default_to_date=df.format(date);
                
                //Assigning values to the to dates
                
                view_overall_to_date=default_to_date;
                view_sold_to_date=default_to_date;
                view_balance_to_date=default_to_date;
                view_return_to_date=default_to_date;
                

                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, -1);
                view_overall_from_datechooser.setDate(cal.getTime()); 
                view_sold_from_datechooser.setDate(cal.getTime());
                view_balance_from_datechooser.setDate(cal.getTime()); 
                view_return_from_datechooser.setDate(cal.getTime());
                
                default_from_date=simpleDate .format(cal.getTime());
                
                //Assigning values to the to dates
                
                view_overall_from_date=default_from_date;
                view_sold_from_date=default_from_date;
                view_balance_from_date=default_from_date;
                view_return_from_date=default_from_date;
            }
            catch(Exception e){
                
            }
        }
       private void  view_trial_return(int view_serial_no){
        String view_balance_date=view_sold_raw_data[view_serial_no-1][1];
        String view_balance_chaseNo= view_sold_raw_data[view_serial_no-1][2];
        String view_balance_ornament_type=view_sold_raw_data[view_serial_no-1][3];
        String view_balance_ornamentName=view_sold_raw_data[view_serial_no-1][4];
        String view_balance_quality=view_sold_raw_data[view_serial_no-1][5];
        String view_balance_making_charge=view_sold_raw_data[view_serial_no-1][6];
        String view_balance_weight=view_sold_raw_data[view_serial_no-1][7];
        String view_balance_wastage=view_sold_raw_data[view_serial_no-1][8];
        String view_balance_quantity=view_sold_raw_data[view_serial_no-1][9];
        String view_balance_buy=view_sold_raw_data[view_serial_no-1][10];
        String view_balance_barcode=view_sold_raw_data[view_serial_no-1][11];
        String view_balance_status=view_sold_raw_data[view_serial_no-1][12];
        String view_balance_previous_check = null;
        if(view_balance_chaseNo!=null){
        
        try{
                    
                    String sql="SELECT COUNT(chase_no) FROM jewellery_entry WHERE chase_no = " + "'"+  view_balance_chaseNo + "'";
                     con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAJ","root","");
                    pat=con.prepareStatement(sql);
                    ResultSet rs=pat.executeQuery();
                                while(rs.next()){  
                                    view_balance_previous_check=rs.getString(1); 
                                    System.out.println(view_balance_previous_check);
                                }
                                if(view_balance_previous_check=="0"){
//                                    try{
//                                       String sql1="INSERT INTO balance"
//                                            + "(date, chase_no, ornament_type,ornament_name,quality,making_charge, weight,wastage, quantity,buy,barcode,status,)"   //snapshot
//                                            + "VALUES(view_balance_date,view_balance_chaseNo,view_balance_ornament_type,view_balance_ornamentName,view_balance_quality,view_balance_making_charge,view_balance_weight,view_balance_wastage,view_balance_quantity,view_balance_buy,view_balance_barcode,view_balance_status)"; //current_timestamp()
//                                       pat=con.prepareStatement(sql);
//                                       pat.executeUpdate();
//                                       String sql2=DELETE FROM `sold ` WHERE snapshot = 'snapshot value'
//                                    }
//                                    catch(Exception e){
//                                         JOptionPane.showMessageDialog(null,e);
//                                    }
                                }
                                else if(view_balance_previous_check=="1"){
//                                    try{
//                                         String sql2="UPDATE balance SET quantity=quantity+view_balance_quantity  WHERE chase_no=" + "'"+  view_balance_chaseNo + "'";
//                                         pat=con.prepareStatement(sql);
//                                         rs=pat.executeQuery();
//                                         pat.executeUpdate();
//                                    }
//                                    catch(Exception e){
//                                         JOptionPane.showMessageDialog(null,e);
//                                    }
                                }
                                       
        }
        catch(Exception e){
            
        }
       }
       }
 }

