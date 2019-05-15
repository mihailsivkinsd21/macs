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
public class Vlan {
    
    private String vlannr;
    private String swip;
    private String community;
    private String gwMac;
    private int version;
    private ArrayList<Mac> macs = new ArrayList();
    
    
    public Vlan(String pair, String newswip, String newcommunity, int newversion, String newGwMac) throws SocketException, IOException, UnknownHostException, SNMPBadValueException, SNMPGetException {
        vlannr = pairToVlan(pair);
        swip = newswip;
        community = newcommunity;
        version = newversion;
        gwMac = newGwMac;
    }
    
    
    public boolean gwMacExists() {
        
        boolean check = false;
        for (int i=0; i<macs.size(); i++) {
            String checkmac = macs.get(i).getAdress();
            if (checkmac.equals(gwMac.toLowerCase())) {
                check = true;
                break;
            }
        }
                   
        
        return check;
    }
    
    
    
    public void initMacs() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException {
       InetAddress hostAddress = InetAddress.getByName(swip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.7.1.2.2.1.2." + vlannr);
       
       for (int i=0; i<newVars.size(); i++) {
           Mac newMac = new Mac(newVars.getSNMPObjectAt(i).toString());
           macs.add(newMac);
       }
       comInterface.closeConnection();
       
    }
    
    
    public String getVlannr() {
        return vlannr;
    }
    
    public ArrayList<Mac> getMacs() throws SocketException, IOException, UnknownHostException, SNMPBadValueException, SNMPGetException {
        initMacs();
        return macs;
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
    
}
