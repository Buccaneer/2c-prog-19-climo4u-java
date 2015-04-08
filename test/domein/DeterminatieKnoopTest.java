/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import dto.*;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jasper De Vrient
 */
public class DeterminatieKnoopTest {

    /**
     * Test of de constructor van beslissingsknoop wel een resultaatblad
     * aanmaakt voor zowel juist als fout knoop.
     *
     * @author Jasper De Vrient
     */
    @Test
    public void constructorBeslissingsKnoopWerkt() {
        BeslissingsKnoop knoop = new BeslissingsKnoop();

        assertNotNull(knoop.getJuistKnoop());
        assertNotNull(knoop.getFoutKnoop());

        assertTrue("Beslissingsknoop dient een resultaatblad in te stellen voor juist bij constructie.", knoop.getJuistKnoop().getClass().equals(ResultaatBlad.class));
        assertTrue("Beslissingsknoop dient een resultaatblad in te stellen voor fout bij constructie.", knoop.getFoutKnoop().getClass().equals(ResultaatBlad.class));
    }

    @Test
    public void omzettenBeslissingsKnoopInResultaatBladWerkt() {
        fail("Test niet af.");
    }

    /**
     * Kijkt wanneer een boom moet worden omgezet of dat werkt wanneer hij geen
     * ouder heeft.
     *
     * @author Jasper De Vrient
     */
    @Test
    public void omzettenBeginKnoopResetDeBoom() {
        BeslissingsKnoop k = new BeslissingsKnoop(2);
        BeslissingsKnoop k1 = new BeslissingsKnoop();
        k.setJuistKnoop(k1);
        
        DeterminatieKnoopDto dto = k1.maakDtoAan();
        
        dto.toResultaatBlad();
        
        k.wijzigKnoop(dto);
        
        assertNotSame(k1, k.getJuistKnoop());
    }

    /**
     * Kijkt of het wijzigen van de attributen werkt. (via Dto).
     *
     * @author Jasper De Vrient
     */
    @Test
    public void wijzigResultaatBladWerkt() {
        final int ID = 5;
        final String NIEUWKLIMAATTYPE = "klimaattype";
        final String NIEUWFOTO = "veg.png";
        final String NIEUWVEGTYPE = "veg";

        ResultaatBlad blad = new ResultaatBlad(ID);
        VegetatieType veg = new VegetatieType();
        veg.setFoto("hallo.jpg");
        veg.setNaam("hallo");
        blad.setKlimaatType("mooi");
        blad.setVegetatieType(veg);

        DeterminatieKnoopDto wijzigingen = new DeterminatieKnoopDto();
        wijzigingen.setId(ID);
        wijzigingen.setKlimaattype(NIEUWKLIMAATTYPE);
        VegetatieTypeDto dto = new VegetatieTypeDto();
        dto.setFoto(NIEUWFOTO);
        dto.setNaam(NIEUWVEGTYPE);
        wijzigingen.setVegetatieType(dto);

        blad.wijzigKnoop(wijzigingen);

        assertEquals(NIEUWKLIMAATTYPE, blad.getKlimaatType());
        assertEquals(NIEUWFOTO, veg.getFoto());
        assertEquals(NIEUWVEGTYPE, veg.getNaam());
    }

    /**
     * Kijk op het wijzigen van de attributen werkt. (via Dto).
     *
     * @author Jasper De Vrient
     */
    @Test
    public void wijzigBeslissingsKnoopWerkt() {
        final int ID = 5;
        final ConstanteParameter LINKS = ParameterFactory.maakConstanteParameter(5);
        final ConstanteParameter RECHTS = ParameterFactory.maakConstanteParameter(6);
        final VergelijkingsOperator OPERATOR = VergelijkingsOperator.KLEINERDAN;

        BeslissingsKnoop knoop = new BeslissingsKnoop(ID);
        Vergelijking v = new Vergelijking();
        v.setLinkerParameter(ParameterFactory.maakConstanteParameter(-3));
        v.setOperator(VergelijkingsOperator.GROTERDANGELIJKAAN);
        v.setRechterParameter(ParameterFactory.maakConstanteParameter(9999));
        knoop.setVergelijking(v);

        DeterminatieKnoopDto wijzigingen = new DeterminatieKnoopDto();
        wijzigingen.setId(ID);
        VergelijkingDto vd = new VergelijkingDto();
        vd.setLinks(new ParameterDto(LINKS.getNaam(), LINKS.getWaarde()));
        vd.setRechts(new ParameterDto(RECHTS.getNaam(), RECHTS.getWaarde()));
        vd.setOperator(OPERATOR);
        wijzigingen.setVergelijking(vd);

        knoop.wijzigKnoop(wijzigingen);

        ConstanteParameter clp = (ConstanteParameter) v.getLinkerParameter();
        ConstanteParameter crp = (ConstanteParameter) v.getRechterParameter();

        assertEquals(LINKS.getWaarde(), clp.getWaarde(), 2);
        assertSame(OPERATOR, v.getOperator());
        assertEquals(RECHTS.getWaarde(), crp.getWaarde(), 2);
    }

    @Test
    public void omzettenResultaatBladInBeslissingsKnoopWerkt() {
        fail("Test niet af.");
    }

    @Test
    public void vergelijkingVanBeslissingsKnoopNullGooitException() {
        fail("Test niet af.");
    }

    @Test
    public void vegetatieTypeVanResultaatBladKanNietNullZijnException() {
        fail("Test niet af.");
    }

    @Test
    public void validerenVanJuisteGegevensGooitGeenException() {
        fail("Test niet af.");
    }

    /**
     * Controlleerd of de maakDto() methode een geldige boom terug geeft.
     *
     * @author Jasper De Vrient
     */
    @Test
    public void maakDtoAanVoorBeslissingsKnoopIsGeldig() {
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

        DeterminatieKnoopDto dto = knoop.maakDtoAan();

        assertEquals(LINKS.getNaam(), dto.getVergelijking().getLinks().getNaam());
        assertEquals(LINKS.getWaarde(), dto.getVergelijking().getLinks().getWaarde(), 2);
        assertEquals(RECHTS.getNaam(), dto.getVergelijking().getRechts().getNaam());
        assertEquals(RECHTS.getWaarde(), dto.getVergelijking().getRechts().getWaarde(), 2);
        assertSame(OPERATOR, dto.getVergelijking().getOperator());
        assertNotNull("Juist boom werd niet ingesteld.", dto.getJa());
        assertNotNull("Fout boom werd niet ingesteld.", dto.getNee());
        assertSame(dto, dto.getJa().getOuder());
        assertSame(dto, dto.getNee().getOuder());
        assertTrue("Foutief dto.isBeslissingsKnoop geeft false bij beslissingsknoop roep toBeslissingsKnoop aan aub.", dto.isBeslissingsKnoop());
        assertEquals(15, dto.getId());
        assertNull("Waarde vegetatietype bestaat niet bij beslissingsknoop werd wel ingesteld.", dto.getVegetatieType());
        assertNull("Waarde klimaattype bestaat niet bij beslissingsknoop werd wel ingesteld.", dto.getKlimaattype());

    }

    @Test
    public void maakDtoAanVoorResultaatBladIsGeldig() {
        fail("Test niet af.");
    }
}
