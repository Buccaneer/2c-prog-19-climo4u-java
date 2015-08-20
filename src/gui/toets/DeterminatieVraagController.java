package gui.toets;

import controller.ToetsController;
import dto.KlimatogramDto;
import dto.VraagDto;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jasper De Vrient
 */
public class DeterminatieVraagController extends AnchorPane implements IToetsVraag {

    @FXML
    private ComboBox<KlimatogramDto> cboKlimatogram;

    private ToetsController controller;

    @Override
    public String getFxmlBestand() {
        return "/fxml/toets/DeterminatieVraagPanel.fxml";
    }

    @Override
    public void setController(ToetsController controller) {
        this.controller = controller;
    }

    @Override
    public void laden(VraagDto vraag) {
        cboKlimatogram.setItems(controller.geefKlimatogrammen());
        if (vraag.getKlimatogrammen().size() > 0) {
            KlimatogramDto first = vraag.getKlimatogrammen().get(0);
            cboKlimatogram.getSelectionModel().select(first);
        }
    }

    @Override
    public void opslaan(VraagDto vraag) {
        vraag.getKlimatogrammen().clear();
        vraag.getKlimatogrammen().add(cboKlimatogram.getSelectionModel().getSelectedItem());
    }

}