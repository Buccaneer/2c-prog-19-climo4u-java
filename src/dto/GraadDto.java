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
    private DeterminatieTabelDto tabel;

    public DeterminatieTabelDto getTabel() {
        return tabel;
    }
    
    

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

    public GraadDto(int graad, int jaar, DeterminatieTabelDto tabel) {
        this.graad = graad;
        this.jaar = jaar;
        this.tabel = tabel;
    }

    @Override
    public String toString() {
        return "Graad " + graad +  (jaar != 0 ? (" jaar " + jaar) : "");
    }
    
    
    
    
    
}
