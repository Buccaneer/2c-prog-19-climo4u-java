/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.Observer;
import dto.DeterminatieKnoopDto;
import dto.DeterminatieTabelDto;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Jasper De Vrient
 */
public class BoomPanelController extends ScrollPane implements NodeGeselecteerdListener, Observer{

     private List<NodeGeselecteerdListener> listeners = new ArrayList<>();  
     private List<NodePanelController> nodes = new ArrayList<>();
     private DeterminatieKnoopDto eersteKnoop;

     
     @FXML private AnchorPane content;
     
     
    public boolean addListener(NodeGeselecteerdListener e) {
        
        return listeners.add(e);
        
    }

    @Override
    public void selectieGewijzigd(DeterminatieKnoopDto knoop) { 
       // 1. alle selecties die niet deze knoop zijn worden gedeselecteerd. In nodes.
        // 2. Event werpen
        for (NodePanelController n : nodes)
        {
            if (!n.getKnoop().equals(knoop))
                n.deselecteer();
            else
                n.selecteer();
        }
        listeners.forEach((NodeGeselecteerdListener l) -> l.selectieGewijzigd(knoop));
    }

    @Override
    public void update(String actie, Object object) {
        eersteKnoop = (DeterminatieKnoopDto) object;
        herteken();
    }
    
    private void herteken() {
        // Fancy stuff voor determinatietabel goed weer te geven.
        ObservableList<Node> controls = content.getChildren();
        controls.clear();
        nodes.clear();
        maakNodePanelControllers(eersteKnoop);
    }
    
    private void maakNodePanelControllers(DeterminatieKnoopDto knoop)
    {
        if (knoop != null)
        {
            nodes.add(new NodePanelController(knoop));
            maakNodePanelControllers(knoop.getJa());
            maakNodePanelControllers(knoop.getNee());
        } 
    }
    
     
     
}
