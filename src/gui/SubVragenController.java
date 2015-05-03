package gui;

import controller.ToetsController;
import dto.KlimatogramDto;
import dto.VraagDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jasper De Vrient
 */
public class SubVragenController extends VBox implements IToetsVraag {

    @FXML private Button btnToevoegen;
    @FXML private Button btnVerwijderen;
    @FXML private ComboBox<KlimatogramDto> cboKlimatogram;
    @FXML private ListView<String> lstVragen;
    @FXML private TextField txtVraag;
    
    private ObservableList<String> items = FXCollections.observableArrayList();
    private ToetsController controller;
    
    @Override
    public String getFxmlBestand() {
       return "SubVragenPanel.fxml";
    }

    @Override
    public void setController(ToetsController controller) {
        this.controller = controller;
    }

    @Override
    public void laden(VraagDto vraag) {
            cboKlimatogram.setItems(controller.geefKlimatogrammen());
        if (vraag.getKlimatogrammen().size() > 0) {
            KlimatogramDto first = vraag.getKlimatogrammen().get(0);
            cboKlimatogram.getSelectionModel().select(first);
        }
        items.addAll(vraag.getSubvragen().toArray(new String[] {}));
    }

    @Override
    public void opslaan(VraagDto vraag) {
        vraag.getKlimatogrammen().clear();
        vraag.getKlimatogrammen().add(cboKlimatogram.getSelectionModel().getSelectedItem());
        vraag.getSubvragen().clear();
        vraag.getSubvragen().addAll(items);
    }

    
    public void toevoegen() {
      items.add(txtVraag.getText());
      txtVraag.selectAll();
    }
    
    public void verwijderen() {
        if (!lstVragen.getSelectionModel().isEmpty()) {
            int index = lstVragen.getSelectionModel().getSelectedIndex();
            items.remove(index);
        }
    }
}
