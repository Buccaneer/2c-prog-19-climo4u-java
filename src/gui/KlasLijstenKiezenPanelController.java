/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.LeerlingController;
import controller.Observer;
import dto.GraadDto;
import dto.KlasDto;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.controlsfx.control.StatusBar;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class KlasLijstenKiezenPanelController extends VBox implements Observer {

    @FXML
    private ComboBox<GraadDto> cboGraad;
    @FXML
    private ListView<KlasDto> lstKlas;
    @FXML
    private Button btnToevoegen, btnWijzigen, btnVerwijderen;
    private LeerlingController controller;
    private StatusBar statusBar;

    public KlasLijstenKiezenPanelController(LeerlingController controller, StatusBar statusBar) {
        this.controller = controller;
        this.statusBar = statusBar;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlasLijstenKiezenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(KlasLijstenKiezenPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cboGraad.setItems(controller.getGraden());
        lstKlas.setItems(controller.getKlassen());
        cboGraad.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                clearErrors();
                lstKlas.getItems().clear();
                controller.selecteerGraad(newValue);
            }
        });
        lstKlas.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                clearErrors();
                controller.selecteerKlas(newValue);
            }
        });

        btnToevoegen.setGraphic(new ImageView(new Image("/content/images/plus.png")));
        btnVerwijderen.setGraphic(new ImageView(new Image("/content/images/min.png")));
        btnWijzigen.setGraphic(new ImageView(new Image("/content/images/edit.png")));
    }

    @Override
    public void update(String actie, Object object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    public void voegKlasToe(ActionEvent e) {
        clearErrors();
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Klas toevoegen");
        dialog.setHeaderText("Klas toevoegen");

        ButtonType klasToevoegenButtonType = new ButtonType("Klas toevoegen", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(klasToevoegenButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField klas = new TextField();
        TextField jaar = new TextField();

        grid.add(new Label("Klasnaam:"), 0, 0);
        grid.add(klas, 1, 0);
        grid.add(new Label("Leerjaar:"), 0, 1);
        grid.add(jaar, 1, 1);
        Node klasButton = dialog.getDialogPane().lookupButton(klasToevoegenButtonType);
        klasButton.setDisable(true);

        klas.textProperty().addListener((observable, oldValue, newValue) -> {
            if (klas.textProperty().isEmpty().get() || jaar.textProperty().isEmpty().get()) {
                klasButton.setDisable(true);
            } else {
                klasButton.setDisable(false);
            }
        });

        jaar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (klas.textProperty().isEmpty().get() || jaar.textProperty().isEmpty().get()) {
                klasButton.setDisable(true);
            } else {
                klasButton.setDisable(false);
            }
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> klas.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == klasToevoegenButtonType) {
                return new Pair<>(klas.getText(), jaar.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(klasLeerjaar -> {
            KlasDto dto = new KlasDto();
            dto.setNaam(klasLeerjaar.getKey());
            try {
                dto.setLeerjaar(Integer.parseInt(klasLeerjaar.getValue()));
                controller.maakNieuweKlas(dto);
            } catch (NumberFormatException ex) {
                statusBar.setText("Gelieve enkel cijfers in te vullen in het leerjaar");
            } catch (IllegalArgumentException ex) {
                statusBar.setText(ex.getMessage());
            }
        });
    }

    @FXML
    public void wijzigKlas(ActionEvent e) {
        clearErrors();
        try {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Klas wijzigen");
            dialog.setHeaderText("Klas wijzigen");

            ButtonType klasToevoegenButtonType = new ButtonType("Klas wijzigen", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(klasToevoegenButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField klas = new TextField();
            TextField jaar = new TextField();
            klas.setText(controller.getGeselecteerdeKlasDto().getNaam());
            jaar.setText(String.valueOf(controller.getGeselecteerdeKlasDto().getLeerjaar()));
            grid.add(new Label("Klasnaam:"), 0, 0);
            grid.add(klas, 1, 0);
            grid.add(new Label("Leerjaar:"), 0, 1);
            grid.add(jaar, 1, 1);
            Node klasButton = dialog.getDialogPane().lookupButton(klasToevoegenButtonType);
            klasButton.setDisable(true);

            klas.textProperty().addListener((observable, oldValue, newValue) -> {
                if (klas.textProperty().isEmpty().get() || jaar.textProperty().isEmpty().get()) {
                    klasButton.setDisable(true);
                } else {
                    klasButton.setDisable(false);
                }
            });

            jaar.textProperty().addListener((observable, oldValue, newValue) -> {
                if (klas.textProperty().isEmpty().get() || jaar.textProperty().isEmpty().get()) {
                    klasButton.setDisable(true);
                } else {
                    klasButton.setDisable(false);
                }
            });

            dialog.getDialogPane().setContent(grid);

            Platform.runLater(() -> klas.requestFocus());

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == klasToevoegenButtonType) {
                    return new Pair<>(klas.getText(), jaar.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(klasLeerjaar -> {
                KlasDto dto = new KlasDto();
                dto.setNaam(klasLeerjaar.getKey());
                try {
                    dto.setLeerjaar(Integer.parseInt(klasLeerjaar.getValue()));
                    controller.wijzigKlas(dto);
                } catch (NumberFormatException ex) {
                    statusBar.setText("Gelieve enkel cijfers in te vullen in het leerjaar");
                } catch (IllegalArgumentException ex) {
                    statusBar.setText(ex.getMessage());
                }
            });
        } catch (IllegalArgumentException ex) {
            statusBar.setText(ex.getMessage());
        }

    }

    @FXML
    public void verwijderKlas(ActionEvent e) {
        try {
            clearErrors();
            controller.verwijderKlas();
            
        } catch (IllegalArgumentException ex) {
            statusBar.setText(ex.getMessage());
        }
    }

    private void clearErrors() {
        statusBar.setText("");
    }
}
