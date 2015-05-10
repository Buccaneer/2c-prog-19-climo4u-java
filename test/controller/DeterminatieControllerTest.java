package controller;

import domein.*;
import dto.*;
import java.util.Iterator;
import mock.GenericDaoJpaMock;
import mock.GenericDaoMockGeneric;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import persistentie.GenericDao;

public class DeterminatieControllerTest {

    private DeterminatieController controller;

    @Before
    public void bereidVoor() {
        controller = new DeterminatieController();

    }

    @Test
    public void getGradenWerkt() {

        GenericDaoMockGeneric<Graad, String> graadjpa = new GenericDaoMockGeneric<>();
        Graad g = new Graad(1, 1);
        g.setActieveTabel(new DeterminatieTabel());
        graadjpa.insert("", g);

        controller.setGraadRepository(graadjpa);

        GraadDto[] graden = controller.getGraden().toArray(new GraadDto[]{});

        assertEquals(1, graden.length);
        assertTrue(g.getJaar() == graden[0].getJaar());
        assertEquals(g.getGraad(), graden[0].getGraad());
    }

    @Test
    public void geefDeterminatieTabellenWerkt() {
        String naam = "TestNaam";
        DeterminatieTabel tabel = new DeterminatieTabel();
        tabel.setNaam(naam);
        GenericDao<DeterminatieTabel, Integer> jpa = new GenericDaoJpaMock();
        jpa.insert(tabel);
        controller.setDeterminatieTabelRepository(jpa);
        Iterator<DeterminatieTabelDto> i = controller.getDeterminatieTabellen().iterator();
        assertTrue(i.hasNext());
        assertTrue(i.next().getNaam().equals(naam));
    }

    @Test
    public void maakNieuweDeterminatieTabelWerkt() {
        controller.maakNieuweDeterminatieTabel();
        DeterminatieTabel tabel = controller.getGeselecteerdeDeterminatieTabel();
        assertNotNull(tabel);
        DeterminatieKnoop beginKnoop = tabel.getBeginKnoop();
        assertNotNull(beginKnoop);
        DeterminatieKnoop juistBlad = ((BeslissingsKnoop) beginKnoop).getJuistKnoop();
        assertNotNull(juistBlad);
        assertTrue(juistBlad instanceof ResultaatBlad);
        DeterminatieKnoop foutBlad = ((BeslissingsKnoop) beginKnoop).getFoutKnoop();
        assertNotNull(foutBlad);
        assertTrue(foutBlad instanceof ResultaatBlad);
    }

    @Test
    public void verwijderDeterminatieTabelVerwijdertBestaandeDeterminatieTabel() {
        GenericDaoMockGeneric<DeterminatieTabel, Integer> jpa = new GenericDaoMockGeneric();

        GenericDaoMockGeneric<Graad, String> graadjpa = new GenericDaoMockGeneric<>();
        graadjpa.insert("", new Graad());

        DeterminatieTabel dt = new DeterminatieTabel();

        dt.setId(12);
        DeterminatieTabelDto dto = new DeterminatieTabelDto(12, "");
        jpa.insert(12, dt);

        controller.setDeterminatieKnoopRepository(new GenericDaoMockGeneric<>());

        controller.setDeterminatieTabelRepository(jpa);
        controller.setGraadRepository(graadjpa);

        controller.verwijderDeterminatieTabel(dto);

        assertTrue(jpa.exists(12));

    }

    @Test(expected = IllegalArgumentException.class)
    public void nietbestaandeDeterminatietabelVerwijderenWerktNiet() {
        GenericDaoMockGeneric<DeterminatieTabel, Integer> jpa = new GenericDaoMockGeneric();

        GenericDaoMockGeneric<Graad, String> graadjpa = new GenericDaoMockGeneric<>();
        graadjpa.insert("", new Graad());

        DeterminatieTabel dt = new DeterminatieTabel();

        dt.setId(12);
        DeterminatieTabelDto dto = new DeterminatieTabelDto(13, "");
        jpa.insert(12, dt);

        controller.setDeterminatieKnoopRepository(new GenericDaoMockGeneric<>());

        controller.setDeterminatieTabelRepository(jpa);
        controller.setGraadRepository(graadjpa);

        controller.verwijderDeterminatieTabel(dto);

        assertTrue(jpa.exists(12));

    }

    @Test(expected = IllegalArgumentException.class)
    public void gekoppeldeDeterminatieTabelVerwijderenWerktNiet() {
        GenericDaoMockGeneric<DeterminatieTabel, Integer> jpa = new GenericDaoMockGeneric();

        GenericDaoMockGeneric<Graad, String> graadjpa = new GenericDaoMockGeneric<>();
        Graad x = new Graad();
        graadjpa.insert("", x);

        DeterminatieTabel dt = new DeterminatieTabel();

        dt.setId(12);
        DeterminatieTabelDto dto = new DeterminatieTabelDto(12, "");
        jpa.insert(12, dt);
        x.setActieveTabel(dt);

        controller.setDeterminatieKnoopRepository(new GenericDaoMockGeneric<>());

        controller.setDeterminatieTabelRepository(jpa);
        controller.setGraadRepository(graadjpa);

        controller.verwijderDeterminatieTabel(dto);

        assertTrue(jpa.exists(12));

    }

    @Test
    public void selecteerDeterminatieTabelSelecteertBestaandeDeterminatieTabel() {
        GenericDaoMockGeneric<DeterminatieTabel, Integer> jpa = new GenericDaoMockGeneric();

        GenericDaoMockGeneric<Graad, String> graadjpa = new GenericDaoMockGeneric<>();
        graadjpa.insert("", new Graad());

        DeterminatieTabel dt = new DeterminatieTabel();
        dt.setBeginKnoop(new ResultaatBlad());
        dt.setId(12);
        DeterminatieTabelDto dto = new DeterminatieTabelDto(12, "");
        jpa.insert(12, dt);

        controller.setDeterminatieKnoopRepository(new GenericDaoMockGeneric<>());

        controller.setDeterminatieTabelRepository(jpa);
        controller.setGraadRepository(graadjpa);

        controller.selecteerDeterminatieTabel(dto);

        assertNotNull(controller.getGeselecteerdeDeterminatieTabel());
        assertEquals(12, controller.getGeselecteerdeDeterminatieTabel().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void selecteerDeterminatieTabelSelecteertNietBestaandeDeterminatieTabel() {
        GenericDaoMockGeneric<DeterminatieTabel, Integer> jpa = new GenericDaoMockGeneric();

        GenericDaoMockGeneric<Graad, String> graadjpa = new GenericDaoMockGeneric<>();
        graadjpa.insert("", new Graad());

        DeterminatieTabel dt = new DeterminatieTabel();

        dt.setId(12);
        DeterminatieTabelDto dto = new DeterminatieTabelDto(13, "");
        jpa.insert(12, dt);

        controller.setDeterminatieKnoopRepository(new GenericDaoMockGeneric<>());

        controller.setDeterminatieTabelRepository(jpa);
        controller.setGraadRepository(graadjpa);

        controller.selecteerDeterminatieTabel(dto);

    }

    @Test(expected = IllegalArgumentException.class)
    public void koppelenVanNietBestaandeGraadGooitExecption() {
        controller.koppelGraadMetDeterminatieTabel(new GraadDto(), new DeterminatieTabelDto());
    }

    @Test(expected = IllegalArgumentException.class)
    public void wijzigKnoopAanroepenAlsErGeenDeterminatieTabelIsGeselecteerdGooitExecption() {
        controller.wijzigKnoop(new DeterminatieKnoopDto());
    }

}
