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
import dto.ParameterDto;
import dto.VegetatieTypeDto;
import dto.VergelijkingDto;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
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
    private Button btnOmzetten;
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
            properties.setModeSwitcherVisible(false);
            properties.setSearchBoxVisible(false);
            paneEigenschappen.getChildren().clear();
            paneEigenschappen.getChildren().add(properties);
        }
        properties.setPrefSize(paneEigenschappen.getPrefWidth(), paneEigenschappen.getPrefHeight());
    }

    private void veranderNullWaarden() {
        if (dto.isBeslissingsKnoop()) {
            if (dto.getVergelijking() == null)
                dto.setVergelijking(new VergelijkingDto(new ParameterDto("", 0.0), VergelijkingsOperator.GELIJKAAN, new ParameterDto("", 0.0)));

        } else if (dto.getVegetatieType() == null)
            dto.setVegetatieType(new VegetatieTypeDto());
    }

    @Override
    public void isVeranderd(PropertySheet.Item item) {
        veranderNullWaarden();
        switch (item.getName()) {
            case "Linker parameter": {
                Parameters links = (Parameters) item.getValue();
                if (links != Parameters.CONSTANTEWAARDE) {
                    PropertySheet.Item i =  items.get(1);
                    i.setValue(0.0);
                   
                          
                    dto.getVergelijking().setLinks(Parameters.geefParameter(links));
                }
            }
            break;
            case "Linker Constante waarde":
                if ((Double)item.getValue() != 0.0)
                    items.get(0).setValue(Parameters.CONSTANTEWAARDE);
                dto.getVergelijking().setLinks(new ParameterDto("", (Double) item.getValue()));
                break;
            case "Operator":
                dto.getVergelijking().setOperator((VergelijkingsOperator) item.getValue());
                break;
            case "Rechter parameter": {
                Parameters rechts = (Parameters) item.getValue();
                if (rechts != Parameters.CONSTANTEWAARDE) {
                        PropertySheet.Item i =  items.get(4);
                    i.setValue(0.0);
                    dto.getVergelijking().setRechts(Parameters.geefParameter(rechts));
                }
            }
            break;
            case "Rechter Constante waarde":
                 if ((Double)item.getValue() != 0.0)
                    items.get(3).setValue(Parameters.CONSTANTEWAARDE);
                dto.getVergelijking().setRechts(new ParameterDto("", (Double) item.getValue()));
                break;
            case "Klimaattype":
                dto.setKlimaattype((String) item.getValue());
                break;
            case "Naam":
                dto.getVegetatieType().setNaam((String) item.getValue());
                break;
            case "Foto":
                dto.getVegetatieType().setFoto((String) item.getValue());
                break;
        }
        
     
    }

    @Override
    public void selectieGewijzigd(DeterminatieKnoopDto knoop) {
        // items aanpassen.
        dto = knoop;
        veranderNullWaarden();
        toonProperties();
        
        if (dto.isBeslissingsKnoop())
            btnOmzetten.setText("-");
        else
            btnOmzetten.setText("+");
        items.clear();
        if (dto.isBeslissingsKnoop()) {
            DeterminatieKnoopProperty d = new ParameterProperty("Links", "Linker parameter", Parameters.geefParameters(dto.getVergelijking().getLinks()));
            d.addListener(this);
            items.add(d);
            d = new ConstanteWaardeProperty("Links", "Linker Constante waarde", dto.getVergelijking().getLinks().getWaarde());
            d.addListener(this);
            items.add(d);
            d = new OperatorProperty("Operator", "Operator", dto.getVergelijking().getOperator());
            d.addListener(this);
            items.add(d);
            d = new ParameterProperty("Rechts", "Rechter parameter", Parameters.geefParameters(dto.getVergelijking().getRechts()));
            d.addListener(this);
            items.add(d);
            d = new ConstanteWaardeProperty("Rechts", "Rechter Constante waarde", dto.getVergelijking().getRechts().getWaarde());
            d.addListener(this);
            items.add(d);
        } else {
            DeterminatieKnoopProperty d = new StringProperty("", "Klimaattype", dto.getKlimaattype());
            d.addListener(this);
            items.add(d);
            d = new StringProperty("VegetatieType", "Naam", dto.getVegetatieType().getNaam());
            d.addListener(this);
            items.add(d);
            d = new StringProperty("VegetatieType", "Foto", dto.getVegetatieType().getFoto());
            d.addListener(this);
            items.add(d);
        }
    }

    public void opslaan() {
        if (dto != null)
            controller.wijzigKnoop(dto);
        dto = null;
    }
    
    public void omzetten() {
        if (dto.isBeslissingsKnoop())
            dto.toResultaatBlad();
        else
            dto.toBeslissingsKnoop();
        opslaan();
    }

}
