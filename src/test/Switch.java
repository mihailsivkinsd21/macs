/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
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
/**
 *
 * @author Praktikant
 */
public class Switch {
    
    private String ip = "172.27.78.237";
    private String community = "bcomsnmpadmin";
    private int version = 1;
    private ArrayList<Vlan> vlans = new ArrayList();
    
    public Switch() throws SocketException, IOException, UnknownHostException, SNMPBadValueException, SNMPGetException, Exception {
        initVlans();
    }
    
    private void initVlans() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException, Exception {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.7.1.4.3.1.1");
       //System.out.println(newVars.toString());
      // int size = newVars.size();
       String gwMac = getGwMac();
       
       for (int i = 0; i<newVars.size(); i++) {
           SNMPSequence pair = (SNMPSequence) newVars.getSNMPObjectAt(i);
           if (!isVlan(pair.toString())) {
               break;
           }
           Vlan newVlan = new Vlan(pair.toString(), ip, community, 1, gwMac);
           vlans.add(newVlan);
           
           //System.out.println(vlans.get(i));
       }
       comInterface.closeConnection();
        
    }
    
    public ArrayList<Vlan> getVlans() {
        return vlans;
    }
    
    public String getModel() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.getMIBEntry("1.3.6.1.2.1.1.1.0");
       String check = newVars.toString();
       String model = "";
       int counter = 2;
       do {
           
           if (check.charAt(counter)==' ') {
               break;
           } 
           counter++;
       } while (true);
       
       counter = counter + 2;
       do {
          if (check.charAt(counter)==' ') {
               break;
          } 
          model = model+ check.charAt(counter);
          
          counter++;
       } while (true);
       comInterface.closeConnection();
       return model;
    }
    
    public String getGwMac() throws Exception {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.4.21.1.7");
       String pair = newVars.toString();
       
       if (pair.isEmpty()) {
           return "???";
       }
       
       newVars = newVars = comInterface.getMIBEntry("1.3.6.1.2.1.4.35.1.4.20001.1.4." + pairToGwIp(pair));
       comInterface.closeConnection();
       return (getValueAsMac((SNMPSequence) newVars.getSNMPObjectAt(0)));
       
    }
    
    private String pairToGwIp(String pair) {
       
       int counter = 2;
       do {    
           counter++;
           if (pair.charAt(counter) == ' ') {
               break;
           } 
       } while (true);
        
       String gwip = "";
       counter++;
       do {
           counter++;
           if (pair.charAt(counter) == ' ') {
               break;
           } 
           gwip = gwip + pair.charAt(counter);
       } while (true); 
       
       return gwip;
    }
    
    private boolean isVlan(String pair) {
        
        boolean is;
        
        String check = "";
        int counter = 2;
        int dotcount = 0;
        do {
           if (pair.charAt(counter)== '.') {
               dotcount++;
           }
           counter++;
           if(dotcount==12) {
               break; 
           } 
        } while (true);
       
        do {
            if (pair.charAt(counter)=='.') {
                break;
            }
            check = check + pair.charAt(counter);
            counter++;
        } while(true);
        
        if ("1".equals(check)) {
            return true;
        }
        return false;
        
    }
    
    private String getValueAsMac(SNMPSequence pair) {
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
   
}