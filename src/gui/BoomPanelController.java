/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.Observer;
import dto.DeterminatieKnoopDto;
import dto.DeterminatieTabelDto;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author Jasper De Vrient
 */
public class BoomPanelController extends ScrollPane implements NodeGeselecteerdListener, Observer
{

    private List<NodeGeselecteerdListener> listeners = new ArrayList<>();
    private List<NodePanelController> nodes = new ArrayList<>();
    private List<Verbinding> verbindingen = new ArrayList();
    private DeterminatieKnoopDto eersteKnoop;

    private int x = 0;
    private int y = 0;
    private final static int HOOGTE = 40;
    private final static int BREEDTE = 200;
    private final static int MARGE_H = 50;
    private final static int MARGE_B = 50;
    
    @FXML
    private Pane content;

    public BoomPanelController()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BoomPanel.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public boolean addListener(NodeGeselecteerdListener e)
    {
        return listeners.add(e);
    }

    @Override
    public void selectieGewijzigd(DeterminatieKnoopDto knoop)
    {
        // 1. alle selecties die niet deze knoop zijn worden gedeselecteerd. In nodes.
        // 2. Event werpen
        for (NodePanelController n : nodes)
        {
            if (n.getKnoop().getId() != knoop.getId())
            {
                n.deselecteer();
            }
            else
            {
                n.selecteer();
            }
        }
        listeners.forEach((NodeGeselecteerdListener l) -> l.selectieGewijzigd(knoop));
    }

    @Override
    public void update(String actie, Object object)
    {
        eersteKnoop = (DeterminatieKnoopDto) object;
        log(eersteKnoop);
        herteken();
    }
    
    private void log(DeterminatieKnoopDto k)
    {
        if (k != null)
        {
            log(k.getJa());
            log(k.getNee());
        }
    }

    private class Verbinding
    {

        private boolean jaVerbinding;
        private NodePanelController begin;
        private NodePanelController einde;

        public Verbinding(NodePanelController begin, boolean jaVerbinding)
        {
            this.begin = begin;
            this.jaVerbinding = jaVerbinding;
        }

        public void setEinde(NodePanelController einde)
        {
            this.einde = einde;
        }

        public List<Shape> maakVerbinding()
        {
            List<Shape> verbinding = new ArrayList();
            Point2D beginPunt;
            Point2D tussenPunt;
            Point2D eindPunt;
            if (jaVerbinding)
            {
                beginPunt = new Point2D(begin.getLayoutX() + begin.getMinWidth(), begin.getLayoutY() + begin.getMinHeight() / 2);
                eindPunt = new Point2D(einde.getLayoutX(), einde.getLayoutY() + einde.getMinHeight() / 2);
                Line lijn = maakLijn(beginPunt.getX(), beginPunt.getY(), eindPunt.getX(), eindPunt.getY());
                verbinding.add(lijn);
                Text label = maakLabel(beginPunt.getX() + (eindPunt.getX() - beginPunt.getX()) / 2 - 10, eindPunt.getY(), "Ja");
                verbinding.add(label);
                Polygon driehoek = maakDriehoek(eindPunt.getX(), eindPunt.getY(), eindPunt.getX() - 8, eindPunt.getY() - 4, eindPunt.getX() - 8, eindPunt.getY() + 4);
                verbinding.add(driehoek);
            }
            else
            {
                if (einde.getKnoop().isResultaatBlad())
                {
                    beginPunt = new Point2D(begin.getLayoutX() + begin.getMinWidth() / 2, begin.getLayoutY() + begin.getMinHeight());
                    eindPunt = new Point2D(einde.getLayoutX(), einde.getLayoutY() + einde.getMinHeight() / 2);
                    tussenPunt = new Point2D(beginPunt.getX(), eindPunt.getY());
                    Line lijn1 = maakLijn(beginPunt.getX(), beginPunt.getY(), tussenPunt.getX(), tussenPunt.getY());
                    verbinding.add(lijn1);
                    Line lijn2 = maakLijn(tussenPunt.getX(), tussenPunt.getY(), eindPunt.getX(), eindPunt.getY());
                    verbinding.add(lijn2);
                    Text label = maakLabel(tussenPunt.getX() + (eindPunt.getX() - tussenPunt.getX()) / 2 - 10, eindPunt.getY(), "Nee");
                    verbinding.add(label);
                    Polygon driehoek = maakDriehoek(eindPunt.getX(), eindPunt.getY(), eindPunt.getX() - 8, eindPunt.getY() - 4, eindPunt.getX() - 8, eindPunt.getY() + 4);
                    verbinding.add(driehoek);
                }
                else
                {
                    beginPunt = new Point2D(begin.getLayoutX() + begin.getMinWidth() / 2, begin.getLayoutY() + begin.getMinHeight());
                    eindPunt = new Point2D(einde.getLayoutX() + einde.getMinWidth() / 2, einde.getLayoutY());
                    Line lijn = maakLijn(beginPunt.getX(), beginPunt.getY(), eindPunt.getX(), eindPunt.getY());
                    verbinding.add(lijn);
                    Text label = maakLabel(beginPunt.getX() + 2, beginPunt.getY() + (eindPunt.getY() - beginPunt.getY()) / 2, "Nee");
                    verbinding.add(label);
                    Polygon driehoek = maakDriehoek(eindPunt.getX(), eindPunt.getY(), eindPunt.getX() - 4, eindPunt.getY() - 8, eindPunt.getX() + 4, eindPunt.getY() - 8);
                    verbinding.add(driehoek);
                }
            }
            return verbinding;
        }

