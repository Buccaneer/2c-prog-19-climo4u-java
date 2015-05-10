package gui;

/**
 *
 * @author Jasper De Vrient
 */
public class StringProperty extends DeterminatieKnoopProperty {

    private String waarde;

    @Override
    public Class<?> getType() {
        return String.class;
    }

    public StringProperty(String categorie, String naam, String waarde) {
        this.categorie = categorie;
        this.naam = naam;
        this.waarde = waarde;
        if (waarde == null) {
            this.waarde = "";
        }
    }

    @Override
    public Object getValue() {
        return waarde;
    }

    @Override
    public void setValue(Object o) {
        waarde = o.toString();
        gewijzigd();
    }

}
