/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dmitrijsli
 */
public class PortTest {
    
    public PortTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getVlansToPort method, of class Port.
     */
    @Test
    public void testGetVlansStringTwo() {        
        Port p = new Port();        
        p.getVlans().add(Vlan.create("10"));
        p.getVlans().add(Vlan.create("1"));
        assertEquals("10, 1", p.getVlansString());        
    }        
    
    @Test
    public void testGetVlansStringFive() {        
        Port p = new Port();                
        p.getVlans().add(Vlan.create("1"));
        p.getVlans().add(Vlan.create("2"));
        p.getVlans().add(Vlan.create("3"));
        p.getVlans().add(Vlan.create("4"));
        p.getVlans().add(Vlan.create("5"));    
        assertEquals("1, 2, 3, 4, 5", p.getVlansString());        
    }        
    
    @Test
    public void testGetVlansStringSix() {        
        Port p = new Port();                
        p.getVlans().add(Vlan.create("1"));
        p.getVlans().add(Vlan.create("2"));
        p.getVlans().add(Vlan.create("3"));
        p.getVlans().add(Vlan.create("4"));
        p.getVlans().add(Vlan.create("5"));
        p.getVlans().add(Vlan.create("6"));
        assertEquals("1, 2, 3, 4, 5...", p.getVlansString());        
    }        
    
}
