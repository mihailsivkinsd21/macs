/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import snmp.SNMPBadValueException;
import snmp.SNMPGetException;

/**
 *
 * @author Praktikant
 */
public class SwitchConnection {
    
    private Switch switchUp = null;
    private Switch switchDown = null;
    private int upportNbr = 0;
    private int downportNbr = 0;
    private ArrayList<String> vlanErrors = new ArrayList<String>();
    
    public SwitchConnection(Switch switchUp, Switch switchDown, int upportNbr, int downportNbr) {
        try {
            this.upportNbr = upportNbr;
            this.downportNbr = downportNbr;
            this.switchUp = switchUp;
            this.switchDown = switchDown;
            if (switchUp.getModel().equals("TIMEOUT") || switchDown.getModel().equals("TIMEOUT")) {
                vlanErrors.add("SWITCH TIMEOUT");
            } else {
                if (switchUp.getPorts().isEmpty() || switchUp.getVlans().isEmpty()) {
                    switchUp.initVlansAndPorts();
                }
                if (switchDown.getPorts().isEmpty() || switchDown.getVlans().isEmpty()) {
                    switchDown.initVlansAndPorts();
                }
            
            

           
                if (!switchUp.getPortByNbr(upportNbr).hasSameVlans(switchDown.getPortByNbr(downportNbr))) {
                    switchUp.getPortByNbr(upportNbr).findVlanErrors(switchDown.getPortByNbr(downportNbr));
                    vlanErrors.addAll(switchUp.getPortByNbr(upportNbr).getVlansError());
                } else {
                    vlanErrors.add("VLANS OK");
                }
            } 
                
            
        } catch (SocketException ex) {
            Logger.getLogger(SwitchConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SwitchConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SNMPBadValueException ex) {
            Logger.getLogger(SwitchConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SNMPGetException ex) {
            Logger.getLogger(SwitchConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Switch getSwitchUp() {
        return switchUp;
    }

    public void setSwitchUp(Switch switchUp) {
        this.switchUp = switchUp;
    }

    public Switch getSwitchDown() {
        return switchDown;
    }

    public void setSwitchDown(Switch switchDown) {
        this.switchDown = switchDown;
    }

    public int getUpportNbr() {
        return upportNbr;
    }

    public void setUpportNbr(int upportNbr) {
        this.upportNbr = upportNbr;
    }

    public int getDownportNbr() {
        return downportNbr;
    }

    public void setDownportNbr(int downportNbr) {
        this.downportNbr = downportNbr;
    }

    public ArrayList<String> getVlanErrors() {
        return vlanErrors;
    }
    
    public String getVlanErrorsString() {
        return vlanErrors.toString();
    }

    public void setVlanErrors(ArrayList<String> vlanErrors) {
        this.vlanErrors = vlanErrors;
    }
    
    public Port getUpPort() {
        return switchUp.getPortByNbr(upportNbr);
    }
    
    public Port getDownPort() {
        return switchDown.getPortByNbr(downportNbr);
    }
  
    
   
    
    
}
