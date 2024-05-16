package org.nikisurance.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import jakarta.persistence.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.nikisurance.entity.Person;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
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


    private Stage stage;
    private Scene scene;
    private Parent root;
    private double x, y= 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        em = Persistence.createEntityManagerFactory("my-persistence-unit").createEntityManager();
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
    public void handleLoginButtonAction(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        Person person = login(username, password);
        if (person != null){
            showAlert(Alert.AlertType.INFORMATION, "Login successful!", "Welcome " + person.getFullName());
            UserSession.getInstance().setLoggedInPerson(person); // Set logged in user
            // Method to navigate to main application view
        } else {
            showAlert(Alert.AlertType.ERROR, "Login failed!", "Invalid username or password.");
        }
    }

    @FXML
    public Person login(String username, String password){
        try {
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

    private void navigateToMainAppView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikisurance/fxml/Main.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to navigate to main application view.");
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void closeProgram(javafx.scene.input.MouseEvent e) {
        stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
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
