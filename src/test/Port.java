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
    private ArrayList<Vlan> vlans = new ArrayList();
    private ArrayList<PortVlan> portVlans = new ArrayList();
    private ArrayList<String> vlansError = new ArrayList<String>();
    private boolean isUplink;
    private ArrayList <String> problemVlans = new ArrayList<String>();
    
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
    
    public boolean getIsUplink() {
        return isUplink;
    }
    
    public void setIsUplink(boolean is) {
        this.isUplink = is;
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
    
    public ArrayList<String> getVlansError() {
        return vlansError;
    }

    public int getVlanSize() {
        return vlans.size();
    }

    public ArrayList<Vlan> getVlans() {
        return vlans;
    }
    
    public ArrayList<Vlan> getNonMgmVlans() {
        ArrayList<Vlan> nonMgmVlans = new ArrayList<Vlan>();
        for (Vlan v: getVlans()) {
            if (!"17".equals(v.getVlanNbr()) && !"19".equals(v.getVlanNbr()) && !"2".equals(v.getVlanNbr())) {
                nonMgmVlans.add(v);
            }
        }
        return nonMgmVlans;
    }

    public int getPortNbr() {
        return portNbr;
    }
    
    public void setPortNbr(int portNbr) {
        this.portNbr = portNbr;
    }
    
    public void setPortName(String name) {
        this.portName = name;
    }

    public String getPortName() {
        return portName;
    }

    private int oidToPortNbr(String oid) {
        String[] parts = oid.split("\\.");
        return Integer.parseInt(parts[parts.length - 1]);
    }
    
    public boolean hasSameVlans(Port otherPort) {
        
//        System.out.println(this.getNonMgmVlanNbrs());
//        System.out.println(otherPort.getNonMgmVlanNbrs());
        
        return this.getNonMgmVlanNbrs().containsAll(otherPort.getNonMgmVlanNbrs()) && otherPort.getNonMgmVlanNbrs().containsAll(this.getNonMgmVlanNbrs());
        
    }
    
    public void findVlanErrors(Port otherPort) {
        
        this.getVlansError().clear();
        
        ArrayList<String> checkList = new ArrayList<String>();
        checkList.addAll(this.getNonMgmVlanNbrs());
        checkList.removeAll(otherPort.getNonMgmVlanNbrs());
        this.getVlansError().addAll(checkList);
        
        checkList.clear();
        checkList.addAll(otherPort.getNonMgmVlanNbrs());
        checkList.removeAll(this.getNonMgmVlanNbrs());
        this.getVlansError().addAll(checkList);
        
    }
    
    public ArrayList<String> getNonMgmVlanNbrs() {
        ArrayList <Vlan> nonMgmVlans = this.getNonMgmVlans();
        ArrayList <String> vlanNbrs = new ArrayList<String>();
        for (Vlan v: nonMgmVlans) {
            vlanNbrs.add(v.getVlanNbr());
        }
        return vlanNbrs;
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
    
    public ArrayList<String> getProblemVlans() {
        return problemVlans;
    }
   

}
