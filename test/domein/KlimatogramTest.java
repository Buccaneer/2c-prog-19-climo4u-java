/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Jasper De Vrient
 */
public class KlimatogramTest {

    private Klimatogram klimatogram;
    private static final int HUIDIGJAAR = 2015;

    public KlimatogramTest() {
    }

    @Before
    public void MaakKlimatogram() {
        klimatogram = new Klimatogram();
    }

    @Test(expected = IllegalArgumentException.class)
    public void latitudeMagNietKleinerZijnDanMin90() {
        klimatogram.setLatitude(-91.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void latitudeMagNietGroterZijnDan90() {
        klimatogram.setLatitude(1848.48);
    }

    @Test(expected = IllegalArgumentException.class)
    public void longitudeMagNietKleinerZijnDanMin180() {
        klimatogram.setLongitude(-181.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void longitudeMagNietGroterZijnDan180() {
        klimatogram.setLongitude(18100.0);
    }

    @Test
    public void longitudeNegatieveGrenswaardeWerkt() {
        double grenswaarde = -180;
        klimatogram.setLongitude(grenswaarde);

        assertEquals(grenswaarde, klimatogram.getLongitude(), 0);
    }

    @Test
    public void longitudePositieveGrenswaardeWerkt() {
        double grenswaarde = 180;
        klimatogram.setLongitude(grenswaarde);

        assertEquals(grenswaarde, klimatogram.getLongitude(), 0);
    }

    @Test
    public void latitudeNegatieveGrenswaardeWerkt() {
        double grenswaarde = -90;
        klimatogram.setLongitude(grenswaarde);

        assertEquals(grenswaarde, klimatogram.getLongitude(), 0);
    }

    @Test
    public void latitudePositieveGrenswaardeWerkt() {
        double grenswaarde = 90;
        klimatogram.setLongitude(grenswaarde);

        assertEquals(grenswaarde, klimatogram.getLongitude(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void beginJaarMagNietKleinerDan0Zijn() {
        klimatogram.setBeginJaar(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void beginJaarMagNietGroterDanHuidigJaarZijn() {

        klimatogram.setBeginJaar(HUIDIGJAAR + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eindJaarMagNietKleinerDan0Zijn() {
        klimatogram.setEindJaar(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eindJaarMagNietGroterDanHuidigJaarZijn() {

        klimatogram.setEindJaar(HUIDIGJAAR + 1);
    }

    @Test
    public void constructorMaaktAlleMaandenAan() {
        int p = klimatogram.getMaanden().size();
        final int EXPECTED = 12;
        
        assertEquals(EXPECTED, p);
    }
}
