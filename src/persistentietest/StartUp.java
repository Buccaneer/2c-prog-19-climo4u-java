package persistentietest;

import controller.KlimatogramController;
import persistentie.*;
import domein.*;
import dto.ContinentDto;
import dto.KlimatogramDto;
import dto.LandDto;
import dto.MaandDto;
import java.util.List;
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

        ObservableList<ContinentDto> items = kc.getContinenten();

        for (ContinentDto dto : items) {
            System.out.println(dto.getNaam());

        }

        kc.selecteerContinent(items.get(1));

        ObservableList<LandDto> landen = kc.getLanden();

        for (LandDto dto : landen) {
            System.out.println(dto.getNaam());

        }

        kc.selecteerLand(landen.get(0));

        ObservableList<KlimatogramDto> klimatogrammen = kc.getKlimatogrammen();

        for (KlimatogramDto dto : klimatogrammen) {
            System.out.println(dto.getLocatie());
        }

        ContinentDto c = new ContinentDto();
        c.setNaam("Middle Earth");
        kc.voegContinentToe(c);

        kc.selecteerContinent(c);

        LandDto shire = new LandDto();
        shire.setNaam("The Shire");

        kc.voegLandToe(shire);
        kc.selecteerLand(shire);

        KlimatogramDto dto = new KlimatogramDto();
        dto.setBeginJaar(1999);
        dto.setEindJaar(2003);
        dto.setLatitude(4.555);
        dto.setLocatie("Bilbos house");
        dto.setLongitude(2.3333);
        dto.setStation("12345");
        dto.maanden = FXCollections.observableArrayList();
        for (int i = 0; i < 12; i++) {
            MaandDto maand = new MaandDto();
            int ii = i + 1;
            String naam = String.format("%d", ii);
            maand.setNaam(naam);
            maand.setNeerslag(150);
            maand.setTemperatuur(22.3);
            dto.maanden.add(maand);
        }

        
        kc.voegKlimatogramToe(dto);
        GenericDaoJpa.closePersistency();
    }

}