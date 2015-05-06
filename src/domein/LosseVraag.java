package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ToetsVragen")
public class LosseVraag extends ToetsVraag {

    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Klimatogram klimatogram;

    @ElementCollection
    private List<String> subvragenLijst;

    public LosseVraag() {
        subvragenLijst = new ArrayList<>();
        subvragenLijst.addAll(Arrays.asList(new String[]{"Wat is de warmste maand?",
            "Wat is de temperatuur van de warmste maand in °C (Tw)?",
            "Wat is de koudste maand?",
            "Wat is de temperatuur van de koudste maand in °C (Tk)?",
            "Hoeveel droge maanden zijn er (D)?",
            "Hoeveelheid mm neerslag in de zomer?",
            "Hoeveelheid mm neerslag in de winter?"}));
    }

    public Klimatogram getKlimatogram() {
        return this.klimatogram;
    }

    public void setKlimatogram(Klimatogram klimatogram) {
        if (klimatogram == null)
            throw new IllegalArgumentException();
        this.klimatogram = klimatogram;
    }

    public List<String> getSubvragenLijst() {
        return this.subvragenLijst;
    }

    public void setSubvragenLijst(List<String> subvragenLijst) {
        if (subvragenLijst == null)
            throw new IllegalArgumentException();
        this.subvragenLijst = subvragenLijst;
    }

    /**
     *
     * @param vraag
     */
    public void voegVraagToe(String vraag) {
        if (vraag == null || vraag.equals(""))
            throw new IllegalArgumentException();
        if (subvragenLijst.contains(vraag))
            throw new IllegalArgumentException("Vraag is al toegevoegd.");
        subvragenLijst.add(vraag);
    }

    /**
     *
     * @param vraag
     */
    public void verwijderVraag(String vraag) {
        if (vraag == null || vraag.equals(""))
            throw new IllegalArgumentException();
        if (!subvragenLijst.contains(vraag))
            throw new IllegalArgumentException("Deze vraag behoort niet tot deze toets");
        subvragenLijst.remove(vraag);
    }

}
