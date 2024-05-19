package org.nikisurance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.nikisurance.entity.Beneficiary;
import org.nikisurance.entity.Customer;
import org.nikisurance.entity.Dependent;
import org.nikisurance.service.impl.BeneficiaryServiceImpl;
import org.nikisurance.service.impl.CustomerServiceImpl;
import org.nikisurance.service.interfaces.BeneficiaryService;
import org.nikisurance.service.interfaces.CustomerService;

import java.util.Optional;

public class BeneficiaryDetailsController {
    @FXML
    private TextField idField, nameField, usernameField, passwordField, emailField, phoneNumberField, addressField, customerTypeField, policyHolderField, cardNumberField;
    @FXML private HBox policyHolderContainer;
    @FXML private Button editButton, saveButton, deleteButton, cancelButton;
    private final BeneficiaryService beneficiaryService;

    public BeneficiaryDetailsController() {
        this.beneficiaryService = new BeneficiaryServiceImpl();
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        idField.setText(Long.toString(beneficiary.getId()));
        nameField.setText(beneficiary.getFullName());
        usernameField.setText(beneficiary.getUsername());
        passwordField.setText(beneficiary.getPassword());
        emailField.setText(beneficiary.getEmail());
        phoneNumberField.setText(String.valueOf(beneficiary.getPhoneNumber()));
        addressField.setText(beneficiary.getAddress());
        customerTypeField.setText(beneficiary.getCustomerType());
        cardNumberField.setText(String.valueOf(beneficiary.getInsuranceCard().getCardID()));

        setEditable(false);

        policyHolderContainer.setVisible(beneficiary instanceof Dependent);
        policyHolderContainer.setManaged(beneficiary instanceof Dependent);
        if (beneficiary instanceof Dependent) {
            policyHolderField.setText(String.valueOf(((Dependent) beneficiary).getPolicyHolder().getId()));
        }
    }

    private void setEditable(boolean value) {
        nameField.setEditable(value);
        usernameField.setEditable(value);
        passwordField.setEditable(value);
        emailField.setEditable(value);
        phoneNumberField.setEditable(value);
        addressField.setEditable(value);
        customerTypeField.setEditable(value);
        cardNumberField.setEditable(value);
        policyHolderField.setEditable(value);
    }

    @FXML
    private void handleEditAction() {
        boolean isEditable = !nameField.isEditable();
        setEditable(isEditable);
        editButton.setText(isEditable ? "Save" : "Edit");
        saveButton.setDisable(!isEditable);
        cancelButton.setDisable(!isEditable);
    }

    @FXML
    private void handleSaveAction() {
        try {
            Beneficiary beneficiary = beneficiaryService.getBeneficiary(Long.parseLong(idField.getText()));
            updateCustomerFields(beneficiary);
            beneficiaryService.updateBeneficiary(beneficiary);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer details updated successfully.");
            setEditable(false);
            editButton.setText("Edit");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error", "Failed to save customer details: " + e.getMessage());
        }
    }

    private void updateCustomerFields(Customer customer) {
        customer.setFullName(nameField.getText());
        customer.setUsername(usernameField.getText());
        customer.setPassword(passwordField.getText());
        if (customer instanceof Beneficiary) {
            Beneficiary beneficiary = (Beneficiary) customer;
            beneficiary.setEmail(emailField.getText());
            beneficiary.setPhoneNumber(Long.parseLong(phoneNumberField.getText()));
            beneficiary.setAddress(addressField.getText());
        }
    }

    @FXML
    private void handleDeleteAction() {
        if (showConfirmationDialog()) {
            deleteCustomer();
            ((Stage) idField.getScene().getWindow()).close();
        }
    }

    private void deleteCustomer() {
        try {
            beneficiaryService.deleteBeneficiary(Long.parseLong(idField.getText()));
            showAlert(Alert.AlertType.INFORMATION, "Deleted", "Customer has been deleted.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to delete customer: " + e.getMessage());
        }
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this customer?");
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent() && action.get() == ButtonType.OK;
    }

    @FXML
    private void handleCancelAction() {
        setBeneficiary(beneficiaryService.getBeneficiary(Long.parseLong(idField.getText()))); // Re-fetch and reset the details
        toggleEdit(); // Reset editable state to non-editable
    }

    @FXML
    private void toggleEdit() {
        boolean isEditable = !nameField.isEditable();
        setEditable(isEditable);
        editButton.setText(isEditable ? "Save" : "Edit");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
