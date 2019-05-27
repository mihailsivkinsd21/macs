/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Praktikant
 */
public class VlanOnPort {
    
    private String vlannr;
    
    public VlanOnPort(String vlannr) {
        this.vlannr = vlannr;
    }
    
    public void setVlannr(String vlannr) {
        this.vlannr = vlannr;
    }
    
    public String getVlannr() {
        return vlannr;
    }
    
}
