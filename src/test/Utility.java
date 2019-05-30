/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import snmp.SNMPOctetString;
import snmp.SNMPSequence;
import snmp.*;
import snmp.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import snmp.*;
import java.util.*;
import java.math.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Praktikant
 */
public class Utility {
    private static final int SNMP_OID = 0;
    private static final int SNMP_VALUE = 1;
    
    public static String getOid(SNMPSequence snmpSequence) {
        return String.valueOf(snmpSequence.getSNMPObjectAt(SNMP_OID));
    }
    
    public static String getValue(SNMPSequence snmpSequence) {
       return String.valueOf(snmpSequence.getSNMPObjectAt(SNMP_VALUE));
    }
    
    public static boolean isVlanOid(String oid) {
        
        String [] parts = oid.split("\\.");
        if ("1".equals(parts[parts.length-2])) {
            return true;
        } 
        return false;
        
    }
    
    public static void showTimeoutError() {
        JFrame frame = new JFrame("Timeout error");
        JOptionPane.showMessageDialog(frame, "Timeout error");
    }
    
    public static boolean isPortOid(String oid) {
        String [] parts = oid.split("\\.");
        if ("1".equals(parts[parts.length-2])) {
            return true;
        } 
        return false;
    }
    
    public static String getValueAsMac(SNMPSequence pair) {
        Object obj = pair.getSNMPObjectAt(1);
        String mac = null;
        if(obj instanceof SNMPOctetString){
          SNMPOctetString octetString = (SNMPOctetString) obj;
          mac = octetString.toHexString();
          mac = mac.trim().replace(" ", ":").toUpperCase();
        } else {
          throw new RuntimeException( " pair is not SNMPOctetString");
        }
        return mac;
   }
   
   public static ArrayList getPortNumbers(byte[] bytes) {
        ArrayList list = new ArrayList();
        BigInteger s = new BigInteger(bytes);
        
        int len = bytes.length * 8;
        
        for (int i = len - 1; i >=  0 ; i--) {
            if (s.testBit(i)) {
                list.add(len - i);
            }
        }
        return list;
    }
}
