package domein;

import java.util.*;
import java.util.regex.Pattern;
import javax.persistence.*;

@Entity
@Table(name = "continenten")
public class Continent
{
   
    @OneToMany(mappedBy = "continent",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Land> landen;
    @Column(name="Naam", length = 128)
    @Id
    private String naam;

    public String getNaam()
    {
        return this.naam;
    }

    public void setNaam(String naam)
    {
        if (naam == null)
        {
            throw new IllegalArgumentException("Naam van een continent mag niet null zijn.");
        }
        naam = naam.trim();
        if (naam.isEmpty())
        {
            throw new IllegalArgumentException("Naam van een continent mag niet leeg zijn.");
        }
        if (naam.length() > 40)
        {
            throw new IllegalArgumentException("De naam van een continent mag maximaal 40 tekens bevatten.");
        }
        if (Pattern.compile(".*[^ëa-zA-Z\\s-].*").matcher(naam).matches())
        {
            throw new IllegalArgumentException("De naam van een continent mag enkel letters, spaties en koppeltekens bevatten.");
        }
        this.naam = naam;
    }

    public Continent()
    {
        landen = new LinkedList<>();
        naam = "NIEUW CONTINENT";
    }

    /**
     *
     * @param naam
     */
    public Continent(String naam)
    {
        this();
        setNaam(naam);
    }

    /**
     *
     * @param land
     */
    public void voegLandToe(Land land)
    {
        if (land == null)
        {
            throw new IllegalArgumentException("Land met waarde null kan niet aan continent toegevoegd worden.");
        }
        for (Land l : landen)
        {
            if (l.getNaam().equals(land.getNaam()))
            {
                throw new IllegalArgumentException("Dit land werd reeds toegevoegd aan dit continent.");
            }
        }
        land.setContinent(this);
        landen.add(land);
    }

    public Collection<Land> getLanden()
    {
        return landen;
    }

}
