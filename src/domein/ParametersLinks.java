/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import dto.ParameterDto;

/**
 *
 * @author Jasper De Vrient
 */
public enum ParametersLinks {

    TEMPERATUURWARSMSTEMAAND("Tw"), TEMPERATUURKOUDSTEMAAND("Tk"),
    NEERSLAGZOMER("Nz"), NEERSLAGWINTER("Nw"), AANTALDROGEMAANDEN("D"), GEMIDDELDETEMPERATUURJAAR("Tj"),
    TOTALENEERSLAGJAAR("Nj"), TEMPARATUURVIERDEWARMSTEMAAND("T 4de Warmste Maand")
;
    private String waarde;

    private ParametersLinks(String waarde) {
        this.waarde = waarde;
    }

    public String toString() {
        return waarde;
    }
    
    public static ParameterDto geefParameter(ParametersLinks p) {
        for (Parameter param: ParameterFactory.geefParameters()) {
            if (param.getNaam().equals(p.toString()))
                return new ParameterDto(param.getNaam(), false);
        }
        return new ParameterDto();
    }
    
    public static ParametersLinks geefParameters(ParameterDto p) {
        for (ParametersLinks param : values()) {
            if (param.toString().equals(p.getNaam()))
                return param;
        }
        return TEMPERATUURWARSMSTEMAAND;
    }
}
