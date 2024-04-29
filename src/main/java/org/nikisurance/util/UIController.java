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
import java.util.Objects;
import java.util.ResourceBundle;
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
}