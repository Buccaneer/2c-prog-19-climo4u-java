package gui;

import domein.*;

/**
 *
 * @author Jasper De Vrient
 */
public class ParameterProperty extends DeterminatieKnoopProperty {

    private Object waarde;
    @Override
    public Class<?> getType() {
    return waarde instanceof ParametersLinks ? ParametersLinks.class : ParametersRechts.class;
    }

    @Override
    public Object getValue() {
      return waarde;
    }

    @Override
    public void setValue(Object o) {
      waarde = o;
        gewijzigd();
    }
    public ParameterProperty(String categorie, String naam, ParametersRechts waarde) {
        this.categorie = categorie;
        this.naam = naam;
        this.waarde = waarde;
    }
    
    public ParameterProperty(String categorie, String naam, ParametersLinks waarde) {
        this.categorie = categorie;
        this.naam = naam;
        this.waarde = waarde;
    }
    
    public ParameterProperty(String categorie, String naam, ParametersLinks waarde) {
        this.categorie = categorie;
        this.naam = naam;
        this.waarde = waarde;
    }
}
