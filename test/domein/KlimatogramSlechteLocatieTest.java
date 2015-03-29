package domein;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class KlimatogramSlechteLocatieTest {

    private String parameter;
    private String reden;
    private static Klimatogram klimatogram;

    public KlimatogramSlechteLocatieTest(String parameter, String reden) {
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
        return Arrays.asList(new Object[]{"#Continent1","numerieke waarden en ongeldige tekens"}, new Object[]{"Zuid_America", "Ongeldige tekens"}, new Object[]{"","lege string"}, new Object[]{null,"null"}, new Object[] {"         ","lege string"}, new Object[] {"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","meer dan 40 chars"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLocatieVerwerptSlechteLocatie() {
        klimatogram.setLocatie(parameter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorVerwerptSlechteLocatie() {
        Klimatogram c = new Klimatogram(parameter);
    }
    
}
