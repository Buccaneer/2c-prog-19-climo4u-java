package gui.toets;

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
