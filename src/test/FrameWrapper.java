/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.jdesktop.observablecollections.ObservableCollections;
import snmp.SNMPBadValueException;
import snmp.SNMPGetException;

/**
 *
 * @author Praktikant
 */
public class FrameWrapper extends PropertySupport {
    private List<Switch> switches = ObservableCollections.observableList(new ArrayList<Switch>());
    private List<Mac> portMacs = ObservableCollections.observableList(new ArrayList<Mac>());
    
    private Vlan vlan = null;
    private Mac mac = null;
    private Port port = null;
    private VlanToPort portVlan = null;
    private Mac portMac = null;
    
    private Switch curSwitch = null;
                
    private String ip = "172.27.78.237";
    private String community = "bcomsnmpadmin";
    
    
    public FrameWrapper() {        
    }
        
    
    public void checkSwitch() {
        curSwitch = new Switch(ip, community);
        
        switches.add(new Switch("172.27.78.237", "bcomsnmpadmin"));
        switches.add(new Switch("172.27.78.163", "bcomsnmpadmin"));
        switches.add(new Switch("172.27.78.197", "bcomsnmpadmin"));
        switches.add(new Switch("172.27.78.198", "bcomsnmpadmin"));
        switches.add(new Switch("172.27.78.196", "bcomsnmpadmin"));
        firePropertyChange("curSwitch");
    }    
    
    public Port getPort() {        
        return port;
    }
        
    public VlanToPort getPortVlan() {
        return portVlan;
    }
    
    public List<Mac> getPortMacs() {
        return portMacs;
    }
        
    public void setPort(Port port) {
        this.port = port;
        firePropertyChange("port");
    }
    
    public Mac getMac() {
        return mac;
    }
    
    public void setMac(Mac mac) {
        this.mac = mac;        
    }        
    
    public List<Switch> getSwitches() {
        return switches;
    }
       
    public Vlan getVlan() {
        return vlan;
    }

    public void setVlan(Vlan vlan)  {       
       this.vlan = vlan;
       firePropertyChange("vlan");
    }

    public Switch getCurSwitch() {
        return curSwitch;
    }

    public void setCurSwitch(Switch curSwitch) {
        this.curSwitch = curSwitch;
    }
    
    
    
    public void setPortVlan(VlanToPort newportVlan) {
        
        this.portVlan = newportVlan;
        if (portVlan!=null) {
            portMacs.clear();
            portMacs.addAll(portVlan.getMacs());
        }
        //firePropertyChange("curSwitch");
        firePropertyChange("portVlan");
        //firePropertyChange("curSwitch");
        
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Switch getSelectedSwitch() {
        return null;
    }

    public void setSelectedSwitch(Switch selectedSwitch) {        
        curSwitch = selectedSwitch;
        portMacs.clear();
        firePropertyChange("curSwitch");
    }
    
    public Mac getPortMac() {
        return portMac;
    }
    
    public void setPortMac(Mac portMac) {
        this.portMac = portMac;
    }
    
   

}
