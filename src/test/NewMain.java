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
        
            
            ArrayList<Port> ports = sw.getPorts();
            for (int i = 0; i<ports.size(); i++) {
                System.out.print(ports.get(i).getPortnr() + " ");
                System.out.println(ports.get(i).getPortname());
            }
            
            System.out.print("Repeat? 0=no : ");
            rep=in.nextInt();
        
        
        } while (rep!=0);
        
        /*
        Mac test = new Mac();
        System.out.println(test.oidToMac("1.3.6.1.2.1.17.7.1.2.2.1.2.19.0.37.13.0.28.197"));
        Vlan vtest = new Vlan();
        System.out.println(vtest.oidToVlan("1.3.6.1.2.1.17.7.1.2"));
                */
   
    } 
    
}
