/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Praktikant
 */
public class MainInterfaceWrapper extends PropertySupport {
    private List<Switch> switches = ObservableCollections.observableList(new ArrayList<Switch>());
    
    private Vlan vlan = null;
    private Mac mac = null;
    private Port port = null;
    private Vlan vlanOnPort = null;
    private PortVlan portToVlan = null;
    private Mac portMac = null;
    
    private Switch curSwitch = null;
                
    private String ip = "172.27.78.237";
    private String community = "bcomsnmpadmin";
    
    
    public MainInterfaceWrapper() {
        
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.switches);
        hash = 97 * hash + Objects.hashCode(this.vlan);
        hash = 97 * hash + Objects.hashCode(this.mac);
        hash = 97 * hash + Objects.hashCode(this.port);
        hash = 97 * hash + Objects.hashCode(this.vlanOnPort);
        hash = 97 * hash + Objects.hashCode(this.portToVlan);
        hash = 97 * hash + Objects.hashCode(this.portMac);
        hash = 97 * hash + Objects.hashCode(this.curSwitch);
        hash = 97 * hash + Objects.hashCode(this.ip);
        hash = 97 * hash + Objects.hashCode(this.community);
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
        final MainInterfaceWrapper other = (MainInterfaceWrapper) obj;
        if (!Objects.equals(this.switches, other.switches)) {
            return false;
        }
        if (!Objects.equals(this.vlan, other.vlan)) {
            return false;
        }
        if (!Objects.equals(this.mac, other.mac)) {
            return false;
        }
        if (!Objects.equals(this.port, other.port)) {
            return false;
        }
        if (!Objects.equals(this.vlanOnPort, other.vlanOnPort)) {
            return false;
        }
        if (!Objects.equals(this.portToVlan, other.portToVlan)) {
            return false;
        }
        if (!Objects.equals(this.portMac, other.portMac)) {
            return false;
        }
        if (!Objects.equals(this.curSwitch, other.curSwitch)) {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!Objects.equals(this.community, other.community)) {
            return false;
        }
        return true;
    }
        
    
    public void checkSwitch() {
        try {
            curSwitch = new Switch(ip, community);

            switches.add(new Switch("172.27.78.237", "bcomsnmpadmin"));
            switches.add(new Switch("172.27.78.163", "bcomsnmpadmin"));
            switches.add(new Switch("172.27.78.196", "bcomsnmpadmin"));
            //switches.add(new Switch("172.27.78.197", "bcomsnmpadmin"));
            switches.add(new Switch("172.27.78.198", "bcomsnmpadmin"));
            //switches.add(new Switch("172.27.78.196", "bcomsnmpadmin"));
            firePropertyChange("curSwitch");
        } catch (Exception ex) {
            Utility.showTimeoutError();
        }
    }    
    
    public Port getPort() {  
        //System.out.println(port);
        return port;
        //System.out.println(port);
    }
    
    public PortVlan getPortToVlan() {
        //System.out.println(portToVlan);
        return portToVlan;
    }
    
    
    public void setPortToVlan(PortVlan portToVlan) {
        this.portToVlan = portToVlan;
        //System.out.println(portToVlan);
        firePropertyChange("portToVlan");
    }
    
    public Mac getPortMac() {
        return portMac;
    }
    
    public void setPortMac(Mac portMac) {
        this.portMac = portMac;
    }
    
    public void setPort(Port port) {
        this.port = port;
        firePropertyChange("port");
        //System.out.println(port);
    }
    
    public void refreshCurSwitch() {
        try {
            curSwitch.init();
            firePropertyChange("curSwitch");
        } catch (Exception ex) {
            Utility.showTimeoutError();
        }
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
        firePropertyChange("curSwitch");
        
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
        firePropertyChange("curSwitch");
    }
    
    
    
    public Vlan getVlanOnPort() {
        return vlanOnPort;
    }
    
    public void setVlanOnPort(Vlan vlanOnPort) {
        this.vlanOnPort = vlanOnPort;
    }
    

}
