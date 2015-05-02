/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Annemie
 */
public class DerdeGraadVraagTest {
    
    public DerdeGraadVraagTest() {
    }

    /**
     * Test of getKlimatogrammen method, of class DerdeGraadVraag.
     */
    @Test
    public void testGetKlimatogrammen() {
        System.out.println("getKlimatogrammen");
        DerdeGraadVraag instance = new DerdeGraadVraag();
        Set<Klimatogram> expResult = null;
        Set<Klimatogram> result = instance.getKlimatogrammen();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setKlimatogrammen method, of class DerdeGraadVraag.
     */
    @Test
    public void testSetKlimatogrammen() {
        System.out.println("setKlimatogrammen");
        HashSet<Klimatogram> klimatogrammen = null;
        DerdeGraadVraag instance = new DerdeGraadVraag();
        instance.setKlimatogrammen(klimatogrammen);
        fail("The test case is a prototype.");
    }

    /**
     * Test of voegKlimatogramToe method, of class DerdeGraadVraag.
     */
    @Test
    public void testVoegKlimatogramToe() {
        System.out.println("voegKlimatogramToe");
        Klimatogram klimatogram = null;
        DerdeGraadVraag instance = new DerdeGraadVraag();
        instance.voegKlimatogramToe(klimatogram);
        fail("The test case is a prototype.");
    }

    /**
     * Test of verwijderKlimatogram method, of class DerdeGraadVraag.
     */
    @Test
    public void testVerwijderKlimatogram() {
        System.out.println("verwijderKlimatogram");
        Klimatogram klimatogram = null;
        DerdeGraadVraag instance = new DerdeGraadVraag();
        instance.verwijderKlimatogram(klimatogram);
        fail("The test case is a prototype.");
    }
    
}
