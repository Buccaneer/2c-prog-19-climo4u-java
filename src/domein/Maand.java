package domein;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class Maand {

    private int maandId;
    private String naam;
    private int neerslag;
    private double temperatuur;

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

}
