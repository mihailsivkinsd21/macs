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
    //private ArrayList<String> macs = new ArrayList<String>();
    private ArrayList<String> vlans = new ArrayList<String>();
    
    public Switch() {
    }
    
    public Switch(String newip, String newcommunity, int newversion) {
        setIp(newip);
        setCommunity(newcommunity);
        setVersion(newversion);
    }
    
    public ArrayList<String> getVlans() {
        return vlans;
    }
    
    public String getIp() {
        return ip;
    }
    
    public String getCommunity() {
        return community;
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setIp(String newip) {
        ip = newip;
    }
    
    public void setCommunity(String newcommunity) {
        community = newcommunity;
    }
    
    public void setVersion(int newversion) {
        version = newversion;
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
   
   public String getGwMac() throws Exception {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.4.21.1.7");
       String pair = newVars.toString();
       
       if (pair.isEmpty()) {
           return "???";
       }
       
       newVars = newVars = comInterface.getMIBEntry("1.3.6.1.2.1.4.35.1.4.20001.1.4." + pairToGwIp(pair));
       
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
   
   public ArrayList<String> getMacs(String vlannr) throws IOException, SNMPBadValueException, SNMPGetException {
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       ArrayList<String> macs = new ArrayList<String>();
       
       //SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.4.3.1.1");
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.7.1.2.2.1.2." + vlannr);
       //System.out.println(newVars.size());
       for (int i=0; i<newVars.size(); i++) {
           SNMPSequence pair = (SNMPSequence) newVars.getSNMPObjectAt(i);
           //System.out.println(macToHex(pairToMac(pair.toString())));
           macs.add(macToHex(pairToMac(pair.toString())));
       }
       
            
       
       return macs;
       
   }
       
   private static String macToHex(String str) {
        try {
            String separator = ":";
            String result = "";
            String[] macParts = str.trim().split("\\:");
            for (int i = 0; i < macParts.length; i++) {
                Integer dec = Integer.parseInt(macParts[i]);
                String hexOctet = Integer.toHexString(dec);
                if (hexOctet.length() < 2) {
                    hexOctet = "0" + hexOctet;
                }
                if (i < 5) {
                    result += hexOctet + separator;
                } else {
                    result += hexOctet;
                }
            }
            result.toLowerCase();
            return result;

        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }
   
    private String pairToMac(String pair) {
        
        String mac = "";
        int counter = 2;
        int dotcount = 0;
        do {
           if (pair.charAt(counter)== '.') {
               dotcount++;
           }
           counter++;
           if(dotcount==14) {
               break; 
           }
            
        } while (true);
        
      
        do {
            if (pair.charAt(counter)==' ') {
                break;
            }
            if (pair.charAt(counter)!='.') {
                mac = mac + pair.charAt(counter);
            } else {
                mac = mac + ":";
            }
            counter++;
        } while(true);
        
        return mac;
        
    }
    
    private String pairToVlan(String pair) {
        
        String vlan = "";
        int counter = 2;
        int dotcount = 0;
        do {
           if (pair.charAt(counter)== '.') {
               dotcount++;
           }
           counter++;
           if(dotcount==13) {
               break; 
           }
            
        } while (true);
       
        do {
            if (pair.charAt(counter)==' ') {
                break;
            }
            vlan = vlan + pair.charAt(counter);
            counter++;
        } while(true);
        
        
        
        
        
        return vlan;
        
    }
    
    public void initVlans() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.7.1.4.3.1.1");
       //System.out.println(newVars.toString());
      // int size = newVars.size();
       
       for (int i = 0; i<newVars.size(); i++) {
           SNMPSequence pair = (SNMPSequence) newVars.getSNMPObjectAt(i);
           if (!isVlan(pair.toString())) {
               break;
           }
           vlans.add(pairToVlan(pair.toString()));
           //System.out.println(vlans.get(i));
       }
        
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
       return model;
    }
       
       
    public boolean gwMacExists(String vlannr) throws IOException, SNMPBadValueException, SNMPGetException, Exception {
        
        boolean check = false;
        ArrayList<String> macsofvlan = getMacs(vlannr);
        
        for (int i = 0; i<macsofvlan.size(); i++) {
            if(macsofvlan.get(i).equals(getGwMac().toLowerCase())) {
                check = true;
            }
        }
        
        return check;
    }  
       
       
   }
   
   
    

