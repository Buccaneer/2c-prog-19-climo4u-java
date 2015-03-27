package domein;

import java.util.*;
import java.util.regex.Pattern;

public class Land {

    private Collection<Klimatogram> klimatogrammen;
    private String naam;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        if (naam == null)
            throw new IllegalArgumentException("De locatie van een klimatogram mag niet null zijn.");
        naam = naam.trim();
        if (naam.isEmpty())
            throw new IllegalArgumentException("De locatie van een klimatogram mag niet leeg zijn.");
        if (Pattern.compile(".*[^\'áàäâÅçÇëéèêïîíñöóôüûúa-zA-Z -].*").matcher(naam).matches())
        {
            throw new IllegalArgumentException("De naam van een locatie mag enkel letters, spaties en koppeltekens bevatten.");
        }
        this.naam = naam;
    }

    public Land() {
        klimatogrammen = new LinkedList<>();
    }

    /**
     *
     * @param naam
     */
    public Land(String naam) {
        this();
        setNaam(naam);
    }

    /**
     *
     * @param klimatogram
     */
    public void voegKlimatogramToe(Klimatogram klimatogram) {
        if (klimatogram == null)
        {
            throw new IllegalArgumentException("Klimatogram met waarde null kan niet aan land toegevoegd worden.");
        }
        for (Klimatogram k : klimatogrammen)
        {
            if (k.getLocatie().equals(klimatogram.getLocatie()))
            {
                throw new IllegalArgumentException("Dit klimatogram werd reeds toegevoegd aan dit land.");
            }
        }
        klimatogrammen.add(klimatogram);
    }

    public Collection<Klimatogram> getKlimatogrammen() {
        return klimatogrammen;
    }

}
