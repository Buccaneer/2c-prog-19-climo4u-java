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
public class LandGoedNaamTest {

    private String parameter;
    private static Land land;

    public LandGoedNaamTest(String parameter) {
        this.parameter = parameter;

    }

    @Before
    public void maakContinent() {
        land = new Land();

    }

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[]{"België"}, new Object[]{"Verenigde Arabische Emiraten"}, new Object[]{"Bosnië-Hersecovina"}, new Object[]{"UK"});
    }

    @Test
    public void setNaamAccepteerdGoedeNaam() {
        land.setNaam(parameter);

        assertEquals(parameter, land.getNaam());
    }

    @Test
    public void constructorAccepteerdGoedeNaam() {
        Land c = new Land(parameter);

        assertEquals(parameter, c.getNaam());
    }
}
