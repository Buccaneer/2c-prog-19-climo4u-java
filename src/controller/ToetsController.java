package controller;

import domein.*;
import dto.*;
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

    private GenericDao<Toets, Integer> toetsrepository = new GenericDaoJpa<>(Toets.class);
    private GenericDao<Continent, String> continentrepository = new GenericDaoJpa<>(Continent.class);
    private GenericDao<Graad, Integer> graadrepository =new GenericDaoJpa<>(Graad.class);

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
        // TODO - implement ToetsController.verwijderToets
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param toets
     */
    public void wijzigToets(ToetsDto toets) {
        // TODO - implement ToetsController.wijzigToets
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param toets
     */
    public void selecteerToets(ToetsDto toets) {
        // TODO - implement ToetsController.selecteerToets
        throw new UnsupportedOperationException();
    }

    public ObservableList<GraadDto> geefAlleGraden() {
        // TODO - implement ToetsController.geefAlleGraden
        throw new UnsupportedOperationException();
    }

    public ObservableList<KlasDto> geefAlleKlassen() {
        // TODO - implement ToetsController.geefAlleKlassen
        throw new UnsupportedOperationException();
    }

    public ObservableList<KlasDto> geefKlassenVanToets() {
        // TODO - implement ToetsController.geefKlassenVanToets
        throw new UnsupportedOperationException();
    }

    public ObservableList<VraagDto> geefVragen() {
        // TODO - implement ToetsController.geefVragen
        throw new UnsupportedOperationException();
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
        // TODO - implement ToetsController.voegVraagToe
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param vraag
     */
    public void verwijderVraag(VraagDto vraag) {
        // TODO - implement ToetsController.verwijderVraag
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param vraag
     */
    public void wijzigVraag(VraagDto vraag) {
        // TODO - implement ToetsController.wijzigVraag
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
