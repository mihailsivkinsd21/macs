/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.util.ArrayList;
import java.util.*;
/**
 *
 * @author Praktikant
 */
public class PortVlan {
    
    private String vlanNbr;
    private List<Mac> macs = new ArrayList<Mac>();
    private int portNbr;
    private String portName;

    public PortVlan() {
    }
    
    public PortVlan(String vlannr, ArrayList<Mac> macs, int portNbr, String portName) {
        this.vlanNbr = vlannr;
        this.macs = macs;
        this.portNbr = portNbr;
        this.portName = portName;
    }
    
    public String getVlanNbr() {
        return vlanNbr;
    }

    public void setMacs(List<Mac> macs) {
        this.macs = macs;
    }
    
    public List<Mac> getMacs() {
        return macs;
    }
    
    public String getPortName() {
        return portName;
    }
    
    public int getPortNbr() {
        return portNbr;
    }
    
    public int getMacsSize() {
        return macs.size();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.vlanNbr);
        hash = 13 * hash + this.portNbr;
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
        final PortVlan other = (PortVlan) obj;
        if (this.portNbr != other.portNbr) {
            return false;
        }
        if (!Objects.equals(this.vlanNbr, other.vlanNbr)) {
            return false;
        }
        return true;
    }
    
    
    
}
