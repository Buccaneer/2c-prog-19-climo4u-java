

import controller.DeterminatieController;
import controller.KlimatogramController;
import controller.LeerlingController;
import domein.Continent;
import gui.KlimatogramFrameController;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistentie.GenericDao;
import persistentie.GenericDaoJpa;

/**
 *
 * @author Jasper De Vrient
 */
public class StartUp extends Application{
    @Override
    public void start(Stage stage) {
        KlimatogramFrameController controller = new KlimatogramFrameController(new KlimatogramController(), new DeterminatieController(), new LeerlingController());
        Scene scene = new Scene(controller);
        stage.setScene(scene);
        stage.setTitle("Klimatogrammen");

        // The stage will not get smaller than its preferred (initial) size.
        /*stage.setOnShown(e -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });*/
        
        //stage.setResizable(false);
        
        stage.show();
        
        scene.getStylesheets().add("/gui/styles.css");
    }
    
     public static void main(String... args) {
        Application.launch(StartUp.class, args);
        
    }
}
