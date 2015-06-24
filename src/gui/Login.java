/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import util.FadeInLeftTransition;
import util.FadeInLeftTransition1;
import util.FadeInRightTransition;
import controller.DeterminatieController;
import controller.KlimatogramController;
import controller.LeerlingController;
import controller.ToetsController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Herudi
 */
public class Login extends AnchorPane {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Text lblWelcome;
    @FXML
    private Text lblUserLogin;
    @FXML
    private Text lblUsername;
    @FXML
    private Text lblPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblClose;
    Stage stage;
    private Menu menu;

    public Login() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        Platform.runLater(() -> {
            new FadeInRightTransition(lblUserLogin).play();
            new FadeInLeftTransition(lblWelcome).play();
            new FadeInLeftTransition1(lblPassword).play();
            new FadeInLeftTransition1(lblUsername).play();
            new FadeInLeftTransition1(txtUsername).play();
            new FadeInLeftTransition1(txtPassword).play();
            new FadeInRightTransition(btnLogin).play();
            lblClose.setOnMouseClicked((MouseEvent event) -> {
                Platform.exit();
                System.exit(0);
            });
        });
    }

    @FXML
    private void login(ActionEvent event) {
        if (txtUsername.getText().equals("user") && txtPassword.getText().equals("user")) {

            Task<Scene> task = new Task<Scene>() {
                @Override
                protected Scene call() throws Exception {
                    menu = new Menu(new KlimatogramController(), new DeterminatieController(), new LeerlingController(), new ToetsController());
                    Scene scene = new Scene(menu);

                    return scene;
                }
            };

            task.setOnSucceeded((WorkerStateEvent t) -> {
                Stage stage2 = new Stage();
                Scene s = task.getValue();
                //s.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto");
                s.getStylesheets().add("/css/styles.css");
                s.getStylesheets().add("/css/Metro-UI.css");
                s.getStylesheets().add("/css/winDec.css");
                stage2.setScene(s);
                stage2.setTitle("Climo4U");
                stage2.setOnShown(e -> {
                    stage2.setMinWidth(stage2.getWidth());
                    stage2.setMinHeight(stage2.getHeight() + 5);
                });
                Platform.runLater(() -> stage.close());
                // stage2.setMaximized(true);
                // stage2.getIcons().add(new Image("/gui/content/images/testpictogram.png"));
                stage2.initStyle(StageStyle.UNDECORATED);

                menu.setStage(stage2);
                stage2.setMaximized(true);
                stage2.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

                stage2.show();
            });
            startUpScherm((Stage) lblClose.getScene().getWindow());
            new Thread(task).start();

//            Stage st = new Stage();
//            stage = (Stage) lblClose.getScene().getWindow();
//            Menu controller = new Menu(new KlimatogramController(), new DeterminatieController(), new LeerlingController(), new ToetsController());
//            Scene scene = new Scene(controller);
//            st.initStyle(StageStyle.UNDECORATED);
//            st.setResizable(true);
//            st.setMaximized(false);
//            st.setTitle("Climo4U");
//            st.setScene(scene);
//            st.show();
//            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Foutieve login, controleer uw gebruikersnaam en wachtwoord.");
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Info");
            alert.showAndWait();
        }
    }

    private void startUpScherm(Stage stage) {
        //stage.initStyle(StageStyle.UNDECORATED);
        Scene s = new Scene(new Splash());
        stage.setScene(s);
        stage.show();
        this.stage = stage;
    }

}
