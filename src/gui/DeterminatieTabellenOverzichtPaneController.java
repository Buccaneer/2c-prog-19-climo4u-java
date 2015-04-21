/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.DeterminatieController;
import controller.Observer;
import controller.Subject;
import dto.DeterminatieTabelDto;
import dto.GraadDto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jasper De Vrient
 */
public class DeterminatieTabellenOverzichtPaneController extends VBox  {
    @FXML
    private ListView<DeterminatieTabelDto> lstDeterminatieTabellen;
    @FXML
    private Button btnVerwijderen;
    @FXML
    private TextField txtNaam;
    @FXML
    private Button btnToevoegen;
    
    @FXML
    private ComboBox<GraadDto> cboGraden;
    private DeterminatieController controller;
    private DeterminatieTabelDto geselecteerde;

    public DeterminatieTabellenOverzichtPaneController(DeterminatieController determinatie) {
       this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeterminatieTabellenOverzichtPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }    
        
        vulLijstenDeterminatieOp();
        
        vulLijstenGradenOp();
        
        voegEventToeVoorLijst();
        
    }

    private void voegEventToeVoorLijst() {
        lstDeterminatieTabellen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
           
            if (newValue != null) {
                geselecteerde = newValue;
                controller.selecteerDeterminatieTabel(newValue);
                txtNaam.setText(newValue.toString());
            }
        });
    }
    
    private void voegEventToeVoorComboBox() {
        cboGraden.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                controller.koppelGraadMetDeterminatieTabel(newValue, geselecteerde);
            }
        });
    }

    private void vulLijstenDeterminatieOp() {
       ObservableList<DeterminatieTabelDto> list =  controller.getDeterminatieTabellen();
       lstDeterminatieTabellen.setItems(list);
    }
    
    @FXML
    private void voegDeterminatieTabelToe() {
        controller.maakNieuweDeterminatieTabel();
        controller.setNaamDeterminatieTabel(txtNaam.getText());
        
    }
    @FXML
    private void verwijderDeterminatieTabel() {
        controller.verwijderDeterminatieTabel(geselecteerde);
    }

    private void vulLijstenGradenOp() {
          ObservableList<GraadDto> list =  controller.getGraden();
          
          cboGraden.setItems(list);
    }


 
    
}
