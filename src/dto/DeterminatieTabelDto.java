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
public class DeterminatieTabelDto {
    private int id;
    private String naam;
    private DeterminatieKnoopDto beginknoop;

    public DeterminatieTabelDto() {
    }

    public DeterminatieTabelDto(int id, String naam, DeterminatieKnoopDto beginknoop) {
        this.id = id;
        this.naam = naam;
        this.beginknoop = beginknoop;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public DeterminatieKnoopDto getBeginknoop() {
        return beginknoop;
    }

    public void setBeginknoop(DeterminatieKnoopDto beginknoop) {
        this.beginknoop = beginknoop;
    }    
}
