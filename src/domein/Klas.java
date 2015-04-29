package domein;

import java.util.*;
import javax.persistence.*;

@Entity()
@Table(name = "Klassen")
public class Klas {
    
    @JoinColumns({@JoinColumn(name="Nummer", referencedColumnName="Nummer", nullable = false), 
                    @JoinColumn(name="Jaar",referencedColumnName = "Jaar",nullable = true)})
    @ManyToOne(optional = false)
    
    private Graad graad;
    
    @OneToMany(mappedBy = 
            "klas",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private Collection<Leerling> leerlingen = new TreeSet<>(new Comparator<Leerling>() {
        @Override
        public int compare(Leerling o1, Leerling o2) {
            int naam = o1.getNaam().compareTo(o2.getNaam());
            if (naam == 0)
                return o1.getVoornaam().compareTo(o2.getVoornaam());
            return naam;
        }
    });

    public Klas(int id, String naam, int leerjaar) {
        this.id = id;
        this.naam = naam;
        this.leerjaar = leerjaar;
    }

    public Klas() {
    }
    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
  
    private String naam;
    private int leerjaar;

    public int getId() {
        return id;
    }
    
    
    
    public String getNaam() {
        return this.naam;
    }
    
    public void setNaam(String naam) {
        if (naam == null)
            throw new IllegalArgumentException();
        this.naam = naam;
    }
    
    public int getLeerjaar() {
        return this.leerjaar;
    }
    
    public void setLeerjaar(int leerjaar) {
        if (leerjaar < 0)
            throw new IllegalArgumentException();
        this.leerjaar = leerjaar;
    }

    /**
     *
     * @param leerling
     */
    public void voegLeerlingToe(Leerling leerling) {
        leerlingen.add(leerling);
    }
    
    public void verwijderLeerling(Leerling leerling) {
        leerlingen.remove(leerling);
    }
    
    public Collection<Leerling> getLeerlingen() {
        return leerlingen;
    }
    
    public Graad getGraad() {
        return this.graad;
    }

    /**
     *
     * @param graad
     */
    public void setGraad(Graad graad) {
        if (graad == null)
            throw new IllegalArgumentException();
        this.graad = graad;
    }
    
}
