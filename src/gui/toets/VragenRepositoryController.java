package gui.toets;

import gui.toets.ToetsVraagPanelController;
import controller.ToetsController;
import dto.KlimatogramDto;
import dto.VraagDto;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jasper De Vrient
 */
public class VragenRepositoryController extends HBox {

    @FXML
    private Button btnDetermineren;
    @FXML
    private Button btnSubvragen;
    @FXML
    private Button btnLocaties;
    private ToetsController controller;

    public VragenRepositoryController(ToetsController controller) {
        this.controller = controller;
        ToetsVraagPanelController.laadFxmlBestand("/fxml/toets/VraagRepositoryToolbar.fxml", this, this);
    }

    private VraagDto maakVraagDto(int type) {
        VraagDto v = new VraagDto();
        v.setType(type);
        v.setBeschrijving("");
        v.setKlimatogrammen(new ArrayList<KlimatogramDto>());
        return v;
    }

    public void subvragen() {
        controller.voegVraagToe(maakVraagDto(VraagDto.GRAADEEN));
    }

    public void determineren() {
        controller.voegVraagToe(maakVraagDto(VraagDto.DETERMINATIE));
    }

    public void locatie() {
        controller.voegVraagToe(maakVraagDto(VraagDto.GRAADDRIE));

    }
}
