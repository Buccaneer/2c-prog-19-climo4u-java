package persistentietest;

import persistentie.*;
import domein.*;
import java.util.List;

/**
 *
 * @author Jasper De Vrient
 */
public class StartUp {

    public static void main(String[] args) {
        System.out.println("hier");
        GenericDao<Continent, String> dao = new GenericDaoJpa<>(Continent.class);

       
        
        List<Continent> continents = dao.getAll();

        Continent continent =dao.get("Europa");
        
      Continent t = cloneContinent(continent);
      
     dao.delete(continent);
      t.setNaam("boe boe");
      dao.insert(t);
       
       



        GenericDaoJpa.closePersistency();
    }
    
    private static Continent cloneContinent(Continent c) {
        Continent f = new Continent(c.getNaam());
        for (Land l : c.getLanden()) {
            Land ll =cloneLand(l);
            ll.setContinent(f);
            f.voegLandToe(ll);
        }
        return f;
    }
    private static Land cloneLand(Land l) {
        Land land = new Land(l.getNaam());
        for (Klimatogram k : l.getKlimatogrammen())  {
            Klimatogram kk =cloneKlimatogram(k);
            kk.setLand(land);
            land.voegKlimatogramToe(kk);
        }
        return land;
    }
    
    private static Klimatogram cloneKlimatogram(Klimatogram k) {
        Klimatogram kk = new Klimatogram(k.getLocatie());
        kk.setBeginJaar(k.getBeginJaar());
        kk.setEindJaar(k.getEindJaar());
        kk.setLatitude(k.getLatitude());
        kk.setLongitude(k.getLongitude());
        kk.setStation(k.getStation() != null ? k.getStation() : "11111");
        int index = 0;
       
        List<Maand> maanden =(List<Maand>) kk.getMaanden();
        for (Maand m : k.getMaanden()) {
            Maand n = cloneMaand(m,index+ 1);
            n.setKlimatogram(kk);
            maanden.set(index++, n);
            
        }
        return kk;
    }
    
    private static Maand cloneMaand(Maand m, int index) {
        Maand n = new Maand();
        n.setNaam(index);
        n.setNeerslag(m.getNeerslag());
        n.setTemperatuur(m.getTemperatuur());
        return n;
    }
}
