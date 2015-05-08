package dto;

import java.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class ToetsDto {

    private List<VraagDto> vragen;
    private int id;
    private SimpleStringProperty beschrijving;
    private SimpleStringProperty titel;
    private GregorianCalendar aanvang;
    private GregorianCalendar eind;
    private ObservableValue<GraadDto> graad;
    private DeterminatieKnoopDto determinatietabel;
    private int aantalPuntenTeBehalen;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObservableValue<GraadDto> getGraad() {
        return graad;
    }

    public void setGraad(ObservableValue<GraadDto> graad) {
        this.graad = graad;
    }

    public List<VraagDto> getVragen() {
        return vragen;
    }

    public DeterminatieKnoopDto getDeterminatietabel() {
        return determinatietabel;
    }

    public void setVragen(List<VraagDto> vragen) {
        this.vragen = vragen;
    }

    public void setDeterminatietabel(DeterminatieKnoopDto determinatietabel) {
        this.determinatietabel = determinatietabel;
    }
    
    public void vroegVraagToe(VraagDto vraag) {
        vragen.add(vraag);
    }

    public void verwijderVraag(VraagDto vraag) {
        vragen.remove(vraag);
    }

    public SimpleStringProperty getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(SimpleStringProperty beschrijving) {
        this.beschrijving = beschrijving;
    }

    public SimpleStringProperty getTitel() {
        return this.titel;
    }

    public void setTitel(SimpleStringProperty titel) {
        this.titel = titel;
    }

    public GregorianCalendar getAanvang() {
        return this.aanvang;
    }

    public void setAanvang(GregorianCalendar aanvang) {
        this.aanvang = aanvang;
    }

    public GregorianCalendar getEind() {
        return this.eind;
    }

    public void setEind(GregorianCalendar eind) {
        this.eind = eind;
    }

}