        private Line maakLijn(double xBegin, double yBegin, double xEinde, double yEinde)
        {
            Line lijn = new Line(xBegin, yBegin, xEinde, yEinde);
            lijn.setFill(Color.BLACK);
            lijn.setStrokeWidth(1);
            return lijn;
        }

        private Text maakLabel(double x, double y, String tekst)
        {
            Text label = new Text(x, y - 2, tekst);
            label.setTextAlignment(TextAlignment.LEFT);
            label.setFill(Color.BLACK);
            return label;
        }

        private Polygon maakDriehoek(double... points)
        {
            Polygon driehoek = new Polygon(points);
            driehoek.setFill(Color.BLACK);
            return driehoek;
        }

    }

    private void herteken()
    {
        x = 50;
        y = 50;
        ObservableList<Node> controls = content.getChildren();
        controls.clear();
        nodes.clear();
        verbindingen.clear();
        maakNodePanelControllers(eersteKnoop, 50, null);
        for (NodePanelController n : nodes)
        {
            if (!n.getKnoop().isBeslissingsKnoop())
            {
                n.setLayoutX(x);
            }
            n.stelIn();
            controls.add(n);
        }
        
        for (Verbinding v : verbindingen)
        {
            controls.addAll(v.maakVerbinding());
        }
    }

    private void maakNodePanelControllers(DeterminatieKnoopDto knoop, int x, Verbinding verbinding)
    {
        if (knoop != null)
        {
            if (x > this.x)
            {
                this.x = x;
            }
            NodePanelController npc = new NodePanelController(knoop);
            npc.load();
            npc.setMinHeight(HOOGTE);
            npc.setMaxHeight(HOOGTE);
            npc.setMinWidth(BREEDTE);
            npc.setMaxWidth(BREEDTE);      
            npc.rect();
            npc.setLayoutX(x);
            npc.setLayoutY(y);
            npc.addListener(this);
            nodes.add(npc);
            if (knoop.isResultaatBlad())
            {
                y += HOOGTE + MARGE_H;
            }
            if (verbinding != null)
            {
                verbinding.setEinde(npc);
                verbindingen.add(verbinding);
            }
            Verbinding jaVerbinding = new Verbinding(npc, true);
            Verbinding neeVerbinding = new Verbinding(npc, false);
            maakNodePanelControllers(knoop.getJa(), x + BREEDTE + MARGE_B, jaVerbinding);
            maakNodePanelControllers(knoop.getNee(), x, neeVerbinding);
        }
    }

}
