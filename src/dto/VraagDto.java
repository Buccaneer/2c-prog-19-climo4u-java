package dto;

import domein.*;
import java.util.List;

public class VraagDto {

    private int type;
    public static final int GRAADEEN = 0;
    public static final int DETERMINATIE = 1;
    public static final int GRAADDRIE = 2;
    private List<KlimatogramDto> klimatogrammen;
    private List<String> subvragen;
    private String beschrijving;
    private int puntenTeVerdienen;

    public VraagDto() {
    }

    public VraagDto(int type, List<KlimatogramDto> klimatogrammen, List<String> subvragen, String beschrijving, int puntenTeVerdienen) {
        this.type = type;
        this.klimatogrammen = klimatogrammen;
        this.subvragen = subvragen;
        this.beschrijving = beschrijving;
        this.puntenTeVerdienen = puntenTeVerdienen;
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
        return type == 1;
    }

    public boolean isDeterminatieVraag() {
        return type == 2;
    }

    public boolean isGraadDrieVraag() {
        return type == 3;
    }

}
