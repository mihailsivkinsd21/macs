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
    private String port;
    private int version;
    private ArrayList<Mac> macs = new ArrayList();
    
    
    public Vlan(String oid, String newswip, String newcommunity, int newversion, String newGwMac) throws SocketException, IOException, UnknownHostException, SNMPBadValueException, SNMPGetException {
        Utility u = new Utility();
        vlannr = oidToVlan(oid);
        swip = newswip;
        community = newcommunity;
        version = newversion;
        gwMac = newGwMac;
    }
    
    /*
    public Vlan() {
        
    }
    */
    
    
    public boolean gwMacExists() throws SocketException, IOException, UnknownHostException, SNMPBadValueException, SNMPGetException {
        
        if (macs.isEmpty()) {
            initMacs();
        }
        boolean check = false;
        for (int i=0; i<macs.size(); i++) {
            String checkmac = macs.get(i).getAdress();
            if (checkmac.equals(gwMac)) {
                check = true;
                break;
            }
        }
                   
        
        return check;
    }
    
    
    
    
    private void initMacs() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException {
       
       macs.clear();
       InetAddress hostAddress = InetAddress.getByName(swip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.7.1.2.2.1.2." + vlannr);
       
       for (int i=0; i< newVars.size(); i++) {
           //String s = String.valueOf(((SNMPSequence)newVars.getSNMPObjectAt(i)).getSNMPObjectAt(0));
           String s = Utility.getOid((SNMPSequence)newVars.getSNMPObjectAt(i));
           Mac newMac = new Mac(s);
           macs.add(newMac);
       }
       comInterface.closeConnection();
       
    }
    
    
    public String getVlannr() {
        return vlannr;
    }
    
    public ArrayList<Mac> getMacs() throws SocketException, IOException, UnknownHostException, SNMPBadValueException, SNMPGetException {
        if (macs.isEmpty()) {
            initMacs();
        }
        return macs;
    }
    
    private String oidToVlan(String oid) {
        
        String vlan = "";
        String [] parts = oid.split("\\.");
        return parts[parts.length-1];
        
    }
    

    
}
