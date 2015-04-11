/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.DeterminatieController;
import domein.Parameters;
import domein.VergelijkingsOperator;
import dto.DeterminatieKnoopDto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanProperty;

/**
 * FXML Controller class
 *
 * @author Jasper De Vrient
 */
public class PropertyContainerPanelController extends BorderPane implements PropertyVeranderdListener, NodeGeselecteerdListener {

    @FXML
    private Button btnOpslaan;
    @FXML
    private Pane paneEigenschappen;
    private PropertySheet properties;
    private DeterminatieKnoopDto dto;
    private DeterminatieController controller;
    private ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();

    public PropertyContainerPanelController(DeterminatieController controller) {
        this.controller = controller;
         FXMLLoader loader = new FXMLLoader(getClass().getResource("PropertyContainerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void toonProperties() {
        if (properties == null) {
            properties = new PropertySheet(items);
            paneEigenschappen.getChildren().clear();
            paneEigenschappen.getChildren().add(properties);
        }
        properties.setPrefSize(paneEigenschappen.getPrefWidth(), paneEigenschappen.getPrefHeight());
    }

    @Override
    public void isVeranderd(PropertySheet.Item item) {
     switch (item.getName()) {
         case "Linker parameter":
             break;
         case "Linker Constante waarde":
             break;
         case "Operator":
             break;
         case "Rechter parameter":
             break;
         case "Rechter Constante waarde":
             break;
         case "Klimaattype":
             break;
         case "Naam":
             break;
         case "Foto": 
             break;
     }
    }

    @Override
    public void selectieGewijzigd(DeterminatieKnoopDto knoop) {
        // items aanpassen.
        toonProperties();
        items.clear();
        if (knoop.isBeslissingsKnoop()) {
            DeterminatieKnoopProperty d = new ParameterProperty("Links", "Linker parameter", Parameters.CONSTANTEWAARDE);
            d.addListener(this);
            items.add(d);
            d = new ConstanteWaardeProperty("Links", "Linker Constante waarde", knoop.getVergelijking().getLinks().getWaarde());
            d.addListener(this);
            items.add(d);
            d = new OperatorProperty("Operator", "Operator", knoop.getVergelijking().getOperator());
            d.addListener(this);
            items.add(d);
            d = new ParameterProperty("Rechts", "Rechter parameter", Parameters.CONSTANTEWAARDE);
            d.addListener(this);
            items.add(d);
            d = new ConstanteWaardeProperty("Rechts", "Rechter Constante waarde", knoop.getVergelijking().getRechts().getWaarde());
            d.addListener(this);
            items.add(d);
        } else {
            DeterminatieKnoopProperty d = new StringProperty("", "Klimaattype", knoop.getKlimaattype());
            d.addListener(this);
            items.add(d);
            d = new StringProperty("VegetatieType", "Naam", knoop.getVegetatieType().getNaam());
            d.addListener(this);
            items.add(d);
            d = new StringProperty("VegetatieType", "Foto", knoop.getVegetatieType().getFoto());
            d.addListener(this);
            items.add(d);
        }
    }

    public void opslaan() {
        if (dto != null)
            controller.wijzigKnoop(dto);
        dto = null;
    }

}
