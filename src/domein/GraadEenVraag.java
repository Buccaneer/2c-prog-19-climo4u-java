package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraadEenVraag extends ToetsVraag {

    private Klimatogram klimatogram;
    private List<String> subvragenLijst;

    public GraadEenVraag() {
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
        this.klimatogram = klimatogram;
    }

    public List<String> getSubvragenLijst() {
        return this.subvragenLijst;
    }

    public void setSubvragenLijst(List<String> subvragenLijst) {
        this.subvragenLijst = subvragenLijst;
    }

    /**
     *
     * @param vraag
     */
    public void voegVraagToe(String vraag) {
        subvragenLijst.add(vraag);
    }

    /**
     *
     * @param vraag
     */
    public void verwijderVraag(String vraag) {
        subvragenLijst.remove(vraag);
    }

}
