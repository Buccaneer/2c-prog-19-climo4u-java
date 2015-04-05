package controller;

import domein.*;
import dto.*;
import javafx.collections.ObservableList;
import persistentie.*;

public class DeterminatieController
{

    private DeterminatieTabel geselecteerdeDeterminatieTabel;
    private GenericDao<DeterminatieTabel, Integer> determinatieTabelDao;
    private GenericDao<DeterminatieKnoop, Integer> determinatieKnoopDao;

    public ObservableList<GraadDto> getGraden()
    {
        // TODO - implement DeterminatieController.getGraden
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param graad
     */
    public void selecteerGraad(GraadDto graad)
    {
        // TODO - implement DeterminatieController.selecteerGraad
        throw new UnsupportedOperationException();
    }

    public ObservableList<DeterminatieTabelDto> getDeterminatieTabellen()
    {
        // TODO - implement DeterminatieController.getDeterminatieTabellen
        throw new UnsupportedOperationException();
    }

    public void maakNieuweDeterminatieTabel()
    {
        // TODO - implement DeterminatieController.maakNieuweDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param tabel
     */
    public void verwijderDeterminatieTabel(DeterminatieTabelDto tabel)
    {
        // TODO - implement DeterminatieController.verwijderDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param tabel
     */
    public void selecteerDeterminatieTabel(DeterminatieTabelDto tabel)
    {
        // TODO - implement DeterminatieController.selecteerDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param observer
     */
    public void addObserver(Observer observer)
    {
        // TODO - implement DeterminatieController.addObserver
        throw new UnsupportedOperationException();
    }

    private void notifyObservers()
    {
        // TODO - implement DeterminatieController.notifyObservers
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param tabel
     */
    public void wijzigDeterminatieTabel(DeterminatieTabelDto tabel)
    {
        // TODO - implement DeterminatieController.wijzigDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param graad
     * @param tabel
     */
    public void koppelGraadMetDeterminatieTabel(GraadDto graad, DeterminatieTabelDto tabel)
    {
        // TODO - implement DeterminatieController.koppelGraadMetDeterminatieTabel
        throw new UnsupportedOperationException();
    }

    /**
     * Wijzigt de meegegeven knoop in een beslissingsknoop en voegt twee 
     * nieuwe resultaatknopen als kinderen toe.
     * 
     * @param ouder
     */
    public void voegKnopenToe(DeterminatieKnoopDto ouder)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Wijzigt de meegegeven knoop.
     * 
     * @param knoop
     */
    public void wijzigKnoop(DeterminatieKnoopDto knoop)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Probeert de meegegeven knoop te verwijderen. Heeft 3 mogelijke gevallen.
     * <p>
     * 1. Indien het een blad is worden de knoop en zijn broer verwijderd en
     * wordt de ouder een blad.
     * <p>
     * 2. Indien het een tussenknoop is worden de kinderen verwijderd en wordt
     * de tussenknoop een blad.
     * <p>
     * 3. Indien het de beginknoop is wordt de tabel verwijderd en wordt opnieuw
     * begonnen op dezelfde manier als bij een nieuwe tabel.
     *
     * @param knoop
     */
    public void verwijderKnoop(DeterminatieKnoopDto knoop)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Valideert of de determinatietabel in orde is.
     * Indien dit niet het geval is wordt er een exception gegooid.
     */
    public void valideer()
    {
        throw new UnsupportedOperationException();
    }

}
