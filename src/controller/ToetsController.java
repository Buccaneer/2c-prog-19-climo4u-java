package controller;

import domein.DeterminatieVraag;
import domein.Graad;
import domein.Klas;
import domein.Klimatogram;
import domein.LocatieVraag;
import domein.LosseVraag;
import domein.Toets;
import domein.ToetsVraag;
import domein.VraagFactory;
import dto.GraadDto;
import dto.KlasDto;
import dto.KlimatogramDto;
import dto.MaandDto;
import dto.ToetsDto;
import dto.VraagDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistentie.GenericDao;
import persistentie.GenericDaoJpa;

public class ToetsController implements ListChangeListener<KlasDto> {

    private Toets geselecteerdeToets;
    private Graad graad;
    private ObservableList<KlasDto> klassenVanToets = FXCollections.observableArrayList();
    private ObservableList<VraagDto> vragenOL = FXCollections.observableArrayList();
    private GenericDao<Toets, Integer> toetsrepository = new GenericDaoJpa<>(Toets.class);
    private GenericDao<Graad, Integer> graadrepository = new GenericDaoJpa<>(Graad.class);
    private GenericDao<Klas, Integer> klassenRepository = new GenericDaoJpa<>(Klas.class);
    private GenericDao<Klimatogram, String> klimatogramRepository = new GenericDaoJpa<>(Klimatogram.class);

    void setKlimatogramRepository(GenericDao<Klimatogram, String> klimatogramRepository) {
        this.klimatogramRepository = klimatogramRepository;
    }

    private Comparator<KlasDto> klasComparator = new Comparator<KlasDto>() {
        @Override
        public int compare(KlasDto o1, KlasDto o2) {
            if (o1.getLeerjaar() != o2.getLeerjaar()) {
                return o1.getLeerjaar() - o2.getLeerjaar();
            }
            return o1.getNaam().compareTo(o2.getNaam());
        }
    };

    public ToetsController() {
        klassenVanToets.addListener(this);
    }

    //--- Methodes voor de testen ---
    void setToetsrepository(GenericDao<Toets, Integer> toetsrepository) {
        this.toetsrepository = toetsrepository;
    }

    void setKlassenRepository(GenericDao<Klas, Integer> klassenRepository) {
        this.klassenRepository = klassenRepository;
    }

    void setGraadrepository(GenericDao<Graad, Integer> graadrepository) {
        this.graadrepository = graadrepository;
    }

    Toets getGeselecteerdeToetsVoorTesten() {
        return geselecteerdeToets;
    }

    void setGeselecteerdeToets(Toets t) {
        geselecteerdeToets = t;
        vulKlassenVanToetsOp();
        vulVragenOp();
    }

    //--- Einde methodes voor de testen ---
    
    public ToetsDto getGeselecteerdeToets() {
        if (geselecteerdeToets == null) {
            return null;
        }
        ToetsDto dto = new ToetsDto();
        dto.setAanvang(geselecteerdeToets.getStartDatumUur());
        dto.setBeschrijving(new SimpleStringProperty(geselecteerdeToets.getBeschrijving()));
        dto.setEind(geselecteerdeToets.getEindDatumUur());
        GraadDto graad = new GraadDto();
        graad.setGraad(geselecteerdeToets.getGraad().getGraad());
        graad.setJaar(geselecteerdeToets.getGraad().getJaar());
        dto.setGraad(new SimpleObjectProperty(graad));
        dto.setId(geselecteerdeToets.getId());
        dto.setTitel(new SimpleStringProperty(geselecteerdeToets.getTitel()));
        return dto;
    }

    /**
     *
     * @param toetsDto
     */
    public void maakNieuweToets(ToetsDto toetsDto) {
        if (toetsDto == null) {
            throw new IllegalArgumentException("Gelieve een toets te selecteren");
        }
        geselecteerdeToets = new Toets();
        geselecteerdeToets.setTitel(toetsDto.getTitel().getValue());
        Optional<Graad> graad = graadrepository.getAll().stream().filter((Graad g) -> g.getGraad() == toetsDto.getGraad().getValue().getGraad()).findFirst();
        if (!graad.isPresent()) {
            throw new IllegalArgumentException("Graad is niet gevonden");
        }
        geselecteerdeToets.setGraad(graad.get());
        vulKlassenVanToetsOp();

        toetsrepository.insert(geselecteerdeToets);
    }

