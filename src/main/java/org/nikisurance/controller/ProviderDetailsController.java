package org.nikisurance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nikisurance.entity.InsuranceManager;
import org.nikisurance.entity.InsuranceSurveyor;
import org.nikisurance.entity.Provider;
import org.nikisurance.service.impl.ProviderServiceImpl;
import org.nikisurance.service.interfaces.ProviderService;

import java.util.Optional;

public class ProviderDetailsController {
    @FXML private TextField idField;

    @FXML private TextField nameField;

    @FXML private TextField usernameField;

    @FXML private TextField passwordField;

    @FXML private TextField managerIdField;

    @FXML private Button editButton, saveButton, deleteButton, cancelButton;

    private final ProviderService providerService;

    private SystemAdminController systemAdminController;

    public void setSystemAdminController(SystemAdminController systemAdminController) {
        this.systemAdminController = systemAdminController;
    }

    public ProviderDetailsController() {
        this.providerService = new ProviderServiceImpl();
    }

    public void setProvider(Provider provider) {
        idField.setText(Long.toString(provider.getId()));
        idField.setEditable(false);
        idField.setManaged(false);

        nameField.setText(provider.getFullName());
        usernameField.setText(provider.getUsername());
        passwordField.setText(provider.getPassword());

        setEditable(false);

        if (provider instanceof InsuranceSurveyor) {
            managerIdField.setText(Long.toString(((InsuranceSurveyor) provider).getInsuranceManager().getId()));
            managerIdField.setEditable(false);
            managerIdField.setManaged(false);
        }

        saveButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void setEditable(boolean editable) {
        nameField.setEditable(editable);
        usernameField.setEditable(editable);
        passwordField.setEditable(editable);
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
        if (showSaveConfirmationDialog()) {
            try {
                this.saveProviderDetails();
                setEditable(false);
//            editButton.setText("Edit");
                ((Stage) idField.getScene().getWindow()).close();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR,"Error", "Failed to save beneficiary details: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleCancelAction() {
        setProvider(providerService.getProvider(Long.parseLong(idField.getText()))); // Re-fetch and reset the details
        toggleEdit(); // Reset editable state to non-editable
    }

    @FXML
    private void handleDeleteAction() {
        if (showDeleteConfirmationDialog()) {
            deleteProvider();
            ((Stage) idField.getScene().getWindow()).close();
        }
    }

    @FXML
    private void toggleEdit() {
        boolean isEditable = !nameField.isEditable();
        setEditable(isEditable);
        editButton.setText(isEditable ? "Save" : "Edit");
    }

    private void saveProviderDetails() {
        try {
            Provider provider = providerService.getProvider(Long.parseLong(idField.getText()));
            provider.setFullName(nameField.getText());
            provider.setUsername(usernameField.getText());
            provider.setPassword(passwordField.getText());

            if (provider instanceof InsuranceSurveyor) {
                ((InsuranceSurveyor) provider).setInsuranceManager((InsuranceManager) providerService.getProvider(Long.parseLong(idField.getText())));
            }

            providerService.updateProvider(provider);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Provider updated successfully");

            if (systemAdminController != null) {
                // Refresh the table in Admin view
                systemAdminController.refreshBeneficiaryTable();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to save provider details: " + e.getMessage());
        }
    }

    private void deleteProvider() {
        try {
            providerService.deleteProvider(Long.parseLong(idField.getText()));
            showAlert(Alert.AlertType.INFORMATION, "Deleted", "Provider has been deleted successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to delete provider: " + e.getMessage());
        }
    }

    private boolean showDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this provider");
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent() && action.get() == ButtonType.OK;
    }

    private boolean showSaveConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Edit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to edit this provider?");
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
