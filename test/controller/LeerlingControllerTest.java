package controller;

import domein.*;
import dto.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Pieter-Jan
 */
public class LeerlingControllerTest
{
    @Mock
    Leerling l;
 
    @Test(expected = IllegalArgumentException.class)
    public void maakNieuweLeerlingGooitExceptionBijNull()
    {
        LeerlingController c = new LeerlingController();
        c.maakNieuweLeerling(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void maakNieuweLeerlingGooitExceptionIndienGeenGeselecteerdeGraad()
    {
        LeerlingController c = new LeerlingController();
        c.setGeselecteerdeKlas(new Klas());
        c.maakNieuweLeerling(new LeerlingDto());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void maakNieuweLeerlingGooitExceptionIndienGeenGelecteerdeKlas()
    {
        LeerlingController c = new LeerlingController();
        c.setGeselecteerdeGraad(new Graad());
        c.maakNieuweLeerling(new LeerlingDto());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void maakNieuweKlasGooitExceptionBijNull()
    {
        LeerlingController c = new LeerlingController();
        c.maakNieuweKlas(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void maakNieuweKlasGooitExceptionIndienGeenGeselecteerdeGraad()
    {
        LeerlingController c = new LeerlingController();
        c.maakNieuweKlas(new KlasDto());
    }
    
    @Test
    public void selecteerLeerlingWerkt()
    {
        MockitoAnnotations.initMocks(this);
        Mockito.when(l.getId()).thenReturn(1);
        LeerlingController c = new LeerlingController();
        Graad g = new Graad();
        Klas k = new Klas();
        l = new Leerling();
        k.getLeerlingen().add(l);
        c.setGeselecteerdeGraad(g);
        c.setGeselecteerdeKlas(k);
        c.selecteerLeerling(new LeerlingDto(1, "Ik", "Ben", new KlasDto()));
        Assert.assertEquals(l.getId(), c.getGeselecteerdeLeerling().getId());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void selecteerLeerlingGooitExceptionBijNull()
    {
        LeerlingController c = new LeerlingController();
        c.selecteerLeerling(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void selecteerLeerlingGooitExceptionIndienGeenGeselecteerdeGraad()
    {
        LeerlingController c = new LeerlingController();
        c.selecteerLeerling(new LeerlingDto(1, "Ik", "Ben", new KlasDto()));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void selecteerLeerlingGooitExceptionIndienGeenGelecteerdeKlas()
    {
        LeerlingController c = new LeerlingController();
        c.setGeselecteerdeGraad(new Graad());
        c.selecteerLeerling(new LeerlingDto(1, "Ik", "Ben", new KlasDto()));
    }
    
    @Test
    public void selecteerGraadWerkt()
    {
        LeerlingController c = new LeerlingController();
        c.selecteerGraad(new GraadDto(1, 1, null));
        Assert.assertEquals(1, c.getGeselecteerdeGraad().getGraad());
        Assert.assertEquals(1, c.getGeselecteerdeGraad().getJaar().intValue());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void selecteerGraadGooitExceptionBijNull()
    {
        LeerlingController c = new LeerlingController();
        c.selecteerGraad(null);
    }
    
    @Test
    public void selecteerKlasWerkt()
    {
        LeerlingController c = new LeerlingController();
        Graad g = new Graad();
        c.setGeselecteerdeGraad(g);
        c.selecteerKlas(new KlasDto(1, "klas", 1));
        Assert.assertEquals(1, c.getGeselecteerdeKlas().getId());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void selecteerKlasGooitExceptionBijNull()
    {
        LeerlingController c = new LeerlingController();
        c.selecteerKlas(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void selecteerKlasGooitExceptionIndienGeenGeselecteerdeGraad()
    {
        LeerlingController c = new LeerlingController();
        c.selecteerKlas(new KlasDto());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void wijzigLeerlingGooitExceptionBijNull()
    {
        LeerlingController c = new LeerlingController();
        c.wijzigLeerling(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void wijzigLeerlingGooitExceptionIndienGeenGeselecteerdeGraad()
    {
        LeerlingController c = new LeerlingController();
        c.wijzigLeerling(new LeerlingDto());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void wijzigLeerlingGooitExceptionIndienGeenGelecteerdeKlas()
    {
        LeerlingController c = new LeerlingController();
        c.setGeselecteerdeGraad(new Graad());
        c.wijzigLeerling(new LeerlingDto());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void wijzigLeerlingGooitExceptionIndienGeenGelecteerdeLeerling()
    {
        LeerlingController c = new LeerlingController();
        c.setGeselecteerdeGraad(new Graad());
        c.setGeselecteerdeKlas(new Klas());
        c.wijzigLeerling(new LeerlingDto());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void wijzigKlasGooitExceptionIndienBijNull()
    {
        LeerlingController c = new LeerlingController();
        c.wijzigKlas(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void wijzigKlasGooitExceptionIndienGeenGeselecteerdeGraad()
    {
        LeerlingController c = new LeerlingController();
        c.wijzigKlas(new KlasDto());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void wijzigKlasGooitExceptionIndienGeenGelecteerdeKlas()
    {
        LeerlingController c = new LeerlingController();
        c.setGeselecteerdeKlas(new Klas());
        c.wijzigKlas(new KlasDto());
    }    
    
}