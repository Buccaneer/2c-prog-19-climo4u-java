package domein;

import org.junit.Test;

/**
 *
 * @author Pieter-Jan
 */
public class LeerlingTest
{
    
    @Test(expected=IllegalArgumentException.class)
    public void setNaamNullGooitException()
    {
        new Leerling().setNaam(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void setVoorNaamNullGooitException()
    {
        new Leerling().setVoornaam(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void setKlasNullGooitException()
    {
        new Leerling().setKlas(null);
    }
    
}
