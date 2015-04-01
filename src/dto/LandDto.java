package dto;

public class LandDto {

    private String naam;

    public LandDto() {
    }

    public LandDto(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return naam;
    }

}
