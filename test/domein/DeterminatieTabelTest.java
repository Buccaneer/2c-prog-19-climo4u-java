/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import dto.DeterminatieKnoopDto;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jasper De Vrient
 */
public class DeterminatieTabelTest {

    /**
     * Kijkt of ik wel een tabel kan instellen.
     *
     * @author Jasper De Vrient
     */
    @Test
    public void koppelenVanGraadEnDeterminatieTabelWerkt() {
        Graad g = new Graad();
        DeterminatieTabel t = new DeterminatieTabel();

        g.setActieveTabel(t);

        assertSame(t, g.getActieveTabel());
    }

    /**
     * Kijkt of een bezette graad niet gekoppeld kan worden een een
     * determinatietabel.
     *
     * @author Jasper De Vrient
     */
    @Test(expected = IllegalArgumentException.class)
    public void koppelenVanBezetteGraadEnDeterminatieTabelGeeftException() {
        Graad g = new Graad();
        g.setActieveTabel(new DeterminatieTabel());
        g.setActieveTabel(new DeterminatieTabel());
    }

    /**
     * @author Jasper De Vrient
     */
    @Test
    public void omzettenBeslissingsKnoopInResultaatBladWerkt() {
        BeslissingsKnoop knoop = new BeslissingsKnoop(14);
        BeslissingsKnoop r = new BeslissingsKnoop(15);

        knoop.setJuistKnoop(r);

        DeterminatieTabel t = new DeterminatieTabel();
        t.setBeginKnoop(knoop);

        DeterminatieKnoopDto dto = r.maakDtoAan();
        dto.toResultaatBlad();

        t.wijzigKnoop(dto);

        assertNotSame(r, knoop.getJuistKnoop());

        // Indien ik kan casten zonder exception test gelukt.
        ResultaatBlad f = (ResultaatBlad) (knoop.getJuistKnoop());

    }

    @Test
    public void omzettenBeginKnoopResetDeBoom() {
        fail("Test niet af.");
    }

    @Test
    public void wijzigResultaatBladWerkt() {
        fail("Test niet af.");
    }

    /**
     * @author Jasper De Vrient
     */
    @Test
    public void omzettenResultaatBladInBeslissingsKnoopWerkt() {
        BeslissingsKnoop knoop = new BeslissingsKnoop(14);
        ResultaatBlad r = new ResultaatBlad(15);

        knoop.setJuistKnoop(r);

        DeterminatieTabel t = new DeterminatieTabel();
        t.setBeginKnoop(knoop);

        DeterminatieKnoopDto dto = r.maakDtoAan();
        dto.toBeslissingsKnoop();

        t.wijzigKnoop(dto);

        assertNotSame(r, knoop.getJuistKnoop());

        // Indien ik kan casten zonder exception test gelukt.
        BeslissingsKnoop k = (BeslissingsKnoop) knoop.getJuistKnoop();

    }

    @Test
    public void wijzigBeslissingsKnoopWerkt() {
        fail("Test niet af.");
    }

    @Test
    public void validerenVanFoutieveGegevensGooitException() {
        fail("Test niet af.");
    }

    /**
     * Kijkt of het valideren werkt, valideren op null waarden. Geen exceptions
     * == Test geslaagd.
     *
     * @author Jasper De Vrient
     */
    @Test
    public void validerenVanJuisteGegevensGooitGeenException() {
        final ConstanteParameter LINKS = ParameterFactory.maakConstanteParameter(5);
        final ConstanteParameter RECHTS = ParameterFactory.maakConstanteParameter(6);
        final VergelijkingsOperator OPERATOR = VergelijkingsOperator.KLEINERDAN;
        BeslissingsKnoop knoop = new BeslissingsKnoop(15);
        Vergelijking v = new Vergelijking();
        v.setLinkerParameter(LINKS);
        v.setOperator(OPERATOR);
        v.setRechterParameter(RECHTS);
        knoop.setVergelijking(v);
        ResultaatBlad links = new ResultaatBlad(16);
        ResultaatBlad rechts = new ResultaatBlad(17);
        knoop.setFoutKnoop(rechts);
        knoop.setJuistKnoop(links);
        links.setKlimaatType("Linker klimaat");
        rechts.setKlimaatType("Rechter klimaat");
        VegetatieType veg = new VegetatieType();
        veg.setFoto("veg");
        veg.setNaam("veg");
        links.setVegetatieType(veg);
        rechts.setVegetatieType(veg);

        DeterminatieTabel t = new DeterminatieTabel();
        t.setBeginKnoop(knoop);

        t.valideer();
    }

}
