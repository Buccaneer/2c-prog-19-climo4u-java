package dto;

import java.util.*;

public class ContinentDto {

    private String naam;

    private Map<String,Boolean> graden = new HashMap<>();
    
    public ContinentDto() {
    }

    public ContinentDto(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return this.naam;
    }

    public Map<String, Boolean> getGraden() {
        return graden;
    }
    
    
    public void voegGraadToe(String graad) {
        graden.put(graad,Boolean.TRUE);
    }
    
    public void verwijderGraad(String graad) {
        graden.put(graad, Boolean.FALSE);
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return naam;
    }
    
}
