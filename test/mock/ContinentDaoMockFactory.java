package mock;

import domein.Continent;
import domein.Graad;
import domein.Klimatogram;
import domein.Land;
import domein.Maand;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import persistentie.GenericDao;

/**
 *
 * @author Jasper De Vrient
 */
public class ContinentDaoMockFactory {

    @Mock
    private GenericDao<Continent, String> continentDao;
    private List<Continent> continenten;

    public ContinentDaoMockFactory() {
        continenten = new ArrayList<>();
        Continent c = new Continent("Europa");
        Land l = new Land("België");
        Klimatogram k = new Klimatogram("Ukkel");
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

    public void setGraad(Graad g) {
        continenten.forEach((Continent c) -> c.voegGraadToe(g));
    }

    public GenericDao<Continent, String> createMock() {
        MockitoAnnotations.initMocks(this);
        Continent c = continenten.get(0);
        Mockito.when(continentDao.getAll()).thenReturn(continenten);
        Mockito.when(continentDao.exists("Europa")).thenReturn(true);
        Mockito.when(continentDao.exists("Amerika")).thenReturn(false);
        Mockito.when(continentDao.get("Europa")).thenReturn(c);
        return continentDao;
    }

}