    /**
     *
     * @param toets
     */
    public void verwijderToets(ToetsDto toets) {
        if (toets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren.");
        }
        geselecteerdeToets = toetsrepository.get(toets.getId());
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Toets bestaat niet");
        }
        toetsrepository.delete(geselecteerdeToets);
        geselecteerdeToets = null;
    }

    /**
     *
     * @param toets
     */
    public void wijzigToets(ToetsDto toets) {
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren.");
        }
        if (toets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren.");
        }
        geselecteerdeToets = toetsrepository.get(toets.getId());
        try {
            GenericDaoJpa.startTransaction();
            geselecteerdeToets.setBeschrijving(toets.getBeschrijving().getValue());
            geselecteerdeToets.setStartDatumUur(toets.getAanvang());
            geselecteerdeToets.setEindDatumUur(toets.getEind());
            GenericDaoJpa.commitTransaction();
        } catch (IllegalArgumentException e) {
            throw e;
        }
        vulKlassenVanToetsOp();
    }

    /**
     *
     * @param toets
     */
    public void selecteerToets(ToetsDto toets) {
        if (toets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren.");
        }

        geselecteerdeToets = toetsrepository.get(toets.getId());
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Toets bestaat niet");
        }
        graad = geselecteerdeToets.getGraad();
        vulKlassenVanToetsOp();
        vulVragenOp();
    }

    public ObservableList<GraadDto> geefAlleGraden() {
        ObservableList<GraadDto> graadLijst = FXCollections.observableArrayList();
        graadrepository.getAll().forEach((Graad g) -> {
            if (!(g.getJaar() == 2)) {
                graadLijst.add(new GraadDto(g.getNummer(), 0, null));
            }
        });
        return graadLijst;
    }

    public ObservableList<KlasDto> geefAlleKlassen() {
        ObservableList<KlasDto> klasLijst = FXCollections.observableArrayList();
        klassenRepository.getAll().forEach((k) -> {
            klasLijst.add(new KlasDto(k.getId(), k.getNaam(), k.getLeerjaar()));
        });
        return klasLijst;
    }

    public ObservableList<KlasDto> geefKlassenVanToets() {
        klassenVanToets.sort(klasComparator);
        return klassenVanToets;
    }

    public ObservableList<KlasDto> geefKlassenNietVanToets() {
        ObservableList<KlasDto> klasLijst = FXCollections.observableArrayList();
        if (geselecteerdeToets == null) {
            return klasLijst;
        }
        klassenRepository.getAll().forEach((k) -> {
            boolean nietToevoegen = false;
            for (Klas s : geselecteerdeToets.getKlassen()) {
                if (k.getId() == s.getId()) {
                    nietToevoegen = true;
                }
            }
            if (!nietToevoegen) {
                klasLijst.add(new KlasDto(k.getId(), k.getNaam(), k.getLeerjaar()));
            }
        });
        klasLijst.sort(klasComparator);
        return klasLijst;
    }

    private void vulKlassenVanToetsOp() {
        klassenVanToets.removeListener(this);
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren.");
        }
        klassenVanToets.clear();
        geselecteerdeToets.getKlassen().forEach((k) -> {
            klassenVanToets.add(new KlasDto(k.getId(), k.getNaam(), k.getLeerjaar()));
        });
        klassenVanToets.sort(klasComparator);
        klassenVanToets.addListener(this);
    }

    /**
     * Altijd een observable list terug geven ook indien nog geen toets
     * geselecteerd is. Gewoon opvullen en aanpassen.
     *
     * @return
     */
    public ObservableList<VraagDto> geefVragen() {
        return vragenOL;
    }

    private void vulVragenOp() {
        vragenOL.clear();
        geselecteerdeToets.getVragen().forEach((v) -> {
            if (v instanceof LosseVraag) {
                LosseVraag vraag = (LosseVraag) v;
                KlimatogramDto dto = new KlimatogramDto();
                if (vraag.getKlimatogram() != null) {
                    dto.setLocatie(vraag.getKlimatogram().getLocatie());
                }
                List<KlimatogramDto> kLijst = new ArrayList<>(Arrays.asList(dto));
                vragenOL.add(new VraagDto(v.getId(), VraagDto.GRAADEEN, kLijst, vraag.getSubvragenLijst(), v.getBeschrijving(), v.getTeBehalenPunten()));
            }
            if (v instanceof DeterminatieVraag) {
                DeterminatieVraag vraag = (DeterminatieVraag) v;
                KlimatogramDto dto = new KlimatogramDto();
                if (vraag.getKlimatogram() != null) {
                    dto.setLocatie(vraag.getKlimatogram().getLocatie());
                }
                List<KlimatogramDto> kLijst = new ArrayList<>(Arrays.asList(dto));
                vragenOL.add(new VraagDto(v.getId(), VraagDto.DETERMINATIE, kLijst, null, v.getBeschrijving(), v.getTeBehalenPunten()));
            }
            if (v instanceof LocatieVraag) {
                LocatieVraag vraag = (LocatieVraag) v;
                Set<Klimatogram> klimatogrammen = vraag.getKlimatogrammen();
                List<KlimatogramDto> kLijst = new ArrayList<>();
                klimatogrammen.stream().forEach(kl -> {
                    KlimatogramDto dto = new KlimatogramDto();

                    dto.setLocatie(kl.getLocatie());
                    kLijst.add(dto);
                });
                vragenOL.add(new VraagDto(v.getId(), VraagDto.GRAADDRIE, kLijst, null, v.getBeschrijving(), v.getTeBehalenPunten()));
            }

        });
    }

    /**
     *
     * @param vraag
     */
    public void voegVraagToe(VraagDto vraag) {
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren.");
        }
        if (vraag == null) {
            throw new IllegalArgumentException("Gelieve eerst een type vraag te selecteren");
        }
        GenericDaoJpa.startTransaction();
        geselecteerdeToets.voegVraagToe(VraagFactory.maakVraag(vraag, klimatogramRepository));
        GenericDaoJpa.commitTransaction();
        vulVragenOp();
    }

    /**
     *
     * @param vraag
     */
    public void verwijderVraag(VraagDto vraag) {
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren");
        }
        if (vraag == null) {
            throw new IllegalArgumentException("Gelieve eerst een vraag te selecteren.");
        }
        ToetsVraag vr = geselecteerdeToets.getVragen().stream().filter((v) -> v.getId() == vraag.getId()).findFirst().get();
        if (vr == null) {
            throw new IllegalArgumentException("Vraag niet gevonden");
        }

        GenericDaoJpa.startTransaction();
        geselecteerdeToets.verwijderVraag(vr);
        GenericDaoJpa.commitTransaction();

        vulVragenOp();
    }

    /**
     *
     * @param vraag
     */
    public void wijzigVraag(VraagDto vraag) {
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren");
        }
        try {
            ToetsVraag vr = geselecteerdeToets.getVragen().stream().filter((v) -> v.getId() == vraag.getId()).findFirst().get();
            GenericDaoJpa.startTransaction();
            if (vraag.isGraadEenVraag()) {
                LosseVraag vrg = (LosseVraag) vr;
                vrg.setBeschrijving(vraag.getBeschrijving());
                if (vraag.getKlimatogrammen().size() > 0 && vraag.getKlimatogrammen().get(0) != null && vraag.getKlimatogrammen().get(0).getLocatie() != null) {
                    Object o = klimatogramRepository.get(vraag.getKlimatogrammen().get(0).getLocatie());
                    if (o != null) {
                        vrg.setKlimatogram((Klimatogram) o);
                    }
                }
                vrg.setSubvragenLijst(vraag.getSubvragen());
                vrg.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            }
            if (vraag.isDeterminatieVraag()) {
                DeterminatieVraag vrg = (DeterminatieVraag) vr;
                vrg.setBeschrijving(vraag.getBeschrijving());
                if (vraag.getKlimatogrammen().size() > 0 && vraag.getKlimatogrammen().get(0) != null && vraag.getKlimatogrammen().get(0).getLocatie() != null) {
                    Object o = klimatogramRepository.get(vraag.getKlimatogrammen().get(0).getLocatie());
                    if (o != null) {
                        vrg.setKlimatogram((Klimatogram) o);
                    }
                }
                vrg.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            }
            if (vraag.isGraadDrieVraag()) {
                LocatieVraag vrg = (LocatieVraag) vr;
                vrg.setBeschrijving(vraag.getBeschrijving());
                HashSet<Klimatogram> klimatogrammen = new HashSet<>();
                vraag.getKlimatogrammen().stream().forEach(k -> klimatogrammen.add(klimatogramRepository.get(k.getLocatie())));
                vrg.setKlimatogrammen(klimatogrammen);
                vrg.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            }
            GenericDaoJpa.commitTransaction();
        } catch (Exception e) {
        }
        vulVragenOp();
    }

    /**
     * Toetscontroller is geristreerd bij klassenVAnTeots� Indien die observable
     * list wordt aangepast dan dient hij de relaties in het domein goed te
     * stellen. Als deze lijst wordt opgevuld dien je jezelf effen uit te
     * schrijven totdat of totop de lijst weer gevuld is en dan dien je jezelf
     * weer te abboneren�
     *
     */
    @Override
    public void onChanged(Change<? extends KlasDto> c) {
        try {

            List<Klas> klassenDieBestaan = klassenRepository.getAll();

            GenericDaoJpa.startTransaction();
            klassenDieBestaan.stream().forEach((Klas klas) -> klas.verwijderToets(geselecteerdeToets));
            geselecteerdeToets.getKlassen().clear();
            klassenVanToets.stream().forEach((klas) -> {

                Optional<Klas> oK = klassenDieBestaan.stream().filter((Klas k) -> k.getLeerjaar() == klas.getLeerjaar() && klas.getNaam().equals(k.getNaam())).findFirst();
                if (oK.isPresent()) {
                    geselecteerdeToets.voegKlasToe(oK.get());
                }
            });
            GenericDaoJpa.commitTransaction();
            c.next();
        } catch (Exception ex) {
            c.reset();
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public ObservableList<KlimatogramDto> geefKlimatogrammen() {
        List<Klimatogram> klimatogrammen = klimatogramRepository.getAll();
        ObservableList<KlimatogramDto> klimatogram = FXCollections.observableArrayList();
        List<Klimatogram> kl = klimatogrammen.stream().filter(klim -> klim.getLand().getContinent().getGraden().contains(graad)).collect(Collectors.toList());
        kl.stream().forEach(k -> klimatogram.add(new KlimatogramDto(k.getLocatie())));
        return klimatogram;
    }

    public ObservableList<ToetsDto> geefToetsen() {
        List<Toets> toetsen = toetsrepository.getAll();
        ObservableList<ToetsDto> toets = FXCollections.observableArrayList();
        toetsen.stream().forEach(t -> {
            ToetsDto dto = new ToetsDto();
            dto.setAanvang(t.getStartDatumUur());
            dto.setEind(t.getEindDatumUur());
            dto.setBeschrijving(new SimpleStringProperty(t.getBeschrijving()));
            dto.setTitel(new SimpleStringProperty(t.getTitel()));
            dto.setId(t.getId());
            GraadDto graad = new GraadDto();
            graad.setGraad(t.getGraad().getGraad());
            graad.setJaar(t.getGraad().getJaar());
            dto.setGraad(new SimpleObjectProperty(graad));
            toets.add(dto);
        });
        return toets;
    }

    public void verwijderKlas(KlasDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Gelieve een klas te selecteren");
        }
        klassenVanToets.remove(dto);
    }

    public void voegKlasToe(KlasDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Gelieve een klas te selecteren");
        }
        klassenVanToets.add(dto);
        klassenVanToets.sort(klasComparator);
    }

    public ToetsDto getPrintDto() {
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren");
        }
        try {
            geselecteerdeToets.valideer();
        } catch (IllegalArgumentException ex) {
            throw ex;
        }

        ToetsDto dto = new ToetsDto();
        dto.setTitel(new SimpleStringProperty(geselecteerdeToets.getTitel()));
        if (geselecteerdeToets.getBeschrijving() != null) {
            dto.setBeschrijving(new SimpleStringProperty(geselecteerdeToets.getBeschrijving()));
        }
        if (geselecteerdeToets.getStartDatumUur() != null && geselecteerdeToets.getEindDatumUur() != null) {
            dto.setAanvang(geselecteerdeToets.getStartDatumUur());
            dto.setEind(geselecteerdeToets.getEindDatumUur());
        }

        List<VraagDto> vragen = new ArrayList<>();
        for (ToetsVraag v : geselecteerdeToets.getVragen()) {
            VraagDto vraag = new VraagDto();
            vraag.setBeschrijving(v.getBeschrijving());
            vraag.setPuntenTeVerdienen(v.getTeBehalenPunten());
            if (v instanceof LosseVraag) {
                LosseVraag vr = (LosseVraag) v;
                vraag.setType(VraagDto.GRAADEEN);
                KlimatogramDto kl = new KlimatogramDto();
                kl.setBeginJaar(vr.getKlimatogram().getBeginJaar());
                kl.setEindJaar(vr.getKlimatogram().getEindJaar());
                kl.setLatitude(vr.getKlimatogram().getLatitude());
                kl.setLongitude(vr.getKlimatogram().getLongitude());
                kl.setStation(vr.getKlimatogram().getStation());
                kl.setLocatie(vr.getKlimatogram().getLocatie() + " - " + vr.getKlimatogram().getLand().getNaam());
                List<MaandDto> maanden = new ArrayList<>();
                vr.getKlimatogram().getMaanden().stream().forEach(m -> {
                    MaandDto maand = new MaandDto();
                    maand.setNaam(m.getNaam());
                    maand.setNeerslag(m.getNeerslag());
                    maand.setTemperatuur(m.getTemperatuur());
                    maanden.add(maand);
                });
                kl.maanden = FXCollections.observableArrayList(maanden);
                vraag.setKlimatogrammen(Arrays.asList(new KlimatogramDto[]{kl}));
                vraag.setSubvragen(vr.getSubvragenLijst());
            }
            if (v instanceof DeterminatieVraag) {
                DeterminatieVraag vr = (DeterminatieVraag) v;
                vraag.setType(VraagDto.DETERMINATIE);
                KlimatogramDto kl = new KlimatogramDto();
                kl.setBeginJaar(vr.getKlimatogram().getBeginJaar());
                kl.setEindJaar(vr.getKlimatogram().getEindJaar());
                kl.setLatitude(vr.getKlimatogram().getLatitude());
                kl.setLongitude(vr.getKlimatogram().getLongitude());
                kl.setStation(vr.getKlimatogram().getStation());
                kl.setLocatie(vr.getKlimatogram().getLocatie() + " - " + vr.getKlimatogram().getLand().getNaam());
                List<MaandDto> maanden = new ArrayList<>();
                vr.getKlimatogram().getMaanden().stream().forEach(m -> {
                    MaandDto maand = new MaandDto();
                    maand.setNaam(m.getNaam());
                    maand.setNeerslag(m.getNeerslag());
                    maand.setTemperatuur(m.getTemperatuur());
                    maanden.add(maand);
                });
                kl.maanden = FXCollections.observableArrayList(maanden);
                vraag.setKlimatogrammen(Arrays.asList(new KlimatogramDto[]{kl}));
            }
            if (v instanceof LocatieVraag) {
                LocatieVraag vr = (LocatieVraag) v;
                vraag.setType(VraagDto.GRAADDRIE);
                List<KlimatogramDto> klimatogrammen = new ArrayList<>();
                for (Klimatogram klimatogram : vr.getKlimatogrammen()) {
                    KlimatogramDto kl = new KlimatogramDto();
                    kl.setBeginJaar(klimatogram.getBeginJaar());
                    kl.setEindJaar(klimatogram.getEindJaar());
                    kl.setLatitude(klimatogram.getLatitude());
                    kl.setLongitude(klimatogram.getLongitude());
                    kl.setStation(klimatogram.getStation());
                    kl.setLocatie(klimatogram.getLocatie() + " - " + klimatogram.getLand().getNaam());
                    List<MaandDto> maanden = new ArrayList<>();
                    klimatogram.getMaanden().stream().forEach(m -> {
                        MaandDto maand = new MaandDto();
                        maand.setNaam(m.getNaam());
                        maand.setNeerslag(m.getNeerslag());
                        maand.setTemperatuur(m.getTemperatuur());
                        maanden.add(maand);
                    });
                    kl.maanden = FXCollections.observableArrayList(maanden);
                    klimatogrammen.add(kl);
                }
                vraag.setKlimatogrammen(klimatogrammen);
            }
            vragen.add(vraag);
        }
        dto.setVragen(vragen);
        dto.setDeterminatietabel(geselecteerdeToets.getGraad().getActieveTabel().maakDtoAan());
        dto.setAantalPuntenTeBehalen(geselecteerdeToets.berekenTotaleScore());
        return dto;
    }
}
