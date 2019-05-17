/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import org.jdesktop.observablecollections.ObservableCollections;

/**
 *
 * @author Praktikant
 */
public class FrameWrapper {
    private List<Vlan> vlans = ObservableCollections.observableList(new ArrayList<Vlan>());
    private Vlan vlan;
    private Switch curSwitch;
    
    public FrameWrapper() {
        vlans.add(new Vlan("123"));
        vlans.add(new Vlan("1234"));
        curSwitch = new Switch();
    }

    public List<Vlan> getVlans() {
        return vlans;
    }

    public void setVlans(List<Vlan> vlans) {
        this.vlans = vlans;
    }
    
    

    public Vlan getVlan() {
        return vlan;
    }

    public void setVlan(Vlan vlan) {
        this.vlan = vlan;
        System.out.println("vlan " + vlan);
    }

    public Switch getCurSwitch() {
        return curSwitch;
    }

    public void setCurSwitch(Switch curSwitch) {
        this.curSwitch = curSwitch;
    }
    
    
    
}
