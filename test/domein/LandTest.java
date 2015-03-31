/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Jasper De Vrient
 */
public class LandTest {
    
    
    private  Land land;
    private Klimatogram klimatogram;
    
    public LandTest() {
    }
    
    @Before
    public void before() {
        land = new Land("land");
        klimatogram = new Klimatogram();
    }
    
    @Test
    public void klimatogramToevoegenWerkt() {
        land.voegKlimatogramToe(klimatogram);
        
        assertSame(klimatogram, land.getKlimatogrammen().toArray()[0]);
    }
    
    @Test(expected =IllegalArgumentException.class)
    public void nullToevoegenGeeftException() {
       land.voegKlimatogramToe(null);
    }
    
    @Test
    public void klimatogramVerwijderenWerkt()
    {
        land.getKlimatogrammen().add(new Klimatogram("Test"));
        assertTrue(!land.getKlimatogrammen().isEmpty());
        land.verwijderKlimatogram("Test");
        assertTrue(land.getKlimatogrammen().isEmpty());
    }
    
}
