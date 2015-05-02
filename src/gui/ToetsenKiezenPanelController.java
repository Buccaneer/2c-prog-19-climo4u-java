package gui;

import controller.*;
import dto.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

public class ToetsenKiezenPanelController extends VBox
{
    private ToetsController controller;
    private StatusBar statusBar;
    
    @FXML
    TableView<ToetsDto> tblToetsen;
    
    @FXML
    TableColumn colNaam;
    
    @FXML
    TableColumn colGraad;
    
    @FXML
    TextField txfNaam;
    
    @FXML
    TextField txfGraad;
    
    @FXML
    Button btnToetsToevoegen;
    
    @FXML
    TextField txfTitel;
    
    @FXML
    TextField txfBeschrijving;
    
    @FXML
    TextField txfStart;
    
    @FXML
    TextField txfEinde;
    
    @FXML
    TableView tblKlassen;
    
    @FXML
    TableColumn colKlas;
    
    @FXML
    TextField txfKlas;
    
    @FXML
    Button btnKlasToevoegen;
    
    public ToetsenKiezenPanelController(ToetsController controller, StatusBar statusBar) {
        this.controller = controller;
        this.statusBar = statusBar;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToetsenKiezenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(KlasLijstenKiezenPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //TODO: UNCOMMENT ZODRA CONTROLLER WERKT
        //tblToetsen.setItems(controller.geefToetsen());
        tblToetsen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                //TODO: UNCOMMENT ZODRA CONTROLLER WERKT
                //controller.selecteerToets(newValue);
            }
        });
        btnToetsToevoegen.setGraphic(new ImageView(new Image("/content/images/plus_small.png")));
        btnKlasToevoegen.setGraphic(new ImageView(new Image("/content/images/plus_small.png")));
    }
    
    @FXML
    private void toetsToevoegen()
    {
        
    }
    
    @FXML
    private void klasToevoegen()
    {
        
    }
    
    @FXML
    private void todo()
    {
        
    }
    
}
