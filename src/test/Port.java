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
    
    private String portname;
    private int portnr;
    private int vlanscount;
    ArrayList<VlanOnPort> vlans = new ArrayList();
    private int vlanssize;
    
    public Port(String oid, String value) {
        portname = value;
        portnr = oidToPortnr(oid);
    }
    
    public String getVlansString() {
        //для поля в таблице с портами, где отображаются вланы
        String result = "";
        if (vlans.size()>5) {
            for (int i=0; i<5; i++) {
                result = result + vlans.get(i).getVlannr() + " , ";
            }
            result = result + "...";
        } else {
            for (int i=0; i<vlans.size(); i++) {
                result = result + vlans.get(i).getVlannr();
                if ((vlans.size()-1)!=i) {
                    result = result + " , ";
                }
            }
        }
        return result;
    }
    
    public void addVlan(String vlannr) {
        vlans.add(new VlanOnPort(vlannr));
        vlanssize++;
    }
    
    public int getVlanssize() {
        return vlanssize;
    }
    
    public ArrayList<VlanOnPort> getVlans() {
        return vlans;
    }
    
    public int getPortnr() {
        return portnr;
    }
    
    public String getPortname() {
        return portname;
    }
    
    private int oidToPortnr(String oid) {
        String [] parts = oid.split("\\.");
        return Integer.parseInt(parts[parts.length-1]);
    }
   
    
}
