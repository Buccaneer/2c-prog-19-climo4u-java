package domein;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class KlimatogramSlechtStationTest {

    private String parameter;
    private String reden;
    private static Klimatogram klimatogram;

    public KlimatogramSlechtStationTest(String parameter, String reden) {
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
        return Arrays.asList(new Object[]{"Continent","letters"}, new Object[]{"Zuid_America", "ongeldige tekens"}, /*new Object[]{"","lege string"}, new Object[]{null,"null"}, new Object[] {"       ","lege string spaties"},*/ new Object[] {"666666","meer dan 5 tekens"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void setStationVerwerptSlechtStation() {
        klimatogram.setStation(parameter);
    }
}
