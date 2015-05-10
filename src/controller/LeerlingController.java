package controller;

import dto.*;
import domein.*;
import java.util.*;
import javafx.collections.*;
import persistentie.*;

public class LeerlingController {

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

    private Comparator<KlasDto> klasComparator = new Comparator<KlasDto>() {
        @Override
        public int compare(KlasDto o1, KlasDto o2) {
            if (o1.getLeerjaar() != o2.getLeerjaar()) {
                return o1.getLeerjaar() - o2.getLeerjaar();
            }
            return o1.getNaam().compareTo(o2.getNaam());
        }
    };

    private Comparator<LeerlingDto> leerlingComparator = new Comparator<LeerlingDto>() {

        @Override
        public int compare(LeerlingDto o1, LeerlingDto o2) {
            int naam = o1.getNaam().getValue().compareTo(o2.getNaam().getValue());
            if (naam == 0) {
                return o1.getVoornaam().getValue().compareTo(o2.getVoornaam().getValue());
            }
            return naam;
        }

    };

    /**
     *
     * @param leerling
     */
    public void maakNieuweLeerling(LeerlingDto leerling) {
        if (geselecteerdeGraad == null) {
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        }
        if (geselecteerdeKlas == null) {
            throw new IllegalArgumentException("U dient eerst een klas te selecteren.");
        }

        GenericDaoJpa.startTransaction();
        Leerling l = new Leerling();
        l.setNaam(leerling.getNaam().get());
        l.setVoornaam(leerling.getVoornaam().get());
        l.setKlas(geselecteerdeKlas);
        geselecteerdeKlas.voegLeerlingToe(l);

        GenericDaoJpa.commitTransaction();
        updateLeerlingen();
    }

    /**
     *
     * @param klas
     */
    public void maakNieuweKlas(KlasDto klas) {
        if (geselecteerdeGraad == null) {
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        }

        Optional<Klas> gevonden = klassenRepository.getAll().stream().filter((k) -> k.getNaam().equals(klas.getNaam()) && k.getLeerjaar() == klas.getLeerjaar()).findFirst();

        if (gevonden.isPresent()) {
            throw new IllegalArgumentException("Deze klas bestaat reeds.");
        }

        GenericDaoJpa.startTransaction();

        Klas k = new Klas();
        k.setGraad(geselecteerdeGraad);
        geselecteerdeGraad.voegKlasToe(k);
        k.setNaam(klas.getNaam());
        k.setLeerjaar(klas.getLeerjaar());

        GenericDaoJpa.commitTransaction();
        klassen.clear();
        getAlleKlassen();
        geselecteerdeGraad.getKlassen().forEach((Klas kl) -> klassen.add(new KlasDto(kl.getId(), kl.getNaam(), kl.getLeerjaar())));
        klassen.sort(klasComparator);
    }

    /**
     *
     * @param leerling
     */
    public void selecteerLeerling(LeerlingDto leerling) {
        if (geselecteerdeGraad == null) {
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        }
        if (geselecteerdeKlas == null) {
            throw new IllegalArgumentException("U dient eerst een klas te selecteren.");
        }

        Optional<Leerling> leerlingT = geselecteerdeKlas.getLeerlingen().stream().filter((l) -> l.getId() == leerling.getId()).findFirst();

        if (!leerlingT.isPresent()) {
            throw new IllegalArgumentException("Deze leerling bestaat niet.");
        }

        geselecteerdeLeerling = leerlingT.get();
    }

    /**
     *
     * @param graad
     */
    public void selecteerGraad(GraadDto graad) {
        if (graad == null) {
            throw new IllegalArgumentException("Graad mag niet null zijn.");
        }
        Graad t = gradenRepository.getAll().stream().filter((g) -> g.getNummer() == graad.getGraad()).findFirst().get();
        if (t == null) {
            throw new IllegalArgumentException("De geselecteerde graad bestaat niet.");
        }
        geselecteerdeGraad = t;
        geselecteerdeKlas = null;
        geselecteerdeLeerling = null;
        leerlingen.clear();
        geselecteerdeGraad.getKlassen().forEach((Klas k) -> klassen.add(new KlasDto(k.getId(), k.getNaam(), k.getLeerjaar())));
        klassen.sort(klasComparator);
    }

    /**
     *
     * @param klas
     */
    public void selecteerKlas(KlasDto klas) {
        if (geselecteerdeGraad == null) {
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        }
        Optional<Klas> klasT = geselecteerdeGraad.getKlassen().stream().filter((k) -> k.getNaam().equals(klas.getNaam()) && k.getLeerjaar() == klas.getLeerjaar()).findFirst();

        if (!klasT.isPresent()) {
            throw new IllegalArgumentException("Deze klas bestaat niet.");
        }

        geselecteerdeKlas = klasT.get();
        geselecteerdeLeerling = null;

        updateLeerlingen();
        getAlleKlassen();
    }

    private void updateLeerlingen() {

        leerlingen.clear();

        geselecteerdeKlas.getLeerlingen().forEach((Leerling l) -> leerlingen.add(new LeerlingDto(l.getId(), l.getNaam(), l.getVoornaam(), new KlasDto(l.getKlas().getId(), l.getKlas().getNaam(), l.getKlas().getLeerjaar()))));
        leerlingen.sort(leerlingComparator);
    }

