package domein;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Toetsen")
public class Toets implements Valideerbaar {

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Klas> klassen;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ToetsVraag> vragen;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Graad graad;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titel;
    private String beschrijving;
    @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar startDatumUur;
    @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar eindDatumUur;

    public Toets() {
        vragen = new ArrayList<>();
        klassen = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Graad getGraad() {
        return graad;
    }

    public void setGraad(Graad graad) {
        this.graad = graad;
    }

    public List<Klas> getKlassen() {
        return klassen;
    }

    public void voegKlasToe(Klas klas) {
        if (klas == null) {
            throw new IllegalArgumentException();
        }
        if (klassen.contains(klas)) {
            throw new IllegalArgumentException("Deze klas is al gekoppeld aan deze toets.");
        }
        klassen.add(klas);
        klas.voegToetsToe(this);
    }

    public void verwijderKlas(Klas klas) {
        if (klas == null) {
            throw new IllegalArgumentException();
        }
        if (!klassen.contains(klas)) {
            throw new IllegalArgumentException("Deze klas bevint zich niet bij deze toets.");
        }
        klassen.remove(klas);
    }

    public String getTitel() {
        return this.titel;
    }

    public void setTitel(String titel) {
        if (titel == null || titel.isEmpty()) {
            throw new IllegalArgumentException("Gelieve een titel in te vullen.");
        }
        this.titel = titel;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public GregorianCalendar getStartDatumUur() {
        return this.startDatumUur;
    }

    public void setStartDatumUur(GregorianCalendar startDatumUur) {
        this.startDatumUur = startDatumUur;
    }

    public GregorianCalendar getEindDatumUur() {
        return this.eindDatumUur;
    }

    public void setEindDatumUur(GregorianCalendar eindDatumUur) {
        this.eindDatumUur = eindDatumUur;
    }

    /**
     *
     * @param vraag
     */
    public void voegVraagToe(ToetsVraag vraag) {
        if (vraag == null) {
            throw new IllegalArgumentException();
        }
        if (vragen.contains(vraag)) {
            throw new IllegalArgumentException("Deze vraag behoort al tot de toets.");
        }
        vragen.add(vraag);
    }

    /**
     *
     * @param vraag
     */
    public void verwijderVraag(ToetsVraag vraag) {
        if (vraag == null) {
            throw new IllegalArgumentException();
        }
        if (!vragen.contains(vraag)) {
            throw new IllegalArgumentException("Deze vraag hoort niet bij deze toets.");
        }
        vragen.remove(vraag);
    }

    public List<ToetsVraag> getVragen() {
        return vragen;
    }

    public int berekenTotaleScore() {
        return vragen.stream().map(ToetsVraag::getTeBehalenPunten).reduce(0, (a, b) -> a + b);
    }

    @Override
    public void valideer() {
        IllegalArgumentException exception = new IllegalArgumentException("Niet alle data van de toets is ingevuld");
        if (graad == null) {
            throw exception;
        }
        if (vragen.isEmpty()) {
            throw exception;
        }
        if (titel == null || titel.isEmpty()) {
            throw exception;
        }
        for (ToetsVraag vraag : vragen) {
            if (vraag.getBeschrijving() == null || vraag.getBeschrijving().isEmpty()) {
                throw exception;
            }
            if (vraag.getTeBehalenPunten() == 0) {
                throw exception;
            }
            if (vraag instanceof LosseVraag) {
                LosseVraag vr = (LosseVraag) vraag;
                if (vr.getSubvragenLijst().isEmpty()) {
                    throw exception;
                }
                if (vr.getKlimatogram() == null) {
                    throw exception;
                }
            }
            if (vraag instanceof DeterminatieVraag) {
                DeterminatieVraag vr = (DeterminatieVraag) vraag;
                if (vr.getKlimatogram() == null) {
                    throw exception;
                }
            }
            if (vraag instanceof LocatieVraag) {
                LocatieVraag vr = (LocatieVraag) vraag;
                if (vr.getKlimatogrammen() == null || vr.getKlimatogrammen().isEmpty()) {
                    throw exception;
                }
            }
        }
    }

}
