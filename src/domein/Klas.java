package domein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity()
@Table(name = "Klassen")
public class Klas {

    @JoinColumns({
        @JoinColumn(name = "Nummer", referencedColumnName = "Nummer", nullable = false),
        @JoinColumn(name = "Jaar", referencedColumnName = "Jaar", nullable = true)})
    @ManyToOne(optional = false)

    private Graad graad;

    @OneToMany(mappedBy
            = "klas", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<Leerling> leerlingen = new TreeSet<>(new Comparator<Leerling>() {
        @Override
        public int compare(Leerling o1, Leerling o2) {
            int naam = o1.getNaam().compareTo(o2.getNaam());
            if (naam == 0)
                return o1.getVoornaam().compareTo(o2.getVoornaam());
            return naam;
        }
    });

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "klassen")
    private List<Toets> toetsen;

    public Klas(int id, String naam, int leerjaar) {
        this.id = id;
        this.naam = naam;
        this.leerjaar = leerjaar;
        toetsen = new ArrayList<>();
    }

    public Klas() {
        toetsen = new ArrayList<>();
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
        if (leerling == null)
            throw new IllegalArgumentException();
        leerlingen.add(leerling);
    }

    public void verwijderLeerling(Leerling leerling) {
        if (leerling == null)
            throw new IllegalArgumentException();
        leerlingen.remove(leerling);
    }

    public Collection<Leerling> getLeerlingen() {
        return leerlingen;
    }

    public List<Toets> getToetsen() {
        return toetsen;
    }

    public void voegToetsToe(Toets toets) {
        if (toets == null)
            throw new IllegalArgumentException();
        toetsen.add(toets);
    }

    public boolean verwijderToets(Toets o) {
        return toetsen.remove(o);
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
