/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.KlimatogramController;
import dto.ContinentDto;
import dto.KlimatogramDto;
import dto.LandDto;
import java.io.IOException;
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
public class KlimatogramKiezenPanelController extends Pane {

    @FXML
    private ComboBox cboWerelddeel, cboLand;
    
    @FXML
    private ListView lstLocaties;
    
    private KlimatogramController controller;
    
    public KlimatogramKiezenPanelController(KlimatogramController controller) {
        this.controller=controller;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlimatogramKiezenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        ObservableList<String> continenten = FXCollections.observableArrayList();
        controller.getContinenten().forEach(c->continenten.add(c.getNaam()));
        cboWerelddeel.setItems(continenten);
        cboWerelddeel.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)->{
            if(newValue != null){
                ContinentDto dto = new ContinentDto();
                dto.setNaam(newValue.toString());
                controller.selecteerContinent(dto);
                ObservableList<String> landen = FXCollections.observableArrayList();
                controller.getLanden().forEach(l->landen.add(l.getNaam()));
                cboLand.setItems(landen);
                lstLocaties.setItems(FXCollections.observableArrayList());
                clearList();
            }
        });
        cboLand.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)->{
             if(newValue != null){
                 LandDto dto = new LandDto();
                 dto.setNaam(newValue.toString());
                 controller.selecteerLand(dto);
                 ObservableList<String> locaties = FXCollections.observableArrayList();
                 controller.getLocaties().forEach(l->locaties.add(l.getLocatie()));
                 lstLocaties.setItems(locaties);
                 clearList();
             }
        });
        lstLocaties.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)->{
            if(newValue != null){
                KlimatogramDto dto = new KlimatogramDto();
                dto.setLocatie(newValue.toString());
                controller.selecteerKlimatogram(dto);
            }
        });
    }
    
    @FXML
    public void voegKlimatogramToe(ActionEvent event){
        clearList();
        controller.notifyObservers("voegToe");
    }
    
    public void clearList(){
        lstLocaties.getSelectionModel().clearSelection();
    }
}