    /**
     *
     * @param leerling
     */
    public void wijzigLeerling(LeerlingDto leerling) {
        if (geselecteerdeGraad == null) {
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        }
        if (geselecteerdeKlas == null) {
            throw new IllegalArgumentException("U dient eerst een klas te selecteren.");
        }
        if (geselecteerdeLeerling == null) {
            throw new IllegalArgumentException("U dient eerst een leerling te selecteren.");
        }

        GenericDaoJpa.startTransaction();

        geselecteerdeLeerling.setNaam(leerling.getNaam().get());
        geselecteerdeLeerling.setVoornaam(leerling.getVoornaam().get());

        if (!leerling.getKlas().getValue().getNaam().equals(geselecteerdeKlas.getNaam()) || leerling.getKlas().getValue().getLeerjaar() != geselecteerdeKlas.getLeerjaar()) {
            Optional<Klas> klasT = klassenRepository.getAll().stream().filter((k) -> k.getNaam().equals(leerling.getKlas().getValue().getNaam()) && k.getLeerjaar() == leerling.getKlas().getValue().getLeerjaar()).findFirst();
            if (!klasT.isPresent()) {
                throw new IllegalArgumentException("Klas bestaat niet.");
            }
            geselecteerdeKlas.verwijderLeerling(geselecteerdeLeerling);
            Klas t = klasT.get();

            t.voegLeerlingToe(geselecteerdeLeerling);
            geselecteerdeLeerling.setKlas(t);

            updateLeerlingen();

        }

        GenericDaoJpa.commitTransaction();

    }

    /**
     *
     * @param klas
     */
    public void wijzigKlas(KlasDto klas) {
        if (geselecteerdeGraad == null) {
            throw new IllegalArgumentException("U dient eerst een graad te selecteren.");
        }
        if (geselecteerdeKlas == null) {
            throw new IllegalArgumentException("U dient eerst een klas te selecteren");
        }

        GenericDaoJpa.startTransaction();

        geselecteerdeKlas.setNaam(klas.getNaam());
        geselecteerdeKlas.setLeerjaar(klas.getLeerjaar());

        GenericDaoJpa.commitTransaction();
        klassen.clear();
        geselecteerdeGraad.getKlassen().forEach((Klas kl) -> klassen.add(new KlasDto(kl.getId(), kl.getNaam(), kl.getLeerjaar())));
        klassen.sort(klasComparator);
    }

    public ObservableList<GraadDto> getGraden() {
        graden.clear();

        gradenRepository.getAll().forEach((Graad g) -> {
            if (!(g.getJaar() == 2)) {
                graden.add(new GraadDto(g.getNummer(), 0, null));
            }
        });
        return this.graden;
    }

    public ObservableList<LeerlingDto> getLeerlingen() {
        leerlingen.sort(leerlingComparator);
        return this.leerlingen;
    }

    public ObservableList<KlasDto> getKlassen() {
        klassen.sort(klasComparator);
        return this.klassen;
    }

    public ObservableList<KlasDto> getAlleKlassen() {
        alleKlassen.clear();

        klassenRepository.getAll().forEach((Klas k) -> alleKlassen.add(new KlasDto(k.getId(), k.getNaam(), k.getLeerjaar())));

        return alleKlassen.sorted((KlasDto o1, KlasDto o2) -> {
            int jaar = o1.getLeerjaar() - o2.getLeerjaar();
            if (jaar == 0) {
                return o1.getNaam().compareTo(o2.getNaam());
            }
            return jaar;
        });
    }

    public KlasDto getGeselecteerdeKlasDto() {
        if (geselecteerdeKlas == null) {
            throw new IllegalArgumentException("Gelieve eerst een klas te selecteren.");
        }
        return new KlasDto(geselecteerdeKlas.getId(), geselecteerdeKlas.getNaam(), geselecteerdeKlas.getLeerjaar());
    }

    public void verwijderLeerling() {
        if (geselecteerdeLeerling == null) {
            throw new IllegalArgumentException("Gelieve eerst een leerling te selecteren.");
        }
        geselecteerdeKlas.verwijderLeerling(geselecteerdeLeerling);
        leerlingRepository.delete(geselecteerdeLeerling);

        geselecteerdeLeerling = null;
        updateLeerlingen();
    }

    public void verwijderKlas() {
        if (geselecteerdeKlas == null) {
            throw new IllegalArgumentException("Gelieve eerst een klas te selecteren.");
        }
        if (geselecteerdeKlas.getLeerlingen().isEmpty()) {
            geselecteerdeGraad.verwijderKlas(geselecteerdeKlas);
            klassenRepository.delete(geselecteerdeKlas);
        } else {
            throw new IllegalArgumentException("Een klas kan pas verwijderd worden als ze geen leerlingen meer heeft.");
        }

        klassen.clear();
        geselecteerdeGraad.getKlassen().forEach((Klas kl) -> klassen.add(new KlasDto(kl.getId(), kl.getNaam(), kl.getLeerjaar())));
        klassen.sort(klasComparator);
        geselecteerdeKlas = null;
    }

    //--- Methodes voor de testen ---
    GenericDao<Graad, String> getGradenRepository() {
        return gradenRepository;
    }

    void setGradenRepository(GenericDao<Graad, String> gradenRepository) {
        this.gradenRepository = gradenRepository;
    }

    void setGeselecteerdeKlas(Klas klas) {
        this.geselecteerdeKlas = klas;
    }

    void setGeselecteerdeGraad(Graad graad) {
        this.geselecteerdeGraad = graad;
    }

    Leerling getGeselecteerdeLeerling() {
        return geselecteerdeLeerling;
    }

    Graad getGeselecteerdeGraad() {
        return geselecteerdeGraad;
    }

    Klas getGeselecteerdeKlas() {
        return geselecteerdeKlas;
    }
    //--- Einde methodes voor de testen ---
}
