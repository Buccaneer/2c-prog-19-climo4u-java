/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.DeterminatieController;
import controller.KlimatogramController;
import controller.LeerlingController;
import java.io.IOException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

/**
 * FXML Controller class
 *
 * @author Annemie
 */
public class KlimatogramFrameController extends AnchorPane {

    private KlimatogramController kController;
    private DeterminatieController dController;
    private LeerlingController lController;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab klimatogramTab;

    @FXML
    private Tab determinatieTab;

    @FXML
    private Tab klassenlijstenTab;

    @FXML
    private Tab toetsenTab;

    @FXML
    private StatusBar statusBar;

    private KlimatogramDetailPanelController kdpc;
    private KlimatogramKiezenPanelController kkpc;
    private DeterminatieTabellenOverzichtPaneController dtopc;
    private KlasLijstenDetailPanelController kldpc;
    private  KlasLijstenKiezenPanelController klkpc;
    private BoomPanelController bpc;
    private PropertyContainerPanelController pcpc;

    public KlimatogramFrameController(KlimatogramController kController, DeterminatieController dController, LeerlingController lController) {
        this.kController = kController;
        this.dController = dController;
        this.lController= lController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlimatogramFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        maakKlimatogrammenTab();
        maakDeterminerenTab();
        maakKlassenlijstenTab();
        maakToetsenTab();
    }

    private void maakKlimatogrammenTab() {
        kdpc = new KlimatogramDetailPanelController(this.kController, statusBar);
        kkpc = new KlimatogramKiezenPanelController(this.kController, statusBar);
        this.kController.addObserver(kdpc);
        this.kController.addObserver(kkpc);
        HBox content = new HBox();
        Pane pnlLinks = new Pane();
        VBox pnlRechts = new VBox();
        HBox.setHgrow(pnlRechts, Priority.ALWAYS);
        pnlLinks.getChildren().add(kkpc);
        content.getChildren().add(pnlLinks);
        pnlRechts.getChildren().add(kdpc);
        content.getChildren().add(pnlRechts);
        klimatogramTab.setContent(content);
    }

    private void maakDeterminerenTab() {
        dtopc = new DeterminatieTabellenOverzichtPaneController(this.dController);
        bpc = new BoomPanelController();
        pcpc = new PropertyContainerPanelController(this.dController);
        this.dController.addObserver(bpc);
        this.dController.addObserver(pcpc);
        bpc.addListener(pcpc);
        HBox content = new HBox();
        Pane pnlLinks = new Pane();
        VBox pnlRechts = new VBox();
        HBox.setHgrow(pnlRechts, Priority.ALWAYS);
        VBox.setVgrow(bpc, Priority.ALWAYS);
        pnlLinks.getChildren().add(dtopc);
        content.getChildren().add(pnlLinks);
        pnlRechts.getChildren().add(bpc);
        pnlRechts.getChildren().add(pcpc);
        content.getChildren().add(pnlRechts);
        determinatieTab.setContent(content);
    }

    private void maakKlassenlijstenTab() {
        klkpc = new KlasLijstenKiezenPanelController(lController, statusBar);
        kldpc = new KlasLijstenDetailPanelController(lController, statusBar);
        this.lController.addObserver(klkpc);
        this.lController.addObserver(kldpc);
         HBox content = new HBox();
        Pane pnlLinks = new Pane();
        VBox pnlRechts = new VBox();
        HBox.setHgrow(pnlRechts, Priority.ALWAYS);
        VBox.setVgrow(kldpc, Priority.ALWAYS);
        pnlLinks.getChildren().add(klkpc);
        content.getChildren().add(pnlLinks);
        pnlRechts.getChildren().add(kldpc);
        content.getChildren().add(pnlRechts);
        klassenlijstenTab.setContent(content);
    }

    private void maakToetsenTab() {

    }

}
