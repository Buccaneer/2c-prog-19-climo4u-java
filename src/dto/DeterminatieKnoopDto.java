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
    private boolean resultaatKnoop = true;
    private DeterminatieKnoopDto ja;
    private DeterminatieKnoopDto nee;
    private DeterminatieKnoopDto ouder;
    

    public boolean isResultaatBlad() {
        return resultaatKnoop;
    }
    
    public void toBeslissingsKnoop() {
        resultaatKnoop = false;
    }
    public void toResultaatBlad() {
        resultaatKnoop = true;
    }
    
}
