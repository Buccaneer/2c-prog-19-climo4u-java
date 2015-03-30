package controller;

import java.util.*;
import java.util.Map.Entry;

public class VerkeerdeInputException extends IllegalArgumentException
{
    
    private HashMap<String, IllegalArgumentException> exceptions = new HashMap();
    
    public HashMap<String, IllegalArgumentException> getExceptions()
    {
        return exceptions;
    }
    
    public boolean add(String key, IllegalArgumentException exception)
    {
        if (key == null || exception == null)
            return false;
        exceptions.put(key, exception);
        return true;
    }
    
    public IllegalArgumentException get(String key)
    {
        return exceptions.get(key);
    }
    
    public Set<Entry<String, IllegalArgumentException>> getEntries()
    {
        return exceptions.entrySet();
    }
    
    public boolean isEmpty()
    {
        return exceptions.isEmpty();
    }
    
}
