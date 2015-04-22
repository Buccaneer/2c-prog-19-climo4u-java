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
public enum Parameters {

    TEMPERATUURWARSMSTEMAAND("Tw"), TEMPERATUURKOUDSTEMAAND("Tk"), CONSTANTEWAARDE("Constante waarde"),
    
    /*
    parameters.add(new ParameterTemperatuurWarmsteMaand("Tw"));
        parameters.add(new ParameterTemperatuurKoudsteMaand("Tk"));
        parameters.add(new ParameterNeerslagZomer("Nz"));
        parameters.add(new ParameterNeerslagWinter("Nw"));
        parameters.add(new ParameterAantalDrogeMaanden("D"));
        parameters.add(new ParameterGemiddeldeTemperatuurJaar("Tj"));
        parameters.add(new ParameterTotaleNeerslagJaar("Nj"));
        parameters.add(new TemperatuurVierdeWarmsteMaand("T 4de Warmste Maand"));
    */
    
    NEERSLAGZOMER("Nz"), NEERSLAGWINTER("Nw"), AANTALDROGEMAANDEN("D"), GEMIDDELDETEMPERATUURJAAR("Tj"),
    TOTALENEERSLAGJAAR("Nj"), TEMPARATUURVIERDEWARMSTEMAAND("T 4de Warmste Maand")
;
    private String waarde;

    private Parameters(String waarde) {
        this.waarde = waarde;
    }

    public String toString() {
        return waarde;
    }
    
    public static ParameterDto geefParameter(Parameters p) {
        for (Parameter param: ParameterFactory.geefParameters()) {
            if (param.getNaam().equals(p.toString()))
                return new ParameterDto(param.getNaam(), false);
        }
        return new ParameterDto();
    }
    
    public static Parameters geefParameters(ParameterDto p) {
        for (Parameters param : values()) {
            if (param.toString().equals(p.getNaam()))
                return param;
        }
        return CONSTANTEWAARDE;
    }
}
