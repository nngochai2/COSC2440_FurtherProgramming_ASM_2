package org.nikisurance.controller;

import jakarta.persistence.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.nikisurance.entity.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @PersistenceContext
    private EntityManager em;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        em = Persistence.createEntityManagerFactory("my-persistence-unit").createEntityManager();
    }

    @FXML
    public void handleLoginButtonAction(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        Person person = login(username, password);
        if (person != null){
            showAlert(Alert.AlertType.INFORMATION, "Login successful!", "Welcome " + person.getFullName());
            // Method to navigate to main application view
        } else {
            showAlert(Alert.AlertType.ERROR, "Login failed!", "Invalid username or password.");
        }
    }

    @FXML
    public Person login(String username, String password){
        try {
            String queryString = "SELECT u FROM Person u WHERE u.username = :username";
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
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
