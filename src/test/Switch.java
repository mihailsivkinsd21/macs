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
    
    private String  ip = "172.27.78.237";
    private String community = "bcomsnmpadmin";
    private int version = 1;
    private ArrayList<Vlan> vlans = new ArrayList();
    private ArrayList<Port> ports = new ArrayList();
    
    public Switch() {
        
    }
    
    public void init(){
        try {
            initVlans();
            initPorts();
        } catch (IOException ex) {
            Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SNMPBadValueException ex) {
            Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SNMPGetException ex) {
            Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Switch(String newhostadress, String newcommunity) {
        setIp(newhostadress);
        setCommunity(newcommunity);
    }
    
    public void setIp(String newIp) {
        ip = newIp;
    }
    
    public String getCommunity() {
        return community;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setCommunity(String newCommunity) {
        community = newCommunity;
    }
    
    public boolean isStatusOk() throws IOException, SocketException, SNMPBadValueException, SNMPGetException, Exception {
        
        boolean status = true;
        if (vlans.isEmpty()) {
            initVlans();
        }
        
        for (int i=0; i<vlans.size(); i++) {
            if (!"2".equals(vlans.get(i).getVlannr())) {
                if (!vlans.get(i).gwMacExists()) {
                    status = false;
                    break;
                }
            }
        }
        return status;
    }
    
    private void initPorts() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException, Exception {
       
       ports.clear();
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.31.1.1.1.1");
       String gwMac = getGwMac();
       
       for (int i=0; i<newVars.size(); i++) {
           String oid = Utility.getOid((SNMPSequence) newVars.getSNMPObjectAt(i));
           String value =  Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(i));
           if (Utility.isPortOid(oid)) {
               Port newPort = new Port(oid, value);
               ports.add(newPort);
           } else {
               break;
           }
       }
       
       if (vlans.isEmpty()) {
           initVlans();
       }
       
       for (int i=0; i<vlans.size(); i++) {
           for (int j=0; j<ports.size(); j++) {
               if (vlans.get(i).portExists(ports.get(j).getPortnr())) {
                   ports.get(j).addVlan(vlans.get(i).getVlannr());
               }
           }
       }
       
       comInterface.closeConnection();
    }
    
    public ArrayList<Port> getPorts() throws IOException, SocketException, SNMPBadValueException, SNMPGetException, Exception {
        if (ports.isEmpty()) {
            initPorts();
        }
        return ports;
    }
    
    
    private void initVlans() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException, Exception {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.7.1.4.3.1.1");
       //System.out.println(newVars.toString());
       //int size = newVars.size();
       String gwMac = getGwMac();
       
       for (int i = 0; i<newVars.size(); i++) {
           String oid = Utility.getOid((SNMPSequence) newVars.getSNMPObjectAt(i));
           if (Utility.isVlanOid(oid)) {
                Vlan newVlan = new Vlan(oid, ip, community, 1, gwMac);
                vlans.add(newVlan);
           } else {
               break;
           }
           //System.out.println(vlans.get(i));
       }
       comInterface.closeConnection();
    }
    
    public ArrayList<Vlan> getVlans() throws IOException, SocketException, SNMPBadValueException, SNMPGetException, Exception {
        if (vlans.isEmpty()) {
            initVlans();
        }
        return vlans;
    }
    
    public String getModel() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.getMIBEntry("1.3.6.1.2.1.1.1.0");
       return Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(0));
       
    }
    
    public String getGwMac() throws Exception {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.4.21.1.7");
       String gwip = Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(0));
       
       if ("".equals(gwip)) {
           return "INCORRECT IP";
       }
       
       newVars = comInterface.getMIBEntry("1.3.6.1.2.1.4.35.1.4.20001.1.4." + gwip);
       comInterface.closeConnection();
       return (Utility.getValueAsMac((SNMPSequence) newVars.getSNMPObjectAt(0)));
       
    }
    
    
   
}