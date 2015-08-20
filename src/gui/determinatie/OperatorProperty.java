package gui.determinatie;

import domein.VergelijkingsOperator;

/**
 *
 * @author Jasper De Vrient
 */
public class OperatorProperty extends DeterminatieKnoopProperty {

    private VergelijkingsOperator waarde;
    @Override
    public Class<?> getType() {
   return VergelijkingsOperator.class;
    }

    @Override
    public Object getValue() {
      return waarde;
    }

    @Override
    public void setValue(Object o) {
      waarde = (VergelijkingsOperator)o;
        gewijzigd();
    }
    public OperatorProperty(String categorie, String naam, VergelijkingsOperator waarde) {
        this.categorie = categorie;
        this.naam = naam;
        this.waarde = waarde;
    }

}
