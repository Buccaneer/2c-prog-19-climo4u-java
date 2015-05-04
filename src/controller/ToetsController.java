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
import dto.ToetsDto;
import dto.VraagDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

    public ToetsController() {
        klassenVanToets.addListener(this);
    }

    void setToetsrepository(GenericDao<Toets, Integer> toetsrepository) {
        this.toetsrepository = toetsrepository;
    }

    void setGraadrepository(GenericDao<Graad, Integer> graadrepository) {
        this.graadrepository = graadrepository;
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
        geselecteerdeToets.setTitel(toetsDto.getTitel());
        Optional<Graad> graad = graadrepository.getAll().stream().filter((Graad g) -> g.getGraad() == toetsDto.getGraad().getGraad()).findFirst();
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
        toetsrepository.delete(geselecteerdeToets);
        geselecteerdeToets = null;
    }

    /**
     *
     * @param toets
     */
    public void wijzigToets(ToetsDto toets) {
        if (toets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren.");
        }
        geselecteerdeToets = toetsrepository.get(toets.getId());
        try {
            GenericDaoJpa.startTransaction();
            geselecteerdeToets.setBeschrijving(toets.getBeschrijving());
            geselecteerdeToets.setEindDatumUur(toets.getEind());
            geselecteerdeToets.setStartDatumUur(toets.getAanvang());
            geselecteerdeToets.setTitel(toets.getTitel());

            GenericDaoJpa.commitTransaction();
        } catch (IllegalArgumentException e) {
            throw e;
        }
        vulKlassenVanToetsOp();
        geselecteerdeToets = null;
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

        return klassenVanToets;
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
        klassenVanToets.addListener(this);
    }

    /**
     * Altijd een observable list terug geven ook indien nog geen toets
     * geselecteerd is. Gewoon opvullen en aanpassen.
     *
     * @return
     */
    public ObservableList<VraagDto> geefVragen() {
        // altijd observable list terug geven

        return vragenOL;
    }

    private void vulVragenOp() {
        vragenOL.clear();
        geselecteerdeToets.getVragen().forEach((v) -> {
            if (v instanceof LosseVraag) {
                LosseVraag vraag = (LosseVraag) v;
                KlimatogramDto dto = new KlimatogramDto();
                dto.setLocatie(vraag.getKlimatogram().getLocatie());
                List<KlimatogramDto> kLijst = new ArrayList<>(Arrays.asList(dto));
                vragenOL.add(new VraagDto(v.getId(), VraagDto.GRAADEEN, kLijst, vraag.getSubvragenLijst(), v.getBeschrijving(), v.getTeBehalenPunten()));
            }
            if (v instanceof DeterminatieVraag) {
                DeterminatieVraag vraag = (DeterminatieVraag) v;
                KlimatogramDto dto = new KlimatogramDto();
                dto.setLocatie(vraag.getKlimatogram().getLocatie());
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
        ToetsVraag vr = geselecteerdeToets.getVragen().stream().filter((v) -> v.getId() == vraag.getId()).findFirst().get();
        GenericDaoJpa.startTransaction();
        if (vraag.isGraadEenVraag()) {
            LosseVraag vrg = (LosseVraag) vr;
            vrg.setBeschrijving(vraag.getBeschrijving());
            vrg.setKlimatogram(klimatogramRepository.get(vraag.getKlimatogrammen().get(0).getLocatie()));
            vrg.setSubvragenLijst(vraag.getSubvragen());
            vrg.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
        }
        if (vraag.isDeterminatieVraag()) {
            DeterminatieVraag vrg = (DeterminatieVraag) vr;
            vrg.setBeschrijving(vraag.getBeschrijving());
            vrg.setKlimatogram(klimatogramRepository.get(vraag.getKlimatogrammen().get(0).getLocatie()));
            vrg.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
        }
        if (vraag.isGraadDrieVraag()) {
            LocatieVraag vrg = (LocatieVraag) vr;
            vrg.setBeschrijving(vraag.getBeschrijving());
            HashSet<Klimatogram> klimatogrammen = new HashSet<>();
            vraag.getKlimatogrammen().stream().forEach(k->klimatogrammen.add(klimatogramRepository.get(k.getLocatie())));
            vrg.setKlimatogrammen(klimatogrammen);
            vrg.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
        }
        GenericDaoJpa.commitTransaction();
        vulVragenOp();
    }

    /**
     * Toetscontroller is geristreerd bij klassenVAnTeots� Indien die observable
     * list wordt aangepast dan dient hij de relaties in het domein goed te
     * stellen. Als deze lijst wordt opgevuld dien je jezelf effen uit te
     * schrijven totdat of totop de lijst weer gevuld is en dan dien je jezelf
     * weer te abboneren�
     *
     * @param object
     */
    @Override
    public void onChanged(Change<? extends KlasDto> c) {
        try {
            GenericDaoJpa.startTransaction();
            List<Klas> klassen = geselecteerdeToets.getKlassen();
            klassen.clear();
            List<Klas> klassenDieBestaan = klassenRepository.getAll();

            klassenVanToets.stream().forEach((klas) -> {
                klassen.add(klassenDieBestaan.stream().filter((Klas k) -> k.getLeerjaar() == klas.getLeerjaar() && klas.getNaam().equals(k.getNaam())).findFirst().get());
            });

            GenericDaoJpa.commitTransaction();
        } catch (Exception ex) {
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
            dto.setBeschrijving(t.getBeschrijving());
            dto.setTitel(t.getTitel());
            dto.setId(t.getId());
            toets.add(dto);
        });
        return toets;
    }

    public void verwijderKlas(KlasDto dto) {
        klassenVanToets.remove(dto);
    }

    public void voegKlasToe(KlasDto dto) {
        klassenVanToets.add(dto);
    }
}
