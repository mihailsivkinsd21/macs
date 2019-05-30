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

    public ArrayList<PortVlan> getPortVlans() {

        if (portVlans.isEmpty()) {
            for (Vlan v: vlans) {
                portVlans.add(new PortVlan(v.getVlanNbr(), v.getMacs(), portNbr, portName));
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

    /*public void addVlan(String vlannr, String swip, String community) { //GOVNO
        vlans.add(new Vlan(vlannr, swip, community));
    }
    */

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
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.portName);
        hash = 53 * hash + this.portNbr;
        hash = 53 * hash + Objects.hashCode(this.switchIp);
        hash = 53 * hash + Objects.hashCode(this.community);
        hash = 53 * hash + Objects.hashCode(this.vlans);
        hash = 53 * hash + Objects.hashCode(this.portVlans);
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
        final Port other = (Port) obj;
        if (!Objects.equals(this.portName, other.portName)) {
            return false;
        }
        if (this.portNbr != other.portNbr) {
            return false;
        }
        if (!Objects.equals(this.switchIp, other.switchIp)) {
            return false;
        }
        if (!Objects.equals(this.community, other.community)) {
            return false;
        }
        if (!Objects.equals(this.vlans, other.vlans)) {
            return false;
        }
        if (!Objects.equals(this.portVlans, other.portVlans)) {
            return false;
        }
        return true;
    }

}
