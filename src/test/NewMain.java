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
    


    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        int rep = 0;
        do {
            Switch sw = new Switch("172.27.78.237", "bcomsnmpadmin");
            System.out.println("Gateway mac: ");
            System.out.println(sw.getGwMac());
            System.out.println("Router model: ");
            System.out.println(sw.getModel());
            System.out.println("============: ");
            
            ArrayList<Vlan> vlans = sw.getVlans();
            for (int i=0; i<vlans.size(); i++) {
                System.out.println(vlans.get(i).getVlannr());
            }
            
            ArrayList<VlanToPort> a = sw.getVlanToPortList();
            for (int i = 0; i<a.size(); i++) {
                System.out.print(a.get(i).getPortnr() + " ");
                System.out.println(a.get(i).getVlannr());
            }
            
            System.out.println(a.get(0).getMaccount());
            System.out.println(a.get(0).getMacs().get(0).getAdress());
            
            
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
