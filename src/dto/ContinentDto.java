package dto;

public class ContinentDto {

    private String naam;

    public ContinentDto() {
    }

    public ContinentDto(String naam) {
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
