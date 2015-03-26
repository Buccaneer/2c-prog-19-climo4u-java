package mock;

import domein.*;
import java.util.*;
import org.mockito.*;
import persistentie.GenericDao;

/**
 *
 * @author Jasper De Vrient
 */
public class ContinentDoaMockFactory {
    @Mock
    private GenericDao<Continent, String> continentDao;
    private List<Continent> continenten;
    
    public ContinentDoaMockFactory() {
        continenten = new ArrayList<>();
        Continent c = new Continent("Europa");
        Land l = new Land("België");
        Klimatogram  k = new Klimatogram("Ukkel");
        for (Maand m : k.getMaanden()) {
            m.setNeerslag(15);
            m.setTemperatuur(-5);
        }
        k.setBeginJaar(1995);
        k.setEindJaar(2010);
        k.setLatitude(-4);
        k.setLongitude(4);
        k.setStation("11995");
        l.voegKlimatogramToe(k);
        c.voegLandToe(l);
        continenten.add(c);
    }

    public List<Continent> getContinenten() {
        return continenten;
    }
    
    
    
    public GenericDao<Continent, String> createMock() {
        MockitoAnnotations.initMocks(this);
        Continent c = continenten.get(0);
        Mockito.when(continentDao.getAll()).thenReturn(continenten);
        Mockito.when(continentDao.exists("Europa")).thenReturn(true);
        Mockito.when(continentDao.exists("Amerika")).thenReturn(false);
        Mockito.when(continentDao.get("Europa")).thenReturn(continenten.get(0));
        return continentDao;
    }
}
