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
public class Switch {

    
    
    private String  ip = null;
    private String community = "bcomsnmpadmin";
    private int version = 1;
    private ArrayList<Vlan> vlans = new ArrayList();
    private ArrayList<Port> ports = new ArrayList();
    private ArrayList<PortVlan> vlanToPortList = new ArrayList();
    private String status = "";
    private int uplinkNbr = -1;
    //private ArrayList<String> problemVlans = new ArrayList<String>();
    
    
    private final String OID_PORTS = "1.3.6.1.2.1.31.1.1.1.1";
    private final String OID_VLANS = "1.3.6.1.2.1.17.7.1.4.3.1.1";
    private final String OID_MODEL = "1.3.6.1.2.1.1.1.0";
    
    
    private final String OID_GATEWAY_IP = "1.3.6.1.2.1.4.21.1.7";
    private final String OID_GATEWAY_IP_3100TG = "1.3.6.1.2.1.4.24.4.1.4.0.0.0.0.0.0.0.0.0";
    private final String OID_GATEWAY_IP_DLINK1210 = "1.3.6.1.4.1.171.10.76.28.1.42.1.1.4";
    
    private final String OID_GWMAC_ZYXEL = "1.3.6.1.2.1.4.35.1.4.20001.1.4.";
    private final String OID_GWMAC_DLINK = "1.3.6.1.2.1.4.35.1.4.5121.1.4.";
    private final String OID_GWMAC_DLINK3010 = "1.3.6.1.2.1.4.22.1.2.5121.";
    private final String OID_GWMAC_DLINK3526 = "1.3.6.1.2.1.4.22.1.2.5120.";
    private final String OID_GWMAC_DLINK3100 = "1.3.6.1.2.1.4.22.1.2.100016.";
    private final String OID_GWMAC_AT8000S = "1.3.6.1.2.1.4.22.1.2.100002.";
   // private final String OID_GWMAC_
    
   
        
    public Switch() {
        
    }
    
    public void initVlansAndPorts() {
        //vlans.clear();
        //ports.clear();
        //vlanToPortList.clear();
        //initVlans();
        //initPorts();
        initVlans();
        initPorts();
    }
    
    public void initAll() {
        initVlans();
        initMacsOnVlans();
        initPorts();
        initVlanToPortList();
        updateUplinkStatus();
        initPortProblems();
    }
    
    public Switch(String newhostadress, String newcommunity) {
        setIp(newhostadress);
        setCommunity(newcommunity);        
    }
    
    public Switch(String newhostadress, String newcommunity, int uplinkNbr) {
        setIp(newhostadress);
        setCommunity(newcommunity);
        this.uplinkNbr = uplinkNbr;
    }
    
    public void setUplinkNbr(int uplinkNbr) {
        this.uplinkNbr = uplinkNbr;
    }
    
    public int getUplinkNbr() {
        return uplinkNbr;
    }
    
    public void setIp(String newIp) {
        ip = newIp;
    }
    
