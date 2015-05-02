package dto;

import domein.*;
import java.util.List;

public class VraagDto {

    private int type;
    public final int GRAADEEN = 0;
    public final int DETERMINATIE = 1;
    public final int GRAADDRIE = 2;
    private List<Klimatogram> klimatogrammen;
    private List<String> subvragen;
    private String beschrijving;
    private int puntenTeVerdienen;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Klimatogram> getKlimatogrammen() {
        return this.klimatogrammen;
    }

    public void setKlimatogrammen(List<Klimatogram> klimatogrammen) {
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
