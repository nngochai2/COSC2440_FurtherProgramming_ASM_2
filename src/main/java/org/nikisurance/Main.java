package org.nikisurance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.nikisurance.entity.Admin;
import org.nikisurance.entity.PolicyHolder;
import persistence.CustomPersistenceUnitInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private final Logger logger = Logger.getLogger(Main.class.getName());
    private EntityManagerFactory emf;
    private EntityManager em;

    public static void main(String[] args) {
        launch(args);
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

    private void initializeEntityManager() {
        Map<String, Object> properties = new HashMap<>();

        // Show the queries
        properties.put("hibernate.show_sql", "true");

        // Create the context
        emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), properties);

        // Represents the manager of the context
        em = emf.createEntityManager();
    }

    private void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public <T> T login(Class<T> userType, String username, String password) {
        this.initializeEntityManager();
        try {
            em.getTransaction().begin();

            // Query to find the user by username
            String queryString = "SELECT u FROM" + userType.getSimpleName() + " u WHERE u.username = :username";
            TypedQuery<T> query = em.createQuery(queryString, userType);
            query.setParameter("username", username);
            T user = query.getSingleResult();

            // Check if the retrieved user has the correct password
            if (user != null && getPassword(user).equals(password)) {
                System.out.println("Logged in successfully.");
                return user;
            } else {
                System.out.println("Login failed.");
                return null;
            }
        } catch (NoResultException ex) {
            System.out.println(userType.getSimpleName() + " with username '" + username + "' not found.");
            return null;
        } finally {
            closeEntityManager();
        }
    }

    private <T> String getPassword(T user) {
        if (user instanceof Admin) {
            return ((Admin) user).getPassword();
        } else if (user instanceof PolicyHolder) {
            return ((PolicyHolder) user).getPassword();
        } else {
            return null;
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
