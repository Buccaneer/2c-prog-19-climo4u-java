package controller;

import domein.*;
import dto.*;
import javafx.collections.ObservableList;
import persistentie.*;

public class DeterminatieController implements Subject {

    private DeterminatieTabel geselecteerdeDeterminatieTabel;
    private GenericDao<DeterminatieTabel, Integer> determinatieTabelDao;
    private GenericDao<DeterminatieKnoop, Integer> determinatieKnoopDao;

    public ObservableList<GraadDto> getGraden() {
        // TODO - implement DeterminatieController.getGraden
        throw new UnsupportedOperationException();
    }

//    /**
//     *
//     * @param graad
//     */
//    public void selecteerGraad(GraadDto graad) {
//        // TODO - implement DeterminatieController.selecteerGraad
//        throw new UnsupportedOperationException();
//    }

    public ObservableList<DeterminatieTabelDto> getDeterminatieTabellen() {
        // TODO - implement DeterminatieController.getDeterminatieTabellen
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Stelt het <strong>Begingeval</strong> in: <br />
     * Beslissingsknoop met twee resultaatbladeren.
     * </p>
     */
    public void maakNieuweDeterminatieTabel() {
        // TODO - implement DeterminatieController.maakNieuweDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param tabel
     */
    public void verwijderDeterminatieTabel(DeterminatieTabelDto tabel) {
        // TODO - implement DeterminatieController.verwijderDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param tabel
     */
    public void selecteerDeterminatieTabel(DeterminatieTabelDto tabel) {
        // TODO - implement DeterminatieController.selecteerDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     * Voert de wijzigingen die gebeurd zijn aan determinatietabel door naar db.
     *
     * @param tabel
     */
    public void wijzigDeterminatieTabel(DeterminatieTabelDto tabel) {
        // TODO - implement DeterminatieController.wijzigDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param graad
     * @param tabel
     */
    public void koppelGraadMetDeterminatieTabel(GraadDto graad, DeterminatieTabelDto tabel) {
        // TODO - implement DeterminatieController.koppelGraadMetDeterminatieTabel
        throw new UnsupportedOperationException();
    }

//    /**
//     * Wijzigt de meegegeven knoop in een beslissingsknoop en voegt twee 
//     * nieuwe resultaatknopen als kinderen toe.
//     * 
//     * @param ouder
//     */
//    public void voegKnoopToe(DeterminatieKnoopDto ouder)
//    {
//        throw new UnsupportedOperationException();
//    }
    
    
    /**
     * Wijzigt de meegegeven knoop. Meerdere Gevallen:
     * <ol>
     * <li><p>
     * Het <var>type</var> van de knoop blijft het zelfde: Wijzig de attributen
     * van die knoop.
     * </p>
     * </li>
     * <li><p>
     * Het <var>type</var> van de knoop verandert van resultaatblad naar
     * beslissingsknoop: <ol><li>Ouder beseft dat zijn kind verwijdert moet
     * worden.</li>
     * <li>Ouder verwijdert zijn juiste kind.</li>
     * <li>Ouder maakt een nieuwe <var>BeslissingsKnoop</var> aan en voegt deze
     * toe op de juiste plaats.</li>
     * <li>Ouder roept <code>wijzigKnoop(knoop)</code> aan van dit kind. <em>Om
     * attributen in te stellen.</em></li>
     * </ol></p></li>
     * <li><p>
     * Het <var>type</var> van de knoop verandert van beslissingsknoop naar
     * resultaatblad: Enkele gevallen:<ol><li>
     * <p>
     * <strong>BeginKnoop</strong>: Reset de determinatietabel zie
     * begingeval.</p>
     * </li>
     * <li><p>
     * <strong>Tussenknoop</strong>: Verander de knoop naar een resultaatblad,
     * als gevolg verdwijnen alle kinderen.
     * </p></li>
     * </ol></p></li>
     * </ol>
     *
     * @param knoop
     */
    public void wijzigKnoop(DeterminatieKnoopDto knoop) {
       
        throw new UnsupportedOperationException();
    }

//    /**
//     * Probeert de meegegeven knoop te verwijderen. Heeft 3 mogelijke gevallen.
//     * <p>
//     * 1. Indien het een blad is worden de knoop en zijn broer (of zus?) verwijderd en
//     * wordt de ouder een blad.</p>
//     * <p>
//     * 2. Indien het een tussenknoop is worden de kinderen verwijderd en wordt
//     * de tussenknoop een blad.</p>
//     * <p>
//     * 3. Indien het de beginknoop is wordt de tabel verwijderd en wordt opnieuw
//     * begonnen op dezelfde manier als bij een nieuwe tabel.
//     *</p>
//     * @param knoop
//     */
//    public void verwijderKnoop(DeterminatieKnoopDto knoop)
//    {
//       
//        throw new UnsupportedOperationException();
//    }
    
    /**
     * Valideert of de determinatietabel in orde is. Indien dit niet het geval
     * is wordt er een exception gegooid.
     */
    public void valideer() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeObserver(Observer observer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObservers(String actie, Object object) {
       // 
    }

    @Override
    public void addObserver(Observer observer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
