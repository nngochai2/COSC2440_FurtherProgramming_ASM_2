package org.nikisurance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.Customer;
import org.nikisurance.service.interfaces.ClaimService;
import org.nikisurance.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class PolicyOwnerController implements Initializable {

    @Autowired
    private ClaimService claimService;

    @Autowired
    private CustomerService customerService;

    @FXML
    private TableView<Claim> claimTable;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, String> claimDateColumn;

    @FXML
    private TableColumn<Claim, String> examDateColumn;

    @FXML
    private TableColumn<Claim, Integer> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableColumn<Claim, String> bankingInfoColumn;

    @FXML
    private TextField claimDescriptionField;
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerEmailField;

    private ObservableList<Claim> claimsData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        claimIdColumn.setCellValueFactory(new PropertyValueFactory<>("claimId"));
        claimDateColumn.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDateColumn.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimAmountColumn.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        claimStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        bankingInfoColumn.setCellValueFactory(new PropertyValueFactory<>("receiverBankingInfo"));

        populateTable();
    }

    public void populateTable() {
        List<Claim> claimList = claimService.getAllClaims();
        claimsData = FXCollections.observableArrayList(claimList);
        claimTable.setItems(claimsData);
    }

//    @FXML
//    private void fileClaim() {
//        String description = claimDescriptionField.getText();
//        Claim claim = new Claim();
//
//        Claim savedClaim = claimService.addClaim(claim);
//        showAlert(AlertType.INFORMATION, "Claim Filed", "Claim ID: " + savedClaim.getClaimId());
//    }

    @FXML
    private void updateClaim() {
        String id = claimDescriptionField.getText();
        String description = claimDescriptionField.getText();
        Claim claim = claimService.getClaim(id);
        if (claim != null) {
            claimService.addClaim(claim);
            showAlert(AlertType.INFORMATION, "Claim Updated", "Claim ID: " + id + " updated.");
        } else {
            showAlert(AlertType.ERROR, "Claim Not Found", "No claim found with ID: " + id);
        }
    }

    @FXML
    private void deleteClaim() {
        String id = claimDescriptionField.getText();
        Claim claim = claimService.getClaim(id);
        if (claim != null) {
            claimService.deleteClaim(claim);
            showAlert(AlertType.INFORMATION, "Claim Deleted", "Claim ID: " + id + " deleted.");
        } else {
            showAlert(AlertType.ERROR, "Claim Not Found", "No claim found with ID: " + id);
        }
    }

//    @FXML
//    private void addCustomer() {
//        String name = customerNameField.getText();
//        String email = customerEmailField.getText();
//        Customer customer = new Customer();
//        customer.setFullName(name);
//
//        Customer savedCustomer = customerService.addCustomer(customer);
//        showAlert(AlertType.INFORMATION, "Customer Added", "Customer ID: " + savedCustomer.getId());
//    }

    @FXML
    private void removeCustomer() {
        Long id = Long.parseLong(customerIdField.getText());
        customerService.deleteCustomer(id);
        showAlert(AlertType.INFORMATION, "Customer Removed", "Customer with ID: " + id + " removed.");
    }

    @FXML
    private void getCustomer() {
        Long id = Long.parseLong(customerIdField.getText());
        Customer customer = customerService.getCustomer(id);

        if (customer != null) {
            customerNameField.setText(customer.getFullName());
            showAlert(AlertType.INFORMATION, "Customer Retrieved", "Customer ID: " + id + ", Name: " + customer.getFullName());
        } else {
            showAlert(AlertType.ERROR, "Customer Not Found", "No customer found with ID: " + id);
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
