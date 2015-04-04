/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.KlimatogramController;
import controller.Observer;
import dto.ContinentDto;
import dto.KlimatogramDto;
import dto.LandDto;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;

/**
 * FXML Controller class
 *
 * @author Annemie
 */
public class KlimatogramKiezenPanelController extends Pane implements Observer {

    @FXML
    private ComboBox<ContinentDto> cboWerelddeel;
    @FXML
    private ComboBox<LandDto> cboLand;
    @FXML
    private Button btnLandOk, btnLandCancel, btnWerelddeelOk, btnWerelddeelCancel;
    @FXML
    private ListView<KlimatogramDto> lstLocaties;
    @FXML
    private TextField txfLand, txfWerelddeel;
    private StatusBar statusBar;
    private KlimatogramController controller;

    public KlimatogramKiezenPanelController(KlimatogramController controller, StatusBar statusBar) {
        this.controller = controller;
        this.statusBar = statusBar;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlimatogramKiezenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        cboWerelddeel.setItems(controller.getContinenten());
        cboLand.setItems(controller.getLanden());
        lstLocaties.setItems(controller.getLocaties());

        cboWerelddeel.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                controller.selecteerContinent(newValue);
                clearSelectie();
            }
        });
        cboLand.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                controller.selecteerLand(newValue);
                clearSelectie();
            }
        });
        lstLocaties.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                controller.selecteerKlimatogram(newValue);
            }
        });
    }

    @FXML
    public void voegKlimatogramToe(ActionEvent event) {
        try {
            clearErrors();
            controller.voegToe();
            this.setDisable(true);
            clearSelectie();
        } catch (IllegalArgumentException e) {
            statusBar.setText(e.getMessage());
        }
    }

    @FXML
    public void verwijderKlimatogram(ActionEvent event) {

        try {
            clearErrors();
            controller.verwijderKlimatogram(lstLocaties.getSelectionModel().getSelectedItem().getLocatie());
        } catch (IllegalArgumentException ex) {
            statusBar.setText(ex.getMessage());
        } catch (NullPointerException ex) {
            statusBar.setText("U moet eerst een klimatogram selecteren");
        }
    }

    @Override
    public void update(String actie, Object object) {
        if (actie.equals("menu")) {
            this.setDisable(false);
        }
    }

    @FXML
    public void wijzigKlimatogram(ActionEvent event) {

        try {
            clearErrors();
            controller.wijzig();
            this.setDisable(true);
        } catch (IllegalArgumentException e) {
            statusBar.setText(e.getMessage());
        }

    }

    @FXML
    public void werelddeelOk(ActionEvent event) {
        if (btnWerelddeelOk.getText().equals("+")) {
            cboWerelddeel.setVisible(false);
            txfWerelddeel.setVisible(true);
            btnWerelddeelOk.setText("v");
            btnWerelddeelCancel.setText("x");
        } else {
            try {
                clearErrors();
                controller.voegContinentToe(new ContinentDto(txfWerelddeel.getText()));
                txfWerelddeel.clear();
                cboWerelddeel.setVisible(true);
                txfWerelddeel.setVisible(false);
                btnWerelddeelOk.setText("+");
                btnWerelddeelCancel.setText("-");
            } catch (IllegalArgumentException e) {
                statusBar.setText(e.getMessage());
            } catch (Exception ex) {
                statusBar.setText("Er is een fout opgetreden");
            }
        }
    }

    @FXML
    public void werelddeelCancel(ActionEvent event) {
        if (btnWerelddeelCancel.getText().equals("-")) {
            try {
                clearErrors();
                Optional<ButtonType> result = maakAlert("Werelddeel verwijderen", "Werelddeel verwijderen", "Bent u zeker dat u dit werelddeel wilt verwijderen? Alle landen en klimatogrammen zullen mee verwijderd worden.");
                if (result.get().getButtonData() == ButtonData.YES) {
                    controller.verwijderContinent(cboWerelddeel.getSelectionModel().getSelectedItem());
                }
            } catch (IllegalArgumentException e) {
                statusBar.setText(e.getMessage());
            } catch (NullPointerException e) {
                statusBar.setText("U moet eerst een werelddeel selecteren");
            }
        } else {
            txfWerelddeel.clear();
            cboWerelddeel.setVisible(true);
            txfWerelddeel.setVisible(false);
            btnWerelddeelOk.setText("+");
            btnWerelddeelCancel.setText("-");
        }
    }

    @FXML
    public void landOk(ActionEvent event) {
        if (btnLandOk.getText().equals("+")) {
            if (controller.werelddeelGeselecteerd()) {
                clearErrors();
                cboLand.setVisible(false);
                txfLand.setVisible(true);
                btnLandOk.setText("v");
                btnLandCancel.setText("x");
            } else {
                statusBar.setText("U moet eerst een werelddeel selecteren");
            }

        } else {
            try {
                clearErrors();
                controller.voegLandToe(new LandDto(txfLand.getText()));
                txfLand.clear();
                cboLand.setVisible(true);
                txfLand.setVisible(false);
                btnLandOk.setText("+");
                btnLandCancel.setText("-");
            } catch (IllegalArgumentException e) {
                statusBar.setText(e.getMessage());
            } catch (Exception ex) {
                statusBar.setText("Er is een fout opgetreden");
            }
        }
    }

    @FXML
    public void landCancel(ActionEvent event) {
        if (btnLandCancel.getText().equals("-")) {
            try {
                clearErrors();
                Optional<ButtonType> result = maakAlert("Land verwijderen", "Land verwijderen", "Bent u zeker dat u dit land wilt verwijderen? Alle klimatogrammen zullen mee verwijderd worden.");
                if (result.get().getButtonData() == ButtonData.YES) {
                    controller.verwijderLand(cboLand.getSelectionModel().getSelectedItem());
                }
            } catch (IllegalArgumentException e) {
                statusBar.setText(e.getMessage());
            } catch (NullPointerException e) {
                statusBar.setText("U moet eerst een land selecteren");
            }
        } else {
            txfLand.clear();
            cboLand.setVisible(true);
            txfLand.setVisible(false);
            btnLandOk.setText("+");
            btnLandCancel.setText("-");
        }
    }

    public void clearErrors() {
        statusBar.setText("");
    }

    public void clearSelectie() {
        lstLocaties.getSelectionModel().clearSelection();
    }

    public Optional<ButtonType> maakAlert(String titel, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titel);
        alert.setHeaderText(header);
        alert.setContentText(content);
        ButtonType buttonOk = new ButtonType("Verwijderen", ButtonData.YES);
        ButtonType buttonCancel = new ButtonType("Annuleren", ButtonData.NO);
        alert.getButtonTypes().setAll(buttonOk,buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
}
