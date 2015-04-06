package dto;

import domein.VergelijkingsOperator;

/**
 *
 * @author Jasper De Vrient
 */
class VergelijkingDto {
    private ParameterDto links;
    private VergelijkingsOperator operator;
    private ParameterDto rechts;

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
