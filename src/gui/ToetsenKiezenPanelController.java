package gui;

import controller.*;
import dto.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import org.controlsfx.control.StatusBar;

public class ToetsenKiezenPanelController extends VBox
{

    /**
     * http://jfxtras.org/
     *
     * Voor date and time picker ??
     */
    private ToetsController controller;
    private StatusBar statusBar;

    @FXML
    TableView<ToetsDto> tblToetsen;

    @FXML //ToetsNaam
    TableColumn<ToetsDto, String> colNaam;

    @FXML //ToetsGraad
    TableColumn<ToetsDto, String> colGraad;

    @FXML //ToetsNaam
    TextField txfNaam;

    @FXML //ToetsGraad
    ComboBox<GraadDto> cboGraad;

    @FXML
    Button btnToetsToevoegen;

    @FXML
    VBox vboxGegevens;

    @FXML //ToetsTitel
    TextField txfTitel;

    @FXML //ToetsBeschrijving
    TextField txfBeschrijving;

    @FXML //ToetsStartDatum
    DatePicker datStart;

    @FXML //ToetsEindDatum
    DatePicker datEinde;

    @FXML
    Button btnWijzigToets;

    @FXML
    TableView<KlasDto> tblKlassen;

    @FXML
    TableColumn<KlasDto, String> colKlas;

    @FXML
    ComboBox<KlasDto> cboKlas;

    @FXML
    Button btnKlasToevoegen;

