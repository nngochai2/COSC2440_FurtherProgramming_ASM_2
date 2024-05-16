package org.nikisurance;

import jakarta.persistence.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nikisurance.controller.LoginController;
import org.nikisurance.controller.SystemAdminController;
import org.nikisurance.entity.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    private static final EntityManager em = emf.createEntityManager();
    private static final Map<String, String> properties = new HashMap<>();


    public static void main(String[] args) {
        properties.put("hibernate.show_sql", "true");
        em.getTransaction().begin();



//        ClaimRepositoryImpl claimRepository = new ClaimRepositoryImpl();
//        DependentRepositoryImpl dependentRepository = new DependentRepositoryImpl();
//        InsuranceCardRepositoryImpl insuranceCardRepository = new InsuranceCardRepositoryImpl();
//        InsuranceManagerRepositoryImpl insuranceManagerRepository = new InsuranceManagerRepositoryImpl();
//        InsuranceSurveyorRepositoryImpl insuranceSurveyorRepository = new InsuranceSurveyorRepositoryImpl();
//        PolicyHolderRepositoryImpl policyHolderRepository = new PolicyHolderRepositoryImpl();
//        InsuranceCardRepositoryImpl insuranceCardRepositoryImpl = new InsuranceCardRepositoryImpl();



//        System.out.println("\n=============================================================== WELCOME TO INSURANCE CLAIMS MANAGEMENT SYSTEM! ===============================================================");
//        System.out.println("Enter your username:");
//        String username = scanner.nextLine();
//
//        System.out.println("Enter your password:");
//        String password = scanner.nextLine();

        PolicyOwner policyOwner = new PolicyOwner();
        policyOwner.setUsername("nikilon");
        policyOwner.setPassword("nikilon");
        policyOwner.setFullName("Nguyen Chi Nghia");

        PolicyHolder policyHolder = new PolicyHolder();
        policyHolder.setUsername("hainguyen");
        policyHolder.setPassword("hainguyen");
        policyHolder.setFullName("Nguyen Ngoc Hai");
        policyHolder.setBankName("MB Bank");
        policyHolder.setBankNumber(12345678L);
        policyHolder.setEmail("hainguyen@gmail.com");
        policyHolder.setPhoneNumber(123456789L);
        policyHolder.setAddress("Hanoi, Vietnam");
        policyHolder.setPolicyOwner(policyOwner);
        policyHolder.setCustomerType("BENEFICIARY");
        policyHolder.setBeneficiaryType("POLICYHOLDER");

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/AdminUI.fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
//            LoginController loginController = loader.getController();
//            loginController.setStage(stage);
            SystemAdminController systemAdminController = loader.getController();
            systemAdminController.setStage(stage);
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

    @FXML
    public Person login(String username, String password) {
        try {
            // Query to find the person by username
            String queryString = "SELECT u FROM Person u WHERE u.username = :username";
            TypedQuery<Person> query = em.createQuery(queryString, Person.class);
            query.setParameter("username", username);
            Person person = query.getSingleResult();

            // Check if the retrieved person has the correct password
            if (person != null && person.getPassword().equals(password)) {
                System.out.println("Logged in successfully.");
                return person;
            } else {
                System.err.println("Login failed.");
                return null;
            }
        } catch (NoResultException ex) {
            System.out.println("Person with username '" + username + "' not found.");
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
