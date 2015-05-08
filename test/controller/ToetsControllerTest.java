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
import domein.Maand;
import domein.Toets;
import mock.ContinentDaoMockFactory;
import mock.GenericDaoMockGeneric;
import mock.ToetsRepositoryMock;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jasper De Vrient
 */
public class ToetsControllerTest {

    private ToetsController controller;
    private ToetsRepositoryMock trm;
    private GenericDaoMockGeneric<Klas, Integer> klassen = new GenericDaoMockGeneric<>();
    private GenericDaoMockGeneric<Graad, Integer> graden = new GenericDaoMockGeneric<>();
    private GenericDaoMockGeneric<Klimatogram, String> klimatogrammen = new GenericDaoMockGeneric<>();

    public ToetsControllerTest() {
    }

    @Before
    public void prepare() {
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
        Toets t = trm.getAll().get(0);
        t.setGraad(g);
        t.voegKlasToe(k);

        Continent c = new Continent("Europa");
        Land l = new Land("België");
        Klimatogram kk = new Klimatogram("Ukkel");
        for (Maand m : kk.getMaanden()) {
            m.setNeerslag(15);
            m.setTemperatuur(-5);
        }
        kk.setBeginJaar(1995);
        kk.setEindJaar(2010);
        kk.setLatitude(-4);
        kk.setLongitude(4);
        kk.setStation("11995");

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
    }

    @Test
    public void geefAlleKlassenGeeftAlleKlassen() {
    }

    @Test
    public void geefToetsenGeeftToetsen() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void geefVragenGooitExecptieAlsToetsNogNietWerdGeselecteed() {

    }

    @Test
    public void geefVragenWerktAlsToetsGeselecteerdWerd() {
    }

    @Test
    public void geefAlleKlassenWerktGeeftLetterlijkAlleKlassen() {
    }

    @Test
    public void selecteerToetsWerkt() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void selecteerToetsNullWaardeGeeftFout() {
    }

    @Test
    public void aanpassenToevoegingLisjtKlassenVanToetsWerkt() {
    }

    @Test
    public void aanpassenVerwijderenLijstKlassenVantoetsWerkt() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void aanpassenLijstKlassenVanToetsWerktNietIndienGeenToetsGeselecteerd() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void maakNieuwToetsGooitExceptieBijNull() {
    }

    @Test()
    public void maakNieuweToetsCreeertNieuweToets() {
    }

}
