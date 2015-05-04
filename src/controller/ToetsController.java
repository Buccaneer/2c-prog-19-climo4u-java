package controller;

import domein.*;
import dto.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistentie.GenericDao;
import persistentie.GenericDaoJpa;

public class ToetsController implements ListChangeListener<KlasDto> {

    private Toets geselecteerdeToets;
    private Graad graad;
    private ToetsVraag geselecteerdeVraag;
    private ObservableList<KlasDto> klassenVanToets = FXCollections.observableArrayList();
private ObservableList<VraagDto>  vragenOL= FXCollections.observableArrayList();
    private GenericDao<Toets, Integer> toetsrepository = new GenericDaoJpa<>(Toets.class);
    private GenericDao<Continent, String> continentrepository = new GenericDaoJpa<>(Continent.class);
    private GenericDao<Graad, Integer> graadrepository = new GenericDaoJpa<>(Graad.class);
    private GenericDao<Klas, Integer> klassenRepository = new GenericDaoJpa<>(Klas.class);
    private GenericDao<Klimatogram, String> klimatogramRepository = new GenericDaoJpa<>(Klimatogram.class);

    void setToetsrepository(GenericDao<Toets, Integer> toetsrepository) {
        this.toetsrepository = toetsrepository;
    }

    void setGraadrepository(GenericDao<Graad, Integer> graadrepository) {
        this.graadrepository = graadrepository;
    }

    void setContinentrepository(GenericDao<Continent, String> continentrepository) {
        this.continentrepository = continentrepository;
    }

    public ToetsController() {
        klassenVanToets.addListener(this);
    }

    /**
     *
     * @param toetsDto
     */
    public void maakNieuweToets(ToetsDto toetsDto) {
        geselecteerdeToets = new Toets();
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

            geselecteerdeToets.setNaam(toets.getNaam());
            // moet nog gebeuren -> geselecteerdeToets.setBeschrijving(toets);

            geselecteerdeToets.setEindDatumUur(toets.getEind());
            geselecteerdeToets.setStartDatumUur(toets.getAanvang());
            // moet nog gebeuren -> geselecteerdeToets.setGraad(toets);
            geselecteerdeToets.setTitel(toets.getTitel());

            GenericDaoJpa.commitTransaction();
        } catch (IllegalArgumentException e) {
            throw e;
        }
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
        if (geselecteerdeToets == null) {
            throw new IllegalArgumentException("Gelieve eerst een toets te selecteren.");
        }
        
        geselecteerdeToets.getKlassen().forEach((k)->{
           klassenVanToets.add(new KlasDto(k.getId(),k.getNaam(),k.getLeerjaar()));
        });
        return klassenVanToets;
    }
/**
 * Altijd een observable list terug geven ook indien nog geen toets geselecteerd is. Gewoon opvullen en aanpassen.
 * @return 
 */
    public ObservableList<VraagDto> geefVragen() {
        // altijd observable list terug geven
      
        return vragenOL;
    }

    private void vulVragenOp()
    {
        vragenOL.clear();
        geselecteerdeToets.getVragen().forEach((v)->{
            if(v instanceof LosseVraag){
                LosseVraag vraag = (LosseVraag)v;
                KlimatogramDto dto = new KlimatogramDto();
                dto.setLocatie(vraag.getKlimatogram().getLocatie());
                List<KlimatogramDto> kLijst = new ArrayList<>(Arrays.asList(dto));
                vragenOL.add(new VraagDto(VraagDto.GRAADEEN, kLijst, vraag.getSubvragenLijst(), v.getBeschrijving(), v.getTeBehalenPunten()));
            }
            if(v instanceof DeterminatieVraag){
                DeterminatieVraag vraag = (DeterminatieVraag)v;
                KlimatogramDto dto = new KlimatogramDto();
                dto.setLocatie(vraag.getKlimatogram().getLocatie());
                List<KlimatogramDto> kLijst = new ArrayList<>(Arrays.asList(dto));
                vragenOL.add(new VraagDto(VraagDto.DETERMINATIE, kLijst, null, v.getBeschrijving(), v.getTeBehalenPunten()));
            }
            if(v instanceof LocatieVraag){
                LocatieVraag vraag = (LocatieVraag)v;
                Set<Klimatogram> klimatogrammen = vraag.getKlimatogrammen();
                List<KlimatogramDto> kLijst = new ArrayList<>();
                klimatogrammen.stream().forEach(kl->{
                    KlimatogramDto dto  =new KlimatogramDto();
                    dto.setLocatie(kl.getLocatie());
                    kLijst.add(dto);
                });
                vragenOL.add(new VraagDto(VraagDto.DETERMINATIE, kLijst, null, v.getBeschrijving(), v.getTeBehalenPunten()));
            }
               
        });
    }

    public void voegKlasToe(KlasDto klas) {
        klassenVanToets.add(klas);
    }

    public void verwijderKlas(KlasDto klas) {
        klassenVanToets.remove(klas);
    }

    /**
     *
     * @param vraag
     */
    public void voegVraagToe(VraagDto vraag) {
        if(vraag == null){
            throw new IllegalArgumentException("Gelieve eerst een type vraag te selecteren");
        } // Factory implementeren
        if(vraag.isDeterminatieVraag()){
            DeterminatieVraag v = new DeterminatieVraag();
            v.setBeschrijving(vraag.getBeschrijving());
            Klimatogram klim = klimatogramRepository.get(vraag.getKlimatogrammen().get(0).getLocatie());
            v.setKlimatogram(klim);
            v.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            geselecteerdeToets.voegVraagToe(v);
        }
        if(vraag.isGraadDrieVraag()){
            LocatieVraag v = new LocatieVraag();
            v.setBeschrijving(vraag.getBeschrijving());
            HashSet<Klimatogram> klim = new HashSet<>();
            vraag.getKlimatogrammen().stream().forEach(kl->{
                Klimatogram k = klimatogramRepository.get(kl.getLocatie());
                klim.add(k);
            });
            v.setKlimatogrammen(klim);
            v.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            geselecteerdeToets.voegVraagToe(v);
        }
         if(vraag.isGraadEenVraag()){
           LosseVraag v = new LosseVraag();
           v.setSubvragenLijst(vraag.getSubvragen());
           v.setBeschrijving(vraag.getBeschrijving());
           Klimatogram klim = klimatogramRepository.get(vraag.getKlimatogrammen().get(0).getLocatie());
           v.setKlimatogram(klim);
            v.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            geselecteerdeToets.voegVraagToe(v);
        }
       vulVragenOp();
    }

    /**
     *
     * @param vraag
     */
    public void verwijderVraag(VraagDto vraag) {
        if(vraag == null){
            throw new IllegalArgumentException("Gelieve eerst een vraag te selecteren.");
        }
         vulVragenOp();
        
    }

    /**
     *
     * @param vraag
     */
    public void wijzigVraag(VraagDto vraag) {
        // TODO - implement ToetsController.wijzigVraag
         vulVragenOp();
        throw new UnsupportedOperationException();
       
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ObservableList<KlimatogramDto> geefKlimatogrammen() {
        // TODO - implement ToetsController.geefKlimatogrammen
        throw new UnsupportedOperationException();
    }

    public ObservableList<ToetsDto> geefToetsen() {
        // TODO - implement ToetsController.geefToetsen
        throw new UnsupportedOperationException();
    }

}
