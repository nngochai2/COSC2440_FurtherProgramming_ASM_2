package org.nikisurance.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import jakarta.persistence.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nikisurance.entity.*;
import org.nikisurance.service.impl.EMFactory;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @PersistenceContext
    private EntityManager em;

    @FXML
    private AnchorPane sideBar;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private ComboBox<String> loginRoleComboBox;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private double x, y= 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginRoleComboBox.setItems(FXCollections.observableArrayList("Policy Holder", "Dependent", "Policy Owner", "Insurance Surveyor", "Insurance Manager", "Admin"));
        Platform.runLater(() -> {
            if (sideBar.getScene() != null && sideBar.getScene().getWindow() != null) {
                stage = (Stage) sideBar.getScene().getWindow();
                // You can now safely use the stage
            } else {
                System.out.println("Scene or window is not yet initialized.");
            }
        });
        sideBar.setOnMousePressed(mouseEvent -> {
           x = mouseEvent.getSceneX();
           y = mouseEvent.getSceneY();
        });

        sideBar.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }

    @FXML
    public void handleLoginButtonAction() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Person person = null;
        String loginRole = loginRoleComboBox.getValue();

        if (loginRole != null) {
            switch (loginRole) {
                case "Policy Holder" -> person = this.loginAsPolicyHolder(username, password);
                case "Dependent" -> person = this.loginAsDependent(username, password);
                case "Policy Owner" -> person = this.loginAsPolicyOwner(username, password);
                case "Insurance Manager" -> person = this.loginAsManager(username, password);
                case "Insurance Surveyor" -> person = this.loginAsSurveyor(username, password);
                case "Admin" -> person = this.loginAsAdmin(username, password);
                default -> showAlert(Alert.AlertType.ERROR, "Error", "No role selected.");
            }
        }
        if (person != null){
            showAlert(Alert.AlertType.INFORMATION, "Login successful!", "Welcome " + person.getFullName());
            UserSession.getInstance().setLoggedInPerson(person); // Set logged in user

            // Navigate to main application view
            this.redirectToUserUI(person);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login failed!", "Invalid username or password.");
        }
    }

    @FXML
    public PolicyHolder loginAsPolicyHolder(String username, String password) {
        EntityManager em = null;
        try {
            em = EMFactory.getInstance().getEntityManager();
            String queryString = "SELECT p FROM PolicyHolder p WHERE p.username = :username";
            TypedQuery<PolicyHolder> query = em.createQuery(queryString, PolicyHolder.class);
            query.setParameter("username", username);
            PolicyHolder person = query.getSingleResult();

            if (person != null && person.getPassword().equals(password)){
                System.out.println("Logged in successfully");
                System.out.println(person);
                return person;
            } else {
                System.err.println("Login failed");
                return null;
            }
        } catch (NoResultException ex) {
            System.err.println("Person with username '" + username + "' not found");
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @FXML
    public Dependent loginAsDependent(String username, String password) {
        EntityManager em = null;
        try {
            em = EMFactory.getInstance().getEntityManager();
            String queryString = "SELECT d FROM Dependent d WHERE d.username = :username";
            TypedQuery<Dependent> query = em.createQuery(queryString, Dependent.class);
            query.setParameter("username", username);
            Dependent person = query.getSingleResult();

            if (person != null && person.getPassword().equals(password)){
                System.out.println("Logged in successfully");
                System.out.println(person);
                return person;
            } else {
                System.err.println("Login failed");
                return null;
            }
        } catch (NoResultException ex) {
            System.err.println("Person with username '" + username + "' not found");
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @FXML
    public PolicyOwner loginAsPolicyOwner(String username, String password) {
        EntityManager em = null;
        try {
            em = EMFactory.getInstance().getEntityManager();
            String queryString = "SELECT d FROM Dependent d WHERE d.username = :username";
            TypedQuery<PolicyOwner> query = em.createQuery(queryString, PolicyOwner.class);
            query.setParameter("username", username);
            PolicyOwner person = query.getSingleResult();

            if (person != null && person.getPassword().equals(password)){
                System.out.println("Logged in successfully");
                System.out.println(person);
                return person;
            } else {
                System.err.println("Login failed");
                return null;
            }
        } catch (NoResultException ex) {
            System.err.println("Person with username '" + username + "' not found");
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @FXML
    public InsuranceSurveyor loginAsSurveyor(String username, String password) {
        EntityManager em = null;
        try {
            em = EMFactory.getInstance().getEntityManager();
            String queryString = "SELECT i FROM InsuranceSurveyor i WHERE i.username = :username";
            TypedQuery<InsuranceSurveyor> query = em.createQuery(queryString, InsuranceSurveyor.class);
            query.setParameter("username", username);
            InsuranceSurveyor person = query.getSingleResult();

            if (person != null && person.getPassword().equals(password)){
                System.out.println("Logged in successfully");
                System.out.println(person);
                return person;
            } else {
                System.err.println("Login failed");
                return null;
            }
        } catch (NoResultException ex) {
            System.err.println("Person with username '" + username + "' not found");
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @FXML
    public InsuranceManager loginAsManager(String username, String password) {
        EntityManager em = null;
        try {
            em = EMFactory.getInstance().getEntityManager();
            String queryString = "SELECT im FROM InsuranceManager im WHERE im.username = :username";
            TypedQuery<InsuranceManager> query = em.createQuery(queryString, InsuranceManager.class);
            query.setParameter("username", username);
            InsuranceManager person = query.getSingleResult();

            if (person != null && person.getPassword().equals(password)){
                System.out.println("Logged in successfully");
                System.out.println(person);
                return person;
            } else {
                System.err.println("Login failed");
                return null;
            }
        } catch (NoResultException ex) {
            System.err.println("Person with username '" + username + "' not found");
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @FXML
    public Person loginAsAdmin(String username, String password){
        EntityManager em = null;
        try {
            em = EMFactory.getInstance().getEntityManager();
            String queryString = "SELECT p FROM Person p WHERE p.username = :username";
            TypedQuery<Person> query = em.createQuery(queryString, Person.class);
            query.setParameter("username", username);
            Person person = query.getSingleResult();

            if (person != null && person.getPassword().equals(password)){
                System.out.println("Logged in successfully");
                System.out.println(person);
                return person;
            } else {
                System.err.println("Login failed");
                return null;
            }
        } catch (NoResultException ex) {
            System.err.println("Person with username '" + username + "' not found");
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void closeProgram(javafx.scene.input.MouseEvent e) {
        stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void redirectToUserUI(Person person) throws IOException {
        if (stage == null) {
            stage = (Stage) loginButton.getScene().getWindow();
        }
        root = null;
        if (person instanceof Admin) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/AdminUI.fxml")));
        } else if (person instanceof PolicyHolder) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/PolicyHolderUI.fxml")));
        } else if (person instanceof Dependent) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/DependentUI.fxml")));
        } else if (person instanceof PolicyOwner) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/PolicyOwnerUI.fxml")));
        } else if (person instanceof InsuranceManager) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/InsuranceManagerUI.fxml")));
        } else if (person instanceof InsuranceSurveyor) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/nikisurance/fxml/InsuranceSurveyorUI.fxml")));
        } else {
            showAlert(Alert.AlertType.ERROR, "Login failed!", "Unknown user type.");
        }

        scene = new Scene(root);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

}
