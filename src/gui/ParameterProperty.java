package gui;

import domein.*;

/**
 *
 * @author Jasper De Vrient
 */
public class ParameterProperty extends DeterminatieKnoopProperty {

    private Parameters waarde;
    @Override
    public Class<?> getType() {
   return Parameters.class;
    }

    @Override
    public Object getValue() {
      return waarde;
    }

    @Override
    public void setValue(Object o) {
      waarde = (Parameters)o;
        gewijzigd();
    }
    public ParameterProperty(String categorie, String naam, Parameters waarde) {
        this.categorie = categorie;
        this.naam = naam;
        this.waarde = waarde;
    }

}
