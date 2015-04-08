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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Jasper De Vrient
 */
@RunWith(Parameterized.class)
public class ValiderenDeterminatieKnoopExceptieTest {

    private String naam;
    private String reden;

    public ValiderenDeterminatieKnoopExceptieTest(String naam, String reden) {
        this.naam = naam;
        this.reden = reden;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[]{"#Continent1", "numerieke waarden en ongeldige tekens"}, new Object[]{"Zuid_America", "Ongeldige tekens"}, new Object[]{"        ", "lege string"}, new Object[]{"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "meer dan 40 chars"}, new Object[]{"", "lege string"}, new Object[]{null, "null"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void foutiefKlimaatTypeGooitExecption() {
        ResultaatBlad b = new ResultaatBlad();
        b.setKlimaatType(naam);

        b.valideer();
    }

}
