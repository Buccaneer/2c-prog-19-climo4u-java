package controller;

import persistentie.*;
import domein.*;
import dto.*;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KlimatogramController {

    private GenericDao<Continent,String> continentenRepository;
    protected Continent geselecteerdContinent;
    protected Land geselecteerdLand;
    protected Klimatogram geselecteerdKlimatogram;

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

}
