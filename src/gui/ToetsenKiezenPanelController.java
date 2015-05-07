package gui;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import controller.ToetsController;
import dto.GraadDto;
import dto.KlasDto;
import dto.ToetsDto;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.controlsfx.control.StatusBar;

public class ToetsenKiezenPanelController extends VBox
{

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

    @FXML //ToetsBeschrijving
    TextField txfBeschrijving;

    @FXML //ToetsStartDatum
    LocalDateTimeTextField datStart;

    @FXML //ToetsEindDatum
    LocalDateTimeTextField datEinde;

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
        datStart.dateTimeFormatterProperty().set(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        datEinde.dateTimeFormatterProperty().set(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        datEinde.setLocale(Locale.GERMAN);
        datStart.setLocale(Locale.GERMAN);
        vboxGegevens.setDisable(true);
        tblToetsen.setItems(controller.geefToetsen());
        tblToetsen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
        {
            vboxGegevens.setDisable(true);
            if (newValue != null)
            {
                txfBeschrijving.setText("");
                datStart.setLocalDateTime(null);
                datEinde.setLocalDateTime(null);
                controller.selecteerToets(newValue);
                ToetsDto toets = controller.getGeselecteerdeToets();
                vboxGegevens.setDisable(false);
                if (toets.getBeschrijving() != null)
                {
                    txfBeschrijving.setText(toets.getBeschrijving().getValue());
                }
                if (toets.getAanvang() != null)
                {
                    LocalDateTime dt = LocalDateTime.of(toets.getAanvang().get(Calendar.YEAR), toets.getAanvang().get(Calendar.MONTH), toets.getAanvang().get(Calendar.DAY_OF_MONTH), toets.getAanvang().get(Calendar.HOUR_OF_DAY), toets.getAanvang().get(Calendar.MINUTE));
                    datStart.setLocalDateTime(dt);
                }
                if (toets.getEind() != null)
                {
                    LocalDateTime dt = LocalDateTime.of(toets.getEind().get(Calendar.YEAR), toets.getEind().get(Calendar.MONTH), toets.getEind().get(Calendar.DAY_OF_MONTH), toets.getAanvang().get(Calendar.HOUR_OF_DAY), toets.getAanvang().get(Calendar.MINUTE));
                    datEinde.setLocalDateTime(dt);
                }
                tblKlassen.setItems(controller.geefKlassenVanToets());
                refreshKlassen();
            }
        });
        TableColumn verwijderToetsCol = new TableColumn<>("");
        tblToetsen.getColumns().add(verwijderToetsCol);
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
                        if (i == columns - 1 || i == columns - 2)
                        {
                            c.setMinWidth(buttonWidth);
                            c.setMaxWidth(buttonWidth);
                        }
                        else
                        {
                            c.setMinWidth(width - 92);
                            c.setMaxWidth(width - 92);
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
        TableColumn verwijderKlasCol = new TableColumn<>("");
        tblKlassen.getColumns().add(verwijderKlasCol);
        verwijderKlasCol.setSortable(false);
        verwijderKlasCol.setResizable(false);
        verwijderKlasCol.setEditable(false);
        verwijderKlasCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<KlasDto, Boolean>, ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<KlasDto, Boolean> p)
            {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        verwijderKlasCol.setCellFactory(new Callback<TableColumn<KlasDto, Boolean>, TableCell<KlasDto, Boolean>>()
        {
            @Override
            public TableCell<KlasDto, Boolean> call(TableColumn<KlasDto, Boolean> p)
            {
                return new KlasButtonCell();
            }

        });
        tblKlassen.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>()
        {

            @Override
            public Boolean call(TableView.ResizeFeatures p)
            {
                double width = tblKlassen.widthProperty().getValue();
                double buttonWidth = 45;
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
                            c.setMinWidth((width - buttonWidth) / (columns - 1) - 2);
                            c.setMaxWidth((width - buttonWidth) / (columns - 1) - 2);
                        }
                    }
                    i++;
                }
                return true;
            }
        });
        cboKlas.setSkin(new CustomComboBoxListViewSkin(cboKlas));
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
                    tblToetsen.getSelectionModel().select(null);
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
        if (!(txfBeschrijving.getText().isEmpty() || datStart.getLocalDateTime() == null || datEinde.getLocalDateTime() == null))
        {
            try
            {
                ToetsDto dto = new ToetsDto();
                dto.setId(controller.getGeselecteerdeToets().getId());
                dto.setBeschrijving(new SimpleStringProperty(txfBeschrijving.getText()));
                dto.setAanvang(new GregorianCalendar(datStart.getLocalDateTime().getYear(), datStart.getLocalDateTime().getMonthValue(), datStart.getLocalDateTime().getDayOfMonth(), datStart.getLocalDateTime().getHour(), datStart.getLocalDateTime().getMinute()));
                dto.setEind(new GregorianCalendar(datEinde.getLocalDateTime().getYear(), datEinde.getLocalDateTime().getMonthValue(), datEinde.getLocalDateTime().getDayOfMonth(), datEinde.getLocalDateTime().getHour(), datEinde.getLocalDateTime().getMinute()));
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

    private void refreshKlassen()
    {
        cboKlas.getItems().clear();
        cboKlas.setItems(controller.geefKlassenNietVanToets());
    }

    private class CustomComboBoxListViewSkin<T> extends ComboBoxListViewSkin<T>
    {

        CustomComboBoxListViewSkin(final ComboBox<T> comboBox)
        {
            super(comboBox);
        }

        @Override
        protected PopupControl getPopup()
        {
            popup = new PopupControl()
            {

                @Override
                public Styleable getStyleableParent()
                {
                    return getSkinnable();
                }

                /*@Override
                public void show()
                {

                }*/
                
                {
                    
                    setSkin(new Skin<Skinnable>()
                    {
                        @Override
                        public Skinnable getSkinnable()
                        {
                            return getSkinnable();
                        }

                        @Override
                        public Node getNode()
                        {
                            return getPopupContent();
                        }

                        @Override
                        public void dispose()
                        {
                        }
                    });
                }
            };
            popup.getStyleClass().add(COMBO_BOX_STYLE_CLASS);
            popup.setAutoHide(true);
            popup.setAutoFix(true);
            popup.setHideOnEscape(true);
            popup.setOnAutoHide(new EventHandler<Event>()
            {
                @Override
                public void handle(Event e)
                {
                    getBehavior().onAutoHide();
                }
            });
            popup.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent t)
                {
                    getBehavior().onAutoHide();
                }
            });
            InvalidationListener layoutPosListener = new InvalidationListener()
            {
                @Override
                public void invalidated(Observable o)
                {
                    reconfigurePopup();
                }
            };
            getSkinnable().layoutXProperty().addListener(layoutPosListener);
            getSkinnable().layoutYProperty().addListener(layoutPosListener);
            getSkinnable().widthProperty().addListener(layoutPosListener);
            return popup;
        }
        @Override
        public void show()
        {
            if (getSkinnable() == null)
            {
                throw new IllegalStateException("ComboBox is null");
            }
            Node content = getPopupContent();
            if (content == null)
            {
                throw new IllegalStateException("Popup node is null");
            }
            if (getPopup().isShowing())
            {
                return;
            }
            positionAndShowPopup();
        }

        private void positionAndShowPopup()
        {
            if (getPopup().getSkin() == null)
            {
                getSkinnable().getScene().getRoot().impl_processCSS(true);
            }

            Point2D p = getPrefPopupPosition();
            getPopup().getScene().setNodeOrientation(getSkinnable().getEffectiveNodeOrientation());
            getPopup().show(getSkinnable().getScene().getWindow(), p.getX(), p.getY());
        }

        private Point2D getPrefPopupPosition()
        {
            double dx = 0;
            dx += (getSkinnable().getEffectiveNodeOrientation() == NodeOrientation.RIGHT_TO_LEFT) ? -1 : 1;
            return com.sun.javafx.Utils.pointRelativeTo(getSkinnable(), getPopupContent(), HPos.CENTER, VPos.TOP, dx, 0, false);
        }

        void reconfigurePopup()
        {
            if (!getPopup().isShowing())
            {
                return;
            }
            Point2D p = getPrefPopupPosition();
            reconfigurePopup(p.getX(), p.getY(), getPopupContent().prefWidth(1), getPopupContent().prefHeight(1));
        }

        void reconfigurePopup(double x, double y, double minWidth, double minHeight)
        {
            if (!getPopup().isShowing())
            {
                return;
            }

            if (x > -1)
            {
                getPopup().setX(x);
            }
            if (y > -1)
            {
                getPopup().setY(y);
            }
            if (minWidth > -1)
            {
                getPopup().setMinWidth(minWidth);
            }
            if (minHeight > -1)
            {
                getPopup().setMinHeight(minHeight);
            }
        }

    }

    private void refreshToetsen()
    {
        tblToetsen.getItems().clear();
        tblToetsen.setItems(controller.geefToetsen());
    }

    private void clearErrors()
    {
        statusBar.setText("");
    }

    public ReadOnlyObjectProperty<ToetsDto> getGeselecteerdeToetsProperty()
    {
        return tblToetsen.getSelectionModel().selectedItemProperty();
    }

}
