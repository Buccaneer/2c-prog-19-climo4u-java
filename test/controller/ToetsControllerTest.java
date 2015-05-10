/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.Continent;
import domein.Graad;
import domein.Klas;
import domein.Klimatogram;
import domein.Land;
import domein.LosseVraag;
import domein.Maand;
import domein.Toets;
import domein.ToetsVraag;
import dto.GraadDto;
import dto.KlasDto;
import dto.KlimatogramDto;
import dto.ToetsDto;
import dto.VraagDto;
import java.util.Arrays;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import mock.ContinentDaoMockFactory;
import mock.GenericDaoMockGeneric;
import mock.ToetsRepositoryMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import persistentie.GenericDaoJpa;

/**
 *
 * @author Jasper De Vrient
 */
public class ToetsControllerTest {

    private ToetsController controller;
    private ToetsRepositoryMock trm;
    private Toets t;
    private GenericDaoMockGeneric<Klas, Integer> klassen = new GenericDaoMockGeneric<>();
    private GenericDaoMockGeneric<Graad, Integer> graden = new GenericDaoMockGeneric<>();
    private GenericDaoMockGeneric<Klimatogram, String> klimatogrammen = new GenericDaoMockGeneric<>();
    private Klimatogram kk = new Klimatogram("Ukkel");

    public ToetsControllerTest() {
    }

    @Before
    public void prepare() {
        GenericDaoJpa.setIgnoreTransactions(true);
        graden = new GenericDaoMockGeneric<>();
        klassen = new GenericDaoMockGeneric<>();
        ContinentDaoMockFactory cdmf = new ContinentDaoMockFactory();

        Graad g = new Graad(1, 1);
        cdmf.setGraad(g);

        graden.insert(Integer.SIZE, g);
        Klas k = new Klas(1, "1Hw", 1);
        k.setGraad(g);
        klassen.insert(1, k);
        controller = new ToetsController();
        trm = ToetsRepositoryMock.creerMetEenToetsErin();
        t = trm.getAll().get(0);
        t.setGraad(g);
        t.voegKlasToe(k);
        Klas klas = new Klas(2, "2Hw", 2);
        klassen.insert(2, klas);
        LosseVraag vraag = new LosseVraag();
        vraag.setBeschrijving("Vul de vraag in");

        vraag.setTeBehalenPunten(5);
        vraag.setId(1);
        t.voegVraagToe(vraag);
        Continent c = new Continent("Europa");
        Land l = new Land("België");

        for (Maand m : kk.getMaanden()) {
            m.setNeerslag(15);
            m.setTemperatuur(-5);
        }
        kk.setBeginJaar(1995);
        kk.setEindJaar(2010);
        kk.setLatitude(-4);
        kk.setLongitude(4);
        kk.setStation("11995");
        vraag.setKlimatogram(kk);

        l.voegKlimatogramToe(kk);
        c.voegLandToe(l);

        klimatogrammen.insert("Ukkel", kk);
        controller.setToetsrepository(trm);
        controller.setKlassenRepository(klassen);
        controller.setKlimatogramRepository(klimatogrammen);
        controller.setGraadrepository(graden);
    }

    @Test
    public void geefAlleGradenGeeftAlleGraden() {
        ObservableList<GraadDto> graden = controller.geefAlleGraden();
        Assert.assertEquals(1, graden.size());
    }

    @Test
    public void geefKlassenVanToetsGeeftAlleKlassen() {
        Toets toets = trm.getAll().get(0);
        ToetsDto toetsDto = new ToetsDto();
        toetsDto.setAanvang(toets.getStartDatumUur());
        toetsDto.setEind(toets.getEindDatumUur());
        toetsDto.setBeschrijving(new SimpleStringProperty(toets.getBeschrijving()));
        toetsDto.setId(toets.getId());
        toetsDto.setTitel(new SimpleStringProperty(toets.getTitel()));
        controller.selecteerToets(toetsDto);
        ObservableList<KlasDto> klassen = controller.geefAlleKlassen();
        Assert.assertEquals(2, klassen.size());
    }

