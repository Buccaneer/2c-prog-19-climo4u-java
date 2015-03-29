package mock;

import domein.*;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Pieter-Jan
 */
public class DomeinMockFactory
{
    @Mock
    Continent c;
    
    @Mock
    Land l;
    
    @Mock
    Klimatogram k;
    
    @Mock
    Maand m;
    
    public Land maakLandMock(String naam, Collection<Klimatogram> klimatogrammen) {
        MockitoAnnotations.initMocks(this);
        l = new Land();
        Mockito.when(l.getKlimatogrammen()).thenReturn(klimatogrammen);
        Mockito.when(l.getNaam()).thenReturn(naam);
        return l;
    }
    
    public Land maakLandMock(String naam)
    {
        return maakLandMock(naam, null);
    }
    
    public Land maakLandMock()
    {
        return maakLandMock("LegoLand");
    }
    
    public Klimatogram maakKlimatogramMock(String locatie, Collection<Maand> maanden)
    {
        MockitoAnnotations.initMocks(this);
        k = new Klimatogram();
        Mockito.when(k.getBeginJaar()).thenReturn(1970);
        Mockito.when(k.getEindJaar()).thenReturn(2000);
        Mockito.when(k.getLatitude()).thenReturn(0.0);
        Mockito.when(k.getLongitude()).thenReturn(0.0);
        Mockito.when(k.getLocatie()).thenReturn(locatie);
        Mockito.when(k.getMaanden()).thenReturn(maanden);
        Mockito.when(k.getStation()).thenReturn("00001");
        return k;
    }
    
    public Klimatogram maakKlimatogramMock(String locatie, int[] neerslagen, double[] temperaturen)
    {
        Collection maanden = new ArrayList();
        for(int i = 0; i < 12; i++)
        {
            maanden.add(maakMaandMock(i+1, neerslagen[i], temperaturen[i]));
        }
        return maakKlimatogramMock(locatie, maanden);
    }
    public Klimatogram maakKlimatogramMock(String locatie)
    {
        return maakKlimatogramMock(locatie, null);
    }
    
    public Klimatogram maakKlimatogramMock()
    {
        return maakKlimatogramMock("Sevastovladimoskvaryazopol");
    }
    
    public Maand maakMaandMock(int maand, int neerslag, double temperatuur)
    {
        MockitoAnnotations.initMocks(this);
        m = new Maand();
        Mockito.when(m.getNaam()).thenReturn(Month.of(maand).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("nl_BE")));
        Mockito.when(m.getNeerslag()).thenReturn(neerslag);
        Mockito.when(m.getTemperatuur()).thenReturn(temperatuur);
        return m;
    }
    
    
}
