/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.DeterminatieKnoopDto;
import java.net.URL;
import java.util.ArrayList;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Jasper De Vrient
 */
public class NodePanelController extends Pane {
    
    @FXML private Label lblText;
    @FXML private Rectangle rect;
    
    private Paint origineel;
    private Paint geselecteerd;
    
    private List<NodeGeselecteerdListener> listeners = new ArrayList<>();

    private DeterminatieKnoopDto knoop;

    public NodePanelController(DeterminatieKnoopDto knoop) {
        this.knoop = knoop;
        this.setBackground(Background.EMPTY);
        origineel = rect.getFill();
        geselecteerd = Color.RED;
        
    }

    public DeterminatieKnoopDto getKnoop() {
        return knoop;
    }
    
    
    
    public void addListener(NodeGeselecteerdListener listener) {
        listeners.add(listener);
    }
    
    private void stelIn() {
        if (knoop.isBeslissingsKnoop())
        {
            
        } else {
            lblText.setText(knoop.getKlimaattype());
        }
    }
    
    public void selecteer() {
        listeners.forEach((NodeGeselecteerdListener l) -> l.selectieGewijzigd(knoop));
        rect.setFill(geselecteerd);
    }
    
    public void deselecteer() {
        rect.setFill(origineel);
    }
    
    
    
}
