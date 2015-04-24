/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Jan
 */
public class FotoProperty extends DeterminatieKnoopProperty {

    private Fotos waarde;

    @Override
    public Class<?> getType() {
        return Fotos.class;
    }

    @Override
    public Object getValue() {
        return waarde;
    }

    @Override
    public void setValue(Object o) {
        waarde = (Fotos) o;
        gewijzigd();
    }

    public FotoProperty(String categorie, String naam, Fotos waarde) {
        this.categorie = categorie;
        this.naam = naam;
        this.waarde = waarde;
    }
}
