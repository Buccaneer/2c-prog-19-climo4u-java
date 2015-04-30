/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.LeerlingController;
import controller.Observer;
import dto.KlasDto;
import dto.LeerlingDto;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import org.controlsfx.control.StatusBar;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class KlasLijstenDetailPanelController extends VBox {

    @FXML
    private TableView<LeerlingDto> tblKlaslijsten;
    @FXML
    private TableColumn<LeerlingDto, String> colNaam;
    @FXML
    private TableColumn<LeerlingDto, String> colVoornaam;
    @FXML
    private TableColumn<LeerlingDto, KlasDto> colKlas;
    @FXML
    private Button btnToevoegen;
    @FXML
    private TextField txfNaam, txfVoornaam;
    private StatusBar statusBar;
    private LeerlingController controller;

    public KlasLijstenDetailPanelController(LeerlingController controller, StatusBar statusBar) {
        this.controller = controller;
        this.statusBar = statusBar;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlasLijstenDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(KlasLijstenDetailPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tblKlaslijsten.setItems(controller.getLeerlingen());

        colNaam.setCellValueFactory(cellData -> cellData.getValue().getNaam());
        colNaam.setCellFactory(TextFieldTableCell.forTableColumn());
        colNaam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LeerlingDto, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<LeerlingDto, String> event) {
                LeerlingDto dto = (LeerlingDto) event.getTableView().getItems().get(event.getTablePosition().getRow());
                dto.setNaam(new SimpleStringProperty(event.getNewValue()));
                controller.wijzigLeerling(dto);
            }
        });

        colVoornaam.setCellValueFactory(cellData -> cellData.getValue().getVoornaam());
        colVoornaam.setCellFactory(TextFieldTableCell.forTableColumn());
        colVoornaam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LeerlingDto, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<LeerlingDto, String> event) {
                LeerlingDto dto = (LeerlingDto) event.getTableView().getItems().get(event.getTablePosition().getRow());
                dto.setVoornaam(new SimpleStringProperty(event.getNewValue()));
                controller.wijzigLeerling(dto);
            }
        });

        tblKlaslijsten.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                controller.selecteerLeerling(newValue);
            }
        });

        TableColumn col_action = new TableColumn<>("");
        tblKlaslijsten.getColumns().add(col_action);

        col_action.setPrefWidth(35);
        col_action.setSortable(false);
        col_action.setResizable(false);
        col_action.setEditable(false);
        col_action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LeerlingDto, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<LeerlingDto, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        col_action.setCellFactory(new Callback<TableColumn<LeerlingDto, Boolean>, TableCell<LeerlingDto, Boolean>>() {
            @Override
            public TableCell<LeerlingDto, Boolean> call(TableColumn<LeerlingDto, Boolean> p) {
                return new ButtonCell();
            }

        });

        colKlas.setCellValueFactory(cellData->cellData.getValue().getKlas());
        colKlas.setCellFactory(ComboBoxTableCell.forTableColumn(controller.getAlleKlassen()));
        colKlas.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LeerlingDto, KlasDto>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<LeerlingDto, KlasDto> event) {
                LeerlingDto leerling = (LeerlingDto) event.getTableView().getItems().get(event.getTablePosition().getRow());
                controller.selecteerLeerling(leerling);
                leerling.setKlas(event.getNewValue());
                controller.wijzigLeerling(leerling);
            }
        });

    }

    private class ButtonCell extends TableCell<LeerlingDto, Boolean> {

        final Button cellButton = new Button("x");

        ButtonCell() {
            cellButton.getStyleClass().add("cancel");
            cellButton.setPrefSize(25, 25);
            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    LeerlingDto dto = (LeerlingDto) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                    controller.selecteerLeerling(dto);
                    controller.verwijderLeerling();
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

    @FXML
    private void leerlingToevoegen(ActionEvent e) {
        clearErrors();
        if (!(txfNaam.getText().isEmpty() || txfVoornaam.getText().isEmpty())) {
            try {
                LeerlingDto dto = new LeerlingDto();
                dto.setNaam(new SimpleStringProperty(txfNaam.getText()));
                dto.setVoornaam(new SimpleStringProperty(txfVoornaam.getText()));
                controller.maakNieuweLeerling(dto);
                txfNaam.setText("");
                txfVoornaam.setText("");
            } catch (IllegalArgumentException ex) {
                statusBar.setText(ex.getMessage());
            }
        }else{
            statusBar.setText("Gelieve beide velden in te vullen");
        }
    }

    private void clearErrors() {
        statusBar.setText("");
    }
}
