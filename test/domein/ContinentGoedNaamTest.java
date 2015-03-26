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
public class ContinentGoedNaamTest {

    private String parameter;
    private static Continent continent;

    public ContinentGoedNaamTest(String parameter) {
        this.parameter = parameter;

    }

    @Before
    public void maakContinent() {
        continent = new Continent();

    }

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[]{"Afrika"}, new Object[]{"Zuid-America"}, new Object[]{"Azië"}, new Object[]{"Eur Azië"});
    }

    @Test
    public void setNaamAccepteerdGoedeNaam() {
        continent.setNaam(parameter);

        assertEquals(parameter, continent.getNaam());
    }

    @Test
    public void constructorAccepteerdGoedeNaam() {
        Continent c = new Continent(parameter);

        assertEquals(parameter, c.getNaam());
    }
}
