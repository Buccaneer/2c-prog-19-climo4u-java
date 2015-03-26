package domein;

import java.util.*;

public class Continent {

    private Collection<Land> landen;
    private String naam;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Continent() {
        // TODO - implement Continent.Continent
    }

    /**
     *
     * @param naam
     */
    public Continent(String naam) {
        // TODO - implement Continent.Continent
    }

    /**
     *
     * @param land
     */
    public void voegLandToe(Land land) {
        // TODO - implement Continent.voegLandToe
        throw new UnsupportedOperationException();
    }

    public Collection<Land> getLanden() {
        return landen;
    }

}
