package domein;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.persistence.*;

@Entity
@Table(name = "maanden")
public class Maand {

    @Column(name = "MaandId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int maandId;
    
    @Column(name="Naam", length = 15, nullable = false)
    private String naam;
    @Column(name="Neerslag", nullable =false)
    private int neerslag;
    @Column(name="Temperatuur", nullable= false)
    private double temperatuur;
   
    @JoinColumn(name="Klimatogram"
            + "_Locatie",nullable = false)
    @ManyToOne(optional = false)
    private Klimatogram klimatogram;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(int maand) {
        if (maand < 1 || maand > 12)
            throw new IllegalArgumentException("Het nummer van een maand moet tussen 1 en 12 liggen.");
        naam = Month.of(maand).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("nl_BE"));
    }

    public int getNeerslag() {
        return this.neerslag;
    }

    public void setNeerslag(int neerslag) {
        if (neerslag < 0) throw new IllegalArgumentException("Neerslag kan niet negatief zijn.");
        this.neerslag = neerslag;
    }

    public double getTemperatuur() {
        return this.temperatuur;
    }

    public void setTemperatuur(double temperatuur) {
        if (temperatuur <= -273.15) throw new IllegalArgumentException("Temperatuur hoger dan -273.15 °C zijn.");
        this.temperatuur = temperatuur;
    }

   public void setKlimatogram(Klimatogram aThis) {
      klimatogram = aThis;
    }

}
