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
public class Vlan extends PropertySupport {

    

    private String vlanNbr;
    private String switchIp;
    private String community;
    private String gatewayMac;
    //private String port;
    private String gatewayMacCheck = "";
    private int version;
    private int macCount; 
    private ArrayList<Mac> macs = new ArrayList();
    private ArrayList ports = new ArrayList();
    
    public Vlan(){
        
    }
    
    public Vlan(String vlan, String swip){
        this.vlanNbr = vlan;
        this.switchIp = swip;
    }
    
    public Vlan(String vlannr, String swip, String community) {
        this.vlanNbr = vlannr;
        this.switchIp = swip;
        this.community = community;
    }
    
    
    public Vlan(String oid, String newswip, String newcommunity, int newversion, String newGwMac) {
        vlanNbr = oidToVlan(oid);
        switchIp = newswip;
        community = newcommunity;
        version = newversion;
        gatewayMac = newGwMac;
        initMacs();
    }
    
    public static Vlan create(String string) {
        Vlan v = new Vlan();
        v.setVlanNbr(string);
        return v;
    }
     
    public boolean gwMacExists() {
        boolean check = false;
        for (Mac m: macs) {
            String checkmac = m.getAddress();
            if (checkmac.equals(gatewayMac)) {
                check = true;
                break;
            }
        }
        return check;
    }
    
    
    private void initPorts()  {
        
        try {
            ports.clear();
            InetAddress hostAddress = InetAddress.getByName(switchIp);
            SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
            SNMPVarBindList memberPorts = comInterface.getMIBEntry("1.3.6.1.2.1.17.7.1.4.3.1.4." + vlanNbr);
            
            SNMPSequence pair = (SNMPSequence) memberPorts.getSNMPObjectAt(0);
            
            byte[] untg = (byte[]) pair.getSNMPObjectAt(1).getValue();
            
            byte[] trimmedUntg = new byte[16];
            
            for (int i = 0; i < untg.length && i < 16 ; i++) {
                if (untg[i] != 0) {
                    trimmedUntg[i] = untg[i];
                } else {
                    trimmedUntg[i] = 0;
                }
            }
            ArrayList allPorts = Utility.getPortNumbers(trimmedUntg);
            
            
            
            memberPorts = comInterface.getMIBEntry("1.3.6.1.2.1.17.7.1.4.3.1.2." + vlanNbr);
            pair = (SNMPSequence) memberPorts.getSNMPObjectAt(0);
            
            byte[] tg = (byte[]) pair.getSNMPObjectAt(1).getValue();
            
            byte[] trimmedTg = new byte[16];
            
            for (int i = 0; i < tg.length && i < 16 ; i++) {
                if (tg[i] != 0) {
                    trimmedTg[i] = tg[i];
                } else {
                    trimmedTg[i] = 0;
                }
            }
            
            ArrayList taggedPorts = Utility.getPortNumbers(trimmedTg);
            
            Set<String> set = new LinkedHashSet<>(allPorts);
            set.addAll(taggedPorts);
            ArrayList allports = new ArrayList(set);
            
            
            ports = allports;
            
            comInterface.closeConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
         
    }
    
    public ArrayList getPorts() {
        if (ports.isEmpty()) {
            initPorts();
        }
        return ports;
    }
    
    public boolean portExists(int port)  {
        if (ports.isEmpty()) {
            initPorts();
        }
        for (int i = 0; i<ports.size(); i++) {
            if (Integer.parseInt(ports.get(i).toString())==port) {
                return true;
            }
        }
        return false;
    }
    
    
    
    private void initMacs() {

        try {
            macs.clear();
            InetAddress hostAddress = InetAddress.getByName(switchIp);
            SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
            SNMPVarBindList newVars = comInterface.retrieveMIBTable("1.3.6.1.2.1.17.7.1.2.2.1.2." + vlanNbr);

            for (int i = 0; i < newVars.size(); i++) {
                //String s = String.valueOf(((SNMPSequence)newVars.getSNMPObjectAt(i)).getSNMPObjectAt(0));
                String s = Utility.getOid((SNMPSequence) newVars.getSNMPObjectAt(i));
                Mac newMac = new Mac(s);
                macs.add(newMac);
            }
            
            
            comInterface.closeConnection();

            macCount = macs.size();
            if (gwMacExists()) {
                gatewayMacCheck = gatewayMac;
            } else {
                gatewayMacCheck = "";
            }
        } catch (Exception ex) {
           throw new RuntimeException(ex);
        }

    }

    public String getVlanNbr() {
        return vlanNbr;
    }

    public void setVlanNbr(String vlannr) {
        this.vlanNbr = vlannr;
    }
    
    public String getSwitchIp() {
        return switchIp;
    }
    
    public String getGatewayMac() throws IOException, SocketException, UnknownHostException, SNMPBadValueException, SNMPGetException {
        return gatewayMac;
    }
    
    public String getGatewayMacCheck() {
        return gatewayMacCheck;
    }
    
    
    public ArrayList<Mac> getMacs() {
        try {
            if (macs.isEmpty()) {
                initMacs();
            }

            return macs;
        } catch (Exception ex) {
        }
        macs.clear();
        return macs;
    }
    
    private String oidToVlan(String oid) {
        
        String vlan = "";
        String [] parts = oid.split("\\.");
        return parts[parts.length-1];
        
    }
    
    public int getMacCount() {
        return macCount;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.vlanNbr);
        hash = 89 * hash + Objects.hashCode(this.switchIp);
        hash = 89 * hash + Objects.hashCode(this.community);
        hash = 89 * hash + Objects.hashCode(this.gatewayMac);
        hash = 89 * hash + Objects.hashCode(this.gatewayMacCheck);
        hash = 89 * hash + this.version;
        hash = 89 * hash + this.macCount;
        hash = 89 * hash + Objects.hashCode(this.macs);
        hash = 89 * hash + Objects.hashCode(this.ports);
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
        final Vlan other = (Vlan) obj;
        if (!Objects.equals(this.vlanNbr, other.vlanNbr)) {
            return false;
        }
        if (!Objects.equals(this.switchIp, other.switchIp)) {
            return false;
        }
        if (!Objects.equals(this.community, other.community)) {
            return false;
        }
        if (!Objects.equals(this.gatewayMac, other.gatewayMac)) {
            return false;
        }
        if (!Objects.equals(this.gatewayMacCheck, other.gatewayMacCheck)) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.macCount != other.macCount) {
            return false;
        }
        if (!Objects.equals(this.ports, other.ports)) {
            return false;
        }
        return true;
    }
    
    
    

    
}
