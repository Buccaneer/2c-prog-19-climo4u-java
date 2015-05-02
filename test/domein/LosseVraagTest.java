/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Annemie
 */
public class LosseVraagTest {
    
    public LosseVraagTest() {
    }

    /**
     * Test of getKlimatogram method, of class GraadEenVraag.
     */
    @Test
    public void testGetKlimatogram() {
        System.out.println("getKlimatogram");
        LosseVraag instance = new LosseVraag();
        Klimatogram expResult = null;
        Klimatogram result = instance.getKlimatogram();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setKlimatogram method, of class GraadEenVraag.
     */
    @Test
    public void testSetKlimatogram() {
        System.out.println("setKlimatogram");
        Klimatogram klimatogram = null;
        LosseVraag instance = new LosseVraag();
        instance.setKlimatogram(klimatogram);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSubvragenLijst method, of class GraadEenVraag.
     */
    @Test
    public void testGetSubvragenLijst() {
        System.out.println("getSubvragenLijst");
        LosseVraag instance = new LosseVraag();
        List<String> expResult = null;
        List<String> result = instance.getSubvragenLijst();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSubvragenLijst method, of class GraadEenVraag.
     */
    @Test
    public void testSetSubvragenLijst() {
        System.out.println("setSubvragenLijst");
        List<String> subvragenLijst = null;
        LosseVraag instance = new LosseVraag();
        instance.setSubvragenLijst(subvragenLijst);
        fail("The test case is a prototype.");
    }

    /**
     * Test of voegVraagToe method, of class GraadEenVraag.
     */
    @Test
    public void testVoegVraagToe() {
        System.out.println("voegVraagToe");
        String vraag = "";
        LosseVraag instance = new LosseVraag();
        instance.voegVraagToe(vraag);
        fail("The test case is a prototype.");
    }

    /**
     * Test of verwijderVraag method, of class GraadEenVraag.
     */
    @Test
    public void testVerwijderVraag() {
        System.out.println("verwijderVraag");
        String vraag = "";
        LosseVraag instance = new LosseVraag();
        instance.verwijderVraag(vraag);
        fail("The test case is a prototype.");
    }
    
}
