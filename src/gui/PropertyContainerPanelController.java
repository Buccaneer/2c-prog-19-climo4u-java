/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.DeterminatieController;
import controller.Observer;
import domein.ParametersLinks;
import domein.ParametersRechts;
import domein.VergelijkingsOperator;
import dto.DeterminatieKnoopDto;
import dto.ParameterDto;
import dto.VegetatieTypeDto;
import dto.VergelijkingDto;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.controlsfx.control.PropertySheet;

/**
 * FXML Controller class
 *
 * @author Jasper De Vrient
 */
public class PropertyContainerPanelController extends BorderPane implements PropertyVeranderdListener, NodeGeselecteerdListener, Observer {

    @FXML
    private Button btnOpslaan;

    @FXML
    private Label lblStatus;

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
        btnOmzetten.setGraphic(new ImageView(new Image("/content/images/plus_small.png")));
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
        properties.setPadding(paneEigenschappen.getPadding());
    }

    private void veranderNullWaarden() {
        if (dto.isBeslissingsKnoop()) {
            if (dto.getVergelijking() == null) {
                dto.setVergelijking(new VergelijkingDto(new ParameterDto("", 0.0, true), VergelijkingsOperator.GELIJKAAN, new ParameterDto("", 0.0, true)));
            }

        } else if (dto.getVegetatieType() == null) {
            dto.setVegetatieType(new VegetatieTypeDto());
        }
    }

    @Override
    public void isVeranderd(PropertySheet.Item item) {
        veranderNullWaarden();
        switch (item.getName()) {
            case "Linker parameter": {
                ParametersLinks links = (ParametersLinks) item.getValue();

                dto.getVergelijking().setLinks(ParametersLinks.geefParameter(links));

            }
            break;
            /*case "Linker Constante waarde":
                dto.getVergelijking().setLinks(new ParameterDto("", (Double) item.getValue(), true));
                break;*/
            case "Operator":
                dto.getVergelijking().setOperator((VergelijkingsOperator) item.getValue());
                break;
            case "Rechter parameter": {
                ParametersRechts rechts = (ParametersRechts) item.getValue();

                dto.getVergelijking().setRechts(ParametersRechts.geefParameter(rechts));

            }
            break;
            case "Rechter Constante waarde":

                dto.getVergelijking().setRechts(new ParameterDto("", (Double) item.getValue(), true));
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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vulItemsIn();
            }
        });
    }

    @Override
    public void selectieGewijzigd(DeterminatieKnoopDto knoop) {
        // items aanpassen.
        dto = knoop;
        veranderNullWaarden();
        toonProperties();

        if (dto.isBeslissingsKnoop()) {
            btnOmzetten.setGraphic(new ImageView(new Image("/content/images/min_small.png")));
        } else {
            btnOmzetten.setGraphic(new ImageView(new Image("/content/images/plus_small.png")));
        }
        vulItemsIn();
    }

    private void vulItemsIn() {
        items.clear();

        if (dto.isBeslissingsKnoop()) {
            DeterminatieKnoopProperty d = new ParameterProperty("Links", "Linker parameter", ParametersLinks.geefParameters(dto.getVergelijking().getLinks()));
            items.add(d);
            d.addListener(this);

            /*if (items.stream().anyMatch(i -> i.getValue().toString().equals("Constante waarde") && i.getName().equals("Linker parameter"))) {
                d = new ConstanteWaardeProperty("Links", "Linker Constante waarde", dto.getVergelijking().getLinks().getWaarde());
                items.add(d);
                d.addListener(this);

            }*/

            d = new OperatorProperty("Operator", "Operator", dto.getVergelijking().getOperator());
            items.add(d);
            d.addListener(this);
            d = new ParameterProperty("Rechts", "Rechter parameter", ParametersRechts.geefParameters(dto.getVergelijking().getRechts()));

            items.add(d);
            d.addListener(this);
            if (items.stream().anyMatch(i -> i.getValue().toString().equals("Constante waarde") && i.getName().equals("Rechter parameter"))) {
                d = new ConstanteWaardeProperty("Rechts", "Rechter Constante waarde", dto.getVergelijking().getRechts().getWaarde());
                items.add(d);
                d.addListener(this);

            }
        } else {
            DeterminatieKnoopProperty d = new StringProperty("", "Klimaattype", dto.getKlimaattype());

            items.add(d);
            d.addListener(this);

            d = new StringProperty("VegetatieType", "Naam", dto.getVegetatieType().getNaam());

            items.add(d);
            d.addListener(this);

            d = new StringProperty("VegetatieType", "Foto", dto.getVegetatieType().getFoto());

            items.add(d);
            d.addListener(this);

        }
        items.stream().forEach((item) -> {
            ((DeterminatieKnoopProperty) item).bemerken();
        });
    }

    public void opslaan() {
        if (dto != null) {
            controller.wijzigKnoop(dto);
        }
        dto = null;
    }

    public void omzetten() {
        if (dto != null) {
            if (dto.isBeslissingsKnoop()) {
                dto.toResultaatBlad();
            } else {
                dto.toBeslissingsKnoop();
            }
            controller.wijzigKnoop(dto);
        }
    }

    @Override
    public void update(String actie, Object object) {
        if (actie.equals("verwijderen")) {
            this.setDisable(true);
        } else {
            this.setDisable(false);
        }

        try {
            controller.valideer();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Determinatatietabel voldoet zich aan de voorwaarden.");
        } catch (Exception ex) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Determinatatietabel voldoet zich niet aan de voorwaarden.");
        }
    }

}
