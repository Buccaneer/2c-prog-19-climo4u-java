/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.ToetsController;
import dto.VraagDto;

/**
 *
 * @author Jasper De Vrient
 */
public interface IToetsVraag{
    public String getFxmlBestand();
    public void setController(ToetsController controller);
    public void laden(VraagDto vraag);
    public void opslaan(VraagDto vraag);
}
