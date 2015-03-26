package domein;

import java.util.*;

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
        this.beginJaar = beginJaar;
    }

    public int getEindJaar() {
        return this.eindJaar;
    }

    public void setEindJaar(int eindJaar) {
        this.eindJaar = eindJaar;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStation() {
        return this.station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Collection<Maand> getMaanden() {
        return maanden;
    }


    
    public Klimatogram() {
        // TODO - implement Klimatogram.Klimatogram
        throw new UnsupportedOperationException();
    }

    
    
    /**
     *
     * @param locatie
     */
    public Klimatogram(String locatie) {
        // TODO - implement Klimatogram.Klimatogram
        throw new UnsupportedOperationException();
    }

}
