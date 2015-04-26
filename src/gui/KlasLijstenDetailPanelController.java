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
import dto.MaandDto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.controlsfx.control.StatusBar;
import sun.plugin2.jvm.RemoteJVMLauncher;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class KlasLijstenDetailPanelController extends HBox implements Observer{
    @FXML
    private TableView<LeerlingDto> tblKlaslijsten;
    @FXML
    private TableColumn<LeerlingDto, String> colNaam;
    @FXML
    private TableColumn<LeerlingDto, String> colVoornaam;
    @FXML
    private TableColumn<KlasDto, String> colKlas;
    @FXML
    private TableColumn<LeerlingDto, Button> colDel;
    private StatusBar statusBar;
    private LeerlingController controller;

    public KlasLijstenDetailPanelController(LeerlingController controller, StatusBar statusBar) {
        this.controller = controller;
        this.statusBar = statusBar;
        
        FXMLLoader loader =new FXMLLoader(getClass().getResource("KlasLijstenDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(KlasLijstenDetailPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tblKlaslijsten.setItems(controller.getLeerlingen());
        
        colNaam.setCellValueFactory(cellData-> cellData.getValue().getNaam());
        colNaam.setCellFactory(TextFieldTableCell.forTableColumn());
        colNaam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LeerlingDto, String>>(){
            @Override
            public void handle(TableColumn.CellEditEvent<LeerlingDto, String> event) {
                  ((LeerlingDto) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow())).setNaam(new SimpleStringProperty(event.getNewValue()));
            }
        });
        
        colVoornaam.setCellValueFactory(cellData->cellData.getValue().getVoornaam());
        colVoornaam.setCellFactory(TextFieldTableCell.forTableColumn());
        colNaam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LeerlingDto, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<LeerlingDto, String> event) {
                ((LeerlingDto) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow())).setVoornaam(new SimpleStringProperty(event.getNewValue()));
            }
        });
        
        colKlas.setCellValueFactory(null);
        
        colDel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LeerlingDto, Button>, ObservableValue<Button>>() {

            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<LeerlingDto, Button> param) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }    

    @Override
    public void update(String actie, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
