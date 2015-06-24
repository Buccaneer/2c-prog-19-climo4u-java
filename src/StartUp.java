
import gui.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jasper De Vrient
 */
public class StartUp extends Application {

    @Override
    public void start(Stage stage) {
//        Task<Scene> task = new Task<Scene>() {
//            @Override
//            protected Scene call() throws Exception {
//                Login login = new Login(new KlimatogramController(), new DeterminatieController(), new LeerlingController(), new ToetsController());
//                Scene scene = new Scene(login);
//
//                return scene;
//            }
//        };
        Login login = new Login();
        Scene scene = new Scene(login);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
//        task.setOnSucceeded((WorkerStateEvent t) -> {
//            Stage stage2 = new Stage();
//            Scene s = task.getValue();
//            //s.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto");
//            s.getStylesheets().add("/css/styles.css");
//            stage2.setScene(s);
//            stage2.setTitle("Climo4U");
//            stage2.setOnShown(e -> {
//                stage2.setMinWidth(stage2.getWidth());
//                stage2.setMinHeight(stage2.getHeight() + 5);
//            });
//            stage.close();
//            // stage2.setMaximized(true);
//            // stage2.getIcons().add(new Image("/gui/content/images/testpictogram.png"));
//            stage2.initStyle(StageStyle.UNDECORATED);
//            stage2.show();
//        });
////        stage.getIcons().add(new Image("/gui/content/images/testpictogram.png"));
//        startUpScherm(stage);
//        new Thread(task).start();

//        Scene scene = new Scene(new Splash());
//        stage.setScene(scene);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.show();
//        KlimatogramFrameController dc = new KlimatogramFrameController(new KlimatogramController(), new DeterminatieController(), new LeerlingController(), new ToetsController());
//        Scene scene = new Scene(dc);
//        scene.getStylesheets().add("/css/styles.css");
//        stage.setScene(scene);
//        stage.show();
//        KlimatogramFrameController controller = new KlimatogramFrameController(new KlimatogramController(), new DeterminatieController(), new LeerlingController(), new ToetsController());
//        Scene scene = new Scene(controller);
//        stage.setScene(scene);
//        stage.setTitle("Klimatogrammen");
        // The stage will not get smaller than its preferred (initial) size.
//        stage.setOnShown(e -> {
//            stage.setMinWidth(stage.getWidth());
//            stage.setMinHeight(stage.getHeight());
//        });
//
//        stage.show();
//
//        scene.getStylesheets().add("/gui/styles.css");
    }

    public static void main(String... args) {
        Application.launch(StartUp.class, args);
    }
//
//    private void startUpScherm(Stage stage) {
//        stage.initStyle(StageStyle.UNDECORATED);
//        Scene s = new Scene(new Splash());
//        stage.setScene(s);
//        stage.show();
//    }
}
