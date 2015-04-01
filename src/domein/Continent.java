package domein;

import java.util.*;
import java.util.regex.Pattern;
import javax.persistence.*;

@Entity
@Table(name = "continenten")
public class Continent implements Cloneable
{
   
    @OneToMany(mappedBy = "continent",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Land> landen;
    @Column(name="Naam", length = 128)
    @Id
    private String naam;
    
    @JoinTable(name= "GraadContinent", joinColumns = {
        @JoinColumn(name="Continent_Naam", referencedColumnName="Naam")
    },
            
            inverseJoinColumns=
                    {@JoinColumn(name="Graad_Nummer", referencedColumnName="Nummer"), 
                    @JoinColumn(name="Graad_Jaar",referencedColumnName = "Jaar")}
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Graad> graden;

    public String getNaam()
    {
        return this.naam;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
           Continent f = new Continent(naam);
        for (Land l : landen) {
            Land ll =(Land) l.clone();
            ll.setContinent(f);
            f.voegLandToe(ll);
        }
        return f;
    }

    public List<Graad> getGraden() {
        return graden;
    }
    
    public void voegGraadToe(Graad graad) {
        graden.add(graad);
    }
    
    public void verwijderGraad(Graad graad) {
        ListIterator<Graad> gi = graden.listIterator();
        
        while (gi.hasNext()) {
            Graad g = gi.next();
            if (g.equals(graad)) {
                gi.remove();
                
            }
        }
        
    }

    public void setGraden(List<Graad> graden) {
        this.graden = graden;
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
        graden = new  LinkedList<>();
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

    public List<Land> getLanden()
    {
        return landen;
    }

    public void verwijderLand(String naam) {
        Land land = null;
       for (Land l : landen) {
           if (l.getNaam().equals(naam)){
               land = l;
               break;
           }
       }
       if (land !=null) {
           landen.remove(land);
       }
    }

}
