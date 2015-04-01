package persistentietest;

import controller.KlimatogramController;
import persistentie.*;
import domein.*;
import dto.ContinentDto;
import dto.KlimatogramDto;
import dto.LandDto;
import dto.MaandDto;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jasper De Vrient
 */
public class StartUp {

    public static void main(String[] args) {
        System.out.println("hier");
        GenericDao<Continent, String> dao = new GenericDaoJpa<>(Continent.class);

        controller.KlimatogramController kc = new KlimatogramController();
        kc.setContinentRepository(dao);

        List<ContinentDto>cds = kc.getContinenten();
        
        for (ContinentDto cd : cds) {
            System.out.println(cd.getNaam());
            for (Map.Entry<String, Boolean> e : cd.getGraden().entrySet()) {
                System.out.printf("%s %b\n",e.getKey(), e.getValue());
            }
        }
        
        GenericDaoJpa.closePersistency();
    }

}