/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import util.FadeInLeftTransition;
import util.FadeInRightTransition;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Herudi
 */
public class Splash extends AnchorPane {

    @FXML
    private Text lblWelcome;
    @FXML
    private Text lblClimo;
    @FXML
    private Label lblClose;
    @FXML
    private Text lblGeduld;
    Stage stage;
    @FXML
    private ImageView imgLoading;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    public Splash() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Splash.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
        }
        longStart();
        lblClose.setOnMouseClicked((MouseEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void longStart() {
//        Service<ApplicationContext> service = new Service<ApplicationContext>() {
//            @Override
//            protected Task<ApplicationContext> createTask() {
//                return new Task<ApplicationContext>() {
//                    @Override
//                    protected ApplicationContext call() throws Exception {
//                        ApplicationContext appContex = config.getInstance().getApplicationContext();
//                        int max = appContex.getBeanDefinitionCount();
//                        updateProgress(0, max);
//                        for (int k = 0; k < max; k++) {
//                            Thread.sleep(50);
//                            updateProgress(k + 1, max);
//                        }
//                        return appContex;
//                    }
//                };
//            }
//        };
//        service.start();
//        service.setOnRunning((WorkerStateEvent event) -> {
        new FadeInLeftTransition(lblWelcome).play();
        new FadeInRightTransition(lblClimo).play();
        new FadeInLeftTransition(lblGeduld).play();
//        });
//        service.setOnSucceeded((WorkerStateEvent event) -> {
//
//            Stage st = new Stage();
//            stage = (Stage) lblClose.getScene().getWindow();
//            Login controller = new Login();
//            Scene scene = new Scene(controller);
//            st.initStyle(StageStyle.UNDECORATED);
//            st.setResizable(true);
//            st.setMaximized(false);
//            st.setScene(scene);
//            st.show();
//            stage.close();
//
//        });
    }
}
