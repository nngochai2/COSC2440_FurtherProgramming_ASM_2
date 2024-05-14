package org.nikisurance.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Any initialization code
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
}