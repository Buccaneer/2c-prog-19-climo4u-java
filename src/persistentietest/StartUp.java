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
       
       kc.selecteerContinent(cds.get(0));
   //     kc.verwijderContinent(cds.get(0));
        
       List<LandDto> landen = kc.getLanden();
//        
//        
       System.out.println(landen.get(0).getNaam());
     //   kc.verwijderLand(landen.get(0));
        
        kc.selecteerLand(landen.get(0));
//        
        List<KlimatogramDto> klims = kc.getKlimatogrammen();
//        
        kc.selecteerKlimatogram(klims.get(0));
//       
        
        System.out.println(klims.get(0).getLocatie());
        kc.verwijderKlimatogram(klims.get(0).getLocatie());
        GenericDaoJpa.closePersistency();
    }

}