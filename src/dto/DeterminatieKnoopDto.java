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
public class DeterminatieKnoopDto {
    private boolean blad = true;
    private DeterminatieKnoopDto ja;
    private DeterminatieKnoopDto nee;
    private DeterminatieKnoopDto ouder;
    private String klimaattype;
    private VegetatieTypeDto vegetatieType;
    private VergelijkingDto vergelijking;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public boolean isBeslissingsKnoop() {
        return blad == false;
    }

    public void setResultaatKnoop(boolean resultaatKnoop) {
        this.blad = resultaatKnoop;
    }

    public DeterminatieKnoopDto getJa() {
        return ja;
    }

    public void setJa(DeterminatieKnoopDto ja) {
        this.ja = ja;
    }

    public DeterminatieKnoopDto getNee() {
        return nee;
    }

    public void setNee(DeterminatieKnoopDto nee) {
        this.nee = nee;
    }

    public DeterminatieKnoopDto getOuder() {
        return ouder;
    }

    public void setOuder(DeterminatieKnoopDto ouder) {
        this.ouder = ouder;
    }

    public String getKlimaattype() {
        return klimaattype;
    }

    public void setKlimaattype(String klimaattype) {
        this.klimaattype = klimaattype;
    }

    public VegetatieTypeDto getVegetatieType() {
        return vegetatieType;
    }

    public void setVegetatieType(VegetatieTypeDto vegetatieType) {
        this.vegetatieType = vegetatieType;
    }

    public VergelijkingDto getVergelijking() {
        return vergelijking;
    }

    public void setVergelijking(VergelijkingDto vergelijking) {
        this.vergelijking = vergelijking;
    }
    
    
    

    public boolean isResultaatBlad() {
        return blad== true;
    }
    
    public void toBeslissingsKnoop() {
        blad = false;
    }
    public void toResultaatBlad() {
        blad = true;
    }
    
}
