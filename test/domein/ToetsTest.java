/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Annemie
 */
public class ToetsTest {

    private Toets toets;

    @Before
    public void maakTest() {
        toets = new Toets();
    }

    /**
     * Test of setTitel method, of class Toets.
     */
    @Test
    public void testSetTitel() {
        toets.setTitel("Test");
        Assert.assertEquals("Test", toets.getTitel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTitelMetNull() {
        toets.setTitel(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTitelMetLegeString() {
        toets.setTitel("");
    }

    /**
     * Test of setBeschrijving method, of class Toets.
     */
    @Test
    public void testSetBeschrijving() {
        toets.setBeschrijving("Dit is een test");
        Assert.assertEquals("Dit is een test", toets.getBeschrijving());
    }

    public void testSetBeschrijvingMetNull() {
        toets.setBeschrijving(null);
        Assert.assertNull(toets.getBeschrijving());
    }

    public void testSetBeschrijvingMetLegeString() {
        toets.setBeschrijving("");
        Assert.assertEquals("", toets.getBeschrijving());
    }

    /**
     * Test of setStartDatumUur method, of class Toets.
     */
    @Test
    public void testSetStartDatumUur() {
        GregorianCalendar date = new GregorianCalendar(2015, 10, 10, 10, 0);
        date.set(Calendar.YEAR, 0);
        date.add(Calendar.YEAR, 1);
        toets.setStartDatumUur(date);
        Assert.assertEquals(date, toets.getStartDatumUur());

    }

    /**
     * Test of setEindDatumUur method, of class Toets.
     */
    @Test
    public void testSetEindDatumUur() {
        GregorianCalendar date = new GregorianCalendar(2015, 10, 10, 12, 0);
        date.set(Calendar.YEAR, 0);
        date.add(Calendar.YEAR, 1);
        toets.setEindDatumUur(date);
        Assert.assertEquals(date, toets.getEindDatumUur());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartDatumLigtVoorVandaag() {
        GregorianCalendar date = new GregorianCalendar(2015, 3, 10);
        toets.setStartDatumUur(date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEindDatumLigtVoorStartDatum() {
        GregorianCalendar date = new GregorianCalendar(2015, 10, 10, 12, 0);
        date.set(Calendar.YEAR, 0);
        date.add(Calendar.YEAR, 1);
        toets.setStartDatumUur(date);

        GregorianCalendar date2 = new GregorianCalendar(2015, 10, 10, 10, 0);
        date.set(Calendar.YEAR, 0);
        date.add(Calendar.YEAR, 1);
        toets.setEindDatumUur(date2);
    }

    /**
     * Test of voegVraagToe method, of class Toets.
     */
    @Test
    public void testVoegVraagToe() {
        DeterminatieVraag vraag = new DeterminatieVraag();
        toets.voegVraagToe(vraag);

    }

    /**
     * Test of verwijderVraag method, of class Toets.
     */
    @Test
    public void testVerwijderVraag() {

    }

    /**
     * Test of berekenTotaleScore method, of class Toets.
     */
    @Test
    public void testBerekenTotaleScore() {

    }

}
