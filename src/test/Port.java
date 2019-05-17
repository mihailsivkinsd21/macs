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
    
    private String swip;
    private String community;
    private String gwMac;
    private String portname;
    private int portnr;
    private int version;
    ArrayList<Vlan> vlans = new ArrayList();
    
    public Port(String oid, String value, String newswip, String newcommunity, String newGwMac, int newversion) {
        swip = newswip;
        community = newcommunity;
        gwMac = newGwMac;
        portname = value;
        portnr = oidToPortnr(oid);
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
