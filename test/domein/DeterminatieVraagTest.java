/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Annemie
 */
public class DeterminatieVraagTest {
    @Test
    public void testSetKlimatogram() {
        Klimatogram klimatogram = new Klimatogram("Deinze");
        DeterminatieVraag vraag = new DeterminatieVraag();
        vraag.setKlimatogram(klimatogram);
        Assert.assertEquals(klimatogram,vraag.getKlimatogram());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testSetNullKlimatogram(){
        new DeterminatieVraag().setKlimatogram(null);
    }
}

