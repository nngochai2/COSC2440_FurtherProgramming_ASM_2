package org.nikisurance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.nikisurance.entity.Beneficiary;
import org.nikisurance.entity.Dependent;
import org.nikisurance.service.impl.BeneficiaryServiceImpl;
import org.nikisurance.service.interfaces.BeneficiaryService;

import java.util.Optional;

public class BeneficiaryDetailsController {
    @FXML
    private TextField idField, nameField, usernameField, passwordField, emailField, phoneNumberField, addressField, customerTypeField, policyHolderField, cardNumberField;
    @FXML private HBox policyHolderContainer;
    @FXML private Button editButton, saveButton, deleteButton, cancelButton;
    private final BeneficiaryService beneficiaryService;

    private SystemAdminController systemAdminController;

    public void setSystemAdminController(SystemAdminController systemAdminController) {
        this.systemAdminController = systemAdminController;
    }

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
        customerTypeField.setText(beneficiary.getBeneficiaryType());
        cardNumberField.setText(String.valueOf(beneficiary.getInsuranceCard().getCardID()));

        setEditable(false);

        idField.setEditable(false);
        idField.setManaged(false);

        policyHolderContainer.setVisible(beneficiary instanceof Dependent);
        policyHolderContainer.setManaged(beneficiary instanceof Dependent);
        if (beneficiary instanceof Dependent) {
            policyHolderField.setText(String.valueOf(((Dependent) beneficiary).getPolicyHolder().getId()));
            policyHolderField.setEditable(false);
            policyHolderField.setManaged(false);
        }

        saveButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    private void setEditable(boolean value) {
        nameField.setEditable(value);
        usernameField.setEditable(value);
        passwordField.setEditable(value);
        emailField.setEditable(value);
        phoneNumberField.setEditable(value);
        addressField.setEditable(value);
        cardNumberField.setEditable(value);
    }

    @FXML
    private void handleEditAction() {
        boolean isEditable = !nameField.isEditable();
        setEditable(isEditable);
//        editButton.setText(isEditable ? "Save" : "Edit");
        editButton.setDisable(true);
        saveButton.setDisable(!isEditable);
        cancelButton.setDisable(!isEditable);
    }

    @FXML
    private void handleSaveAction() {
        try {
            this.saveBeneficiaryDetails();
            setEditable(false);
            editButton.setText("Edit");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error", "Failed to save beneficiary details: " + e.getMessage());
        }
    }

    private void updateBeneficiaryFields(Beneficiary beneficiary) {
        beneficiary.setFullName(nameField.getText());
        beneficiary.setUsername(usernameField.getText());
        beneficiary.setPassword(passwordField.getText());
        beneficiary.setEmail(emailField.getText());
        beneficiary.setPhoneNumber(Long.parseLong(phoneNumberField.getText()));
        beneficiary.setAddress(addressField.getText());
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
            showAlert(Alert.AlertType.INFORMATION, "Deleted", "Beneficiary has been deleted.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to delete beneficiary: " + e.getMessage());
        }
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this beneficiary?");
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

    private void saveBeneficiaryDetails() {
        try {
            Beneficiary beneficiary = beneficiaryService.getBeneficiary(Long.parseLong(idField.getText()));
            updateBeneficiaryFields(beneficiary);
            beneficiaryService.updateBeneficiary(beneficiary);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Beneficiary details updated successfully.");

            if (systemAdminController != null) {
                // Refresh the table in Admin.fxml
                systemAdminController.refreshBeneficiaryTable();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to save beneficiary details: " + e.getMessage());
        }
    }
}
