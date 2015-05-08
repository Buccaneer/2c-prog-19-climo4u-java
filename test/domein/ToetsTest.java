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
import org.omg.PortableInterceptor.LOCATION_FORWARD;

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
        GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
        date.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        date.add(Calendar.YEAR, 1);
        toets.setStartDatumUur(date);
        Assert.assertEquals(date, toets.getStartDatumUur());
    }

    /**
     * Test of setEindDatumUur method, of class Toets.
     */
    @Test
    public void testSetEindDatumUur() {
        GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
        date.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        date.add(Calendar.YEAR, 1);
        toets.setEindDatumUur(date);
        Assert.assertEquals(date, toets.getEindDatumUur());
    }

    /**
     * Test of voegVraagToe method, of class Toets.
     */
    @Test
    public void testVoegVraagToe() {
        DeterminatieVraag vraag = new DeterminatieVraag();
        toets.voegVraagToe(vraag);
        Assert.assertEquals(1, toets.getVragen().size());
    }

    /**
     * Test of verwijderVraag method, of class Toets.
     */
    @Test
    public void testVerwijderVraag() {
        DeterminatieVraag vraag = new DeterminatieVraag();
        toets.voegVraagToe(vraag);
        Assert.assertEquals(1, toets.getVragen().size());
        toets.verwijderVraag(vraag);
        Assert.assertEquals(0, toets.getVragen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoegNullVraagToe() {
        toets.voegVraagToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerwijderNullVraag() {
        DeterminatieVraag vraag = new DeterminatieVraag();
        toets.voegVraagToe(vraag);
        toets.verwijderVraag(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVraagVerwijderenDieErNietInZit() {
        DeterminatieVraag vraag = new DeterminatieVraag();
        toets.voegVraagToe(vraag);
        LocatieVraag vraag2 = new LocatieVraag();
        toets.verwijderVraag(vraag2);
    }

    @Test
    public void testVoegKlasToe() {
        Klas klas = new Klas();
        toets.voegKlasToe(klas);
        Assert.assertEquals(1, toets.getKlassen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoegNullKlasToe() {
        toets.voegKlasToe(null);
    }

    @Test
    public void testVerwijderKlas() {
        Klas klas = new Klas();
        toets.voegKlasToe(klas);
        Assert.assertEquals(1, toets.getKlassen().size());
        toets.verwijderKlas(klas);
        Assert.assertEquals(0, toets.getKlassen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerwijderNullKlas() {
        toets.verwijderKlas(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerwijderKlasDieErNietInZit() {
        Klas klas = new Klas();
        Klas klas2 = new Klas();
        toets.voegKlasToe(klas);
        toets.verwijderKlas(klas2);
    }

    /**
     * Test of berekenTotaleScore method, of class Toets.
     */
    @Test
    public void testBerekenTotaleScore() {
        LocatieVraag vraag1 = new LocatieVraag();
        vraag1.setTeBehalenPunten(5);
        LocatieVraag vraag2 = new LocatieVraag();
        vraag2.setTeBehalenPunten(10);
        toets.voegVraagToe(vraag2);
        toets.voegVraagToe(vraag1);
        Assert.assertEquals(15, toets.berekenTotaleScore());
    }
}
