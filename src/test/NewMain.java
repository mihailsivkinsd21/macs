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
              
              SwitchConnection con = new SwitchConnection(swup,swdown,27,28);
              System.out.println(con.getVlanErrors());

            
              
            
        
        } while (rep!=0);
        
        
                
   
    } 
    
}
