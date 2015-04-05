/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import util.IntegerConverter;
import util.DoubleConverter;
import controller.KlimatogramController;
import controller.Observer;
import controller.VerkeerdeInputException;
import dto.KlimatogramDto;
import dto.MaandDto;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.StatusBar;

/**
 * FXML Controller class
 *
 * @author Annemie
 */
public class KlimatogramDetailPanelController extends Pane implements Observer {

    @FXML
    private Label lblValidatieLocatie, lblValidatieStation, lblValidatieLatitude, lblValidatieLongitude, lblValidatiePeriode;
    @FXML
    private ComboBox cboLat, cboLong;
    @FXML
    private Button btnAnnuleren, btnOpslaan, btnWijzig;
    @FXML
    private TextField txfStation, txfLatitudeUren, txfLatitudeMinuten, txfLatitudeSeconden, txfLongitudeUren, txfLongitudeMinuten, txfLongitudeSeconden, txfBeginPeriode, txfEindPeriode, txfGemiddeldeTemperatuur, txfTotaleJaarneerslag, txfLocatie;
    @FXML
    private TableView<MaandDto> tblMaanden;
    @FXML
    private TableColumn<MaandDto, String> maandColumn;
    @FXML
    private TableColumn<MaandDto, Integer> neerslagColumn;
    @FXML
    private TableColumn<MaandDto, Double> temperatuurColumn;
    @FXML
    private Pane pnlKlimatogram;
    private KlimatogramController controller;
    private KlimatogramDto klimatogram;
    private StatusBar statusBar;
    private Image imgFout, imgLeeg;

    public KlimatogramDetailPanelController(KlimatogramController controller, StatusBar statusBar) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlimatogramDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.controller = controller;
        this.statusBar = statusBar;

        cboLat.setItems(FXCollections.observableArrayList(Arrays.asList(new String[]{"N", "Z"})));
        cboLong.setItems(FXCollections.observableArrayList(Arrays.asList(new String[]{"O", "W"})));
        temperatuurColumn.setCellFactory(TextFieldTableCell.<MaandDto, Double>forTableColumn(new DoubleConverter()));

        temperatuurColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<MaandDto, Double>>() {
                    @Override
                    public void handle(CellEditEvent<MaandDto, Double> t) {
                        try {
                            ((MaandDto) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setTemperatuur(t.getNewValue());
                        } catch (NumberFormatException ex) {
                        }
                        StackPane chart = new KlimatogramGrafiek().createChart(klimatogram);
                        pnlKlimatogram.getChildren().clear();
                        chart.setPrefSize(pnlKlimatogram.getPrefWidth(), pnlKlimatogram.getPrefHeight());
                        pnlKlimatogram.getChildren().add(chart);
                        txfGemiddeldeTemperatuur.setText(String.valueOf(berekenJaartemperatuur(klimatogram.maanden)));
                        txfTotaleJaarneerslag.setText(String.valueOf(berekenTotaleNeerslag(klimatogram.maanden)));
                    }
                }
        );

