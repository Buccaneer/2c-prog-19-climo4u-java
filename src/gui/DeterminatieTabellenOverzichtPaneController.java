/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.DeterminatieController;
import controller.Observer;
import controller.Subject;
import dto.DeterminatieTabelDto;
import dto.GraadDto;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jasper De Vrient
 */
public class DeterminatieTabellenOverzichtPaneController extends VBox {

    @FXML
    private ListView<DeterminatieTabelDto> lstDeterminatieTabellen;
    @FXML
    private Button btnVerwijderen;
    @FXML
    private TextField txtNaam;
    @FXML
    private Button btnToevoegen;

    @FXML
    private ComboBox<GraadDto> cboGraden;
    @FXML
    private ComboBox<DeterminatieTabelDto> cboDeterminatieTabellen;
    @FXML
    private Button btnKoppelen;
    private DeterminatieController controller;
    private DeterminatieTabelDto geselecteerde;

    public DeterminatieTabellenOverzichtPaneController(DeterminatieController determinatie) {
        this.controller = determinatie;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeterminatieTabellenOverzichtPane.fxml"));
        loader.setRoot(null);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        vulLijstenDeterminatieOp();

        vulLijstenGradenOp();

        voegEventToeVoorLijst();
        voegEventToeVoorComboBoxGraden();
    }

    private void voegEventToeVoorLijst() {
        lstDeterminatieTabellen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

            if (newValue != null) {
                geselecteerde = newValue;
                controller.selecteerDeterminatieTabel(newValue);
                txtNaam.setText(newValue.toString());
            }
        });
    }

    private void voegEventToeVoorComboBoxGraden() {
        cboGraden.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                DeterminatieTabelDto dto = cboDeterminatieTabellen.getItems().stream().filter(c -> c.getId() == newValue.getTabel().getId()).findAny().get();
                if (dto != null)
                    cboDeterminatieTabellen.getSelectionModel().select(dto);
            }
        });
    }

    private void vulLijstenDeterminatieOp() {
        ObservableList<DeterminatieTabelDto> list = controller.getDeterminatieTabellen();
        lstDeterminatieTabellen.setItems(list);
        cboDeterminatieTabellen.setItems(list);
        list.addListener(new ListChangeListener<DeterminatieTabelDto>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends DeterminatieTabelDto> a) {
                if (cboGraden.getValue() != null) {
                    DeterminatieTabelDto dto = cboDeterminatieTabellen.getItems().stream().filter(c -> c.getId() == cboGraden.getValue().getTabel().getId()).findAny().get();
                    if (dto != null)
                        cboDeterminatieTabellen.getSelectionModel().select(dto);
                }
            }
        });
    }

    @FXML
    private void voegDeterminatieTabelToe() {
        controller.maakNieuweDeterminatieTabel();
        controller.setNaamDeterminatieTabel(txtNaam.getText());

    }

    @FXML
    private void verwijderDeterminatieTabel() {
        try {
            Optional<ButtonType> result = maakAlert("Determinatietabel verwijderen", "Determinatietabel verwijderen", "Bent u zeker dat u deze determinatietabel wilt verwijderen? Er is geen weg terug.");
            if (result.get().getButtonData() == ButtonBar.ButtonData.YES)
                controller.verwijderDeterminatieTabel(geselecteerde);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(ex.getMessage());
            a.show();
        }
    }

    private void vulLijstenGradenOp() {
        ObservableList<GraadDto> list = controller.getGraden();

        cboGraden.setItems(list);
    }

    @FXML
    private void naamWijzigen() {
        try {
            if (geselecteerde != null)
                controller.setNaamDeterminatieTabel(txtNaam.getText());
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Foutje");
            a.setContentText(ex.getMessage());
            a.show();
        }
    }

    @FXML
    private void koppelen() {
        try {
            controller.koppelGraadMetDeterminatieTabel(cboGraden.getValue(), cboDeterminatieTabellen.getValue());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Foutje");
            a.setContentText("De geselecteerde determinatietabel voldoet niet aan de vereisten om gekoppeld te worden met een graad.");
            a.show();
        }
    }

    public Optional<ButtonType> maakAlert(String titel, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titel);
        alert.setHeaderText(header);
        alert.setContentText(content);
        ButtonType buttonOk = new ButtonType("Verwijderen", ButtonBar.ButtonData.YES);
        ButtonType buttonCancel = new ButtonType("Annuleren", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonOk, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

}
