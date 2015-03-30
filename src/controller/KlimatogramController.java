package controller;

import persistentie.*;
import domein.*;
import dto.*;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KlimatogramController
{

    private GenericDao<Continent, String> continentenRepository;
    protected Continent geselecteerdContinent;
    protected Land geselecteerdLand;
    protected Klimatogram geselecteerdKlimatogram;

    public KlimatogramController()
    {
        continentenRepository = new GenericDaoJpa<>(Continent.class);
    }

    /**
     *
     * @param continent
     */
    public void voegContinentToe(ContinentDto continent) throws IllegalArgumentException
    {
        if (continent == null)
        {
            throw new IllegalArgumentException("Continent moet correct ingevuld zijn");
        }
        Continent cont = new Continent(continent.getNaam());
        continentenRepository.insert(cont);
    }

    public ObservableList<ContinentDto> getContinenten()
    {
        List<Continent> continenten = continentenRepository.getAll();
        ObservableList<ContinentDto> continentenDto = FXCollections.observableArrayList();
        for (Continent c : continenten)
        {
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
    public void selecteerContinent(ContinentDto continent)
    {
        if (continent == null || continent.getNaam() == null)
        {
            throw new IllegalArgumentException("Continent moet correct ingevuld zijn");
        }
        Continent cont = continentenRepository.get(continent.getNaam());
        if (cont == null)
        {
            throw new IllegalArgumentException("Continent bestaat niet");
        }
        geselecteerdContinent = cont;
        geselecteerdKlimatogram = null;
        geselecteerdLand = null;
    }

    public ObservableList<LandDto> getLanden()
    {
        if (geselecteerdContinent == null)
        {
            throw new IllegalArgumentException("Continent moet eerst geselecteerd worden");
        }
        Collection<Land> landen = geselecteerdContinent.getLanden();
        ObservableList<LandDto> landenDto = FXCollections.observableArrayList();
        for (Land l : landen)
        {
            LandDto land = new LandDto();
            land.setNaam(l.getNaam());
            landenDto.add(land);
        }
        return landenDto;
    }

    public ObservableList<KlimatogramDto> getLocaties()
    {
        if (geselecteerdLand == null)
        {
            throw new IllegalArgumentException("Land moet eerst geselecteerd worden");
        }
        Collection<Klimatogram> klimatogrammen = geselecteerdLand.getKlimatogrammen();
        ObservableList<KlimatogramDto> klimatogrammenDto = FXCollections.observableArrayList();
        for (Klimatogram k : klimatogrammen)
        {
            KlimatogramDto klimatogram = new KlimatogramDto();
            klimatogram.setLocatie(k.getLocatie());
            klimatogrammenDto.add(klimatogram);
        }
        return klimatogrammenDto;
    }

    /**
     *
     * @param land
     */
    public void selecteerLand(LandDto land)
    {
        if (geselecteerdContinent == null)
        {
            throw new IllegalArgumentException("Continent moet eerst geselecteerd worden");
        }
        if (land == null || land.getNaam() == null)
        {
            throw new IllegalArgumentException("Land moet correct ingevuld zijn");
        }
        Collection<Land> landen = geselecteerdContinent.getLanden();
        Land la = landen.stream().filter(l -> l.getNaam().equalsIgnoreCase(land.getNaam())).findFirst().get();
        if (la == null)
        {
            throw new IllegalArgumentException("Land bestaat niet");
        }
        geselecteerdLand = la;
        geselecteerdKlimatogram = null;
    }

    /**
     *
     * @param klimatogramDto
     */
    public void voegKlimatogramToe(KlimatogramDto klimatogramDto) throws IllegalArgumentException, VerkeerdeInputException
    {
        if (klimatogramDto == null)
        {
            throw new IllegalArgumentException("Klimatogram moet correct ingevuld zijn");
        }
        VerkeerdeInputException vie = new VerkeerdeInputException();
        Klimatogram klim = new Klimatogram();
        try { klim.setBeginJaar(klimatogramDto.getBeginJaar()); }
        catch(IllegalArgumentException e) { vie.add("beginJaar", e); }
        try { klim.setEindJaar(klimatogramDto.getEindJaar()); }
        catch(IllegalArgumentException e) { vie.add("eindJaar", e); }
        try { klim.setLatitude(klimatogramDto.getLatitude()); }
        catch(IllegalArgumentException e) { vie.add("latitude", e); }
        try { klim.setLongitude(klimatogramDto.getLongitude()); }
        catch(IllegalArgumentException e) { vie.add("longitude", e); }
        try { klim.setLocatie(klimatogramDto.getLocatie()); }
        catch(IllegalArgumentException e) { vie.add("locatie", e); }
        try { klim.setStation(klimatogramDto.getStation()); }
        catch(IllegalArgumentException e) { vie.add("station", e); }
        if (!vie.isEmpty())
            throw vie;
        geselecteerdLand.voegKlimatogramToe(klim);
        continentenRepository.update(geselecteerdContinent);
        geselecteerdKlimatogram = klim;
    }

    public ObservableList<MaandDto> getMaanden()
    {
        if (geselecteerdKlimatogram == null)
        {
            throw new IllegalArgumentException("Klimatogram moet eerst geselecteerd worden");
        }
        Collection<Maand> maanden = geselecteerdKlimatogram.getMaanden();
        ObservableList<MaandDto> maandenDto = FXCollections.observableArrayList();
        for (Maand m : maanden)
        {
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
    public void voegLandToe(LandDto land) throws IllegalArgumentException
    {
        if (land == null)
        {
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
    public void setContinentRepository(GenericDao<Continent, String> continentDao)
    {
        continentenRepository = continentDao;
    }

    public ObservableList<KlimatogramDto> getKlimatogrammen()
    {
        if (geselecteerdLand == null)
        {
            throw new IllegalArgumentException("Land moet eerst geselecteerd worden");
        }
        Collection<Klimatogram> klimatogrammen = geselecteerdLand.getKlimatogrammen();
        ObservableList<KlimatogramDto> klimatogrammenDto = FXCollections.observableArrayList();
        for (Klimatogram k : klimatogrammen)
        {
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

    public void wijzigKlimatogram(KlimatogramDto klimatogramDto) throws IllegalArgumentException, VerkeerdeInputException
    {
        if (geselecteerdKlimatogram == null)
            throw new IllegalArgumentException("Klimatogram moet eerst geselecteerd worden");
        if (klimatogramDto == null)
            throw new IllegalArgumentException("Klimatogram moet correct ingevuld zijn");
        VerkeerdeInputException vie = new VerkeerdeInputException();
        try { geselecteerdKlimatogram.setBeginJaar(klimatogramDto.getBeginJaar()); }
        catch(IllegalArgumentException e) { vie.add("beginJaar", e); }
        try { geselecteerdKlimatogram.setEindJaar(klimatogramDto.getEindJaar()); }
        catch(IllegalArgumentException e) { vie.add("eindJaar", e); }
        try { geselecteerdKlimatogram.setLatitude(klimatogramDto.getLatitude()); }
        catch(IllegalArgumentException e) { vie.add("latitude", e); }
        try { geselecteerdKlimatogram.setLongitude(klimatogramDto.getLongitude()); }
        catch(IllegalArgumentException e) { vie.add("longitude", e); }
        try { geselecteerdKlimatogram.setLocatie(klimatogramDto.getLocatie()); }
        catch(IllegalArgumentException e) { vie.add("locatie", e); }
        try { geselecteerdKlimatogram.setStation(klimatogramDto.getStation()); }
        catch(IllegalArgumentException e) { vie.add("station", e); }
        if (!vie.isEmpty())
            throw vie;    
        continentenRepository.update(geselecteerdContinent);
    }

    public void verwijderKlimatogram(String locatie) throws IllegalArgumentException
    {
        if (geselecteerdKlimatogram == null)
            throw new IllegalArgumentException("Klimatogram moet eerst geselecteerd worden");
        geselecteerdLand.verwijderKlimatogram(locatie);
        continentenRepository.update(geselecteerdContinent);
    }
    
}
