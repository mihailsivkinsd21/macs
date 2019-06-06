/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.util.ArrayList;

/**
 *
 * @author Praktikant
 */
public class SwitchConnection {
    
    private Switch switchUp = null;
    private Switch switchDown = null;
    private int upportNbr;
    private int downportNbr;
    private ArrayList<String> vlanErrors = new ArrayList<String>();
    
    public SwitchConnection(Switch switchUp, Switch switchDown, int upportNbr, int downportNbr) {
        switchUp.init();
        switchDown.init();
        this.upportNbr = upportNbr;
        this.downportNbr = downportNbr;
        this.switchUp = switchUp;
        this.switchDown = switchDown;
        if (!switchUp.getPortByNbr(upportNbr).hasSameVlans(switchDown.getPortByNbr(downportNbr))) {
            switchUp.getPortByNbr(upportNbr).findVlanErrors(switchDown.getPortByNbr(downportNbr));
            vlanErrors.addAll(switchUp.getPortByNbr(upportNbr).getVlansError());
        } else {
            vlanErrors.add("VLANS OK");
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
