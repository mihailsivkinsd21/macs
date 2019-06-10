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
    private Port badPort = null;
    
    
    private List<SwitchConnection> switchConnections = ObservableCollections.observableList(new ArrayList<SwitchConnection>());
    private List<Port> badPorts = ObservableCollections.observableList(new ArrayList<Port>());

   
    private SwitchConnection curSwitchConnection = null;

    private Switch curSwitch = null;

    private String ip = "172.27.78.237";
    private String community = "bcomsnmpadmin";
    List<PortVlan> vlanToPortList = ObservableCollections.observableList(new ArrayList<PortVlan>());

    public MainInterfaceWrapper() {

    }

    public void checkSwitch() {
        try {
            //setCurSwitch(null);
            curSwitch = new Switch(ip, community);
            switches.clear();
            switchConnections.clear();
            Switch sw1 = new Switch("172.27.78.237", "bcomsnmpadmin", 28);
            //sw1.initVlans();
            //curSwitch.initMacsOnVlans();
            
            
            
            switches.add(sw1);
            
            Switch sw2 = new Switch("172.27.78.196", "bcomsnmpadmin", 28);
            //sw2.updateUplinkStatus(28);
            switches.add(sw2);
            //sw2.updateUplinkStatus();
            Switch sw3 = new Switch("172.27.78.198", "bcomsnmpadmin", 28);
            //sw3.updateUplinkStatus(28);
            switches.add(sw3);
            
            Switch sw4 = new Switch("172.27.78.163", "bcomsnmpadmin");
//            //sw4.updateUplinkStatus(28);
            switches.add(sw4);
            
            Switch sw5 = new Switch("172.27.78.197", "bcomsnmpadmin", 28);
            switches.add(sw5);
            
            
            badPorts.clear();
            for (Switch sw: switches) {
                sw.initVlansAndPorts();
                sw.updateUplinkStatus();
                
//                if (sw.getIp().equals("172.27.78.197")) {   //BAD PORT TEST
//                    Port fakePort = new Port();
//                    fakePort.setPortName("Fake port");
//                    fakePort.setPortNbr(333);
//                    fakePort.getVlans().add(new Vlan("3842"));
//                    fakePort.getVlans().add(new Vlan("22"));
//                    fakePort.getVlans().add(new Vlan("33"));
//                    sw.getPorts().add(fakePort);
//                   
//                }
                
                
                sw.initPortProblems();
                for (Port p: sw.getBadPorts()) {
                    p.setSwitchIp(sw.getIp());
                }
                badPorts.addAll(sw.getBadPorts());
                
            }
            
           // switches.add(new Switch("172.25.2.236", "bcomsnmpadmin"));
            //switches.add(new Switch("172.27.78.197", "bcomsnmpadmin"));
            //switches.add(new Switch("172.27.78.198", "bcomsnmpadmin"));
//            switches.add(new Switch("172.27.72.110", "bcomsnmpadmin"));
//            switches.add(new Switch("172.16.131.2", "bcomsnmpadmin"));
//            switches.add(new Switch("172.20.3.77", "bcomsnmpadmin"));
//            switches.add(new Switch("172.27.64.118", "bcomsnmpadmin"));
            
            switchConnections.add(new SwitchConnection(sw1,sw2, 27,28));
            switchConnections.add(new SwitchConnection(sw1,sw3,26,28));
            switchConnections.add(new SwitchConnection(sw4,sw1, 1, 28));
            switchConnections.add(new SwitchConnection(sw1,sw5,25,28));
            

            //switches.add(new Switch("172.27.78.196", "bcomsnmpadmin"));
            firePropertyChange("curSwitch");
        } catch (Exception ex) {
//            switches.clear();
//            curSwitch = null;
//            firePropertyChange("curSwitch");
            firePropertyChange("switches");

            throw new RuntimeException(ex);
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
        
        setCurSwitch(null);
        this.curSwitch.initAll();
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

    public void setVlan(Vlan vlan) {
        this.vlan = vlan;
        firePropertyChange("vlan");
    }

    public Switch getCurSwitch() {

        return curSwitch;
    }

    public void setCurSwitch(Switch curSwitch) {
        try {
            this.curSwitch = curSwitch;
            if (curSwitch != null) {
                if (!curSwitch.isInited()) {
                    this.curSwitch.initAll();
                    //System.out.println("a");
                }
            }
            firePropertyChange("curSwitch", null, new Object());

        } catch (Exception ex) {

            Utility.showTimeoutError();
            throw new RuntimeException(ex);

        }

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

    public Vlan getVlanOnPort() {
        return vlanOnPort;
    }

    public void setVlanOnPort(Vlan vlanOnPort) {
        this.vlanOnPort = vlanOnPort;
    }
    
     public List<SwitchConnection> getSwitchConnections() {
        return switchConnections;
    }

    public void setSwitchConnections(List<SwitchConnection> switchConnections) {
        this.switchConnections = switchConnections;
    }

    public SwitchConnection getCurSwitchConnection() {
        return curSwitchConnection;
    }

    public void setCurSwitchConnection(SwitchConnection curSwitchConnection) {
        this.curSwitchConnection = curSwitchConnection;
    }

    public Port getBadPort() {
        return badPort;
    }

    public void setBadPort(Port badPort) {
        this.badPort = badPort;
    }

    public List<Port> getBadPorts() {
        return badPorts;
    }

    public void setBadPorts(List<Port> badPorts) {
        this.badPorts = badPorts;
    }



}
