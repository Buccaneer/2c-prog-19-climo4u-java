package dto;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.eclipse.persistence.internal.xr.sxf.SimpleXMLFormatProject;
import sun.awt.im.SimpleInputMethodWindow;

public class MaandDto {

    private SimpleStringProperty naam = new SimpleStringProperty();
    private SimpleIntegerProperty neerslag = new SimpleIntegerProperty();
    private SimpleDoubleProperty temperatuur = new SimpleDoubleProperty();

    public MaandDto (){}
    public MaandDto(String naam, int neerslag, int temperatuur){
        setNaam(naam);
    }
    
    public String getNaam() {
        return this.naam.get();
    }

    public void setNaam(String naam) {
        this.naam.set(naam);
    }

    public int getNeerslag() {
        return this.neerslag.getValue();
    }

    public void setNeerslag(int neerslag) {
        this.neerslag.set(neerslag);
    }

    public double getTemperatuur() {
        return this.temperatuur.getValue();
    }

    public void setTemperatuur(double temperatuur) {
        this.temperatuur.set(temperatuur);
    }

    public SimpleStringProperty naamProperty() {
        return naam;
    }

    public SimpleDoubleProperty temperatuurProperty() {
        return temperatuur;
    }

    public SimpleIntegerProperty neerslagProperty() {
        return neerslag;
    }
}
