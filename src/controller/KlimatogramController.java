package controller;

import domein.Continent;
import domein.Klimatogram;
import domein.Land;
import domein.Maand;
import dto.ContinentDto;
import dto.KlimatogramDto;
import dto.LandDto;
import dto.MaandDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistentie.GenericDao;
import persistentie.GenericDaoJpa;

public class KlimatogramController implements Subject {

    private List<Observer> observers;
    private GenericDao<Continent, String> continentenRepository;
    protected Continent geselecteerdContinent;
    protected Land geselecteerdLand;
    protected Klimatogram geselecteerdKlimatogram;
    private ObservableList<String> continenten, landen, locaties;

    public KlimatogramController() {
        observers = new ArrayList<>();
        continentenRepository = new GenericDaoJpa<>(Continent.class);
        continenten = FXCollections.observableArrayList();
        landen = FXCollections.observableArrayList();
        locaties = FXCollections.observableArrayList();
    }

    /**
     *
     * @param continent
     */
    public void voegContinentToe(ContinentDto continent) {
        if (continent == null) {
            throw new IllegalArgumentException("Continent moet correct ingevuld zijn");
        }
        Continent cont = new Continent(continent.getNaam());
        continentenRepository.insert(cont);
    }

    public ObservableList<String> getContinenten() {
        List<Continent> continenten = continentenRepository.getAll();
        continenten.forEach(c -> this.continenten.add(c.getNaam()));
        return this.continenten;
    }

    /**
     *
     * @param continent
     */
    public void selecteerContinent(ContinentDto continent) {
        if (continent == null || continent.getNaam() == null) {
            throw new IllegalArgumentException("Continent moet correct ingevuld zijn");
        }
        Continent cont = continentenRepository.get(continent.getNaam());
        if (cont == null) {
            throw new IllegalArgumentException("Continent bestaat niet");
        }
        geselecteerdContinent = cont;
        landen.clear();
        locaties.clear();
        geselecteerdContinent.getLanden().forEach(l -> landen.add(l.getNaam()));
        geselecteerdKlimatogram = null;
        geselecteerdLand = null;
    }

    public ObservableList<String> getLanden() {
        return landen;
    }

    public ObservableList<String> getLocaties() {
        return locaties;
    }

    public void selecteerKlimatogram(KlimatogramDto klimatogram) {
        if (geselecteerdLand == null) {
            throw new IllegalArgumentException("Land moet eerst geselecteerd worden");
        }
        if (klimatogram == null || klimatogram.getLocatie() == null) {
            throw new IllegalArgumentException("Locatie moet correct ingevuld zijn");
        }
        Klimatogram klim = geselecteerdLand.getKlimatogrammen().stream().filter(kl -> kl.getLocatie().equalsIgnoreCase(klimatogram.getLocatie())).findFirst().get();
        if (klim == null) {
            throw new IllegalArgumentException("Klimatogram bestaat niet");
        }
        geselecteerdKlimatogram = klim;
        KlimatogramDto kl = new KlimatogramDto();
        kl.setBeginJaar(geselecteerdKlimatogram.getBeginJaar());
        kl.setEindJaar(geselecteerdKlimatogram.getEindJaar());
        kl.setLatitude(geselecteerdKlimatogram.getLatitude());
        kl.setLongitude(geselecteerdKlimatogram.getLongitude());
        kl.setStation(geselecteerdKlimatogram.getStation());
        kl.setLocatie(geselecteerdKlimatogram.getLocatie());
        List<MaandDto> maanden = new ArrayList<>();
        geselecteerdKlimatogram.getMaanden().stream().forEach(m -> {
            MaandDto maand = new MaandDto();
            maand.setNaam(m.getNaam());
            maand.setNeerslag(m.getNeerslag());
            maand.setTemperatuur(m.getTemperatuur());
            maanden.add(maand);
        });
        kl.maanden = FXCollections.observableArrayList(maanden);
        notifyObservers(kl);
    }

