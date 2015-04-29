package mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import persistentie.GenericDao;

/**
 *
 * @author Jasper De Vrient
 */
public class GenericDaoMockGeneric<T,K> implements GenericDao<T, K>{
private HashMap<K,T> _items = new HashMap<>();
    
    @Override
    public List<T> getAll() {
      List<T> l = new ArrayList<>();
      for (T item : _items.values())
          l.add(item);
      return l;
    }

    @Override
    public void insert(T item) {
    
    }

    public T insert(K key, T value) {
        return _items.put(key, value);
    }
    
    

    @Override
    public void delete(T item) {
       
    }

    @Override
    public T get(K id) {
        if (!exists(id))
            return null;
       return _items.get(id);
    }

    @Override
    public boolean exists(K id) {
       return _items.containsKey(id);
    }

    @Override
    public void update(T item) {
    
    }
    
    

}
