/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Gebruiker
 */
public enum VergelijkingsOperator {

    KLEINERDAN("<"),
    GELIJKAAN("="),
    KLEINERDANGELIJKAAN("≤"),
    GROTERDAN(">"),
    GROTERDANGELIJKAAN("≥"),
    NIETGELIJKAAN("≠");
    
        private String teken;

    private VergelijkingsOperator(String teken) {
        this.teken = teken;
    }

    @Override
    public String toString() {
        return teken;
    }
    
    
        
        
}
