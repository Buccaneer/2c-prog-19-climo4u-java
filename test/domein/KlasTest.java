package domein;

import org.junit.Test;

/**
 *
 * @author Pieter-Jan
 */
public class KlasTest {

    @Test(expected = IllegalArgumentException.class)
    public void setNaamNullGooitException() {
        new Klas().setNaam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLeerjaarNegatiefGooitException() {
        new Klas().setLeerjaar(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGraadNullGooitException() {
        new Klas().setGraad(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegNulltoetsToeGeeftException() {
        new Klas().voegToetsToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegNullLeerlingToeGeeftException() {
        new Klas().voegLeerlingToe(null);
    }
}
