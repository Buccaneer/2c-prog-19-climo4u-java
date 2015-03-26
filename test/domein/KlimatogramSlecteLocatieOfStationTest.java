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
public class KlimatogramSlecteLocatieOfStationTest {

    private String parameter;
    private String reden;
    private static Klimatogram klimatogram;

    public KlimatogramSlecteLocatieOfStationTest(String parameter, String reden) {
        this.parameter = parameter;
        this.reden = reden;

    }
    
    @Override
    public String toString() {
        return reden;
    }

    @Before
    public void maakKlimatogram() {
        klimatogram = new Klimatogram();

    }

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[]{"#Continent1","numerieke waarden en ongeldige tekens"}, new Object[]{"Zuid_America", "Ongeldige tekens"}, new Object[]{"","lege string"}, new Object[]{null,"null"}, new Object[] {"         ","lege string"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLocatieVerwerptSlechteLocatie() {
        klimatogram.setLocatie(parameter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorVerwerptSlechteLocatie() {
        Klimatogram c = new Klimatogram(parameter);
    }
    
        @Test(expected = IllegalArgumentException.class)
    public void setStationVerwerptSlechtStation() {
        klimatogram.setStation(parameter);
    }
}
