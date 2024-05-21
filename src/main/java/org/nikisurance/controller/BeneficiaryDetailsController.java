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
import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.service.impl.BeneficiaryServiceImpl;
import org.nikisurance.service.impl.DependentServiceImpl;
import org.nikisurance.service.impl.PolicyHolderServiceImpl;
import org.nikisurance.service.interfaces.BeneficiaryService;
import org.nikisurance.service.interfaces.DependentService;
import org.nikisurance.service.interfaces.PolicyHolderService;

import java.util.Optional;

public class BeneficiaryDetailsController {
    @FXML
    private TextField idField, nameField, usernameField, passwordField, emailField, phoneNumberField, addressField, customerTypeField, policyHolderField, cardNumberField;
    @FXML private HBox policyHolderContainer;
    @FXML private Button editButton, saveButton, deleteButton, cancelButton;

    private final BeneficiaryService beneficiaryService;
    private final DependentService dependentService;
    private final PolicyHolderService policyHolderService;

    private SystemAdminController systemAdminController;

    public void setSystemAdminController(SystemAdminController systemAdminController) {
        this.systemAdminController = systemAdminController;
    }

    public BeneficiaryDetailsController() {
        this.beneficiaryService = new BeneficiaryServiceImpl();
        this.dependentService = new DependentServiceImpl();
        this.policyHolderService = new PolicyHolderServiceImpl();
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

        deleteButton.setDisable(true);
        editButton.setDisable(true);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
    }

    @FXML
    private void handleSaveAction() {
        if (showSaveConfirmationDialog()) {
            try {
                this.saveBeneficiaryDetails();
                setEditable(false);
//            editButton.setText("Edit");
                ((Stage) idField.getScene().getWindow()).close();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR,"Error", "Failed to save beneficiary details: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleDeleteAction() {
        if (showDeleteConfirmationDialog()) {
            deleteCustomer();
            ((Stage) idField.getScene().getWindow()).close();
        }
    }

    @FXML
    private void handleCancelAction() {
        setBeneficiary(beneficiaryService.getBeneficiary(Long.parseLong(idField.getText()))); // Re-fetch and reset the details
        setEditable(false); // Reset editable state to non-editable
        editButton.setDisable(false);
        cancelButton.setDisable(true);
        saveButton.setDisable(true);
        deleteButton.setDisable(false);
    }

    private void saveBeneficiaryDetails() {
        try {
            Beneficiary beneficiary = beneficiaryService.getBeneficiary(Long.parseLong(idField.getText()));
            updateBeneficiaryFields(beneficiary);
            beneficiaryService.updateBeneficiary(beneficiary);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Beneficiary details updated successfully.");

            if (systemAdminController != null) {
                // Refresh the table in Admin view
                systemAdminController.refreshBeneficiaryTable();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to save beneficiary details: " + e.getMessage());
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

    private void deleteCustomer() {
        try {
            Beneficiary beneficiary = beneficiaryService.getBeneficiary(Long.parseLong(idField.getText()));
            if (beneficiary instanceof Dependent) {
                dependentService.deleteDependent(beneficiary.getId());
            } else if (beneficiary instanceof PolicyHolder) {
                policyHolderService.deletePolicyHolder(beneficiary.getId());
            }
            showAlert(Alert.AlertType.INFORMATION, "Deleted", "Beneficiary has been deleted successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to delete beneficiary: " + e.getMessage());
        }
    }

    private boolean showDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this beneficiary?");
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent() && action.get() == ButtonType.OK;
    }

    private boolean showSaveConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Edit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to edit this beneficiary?");
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent() && action.get() == ButtonType.OK;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
