/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
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
import java.util.Scanner;

/**
 *
 * @author Praktikant
 */   



public class NewMain {

    /**
     * @param args the command line arguments
     */
    
    public static final int UPPORT_NBR = 26;
    public static final int DWNPORT_NBR = 27;
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        
        
        int rep = 0;
        do {
            
            Switch swup = new Switch("172.27.78.237", "bcomsnmpadmin");
            
            Switch swdown = new Switch ("172.27.78.196", "bcomsnmpadmin");
            
            
            ArrayList <Vlan> portupvlans = swup.getPorts().get(UPPORT_NBR).getVlans();
            ArrayList <Vlan> portdownvlans = swdown.getPorts().get(DWNPORT_NBR).getVlans();
            
            for (Vlan v: portupvlans) {
                System.out.println(v.getVlanNbr());
            }
            System.out.println("");
            for (Vlan v: portdownvlans) {
                System.out.println(v.getVlanNbr());
            }
            
            //sw.initVlanToPortList();
//            for (PortVlan p: sw.getVlanToPortList()) {
//                System.out.println(p.getPortNbr() + "  " + p.getVlanNbr());
//            }
            
            System.out.println(Utility.checkPorts(swup.getPorts().get(UPPORT_NBR), swdown));
            
            
            //System.out.println(sw.getStatus());

            /*ArrayList<Vlan> vlans = new ArrayList();
            vlans = sw.getVlans();
            System.out.println("Vlans: ");
            for (int i = 0; i<vlans.size(); i++) {
                System.out.println(vlans.get(i).getVlannr());
            }
            System.out.println("============: ");

            Scanner in = new Scanner(System.in);
            System.out.print("Check vlan (position): ");
            int check;
            check = in.nextInt();
            ArrayList<Mac> macs = vlans.get(check).getMacs();
            System.out.println("Macs of vlan " + vlans.get(check).getVlannr() + " (" + macs.size() + ") : ");
            for (int i=0; i<macs.size(); i++) {
                System.out.println(macs.get(i).getAdress());
            }
            

            System.out.println(vlans.get(check).gwMacExists());
        
            
            System.out.println(sw.getStatus());
                
                
                
            System.out.print("Repeat? 0=no : ");
            rep=in.nextInt();
        */
        
        } while (rep!=0);
        
        
                
   
    } 
    
}
