package org.nikisurance.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.nikisurance.entity.Customer;
import org.nikisurance.service.interfaces.ClaimService;
import org.nikisurance.service.interfaces.CustomerService;
import org.nikisurance.service.interfaces.DependentService;
import org.nikisurance.service.interfaces.PolicyHolderService;
import org.nikisurance.service.interfaces.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class SystemAdminController implements Initializable {

    @Autowired
    private ClaimService claimService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DependentService dependentService;

    @Autowired
    private PolicyHolderService policyHolderService;

    @Autowired
    private ProviderService providerService;

    @FXML
    private TextField entityIdField;

    @FXML
    private Button retrieveInfoButton;

    @FXML
    private Button updateInfoButton;

    @FXML
    private Button deleteEntityButton;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnClaims;

    @FXML
    private JFXButton btnUsers;

    @FXML
    private JFXButton btnProviders;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private Pane pnDashboard;

    @FXML
    private Pane pnClaims;

    @FXML
    private Pane pnUsers;

    @FXML
    private Pane pnProviders;

    @FXML
    private Pane pnSettings;

    @FXML
    private AnchorPane sideBar;

    private double x = 0, y = 0;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Any initialization code
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
    private void retrieveInfo() {
        String id = entityIdField.getText();
        Customer customer = customerService.getCustomer(Long.parseLong(id));
        if (customer != null) {
            showAlert(AlertType.INFORMATION, "Customer Retrieved", "Customer ID: " + customer.getId() + ", Name: " + customer.getFullName());
        } else {
            showAlert(AlertType.ERROR, "Customer Not Found", "No customer found with ID: " + id);
        }
    }

    @FXML
    private void updateInfo() {
        // Logic to update information of entities (claims, customers, etc.)
    }

    @FXML
    private void deleteEntity() {
        String id = entityIdField.getText();
        customerService.deleteCustomer(Long.parseLong(id));
        showAlert(AlertType.INFORMATION, "Entity Deleted", "Entity with ID: " + id + " has been deleted.");
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnDashboard) {
            pnDashboard.toFront();
            closeButton.toFront();
        }
        if (actionEvent.getSource() == btnClaims) {
            pnClaims.toFront();
            closeButton.toFront();
        }
        if (actionEvent.getSource() == btnUsers) {
            pnUsers.toFront();
            closeButton.toFront();
        }
        if(actionEvent.getSource()==btnProviders)
        {
            pnProviders.toFront();
            closeButton.toFront();
        }
        if(actionEvent.getSource()==btnSettings)
        {
            pnSettings.toFront();
            closeButton.toFront();
        }
    }


    @FXML
    private void closeProgram(javafx.scene.input.MouseEvent e) {
        stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}