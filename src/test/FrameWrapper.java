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
public class FrameWrapper {
    private List<Switch> switches = ObservableCollections.observableList(new ArrayList<Switch>());
    private List<Vlan> vlans = ObservableCollections.observableList(new ArrayList<Vlan>());
    private List<Mac> curVlanMacs = ObservableCollections.observableList(new ArrayList<Mac>());
    private List<Port> ports = ObservableCollections.observableList(new ArrayList<Port>());
    private List<VlanOnPort> curPortVlans = ObservableCollections.observableList(new ArrayList<VlanOnPort>());
    private Vlan vlan = null;
    private Mac mac = null;
    private Port port = null;
    private VlanOnPort portVlan = null;
    private Switch curSwitch = null;
    private Switch switchFinder = null;
    
    
    public FrameWrapper() {
        vlans.add(new Vlan("123","asf"));
        vlans.add(new Vlan("1234","asf"));
        curSwitch = new Switch();
    }
    
    public Switch getSwitchFinder() {
        return switchFinder;
    }
    
    public void setSwitchFinder(Switch switchFinder) {
        this.switchFinder = switchFinder;
        if(switchFinder!=null) {
            vlans.clear();
            vlans.addAll(switchFinder.getVlans());
            ports.clear();
            ports.addAll(switchFinder.getPorts());
            curSwitch = switchFinder;
            curVlanMacs.clear();
            curPortVlans.clear();
        }
    }
    
    public FrameWrapper(Switch curSwitch) throws SNMPBadValueException, SNMPGetException, Exception {
        vlans = curSwitch.getVlans();
        ports = curSwitch.getPorts();
        this.curSwitch = curSwitch;
        
        switches.add(new Switch("172.27.78.237", "bcomsnmpadmin"));
        switches.add(new Switch("172.27.78.163", "bcomsnmpadmin"));
        switches.add(new Switch("172.27.78.197", "bcomsnmpadmin"));
        switches.add(new Switch("172.27.78.198", "bcomsnmpadmin"));
        switches.add(new Switch("172.27.78.196", "bcomsnmpadmin"));
               
    }

    public List<Vlan> getVlans() {
        return vlans;
    }
    
    public Port getPort() {
        return port;
    }
    
    public List<VlanOnPort> getCurPortVlans() {
        return curPortVlans;
    }
    
    public VlanOnPort getPortVlan() {
        return portVlan;
    }
    
    public List<Port> getPorts() {
        return ports;
    }
    
    public void setPort(Port port) {
        this.port = port;
        if (port != null) {
           curPortVlans.clear();
           curPortVlans.addAll(port.getVlans());
       } 
        
    }
    
    public List<Mac> getCurVlanMacs()  {
        return curVlanMacs;
    }

    public void setVlans(List<Vlan> vlans) {
        this.vlans = vlans;
    }
    
    public Mac getMac() {
        return mac;
    }
    
    public void setMac(Mac mac) {
        this.mac = mac;
    }
    
    public void setCurVlanMacs(List<Mac> curVlanMacs) {
        this.curVlanMacs = curVlanMacs;
    }
    
    public List<Switch> getSwitches() {
        return switches;
    }
    
    

    public Vlan getVlan() {
        return vlan;
    }

    public void setVlan(Vlan vlan)  {
       
       this.vlan = vlan;
       if (vlan != null) {
           curVlanMacs.clear();
           curVlanMacs.addAll(vlan.getMacs());
       } 
       // curVlanMacs = vlan.getMacs();
       // Vlan lal = vlan;
        //System.out.println(vlan);
       
    }

    public Switch getCurSwitch() {
        return curSwitch;
    }

    public void setCurSwitch(Switch curSwitch) {
        this.curSwitch = curSwitch;
    }
    
    
    
}
