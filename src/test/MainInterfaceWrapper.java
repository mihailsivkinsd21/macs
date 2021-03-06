/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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

    private String ip = "172.27.1.107";
    private String community = "bcomsnmpadmin";
    List<PortVlan> vlanToPortList = ObservableCollections.observableList(new ArrayList<PortVlan>());

    public MainInterfaceWrapper() {

    }

    public void checkSwitch() {
        try {
            switches.clear();
            switchConnections.clear();
            final int UPSWITCH_IP_POSITION = 0;
            final int CURSWITCH_IP_POSITION = 1;
            String link = "http://192.168.146.228:8080/irida_web/switches/check/?ip=" + ip;
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            
            String line;
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            
            Integer nextUplink = 0;
            Integer nextUpswitchPort = 0;
            for (String s: lines) {
                
                String[] splitString = s.split(",");
                String sIp = splitString[0];
                if (s.equals(lines.get(UPSWITCH_IP_POSITION))) {     
                    switches.add(new Switch(sIp, community));
                    nextUplink = Integer.parseInt(splitString[2]);
                    nextUpswitchPort = Integer.parseInt(splitString[1]);
                } else if (s.equals(lines.get(CURSWITCH_IP_POSITION))) {
                    switches.add(new Switch(sIp, community, nextUplink));
                    switchConnections.add(new SwitchConnection(switches.get(UPSWITCH_IP_POSITION), switches.get(CURSWITCH_IP_POSITION), nextUpswitchPort, nextUplink));
                    //switches.add(new Switch(sIp, community, nextUplink));
                } else {
                    Integer uplink = Integer.parseInt(splitString[1]);
                    Integer upswitchPort = Integer.parseInt(splitString[2]);
                    Switch adding = new Switch(sIp, community, uplink);
                    switchConnections.add(new SwitchConnection(switches.get(CURSWITCH_IP_POSITION), adding,upswitchPort, uplink));
                    switches.add(adding);
                }
                
            }
            
            
            badPorts.clear();
            for (Switch sw: switches) {
                sw.initVlansAndPorts();
                sw.updateUplinkStatus();
                
                sw.initPortProblems();
                for (Port p: sw.getBadPorts()) {
                    p.setSwitchIp(sw.getIp());
                }
                badPorts.addAll(sw.getBadPorts());
            }
            

            firePropertyChange("curSwitch");
        } catch (Exception ex) {
            firePropertyChange("switches");
            throw new RuntimeException(ex);
        }
    }

    public Port getPort() {
        //System.out.println(port);
        return port;
        //System.out.println(port);
    }
    
    public void updateStatuses() {
        ArrayList<Switch> copySwitches = new ArrayList<Switch>();
        for (Switch sw: switches) {
            sw.initAll();
            sw.initStatus();
            copySwitches.add(sw);
            //System.out.println(sw.getStatus());
        }
        switches.clear();
        switches.addAll(copySwitches);
        //firePropertyChange("curSwitch");
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
        if (this.curSwitch.getModel().equals("TIMEOUT")) {
            this.curSwitch.setModel("");
        }
        this.curSwitch.initAll();
        this.curSwitch.initStatus();
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
