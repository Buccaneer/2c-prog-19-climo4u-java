/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.klassenlijst.*;
import gui.klimatogram.*;
import gui.determinatie.*;
import gui.toets.*;
import controller.DeterminatieController;
import controller.KlimatogramController;
import controller.LeerlingController;
import controller.ToetsController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.FadeInUpTransition;

/**
 * FXML Controller class
 *
 * @author Herudi
 */
public class Menu extends AnchorPane {

    @FXML
    private Button close;
    @FXML
    private Button maximize;
    @FXML
    private Button minimize;
    @FXML
    private Button resize;
    @FXML
    private Button fullscreen;
    @FXML
    private Text titel;
    private Stage stage;
    private Rectangle2D rec2;
    private Double w, h;
    @FXML
    private ListView<String> listMenu;
    @FXML
    private AnchorPane paneData;
    @FXML
    private Button btnLogout;
    private double xOffset = 0, yOffset = 0;
    private KlimatogramController kController;
    private DeterminatieController dController;
    private LeerlingController lController;
    private ToetsController tController;
    @FXML
    private Label statusBar;
    @FXML
    private ImageView imgLoad;
    private HBox determinerenTab, klimatogramTab, toetsenTab, klaslijstenTab;

    public Menu(KlimatogramController klimatogramController, DeterminatieController determinatieController, LeerlingController leerlingController, ToetsController toetsController) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        rec2 = Screen.getPrimary().getVisualBounds();
        w = 0.1;
        h = 0.1;
        listMenu.getItems().addAll("Klimatogrammen", "Determinatietabellen", "Klassenlijsten", "Toetsen");
        Platform.runLater(() -> {

            //stage = (Stage) maximize.getScene().getWindow();
            //stage.setMaximized(true);
            //stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
            resize.setVisible(false);
            //listMenu.getSelectionModel().select(0);
            maakKlimatogramTab();
            //listMenu.requestFocus();

        });
        this.kController = klimatogramController;
        this.dController = determinatieController;
        this.lController = leerlingController;
        this.tController = toetsController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void titleClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                maximize();
            }
        }
    }

    @FXML
    private void dragPressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void drag(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    private void maximize() {
        stage = (Stage) maximize.getScene().getWindow();
        if (stage.isMaximized()) {
            //   if (w == rec2.getWidth() && h == rec2.getHeight()) {
            stage.setMaximized(false);
            stage.setHeight(800);
            stage.setWidth(1280);
            stage.centerOnScreen();
            maximize.getStyleClass().remove("decoration-button-restore");
            resize.setVisible(true);
//            } else {
//                stage.setMaximized(false);
//                maximize.getStyleClass().remove("decoration-button-restore");
//                resize.setVisible(true);
//            }

        } else {
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
            resize.setVisible(false);
        }
    }

    @FXML
    private void maximize(ActionEvent event) {
        stage = (Stage) maximize.getScene().getWindow();
        if (stage.isMaximized()) {
//            if (w == rec2.getWidth() && h == rec2.getHeight()) {
            stage.setMaximized(false);
            stage.setHeight(800);
            stage.setWidth(1280);
            stage.centerOnScreen();
            maximize.getStyleClass().remove("decoration-button-restore");
            resize.setVisible(true);
//            } else {
//                stage.setMaximized(false);
//                maximize.getStyleClass().remove("decoration-button-restore");
//                resize.setVisible(true);
//            }

        } else {
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
            resize.setVisible(false);
        }
    }

    @FXML
    private void minimize(ActionEvent event) {
        stage = (Stage) minimize.getScene().getWindow();
        if (stage.isMaximized()) {
            w = rec2.getWidth();
            h = rec2.getHeight();
            stage.setMaximized(false);
            stage.setHeight(h);
            stage.setWidth(w);
            stage.centerOnScreen();
            Platform.runLater(() -> {
                stage.setIconified(true);
            });
        } else {
            stage.setIconified(true);
        }
    }

    @FXML
    private void resize(MouseEvent event) {
        double newX = event.getScreenX() - stage.getX() + 13;
        double newY = event.getScreenY() - stage.getY() + 10;
        if (newX % 5 == 0 || newY % 5 == 0) {
            if (newX > 1280) {
                stage.setWidth(newX);
            } else {
                stage.setWidth(1280);
            }

            if (newY > 800) {
                stage.setHeight(newY);
            } else {
                stage.setHeight(800);
            }
        }
    }

    @FXML
    private void fullscreen(ActionEvent event) {
        stage = (Stage) fullscreen.getScene().getWindow();
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void actieMenu(MouseEvent event) {
        switch (listMenu.getSelectionModel().getSelectedIndex()) {
            case 0: {
                maakKlimatogramTab();
            }
            break;
            case 1: {
                maakDeterminerenTab();
            }
            break;
            case 2: {
                maakKlassenlijstenTab();
            }
            break;
            case 3: {
                maakToetsenTab();
            }
            break;
        }
        Platform.runLater(() -> {
            new FadeInUpTransition(paneData).play();
        });
    }

    @FXML
    private void logout(ActionEvent event) {
        Stage st = new Stage();
        stage = (Stage) btnLogout.getScene().getWindow();
        Login controller = new Login();
        Scene scene = new Scene(controller);
        st.initStyle(StageStyle.UNDECORATED);
        st.setResizable(true);
        st.setMaximized(false);
        st.setScene(scene);
        st.show();
        stage.close();
    }

    private void maakKlimatogramTab() {
        if (klimatogramTab == null) {
            KlimatogramDetailPanelController kdpc = new KlimatogramDetailPanelController(this.kController, statusBar, imgLoad);
            KlimatogramKiezenPanelController kkpc = new KlimatogramKiezenPanelController(this.kController, statusBar, imgLoad);
            this.kController.addObserver(kdpc);
            this.kController.addObserver(kkpc);
            klimatogramTab = new HBox();
            Pane pnlLinks = new Pane();
            HBox pnlRechts = new HBox();
            HBox.setHgrow(pnlRechts, Priority.ALWAYS);
            HBox.setHgrow(kdpc, Priority.ALWAYS);
            pnlLinks.getChildren().add(kkpc);
            klimatogramTab.getChildren().add(pnlLinks);
            pnlRechts.getChildren().add(kdpc);
            klimatogramTab.getChildren().add(pnlRechts);
            setAnchor(klimatogramTab);
        }
        paneData.getChildren().clear();
        titel.setText("Klimatogrammenbeheer");
        paneData.getChildren().add(klimatogramTab);
    }

    private void setAnchor(HBox content) {
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
        AnchorPane.setTopAnchor(content, 0.0);
    }

    private void maakDeterminerenTab() {
        if (determinerenTab == null) {
            DeterminatieTabellenOverzichtPaneController dtopc = new DeterminatieTabellenOverzichtPaneController(this.dController);
            BoomPanelController bpc = new BoomPanelController();
            PropertyContainerPanelController pcpc = new PropertyContainerPanelController(this.dController);
            this.dController.addObserver(bpc);
            this.dController.addObserver(pcpc);
            bpc.addListener(pcpc);
            determinerenTab = new HBox();
            Pane pnlLinks = new Pane();
            VBox pnlRechts = new VBox();
            HBox.setHgrow(pnlRechts, Priority.ALWAYS);
            VBox.setVgrow(bpc, Priority.ALWAYS);
            pnlLinks.getChildren().add(dtopc);
            determinerenTab.getChildren().add(pnlLinks);
            pnlRechts.getChildren().add(bpc);
            pnlRechts.getChildren().add(pcpc);
            determinerenTab.getChildren().add(pnlRechts);
            setAnchor(determinerenTab);
        }
        paneData.getChildren().clear();
        titel.setText("Determinatiebeheer");
        paneData.getChildren().add(determinerenTab);
    }

    private void maakKlassenlijstenTab() {
        if (klaslijstenTab == null) {
            KlasLijstenKiezenPanelController klkpc = new KlasLijstenKiezenPanelController(lController, statusBar);
            KlasLijstenDetailPanelController kldpc = new KlasLijstenDetailPanelController(lController, statusBar);
     
            Pane pnlLinks = new Pane();
            VBox pnlRechts = new VBox();
            

            HBox.setHgrow(pnlRechts, Priority.ALWAYS);
            VBox.setVgrow(kldpc, Priority.ALWAYS);
            
            pnlLinks.getChildren().add(klkpc);
        
            pnlRechts.getChildren().add(kldpc);
        
                   klaslijstenTab = new HBox(pnlLinks,pnlRechts);
            setAnchor(klaslijstenTab);
        }
        paneData.getChildren().clear();
        titel.setText("Klaslijstenbeheer");
        paneData.getChildren().add(klaslijstenTab);
    }

    private void maakToetsenTab() {
        if (toetsenTab == null) {
            ToetsenKiezenPanelController tkpc = new ToetsenKiezenPanelController(tController, statusBar);
            toetsenTab = new HBox();
            VBox.setVgrow(tkpc, Priority.ALWAYS);
            ToetsenDetailPanelController pnlRechts = new ToetsenDetailPanelController();
            pnlRechts.setPadding(new Insets(15, 15, 15, 15));
            VBox.setVgrow(pnlRechts, Priority.ALWAYS);
            HBox.setHgrow(pnlRechts, Priority.ALWAYS);
            ToetsVragenOverzichtController tvoc = new ToetsVragenOverzichtController(tController);
            tkpc.getGeselecteerdeToetsProperty().addListener(pnlRechts);
            pnlRechts.setDisable(true);
            HBox.setHgrow(tvoc, Priority.ALWAYS);
            toetsenTab.getChildren().add(tkpc);
            pnlRechts.getChildren().add(new VragenRepositoryController(tController));
            pnlRechts.getChildren().add(tvoc);
            toetsenTab.getChildren().add(pnlRechts);
            setAnchor(toetsenTab);
        }
        paneData.getChildren().clear();
        titel.setText("Toetsenbeheer");
        paneData.getChildren().add(toetsenTab);
    }
}
