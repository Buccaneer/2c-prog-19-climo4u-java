package domein;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class VegetatieTypeFoutieveNaamParameterizedTest {
    
    private String waarde;
    private String reden;

    public VegetatieTypeFoutieveNaamParameterizedTest(String waarde, String reden) {
        this.waarde = waarde;
        this.reden = reden;
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[]{"#Continent","ongeldige tekens"}, new Object[]{"","lege string"}, new Object[]{null,"null"}, new Object[] {"         ","lege string"});
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setNaamMetOngeldigeWaardeGooitExecption() {
        VegetatieType veggie = new VegetatieType();
        veggie.setNaam(waarde);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setFotoMetOngeldigeWaardeGooitExecption() {
        VegetatieType veggie = new VegetatieType();
        veggie.setFoto(waarde);
    }
    
}
