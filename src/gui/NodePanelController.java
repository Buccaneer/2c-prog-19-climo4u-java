/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.DeterminatieKnoopDto;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Jasper De Vrient
 */
public class NodePanelController extends StackPane {
    
    @FXML private Label lblText;
    @FXML private Rectangle rect;
    
    private Paint origineel;
    private Paint geselecteerd;
    
    private List<NodeGeselecteerdListener> listeners = new ArrayList<>();

    private DeterminatieKnoopDto knoop;

    public NodePanelController(DeterminatieKnoopDto knoop) {
        
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("NodePanel.fxml"));
//        //loader.setRoot(null);
//        loader.setRoot(this);
//        //loader.setController(this);
//        try {
//            loader.load();
//        } catch (IOException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
        this.knoop = knoop;
//        origineel = rect.getFill();
//        geselecteerd = Color.RED;
    }

    public DeterminatieKnoopDto getKnoop() {
        return knoop;
    }
    
    public void load()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NodePanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        origineel = rect.getFill();
        geselecteerd = Color.RED;
    }
    
    public void addListener(NodeGeselecteerdListener listener) {
        listeners.add(listener);
    }
    
    public void stelIn() {
        if (knoop.isBeslissingsKnoop())
        {
            lblText.setText(knoop.getVergelijking().toString());
        } else {
            lblText.setText(knoop.getKlimaattype());
        }
    }
    
    public void selecteer() {
    
        rect.setFill(geselecteerd);
    }
    
    public void selecteerMuis() {
            listeners.forEach((NodeGeselecteerdListener l) -> l.selectieGewijzigd(knoop));
            selecteer();
    }
    
    public void deselecteer() {
        rect.setFill(origineel);
    }
    
    public void rect()
    {
        rect.setWidth(this.getMinWidth());
        rect.setHeight(this.getMinHeight());
    }
    
    
    @FXML
    private void muisBinnen() {
        cursorProperty().setValue(Cursor.HAND);
    }
    
    
}
