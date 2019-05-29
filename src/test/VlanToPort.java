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
import java.util.LinkedHashSet;
import java.util.*;
import java.math.*;
import java.net.*;
/**
 *
 * @author Praktikant
 */
public class VlanToPort {
    
    private String vlannr;
    private ArrayList<Mac> macs;
    private int portnr;
    private String portname;
    
    public VlanToPort(String vlannr, ArrayList<Mac> macs, int portnr,String portname) {
        this.vlannr = vlannr;
        this.macs = macs;
        this.portnr = portnr;
        this.portname = portname;
    }
    
    public String getVlannr() {
        return vlannr;
    }
    
    public ArrayList<Mac> getMacs() {
        return macs;
    }
    
    public String getPortname() {
        return portname;
    }
    
    public int getPortnr() {
        return portnr;
    }
    
    public int getMaccount() {
        return macs.size();
    }
    
}
