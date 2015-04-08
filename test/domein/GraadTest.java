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
 * @author Jasper De Vrient
 */
public class GraadTest {

    /**
     * @author Jasper De Vrient
     */
    @Test
    public void koppelenVanGraadEnDeterminatieTabelWerkt() {
        Graad g = new Graad();

        g.setActieveTabel(new DeterminatieTabel());
    }

    /**
     * @author Jasper De Vrient
     */
    @Test(expected = IllegalArgumentException.class)
    public void koppelenVanBezetteGraadEnDeterminatieTabelGeeftException() {
        Graad g = new Graad();
        g.setActieveTabel(new DeterminatieTabel());
        g.setActieveTabel(new DeterminatieTabel());
    }
}