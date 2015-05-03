/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Annemie
 */
public class LocatieVraagTest {

    private LocatieVraag vraag;

    @Before
    public void init(){
        vraag=new LocatieVraag();
    }
    
    @Test
    public void testSetKlimatogrammen() {
        Klimatogram klim1 = new Klimatogram("Testeen");
        Klimatogram klim2 = new Klimatogram("Testtwee");
        Klimatogram klim3 = new Klimatogram("Testdrie");
        HashSet<Klimatogram> set = new HashSet<>();
        set.addAll(Arrays.asList(new Klimatogram[]{klim1, klim2, klim3}));
        vraag.setKlimatogrammen(set);
        Assert.assertEquals(3, vraag.getKlimatogrammen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullKlimatogrammen() {
        vraag.setKlimatogrammen(null);
    }

    /**
     * Test of voegKlimatogramToe method, of class DerdeGraadVraag.
     */
    @Test
    public void testVoegKlimatogramToe() {
        Klimatogram klim1 = new Klimatogram("Testeen");
        Klimatogram klim2 = new Klimatogram("Testtwee");
        Klimatogram klim3 = new Klimatogram("Testdrie");
        vraag.voegKlimatogramToe(klim3);
        vraag.voegKlimatogramToe(klim2);
        vraag.voegKlimatogramToe(klim1);
        Assert.assertEquals(3, vraag.getKlimatogrammen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoegNullKlimatogramToe() {
        vraag.voegKlimatogramToe(null);
    }

    /**
     * Test of verwijderKlimatogram method, of class DerdeGraadVraag.
     */
    @Test
    public void testVerwijderKlimatogram() {
        Klimatogram klim1 = new Klimatogram("Testeen");
        Klimatogram klim2 = new Klimatogram("Testtwee");
        Klimatogram klim3 = new Klimatogram("Testdrie");
        vraag.voegKlimatogramToe(klim3);
        vraag.voegKlimatogramToe(klim2);
        vraag.voegKlimatogramToe(klim1);
        Assert.assertEquals(3, vraag.getKlimatogrammen().size());
        vraag.verwijderKlimatogram(klim1);
        Assert.assertEquals(2, vraag.getKlimatogrammen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerwijderNullKlimatogram() {
        vraag.verwijderKlimatogram(null);
    }
}
