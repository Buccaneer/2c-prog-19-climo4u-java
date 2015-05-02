package gui;

import controller.ToetsController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

public class ToetsenDetailPanelController extends VBox
{
    private ToetsController controller;
    private StatusBar statusBar;
    
    public ToetsenDetailPanelController(ToetsController controller, StatusBar statusBar) {
        this.controller = controller;
        this.statusBar = statusBar;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToetsenDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(KlasLijstenKiezenPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}