package gui;

import controller.ToetsController;
import dto.VraagDto;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jasper De Vrient
 */
public class ToetsVragenOverzichtController extends ScrollPane implements ListChangeListener<VraagDto> {

    @FXML
    private VBox content;
    private ToetsController controller;
    private ObservableList<VraagDto> vragen;

    public ToetsVragenOverzichtController(ToetsController controller) {
        this.controller = controller;
        this.vragen = controller.geefVragen();
        vragen.addListener(this);
        ToetsVraagPanelController.laadFxmlBestand("/fxml/ToetsVragenOverzicht.fxml", this, this);
    }

    private void herteken() {
        ObservableList<Node> nodes = content.getChildren();

        nodes.clear();

        for (VraagDto vraag : vragen)
            voegVraagToe(nodes, vraag);

    }

    private void voegVraagToe(ObservableList<Node> nodes, VraagDto vraag) {

        IToetsVraag toetsvraag = null;
        switch (vraag.getType()) {
            case VraagDto.DETERMINATIE:
                toetsvraag = new DeterminatieVraagController();
                break;
            case VraagDto.GRAADDRIE:
                toetsvraag = new LocatieOefeningController();
                break;
            case VraagDto.GRAADEEN:
                toetsvraag = new SubVragenController();
                break;
            default:
                throw new RuntimeException("Niet ondersteunde vraag.");
        }

        if (toetsvraag != null) {
            ToetsVraagPanelController tvp = new ToetsVraagPanelController(controller, toetsvraag, vraag);

            HBox.setHgrow(tvp, Priority.ALWAYS);
            nodes.add(tvp);
        }
    }

    @Override
    public void onChanged(Change<? extends VraagDto> c) {
        herteken();
    }

}
