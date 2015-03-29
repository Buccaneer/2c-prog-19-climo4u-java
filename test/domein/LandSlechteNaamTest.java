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
public class LandSlechteNaamTest {

    private String parameter;
    private String reden;
    private static Land land;

    public LandSlechteNaamTest(String parameter, String reden) {
        this.parameter = parameter;
        this.reden = reden;

    }
    
    @Override
    public String toString() {
        return reden;
    }

    @Before
    public void maakContinent() {
        land = new Land();

    }

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[]{"#land","numerieke waarden en ongeldige tekens"}, new Object[]{"F_r_a_n_k_e", "Ongeldige tekens"}, new Object[]{"","lege string"}, new Object[]{null,"null"}, new Object[] {"         ","lege string"}, new Object[] {"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","meer dan 40 chars"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNaamVerwerptSlechteNaam() {
        land.setNaam(parameter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorVerwerptSlechteNaam() {
        Land c = new Land(parameter);
    }
}
