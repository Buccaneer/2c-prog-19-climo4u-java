/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.LeerlingController;
import controller.Observer;
import dto.GraadDto;
import dto.KlasDto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class KlasLijstenKiezenPanelController extends VBox implements Observer {

    @FXML
    private ComboBox<GraadDto> cboGraad;
    @FXML
    private ListView<KlasDto> lstKlas;
    @FXML
    private Button btnToevoegen;
    @FXML
    private Button btnWijzigen;
    @FXML
    private Button btnVerwijderen;
    private LeerlingController controller;
    private StatusBar statusBar;

    public KlasLijstenKiezenPanelController(LeerlingController controller, StatusBar statusBar) {
        this.controller = controller;
        this.statusBar = statusBar;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlasLijstenKiezenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(KlasLijstenKiezenPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cboGraad.setItems(controller.getGraden());
        lstKlas.setItems(controller.getKlassen());
        cboGraad.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                controller.selecteerGraad(newValue);
            }
        });
        lstKlas.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                controller.selecteerKlas(newValue);
            }
        });
    }

    @Override
    public void update(String actie, Object object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @FXML
    public void voegKlasToe(ActionEvent e){
        
    }
    
    @FXML
    public void wijzigKlas(ActionEvent e){
        
    }
    
    @FXML
    public void verwijderKlas(ActionEvent e){
        
    }
}
