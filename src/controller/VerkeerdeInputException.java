package controller;

import java.util.*;
import java.util.Map.Entry;

public class VerkeerdeInputException extends IllegalArgumentException
{
    
    private HashMap<String, Exception> exceptions = new HashMap();
    
    public HashMap<String, Exception> getExceptions()
    {
        return exceptions;
    }
    
    public boolean add(String key, Exception exception)
    {
        if (key == null || exception == null)
            return false;
        exceptions.put(key, exception);
        return true;
    }
    
    public Exception get(String key)
    {
        return exceptions.get(key);
    }
    
    public Set<Entry<String, Exception>> getEntries()
    {
        return exceptions.entrySet();
    }
    
    public boolean isEmpty()
    {
        return exceptions.isEmpty();
    }
    
}
