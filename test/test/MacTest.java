/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Praktikant
 */
public class MacTest {
    
    public MacTest() {
    }
    
   

    /**
     * Test of getAdress method, of class Mac.
     */
    @org.junit.Test
    public void testOidToMac() {
        Mac m = new Mac();
        String oid = "1.3.6.1.2.1.17.7.1.2.2.1.2.19.0.37.13.0.28.197";
        assertEquals("00:25:0D:00:1C:C5", m.oidToMac(oid));
    }

    /**
     * Test of pairToMac method, of class Mac.
     */
   
    
}