    /**
     *
     * @param land
     */
    public void selecteerLand(LandDto land) {
        if (geselecteerdContinent == null) {
            throw new IllegalArgumentException("Continent moet eerst geselecteerd worden");
        }
        if (land == null || land.getNaam() == null) {
            throw new IllegalArgumentException("Land moet correct ingevuld zijn");
        }
        Collection<Land> landen = geselecteerdContinent.getLanden();
        Land la = landen.stream().filter(l -> l.getNaam().equalsIgnoreCase(land.getNaam())).findFirst().get();
        if (la == null) {
            throw new IllegalArgumentException("Land bestaat niet");
        }
        geselecteerdLand = la;
        locaties.clear();
        geselecteerdLand.getKlimatogrammen().forEach(k -> locaties.add(k.getLocatie()));
        geselecteerdKlimatogram = null;
    }

    /**
     *
     * @param klimatogram
     */
    public void voegKlimatogramToe(KlimatogramDto klimatogramDto) {
        if (klimatogramDto == null) {
            throw new IllegalArgumentException("Klimatogram moet correct ingevuld zijn");
        }
        VerkeerdeInputException vie = new VerkeerdeInputException();
        Klimatogram klim = new Klimatogram();
        try {
            klim.setBeginJaar(klimatogramDto.getBeginJaar());
        } catch (IllegalArgumentException e) {
            vie.add("beginJaar", e);
        }
        try {
            klim.setEindJaar(klimatogramDto.getEindJaar());
        } catch (IllegalArgumentException e) {
            vie.add("eindJaar", e);
        }
        try {
            klim.setLatitude(klimatogramDto.getLatitude());
        } catch (IllegalArgumentException e) {
            vie.add("latitude", e);
        }
        try {
            klim.setLongitude(klimatogramDto.getLongitude());
        } catch (IllegalArgumentException e) {
            vie.add("longitude", e);
        }
        try {
            klim.setLocatie(klimatogramDto.getLocatie());
        } catch (IllegalArgumentException e) {
            vie.add("locatie", e);
        }
        try {
            klim.setStation(klimatogramDto.getStation());
        } catch (IllegalArgumentException e) {
            vie.add("station", e);
        }
        for (int i = 0; i < 12; i++) {
            try {
                klim.getMaanden().get(i).setNeerslag(klimatogramDto.maanden.get(i).getNeerslag());
            } catch (IllegalArgumentException e) {
                vie.add("neerslagMaand" + (i + 1), e);
            }
            try {
                klim.getMaanden().get(i).setTemperatuur(klimatogramDto.maanden.get(i).getTemperatuur());
            } catch (IllegalArgumentException e) {
                vie.add("temperatuurMaand" + (i + 1), e);
            }
        }
        if (!vie.isEmpty()) {
            throw vie;
        }
        geselecteerdLand.voegKlimatogramToe(klim);
        continentenRepository.update(geselecteerdContinent);
        geselecteerdKlimatogram = klim;
    }

    public ObservableList<MaandDto> getMaanden() {
        if (geselecteerdKlimatogram == null) {
            throw new IllegalArgumentException("Klimatogram moet eerst geselecteerd worden");
        }
        Collection<Maand> maanden = geselecteerdKlimatogram.getMaanden();
        ObservableList<MaandDto> maandenDto = FXCollections.observableArrayList();
        for (Maand m : maanden) {
            MaandDto maand = new MaandDto();
            maand.setNaam(m.getNaam());
            maand.setNeerslag(m.getNeerslag());
            maand.setTemperatuur(m.getTemperatuur());
            maandenDto.add(maand);
        }
        return maandenDto;
    }

    /**
     *
     * @param land
     */
    public void voegLandToe(LandDto land) throws IllegalArgumentException {
        if (land == null) {
            throw new IllegalArgumentException("Land moet correct ingevuld zijn");
        }
        Land l = new Land(land.getNaam());
        geselecteerdContinent.voegLandToe(l);
        continentenRepository.update(geselecteerdContinent);
    }

    /**
     *
     * @param continentDao
     */
    public void setContinentRepository(GenericDao<Continent, String> continentDao) {
        continentenRepository = continentDao;
    }

