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
       /*
       //String ip;
       Scanner in = new Scanner(System.in);
       
       //ip = in.nextLine();
            
       Switch sw = new Switch("172.27.78.237", "bcomsnmpadmin", 1);
       
       System.out.println("Router model: ");
       System.out.println(sw.getModel());
       System.out.println("Gateway mac: ");
       System.out.println(sw.getGwMac());
       
       
       
       //System.out.println(macToHex("0:1:22:78:225:213"));
       //System.out.println(sw.pairToVlan("( 1.3.6.1.2.1.17.7.1.4.3.1.1.3842 Vl3842 )"));
       
       
       sw.initVlans();
       ArrayList<String> vlans = sw.getVlans();
       System.out.println("=============");
       System.out.println("Vlans: ");
       for (int i=0; i<vlans.size(); i++) {
           System.out.println(vlans.get(i));
       }
       System.out.println("=============");
       String checkvlan;
       
       System.out.print("Vlan nr: ");
       checkvlan = in.nextLine();
       System.out.println("=============");
       ArrayList<String> macsofvlan = sw.getMacs(checkvlan);
       System.out.println("Macs on vlan nr " + checkvlan + " (" + macsofvlan.size() + ") :");
       
       for (int i = 0; i<macsofvlan.size(); i++) {
           System.out.println(macsofvlan.get(i));
       }
       
       System.out.println(sw.gwMacExists(checkvlan));
       */
        
        Switch sw = new Switch();
        System.out.println("Gateway mac: ");
        System.out.println(sw.getGwMac());
        System.out.println("Router model: ");
        System.out.println(sw.getModel());
        System.out.println("============: ");
        
        ArrayList<Vlan> vlans = new ArrayList();
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
        
        
        
        
        
        
    }
   
    
    
}
