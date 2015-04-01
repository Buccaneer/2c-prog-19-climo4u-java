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
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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
    private ListView<KlimatogramDto> lstLocaties;

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
            controller.verwijderKlimatogram(lstLocaties.getSelectionModel().getSelectedItem().toString());
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
}
