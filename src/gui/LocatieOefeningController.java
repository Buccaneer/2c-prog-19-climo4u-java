package gui;

import controller.ToetsController;
import dto.KlimatogramDto;
import dto.VraagDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jasper De Vrient
 */
public class LocatieOefeningController extends VBox implements IToetsVraag {

    @FXML
    private ListView<KlimatogramDto> lstKlimatogrammen;
    @FXML
    private ComboBox<KlimatogramDto> cboKlimatogram;
    @FXML
    private Button btnToevoegen;
    @FXML
    private Button btnVerwijderen;

    private final String TOEVOEGENTEKST = "Toevoegen";
    private final String WIJZIGENTEKST = "Wijzigen";

    private ToetsController controller;
    private ObservableList<KlimatogramDto> items = FXCollections.observableArrayList();
    private KlimatogramDto geselecteerdeKlimatogram = null;

    @Override
    public String getFxmlBestand() {
        return "/fxml/LocatieVraagPanel.fxml";
    }

    @Override
    public void setController(ToetsController controller) {
        this.controller = controller;
    }

    @Override
    public void laden(VraagDto vraag) {
        cboKlimatogram.setItems(controller.geefKlimatogrammen());
        lstKlimatogrammen.setItems(items);
        items.clear();

        items.addAll(vraag.getKlimatogrammen());

        lstKlimatogrammen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() >= 0) {
                    geselecteerdeKlimatogram = items.get(newValue.intValue());
                    cboKlimatogram.getSelectionModel().select(geselecteerdeKlimatogram);
                    btnToevoegen.setText(WIJZIGENTEKST);
                }
            }

        });

        btnToevoegen.setText(TOEVOEGENTEKST);
    }

    @Override
    public void opslaan(VraagDto vraag) {
        vraag.getKlimatogrammen().clear();
        vraag.getKlimatogrammen().addAll(items);
    }

    public void toevoegen() {
        if (geselecteerdeKlimatogram == null) {
            items.add(cboKlimatogram.getSelectionModel().getSelectedItem());
        } else {
            int i = items.indexOf(geselecteerdeKlimatogram);
            items.set(i, cboKlimatogram.getSelectionModel().getSelectedItem());
        }

        geselecteerdeKlimatogram = null;
        btnToevoegen.setText(TOEVOEGENTEKST);
    }

    public void verwijderen() {
        if (geselecteerdeKlimatogram != null) {
            items.remove(geselecteerdeKlimatogram);
        }

        geselecteerdeKlimatogram = null;
        btnToevoegen.setText(TOEVOEGENTEKST);
        lstKlimatogrammen.getSelectionModel().clearSelection();
    }

}
