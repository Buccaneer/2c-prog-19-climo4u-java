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
import dto.KlimatogramDto;
import dto.MaandDto;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Annemie
 */
public class KlimatogramDetailPanelController extends Pane implements Observer {

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

    private ObservableList<MaandDto> maanden = FXCollections.observableArrayList();

    private KlimatogramDto klimatogram;

    public KlimatogramDetailPanelController(KlimatogramController controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlimatogramDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.controller = controller;
        cboLat.setItems(FXCollections.observableArrayList(Arrays.asList(new String[]{"N", "Z"})));
        cboLong.setItems(FXCollections.observableArrayList(Arrays.asList(new String[]{"O", "W"})));
        maanden.add(new MaandDto("Januari", 0,0));
        maanden.add(new MaandDto("Februari",0,0));
        maanden.add(new MaandDto("Maart",0,0));
        maanden.add(new MaandDto("April",0,0));
        maanden.add(new MaandDto("Mei",0,0));
        maanden.add(new MaandDto("Juni",0,0));
        maanden.add(new MaandDto("Juli",0,0));
        maanden.add(new MaandDto("Augustus",0,0));
        maanden.add(new MaandDto("September",0,0));
        maanden.add(new MaandDto("Oktober",0,0));
        maanden.add(new MaandDto("November",0,0));
        maanden.add(new MaandDto("December",0,0));
        temperatuurColumn.setCellFactory(TextFieldTableCell.<MaandDto, Double>forTableColumn(new DoubleConverter()));

        temperatuurColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<MaandDto, Double>>() {
                    @Override
                    public void handle(CellEditEvent<MaandDto, Double> t) {
                        try {
                            ((MaandDto) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setTemperatuur(t.getNewValue());
                        } catch (NumberFormatException ex) {
                            System.out.println("hallo");
                        }
                        klimatogram = new KlimatogramDto();
                        klimatogram.maanden = FXCollections.observableArrayList(maanden);
                        StackPane chart = new KlimatogramGrafiek().createChart(klimatogram);
                        pnlKlimatogram.getChildren().clear();
                        chart.setPrefSize(pnlKlimatogram.getPrefWidth(), pnlKlimatogram.getPrefHeight());
                        pnlKlimatogram.getChildren().add(chart);
                        txfGemiddeldeTemperatuur.setText(String.valueOf(berekenJaartemperatuur(maanden)));
                        txfTotaleJaarneerslag.setText(String.valueOf(berekenTotaleNeerslag(maanden)));
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

                        klimatogram = new KlimatogramDto();
                        klimatogram.maanden = FXCollections.observableArrayList(maanden);
                        StackPane chart = new KlimatogramGrafiek().createChart(klimatogram);
                        pnlKlimatogram.getChildren().clear();
                        chart.setPrefSize(pnlKlimatogram.getPrefWidth(), pnlKlimatogram.getPrefHeight());
                        pnlKlimatogram.getChildren().add(chart);
                        txfGemiddeldeTemperatuur.setText(String.valueOf(berekenJaartemperatuur(maanden)));
                        txfTotaleJaarneerslag.setText(String.valueOf(berekenTotaleNeerslag(maanden)));
                    }
                }
        );
    }

    @Override
    public void update(Object object) {
        if (object instanceof String) {
            if (object.toString().equals("voegToe")) {
                voegKlimatogramToe();
            }
            if (object.toString().equals("wijzig")) {
                wijzigKlimatogram();
            }

        } else {
            vulIn(object);
        }
    }

    private double berekenJaartemperatuur(List<MaandDto> maanden) {
        return maanden.stream().mapToDouble(m -> m.getTemperatuur()).average().getAsDouble();
    }

    private int berekenTotaleNeerslag(List<MaandDto> maanden) {
        return maanden.stream().mapToInt(m -> m.getNeerslag()).sum();
    }

    public void vulIn(Object object) {
        klimatogram = (KlimatogramDto) object;
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
        maanden=klimatogram.maanden;
        tblMaanden.setItems(maanden);
        maandColumn.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        temperatuurColumn.setCellValueFactory(cellData -> cellData.getValue().temperatuurProperty().asObject());
        neerslagColumn.setCellValueFactory(cellData -> cellData.getValue().neerslagProperty().asObject());

        StackPane chart = new KlimatogramGrafiek().createChart(klimatogram);
        pnlKlimatogram.getChildren().clear();
        chart.setPrefSize(pnlKlimatogram.getPrefWidth(), pnlKlimatogram.getPrefHeight());
        pnlKlimatogram.getChildren().add(chart);
    }

    public void voegKlimatogramToe() {
        clear();
        maanden.stream().forEach(m -> {
            m.setNeerslag(0);
            m.setTemperatuur(0);
        });
        tblMaanden.setItems(FXCollections.observableArrayList(maanden));
        maandColumn.setCellValueFactory(cellData -> cellData.getValue().naamProperty());

        
        btnAnnuleren.setVisible(true);
        btnOpslaan.setVisible(true);
        this.setDisable(false);

    }

    public void clear() {
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
        zetWaardenInDto();
        controller.voegKlimatogramToe(klimatogram);
        this.setDisable(true);
        controller.notifyObservers("menu");

    }
    
    public void zetWaardenInDto(){
        klimatogram = new KlimatogramDto();
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
        klimatogram.maanden = maanden;
    }

    @FXML
    public void wijzig(ActionEvent event) {
        zetWaardenInDto();
        controller.wijzigKlimatogram(klimatogram);
        this.setDisable(true);
        controller.notifyObservers("menu");

    }

    @FXML
    public void annuleren(ActionEvent event) {
        this.setDisable(true);
        clear();
        controller.notifyObservers("menu");
    }

    @FXML
    public void wijzigKlimatogram() {
        btnAnnuleren.setVisible(true);
        btnWijzig.setVisible(true);
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
        klimatogram.maanden = maanden;
        this.setDisable(false);
    }
}
