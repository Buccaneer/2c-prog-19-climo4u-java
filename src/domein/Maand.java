package domein;

public class Maand {

    private int maandId;
    private String naam;
    private int neerslag;
    private double temperatuur;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getNeerslag() {
        return this.neerslag;
    }

    public void setNeerslag(int neerslag) {
        this.neerslag = neerslag;
    }

    public double getTemperatuur() {
        return this.temperatuur;
    }

    public void setTemperatuur(double temperatuur) {
        this.temperatuur = temperatuur;
    }

}