        neerslagColumn.setCellFactory(TextFieldTableCell.<MaandDto, Integer>forTableColumn(new IntegerConverter()));
        neerslagColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<MaandDto, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<MaandDto, Integer> t) {
                        try {
                            ((MaandDto) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setNeerslag(t.getNewValue());
                        } catch (NumberFormatException ex) {
                        }
                        StackPane chart = new KlimatogramGrafiek().createChart(klimatogram);
                        pnlKlimatogram.getChildren().clear();
                        chart.setPrefSize(pnlKlimatogram.getPrefWidth(), pnlKlimatogram.getPrefHeight());
                        pnlKlimatogram.getChildren().add(chart);
                        txfGemiddeldeTemperatuur.setText(String.valueOf(berekenJaartemperatuur(klimatogram.maanden)));
                        txfTotaleJaarneerslag.setText(String.valueOf(berekenTotaleNeerslag(klimatogram.maanden)));
                    }
                }
        );
        imgFout = new Image("/content/images/x.png");
        imgLeeg = null;
    }

    @Override
    public void update(String actie, Object object) {
        klimatogram = (KlimatogramDto) object;
        if (actie.equals("voegToe")) {
            voegKlimatogramToe();
        }
        if (actie.equals("wijzig")) {
            wijzigKlimatogram();
        } else if (actie.equals("vulIn")) {
            vulIn();
        }
    }

    private double berekenJaartemperatuur(List<MaandDto> maanden) {
        return maanden.stream().mapToDouble(m -> m.getTemperatuur()).average().getAsDouble();
    }

    private int berekenTotaleNeerslag(List<MaandDto> maanden) {
        return maanden.stream().mapToInt(m -> m.getNeerslag()).sum();
    }

    private void vulIn() {
        txfLocatie.setText(klimatogram.getLocatie());
        txfStation.setText(klimatogram.getStation());
        txfBeginPeriode.setText(String.format("%d", klimatogram.getBeginJaar()));
        txfEindPeriode.setText(String.format("%d", klimatogram.getEindJaar()));
        double temperatuur = berekenJaartemperatuur(klimatogram.maanden);
        txfGemiddeldeTemperatuur.setText(String.format("%.2f", temperatuur));
        int neerslag = berekenTotaleNeerslag(klimatogram.maanden);
        txfTotaleJaarneerslag.setText(String.format("%d", neerslag));
        double latitude = klimatogram.getLatitude();
        if (latitude >= 0) {
            cboLat.getSelectionModel().select("N");
        } else {
            cboLat.getSelectionModel().select("Z");
        }
        latitude = Math.abs(latitude);
        double degrees = Math.floor(latitude);
        double minutes = Math.floor((latitude - degrees) * 60);
        double seconds = Math.floor((((latitude - degrees) * 60) - minutes) * 60);
        txfLatitudeUren.setText(String.format("%.0f", degrees));
        txfLatitudeMinuten.setText(String.format("%.0f", minutes));
        txfLatitudeSeconden.setText(String.format("%.0f", seconds));
        double longitude = klimatogram.getLongitude();
        if (longitude >= 0) {
            cboLong.getSelectionModel().select("O");
        } else {
            cboLong.getSelectionModel().select("W");
        }
        longitude = Math.abs(longitude);
        degrees = Math.floor(longitude);
        minutes = Math.floor((longitude - degrees) * 60);
        seconds = Math.floor((((longitude - degrees) * 60) - minutes) * 60);
        txfLongitudeUren.setText(String.format("%.0f", degrees));
        txfLongitudeMinuten.setText(String.format("%.0f", minutes));
        txfLongitudeSeconden.setText(String.format("%.0f", seconds));
        tblMaanden.setItems(klimatogram.maanden);
        maandColumn.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        temperatuurColumn.setCellValueFactory(cellData -> cellData.getValue().temperatuurProperty().asObject());
        neerslagColumn.setCellValueFactory(cellData -> cellData.getValue().neerslagProperty().asObject());

        StackPane chart = new KlimatogramGrafiek().createChart(klimatogram);
        pnlKlimatogram.getChildren().clear();
        chart.setPrefSize(pnlKlimatogram.getPrefWidth(), pnlKlimatogram.getPrefHeight());
        pnlKlimatogram.getChildren().add(chart);
    }

    private void voegKlimatogramToe() {
        clear();
        clearErrors();
        toonElementen();
        tblMaanden.setItems(klimatogram.maanden);
        maandColumn.setCellValueFactory(cellData -> cellData.getValue().naamProperty());

        btnAnnuleren.setVisible(true);
        btnOpslaan.setVisible(true);
        this.setDisable(false);

    }

    private void clear() {
        txfStation.clear();
        txfBeginPeriode.clear();
        txfEindPeriode.clear();
        txfGemiddeldeTemperatuur.clear();
        txfLatitudeMinuten.clear();
        txfLatitudeSeconden.clear();
        txfLatitudeUren.clear();
        txfLongitudeMinuten.clear();
        txfLongitudeSeconden.clear();
        txfLongitudeUren.clear();
        txfTotaleJaarneerslag.clear();
        txfGemiddeldeTemperatuur.clear();
        txfLocatie.clear();
        pnlKlimatogram.getChildren().clear();
    }

    @FXML
    public void opslaan(ActionEvent event) {
        try {
            clearErrors();
            zetWaardenInDto();
            controller.voegKlimatogramToe(klimatogram);
            this.setDisable(true);
            controller.notifyObservers("menu", null);
            verbergElementen();
        } catch (VerkeerdeInputException e) {
            behandelExcepties(e);
        } catch (NumberFormatException | NullPointerException e) {

        }
    }

    private void zetWaardenInDto() throws NumberFormatException {
        boolean exceptie = false;

        klimatogram.setStation(txfStation.getText());
        if (!txfLocatie.getText().isEmpty()) {
            klimatogram.setLocatie(txfLocatie.getText());
        } else {
            setTooltip(lblValidatieLocatie, "Gelieve dit veld geldig in te vullen");
            exceptie = true;
        }

        try {
            double latitudeUren = Double.parseDouble(txfLatitudeUren.getText());
            double latitudeMin = Double.parseDouble(txfLatitudeMinuten.getText());
            double latitudeSec = Double.parseDouble(txfLatitudeSeconden.getText());
            String oostWest = cboLong.getSelectionModel().getSelectedItem().toString();

            double latitude = latitudeUren + latitudeMin / 60 + latitudeSec / 3600;
            if (oostWest.equals("O")) {
                latitude *= -1;
            }
            klimatogram.setLatitude(latitude);
        } catch (NumberFormatException | NullPointerException e) {
            setTooltip(lblValidatieLatitude, "Gelieve enkel cijfers in te vullen en een windrichting te kiezen");
            exceptie = true;
        }

        try {
            double longitudeUren = Double.parseDouble(txfLongitudeUren.getText());
            double longitudeMin = Double.parseDouble(txfLongitudeMinuten.getText());
            double longitudeSec = Double.parseDouble(txfLongitudeSeconden.getText());
            String noordZuid = cboLong.getSelectionModel().getSelectedItem().toString();
            double longitude = longitudeUren + longitudeMin / 60 + longitudeSec / 3600;
            if (noordZuid.equals("Z")) {
                longitude *= -1;
            }
            klimatogram.setLongitude(longitude);
        } catch (NumberFormatException | NullPointerException e) {
            setTooltip(lblValidatieLongitude, "Gelieve enkel cijfers in te vullen en een windrichting te kiezen");
            exceptie = true;
        }

        try {
            klimatogram.setBeginJaar(Integer.parseInt(txfBeginPeriode.getText()));
            klimatogram.setEindJaar(Integer.parseInt(txfEindPeriode.getText()));
        } catch (NumberFormatException e) {
            setTooltip(lblValidatiePeriode, "Gelieve enkel cijfers in te vullen");
            exceptie = true;
        }
        if (exceptie) {
            throw new NumberFormatException();
        }
    }

    @FXML
    public void wijzig(ActionEvent event) {
        try {
            zetWaardenInDto();
            controller.wijzigKlimatogram(klimatogram);
            this.setDisable(true);
            controller.notifyObservers("menu", null);
            verbergElementen();
        } catch (VerkeerdeInputException e) {
            behandelExcepties(e);
        } catch (NumberFormatException | NullPointerException e) {
        }
    }

    @FXML
    public void annuleren(ActionEvent event) {
        this.setDisable(true);
        clear();
        controller.notifyObservers("menu", null);
        verbergElementen();
    }

    private void wijzigKlimatogram() {
        btnAnnuleren.setVisible(true);
        btnWijzig.setVisible(true);
        clearErrors();
        toonElementen();
        
        tblMaanden.setItems(klimatogram.maanden);
        klimatogram.setStation(txfStation.getText());
        klimatogram.setBeginJaar(Integer.parseInt(txfBeginPeriode.getText()));
        klimatogram.setEindJaar(Integer.parseInt(txfEindPeriode.getText()));
        klimatogram.setLocatie(txfLocatie.getText());

        double longitudeUren = Double.parseDouble(txfLongitudeUren.getText());
        double longitudeMin = Double.parseDouble(txfLongitudeMinuten.getText());
        double longitudeSec = Double.parseDouble(txfLongitudeSeconden.getText());
        String noordZuid = cboLong.getSelectionModel().getSelectedItem().toString();

        double longitude = longitudeUren + longitudeMin / 60 + longitudeSec / 3600;

        if (noordZuid.equals("Z")) {
            longitude *= -1;
        }

        klimatogram.setLongitude(longitude);

        double latitudeUren = Double.parseDouble(txfLatitudeUren.getText());
        double latitudeMin = Double.parseDouble(txfLatitudeMinuten.getText());
        double latitudeSec = Double.parseDouble(txfLatitudeSeconden.getText());
        String oostWest = cboLong.getSelectionModel().getSelectedItem().toString();

        double latitude = latitudeUren + latitudeMin / 60 + latitudeSec / 3600;
        if (oostWest.equals("O")) {
            latitude *= -1;
        }
        klimatogram.setLatitude(latitude);
        this.setDisable(false);
    }

    private void verbergElementen() {
        btnAnnuleren.setVisible(false);
        btnOpslaan.setVisible(false);
        btnWijzig.setVisible(false);
        lblValidatieLatitude.setVisible(false);
        lblValidatieLocatie.setVisible(false);
        lblValidatieLongitude.setVisible(false);
        lblValidatiePeriode.setVisible(false);
        lblValidatieStation.setVisible(false);
    }

    private void toonElementen() {
        lblValidatieLatitude.setVisible(true);
        lblValidatieLocatie.setVisible(true);
        lblValidatieLongitude.setVisible(true);
        lblValidatiePeriode.setVisible(true);
        lblValidatieStation.setVisible(true);
    }

    private void setTooltip(Button button, String tooltip) {
        button.setTooltip(new Tooltip(tooltip));
    }

    private void setTooltip(Label label, String tooltip) {
        label.setTooltip(new Tooltip(tooltip));
        label.setGraphic(new ImageView(imgFout));
    }

    private void clearErrors() {
        statusBar.setText("");
        lblValidatieLatitude.setTooltip(new Tooltip(""));
        lblValidatieLocatie.setTooltip(new Tooltip(""));
        lblValidatieLongitude.setTooltip(new Tooltip(""));
        lblValidatiePeriode.setTooltip(new Tooltip(""));
        lblValidatieStation.setTooltip(new Tooltip(""));
        lblValidatieLatitude.setGraphic(new ImageView(imgLeeg));
        lblValidatieLocatie.setGraphic(new ImageView(imgLeeg));
        lblValidatieLongitude.setGraphic(new ImageView(imgLeeg));
        lblValidatiePeriode.setGraphic(new ImageView(imgLeeg));
        lblValidatieStation.setGraphic(new ImageView(imgLeeg));
    }

    private void behandelExcepties(VerkeerdeInputException e) {
        for (Entry<String, Exception> exception : e.getEntries()) {
            switch (exception.getKey()) {
                case "beginJaar":
                case "eindJaar":
                    setTooltip(lblValidatiePeriode, exception.getValue().getMessage());
                    break;
                case "latitude":
                    setTooltip(lblValidatieLatitude, exception.getValue().getMessage());
                    break;
                case "longitude":
                    setTooltip(lblValidatieLongitude, exception.getValue().getMessage());
                    break;
                case "locatie":
                    setTooltip(lblValidatieLocatie, exception.getValue().getMessage());
                    break;
                case "station":
                    setTooltip(lblValidatieStation, exception.getValue().getMessage());
                    break;
            }
        }
    }
}