    public ObservableList<KlimatogramDto> getKlimatogrammen() {
        if (geselecteerdLand == null) {
            throw new IllegalArgumentException("Land moet eerst geselecteerd worden");
        }
        Collection<Klimatogram> klimatogrammen = geselecteerdLand.getKlimatogrammen();
        ObservableList<KlimatogramDto> klimatogrammenDto = FXCollections.observableArrayList();
        for (Klimatogram k : klimatogrammen) {
            KlimatogramDto kDto = new KlimatogramDto();
            kDto.setBeginJaar(k.getBeginJaar());
            kDto.setEindJaar(k.getEindJaar());
            kDto.setLatitude(k.getLatitude());
            kDto.setLocatie(k.getLocatie());
            kDto.setLongitude(k.getLongitude());
            kDto.setStation(k.getStation());
            klimatogrammenDto.add(kDto);
        }
        return klimatogrammenDto.sorted((KlimatogramDto o1, KlimatogramDto o2) -> o1.getLocatie().compareTo(o2.getLocatie()));
    }

    public void wijzigKlimatogram(KlimatogramDto klimatogramDto) throws IllegalArgumentException, VerkeerdeInputException {
        boolean locatieGewijzigd = false;
        if (geselecteerdKlimatogram == null) {
            throw new IllegalArgumentException("Klimatogram moet eerst geselecteerd worden");
        }
        if (klimatogramDto == null) {
            throw new IllegalArgumentException("Klimatogram moet correct ingevuld zijn");
        }
        VerkeerdeInputException vie = new VerkeerdeInputException();
        if (!geselecteerdKlimatogram.getLocatie().equals(klimatogramDto.getLocatie())) {
            try {
                geselecteerdKlimatogram.setLocatie(klimatogramDto.getLocatie());
                locatieGewijzigd = true;
            } catch (IllegalArgumentException e) {
                vie.add("locatie", e);
            }
        }
        try {
            geselecteerdKlimatogram.setBeginJaar(klimatogramDto.getBeginJaar());
        } catch (IllegalArgumentException e) {
            vie.add("beginJaar", e);
        }
        try {
            geselecteerdKlimatogram.setEindJaar(klimatogramDto.getEindJaar());
        } catch (IllegalArgumentException e) {
            vie.add("eindJaar", e);
        }
        try {
            geselecteerdKlimatogram.setLatitude(klimatogramDto.getLatitude());
        } catch (IllegalArgumentException e) {
            vie.add("latitude", e);
        }
        try {
            geselecteerdKlimatogram.setLongitude(klimatogramDto.getLongitude());
        } catch (IllegalArgumentException e) {
            vie.add("longitude", e);
        }
        try {
            geselecteerdKlimatogram.setStation(klimatogramDto.getStation());
        } catch (IllegalArgumentException e) {
            vie.add("station", e);
        }
        for (int i = 0; i < 12; i++) {
            try {
                geselecteerdKlimatogram.getMaanden().get(i).setNeerslag(klimatogramDto.maanden.get(i).getNeerslag());
            } catch (IllegalArgumentException e) {
                vie.add("neerslagMaand" + (i + 1), e);
            }
            try {
                geselecteerdKlimatogram.getMaanden().get(i).setTemperatuur(klimatogramDto.maanden.get(i).getTemperatuur());
            } catch (IllegalArgumentException e) {
                vie.add("temperatuurMaand" + (i + 1), e);
            }
        }
        if (!vie.isEmpty()) {
            throw vie;
        }

        if (locatieGewijzigd) {
            try {
                Klimatogram k = (Klimatogram) geselecteerdKlimatogram.clone();
                geselecteerdLand.verwijderKlimatogram(geselecteerdKlimatogram.getLocatie());
                GenericDaoJpa.detach(geselecteerdKlimatogram);
                continentenRepository.update(geselecteerdContinent);
                geselecteerdLand.voegKlimatogramToe(k);
                continentenRepository.update(geselecteerdContinent);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
        } else {
            continentenRepository.update(geselecteerdContinent);
        }
    }

    public void verwijderKlimatogram(String locatie) throws IllegalArgumentException {
        if (geselecteerdKlimatogram == null) {
            throw new IllegalArgumentException("Klimatogram moet eerst geselecteerd worden");
        }
        geselecteerdLand.verwijderKlimatogram(locatie);
        continentenRepository.update(geselecteerdContinent);
    }

    public boolean klimatogramGeselecteerd() {
        return geselecteerdKlimatogram != null;
    }

    public boolean landGeselecteerd() {
        return geselecteerdLand != null;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void notifyObservers(Object object) {
        observers.forEach(o -> o.update(object));
    }

}
