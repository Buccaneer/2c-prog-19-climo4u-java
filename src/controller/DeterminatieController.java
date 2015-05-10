package controller;

import domein.*;
import dto.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistentie.*;

public class DeterminatieController implements Subject {

    private DeterminatieTabel geselecteerdeDeterminatieTabel;
    private ObservableList<DeterminatieTabelDto> determinatietabellen = FXCollections.observableArrayList();
    private ObservableList<GraadDto> graden = FXCollections.observableArrayList();
    private GenericDao<DeterminatieTabel, Integer> determinatieTabelRepository = new GenericDaoJpa<>(DeterminatieTabel.class);
    private GenericDao<DeterminatieKnoop, String> determinatieKnoopRepository = new GenericDaoJpa<>(DeterminatieKnoop.class);
    private GenericDao<Graad, String> graadRepository = new GenericDaoJpa<>(Graad.class);
    private List<Observer> observers = new ArrayList<>();

    public ObservableList<GraadDto> getGraden() {
        graden.clear();
        List<Graad> graad = graadRepository.getAll();
        graad.stream().forEach(g -> graden.add(new GraadDto(g.getNummer(), g.getJaar(), new DeterminatieTabelDto(g.getActieveTabel().getId(), g.getActieveTabel().getNaam()))));
        return graden;
    }

    public ObservableList<DeterminatieTabelDto> getDeterminatieTabellen() {
        determinatietabellen.clear();
        List<DeterminatieTabel> tabellen = determinatieTabelRepository.getAll();
        tabellen.forEach(tabel -> determinatietabellen.add(new DeterminatieTabelDto(tabel.getId(), tabel.getNaam())));
        return determinatietabellen;
    }

    /**
     * <p>
     * Stelt het <strong>Begingeval</strong> in: <br />
     * Beslissingsknoop met twee resultaatbladeren.
     * </p>
     */
    public void maakNieuweDeterminatieTabel() {
        DeterminatieKnoop beginKnoop = new BeslissingsKnoop();
        geselecteerdeDeterminatieTabel = new DeterminatieTabel();
        geselecteerdeDeterminatieTabel.setBeginKnoop(beginKnoop);

        determinatieTabelRepository.insert(geselecteerdeDeterminatieTabel);

        getDeterminatieTabellen();

        notifyObservers("", geselecteerdeDeterminatieTabel.maakDtoAan());
    }

    public void setNaamDeterminatieTabel(String naam) {
        GenericDaoJpa.startTransaction();

        geselecteerdeDeterminatieTabel.setNaam(naam);

        GenericDaoJpa.commitTransaction();
        notifyObservers("", geselecteerdeDeterminatieTabel.maakDtoAan());
        getDeterminatieTabellen();
    }

    /**
     *
     * @param tabel
     */
    public void verwijderDeterminatieTabel(DeterminatieTabelDto tabel) {
        if (tabel == null) {
            throw new IllegalArgumentException("U moet eerst een determinatietabel selecteren");
        }
        DeterminatieTabel t = determinatieTabelRepository.get(tabel.getId());
        try {
            List<Graad> graden = graadRepository.getAll();

            if (graden.stream().anyMatch(g -> g.getActieveTabel() == t)) {
                throw new Exception();
            }

            determinatieKnoopRepository.delete(t.getBeginKnoop());
            determinatieTabelRepository.delete(t);

            geselecteerdeDeterminatieTabel = null;
            determinatietabellen.clear();
            getDeterminatieTabellen();
            notifyObservers("verwijderen", t);

        } catch (Exception ex) {
            throw new IllegalArgumentException("Deze determinatietabel wordt nog gebruikt door een graad.");
        }
    }

    /**
     *
     * @param tabel
     */
    public void selecteerDeterminatieTabel(DeterminatieTabelDto tabel) {
        if (tabel == null) {
            throw new IllegalArgumentException();
        }

        geselecteerdeDeterminatieTabel = determinatieTabelRepository.get(tabel.getId());
        if (geselecteerdeDeterminatieTabel == null) {
            throw new IllegalArgumentException("Determinatietabel bestaat niet.");
        }
        notifyObservers("", geselecteerdeDeterminatieTabel.getBeginKnoop().maakDtoAan());

    }

    /**
     * Voert de wijzigingen die gebeurd zijn aan determinatietabel door naar db.
     *
     * @param tabel
     */
    public void wijzigDeterminatieTabel(DeterminatieTabelDto tabel) {
        if (tabel == null) {
            throw new IllegalArgumentException("U moet eerst een determinatietabel selecteren");
        }
    }

    /**
     *
     * @param graad
     * @param tabel
     */
    public void koppelGraadMetDeterminatieTabel(GraadDto graad, DeterminatieTabelDto tabel) {

        if (graad == null) {
            throw new IllegalArgumentException("U moet eerst een graad selecteren");
        }
        if (tabel == null) {
            throw new IllegalArgumentException("U moet eerst een determinatietabel selecteren");
        }
        List<Graad> gr = graadRepository.getAll();
        DeterminatieTabel tab = determinatieTabelRepository.get(tabel.getId());
        if (tab == null) {
            throw new IllegalArgumentException("De determinatietabel bestaat niet.");
        }
        tab.valideer();

        Graad g = gr.stream().filter(gg -> gg.getJaar() == graad.getJaar() && gg.getNummer() == graad.getGraad()).findFirst().get();
        if (g == null) {
            throw new IllegalArgumentException("De graad bestaat niet.");
        }
        GenericDaoJpa.startTransaction();

        g.setActieveTabel(tab);

        GenericDaoJpa.commitTransaction();

        getGraden();
    }

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
        try {
            GenericDaoJpa.startTransaction();
            geselecteerdeDeterminatieTabel.wijzigKnoop(knoop);

            GenericDaoJpa.commitTransaction();

            notifyObservers("", geselecteerdeDeterminatieTabel.maakDtoAan());
        } catch (Exception ex) {
            GenericDaoJpa.rollbackTransaction();
            throw new IllegalArgumentException("Kon wijzigingen niet opslaan.");
        }
    }

    /**
     * Valideert of de determinatietabel in orde is. Indien dit niet het geval
     * is wordt er een exception gegooid.
     */
    public void valideer() {
        try {
            geselecteerdeDeterminatieTabel.valideer();
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String actie, Object object) {
        observers.forEach(o -> o.update(actie, object));
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // --- Methodes voor de testen ---
    void setDeterminatieTabelRepository(GenericDao<DeterminatieTabel, Integer> determinatieTabelRepository) {
        this.determinatieTabelRepository = determinatieTabelRepository;
    }

    DeterminatieTabel getGeselecteerdeDeterminatieTabel() {
        return geselecteerdeDeterminatieTabel;
    }

    void setGraadRepository(GenericDao<Graad, String> graadRepository) {
        this.graadRepository = graadRepository;
    }

    void setDeterminatieKnoopRepository(GenericDao<DeterminatieKnoop, String> determinatieKnoopRepository) {
        this.determinatieKnoopRepository = determinatieKnoopRepository;
    }
    //--- Einde methodes voor de testen ---
}
