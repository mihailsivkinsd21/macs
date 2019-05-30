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
    private ArrayList<PortVlan> vlanToPortList = new ArrayList();
    private String status = "";
        
    public Switch() {
        
    }
    
    public void init() {
        initVlans();
        initPorts();
        initVlanToPortList();
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
    
    private boolean isStatusOk() {
        boolean statusCheck = true;
        if (vlans.isEmpty()) {
            initVlans();
        }

        for (int i = 0; i < vlans.size(); i++) {
            if (!"2".equals(vlans.get(i).getVlanNbr())) {
                if (!vlans.get(i).gwMacExists()) {
                    statusCheck = false;
                    break;
                }
            }
        }
        return statusCheck;
    }
    
    public String getStatus() {
        if (isStatusOk()) {
            return "OK";
        }
        return "NOT OK";
    }
    
    private void initVlanToPortList() {
        if (ports.isEmpty()) {
            initPorts();
        }
        vlanToPortList.clear();
        for (int i = 0; i < ports.size(); i++) {
            vlanToPortList.addAll(ports.get(i).getPortVlans());
        }
    }
    
    public ArrayList<PortVlan> getVlanToPortList() {
        if (vlanToPortList.isEmpty()) {
            initVlanToPortList();
        }        
        return vlanToPortList;
    }
    
    private void initPorts()  {
       
        try {
            ports.clear();
            InetAddress hostAddress = InetAddress.getByName(ip);
            SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
            
            SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.31.1.1.1.1");
            String gwMac = getGatewayMac();
            
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
            
            
            for (Vlan v : vlans) {
                for (Port p : ports) {
                    if (v.portExists(p.getPortNbr())) {
                        p.addVlan(v);
                    }
                }
            }
            
            comInterface.closeConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public ArrayList<Port> getPorts()  {
        if (ports.isEmpty()) {
            initPorts();
        }
        return ports;
    }
    
    
    public void initVlans()  {
       
        vlans.clear();
        try {
            InetAddress hostAddress = InetAddress.getByName(ip);
            SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
            
            SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.7.1.4.3.1.1");            
            String gwMac = getGatewayMac();
            
            for (int i = 0; i<newVars.size(); i++) {
                String oid = Utility.getOid((SNMPSequence) newVars.getSNMPObjectAt(i));
                String value = Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(i));
                if (Utility.isVlanOid(oid)) {
                    if (!"default".equals(value)) {
                        Vlan newVlan = new Vlan(oid, ip, community, 1, gwMac);
                        vlans.add(newVlan);
                    }
                } else {
                    break;
                }
            }
            comInterface.closeConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public ArrayList<Vlan> getVlans()  {
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
    
    public String getGatewayMac() throws Exception {
       
       InetAddress hostAddress = InetAddress.getByName(ip);             
       SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
       
       SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.4.21.1.7");
       String gwip = Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(0));
       
       if ("".equals(gwip)) {
           return "INCORRECT IP";
       }
       
       
       
       
       if (getModel().contains("DGS-3420")) {
           newVars = comInterface.getMIBEntry("1.3.6.1.2.1.4.22.1.2.5121." + gwip);
       } else {
           newVars = comInterface.getMIBEntry("1.3.6.1.2.1.4.35.1.4.20001.1.4." + gwip);
       }
       
       
       comInterface.closeConnection();
       return (Utility.getValueAsMac((SNMPSequence) newVars.getSNMPObjectAt(0)));
       
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.ip);
        hash = 71 * hash + Objects.hashCode(this.community);
        hash = 71 * hash + this.version;
        hash = 71 * hash + Objects.hashCode(this.vlans);
        hash = 71 * hash + Objects.hashCode(this.ports);
        hash = 71 * hash + Objects.hashCode(this.vlanToPortList);
        hash = 71 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Switch other = (Switch) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!Objects.equals(this.community, other.community)) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.vlans, other.vlans)) {
            return false;
        }
        if (!Objects.equals(this.ports, other.ports)) {
            return false;
        }
        if (!Objects.equals(this.vlanToPortList, other.vlanToPortList)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }

}