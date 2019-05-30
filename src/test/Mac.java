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
public class Mac {
    
    private String address;
    
    public Mac() {        
    }
    
    public Mac(String oid) {
        address = oidToMac(oid);
    }
    
    public String getAddress() {
        return address;
    }
    
    public String oidToMac(String oid) {
        String[] parts = oid.split("\\.");
        String macDec = "";
        for (int i = parts.length - 6; i < parts.length; i++) {
            macDec = macDec + parts[i];
            if (i != parts.length - 1) {
                macDec = macDec + ":";
            }
        }
        return macToHex(macDec).toUpperCase();
    }
    
    private String macToHex(String str) {
        try {
            String separator = ":";
            String result = "";
            String[] macParts = str.trim().split("\\:");
            for (int i = 0; i < macParts.length; i++) {
                Integer dec = Integer.parseInt(macParts[i]);
                String hexOctet = Integer.toHexString(dec);
                if (hexOctet.length() < 2) {
                    hexOctet = "0" + hexOctet;
                }
                
                if (i < 5) {
                    result += hexOctet + separator;
                } else {
                    result += hexOctet;
                }
            }
            //result.toLowerCase();
            return result;

        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.address);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mac other = (Mac) obj;
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }
    
}
