package mock;

import domein.Toets;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import persistentie.GenericDao;

/**
 *
 * @author Jasper De Vrient
 */
public class ToetsRepositoryMock implements GenericDao<Toets, Integer> {

    private Map<Integer, Toets> items = new HashMap();
    private int ai = 1;

    @Override
    public List<Toets> getAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public void insert(Toets item) {
        item.setId(++ai);
        items.put(ai, item);
    }

    @Override
    public void delete(Toets item) {
        items.remove(item);
    }

    @Override
    public Toets get(Integer id) {
      return items.get(id);
    }

    @Override
    public boolean exists(Integer id) {
      return items.containsKey(id);
    }

    @Override
    public void update(Toets item) {
       items.put(item.getId(), item);
    }

    
    public static ToetsRepositoryMock creerMetEenToetsErin() {
        
        ToetsRepositoryMock trm = new ToetsRepositoryMock();
        
        Toets t = new Toets();
        t.setStartDatumUur(new GregorianCalendar(2015, 04, 21,16,00));
        t.setEindDatumUur(new GregorianCalendar(2015,04,21,18,00));
        t.setTitel("TDD-Test");
        t.setBeschrijving("spatie");
        //t.setNaam("TDD-Test");
        
        trm.insert(t);
        
        return trm;
    }
}
