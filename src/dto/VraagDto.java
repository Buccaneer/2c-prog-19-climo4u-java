package dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VraagDto {

    private int type;
    private int id;
    public static final int GRAADEEN = 0;
    public static final int DETERMINATIE = 1;
    public static final int GRAADDRIE = 2;
    private List<KlimatogramDto> klimatogrammen;
    private List<String> subvragen;
    private String beschrijving;
    private int puntenTeVerdienen;

    public VraagDto() {
        subvragen = new ArrayList<>();
        subvragen.addAll(Arrays.asList(new String[]{"Wat is de warmste maand?",
            "Wat is de temperatuur van de warmste maand in °C (Tw)?",
            "Wat is de koudste maand?",
            "Wat is de temperatuur van de koudste maand in °C (Tk)?",
            "Hoeveel droge maanden zijn er (D)?",
            "Hoeveelheid mm neerslag in de zomer?",
            "Hoeveelheid mm neerslag in de winter?"}));
    }
    
    public VraagDto(int id){
        this.id = id;
    }

    public VraagDto(int id, int type, List<KlimatogramDto> klimatogrammen, List<String> subvragen, String beschrijving, int puntenTeVerdienen) {
        this.id = id;
        this.type = type;
        this.klimatogrammen = klimatogrammen;
        this.subvragen = subvragen;
        this.beschrijving = beschrijving;
        this.puntenTeVerdienen = puntenTeVerdienen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<KlimatogramDto> getKlimatogrammen() {
        return this.klimatogrammen;
    }

    public void setKlimatogrammen(List<KlimatogramDto> klimatogrammen) {
        this.klimatogrammen = klimatogrammen;
    }

    public List<String> getSubvragen() {
        return this.subvragen;
    }

    public void setSubvragen(List<String> subvragen) {
        this.subvragen = subvragen;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public int getPuntenTeVerdienen() {
        return this.puntenTeVerdienen;
    }

    public void setPuntenTeVerdienen(int puntenTeVerdienen) {
        this.puntenTeVerdienen = puntenTeVerdienen;
    }

    public boolean isGraadEenVraag() {
        return type == GRAADEEN;
    }

    public boolean isDeterminatieVraag() {
        return type == DETERMINATIE;
    }

    public boolean isGraadDrieVraag() {
        return type == GRAADDRIE;
    }

}
