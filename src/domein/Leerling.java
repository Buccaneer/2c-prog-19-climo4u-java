package domein;

public class Leerling {

    private Klas klas;
    private int id;
    private String naam;
    private String voornaam;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getId() {
        return id;
    }

    public String getVoornaam() {
        return this.voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public Klas getKlas() {
        return this.klas;
    }

    /**
     *
     * @param klas
     */
    public void setKlas(Klas klas) {
        this.klas = klas;
    }

}
