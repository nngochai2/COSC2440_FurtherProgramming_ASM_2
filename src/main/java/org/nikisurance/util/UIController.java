package org.nikisurance.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Objects;
import java.util.ResourceBundle;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UIController implements Initializable {
    @FXML
    private AnchorPane welcomeMenu;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label loginMessageLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String inputRole;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] roles = {"Policy Holder", "Dependent", "Insurance Surveyors", "Insurance Managers", "System Admin"};
        comboBox.getItems().addAll(roles);
    }

    public String getInputRole() {
        comboBox.setOnAction(actionEvent -> {
            inputRole = comboBox.getValue();
        });
        return inputRole;
    }

    public void switchToClaimView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/furtherprogramming_asm_2/fxml/ClaimView.fxml")));
        stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Method to close the application
    public void logout(ActionEvent event) {
        // Asking for user confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out!");
        alert.setContentText("Do you want to close the application?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) welcomeMenu.getScene().getWindow(); // Current stage
            System.out.println("You have successfully logged out!");
            stage.close();
        }
    }

//    public void validateLogin() {
//        DbFunctions dbFunctions = new DbFunctions();
//        Connection connectDB = dbFunctions.getConnection("nikisurance", "postgres", "123456");
//        String verifyLogin = "SELECT COUNT(1) FROM admin WHERE name = '" + nameTextField.getText() + "' AND password = '" + passwordTextField.getText() + "'";
//
//        try {
//            Statement statement = connectDB.createStatement();
//            ResultSet queryResult = statement.executeQuery(verifyLogin);
//
//            while (queryResult.next()) {
//                if (queryResult.getInt(1) == 1) {
//                    loginMessageLabel.setText("Welcome " + nameTextField.getText());
//                } else {
//                    loginMessageLabel.setText("Invalid Login. Please try again.");
//                }
//            }
//        } catch (Exception e) {
//            logger.log(Level.SEVERE, e.getMessage());
//        }
//    }

//    public void login(ActionEvent event) throws IOException {
//        String role = this.getInputRole();
//
//        if (!nameTextField.getText().isBlank() && !passwordTextField.getText().isBlank() && role != null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            loginMessageLabel.setText("Please enter all the fields correctly.");
//        }
//        // Authenticate login information
//        switch (role) {
//            case "Policy Holder" -> {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/furtherprogramming_asm_2/fxml/PolicyHolderView.fxml"));
//                root = loader.load();
//                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }
//            case "Dependent" -> {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/furtherprogramming_asm_2/fxml/DependentView.fxml"));
//                root = loader.load();
//                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }
//            case "System Admin" -> validateLogin();
//            case null -> {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + role);
//        }
//    }
}