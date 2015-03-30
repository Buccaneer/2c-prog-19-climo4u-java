/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.KlimatogramController;
import controller.Observer;
import dto.KlimatogramDto;
import dto.MaandDto;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Annemie
 */
public class KlimatogramDetailPanelController extends Pane implements Observer{

    @FXML
    private Button btnAnnuleren,btnOpslaan;
    
    @FXML
    private TextField txfStation, txfLatitudeUren, txfLatitudeMinuten,txfLatitudeSeconden,txfLongitudeUren, txfLongitudeMinuten,txfLongitudeSeconden,txfBeginPeriode,txfEindPeriode,txfGemiddeldeTemperatuur,txfTotaleJaarneerslag;
    
    @FXML
    private TableView tblMaanden;
    
    @FXML
    private TableColumn maandColumn, neerslagColumn, temperatuurColumn;
    
    @FXML
    private Pane pnlKlimatogram;
    
    private KlimatogramController controller;
    
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
    }
    
    @Override
    public void update(Object object) {
       KlimatogramDto klimatogram = (KlimatogramDto) object;
        
        txfStation.setText(klimatogram.getStation());
        txfBeginPeriode.setText(String.format("%d",klimatogram.getBeginJaar()));
        txfEindPeriode.setText(String.format("%d",klimatogram.getEindJaar()));
        double temperatuur = berekenJaartemperatuur(klimatogram.maanden);
        txfGemiddeldeTemperatuur.setText(String.format("%.2f", temperatuur));
        int neerslag = berekenTotaleNeerslag(klimatogram.maanden);
        txfTotaleJaarneerslag.setText(String.format("%d",neerslag));
        double latitude = klimatogram.getLatitude();
        double degrees = Math.floor(latitude);
        double minutes = Math.floor((latitude - degrees) * 60);
        double seconds = Math.floor((((latitude - degrees) * 60) - minutes) * 60);
        txfLatitudeUren.setText(String.format("%.0f", degrees));
        txfLatitudeMinuten.setText(String.format("%.0f",minutes));
        txfLatitudeSeconden.setText(String.format("%.0f", seconds));
        double longitude = klimatogram.getLongitude();
         degrees = Math.floor(longitude);
         minutes = Math.floor((longitude - degrees) * 60);
         seconds = Math.floor((((longitude - degrees) * 60) - minutes) * 60);
        txfLongitudeUren.setText(String.format("%.0f", degrees));
        txfLongitudeMinuten.setText(String.format("%.0f",minutes));
        txfLongitudeSeconden.setText(String.format("%.0f", seconds));
        
        StackPane chart = new KlimatogramGrafiek().createChart(klimatogram);
        pnlKlimatogram.getChildren().clear();
        chart.setPrefSize(pnlKlimatogram.getPrefWidth(), pnlKlimatogram.getPrefHeight());
        pnlKlimatogram.getChildren().add(chart);
    }
    
    private double berekenJaartemperatuur(List<MaandDto> maanden){
        return maanden.stream().mapToDouble(m->m.getTemperatuur()).average().getAsDouble();
    }

    private int berekenTotaleNeerslag(List<MaandDto> maanden) {
        return maanden.stream().mapToInt(m->m.getNeerslag()).sum();
    }
}