    @Test
    public void geefToetsenGeeftToetsen() {
        ObservableList<ToetsDto> toetsen = controller.geefToetsen();
        Assert.assertEquals(1, toetsen.size());
    }

    @Test
    public void geefVragenWerktAlsToetsGeselecteerdWerd() {
        Toets toets = t;
        controller.setGeselecteerdeToets(toets);
        ObservableList<VraagDto> vragen = controller.geefVragen();
        Assert.assertEquals(1, vragen.size());
    }

    @Test
    public void geefAlleKlassenWerktGeeftLetterlijkAlleKlassen() {
        ObservableList<KlasDto> klassen = controller.geefAlleKlassen();
        Assert.assertEquals(2, klassen.size());
    }

    @Test
    public void selecteerToetsWerkt() {
        Toets toets = t;
        ToetsDto toetsDto = new ToetsDto();
        toetsDto.setAanvang(toets.getStartDatumUur());
        toetsDto.setEind(toets.getEindDatumUur());
        toetsDto.setBeschrijving(new SimpleStringProperty(toets.getBeschrijving()));
        toetsDto.setId(toets.getId());
        toetsDto.setTitel(new SimpleStringProperty(toets.getTitel()));
        controller.selecteerToets(toetsDto);
        Assert.assertNotNull(controller.getGeselecteerdeToets());
    }

    @Test(expected = IllegalArgumentException.class)
    public void selecteerToetsNullWaardeGeeftFout() {
        controller.selecteerToets(null);
    }

    @Test
    public void aanpassenToevoegingLijstKlassenVanToetsWerkt() {
        Klas klas = klassen.get(2);
        KlasDto dto = new KlasDto(klas.getId(), klas.getNaam(), klas.getLeerjaar());
        Toets toets = t;
        controller.setGeselecteerdeToets(toets);
        ObservableList<KlasDto> klassen = controller.geefKlassenVanToets();
        klassen.add(dto);
        Assert.assertEquals(2, controller.getGeselecteerdeToetsVoorTesten().getKlassen().size());
    }

