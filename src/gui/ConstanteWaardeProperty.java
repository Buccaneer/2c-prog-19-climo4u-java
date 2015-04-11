package gui;

import domein.VergelijkingsOperator;

/**
 *
 * @author Jasper De Vrient
 */
public class ConstanteWaardeProperty extends DeterminatieKnoopProperty {

    private Double waarde;
    @Override
    public Class<?> getType() {
   return Double.class;
    }

    @Override
    public Object getValue() {
      return waarde;
    }

    @Override
    public void setValue(Object o) {
      waarde = (Double)o;
        gewijzigd();
    }
    public ConstanteWaardeProperty(String categorie, String naam, Double waarde) {
        this.categorie = categorie;
        this.naam = naam;
        this.waarde = waarde;
    }

}
