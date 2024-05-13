package org.nikisurance;
import org.nikisurance.service.*;

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
        launch(args);

        // Instantiate the service classes
        ClaimService claimService = new ClaimService();
        CustomerService customerService = new CustomerService();
        InsuranceCardService insuranceCardService = new InsuranceCardService();

        // Use the service classes to create entities
        Claim claim = claimService.makeClaim()
                .withClaimId("c-0000000001")
                .withClaimDate(new Date())
                .withInsuredPerson("John Doe")
                .withCardNumber(123456789)
                .withExamDate(new Date())
                .withClaimAmount(1000)
                .withStatus(ClaimStatus.NEW)
                .withReceiverBankingInfo("Bank Info")
                .withSurveyorId(1L)
                .build();

        PolicyHolder policyHolder = customerService.makePolicyHolder()
                .withBankName("Bank Name")
                .withBankNumber(123456789L)
                .withEmail("john.doe@example.com")
                .withPhoneNumber(1234567890L)
                .withAddress("123 Street, City, Country")
                .withInsuranceCard(insuranceCardService.makeCard().build())
                .build();

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
