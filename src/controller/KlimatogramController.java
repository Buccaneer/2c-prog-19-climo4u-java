package controller;

import persistentie.*;
import domein.*;
import dto.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KlimatogramController implements Subject{
    
    private List<Observer> observers;
    private GenericDao<Continent,String> continentenRepository;
    protected Continent geselecteerdContinent;
    protected Land geselecteerdLand;
    protected Klimatogram geselecteerdKlimatogram;

    public KlimatogramController() {
        observers=new ArrayList<>();
        continentenRepository = new GenericDaoJpa<>(Continent.class);
    }
    
    /**
     *
     * @param continent
     */
    public void voegContinentToe(ContinentDto continent) {
        if(continent==null){
            throw new IllegalArgumentException("Continent moet correct ingevuld zijn");
        }
        Continent cont = new Continent(continent.getNaam());
        continentenRepository.insert(cont);
    }

    public ObservableList<ContinentDto> getContinenten() {
        List<Continent> continenten = continentenRepository.getAll();
        ObservableList<ContinentDto> continentenDto = FXCollections.observableArrayList();
        for (Continent c : continenten) {
            ContinentDto continent = new ContinentDto();
            continent.setNaam(c.getNaam());
            continentenDto.add(continent);
        }
        return continentenDto;
    }

    /**
     *
     * @param continent
     */
    public void selecteerContinent(ContinentDto continent) {
        if(continent==null || continent.getNaam()==null){
            throw new IllegalArgumentException("Continent moet correct ingevuld zijn");
        }
        Continent cont = continentenRepository.get(continent.getNaam());
        if(cont == null){
            throw new IllegalArgumentException("Continent bestaat niet");
        }
        geselecteerdContinent=cont;
        geselecteerdKlimatogram=null;
        geselecteerdLand=null;
    }

    public ObservableList<LandDto> getLanden() {
        if(geselecteerdContinent==null){
            throw new IllegalArgumentException("Continent moet eerst geselecteerd worden");
        }
        Collection<Land> landen = geselecteerdContinent.getLanden();
        ObservableList<LandDto> landenDto = FXCollections.observableArrayList();
        for(Land l : landen){
            LandDto land = new LandDto();
            land.setNaam(l.getNaam());
            landenDto.add(land);
        }
        return landenDto;
    }
    
    public ObservableList<KlimatogramDto> getLocaties(){
        if(geselecteerdLand == null){
            throw new IllegalArgumentException("Land moet eerst geselecteerd worden");
        }
        Collection<Klimatogram> klimatogrammen = geselecteerdLand.getKlimatogrammen();
        ObservableList<KlimatogramDto> klimatogrammenDto = FXCollections.observableArrayList();
        for(Klimatogram k : klimatogrammen){
            KlimatogramDto klimatogram = new KlimatogramDto();
            klimatogram.setLocatie(k.getLocatie());
            klimatogrammenDto.add(klimatogram);
        }
        return klimatogrammenDto;
    }

    public void selecteerKlimatogram(KlimatogramDto klimatogram){
        if(geselecteerdLand == null){
            throw new IllegalArgumentException("Land moet eerst geselecteerd worden");
        }
        if(klimatogram == null || klimatogram.getLocatie() == null){
            throw new IllegalArgumentException("Locatie moet correct ingevuld zijn");
        }
        Klimatogram klim = geselecteerdLand.getKlimatogrammen().stream().filter(kl->kl.getLocatie().equalsIgnoreCase(klimatogram.getLocatie())).findFirst().get();
        if(klim == null){
            throw new IllegalArgumentException("Klimatogram bestaat niet");
        }
        geselecteerdKlimatogram=klim;
        KlimatogramDto kl = new KlimatogramDto();
        kl.setBeginJaar(geselecteerdKlimatogram.getBeginJaar());
        kl.setEindJaar(geselecteerdKlimatogram.getEindJaar());
        kl.setLatitude(geselecteerdKlimatogram.getLatitude());
        kl.setLongitude(geselecteerdKlimatogram.getLongitude());
        kl.setStation(geselecteerdKlimatogram.getStation());
        List<MaandDto> maanden = new ArrayList<>();
        geselecteerdKlimatogram.getMaanden().stream().forEach(m->{
            MaandDto maand = new MaandDto();
            maand.setNaam(m.getNaam());
            maand.setNeerslag(m.getNeerslag());
            maand.setTemperatuur(m.getTemperatuur());
            maanden.add(maand);
        });
        kl.maanden = maanden;
        notifyObservers(kl);
    }
    
    /**
     *
     * @param land
     */
    public void selecteerLand(LandDto land) {
        if(geselecteerdContinent == null){
            throw new IllegalArgumentException("Continent moet eerst geselecteerd worden");
        }
        if(land==null || land.getNaam() == null){
            throw new IllegalArgumentException("Land moet correct ingevuld zijn");
        }
        Collection<Land> landen = geselecteerdContinent.getLanden();
        Land la = landen.stream().filter(l->l.getNaam().equalsIgnoreCase(land.getNaam())).findFirst().get();
        if(la == null){
            throw new IllegalArgumentException("Land bestaat niet");
        }
        geselecteerdLand=la;
        geselecteerdKlimatogram=null;
    }

    /**
     *
     * @param klimatogram
     */
    public void voegKlimatogramToe(KlimatogramDto klimatogram) {
        if(klimatogram==null){
            throw new IllegalArgumentException("Klimatogram moet correct ingevuld zijn");
        }
        Klimatogram klim = new Klimatogram(klimatogram.getLocatie());
        klim.setBeginJaar(klimatogram.getBeginJaar());
        klim.setEindJaar(klimatogram.getEindJaar());
        klim.setLatitude(klimatogram.getLatitude());
        klim.setLongitude(klimatogram.getLongitude());
        klim.setStation(klimatogram.getStation());
        for (int i = 0; i < 12; i++){
            klim.getMaanden().get(i).setNeerslag(klimatogram.maanden.get(i).getNeerslag());
            klim.getMaanden().get(i).setTemperatuur(klimatogram.maanden.get(i).getTemperatuur());
        }
        geselecteerdLand.voegKlimatogramToe(klim);
        continentenRepository.update(geselecteerdContinent);
        geselecteerdKlimatogram=klim;
    }

    public ObservableList<MaandDto> getMaanden() {
        if(geselecteerdKlimatogram==null){
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
    public void voegLandToe(LandDto land) {
        if(land == null){
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

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer observer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObservers() {
        
    }

    @Override
    public void notifyObservers(Object object) {
        observers.forEach(o->o.update(object));
    }
}
