package gui.klimatogram;

import controller.KlimatogramController;
import controller.Observer;
import dto.ContinentDto;
import dto.KlimatogramDto;
import dto.LandDto;
import java.io.IOException;
import java.util.Optional;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Annemie
 */
public class KlimatogramKiezenPanelController extends Pane implements Observer {

    @FXML
    private ComboBox<ContinentDto> cboWerelddeel;
    @FXML
    private ComboBox<LandDto> cboLand;
    @FXML
    private Button btnLandOk, btnLandCancel, btnWerelddeelOk, btnWerelddeelCancel, btnKlimatogramToevoegen, btnKlimatogramWijzigen, btnKlimatogramVerwijderen;
    @FXML
    private ListView<KlimatogramDto> lstLocaties;
    @FXML
    private TextField txfLand, txfWerelddeel;
    @FXML
    private CheckBox chkEerste, chkTweede, chkDerde;
    private CheckBox[] checkBoxen = new CheckBox[3];
    @FXML
    private Label lblWerelddeel;
    private ImageView imgLoad;
    private Label statusBar;
    private KlimatogramController controller;

    public KlimatogramKiezenPanelController(KlimatogramController controller, Label statusBar, ImageView imgLoad) {
        this.controller = controller;
        this.statusBar = statusBar;
        this.imgLoad = imgLoad;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/klimatogram/KlimatogramKiezenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

//        cboWerelddeel.setItems(controller.getContinenten());
//        cboLand.setItems(controller.getLanden());
//        lstLocaties.setItems(controller.getLocaties());
        cboWerelddeel.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            clearErrors();
            clearLandenCombobox();
            if (newValue != null) {
                controller.clearLijstenWerelddeel();
                controller.selecteerContinent(newValue);
                clearSelectieLijst();
                int aantal = cboLand.getItems().size();
                cboLand.setVisibleRowCount(aantal < 8 ? aantal : 8);
            }
        });
        cboLand.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            clearErrors();
            if (newValue != null) {
                controller.clearLijstenLand();
                controller.selecteerLand(newValue);
                clearSelectieLijst();
            }
        });
        lstLocaties.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            clearErrors();
            if (newValue != null) {
                controller.selecteerKlimatogram(newValue);
            }
        });

        btnKlimatogramToevoegen.setGraphic(new ImageView(new Image("/img/plus.png")));
        btnKlimatogramVerwijderen.setGraphic(new ImageView(new Image("/img/min.png")));
        btnKlimatogramWijzigen.setGraphic(new ImageView(new Image("/img/edit.png")));
        btnLandOk.setGraphic(new ImageView(new Image("/img/plus_small.png")));
        btnLandCancel.setGraphic(new ImageView(new Image("/img/min_small.png")));
        btnWerelddeelOk.setGraphic(new ImageView(new Image("/img/plus_small.png")));
        btnWerelddeelCancel.setGraphic(new ImageView(new Image("/img/min_small.png")));
        setTooltip(btnKlimatogramToevoegen, "Klimatogram toevoegen");
        setTooltip(btnKlimatogramVerwijderen, "Klimatogram verwijderen");
        setTooltip(btnKlimatogramWijzigen, "Klimatogram wijzigen");
        setTooltip(btnLandOk, "Land toevoegen");
        setTooltip(btnLandCancel, "Land verwijderen");
        setTooltip(btnWerelddeelOk, "Werelddeel toevoegen");
        setTooltip(btnWerelddeelCancel, "Werelddeel verwijderen");
        checkBoxen[0] = chkEerste;
        checkBoxen[1] = chkTweede;
        checkBoxen[2] = chkDerde;
        
        vulPanel();
    }

    private void vulPanel() {
        Service<Integer> service = new Service<Integer>() {

            @Override
            protected Task createTask() {
                cboWerelddeel.setItems(controller.getContinenten());
                cboLand.setItems(controller.getLanden());
                lstLocaties.setItems(controller.getLocaties());
                return new Task() {
                    @Override
                    protected Integer call() throws Exception {
                        cboWerelddeel.setItems(controller.getContinenten());
                        cboLand.setItems(controller.getLanden());
                        lstLocaties.setItems(controller.getLocaties());
                        return 1;
                    }
                };
            }
        };
        service.start();
        service.setOnRunning((WorkerStateEvent event) -> imgLoad.setVisible(true));
        service.setOnSucceeded((WorkerStateEvent event) -> {
            imgLoad.setVisible(false);
        });
    }

    @FXML
    public void voegKlimatogramToe(ActionEvent event) {
        try {
            clearErrors();
            controller.voegToe();
            this.setDisable(true);
            clearSelectieLijst();
        } catch (IllegalArgumentException e) {
            statusBar.setText(e.getMessage());
        }
    }

    @FXML
    public void verwijderKlimatogram(ActionEvent event) {
        try {
            clearErrors();
            controller.verwijderKlimatogram(lstLocaties.getSelectionModel().getSelectedItem().getLocatie());
            clearSelectieLijst();
        } catch (IllegalArgumentException ex) {
            statusBar.setText(ex.getMessage());
        } catch (NullPointerException ex) {
            statusBar.setText("U moet eerst een klimatogram selecteren");
        }
    }

    @Override
    public void update(String actie, Object object) {
        if (actie.equals("menu")) {
            this.setDisable(false);
        }
    }

    @FXML
    public void wijzigKlimatogram(ActionEvent event) {
        try {
            clearErrors();
            controller.wijzig();
            clearSelectieLijst();
            this.setDisable(true);
        } catch (IllegalArgumentException e) {
            statusBar.setText(e.getMessage());
        }
    }

    @FXML
    public void werelddeelOk(ActionEvent event) {
        clearErrors();
        if (btnWerelddeelOk.getId().equals("+")) {
            controller.clearLijstenWerelddeel();
            cboWerelddeel.getSelectionModel().clearSelection();
            cboWerelddeel.setVisible(false);
            txfWerelddeel.setVisible(true);
            btnWerelddeelOk.setGraphic(new ImageView(new Image("/img/vinkSmall.png")));
            btnWerelddeelOk.setId("v");
            btnWerelddeelCancel.setGraphic(new ImageView(new Image("/img/xSmall.png")));
            btnWerelddeelCancel.setId("x");
            setTooltip(btnWerelddeelOk, "Opslaan");
            setTooltip(btnWerelddeelCancel, "Annuleren");
        } else {
            try {
                if (txfWerelddeel.isVisible()) {
                    txfWerelddeel.setVisible(false);
                    lblWerelddeel.setText("Welke graad?");
                    for (CheckBox chkBox : checkBoxen) {
                        chkBox.setVisible(true);
                    }
                } else {
                    ContinentDto dto = controller.maakNieuwContinentDto();
                    dto.setNaam(txfWerelddeel.getText());
                    for (int i = 0; i < checkBoxen.length; i++) {
                        if (checkBoxen[i].isSelected()) {
                            if (i == 0) {
                                dto.voegGraadToe(dto.getGraden().keySet().toArray()[i].toString());
                            }
                            if (i == 1) {
                                dto.voegGraadToe(dto.getGraden().keySet().toArray()[i].toString());
                                dto.voegGraadToe(dto.getGraden().keySet().toArray()[i + 1].toString());
                            }
                            if (i == 2) {
                                dto.voegGraadToe(dto.getGraden().keySet().toArray()[i + 1].toString());
                            }
                        }
                    }
                    controller.voegContinentToe(dto);
                    txfWerelddeel.clear();
                    cboWerelddeel.setVisible(true);
                    txfWerelddeel.setVisible(false);
                    for (CheckBox chkBox : checkBoxen) {
                        chkBox.setVisible(false);
                        chkBox.setSelected(false);
                    }
                    lblWerelddeel.setText("Werelddeel");
                    btnWerelddeelOk.setId("+");
                    btnWerelddeelOk.setGraphic(new ImageView(new Image("/img/plus_small.png")));
                    btnWerelddeelCancel.setId("-");
                    btnWerelddeelCancel.setGraphic(new ImageView(new Image("/img/min_small.png")));
                }
            } catch (IllegalArgumentException e) {
                statusBar.setText(e.getMessage());
            } catch (Exception ex) {
                statusBar.setText("Er is een fout opgetreden");
            }
        }
    }

    @FXML
    public void werelddeelCancel(ActionEvent event) {
        clearErrors();
        if (btnWerelddeelCancel.getId().equals("-")) {
            try {
                if (controller.werelddeelGeselecteerd()) {
                    Optional<ButtonType> result = maakAlert("Werelddeel verwijderen", "Werelddeel verwijderen", "Bent u zeker dat u dit werelddeel wilt verwijderen? Alle landen en klimatogrammen zullen mee verwijderd worden.");
                    if (result.get().getButtonData() == ButtonData.YES) {
                        controller.clearLijstenWerelddeel();
                        clearSelectieLijst();
                        controller.verwijderContinent(cboWerelddeel.getSelectionModel().getSelectedItem());
                    }
                } else {
                    statusBar.setText("U moet eerst een werelddeel selecteren");
                }

            } catch (IllegalArgumentException e) {
                statusBar.setText(e.getMessage());
            } catch (NullPointerException e) {
                statusBar.setText("U moet eerst een werelddeel selecteren");
            }
        } else {
            txfWerelddeel.clear();
            cboWerelddeel.setVisible(true);
            txfWerelddeel.setVisible(false);
            for (CheckBox chkBox : checkBoxen) {
                chkBox.setVisible(false);
                chkBox.setSelected(false);
            }
            lblWerelddeel.setText("Werelddeel");
            btnWerelddeelOk.setId("+");
            btnWerelddeelOk.setGraphic(new ImageView(new Image("/img/plus_small.png")));
            btnWerelddeelCancel.setId("-");
            btnWerelddeelCancel.setGraphic(new ImageView(new Image("/img/min_small.png")));
            setTooltip(btnWerelddeelOk, "Werelddeel toevoegen");
            setTooltip(btnWerelddeelCancel, "Werelddeel verwijderen");
        }
    }

    @FXML
    public void landOk(ActionEvent event) {
        clearErrors();
        if (btnLandOk.getId().equals("+")) {
            if (controller.werelddeelGeselecteerd()) {
                controller.clearLijstenLand();
                cboLand.setVisible(false);
                txfLand.setVisible(true);
                btnLandOk.setGraphic(new ImageView(new Image("/img/vinkSmall.png")));
                btnLandOk.setId("cancel");
                btnLandCancel.setGraphic(new ImageView(new Image("/img/xSmall.png")));
                btnLandCancel.setId("x");
                setTooltip(btnLandOk, "Opslaan");
                setTooltip(btnLandCancel, "Wijzigen");
            } else {
                statusBar.setText("U moet eerst een werelddeel selecteren");
            }

        } else {
            try {
                controller.voegLandToe(new LandDto(txfLand.getText()));
                txfLand.clear();
                cboLand.setVisible(true);
                txfLand.setVisible(false);
                btnLandOk.setId("+");
                btnLandOk.setGraphic(new ImageView(new Image("/img/plus_small.png")));
                btnLandCancel.setId("-");
                btnLandCancel.setGraphic(new ImageView(new Image("/img/min_small.png")));
            } catch (IllegalArgumentException e) {
                statusBar.setText(e.getMessage());
            } catch (Exception ex) {
                statusBar.setText("Er is een fout opgetreden");
            }
        }
    }

    @FXML
    public void landCancel(ActionEvent event) {
        clearErrors();
        if (btnLandCancel.getId().equals("-")) {
            try {
                if (controller.landGeselecteerd()) {
                    Optional<ButtonType> result = maakAlert("Land verwijderen", "Land verwijderen", "Bent u zeker dat u dit land wilt verwijderen? Alle klimatogrammen zullen mee verwijderd worden.");
                    if (result.get().getButtonData() == ButtonData.YES) {
                        clearSelectieLijst();
                        controller.clearLijstenLand();
                        controller.verwijderLand(cboLand.getSelectionModel().getSelectedItem());
                    }
                } else {
                    statusBar.setText("U moet eerst een land selecteren");
                }
            } catch (IllegalArgumentException e) {
                statusBar.setText(e.getMessage());
            } catch (NullPointerException e) {
                statusBar.setText("U moet eerst een land selecteren");
            }
        } else {
            txfLand.clear();
            cboLand.setVisible(true);
            txfLand.setVisible(false);
            btnLandOk.setId("+");
            btnLandOk.setGraphic(new ImageView(new Image("/img/plus_small.png")));
            btnLandCancel.setId("-");
            btnLandCancel.setGraphic(new ImageView(new Image("/img/min_small.png")));
            setTooltip(btnLandOk, "Land toevoegen");
            setTooltip(btnLandCancel, "Land verwijderen");
        }
    }

    private void clearErrors() {
        statusBar.setText("");
    }

    private void clearLandenCombobox() {
        cboLand.getSelectionModel().clearSelection();
    }

    private void clearSelectieLijst() {
        lstLocaties.getSelectionModel().clearSelection();
    }

    private Optional<ButtonType> maakAlert(String titel, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titel);
        alert.setHeaderText(header);
        alert.setContentText(content);
        ButtonType buttonOk = new ButtonType("Verwijderen", ButtonData.YES);
        ButtonType buttonCancel = new ButtonType("Annuleren", ButtonData.NO);
        alert.getButtonTypes().setAll(buttonOk, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    private void setTooltip(Button button, String tooltip) {
        button.setTooltip(new Tooltip(tooltip));
    }
}
