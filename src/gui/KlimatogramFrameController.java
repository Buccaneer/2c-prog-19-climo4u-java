/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.DeterminatieController;
import controller.KlimatogramController;
import java.io.IOException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

/**
 * FXML Controller class
 *
 * @author Annemie
 */
public class KlimatogramFrameController extends GridPane
{

    private KlimatogramController kController;
    private DeterminatieController dController;

    //@FXML
    //private SplitPane splSplitPane;

    @FXML
    private Pane pnlLinks;

    @FXML
    private VBox pnlRechts;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuBar logBar;

    private KlimatogramDetailPanelController kdpc;
    private KlimatogramKiezenPanelController kkpc;
    private DeterminatieTabellenOverzichtPaneController dtopc;
    private BoomPanelController bpc;
    private PropertyContainerPanelController pcpc;

    private StatusBar statusBar = new StatusBar();

    public KlimatogramFrameController(KlimatogramController kController, DeterminatieController dController)
    {
        this.kController = kController;
        this.dController = dController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlimatogramFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        statusBar.setText("");
        this.add(statusBar, 0, 2);
        //toonKlimatogrammen();
        toonDetermineren();
        stelMenuBalkIn();
    }

    private void clear()
    {
        pnlLinks.getChildren().clear();
        pnlRechts.getChildren().clear();
    }

    @FXML
    public void toonKlimatogrammen()
    {
        System.out.println("TOON KLIMATOMATEN");
        clear();
        if (kdpc == null || kkpc == null)
        {
            kdpc = new KlimatogramDetailPanelController(this.kController, statusBar);
            kkpc = new KlimatogramKiezenPanelController(this.kController, statusBar);
            this.kController.addObserver(kdpc);
            this.kController.addObserver(kkpc);
        }
        pnlLinks.getChildren().add(kkpc);
        pnlRechts.getChildren().add(kdpc);
    }

    @FXML
    public void toonDetermineren()
    {
        System.out.println("TOON DETERMIPEREN");
        if (dtopc == null || bpc == null || pcpc == null)
        {
            dtopc = new DeterminatieTabellenOverzichtPaneController(dController);
            bpc = new BoomPanelController();
            pcpc = new PropertyContainerPanelController(dController);
            dController.addObserver(bpc);
            bpc.addListener(pcpc);
        }
        clear();
        pnlLinks.getChildren().add(dtopc);
        pnlRechts.getChildren().add(bpc);
        pnlRechts.getChildren().add(pcpc);
    }

    @FXML
    public void toonKlassenlijsten()
    {
        clear();
    }

    @FXML
    public void toonToetsen()
    {
        clear();
    }
    
    private void stelMenuBalkIn()
    {
        for (Menu m : menuBar.getMenus())
        {
            if (m.getId().equals("mnuKlimatogrammen"))
            {
                Label lbl = new Label("Klimatogrammen");
                lbl.setOnMouseClicked(new EventHandler<MouseEvent>()
                {

                    @Override
                    public void handle(MouseEvent t)
                    {
                        toonKlimatogrammen();
                    }
                });
                m.setGraphic(lbl);
            }
            else if (m.getId().equals("mnuDetermineren"))
            {
                Label lbl = new Label("Determineren");
                lbl.setOnMouseClicked(new EventHandler<MouseEvent>()
                {

                    @Override
                    public void handle(MouseEvent t)
                    {
                        toonDetermineren();
                    }
                });
                m.setGraphic(lbl);
            }
            else if (m.getId().equals("mnuKlaslijsten"))
            {
                Label lbl = new Label("Klaslijsten");
                lbl.setOnMouseClicked(new EventHandler<MouseEvent>()
                {

                    @Override
                    public void handle(MouseEvent t)
                    {
                        toonKlassenlijsten();
                    }
                });
                m.setGraphic(lbl);
            }
            else if (m.getId().equals("mnuToetsen"))
            {
                Label lbl = new Label("Toetsen");
                lbl.setOnMouseClicked(new EventHandler<MouseEvent>()
                {

                    @Override
                    public void handle(MouseEvent t)
                    {
                        toonToetsen();
                    }
                });
                m.setGraphic(lbl);
            }
        }
    }

}
