package gui;

import controller.ToetsController;
import dto.VraagDto;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jasper De Vrient
 */
public class ToetsVraagPanelController extends BorderPane {

    @FXML
    private Button btnOpslaan;
    @FXML
    private Button btnVerwijderen;
    @FXML
    private TextArea txaBeschrijving;
    @FXML
    private TextField txtPunten;
    @FXML
    private Pane content;

    private ToetsController controller;
    private IToetsVraag vraag;
    private VraagDto dto;

    public ToetsVraagPanelController(ToetsController controller, IToetsVraag vraag, VraagDto dto) {
        this.controller = controller;
        this.vraag = vraag;
        this.dto = dto;

        laadFxmlBestand("ToetsVraagPanel.fxml", this, this);
        laadKind();
        laadBasisAttributen(dto);
    }

    private void laadBasisAttributen(VraagDto dto1) {
        txaBeschrijving.setText(dto1.getBeschrijving());
        txtPunten.setText(String.format("%d", dto1.getPuntenTeVerdienen()));
    }

    public static void laadFxmlBestand(String naam, Object root, Object controller) throws RuntimeException {
        FXMLLoader loader = new FXMLLoader(root.getClass().getResource(naam));

        loader.setRoot(root);

        loader.setController(controller);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void laadKind() {
        laadFxmlBestand(vraag.getFxmlBestand(), vraag, vraag);

        vraag.setController(controller);
        vraag.laden(dto);

        content.getChildren().clear();

        content.getChildren().add((Node) vraag);
    }

    public void opslaan() {
        opslaanBasisAttributen();
        vraag.opslaan(dto);
        controller.wijzigVraag(dto);
    }

    private void opslaanBasisAttributen() throws NumberFormatException {
        dto.setBeschrijving(txaBeschrijving.getText());
        dto.setPuntenTeVerdienen(Integer.parseInt(txtPunten.getText()));
    }

    public void verwijderen() {
        controller.verwijderVraag(dto);
    }

}