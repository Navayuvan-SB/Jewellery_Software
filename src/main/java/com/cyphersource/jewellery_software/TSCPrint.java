/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyphersource.jewellery_software;

import com.sun.jna.Library;
import com.sun.jna.Native;
import javax.swing.JOptionPane;

public class TSCPrint {

    public interface TscLibDll extends Library {

        TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary("../../../../../../lib/TSCLIB", TscLibDll.class);

        int about();

        int openport(String pirnterName);

        int closeport();

        int sendcommand(String printerCommand);

        int sendBinaryData(byte[] printerCommand, int CommandLength);

        int setup(String width, String height, String speed, String density, String sensor, String vertical, String offset);

        int downloadpcx(String filename, String image_name);

        int barcode(String x, String y, String type, String height, String readable, String rotation, String narrow, String wide, String code);

        int printerfont(String x, String y, String fonttype, String rotation, String xmul, String ymul, String text);

        int clearbuffer();

        int printlabel(String set, String copy);

        int windowsfont(int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, String content);

        int windowsfontUnicode(int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, byte[] content);

        int windowsfontUnicodeLengh(int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, byte[] content, int length);

        byte usbportqueryprinter();

    }

    public boolean print_barcode(String mc, String was, String wt, String quality, String chase_no, String orn_name) {

        byte status = TscLibDll.INSTANCE.usbportqueryprinter();//0 = idle, 1 = head open, 16 = pause, following <ESC>!? command of TSPL manual
        
        if (status != 0){
            return false;
        }
        
        TscLibDll.INSTANCE.openport("TSC TTP-244 Pro");
        TscLibDll.INSTANCE.sendcommand("SIZE 100 mm, 14 mm");
        TscLibDll.INSTANCE.sendcommand("SPEED 4");
        TscLibDll.INSTANCE.sendcommand("DENSITY 12");
        TscLibDll.INSTANCE.sendcommand("DIRECTION 1");
        TscLibDll.INSTANCE.sendcommand("SET TEAR ON");
        TscLibDll.INSTANCE.sendcommand("CODEPAGE UTF-8");
        TscLibDll.INSTANCE.sendcommand("GAP 3 mm, 0 mm");
        TscLibDll.INSTANCE.clearbuffer();
        
        String str_shop_name = "TEXT 333,102,\"2\",270,2,2,\""+ "JAJ" +"\"";
        TscLibDll.INSTANCE.sendcommand(str_shop_name);
                
        String str_mc = "TEXT 374,24,\"0\",0,8,8,\"MC: " + mc + " /G\"";
        TscLibDll.INSTANCE.sendcommand(str_mc);
        
        String str_was = "TEXT 374,54,\"0\",0,8,8,\"WAS:" +  was + "%\"";
        TscLibDll.INSTANCE.sendcommand(str_was);
        
        String str_wt = "TEXT 374,84,\"0\",0,8,8,\"WT: " + wt + "\"";
        TscLibDll.INSTANCE.sendcommand(str_wt);
        
        String str_quality = "TEXT 500,104,\"2\",270,1,1,\" " + quality + "\"";
        TscLibDll.INSTANCE.sendcommand(str_quality);
        
        String str_barcode = "BARCODE 553,24, \"93\",30,2,0,2,4,\" "+ chase_no + "\"";
        TscLibDll.INSTANCE.sendcommand(str_barcode);
        
        String str_orn_name = "TEXT 553,84,\"0\",0,9,9,\"" + orn_name + "\"";
        TscLibDll.INSTANCE.sendcommand(str_orn_name);
        
        TscLibDll.INSTANCE.printlabel("1", "1");
        TscLibDll.INSTANCE.closeport();

        return true;
    }

}
