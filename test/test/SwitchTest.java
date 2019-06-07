/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Praktikant
 */
public class SwitchTest {
    
    public SwitchTest() {
    }
    
    private static Switch createSwitch(String ip){
        return new  Switch(ip, "bcomsnmpadmin");
    }
    
    private Port createPort(Switch s, int i, String... string) {
        Port p = new Port();
        p.setPortName("test " + i);
        p.setPortNbr(i);
        for (String st : string) {
            p.getVlans().add(new Vlan(st));
        }
        s.getPorts().add(p);
        return p;
    }

    @Test
    public void testUplinkHasDownlinkVlansDownlinksEmpty() {
        Switch swup = createSwitch("172.27.78.237");
        createPort(swup, 29, "3842");
        createPort(swup, 1);
        createPort(swup, 2);
        swup.setUplinkNbr(29);
        assertFalse(swup.getUplinkHasDownlinkVlans());
    }
    
    @Test
    public void testUplinkHasDownlinkVlansSameVlans() {
        Switch swup = createSwitch("172.27.78.237");
        createPort(swup, 29, "3842");
        createPort(swup, 1, "3842");
        createPort(swup, 2, "3842");
        swup.setUplinkNbr(29);
        assertTrue(swup.getUplinkHasDownlinkVlans());
    }
    
    @Test
    public void testUplinkHasDownlinkVlansUplinkEmpty() {
        Switch swup = createSwitch("172.27.78.237");
        createPort(swup, 29);
        createPort(swup, 1, "3842");
        createPort(swup, 2, "3842","2");
        swup.setUplinkNbr(29);
        assertFalse(swup.getUplinkHasDownlinkVlans());
    }
    
    @Test
    public void testUplinkHasDownlinkVlansUplinkNotAllVlans() {
        Switch swup = createSwitch("172.27.78.237");
        createPort(swup, 29, "3842");
        createPort(swup, 1, "3842","2");
        createPort(swup, 2, "3842","22","2");
        swup.setUplinkNbr(29);
        assertFalse(swup.getUplinkHasDownlinkVlans());
        swup.initPortProblems();
        for (Port p: swup.getPorts()) {
            System.out.println(p.getPortNbr() + " " + p.getProblemVlans());
        }
        
    }
    
    @Test
    public void testUplinkHasDownlinkVlansUplinkTooMuchVlans() {
        Switch swup = createSwitch("172.27.78.237");
        createPort(swup, 29, "3842","2","22");
        createPort(swup, 1, "3842");
        createPort(swup, 2, "3842");
        swup.setUplinkNbr(29);
        assertFalse(swup.getUplinkHasDownlinkVlans());
    }
    
    @Test
    public void testUplinkHasDownlinkVlansUplinkAndDownlinkEmpty() {
        Switch swup = createSwitch("172.27.78.237");
        createPort(swup, 29);
        createPort(swup, 1);
        createPort(swup, 2);
        swup.setUplinkNbr(29);
        assertTrue(swup.getUplinkHasDownlinkVlans());
    }
    
    @Test
    public void testUplinkHasDownlinkVlansIgnoreMgm() {
        Switch swup = createSwitch("172.27.78.237");
        createPort(swup, 29, "3842","17","2");
        createPort(swup, 1,"3842");
        createPort(swup, 2,"3842","2");
        swup.setUplinkNbr(29);
        assertTrue(swup.getUplinkHasDownlinkVlans());
    }

    
    
}
