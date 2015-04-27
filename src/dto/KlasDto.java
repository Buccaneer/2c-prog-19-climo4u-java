package dto;

public class KlasDto {

    private int id;
    private String naam;
    private int leerjaar;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getLeerjaar() {
        return this.leerjaar;
    }

    public void setLeerjaar(int leerjaar) {
        this.leerjaar = leerjaar;
    }

    public KlasDto() {
    }

    public KlasDto(int id, String naam, int leerjaar) {
        this.id = id;
        this.naam = naam;
        this.leerjaar = leerjaar;
    }

    @Override
    public String toString() {
        return naam + " jaar " + leerjaar;
    }
}
