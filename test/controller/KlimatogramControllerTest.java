/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.*;
import dto.*;
import java.util.*;
import mock.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mockito;
import persistentie.*;

/**
 *
 * @author Jasper De Vrient
 */
public class KlimatogramControllerTest {

    private ContinentDoaMockFactory factory;
    private GenericDao<Continent, String> repo;
    private KlimatogramController controller;

    public KlimatogramControllerTest() {
    }

    @Before
    public void before() {
        factory = new ContinentDoaMockFactory();
        repo = factory.createMock();
        controller = new KlimatogramController();
        controller.setContinentRepository(repo);
    }

    @Test
    public void geefContinentenGeeftLijstVanContinentenTerug() {
        List<ContinentDto> continenten = controller.getContinenten();
        List<Continent> control = factory.getContinenten();

        int index = 0;
        for (ContinentDto continent : continenten) {
            assertEquals(control.get(index++).getNaam(), continent.getNaam());
        }

        Mockito.verify(repo).getAll();

    }

    @Test
    public void voegContinentToeVoegtContinentToe() {
        Continent me = new Continent("Middle Earth");
        ContinentDto medto = new ContinentDto();
        medto.setNaam(me.getNaam());
        controller.voegContinentToe(medto);

        Mockito.verify(repo).insert(me);
    }

    @Test
    public void naSelecterenVanContinentWordEenLijstVanLandenTerugGegeven() {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Land[] control = factory.getContinenten().get(0).getLanden().toArray(new Land[]{});
        List<LandDto> landen = controller.getLanden();

        int index = 0;
        for (LandDto land : landen) {
            assertEquals(control[index++].getNaam(), land.getNaam());
        }

        Mockito.verify(repo).get("Europa");
    }

    @Test
    public void voegLandToeVoegtLandToe() {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Collection<Land> control = factory.getContinenten().get(0).getLanden();

        LandDto land = new LandDto();
        land.setNaam("Frankrijk");

        controller.voegLandToe(land);

        Collection<Land> test = factory.getContinenten().get(0).getLanden();

        assertEquals(control.size() + 1, test.size());

        Mockito.verify(repo).get("Europa");
    }

    /*
   
     public void naSelecterenVanLandWordEenLijstVanKlimatogrammenTerugGegeven() {
     List<ContinentDto> continenten = controller.getContinenten();
     controller.selecteerContinent(continenten.get(0));
     controller.selecteerLand(controller.getLanden().get(0));
        
     Collection<Klimatogram> control = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen();
     List<KlimatogramDto> landen = controller.get();
        
     // Test nog niet nodig UC 2.
     }
     */
    @Test
    public void voegKLimatogramToeVoegKlimatogramToe() {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Collection<Klimatogram> control = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen();
        controller.selecteerLand(controller.getLanden().get(0));

        KlimatogramDto klim = new KlimatogramDto();
        klim.setBeginJaar(1999);
        klim.setEindJaar(2001);
        klim.setLatitude(2.222);
        klim.setLongitude(4.0444);
        klim.setStation("111222");
        klim.maanden = new ArrayList<MaandDto>();
        for (int i = 0; i < 12; ++i) {
            MaandDto dto = new MaandDto();
            dto.setNaam("jan");
            dto.setNeerslag(i);
            dto.setTemperatuur(i * 1.25);
            klim.maanden.add(dto);
        }

        controller.voegKlimatogramToe(klim);

        Collection<Klimatogram> test = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen();
        List<MaandDto> maanden = controller.getMaanden();

        assertEquals(control.size() + 1, test.size());

        assertEquals(12, maanden.size());

        Mockito.verify(repo).get("Europa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanLandenNietOpvragenZonderContinentGeselecteerd() {
        controller.selecteerLand(new LandDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanNietBestaandContinentNietSelecteren() {
        controller.selecteerContinent(new ContinentDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanNullContinentNietSelecteren() {
        controller.selecteerContinent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanGeefLandenNietOpvragenVoorSelecteerContinent() {
        controller.getLanden();
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanGeefMaandenNietOpvragenVoorVoegKlimatogramToe() {
        controller.getMaanden();
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanNietBestaandLandNietSelecteren() {
        ContinentDto c = new ContinentDto();
        c.setNaam("Europa");
        controller.selecteerContinent(c);
        controller.selecteerLand(new LandDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanNullLandNietSelecteren() {
        ContinentDto c = new ContinentDto();
        c.setNaam("Europa");
        controller.selecteerContinent(c);
        controller.selecteerLand(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegContinentToeMagNietNullZijn() {
        controller.voegContinentToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegLandToeMagNietNullZijn() {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Collection<Land> control = factory.getContinenten().get(0).getLanden();

        controller.voegLandToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegKlimatogramToeMagNietNullZijn() {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Collection<Klimatogram> control = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen();
        controller.selecteerLand(controller.getLanden().get(0));

        controller.voegKlimatogramToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegKlimatogramToeMagNietNullMaandenBevatten() {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Collection<Klimatogram> control = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen();
        controller.selecteerLand(controller.getLanden().get(0));

        KlimatogramDto klim = new KlimatogramDto();
        klim.setBeginJaar(1999);
        klim.setEindJaar(2001);
        klim.setLatitude(2.222);
        klim.setLongitude(4.0444);
        klim.setStation("111222");

        controller.voegKlimatogramToe(klim);

    }

    @Test(expected = IllegalArgumentException.class)
    public void voegKlimatogramToeMagNiet0MaandenBevatten() {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Collection<Klimatogram> control = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen();
        controller.selecteerLand(controller.getLanden().get(0));

        KlimatogramDto klim = new KlimatogramDto();
        klim.setBeginJaar(1999);
        klim.setEindJaar(2001);
        klim.setLatitude(2.222);
        klim.setLongitude(4.0444);
        klim.setStation("111222");
        klim.maanden = new ArrayList<MaandDto>();

        controller.voegKlimatogramToe(klim);

    }

}
