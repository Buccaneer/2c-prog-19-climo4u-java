package controller;

import domein.Continent;
import domein.Graad;
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
import java.util.Map.Entry;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistentie.GenericDao;
import persistentie.GenericDaoJpa;

public class KlimatogramController implements Subject {

    private List<Observer> observers;
    private GenericDao<Continent, String> continentenRepository;
    private GenericDao<Graad, String> graadRepository = new GenericDaoJpa<>(Graad.class);
    private GenericDao<Land, String> landenRepository = new GenericDaoJpa<>(Land.class);
    private GenericDao<Klimatogram, String> klimatogramRepository = new GenericDaoJpa<>(Klimatogram.class);
    protected Continent geselecteerdContinent;
    protected Land geselecteerdLand;
    protected Klimatogram geselecteerdKlimatogram;
    private ObservableList<ContinentDto> continenten = FXCollections.observableArrayList();
    private ObservableList<LandDto> landen = FXCollections.observableArrayList();
    private ObservableList<KlimatogramDto> locaties = FXCollections.observableArrayList();

    public KlimatogramController() {
        observers = new ArrayList<>();
        continentenRepository = new GenericDaoJpa<>(Continent.class);
    }

    /**
     *
     * @param continent
     */
    public void voegContinentToe(ContinentDto continent) {
        if (continent == null) {
            throw new IllegalArgumentException("U moet het werelddeel correct invullen");
        }
        Continent cont = new Continent(continent.getNaam());

        List<Graad> graden = graadRepository.getAll();
        if (continent.getGraden().entrySet().stream().allMatch(g -> g.getValue() == false)) {
            throw new IllegalArgumentException("Een werelddeel moet bij minstens 1 graad horen");
        }
        for (Entry<String, Boolean> obj : continent.getGraden().entrySet()) {
            if (obj.getValue() == true) {
                Optional<Graad> graad = graden.stream().filter(g -> g.toString().equals(obj.getKey())).findFirst();
                if (graad.isPresent()) {
                    cont.voegGraadToe(graad.get());
                }
            }
        }

        continentenRepository.insert(cont);
        continenten.clear();
        continenten = getContinenten();
    }

    public ObservableList<ContinentDto> getContinenten() {
        List<Continent> continenten = continentenRepository.getAll();
        List<Graad> graden = graadRepository.getAll();
        this.continenten.clear();
        for (Continent continent : continenten) {
            ContinentDto continentDto = new ContinentDto(continent.getNaam());
            for (Graad graad : graden) {
                continentDto.verwijderGraad(graad.toString());
            }

            for (Graad graad : continent.getGraden()) {
                continentDto.voegGraadToe(graad.toString());
            }
            this.continenten.add(continentDto);
        }
        return this.continenten;
    }

    /**
     *
     * @param continent
     */
    public void selecteerContinent(ContinentDto continent) {
        if (continent == null || continent.getNaam() == null) {
            throw new IllegalArgumentException("U moet een werelddeel selecteren");
        }
        Continent cont = continentenRepository.get(continent.getNaam());
        if (cont == null) {
            throw new IllegalArgumentException("Het werelddeel bestaat niet");
        }
        geselecteerdContinent = cont;
        landen.clear();
        locaties.clear();
        geselecteerdContinent.getLanden().forEach(l -> landen.add(new LandDto(l.getNaam())));
        geselecteerdKlimatogram = null;
        geselecteerdLand = null;
    }

    public ContinentDto maakNieuwContinentDto() {
        ContinentDto dto = new ContinentDto();

        List<Graad> graden = graadRepository.getAll();
        for (Graad graad : graden) {
            dto.verwijderGraad(graad.toString());
        }

        return dto;
    }

    public ObservableList<LandDto> getLanden() {
        return landen;
    }

    public ObservableList<KlimatogramDto> getLocaties() {
        return locaties;
    }

    public void selecteerKlimatogram(KlimatogramDto klimatogram) {
        if (geselecteerdLand == null) {
            throw new IllegalArgumentException("U moet eerst een land selecteren");
        }
        if (klimatogram == null || klimatogram.getLocatie() == null) {
            throw new IllegalArgumentException("U moet een locatie selecteren");
        }
        Klimatogram klim = geselecteerdLand.getKlimatogrammen().stream().filter(kl -> kl.getLocatie().equalsIgnoreCase(klimatogram.getLocatie())).findFirst().get();
        if (klim == null) {
            throw new IllegalArgumentException("De klimatogram bestaat niet");
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
        notifyObservers("vulIn", kl);
    }

    /**
     *
     * @param land
     */
    public void selecteerLand(LandDto land) {
        if (geselecteerdContinent == null) {
            throw new IllegalArgumentException("U moet eerst een werelddeel selecteren");
        }
        if (land == null || land.getNaam() == null) {
            throw new IllegalArgumentException("U moet land invullen");
        }
        Collection<Land> landen = geselecteerdContinent.getLanden();
        Land la = landen.stream().filter(l -> l.getNaam().equalsIgnoreCase(land.getNaam())).findFirst().get();
        if (la == null) {
            throw new IllegalArgumentException("Het land bestaat niet");
        }
        geselecteerdLand = la;
        locaties.clear();
        geselecteerdLand.getKlimatogrammen().forEach(k -> locaties.add(new KlimatogramDto(k.getLocatie())));
        geselecteerdKlimatogram = null;
    }

    /**
     *
     * @param klimatogram
     */
    public void voegKlimatogramToe(KlimatogramDto klimatogramDto) {
        if (klimatogramDto == null) {
            throw new IllegalArgumentException("U moet het klimatogram correct invullen");
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
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                vie.add("neerslagMaand" + (i + 1), e);
            }
            try {
                klim.getMaanden().get(i).setTemperatuur(klimatogramDto.maanden.get(i).getTemperatuur());
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                vie.add("temperatuurMaand" + (i + 1), e);
            }
        }

        if (!vie.isEmpty()) {
            throw vie;
        } else {
            geselecteerdLand.voegKlimatogramToe(klim);
            continentenRepository.update(geselecteerdContinent);
            locaties.clear();
            geselecteerdLand.getKlimatogrammen().forEach(k -> locaties.add(new KlimatogramDto(k.getLocatie())));
            geselecteerdKlimatogram = null;
        }

    }

