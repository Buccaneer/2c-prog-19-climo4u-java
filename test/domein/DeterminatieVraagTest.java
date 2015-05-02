/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Annemie
 */
public class DeterminatieVraagTest {
    
    public DeterminatieVraagTest() {
    }

    /**
     * Test of getKlimatogram method, of class DeterminatieVraag.
     */
    @Test
    public void testGetKlimatogram() {
        System.out.println("getKlimatogram");
        DeterminatieVraag instance = new DeterminatieVraag();
        Klimatogram expResult = null;
        Klimatogram result = instance.getKlimatogram();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setKlimatogram method, of class DeterminatieVraag.
     */
    @Test
    public void testSetKlimatogram() {
        System.out.println("setKlimatogram");
        Klimatogram klimatogram = null;
        DeterminatieVraag instance = new DeterminatieVraag();
        instance.setKlimatogram(klimatogram);
        fail("The test case is a prototype.");
    }
    
}