    public String getCommunity() {
        return community;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setCommunity(String newCommunity) {
        community = newCommunity;
    }
    
    
    
    public String getStatus() {
        boolean statusCheck = true;
        if (vlans.isEmpty()) {
            initVlans();
        }

        for (Vlan v: vlans) {
            if (!"2".equals(v.getVlanNbr())) {
                if (!v.gwMacExists()) {
                    statusCheck = false;
                    break;
                }
            }
        }
        
        if (statusCheck) {
            return "OK";
        }
        return "NOT OK";
    }
    
    
    
    public void initVlanToPortList() {
        try {
            if (ports.isEmpty()) {
                initPorts();
            }
            vlanToPortList.clear();
            for (Port port : ports) {
                vlanToPortList.addAll(port.getPortVlans());
                //System.out.println(port.getPortNbr());
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
    
    public ArrayList<PortVlan> getVlanToPortList() {
        
        return vlanToPortList;
    }
    
    public void initPorts()  {
       
        try {
            ports.clear();
            InetAddress hostAddress = InetAddress.getByName(ip);
            SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
            
            SNMPVarBindList newVars = comInterface.retrieveMIBTable(OID_PORTS);
            String gwMac = getGatewayMac();
            
            for (int i=0; i<newVars.size(); i++) {
                String oid = Utility.getOid((SNMPSequence) newVars.getSNMPObjectAt(i));
                String value =  Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(i));
                if (Utility.isPortOid(oid)) {
                    Port newPort = new Port(oid, value);
                    ports.add(newPort);
                } else {
                    break;
                }
            }
            
            if (vlans.isEmpty()) {
                initVlans();
            }
            
            
            for (Vlan v : vlans) {
                for (Port p : ports) {
                    if (v.portExists(p.getPortNbr())) {
                        p.addVlan(v);
                    }
                }
            }
            
            comInterface.closeConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public ArrayList<Port> getPorts()  {
        return ports;
    }
    
    public Port getPortByNbr(int portNbr) {
        ArrayList<Port> allPorts = new ArrayList<Port>();
        allPorts.addAll(this.getPorts());
        
        for (Port p: allPorts) {
            if (p.getPortNbr()==portNbr) {
                return p;
            }
        }
        return null;
    }
    
    public boolean getUplinkHasDownlinkVlans() {
        if (uplinkNbr!=-1) {
            Port uplinkPort = getPortByNbr(uplinkNbr);

            ArrayList <Port> nonUplinkPorts = new ArrayList<Port>();
            for (Port p : this.getPorts()) {
                if (!p.equals(uplinkPort)) {
                    nonUplinkPorts.add(p);
                }
            }
            
            //System.out.println(uplinkPort.getNonMgmVlanNbrs());
            Set <String> uplinkVlanNbrs = new HashSet(uplinkPort.getNonMgmVlanNbrs());
            //System.out.println(uplinkPort.getNonMgmVlanNbrs());
            Set <String> nonUplinkVlanNbrs = new HashSet <String>();

            for (Port p : nonUplinkPorts) {
                nonUplinkVlanNbrs.addAll(p.getNonMgmVlanNbrs());
            }

    //        if (!uplinkVlanNbrs.containsAll(nonUplinkVlanNbrs) && nonUplinkVlanNbrs.containsAll(uplinkVlanNbrs)) {
    //            
    //            this.initPortProblems(uplinkNbr);
    //            
    //        }



            return uplinkVlanNbrs.containsAll(nonUplinkVlanNbrs) && nonUplinkVlanNbrs.containsAll(uplinkVlanNbrs);
        } else {
            return true;
        }
    }
    
    public void initPortProblems() {
        if (uplinkNbr!=-1) {
            this.updateUplinkStatus();
            ArrayList<String> uplinkVlanNbrs = this.getPortByNbr(uplinkNbr).getNonMgmVlanNbrs();
            Set<String> uplinkBadVlans = new HashSet<String>();
            for (Port p : this.getPorts()) {
                if (!p.getIsUplink()) {
                    p.getProblemVlans().clear();
                    p.getProblemVlans().addAll(p.getNonMgmVlanNbrs());
                    p.getProblemVlans().removeAll(uplinkVlanNbrs);
                    uplinkBadVlans.addAll(p.getProblemVlans());
                    //System.out.println(p.getProblemVlans());
                }
            }
            for (Port p : this.getPorts()) {
                //System.out.println("3");
                if (p.getIsUplink()) {
                    //System.out.println("3");
                    p.getProblemVlans().clear();
                    p.getProblemVlans().addAll(uplinkBadVlans);
                }
            }
        }
    }
    
    public ArrayList<Port> getBadPorts() {
        ArrayList<Port> badPorts = new ArrayList<Port>();
        for (Port p: this.getPorts()) {
            if (!p.getProblemVlans().isEmpty()) {
                badPorts.add(p);
            }
        }
        return badPorts;
    }
    
   
    
    
    public void initVlans()  {
       
        //vlans.clear();
        try {
            vlans.clear();
            InetAddress hostAddress = InetAddress.getByName(ip);
            SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
            
            SNMPVarBindList newVars = comInterface.retrieveMIBTable(OID_VLANS);            
            String gwMac = getGatewayMac();
            
            for (int i = 0; i<newVars.size(); i++) {
                String oid = Utility.getOid((SNMPSequence) newVars.getSNMPObjectAt(i));
                String value = Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(i));
                if (Utility.isVlanOid(oid)) {
                    if (!"default".equals(value)) {
                        Vlan newVlan = new Vlan(oid, ip, community, 1, gwMac);
                        vlans.add(newVlan);
                    }
                } else {
                    break;
                }
            }
            comInterface.closeConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public ArrayList<Vlan> getVlans()  {
        
        return vlans;
    }
    
    
    public String getModel() throws UnknownHostException, SocketException, IOException, SNMPBadValueException, SNMPGetException {
       try {
            InetAddress hostAddress = InetAddress.getByName(ip);             
            SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);

            SNMPVarBindList newVars = comInterface.getMIBEntry(OID_MODEL);
            return Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(0));
       } catch (Exception ex) {
       }
       
       return "";
       
    }
    
    public String getGatewayMac()  {
       
        try {
            InetAddress hostAddress = InetAddress.getByName(ip);
            SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(version, hostAddress, community);
            
            SNMPVarBindList newVars;
            if (getModel().contains("DGS-3100-24TG") || getModel().contains("AT-8000S")) {
                //System.out.println("lal");
                newVars = comInterface.retrieveMIBTable(OID_GATEWAY_IP_3100TG);
            } else if(getModel().contains("DGS-1210")) {
                newVars = comInterface.retrieveMIBTable(OID_GATEWAY_IP_DLINK1210);
            } else {
                newVars = comInterface.retrieveMIBTable(OID_GATEWAY_IP);
            }
            String gwip = Utility.getValue((SNMPSequence) newVars.getSNMPObjectAt(0));

            
            if (getModel().contains("GS22")) {
                newVars = comInterface.getMIBEntry(OID_GWMAC_ZYXEL + gwip);
            } else if (getModel().contains("3010") || getModel().contains("DGS-1210")) {
                newVars = comInterface.getMIBEntry(OID_GWMAC_DLINK3010 + gwip);
            } else if (getModel().contains("3526")) {
                newVars = comInterface.getMIBEntry(OID_GWMAC_DLINK3526 + gwip);
            } else if (getModel().contains("DGS-3100-24TG"))
                newVars = comInterface.getMIBEntry(OID_GWMAC_DLINK3100 + gwip);        
            else if (getModel().contains("AT-8000S")) {
                newVars = comInterface.getMIBEntry(OID_GWMAC_AT8000S + gwip);
            } else {
                newVars = comInterface.getMIBEntry(OID_GWMAC_DLINK + gwip);
            }
            
            //System.out.println(newVars.getSNMPObjectAt(0).toString());
            
            
            comInterface.closeConnection();
            
            return (Utility.getValueAsMac((SNMPSequence) newVars.getSNMPObjectAt(0)));
        } catch (Exception ex) {
           Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
       
    }
    
    public void updateUplinkStatus() {
//        if (ports.isEmpty()) {
//            initPorts();
//        }
        for (Port p: ports) {
            if (p.getPortNbr() == uplinkNbr) {
                p.setIsUplink(true);
            }
        }
    }
    
    public boolean isInited() {
        boolean check = false;
        for (Vlan v: this.getVlans()) {
            if (v.getMacs().size()>0) {
                check = true;
                break;
            }
        }
        
        if (ports.isEmpty()) {
            check = false;
            return check;
        }
        
        if (vlans.isEmpty()) {
            check = false;
            return check;
        }
        
        if (vlanToPortList.isEmpty()) {
            check = false;
            return check;
        }
        
        return check;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.ip);
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
        final Switch other = (Switch) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        
        if (!Objects.equals(this.community, other.community)) {
            return false;
        }
        
//        if (!Objects.equals(this.vlans, other.vlans)) {
//            return false;
//        }
//        
//        if (!Objects.equals(this.ports, other.ports)) {
//            return false;
//        }
//        
//        if (!Objects.equals(this.vlanToPortList, other.vlanToPortList)) {
//            return false;
//        }
                
        
        
        
        return true;
    }
    
    public void initMacsOnVlans() {
        for (Vlan v: this.getVlans()) {
            v.initMacs();
        }
    }
    
    

}