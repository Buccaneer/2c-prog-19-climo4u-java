

import controller.DeterminatieController;
import controller.KlimatogramController;
import controller.LeerlingController;
import controller.ToetsController;
import gui.KlimatogramFrameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jasper De Vrient
 */
public class StartUp extends Application{
    @Override
    public void start(Stage stage) {
        KlimatogramFrameController controller = new KlimatogramFrameController(new KlimatogramController(), new DeterminatieController(), new LeerlingController(), new ToetsController());
        Scene scene = new Scene(controller);
        stage.setScene(scene);
        stage.setTitle("Klimatogrammen");

        // The stage will not get smaller than its preferred (initial) size.
        stage.setOnShown(e -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
                
        stage.show();
        
        scene.getStylesheets().add("/gui/styles.css");
    }
    
     public static void main(String... args) {
        Application.launch(StartUp.class, args);
        
    }
}
