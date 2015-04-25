package controller;

import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken;
import dto.*;
import domein.*;
import java.net.URI;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistentie.GenericDao;
import persistentie.GenericDaoJpa;

public class LeerlingController implements Subject {

    private GenericDao<Graad, String> gradenRepository = new GenericDaoJpa<>(Graad.class);
    private GenericDao<Leerling, Integer> leerlingRepository = new GenericDaoJpa<>(Leerling.class);
    private GenericDao<Klas, Integer> klassenRepository = new GenericDaoJpa<>(Klas.class);

    private ObservableList<LeerlingDto> leerlingen = FXCollections.observableArrayList();
    private ObservableList<KlasDto> klassen = FXCollections.observableArrayList();
    private ObservableList<GraadDto> graden = FXCollections.observableArrayList();
    private ObservableList<KlasDto> alleKlassen = FXCollections.observableArrayList();
    
    private Graad geselecteerdeGraad;
    private Klas geselecteerdeKlas;
    private Leerling geselecteerdeLeerling;

    private Collection<Observer> observers = new HashSet<>();

    /**
     *
     * @param leerling
     */
    public void maakNieuweLeerling(LeerlingDto leerling) {
        if (geselecteerdeGraad != null)
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        if (geselecteerdeKlas != null)
            throw new IllegalArgumentException("U dient eerst een klas te selecteren.");

        GenericDaoJpa.startTransaction();
        Leerling l = new Leerling();
        l.setNaam(leerling.getNaam());
        l.setVoornaam(leerling.getVoornaam());
        l.setKlas(geselecteerdeKlas);
        geselecteerdeKlas.voegLeerlingToe(l);

        GenericDaoJpa.commitTransaction();
    }

    /**
     *
     * @param klas
     */
    public void maakNieuweKlas(KlasDto klas) {
        if (geselecteerdeGraad != null)
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");

        GenericDaoJpa.startTransaction();

        Klas k = new Klas();
        k.setGraad(geselecteerdeGraad);
        geselecteerdeGraad.voegKlasToe(k);
        k.setNaam(klas.getNaam());
        k.setLeerjaar(klas.getLeerjaar());

        GenericDaoJpa.commitTransaction();

    }

    /**
     *
     * @param observer
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     *
     * @param observer
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     *
     * @param leerling
     */
    public void selecteerLeerling(LeerlingDto leerling) {
        if (geselecteerdeGraad != null)
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        if (geselecteerdeKlas != null)
            throw new IllegalArgumentException("U dient eerst een klas te selecteren.");

        Optional<Leerling> leerlingT = geselecteerdeKlas.getLeerlingen().stream().filter((l) -> l.getId() == leerling.getId()).findFirst();

        if (!leerlingT.isPresent())
            throw new IllegalArgumentException("Deze leerling bestaat niet.");

        geselecteerdeLeerling = leerlingT.get();
        notifyObservers(null, geselecteerdeLeerling);
    }

    /**
     *
     * @param graad
     */
    public void selecteerGraad(GraadDto graad) {
        Graad t = gradenRepository.getAll().stream().filter((g) -> g.getJaar() == graad.getJaar() && g.getNummer() == graad.getGraad()).findFirst().get();
        if (t == null)
            throw new IllegalArgumentException("De geselecteerde graad bestaat niet.");
        geselecteerdeGraad = t;
        geselecteerdeKlas = null;
        geselecteerdeLeerling = null;

        geselecteerdeGraad.getKlassen().forEach((Klas k) -> klassen.add(new KlasDto(k.getId(), k.getNaam(), k.getLeerjaar())));
    }

    /**
     *
     * @param klas
     */
    public void selecteerKlas(KlasDto klas) {
        if (geselecteerdeGraad == null)
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        Optional<Klas> klasT = geselecteerdeGraad.getKlassen().stream().filter((k) -> k.getNaam().equals(klas.getNaam())).findFirst();

        if (!klasT.isPresent())
            throw new IllegalArgumentException("Deze klas bestaat niet.");

        geselecteerdeKlas = klasT.get();
        geselecteerdeLeerling = null;
        updateLeerlingen();

    }

    private void updateLeerlingen() {

        leerlingen.clear();

        geselecteerdeKlas.getLeerlingen().forEach((Leerling l) -> leerlingen.add(new LeerlingDto(l.getId(), l.getNaam(), l.getVoornaam(), new KlasDto(l.getKlas().getId(), l.getKlas().getNaam(), l.getKlas().getLeerjaar()))));
    }

    /**
     *
     * @param leerling
     */
    public void wijzigLeerling(LeerlingDto leerling) {
        if (geselecteerdeGraad != null)
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        if (geselecteerdeKlas != null)
            throw new IllegalArgumentException("U dient eerst een klas te selecteren.");
        if (geselecteerdeLeerling != null)
            throw new IllegalArgumentException("U dient eerst een leerling te selecteren.");

        GenericDaoJpa.startTransaction();

        geselecteerdeLeerling.setNaam(leerling.getNaam());
        geselecteerdeLeerling.setVoornaam(leerling.getVoornaam());

        if (!leerling.getKlas().getNaam().equals(geselecteerdeKlas.getNaam())) {
            Optional<Klas> klasT = geselecteerdeGraad.getKlassen().stream().filter((k) -> k.getNaam().equals(leerling.getKlas().getNaam())).findFirst();
            if (!klasT.isPresent())
                throw new IllegalArgumentException("Klas bestaat niet.");
            geselecteerdeKlas.verwijderLeerling(geselecteerdeLeerling);
            Klas t = klasT.get();

            t.voegLeerlingToe(geselecteerdeLeerling);
            geselecteerdeLeerling.setKlas(t);

            updateLeerlingen();

        }

        GenericDaoJpa
                .commitTransaction();

        notifyObservers(null, geselecteerdeLeerling);
    }

    /**
     *
     * @param klas
     */
    public void wijzigKlas(KlasDto klas) {
        if (geselecteerdeGraad != null)
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        if (geselecteerdeKlas != null)
            throw new IllegalArgumentException("U dient eerst een klas te selecteren");

        GenericDaoJpa.startTransaction();

        geselecteerdeKlas.setNaam(klas.getNaam());
        geselecteerdeKlas.setLeerjaar(klas.getLeerjaar());

        GenericDaoJpa.commitTransaction();
    }

    public ObservableList<GraadDto> getGraden() {
        graden.clear();

        gradenRepository.getAll().forEach((Graad g) -> graden.add(new GraadDto(g.getNummer(), g.getJaar(), null)));

        return this.graden;
    }

    public ObservableList<LeerlingDto> getLeerlingen() {
        return this.leerlingen;
    }

    public ObservableList<KlasDto> getKlassen() {
        return this.klassen;
    }

    public ObservableList<KlasDto> getAlleKlassen() {
        alleKlassen.clear();

        klassenRepository.getAll().forEach((Klas k) -> alleKlassen.add(new KlasDto(k.getId(), k.getNaam(), k.getLeerjaar())));

        return alleKlassen.sorted((KlasDto o1, KlasDto o2) -> o1.getNaam().compareTo(o2.getNaam()));
    }

    @Override
    public void notifyObservers(String actie, Object object) {
        observers.forEach((Observer o) -> o.update(null, object));
    }

    public void verwijderLeerling() {
        
        leerlingRepository.delete(geselecteerdeLeerling);
       updateLeerlingen();
        
        geselecteerdeLeerling = null;
        notifyObservers("verwijdert", null);
    }

}
