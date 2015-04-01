package domein;

import java.util.*;
import java.util.regex.Pattern;
import javax.persistence.*;
import persistentie.GenericDaoJpa;

@Entity
@Table(name = "landen")
public class Land implements Cloneable
{

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "land")
    private List<Klimatogram> klimatogrammen;

    @JoinColumn(name = "Continent_Naam")
    @ManyToOne(optional = true) // Fout in db dotnet maar goed.
    private Continent continent;
    @Column(name = "Naam", length = 40)
    @Id
    private String naam;

    public String getNaam()
    {
        return this.naam;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
           Land land = new Land(naam);
        for (Klimatogram k : klimatogrammen)  {
            Klimatogram kk =(Klimatogram)k.clone();
            kk.setLand(land);
            land.voegKlimatogramToe(kk);
        }
        return land;
    }

    
    
    public void setNaam(String naam)
    {
        if (naam == null)
        {
            throw new IllegalArgumentException("De locatie van een klimatogram mag niet null zijn.");
        }
        naam = naam.trim();
        if (naam.isEmpty())
        {
            throw new IllegalArgumentException("De locatie van een klimatogram mag niet leeg zijn.");
        }
        if (naam.length() > 40)
        {
            throw new IllegalArgumentException("De naam van een land mag maximaal 40 tekens bevatten.");
        }
        if (Pattern.compile(".*[^\'áàäâÅçÇëéèêïîíñöóôüûúa-zA-Z -].*").matcher(naam).matches())
        {
            throw new IllegalArgumentException("De naam van een locatie mag enkel letters, spaties en koppeltekens bevatten.");
        }
        this.naam = naam;
    }

    public Land()
    {
        klimatogrammen = new LinkedList<>();
    }

    /**
     *
     * @param naam
     */
    public Land(String naam)
    {
        this();
        setNaam(naam);
    }

     void setContinent(Continent continent)
    {
        this.continent = continent;
    }

    /**
     *
     * @param klimatogram
     */
    public void voegKlimatogramToe(Klimatogram klimatogram)
    {
        if (klimatogram == null)
        {
            throw new IllegalArgumentException("Klimatogram met waarde null kan niet aan land toegevoegd worden.");
        }
        for (Klimatogram k : klimatogrammen)
        {
            if (k.getLocatie().equals(klimatogram.getLocatie()))
            {
                throw new IllegalArgumentException("Dit klimatogram werd reeds toegevoegd aan dit land.");
            }
        }
        klimatogram.setLand(this);
        klimatogrammen.add(klimatogram);
    }

    public Klimatogram verwijderKlimatogram(String locatie)
    {
        if (locatie == null)
        {
            throw new IllegalArgumentException("Geldige locatie moet meegegeven worden om te kunnen verwijderen");
        }
        Iterator<Klimatogram> iterator = klimatogrammen.iterator();
        while (iterator.hasNext())
        {
            Klimatogram k = iterator.next();
            if (k.getLocatie().equals(locatie))
            {
                iterator.remove();
                return k;
            
            } 
        }
return null;
    }

    public Collection<Klimatogram> getKlimatogrammen()
    {
        return klimatogrammen;
    }

}
