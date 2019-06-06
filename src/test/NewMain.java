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
    
    public static final int UPPORT_NBR = 27;
    public static final int DWNPORT_NBR = 28;
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        
        
        int rep = 0;
        do {
            

              Switch swup = new Switch("172.27.78.237", "bcomsnmpadmin");
              Switch swdown = new Switch("172.27.78.196", "bcomsnmpadmin");
              
              Port p = new Port();
              p.setPortName("test");
              p.setPortNbr(29);
              p.getVlans().add(new Vlan("3842",""));
              p.getVlans().add(new Vlan("2",""));
              p.getVlans().add(new Vlan("22",""));
              swup.getPorts().add(p);
              
              
              System.out.println("Test port: ");  
              System.out.println(swup.getPortByNbr(29).hasSameVlans(swdown.getPortByNbr(28)));
              System.out.println(swup.uplinkHasDownlinkVlans(29));
              
              System.out.println();
              System.out.println("Real port: ");
              System.out.println(swup.getPortByNbr(27).hasSameVlans(swdown.getPortByNbr(28)));
              System.out.println(swup.uplinkHasDownlinkVlans(28));
              

            
              
            
        
        } while (rep!=0);
        
        
                
   
    } 
    
}