    public ObservableList<MaandDto> getMaanden() {
        if (geselecteerdKlimatogram == null) {
            throw new IllegalArgumentException("U moet eerst een klimatogram selecteren");
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
            throw new IllegalArgumentException("U moet het land correct invullen");
        }
        Land l = new Land(land.getNaam());
        geselecteerdContinent.voegLandToe(l);
        continentenRepository.update(geselecteerdContinent);
        landen.clear();
        geselecteerdContinent.getLanden().forEach(la -> landen.add(new LandDto(la.getNaam())));
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
            throw new IllegalArgumentException("U moet eerst een land selecteren");
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
            throw new IllegalArgumentException("U moet eerst een klimatogram selecteren");
        }
        if (klimatogramDto == null) {
            throw new IllegalArgumentException("U moet het klimatogram correct invullen");
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
        locaties.clear();
        geselecteerdLand.getKlimatogrammen().forEach(k -> locaties.add(new KlimatogramDto(k.getLocatie())));
        geselecteerdKlimatogram = null;
    }

    public void verwijderKlimatogram(String locatie) throws IllegalArgumentException {
        if (geselecteerdKlimatogram == null) {
            throw new IllegalArgumentException("U moet eerst een klimatogram selecteren");
        }
        Klimatogram k = geselecteerdLand.verwijderKlimatogram(locatie);
        klimatogramRepository.delete(k);
        locaties.clear();
        geselecteerdLand.getKlimatogrammen().forEach(klim -> locaties.add(new KlimatogramDto(klim.getLocatie())));
        geselecteerdKlimatogram = null;
        notifyObservers("clear", null);
    }

    public void verwijderLand(LandDto land) {
        if (geselecteerdContinent == null) {
            throw new IllegalArgumentException("U moet eerst een continent selecteren");
        }

        Land l = geselecteerdContinent.verwijderLand(land.getNaam());

        landenRepository.delete(l);
        geselecteerdLand = null;
        landen.clear();
        geselecteerdContinent.getLanden().forEach(la -> landen.add(new LandDto(la.getNaam())));
    }

    public void verwijderContinent(ContinentDto continent) {
        Continent c = continentenRepository.get(continent.getNaam());

        if (c != null) {
            GenericDaoJpa.detach(c);
            continentenRepository.delete(c);
        }
        continenten.clear();
        geselecteerdContinent = null;
        continenten = getContinenten();
    }

    public boolean klimatogramGeselecteerd() {
        return geselecteerdKlimatogram != null;
    }

    public boolean landGeselecteerd() {
        return geselecteerdLand != null;
    }

    public boolean werelddeelGeselecteerd() {
        return geselecteerdContinent != null;
    }

    public void voegToe() {
        if (geselecteerdLand != null && geselecteerdContinent != null) {
            KlimatogramDto dto = new KlimatogramDto();
            dto.maanden.addAll(new MaandDto("Januari", 0, 0), new MaandDto("Februari", 0, 0), new MaandDto("Maart", 0, 0), new MaandDto("April", 0, 0), new MaandDto("Mei", 0, 0), new MaandDto("Juni", 0, 0), new MaandDto("Juli", 0, 0), new MaandDto("Augustus", 0, 0), new MaandDto("September", 0, 0), new MaandDto("Oktober", 0, 0), new MaandDto("November", 0, 0), new MaandDto("December", 0, 0));
            notifyObservers("voegToe", dto);
        } else {
            throw new IllegalArgumentException("U moet eerst een land en continent selecteren");
        }
    }

    public void wijzig() {
        if (geselecteerdKlimatogram != null) {
            KlimatogramDto dto = new KlimatogramDto(geselecteerdKlimatogram.getBeginJaar(), geselecteerdKlimatogram.getEindJaar(), geselecteerdKlimatogram.getLatitude(), geselecteerdKlimatogram.getLocatie(), geselecteerdKlimatogram.getLongitude(), geselecteerdKlimatogram.getStation());
            geselecteerdKlimatogram.getMaanden().stream().forEach(m -> {
                String naam = m.getNaam();
                int neerslag = m.getNeerslag();
                double temp = m.getTemperatuur();
                MaandDto d = new MaandDto();
                d.setNaam(naam);
                d.setNeerslag(neerslag);
                d.setTemperatuur(temp);
                dto.maanden.add(d);
            });
            notifyObservers("wijzig", dto);
        } else {
            throw new IllegalArgumentException("U moet eerst een klimatogram selecteren");
        }
    }

    public void clearLijstenWerelddeel() {
        this.geselecteerdContinent = null;
        this.geselecteerdKlimatogram = null;
        this.geselecteerdLand = null;
        landen.clear();
        locaties.clear();
        notifyObservers("clear", null);
    }

    public void clearLijstenLand() {
        geselecteerdLand = null;
        geselecteerdKlimatogram = null;
        locaties.clear();
        notifyObservers("clear", null);
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
    public void notifyObservers(String actie, Object object) {
        observers.forEach(o -> o.update(actie, object));
    }

    public void annuleren() {
        geselecteerdKlimatogram = null;
        notifyObservers("menu", null);
    }
}
