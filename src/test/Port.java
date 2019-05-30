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
public class Port {

    private String portName;
    private int portNbr;

    private String switchIp;
    private String community;
    ArrayList<Vlan> vlans = new ArrayList();
    ArrayList<PortVlan> portVlans = new ArrayList();
    
    private static final int VLAN_STRING_SIZE = 5;
    
    public Port() {
    }
    
    public Port(String oid, String value) {
        portName = value;
        portNbr = oidToPortNbr(oid);
    }

    public ArrayList<PortVlan> getVlansToPort() {

        if (portVlans.isEmpty()) {
            for (int i = 0; i < vlans.size(); i++) {
                portVlans.add(new PortVlan(vlans.get(i).getVlanNbr(), vlans.get(i).getMacs(), portNbr, portName));
            }
        }

        return portVlans;
    }

    public String getVlansString() {
        //для поля в таблице с портами, где отображаются вланы
        String result = "";
        for (Vlan v : getVlansForString()) {
            if (!result.isEmpty()) {
                result += ", ";
            }
            result += v.getVlanNbr();
        }
        if (vlans.size() > VLAN_STRING_SIZE) {
            result += "...";
        }
        return result;
    }
    
    private List<Vlan> getVlansForString(){
        if(vlans.size() > VLAN_STRING_SIZE){
            return vlans.subList(0, VLAN_STRING_SIZE);
        }
        return vlans;
    }

    public void addVlan(String vlannr, String swip, String community) { //GOVNO
        vlans.add(new Vlan(vlannr, swip, community));
    }

    public void addVlan(Vlan newVlan) {
        vlans.add(newVlan);

    }

    public int getVlanSize() {
        return vlans.size();
    }

    public ArrayList<Vlan> getVlans() {
        return vlans;
    }

    public int getPortNbr() {
        return portNbr;
    }

    public String getPortName() {
        return portName;
    }

    private int oidToPortNbr(String oid) {
        String[] parts = oid.split("\\.");
        return Integer.parseInt(parts[parts.length - 1]);
    }

}
