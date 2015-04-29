package dto;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class LeerlingDto {

    private int id;
    private SimpleStringProperty naam;
    private SimpleStringProperty voornaam;
    private ObservableValue<KlasDto> klas;

    public int getId() {
        return id;
    }

    public SimpleStringProperty getNaam() {
        return this.naam;
    }

    public SimpleStringProperty getVoornaam() {
        return this.voornaam;
    }

    public void setNaam(SimpleStringProperty naam) {
        this.naam = naam;
    }

    public void setVoornaam(SimpleStringProperty voornaam) {
        this.voornaam = voornaam;
    }

    public ObservableValue<KlasDto> getKlas() {
        return this.klas;
    }

    public void setKlas(ObservableValue<KlasDto> klas) {
        this.klas = klas;
    }
    
    public void setKlas(KlasDto klas) {
        this.klas = new SimpleObjectProperty<KlasDto>( klas);
    }

    public LeerlingDto() {
    }

    public LeerlingDto(int id, String naam, String voornaam, KlasDto klas) {
        this.id = id;

        this.naam = new SimpleStringProperty(naam);
        this.voornaam = new SimpleStringProperty(voornaam);
        this.klas = new SimpleObjectProperty<KlasDto>( klas);
    }

}
