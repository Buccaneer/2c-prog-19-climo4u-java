package domein;

import java.util.*;
import java.util.regex.Pattern;
import javax.persistence.*;

@Entity
@Table(name = "klimatogrammen")
public class Klimatogram implements Cloneable {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "klimatogram")
    private List<Maand> maanden;

    @JoinColumn(name = "Land_Naam", nullable = false)
    @ManyToOne(optional = false)
    private Land land;
    @Column(name = "BeginJaar", nullable = false)
    private int beginJaar;
    @Column(name = "EindJaar", nullable = false)
    private int eindJaar;
    @Column(name = "Latitude")
    private double latitude;
    @Column(name = "Locatie", length = 40)
    @Id
    private String locatie;
    @Column(name = "Longitute")
    private double longitude;
    @Column(name = "Station", length = 10)
    private String station;

    public int getBeginJaar() {
        return this.beginJaar;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Klimatogram kk = new Klimatogram(locatie);
        kk.beginJaar = (beginJaar);
        kk.eindJaar = (eindJaar);
        kk.latitude = (latitude);
        kk.longitude = (longitude);
        kk.station = station;
        int index = 0;

        List<Maand> maanden = (List<Maand>) kk.getMaanden();
        for (Maand m : maanden) {
            Maand n = (Maand) m.clone();
            n.setKlimatogram(kk);
            maanden.set(index++, n);
        }
        return kk;
    }

    public void setBeginJaar(int beginJaar) {
        int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
        if (beginJaar < 1800 || beginJaar > huidigJaar - 10) {
            throw new IllegalArgumentException("Het beginjaar moet tussen 1800 en " + (huidigJaar - 10) + " liggen.");
        }
        this.beginJaar = beginJaar;
    }

    void setLand(Land land) {
        this.land = land;
    }

    public int getEindJaar() {
        return this.eindJaar;
    }

    public void setEindJaar(int eindJaar) {
        int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
        if (beginJaar < 1810 || beginJaar > huidigJaar) {
            throw new IllegalArgumentException("Het beginjaar moet tussen 1810 en " + huidigJaar + " liggen.");
        }
        this.eindJaar = eindJaar;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("De breedtegraad moet tussen -90 en 90 liggen.");
        }
        this.latitude = latitude;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public void setLocatie(String locatie) {
        if (locatie == null) {
            throw new IllegalArgumentException("De locatie van een klimatogram mag niet null zijn.");
        }
        locatie = locatie.trim();
        if (locatie.isEmpty()) {
            throw new IllegalArgumentException("De locatie van een klimatogram mag niet leeg zijn.");
        }
        if (locatie.length() > 40) {
            throw new IllegalArgumentException("De naam van een locatie mag maximaal 40 tekens bevatten.");
        }
        if (Pattern.compile(".*[^\'áàäâÅçÇëéèêïîíñöóôüûúa-zA-Z ,-].*").matcher(locatie).matches()) {
            throw new IllegalArgumentException("De naam van een locatie mag enkel letters, kommas, apostrofes, spaties en koppeltekens bevatten.");
        }
        this.locatie = locatie;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("De breedtegraad moet tussen -180 en 180 liggen.");
        }
        this.longitude = longitude;
    }

    public String getStation() {
        return this.station;
    }

    public void setStation(String station) {
        //if (station == null)
        //    throw new IllegalArgumentException("Het station van een klimatogram mag niet null zijn.");
        if (station != null) {
            station = station.trim();
            if (station.isEmpty()) {
                this.station = null;
            }
            if (station.length() > 5) {
                throw new IllegalArgumentException("Het station van een klimatogram mag maximaal 5 tekens bevatten.");
            }
            if (Pattern.compile(".*[^0-9].*").matcher(station).matches()) {
                throw new IllegalArgumentException("Een station mag enkel cijfers bevatten.");
            }
        }
        else{
            this.station = "";
        }
    }

    public List<Maand> getMaanden() {
        return maanden;
    }

    public Klimatogram() {
        maanden = new ArrayList();
        for (int i = 1; i <= 12; i++) {
            Maand m = new Maand();
            m.setKlimatogram(this);
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
