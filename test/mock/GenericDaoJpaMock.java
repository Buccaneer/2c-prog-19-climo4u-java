package mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import persistentie.GenericDao;

/**
 *
 * @author Pieter-Jan
 */
public class GenericDaoJpaMock<T, K> implements GenericDao
{
    
    private HashMap<Integer, T> map = new HashMap();

    @Override
    public List getAll()
    {
        List l = new ArrayList();
        l.addAll(map.values());
        return l;
    }

    @Override
    public void insert(Object item)
    {
        int i = new Random().nextInt();
        while (map.containsKey(i))
        {
            i = new Random().nextInt();
        }
        map.put(i, (T) item);
    }

    @Override
    public void delete(Object item)
    {
        map.remove(item);
    }

    @Override
    public Object get(Object id)
    {
        return map.get(id);
    }

    @Override
    public boolean exists(Object id)
    {
        return map.containsKey((K) id);
    }

    @Override
    public void update(Object item)
    {
       
    }

    
    
}
