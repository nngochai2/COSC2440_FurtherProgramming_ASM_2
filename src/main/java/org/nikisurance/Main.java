package org.nikisurance;

import jakarta.persistence.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nikisurance.controller.LoginController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    private static final EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/Main.fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            LoginController loginController = loader.getController();
            loginController.setStage(stage);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Nikisurance");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            // Method to call logout method before closing the application using "X" button
            stage.setOnCloseRequest(windowEvent -> {
//                windowEvent.consume();
//                logout(stage);
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while loading FXML file", e);
        }
    }
}