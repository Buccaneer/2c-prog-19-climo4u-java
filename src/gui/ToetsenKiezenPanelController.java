package gui;

import controller.*;
import dto.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

public class ToetsenKiezenPanelController extends VBox
{
    private ToetsController controller;
    private StatusBar statusBar;
    
    @FXML
    TableView<ToetsDto> tblToetsen;
    
    @FXML //ToetsNaam
    TableColumn colNaam;
    
    @FXML //ToetsGraad
    TableColumn colGraad;
    
    @FXML //ToetsNaam
    TextField txfNaam;
    
    @FXML //ToetsGraad
    ComboBox<GraadDto> cboGraad;
    
    @FXML
    Button btnToetsToevoegen;
    
    @FXML //ToetsTitel
    TextField txfTitel;
    
    @FXML //ToetsBeschrijving
    TextField txfBeschrijving;
    
    @FXML //ToetsStartDatum
    TextField txfStart;
    
    @FXML //ToetsEindDatum
    TextField txfEinde;
    
    @FXML
    TableView tblKlassen;
    
    @FXML
    TableColumn colKlas;
    
    @FXML
    TextField txfKlas;
    
    @FXML
    Button btnKlasToevoegen;
    
    public ToetsenKiezenPanelController(ToetsController controller, StatusBar statusBar) {
        this.controller = controller;
        this.statusBar = statusBar;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToetsenKiezenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(KlasLijstenKiezenPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //TODO: UNCOMMENT ZODRA CONTROLLER WERKT
        //tblToetsen.setItems(controller.geefToetsen());
        tblToetsen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                //TODO: UNCOMMENT ZODRA CONTROLLER WERKT
                //controller.selecteerToets(newValue);
            }
        });
        cboGraad.setItems(controller.geefAlleGraden());
        btnToetsToevoegen.setGraphic(new ImageView(new Image("/content/images/plus_small.png")));
        
        
        
        
        
        btnKlasToevoegen.setGraphic(new ImageView(new Image("/content/images/plus_small.png")));
    }
    
    private class ToetsButtonCell extends TableCell<ToetsDto, Boolean>
    {

        final Button cellButton = new Button();

        ToetsButtonCell()
        {
            cellButton.setGraphic(new ImageView(new Image("/content/images/xSmall.png")));
            cellButton.getStyleClass().add("cancel");
            cellButton.setPrefSize(25, 25);
            cellButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent t)
                {
                    ToetsDto dto = (ToetsDto) ToetsButtonCell.this.getTableView().getItems().get(ToetsButtonCell.this.getIndex());
                    controller.verwijderToets(dto);
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty)
        {
            super.updateItem(t, empty);
            if (!empty)
            {
                setGraphic(cellButton);
            }
            else
            {
                setGraphic(null);
            }
        }
    }
    
    private class KlasButtonCell extends TableCell<KlasDto, Boolean>
    {

        final Button cellButton = new Button();

        KlasButtonCell()
        {
            cellButton.setGraphic(new ImageView(new Image("/content/images/xSmall.png")));
            cellButton.getStyleClass().add("cancel");
            cellButton.setPrefSize(25, 25);
            cellButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent t)
                {
                    KlasDto dto = (KlasDto) KlasButtonCell.this.getTableView().getItems().get(KlasButtonCell.this.getIndex());
                    controller.verwijderKlas(dto);
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty)
        {
            super.updateItem(t, empty);
            if (!empty)
            {
                setGraphic(cellButton);
            }
            else
            {
                setGraphic(null);
            }
        }
    }
    
    @FXML
    private void toetsToevoegen()
    {
        clearErrors();
        if (!(txfNaam.getText().isEmpty() || cboGraad.getSelectionModel().isEmpty()))
        {
            try
            {
                ToetsDto dto = new ToetsDto();
                dto.setNaam(null);
                controller.maakNieuweToets(dto);
                txfNaam.setText("");
            }
            catch (IllegalArgumentException ex)
            {
                statusBar.setText(ex.getMessage());
            }
        }
        else
        {
            statusBar.setText("Gelieve een naam in te vullen en een graad te selecteren.");
        }
    }
    
    @FXML
    private void klasToevoegen()
    {
        
    }
    
    @FXML
    private void todo()
    {
        
    }
    
    private void clearErrors()
    {
        statusBar.setText("");
    }
    
}
