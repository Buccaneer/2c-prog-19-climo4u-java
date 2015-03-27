package domein;

import java.util.*;
import java.util.regex.Pattern;

public class Klimatogram {

    private Collection<Maand> maanden;
    private int beginJaar;
    private int eindJaar;
    private double latitude;
    private String locatie;
    private double longitude;
    private String station;

    public int getBeginJaar() {
        return this.beginJaar;
    }

    public void setBeginJaar(int beginJaar) {
        int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
        if (beginJaar < 1800 || beginJaar > huidigJaar - 10)
            throw new IllegalArgumentException("Het beginjaar moet tussen 1800 en " + (huidigJaar - 10) + " liggen.");
        this.beginJaar = beginJaar;
    }

    public int getEindJaar() {
        return this.eindJaar;
    }

    public void setEindJaar(int eindJaar) {
        int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
        if (beginJaar < 1810 || beginJaar > huidigJaar)
            throw new IllegalArgumentException("Het beginjaar moet tussen 1810 en " + huidigJaar + " liggen.");
        this.eindJaar = eindJaar;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("De breedtegraad moet tussen -90 en 90 liggen.");
        this.latitude = latitude;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public void setLocatie(String locatie) {
        if (locatie == null)
            throw new IllegalArgumentException("De locatie van een klimatogram mag niet null zijn.");
        locatie = locatie.trim();
        if (locatie.isEmpty())
            throw new IllegalArgumentException("De locatie van een klimatogram mag niet leeg zijn.");
        if (Pattern.compile(".*[^\'áàäâÅçÇëéèêïîíñöóôüûúa-zA-Z -].*").matcher(locatie).matches())
        {
            throw new IllegalArgumentException("De naam van een locatie mag enkel letters, spaties en koppeltekens bevatten.");
        }
        this.locatie = locatie;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("De breedtegraad moet tussen -180 en 180 liggen.");
        this.longitude = longitude;
    }

    public String getStation() {
        return this.station;
    }

    public void setStation(String station) {
        if (station == null)
            throw new IllegalArgumentException("Het station van een klimatogram mag niet null zijn.");
        station = station.trim();
        if (station.isEmpty())
            throw new IllegalArgumentException("Het station van een klimatogram mag niet leeg zijn.");
        if (Pattern.compile(".*[^0-9].*").matcher(station).matches())
        {
            throw new IllegalArgumentException("Een station mag enkel cijfers bevatten.");
        }
        this.station = station;
    }

    public Collection<Maand> getMaanden() {
        return maanden;
    }
    
    public Klimatogram() {
        maanden = new ArrayList();
        for (int i = 1; i <= 12; i++)
        {
            Maand m = new Maand();
            m.setNaam(i);
            maanden.add(m);
        }
    }    
    
    /**
     *
     * @param locatie
     */
    public Klimatogram(String locatie) {
        this();
        setLocatie(locatie);
    }

}
