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
    private GenericDao<DeterminatieTabel, String> determinatieTabelRepository = new GenericDaoJpa<>(DeterminatieTabel.class);
    private GenericDao<DeterminatieKnoop, String> determinatieKnoopRepository = new GenericDaoJpa<>(DeterminatieKnoop.class);
    private GenericDao<Graad, String> graadRepository = new GenericDaoJpa<>(Graad.class);
    private List<Observer> observers = new ArrayList<>();
    
    

    public ObservableList<GraadDto> getGraden() {
        //TODO: graaddto nog niet gemaakt?
        List<Graad> graad = graadRepository.getAll();
        graad.stream().forEach(g -> graden.add(new GraadDto()));
        return graden;
    }

//    /**
//     *
//     * @param graad
//     */
//    public void selecteerGraad(GraadDto graad) {
//        // TODO - implement DeterminatieController.selecteerGraad
//        throw new UnsupportedOperationException();
//    }
    public ObservableList<DeterminatieTabelDto> getDeterminatieTabellen() {
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
        notifyObservers("", geselecteerdeDeterminatieTabel.maakDtoAan());
    }

    public void setNaamDeterminatieTabel(String naam) {
        geselecteerdeDeterminatieTabel.setNaam(naam);
        notifyObservers("", geselecteerdeDeterminatieTabel.maakDtoAan());
    }
    
    /**
     *
     * @param tabel
     */
    public void verwijderDeterminatieTabel(DeterminatieTabelDto tabel) {
        if(tabel == null){
            throw new IllegalArgumentException("U moet eerst een determinatietabel selecteren");
        }
        DeterminatieTabel t = determinatieTabelRepository.get(tabel.getNaam());
        
        determinatieTabelRepository.delete(t);
        geselecteerdeDeterminatieTabel = null;
        determinatietabellen.clear();
        getDeterminatieTabellen();
    }

    /**
     *
     * @param tabel
     */
    public void selecteerDeterminatieTabel(DeterminatieTabelDto tabel) {
        if(tabel==null){
            throw new IllegalArgumentException();
        }
        //remove try catch after testing
        try
        {
            geselecteerdeDeterminatieTabel= determinatieTabelRepository.get(tabel.getNaam());
        notifyObservers("", geselecteerdeDeterminatieTabel.maakDtoAan());
        } catch (Exception e)
        {
            //Go fuck yourself exception
        }
        
    }

    /**
     * Voert de wijzigingen die gebeurd zijn aan determinatietabel door naar db.
     *
     * @param tabel
     */
    public void wijzigDeterminatieTabel(DeterminatieTabelDto tabel) {
        if(tabel == null){
            throw new IllegalArgumentException("U moet eerst een determinatietabel selecteren");
        }
    }

    /**
     *
     * @param graad
     * @param tabel
     */
    public void koppelGraadMetDeterminatieTabel(GraadDto graad, DeterminatieTabelDto tabel) {
        if(graad == null){
            throw new IllegalArgumentException("U moet eerst een graad selecteren");
        }
        if(tabel == null){
            throw new IllegalArgumentException("U moet eerst een determinatietabel selecteren");
        }
        //TODO: dto maken
       // Graad gr = graadRepository.get(graad.getNaam());
        DeterminatieTabel tab = determinatieTabelRepository.get(tabel.getNaam());
       // gr.setActieveTabel(tab);
    }

//    /**
//     * Wijzigt de meegegeven knoop in een beslissingsknoop en voegt twee 
//     * nieuwe resultaatknopen als kinderen toe.
//     * 
//     * @param ouder
//     */
//    public void voegKnoopToe(DeterminatieKnoopDto ouder)
//    {
//        throw new UnsupportedOperationException();
//    }
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
        geselecteerdeDeterminatieTabel.wijzigKnoop(knoop);
         notifyObservers("", geselecteerdeDeterminatieTabel.maakDtoAan());
    }

//    /**
//     * Probeert de meegegeven knoop te verwijderen. Heeft 3 mogelijke gevallen.
//     * <p>
//     * 1. Indien het een blad is worden de knoop en zijn broer (of zus?) verwijderd en
//     * wordt de ouder een blad.</p>
//     * <p>
//     * 2. Indien het een tussenknoop is worden de kinderen verwijderd en wordt
//     * de tussenknoop een blad.</p>
//     * <p>
//     * 3. Indien het de beginknoop is wordt de tabel verwijderd en wordt opnieuw
//     * begonnen op dezelfde manier als bij een nieuwe tabel.
//     *</p>
//     * @param knoop
//     */
//    public void verwijderKnoop(DeterminatieKnoopDto knoop)
//    {
//       
//        throw new UnsupportedOperationException();
//    }
    /**
     * Valideert of de determinatietabel in orde is. Indien dit niet het geval
     * is wordt er een exception gegooid.
     */
    public void valideer() {
        try{
            geselecteerdeDeterminatieTabel.valideer();
        }
        catch(IllegalArgumentException e){
            throw e;
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String actie, Object object) {
        observers.forEach(o->o.update(actie,object));
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    // --- Methodes voor de testen ---
    protected void setDeterminatieTabelRepository(GenericDao<DeterminatieTabel, String> determinatieTabelRepository)
    {
        this.determinatieTabelRepository = determinatieTabelRepository;
    }
    
    protected DeterminatieTabel getGeselecteerdeDeterminatieTabel()
    {
        return geselecteerdeDeterminatieTabel;
    }

}
