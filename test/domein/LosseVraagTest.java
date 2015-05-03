/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Annemie
 */
public class LosseVraagTest {

    private LosseVraag losseVraag;

    @Before
    public void init() {
        this.losseVraag = new LosseVraag();
    }

    @Test
    public void testSetKlimatogram() {
        Klimatogram klimatogram = new Klimatogram("Deinze");
        losseVraag.setKlimatogram(klimatogram);
        Assert.assertEquals(klimatogram, losseVraag.getKlimatogram());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testSetNullKlimatogram(){
        losseVraag.setKlimatogram(null);
    }

    @Test
    public void testSetSubvragenLijst() {
        List<String> vragen = Arrays.asList(new String[]{"Test1","Test2","Test3"});
        losseVraag.setSubvragenLijst(vragen);
        Assert.assertEquals(vragen, losseVraag.getSubvragenLijst());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testSetNullSubvragenLijst(){
        losseVraag.setSubvragenLijst(null);
    }

    /**
     * Test of voegVraagToe method, of class GraadEenVraag.
     */
    @Test
    public void testVoegVraagToe() {
        losseVraag.voegVraagToe("Testvraag");
        Assert.assertTrue(losseVraag.getSubvragenLijst().contains("Testvraag"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testVoegNullVraagToe(){
        losseVraag.voegVraagToe(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testVoegLegeVraagToe(){
        losseVraag.voegVraagToe("");
    }

    @Test
    public void testVerwijderVraag() {
        String vraag = losseVraag.getSubvragenLijst().get(1);
        losseVraag.verwijderVraag(vraag);
        Assert.assertFalse(losseVraag.getSubvragenLijst().contains(vraag));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testVerwijderNullVraag(){
        losseVraag.verwijderVraag(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testVerwijderLegeVraag(){
        losseVraag.verwijderVraag("");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testVoegDubbeleVraagToe(){
        String vraag = losseVraag.getSubvragenLijst().get(1);
        losseVraag.voegVraagToe(vraag);
    }
}
