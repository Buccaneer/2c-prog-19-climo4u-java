package domein;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Toetsen")
public class Toets {

    @ManyToMany(mappedBy = "toetsen")
    private List<Klas> klassen;

    @OneToMany
    private List<ToetsVraag> vragen;

    @OneToOne
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
        if(titel == null || titel.isEmpty())
            throw new IllegalArgumentException("Gelieve een titel in te vullen.");
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
        if(startDatumUur.before(GregorianCalendar.getInstance()))
            throw new IllegalArgumentException("De startdatum/uur kan niet voor vandaag liggen");
        this.startDatumUur = startDatumUur;
    }

    public GregorianCalendar getEindDatumUur() {
        return this.eindDatumUur;
    }

    public void setEindDatumUur(GregorianCalendar eindDatumUur) {
        if(eindDatumUur.before(startDatumUur))
            throw new IllegalArgumentException("De einddatum/uur kan niet voor de startdatum/uur liggen.");
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

}
