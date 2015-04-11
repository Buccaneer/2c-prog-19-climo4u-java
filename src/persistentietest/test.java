/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentietest;

import domein.VergelijkingsOperator;
import dto.DeterminatieKnoopDto;
import dto.ParameterDto;
import dto.VegetatieTypeDto;
import dto.VergelijkingDto;
import gui.ConstanteWaardeProperty;
import gui.DeterminatieKnoopProperty;
import gui.OperatorProperty;
import gui.ParameterProperty;
import gui.PropertyContainerPanelController;
import gui.PropertyVeranderdListener;
import gui.StringProperty;
import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.*;
import org.controlsfx.control.PropertySheet.Item;
import org.controlsfx.property.editor.*;
import org.controlsfx.property.*;

/**
 *
 * @author Jasper De Vrient
 */
public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
    
        StackPane root = new StackPane();
        // root.getChildren().add(btn);

        ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();
        PropertyContainerPanelController pcpc = new PropertyContainerPanelController(null);
        
        root.getChildren().add(pcpc);
        DeterminatieKnoopDto dto = new DeterminatieKnoopDto();
        dto.toResultaatBlad();
      //  dto.setVergelijking(new VergelijkingDto(new ParameterDto(), VergelijkingsOperator.GROTERDAN, new ParameterDto()));
        dto.setVegetatieType(new VegetatieTypeDto());
        pcpc.selectieGewijzigd(dto);
        
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
