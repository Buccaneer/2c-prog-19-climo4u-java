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
public class ContinentTest {
    
    private  Continent continent;
    private  Land land;
    
    public ContinentTest() {
    }
    
    @Before
    public void before() {
        continent = new Continent("continent");
        land = new Land("land");
    }
    
    @Test
    public void landToevoegenWerkt() {
        continent.voegLandToe(land);
        
        assertSame(land, continent.getLanden().toArray()[0]);
    }
    
    @Test(expected =IllegalArgumentException.class)
    public void nullToevoegenGeeftException() {
        continent.voegLandToe(null);
    }
    
}
