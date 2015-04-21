/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Gebruiker
 */
public class GraadDto {
    private int graad;
    private int jaar;

    public int getGraad() {
        return graad;
    }

    public void setGraad(int graad) {
        this.graad = graad;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public GraadDto() {
    }

    public GraadDto(int graad, int jaar) {
        this.graad = graad;
        this.jaar = jaar;
    }

    @Override
    public String toString() {
        return "GraadDto{" + "graad=" + graad + ", jaar=" + jaar + '}';
    }
    
    
    
    
    
}
