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
    private String name;
    private DeterminatieKnoopDto beginknoop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeterminatieKnoopDto getBeginknoop() {
        return beginknoop;
    }

    public void setBeginknoop(DeterminatieKnoopDto beginknoop) {
        this.beginknoop = beginknoop;
    }
    
    
    
}
