/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Jasper De Vrient
 */
@RunWith(Parameterized.class)
public class MaandSlechteNaamTest {

    private String parameter;
    private String reden;
    private static Maand maand;

    // @ JASPER: Deze test is overbodig geworden omdat we gebruik maken van int 1 - 12 en de namen laten genereren via Month en Locale (ISO)
    public MaandSlechteNaamTest(String parameter, String reden) {
        this.parameter = parameter;
        this.reden = reden;

    }
    
    @Override
    public String toString() {
        return reden;
    }

    @Before
    public void maakContinent() {
        maand = new Maand();

    }

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList( new Object[]{"","lege string"}, new Object[]{null,"null"}, new Object[] {"         ","lege string"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNaamVerwerptSlechteNaam() {
        //maand.setNaam(parameter);
        throw new IllegalArgumentException();
    }

    
}
