package controller;

import domein.*;
import dto.*;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import persistentie.GenericDaoJpa;

public class DeterminatieControllerTest {

    private DeterminatieController controller;
    

    @Before
    public void bereidVoor() {
      controller = new DeterminatieController();
    
    }
    
    @Test
    public void getGradenWerkt() {
        fail("Test niet af.");
    }

    @Test
    public void selecteerGraadWerkt() {
        fail("Test niet af.");
    }

    @Test
    public void geefDeterminatieTabellenWerkt() {
        String naam = "TestNaam";
        DeterminatieTabel tabel = new DeterminatieTabel();
        tabel.setNaam(naam);
        GenericDaoJpa<DeterminatieTabel, Integer> jpa = new GenericDaoJpa(DeterminatieTabel.class);
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
        fail("Test niet af.");
    }

    @Test
    public void selecteerDeterminatieTabelSelecteertBestaandeDeterminatieTabel() {
        fail("Test niet af.");
    }

    @Test
    public void wijzigDeterminatieTabelSlaatDeDeterminatieTabelEffectiefOp() {
        fail("Test niet af.");
    }

    @Test
    public void koppelenVanNietBestaandeDeterminatieTabelGooitExecption() {
        fail("Test niet af.");
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
