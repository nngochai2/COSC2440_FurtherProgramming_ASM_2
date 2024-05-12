package org.nikisurance;

import jakarta.persistence.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.nikisurance.entity.*;
import org.nikisurance.repository.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private final Logger logger = Logger.getLogger(Main.class.getName());
    private EntityManagerFactory emf;
    private EntityManager em;
    private static final Scanner scanner = new Scanner(System.in);


    public void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
//        EntityManager em = emf.createEntityManager();
//        Map<String, String> properties = new HashMap<>();
//        properties.put("hibernate.show_sql", "true");
//
//        try {
//            em.getTransaction().begin();
//
//            // Sample method to get a policy owner
//            PolicyOwner policyOwner = em.find(PolicyOwner.class, 1);
//            System.out.println(policyOwner);
//
//            InsuranceManager insuranceManager = new InsuranceManager();
//            insuranceManager.setName("Hai Pro");
//            insuranceManager.setPassword("Hai123456");
//            insuranceManager.setRole(ProviderRole.INSURANCE_MANAGER);
//            em.persist(insuranceManager);
//
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }

        ClaimRepositoryImpl claimRepository = new ClaimRepositoryImpl();
        DependentRepositoryImpl dependentRepository = new DependentRepositoryImpl();
        InsuranceCardRepositoryImpl insuranceCardRepository = new InsuranceCardRepositoryImpl();
        InsuranceManagerRepositoryImpl insuranceManagerRepository = new InsuranceManagerRepositoryImpl();
        InsuranceSurveyorRepositoryImpl insuranceSurveyorRepository = new InsuranceSurveyorRepositoryImpl();
        PolicyHolderRepositoryImpl policyHolderRepository = new PolicyHolderRepositoryImpl();
        InsuranceCardRepositoryImpl insuranceCardRepositoryImpl = new InsuranceCardRepositoryImpl();

        System.out.println("\n=============================================================== WELCOME TO INSURANCE CLAIMS MANAGEMENT SYSTEM! ===============================================================");
        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
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

    public <T> T login(Class<T> userType, String username, String password) {
        try {
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
                System.err.println("Login failed.");
                return null;
            }
        } catch (NoResultException ex) {
            System.out.println(userType.getSimpleName() + " with username '" + username + "' not found.");
            return null;
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
