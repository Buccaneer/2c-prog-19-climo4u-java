package dto;

/**
 *
 * @author Jasper De Vrient
 */
public class ParameterDto {

    private boolean isConstant;
    private String naam;
    private double waarde;

    public ParameterDto() {
    }

    public ParameterDto(String naam, boolean isConstante) {
        this.naam = naam;
        this.isConstant = isConstante;
    }

    public ParameterDto(String naam, double waarde, boolean isConstante) {
        this.naam = naam;
        this.waarde = waarde;
        this.isConstant = isConstante;
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
    
    public void setConstant(boolean constant)
    {
        isConstant = constant;
    }
    
    public boolean isConstant()
    {
        return isConstant;
    }
}
