package gui;

import controller.*;
import dto.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
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
    TableView<KlasDto> tblKlassen;
    
    @FXML
    TableColumn colKlas;
    
    @FXML
    ComboBox<KlasDto> cboKlas;
    
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
        //cboGraad.setItems(controller.geefAlleGraden());
        tblToetsen.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>()
        {

            @Override
            public Boolean call(TableView.ResizeFeatures p)
            {
                double width = tblToetsen.widthProperty().getValue();
                double buttonWidth = 34;
                int i = 0;
                int columns = 3;
                for (TableColumn c : tblToetsen.getColumns())
                {
                    if (i < columns)
                    {
                        if (i == columns - 1)
                        {
                            c.setMinWidth(buttonWidth);
                            c.setMaxWidth(buttonWidth);
                        }
                        else
                        {
                            c.setMinWidth((width - buttonWidth) / (columns - 1) - 1);
                            c.setMaxWidth((width - buttonWidth) / (columns - 1) - 1);
                        }
                    }
                    i++;
                }
                return true;
            }
        });
        btnToetsToevoegen.setGraphic(new ImageView(new Image("/content/images/plus_small.png")));
        
        txfTitel.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>()
        {

            @Override
            public void handle(InputMethodEvent t)
            {
                //getToetsDto, wijzigToetsDto, geefToetsDtoAanController
            }
        });
        
        txfBeschrijving.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>()
        {

            @Override
            public void handle(InputMethodEvent t)
            {
                //getToetsDto, wijzigToetsDto, geefToetsDtoAanController
            }
        });
        
        txfStart.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>()
        {

            @Override
            public void handle(InputMethodEvent t)
            {
                //getToetsDto, wijzigToetsDto, geefToetsDtoAanController
            }
        });
        
        txfEinde.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>()
        {

            @Override
            public void handle(InputMethodEvent t)
            {
                //getToetsDto, wijzigToetsDto, geefToetsDtoAanController
            }
        });
        
        tblKlassen.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>()
        {

            @Override
            public Boolean call(TableView.ResizeFeatures p)
            {
                double width = tblKlassen.widthProperty().getValue();
                double buttonWidth = 34;
                int i = 0;
                int columns = 2;
                for (TableColumn c : tblKlassen.getColumns())
                {
                    if (i < columns)
                    {
                        if (i == columns - 1)
                        {
                            c.setMinWidth(buttonWidth);
                            c.setMaxWidth(buttonWidth);
                        }
                        else
                        {
                            c.setMinWidth((width - buttonWidth) / (columns - 1) - 1);
                            c.setMaxWidth((width - buttonWidth) / (columns - 1) - 1);
                        }
                    }
                    i++;
                }
                return true;
            }
        });
        
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
        clearErrors();
        if (!cboKlas.getSelectionModel().isEmpty())
        {
            try
            {
                KlasDto dto = cboKlas.getSelectionModel().getSelectedItem();
                controller.voegKlasToe(dto);
            }
            catch (IllegalArgumentException ex)
            {
                statusBar.setText(ex.getMessage());
            }
        }
    }
    
    @FXML
    private void todo()
    {
        clearErrors();
    }
    
    private void clearErrors()
    {
        statusBar.setText("");
    }
    
}