    public ToetsenKiezenPanelController(ToetsController controller, StatusBar statusBar)
    {
        this.controller = controller;
        this.statusBar = statusBar;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToetsenKiezenPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        }
        catch (IOException ex)
        {
            Logger.getLogger(KlasLijstenKiezenPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vboxGegevens.setDisable(true);
        tblToetsen.setItems(controller.geefToetsen());
        tblToetsen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
        {
            vboxGegevens.setDisable(true);
            if (newValue != null)
            {
                txfTitel.setText("");
                txfBeschrijving.setText("");
                datStart.setValue(null);
                datEinde.setValue(null);
                controller.selecteerToets(newValue);
                ToetsDto toets = controller.getGeselecteerdeToets();
                vboxGegevens.setDisable(false);
                if (toets.getBeschrijving() != null)
                    txfBeschrijving.setText(toets.getBeschrijving().getValue());
                if (toets.getAanvang() != null)
                {
                    LocalDate dt = LocalDate.of(toets.getAanvang().get(Calendar.YEAR), toets.getAanvang().get(Calendar.MONTH), toets.getAanvang().get(Calendar.DAY_OF_MONTH));
                    datStart.setValue(dt);
                }
                if (toets.getEind() != null)
                {
                    LocalDate dt = LocalDate.of(toets.getEind().get(Calendar.YEAR), toets.getEind().get(Calendar.MONTH), toets.getEind().get(Calendar.DAY_OF_MONTH));
                    datEinde.setValue(dt);
                }
                tblKlassen.setItems(controller.geefKlassenVanToets());
            }
        });
        TableColumn verwijderToetsCol = new TableColumn<>("");
        tblToetsen.getColumns().add(verwijderToetsCol);
        verwijderToetsCol.setPrefWidth(35);
        verwijderToetsCol.setSortable(false);
        verwijderToetsCol.setResizable(false);
        verwijderToetsCol.setEditable(false);
        verwijderToetsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ToetsDto, Boolean>, ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ToetsDto, Boolean> p)
            {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        verwijderToetsCol.setCellFactory(new Callback<TableColumn<ToetsDto, Boolean>, TableCell<ToetsDto, Boolean>>()
        {
            @Override
            public TableCell<ToetsDto, Boolean> call(TableColumn<ToetsDto, Boolean> p)
            {
                return new ToetsButtonCell();
            }

        });
        tblToetsen.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>()
        {

            @Override
            public Boolean call(TableView.ResizeFeatures p)
            {
                double width = tblToetsen.widthProperty().getValue();
                double buttonWidth = 45;
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
        colNaam.setCellValueFactory(cellData -> cellData.getValue().getTitel());
        colNaam.setCellFactory(new Callback<TableColumn<ToetsDto, String>, TableCell<ToetsDto, String>>()
        {

            @Override
            public TableCell<ToetsDto, String> call(TableColumn<ToetsDto, String> p)
            {
                TextFieldTableCell<ToetsDto, String> cell = new TextFieldTableCell(new DefaultStringConverter());
                cell.setAlignment(Pos.CENTER_LEFT);
                cell.setPadding(new Insets(0, 0, 0, 5));
                return cell;
            }
        });
        colNaam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ToetsDto, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<ToetsDto, String> event)
            {
                ToetsDto dto = (ToetsDto) event.getTableView().getItems().get(event.getTablePosition().getRow());
                dto.setTitel(new SimpleStringProperty(event.getNewValue()));
                controller.wijzigToets(dto);
            }
        });
        colGraad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGraad().getValue().getGraad() + ""));
        colGraad.setCellFactory(new Callback<TableColumn<ToetsDto, String>, TableCell<ToetsDto, String>>()
        {

            @Override
            public TableCell<ToetsDto, String> call(TableColumn<ToetsDto, String> p)
            {
                TextFieldTableCell<ToetsDto, String> cell = new TextFieldTableCell(new DefaultStringConverter());
                cell.setAlignment(Pos.CENTER_LEFT);
                cell.setPadding(new Insets(0, 0, 0, 5));
                return cell;
            }
        });
        cboGraad.setItems(controller.geefAlleGraden());
        btnToetsToevoegen.setGraphic(new ImageView(new Image("/content/images/plus_small.png")));
        colKlas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNaam()));
        colKlas.setCellFactory(new Callback<TableColumn<KlasDto, String>, TableCell<KlasDto, String>>()
        {

            @Override
            public TableCell<KlasDto, String> call(TableColumn<KlasDto, String> p)
            {
                TextFieldTableCell<KlasDto, String> cell = new TextFieldTableCell<KlasDto, String>(new DefaultStringConverter());
                cell.setAlignment(Pos.BASELINE_LEFT);
                cell.setPadding(new Insets(0, 0, 0, 5));
                return cell;
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
        cboKlas.setItems(controller.geefAlleKlassen());
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
                    refreshToetsen();
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
                    refreshKlassen();
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
                dto.setTitel(new SimpleStringProperty(txfNaam.getText()));
                dto.setGraad(new SimpleObjectProperty(cboGraad.getValue()));
                controller.maakNieuweToets(dto);
                txfNaam.setText("");
                refreshToetsen();
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
    private void toetsWijzigen()
    {
        clearErrors();
        if (!(txfTitel.getText().isEmpty() || txfBeschrijving.getText().isEmpty() || datStart.getEditor().getText().isEmpty() || datEinde.getEditor().getText().isEmpty()))
        {
            try
            {
                ToetsDto dto = new ToetsDto();
                //ER BESTAAT MAAR 1 STRING VOOR NAAM EN TITEL IN DOMEIN?
                //dto.setTitel(new SimpleStringProperty(txfTitel.getText()));
                dto.setId(controller.getGeselecteerdeToets().getId());
                dto.setBeschrijving(new SimpleStringProperty(txfBeschrijving.getText()));
                dto.setAanvang(new GregorianCalendar(datStart.getValue().getYear(), datStart.getValue().getMonthValue(), datStart.getValue().getDayOfMonth()));
                dto.setEind(new GregorianCalendar(datEinde.getValue().getYear(), datEinde.getValue().getMonthValue(), datEinde.getValue().getDayOfMonth()));
                controller.wijzigToets(dto);
                statusBar.setText("De wijzigingen werden opgeslaan.");
            }
            catch (IllegalArgumentException ex)
            {
                statusBar.setText(ex.getMessage());
            }
        }
        else
        {
            statusBar.setText("Gelieve alle gegevens correct in te vullen.");
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
                refreshKlassen();
            }
            catch (IllegalArgumentException ex)
            {
                statusBar.setText(ex.getMessage());
            }
        }
    }

    private void refreshToetsen()
    {
        tblToetsen.getItems().clear();
        tblToetsen.setItems(controller.geefToetsen());
    }
    
    private void refreshKlassen()
    {
        tblKlassen.getItems().clear();
        tblKlassen.setItems(controller.geefKlassenVanToets());
    }

    private void clearErrors()
    {
        statusBar.setText("");
    }

}
