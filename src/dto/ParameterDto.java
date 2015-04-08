package dto;

/**
 *
 * @author Jasper De Vrient
 */
public class ParameterDto {
    private String naam;
    private double waarde;

    public ParameterDto() {
    }

    public ParameterDto(String naam, double waarde) {
        this.naam = naam;
        this.waarde = waarde;
    }
    
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getWaarde() {
        return waarde;
    }

    public void setWaarde(double waarde) {
        this.waarde = waarde;
    }
}
