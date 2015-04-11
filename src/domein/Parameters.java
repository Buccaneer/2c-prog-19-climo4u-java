/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Jasper De Vrient
 */
public enum Parameters {

    TEMPERATUURWARSMSTEMAAND("Tw"), TEMPERATUURKOUDSTEMAAND("Tk"), CONSTANTEWAARDE("Constante waarde");

    private String waarde;

    private Parameters(String waarde) {
        this.waarde = waarde;
    }

    public String toString() {
        return waarde;
    }
}
