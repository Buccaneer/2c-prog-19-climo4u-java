/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.KlimatogramController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Annemie
 */
public class KlimatogramFrameController extends GridPane{

    private KlimatogramController controller;
    
    @FXML
    private SplitPane splSplitPane;
    
    @FXML
    private Pane pnlLinks;
    
    @FXML
    private Pane pnlRechts;
    
    public KlimatogramFrameController(KlimatogramController controller) {
        this.controller = controller;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlimatogramFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        KlimatogramDetailPanelController rechtsePaneel = new KlimatogramDetailPanelController(this.controller);
        KlimatogramKiezenPanelController linksePaneel = new KlimatogramKiezenPanelController(this.controller);
        this.controller.addObserver(rechtsePaneel);
        this.controller.addObserver(linksePaneel);
        pnlLinks.getChildren().add(linksePaneel);
        pnlRechts.getChildren().add(rechtsePaneel);
    }
    
    
}
