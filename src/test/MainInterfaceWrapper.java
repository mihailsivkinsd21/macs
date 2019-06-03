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
    List<PortVlan> vlanToPortList = ObservableCollections.observableList(new ArrayList<PortVlan>());
    
    public MainInterfaceWrapper() {
        
    }
        
    
    public void checkSwitch() {
        try {
            curSwitch = new Switch(ip, community);

            switches.add(new Switch("172.27.78.237", "bcomsnmpadmin"));
            switches.add(new Switch("172.27.78.163", "bcomsnmpadmin"));
            switches.add(new Switch("172.27.78.196", "bcomsnmpadmin"));
            //switches.add(new Switch("172.27.78.197", "bcomsnmpadmin"));
            switches.add(new Switch("172.27.78.198", "bcomsnmpadmin"));
            switches.add(new Switch("172.27.72.110", "bcomsnmpadmin"));
            //switches.add(new Switch("172.16.131.2", "bcomsnmpadmin"));
            switches.add(new Switch("172.20.3.77", "bcomsnmpadmin"));
            //switches.add(new Switch("172.27.78.196", "bcomsnmpadmin"));
            firePropertyChange("curSwitch");
        } catch (Exception ex) {
            Utility.showTimeoutError();
            throw new RuntimeException(ex);
            //switches.clear();
            //firePropertyChange("switches");
            //firePropertyChange("curSwitch");
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
        //Switch refrSwitch = curSwitch;
        //System.out.println(refrSwitch.getIp());
        //refrSwitch.init();
        curSwitch.init();
        setCurSwitch(null);
        firePropertyChange("curSwitch");
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
        //if(curSwitch != null){
                //System.out.println("4 " + this.curSwitch.getIp());
           // }
        return curSwitch;
    }

    public void setCurSwitch(Switch curSwitch) {
        //System.out.println("1");
        try {
//            if(curSwitch != null){
//               System.out.println("2 " + curSwitch.getIp() + " " + curSwitch.equals(this.curSwitch));
//            }
            this.curSwitch = curSwitch;
            //System.out.println("1");
            firePropertyChange("curSwitch", null, new Object());
            //System.out.println("1");
        } catch (Exception ex) {
            //System.out.println("3");
            Utility.showTimeoutError();
            throw new RuntimeException(ex);
            //Utility.showTimeoutError();
        }
        //System.out.println("");
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

//    public Switch getSelectedSwitch() {
//        return null;
//    }
//
//    public void setSelectedSwitch(Switch selectedSwitch) {
//        try {
//            curSwitch = selectedSwitch;
//            System.out.println(curSwitch);
//            firePropertyChange("curSwitch");
//            System.out.println(curSwitch);
//        } catch (Exception ex) {
//            System.out.println("3");
//            Utility.showTimeoutError();
//        }
//    }
    
    
    
    public Vlan getVlanOnPort() {
        return vlanOnPort;
    }
    
    public void setVlanOnPort(Vlan vlanOnPort) {
        this.vlanOnPort = vlanOnPort;
    }
    

}
