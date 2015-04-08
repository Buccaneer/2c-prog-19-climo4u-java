package dto;

import domein.VergelijkingsOperator;

/**
 *
 * @author Jasper De Vrient
 */
public class VergelijkingDto {
    private ParameterDto links;
    private VergelijkingsOperator operator;
    private ParameterDto rechts;

    public VergelijkingDto() {
    }

    public VergelijkingDto(ParameterDto links, VergelijkingsOperator operator, ParameterDto rechts) {
        this.links = links;
        this.operator = operator;
        this.rechts = rechts;
    }
    
    public ParameterDto getLinks() {
        return links;
    }

    public void setLinks(ParameterDto links) {
        this.links = links;
    }

    public VergelijkingsOperator getOperator() {
        return operator;
    }

    public void setOperator(VergelijkingsOperator operator) {
        this.operator = operator;
    }

    public ParameterDto getRechts() {
        return rechts;
    }

    public void setRechts(ParameterDto rechts) {
        this.rechts = rechts;
    }
    
    
}