    @Test
    public void aanpassenVerwijderenLijstKlassenVantoetsWerkt() {
        Toets toets = t;
        controller.setGeselecteerdeToets(toets);
        ObservableList<KlasDto> klassen = controller.geefKlassenVanToets();
        klassen.remove(0);
        Assert.assertEquals(0, controller.getGeselecteerdeToetsVoorTesten().getKlassen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void aanpassenLijstKlassenVanToetsWerktNietIndienGeenToetsGeselecteerd() {
        Toets toets = t;
        ToetsDto toetsDto = new ToetsDto();
        toetsDto.setAanvang(toets.getStartDatumUur());
        toetsDto.setEind(toets.getEindDatumUur());
        toetsDto.setBeschrijving(new SimpleStringProperty(toets.getBeschrijving()));
        toetsDto.setId(toets.getId());
        toetsDto.setTitel(new SimpleStringProperty(toets.getTitel()));
        controller.wijzigToets(toetsDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maakNieuwToetsGooitExceptieBijNull() {
        controller.maakNieuweToets(null);
    }

    @Test
    public void maakNieuweToetsCreeertNieuweToets() {
        Toets toets = t;
        ToetsDto toetsDto = new ToetsDto();
        toetsDto.setAanvang(toets.getStartDatumUur());
        toetsDto.setEind(toets.getEindDatumUur());
        toetsDto.setBeschrijving(new SimpleStringProperty(toets.getBeschrijving()));
        toetsDto.setId(toets.getId());
        toetsDto.setTitel(new SimpleStringProperty(toets.getTitel()));
        Graad graad = graden.getAll().get(0);
        GraadDto graadDto = new GraadDto(graad.getGraad(), graad.getJaar(), null);
        toetsDto.setGraad(new SimpleObjectProperty<>(graadDto));

        controller.maakNieuweToets(toetsDto);
        Assert.assertEquals(2, controller.geefToetsen().size());
    }

    @Test
    public void verwijderToetsVerwijdertToets() {
        Toets toets = t;
        controller.verwijderToets(new ToetsDto(toets.getId()));
        Assert.assertEquals(0, controller.geefToetsen().size());
    }

    @Test
    public void verwijderVraagVerwijdertVraag() {
        Toets toets = t;
        ToetsVraag vraag = toets.getVragen().get(0);
        controller.setGeselecteerdeToets(toets);
        controller.verwijderVraag(new VraagDto(vraag.getId()));
        Assert.assertEquals(0, controller.geefVragen().size());
    }

    @Test
    public void voegKlasToeVoegtKlasToe() {
        Toets toets = t;
        controller.setGeselecteerdeToets(toets);
        controller.voegKlasToe(new KlasDto(1, "QSDF", 654));
        Assert.assertEquals(2, controller.geefKlassenVanToets().size());
    }

    @Test
    public void voegVraagToeVoegtVraagToe() {
        Toets toets = t;
        controller.setGeselecteerdeToets(toets);
        VraagDto vraag = new VraagDto();
        vraag.setBeschrijving("wsdf");
        vraag.setId(13);
        KlimatogramDto dto = new KlimatogramDto(kk.getLocatie());
        vraag.setKlimatogrammen(Arrays.asList(new KlimatogramDto[]{dto}));
        controller.voegVraagToe(vraag);
        Assert.assertEquals(2, controller.geefVragen().size());
    }

    @Test
    public void wijzigToetsWijzigtToets() {
        Toets toets = t;
        controller.setGeselecteerdeToets(toets);
        ToetsDto dto = new ToetsDto();
        dto.setAanvang(toets.getStartDatumUur());
        dto.setEind(toets.getEindDatumUur());
        dto.setId(toets.getId());
        String expected = t.getBeschrijving() + " Test123";
        dto.setBeschrijving(new SimpleStringProperty(t.getBeschrijving() + " Test123"));
        controller.wijzigToets(dto);
        Assert.assertEquals(expected, controller.getGeselecteerdeToets().getBeschrijving().getValue());
    }

    @Test
    public void wijzigVraagWijzigtVraag() {
        Toets toets = t;
        controller.setGeselecteerdeToets(toets);
        String expected = t.getVragen().get(0).getBeschrijving() + " Test123";
        LosseVraag vraag = (LosseVraag) t.getVragen().get(0);
        KlimatogramDto dto = new KlimatogramDto(kk.getLocatie());
        controller.wijzigVraag(new VraagDto(vraag.getId(), VraagDto.GRAADEEN, Arrays.asList(new KlimatogramDto[]{dto}), vraag.getSubvragenLijst(), expected, 5));
        Assert.assertEquals(expected, controller.geefVragen().get(0).getBeschrijving());
    }

    @Test(expected = IllegalArgumentException.class)
    public void verwijderKlasGeeftNullBijGeenToetsGeselecteerd() {
        controller.verwijderKlas(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verwijderToetsGeeftNullBijGeenGeselecteerdeToets() {
        controller.verwijderToets(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verwijderVraagGeeftExceptionBijGeenGeselecteerdeToets() {
        controller.verwijderVraag(new VraagDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegKlasToeGeeftExceptionBijGeenGeselecteerdeToets() {
        controller.voegKlasToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void voegVraagToeGeeftExceptionBijGeenGeselecteerdeToets() {
        controller.voegVraagToe(new VraagDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void wijzigToetsGeeftExceptionBijGeenGeselecteerdeToets() {
        controller.wijzigToets(new ToetsDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void wijzigVraagGeeftExceptionBijGeenGeselecteerdeToets() {
        controller.wijzigVraag(new VraagDto());
    }
}
