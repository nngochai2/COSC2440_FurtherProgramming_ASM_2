package org.nikisurance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.nikisurance.entity.Admin;
import persistence.CustomPersistenceUnitInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        Map<String, Object> properties = new HashMap<>();

        // Show the queries
        properties.put("hibernate.show_sql", "true");

        // Create the context
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), properties);

        // Represents the manager of the context
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin(); // Start the transaction

            System.out.println("Welcome to the application.");
            launch(args);

            // Get an entity
            Admin admin = em.find(Admin.class, 123456);
            System.out.println("Admin found: " + admin);

            em.getTransaction().commit(); // End of the transaction
        } finally {
            em.close();
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/Main.fxml")));
            Scene scene = new Scene(root);
            stage.setTitle("Nikisurance");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            // Method to call logout method before closing the application using "X" button
            stage.setOnCloseRequest(windowEvent -> {
                windowEvent.consume();
                logout(stage);
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while loading FXML file", e);
        }
    }

    // Method to close the application
    public void logout(Stage stage) {
        // Asking for user confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout!");
        alert.setContentText("Do you want to close the application");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("You have successfully logged out!");
            stage.close();
        }
    }
}
