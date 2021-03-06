package domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Leerlingen")
public class Leerling {

    @ManyToOne(optional = true)
    private Klas klas;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String naam;
    private String voornaam;

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        if (naam == null)
            throw new IllegalArgumentException();
        this.naam = naam;
    }

    public int getId() {
        return id;
    }

    public String getVoornaam() {
        return this.voornaam;
    }

    public void setVoornaam(String voornaam) {
        if (voornaam == null)
            throw new IllegalArgumentException();
        this.voornaam = voornaam;
    }

    public Klas getKlas() {
        return this.klas;
    }

    /**
     *
     * @param klas
     */
    public void setKlas(Klas klas) {
        if (klas == null)
            throw new IllegalArgumentException();
        this.klas = klas;
    }

}
