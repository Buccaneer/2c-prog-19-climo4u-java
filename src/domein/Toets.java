package domein;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Toetsen")
public class Toets
{

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
    private GregorianCalendar startDatumUur;
    private GregorianCalendar eindDatumUur;

    public String getTitel()
    {
        return this.titel;
    }

    public void setTitel(String titel)
    {
        this.titel = titel;
    }

    public String getBeschrijving()
    {
        return this.beschrijving;
    }

    public void setBeschrijving(String beschrijving)
    {
        this.beschrijving = beschrijving;
    }

    public GregorianCalendar getStartDatumUur()
    {
        return this.startDatumUur;
    }

    public void setStartDatumUur(GregorianCalendar startDatumUur)
    {
        this.startDatumUur = startDatumUur;
    }

    public GregorianCalendar getEindDatumUur()
    {
        return this.eindDatumUur;
    }

    public void setEindDatumUur(GregorianCalendar eindDatumUur)
    {
        this.eindDatumUur = eindDatumUur;
    }

    /**
     *
     * @param vraag
     */
    public void voegVraagToe(ToetsVraag vraag)
    {
        vragen.add(vraag);
    }

    /**
     *
     * @param vraag
     */
    public void verwijderVraag(ToetsVraag vraag)
    {
        vragen.remove(vraag);
    }

    public List<ToetsVraag> getVragen()
    {
        return vragen;
    }

    public int berekenTotaleScore()
    {
        return vragen.stream().map(ToetsVraag::getTeBehalenPunten).reduce(0, (a, b) -> a + b);
    }

}