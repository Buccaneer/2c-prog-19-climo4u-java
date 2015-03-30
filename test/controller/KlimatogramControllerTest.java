package controller;

import domein.*;
import dto.*;
import java.util.*;
import mock.ContinentDaoMockFactory;
import mock.DomeinMockFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mockito;
import persistentie.*;

public class KlimatogramControllerTest
{

    private final static DomeinMockFactory dmf = new DomeinMockFactory();
    private ContinentDaoMockFactory factory;
    private GenericDao<Continent, String> repo;
    private KlimatogramController controller;

    public KlimatogramControllerTest()
    {
    }

    @Before
    public void before()
    {
        factory = new ContinentDaoMockFactory();
        repo = factory.createMock();
        controller = new KlimatogramController();
        controller.setContinentRepository(repo);
    }

    /**
     * TESTEN UC1 
     * 
     */
    @Test
    public void geefContinentenGeeftAlfabetischeLijstVanContinentenTerug() //UC1, NV1
    {
        List<ContinentDto> continenten = controller.getContinenten();
        List<Continent> control = factory.getContinenten();

        int index = 0;
        for (ContinentDto continent : continenten)
        {
            assertEquals(control.get(index++).getNaam(), continent.getNaam());
        }

        Mockito.verify(repo).getAll();

    }

    @Test
    public void naSelecterenVanContinentWordEenAlfabetischeLijstVanLandenTerugGegeven() //UC1, NV3
    {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Land[] control = factory.getContinenten().get(0).getLanden().toArray(new Land[]
        {
        });
        List<LandDto> landen = controller.getLanden();

        int index = 0;
        for (LandDto land : landen)
        {
            assertEquals(control[index++].getNaam(), land.getNaam());
        }

        Mockito.verify(repo).get("Europa");
    }

    @Test
    public void voegLandToeVoegtLandToe() //UC1, AV4A
    {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        int control = factory.getContinenten().get(0).getLanden().size();

        LandDto land = new LandDto();
        land.setNaam("Frankrijk");

        controller.voegLandToe(land);

        Collection<Land> test = factory.getContinenten().get(0).getLanden();

        assertEquals(control + 1, test.size());

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
    public void voegKlimatogramToeVoegtKlimatogramToe() //UC1, NV8
    {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        int control = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen().size();
        controller.selecteerLand(controller.getLanden().get(0));

        KlimatogramDto klim = new KlimatogramDto();
        klim.setBeginJaar(1999);
        klim.setEindJaar(2001);
        klim.setLatitude(2.222);
        klim.setLongitude(4.0444);
        klim.setLocatie("Frankrijk");
        klim.setStation("111222");
        klim.maanden = new ArrayList<MaandDto>();
        for (int i = 0; i < 12; ++i)
        {
            MaandDto dto = new MaandDto();
            dto.setNaam("jan");
            dto.setNeerslag(i);
            dto.setTemperatuur(i * 1.25);
            klim.maanden.add(dto);
        }

        controller.voegKlimatogramToe(klim);

        Collection<Klimatogram> test = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen();
        List<MaandDto> maanden = controller.getMaanden();

        assertEquals(control + 1, test.size());

        assertEquals(12, maanden.size());

        Mockito.verify(repo).get("Europa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanLandenNietOpvragenZonderContinentGeselecteerd()
    {
        controller.selecteerLand(new LandDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanNietBestaandContinentNietSelecteren()
    {
        controller.selecteerContinent(new ContinentDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanNullContinentNietSelecteren()
    {
        controller.selecteerContinent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanGeefLandenNietOpvragenVoorSelecteerContinent()
    {
        controller.getLanden();
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanGeefMaandenNietOpvragenVoorVoegKlimatogramToe()
    {
        controller.getMaanden();
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanNietBestaandLandNietSelecteren()
    {
        ContinentDto c = new ContinentDto();
        c.setNaam("Europa");
        controller.selecteerContinent(c);
        controller.selecteerLand(new LandDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void kanNullLandNietSelecteren()
    {
        ContinentDto c = new ContinentDto();
        c.setNaam("Europa");
        controller.selecteerContinent(c);
        controller.selecteerLand(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegContinentToeMagNietNullZijn()
    {
        controller.voegContinentToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegLandToeMagNietNullZijn()
    {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Collection<Land> control = factory.getContinenten().get(0).getLanden();

        controller.voegLandToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegKlimatogramToeMagNietNullZijn()
    {
        List<ContinentDto> continenten = controller.getContinenten();
        controller.selecteerContinent(continenten.get(0));
        Collection<Klimatogram> control = factory.getContinenten().get(0).getLanden().stream().findFirst().get().getKlimatogrammen();
        controller.selecteerLand(controller.getLanden().get(0));

        controller.voegKlimatogramToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegKlimatogramToeMagNietNullMaandenBevatten()
    {
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
    public void voegKlimatogramToeMagNiet0MaandenBevatten()
    {
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
    
    /**
     * TESTEN UC2
     * 
     */
    @Test
    public void naSelecterenVanLandWordEenAlfabetischeLijstVanLocatiesTerugGegeven() //UC2, NV5
    {
        Collection<Klimatogram> klimatogrammen = new ArrayList();
        String[] locaties = new String[]{"ABC", "DEF", "GHI", "JKL" , "MNO"};
        klimatogrammen.add(dmf.maakKlimatogramMock(locaties[4]));
        klimatogrammen.add(dmf.maakKlimatogramMock(locaties[1]));
        klimatogrammen.add(dmf.maakKlimatogramMock(locaties[3]));
        klimatogrammen.add(dmf.maakKlimatogramMock(locaties[2]));
        klimatogrammen.add(dmf.maakKlimatogramMock(locaties[0]));
        controller.geselecteerdLand = dmf.maakLandMock("TestLand", klimatogrammen);
        for (int i = 0; i < 5; i++)
        {
            assertEquals(locaties[i], controller.getKlimatogrammen().get(i));
        }
    }
    
    @Test
    public void wijzigKlimatogramWijzigtKlimatogram() //UC2, NV10
    {
        KlimatogramDto kDto = new KlimatogramDto();
        kDto.setBeginJaar(1800);
        kDto.setEindJaar(2000);
        kDto.setLatitude(0);
        kDto.setLongitude(0);
        kDto.setLocatie("LegoLand");
        kDto.setStation("01234");
        Klimatogram k = new Klimatogram();
        k.setBeginJaar(1900);
        k.setEindJaar(2010);
        k.setLatitude(10);
        k.setLongitude(10);
        k.setLocatie("LegoWereld");
        k.setStation("43210");
        controller.geselecteerdKlimatogram = k;
        controller.wijzigKlimatogram(kDto);
        assertEquals(1800, controller.geselecteerdKlimatogram.getBeginJaar());
        assertEquals(2000, controller.geselecteerdKlimatogram.getEindJaar());
        assertEquals(0.0, controller.geselecteerdKlimatogram.getLatitude(), 0.01);
        assertEquals(0.0, controller.geselecteerdKlimatogram.getLongitude(), 0.01);
        assertEquals("LegoLand", controller.geselecteerdKlimatogram.getLocatie());
        assertEquals("01234", controller.geselecteerdKlimatogram.getStation());
    }

}
