package dto;

public class LeerlingDto {

    private int id;
    private String naam;
    private String voornaam;
    private KlasDto klas;

    public int getId() {
        return id;
    }
    
    

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return this.voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public KlasDto getKlas() {
        return this.klas;
    }

    public void setKlas(KlasDto klas) {
        this.klas = klas;
    }

    public LeerlingDto() {
    }

    public LeerlingDto(int id, String naam, String voornaam, KlasDto klas) {
        this.id = id;
        this.naam = naam;
        this.voornaam = voornaam;
        this.klas = klas;
    }

}
