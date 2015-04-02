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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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

    private KlimatogramController controller;

    public KlimatogramKiezenPanelController(KlimatogramController controller) {
        this.controller = controller;
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
        if (controller.landGeselecteerd()) {
            this.setDisable(true);
            clearSelectie();
            controller.notifyObservers("voegToe");
        }
    }

    @FXML
    public void verwijderKlimatogram(ActionEvent event) {
        if (controller.klimatogramGeselecteerd()) {
            controller.verwijderKlimatogram(lstLocaties.getSelectionModel().getSelectedItem().getLocatie());
        }
    }

    public void clearSelectie() {
        lstLocaties.getSelectionModel().clearSelection();
    }

    @Override
    public void update(Object object) {
        if (object.toString().equals("menu")) {
            this.setDisable(false);
        }
    }

    @FXML
    public void wijzigKlimatogram(ActionEvent event) {
        if (controller.klimatogramGeselecteerd()) {
            this.setDisable(true);
            controller.notifyObservers("wijzig");
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
            controller.voegContinentToe(new ContinentDto(txfWerelddeel.getText()));
            txfWerelddeel.clear();
            cboWerelddeel.setVisible(true);
            txfWerelddeel.setVisible(false);
            btnWerelddeelOk.setText("+");
            btnWerelddeelCancel.setText("-");
        }
    }

    @FXML
    public void werelddeelCancel(ActionEvent event) {
        if (btnWerelddeelCancel.getText().equals("-")) {
            controller.verwijderContinent(cboWerelddeel.getSelectionModel().getSelectedItem());
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
            cboLand.setVisible(false);
            txfLand.setVisible(true);
            btnLandOk.setText("v");
            btnLandCancel.setText("x");
        } else {
            controller.voegLandToe(new LandDto(txfLand.getText()));
            txfLand.clear();
            cboLand.setVisible(true);
            txfLand.setVisible(false);
            btnLandOk.setText("+");
            btnLandCancel.setText("-");
        }
    }

    @FXML
    public void landCancel(ActionEvent event) {
        if (btnLandCancel.getText().equals("-")) {
            controller.verwijderLand(cboLand.getSelectionModel().getSelectedItem());
        } else {
            txfLand.clear();
            cboLand.setVisible(true);
            txfLand.setVisible(false);
            btnLandOk.setText("+");
            btnLandCancel.setText("-");
        }
    }
}
