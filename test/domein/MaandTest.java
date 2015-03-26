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
public class MaandTest {
    private Maand maand;
    
    
    public MaandTest() {
    }
    
    @Before
    public void creerMaand() {
        maand = new Maand();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void neerslagKanNietNegatiefZijn() {
        maand.setNeerslag(-1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void temperatuurKanNietNegatiefZijnInGradenKelvin() {
        maand.setTemperatuur(-273.15);
    }
   
    
}
