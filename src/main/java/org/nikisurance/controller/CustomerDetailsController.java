package org.nikisurance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.nikisurance.entity.*;
import org.nikisurance.service.impl.*;
import org.nikisurance.service.interfaces.*;

import java.util.Optional;

public class CustomerDetailsController {
    @FXML private TextField idField, nameField, usernameField, passwordField, emailField, phoneNumberField, addressField, customerTypeField, policyHolderField, basePremiumField, dependentRateField, cardNumberField;
    @FXML private HBox emailContainer, phoneContainer, addressContainer, cardNumberContainer, basePremiumContainer, dependentRateContainer, policyHolderContainer, customerTypeContainer;
    @FXML private Button editButton, saveButton, deleteButton, cancelButton;

    private final CustomerService customerService;

    public CustomerDetailsController() {
        this.customerService = new CustomerServiceImpl();
    }

    public void setCustomer(Customer customer) {
        idField.setText(Long.toString(customer.getId()));
        nameField.setText(customer.getFullName());
        usernameField.setText(customer.getUsername());
        passwordField.setText(customer.getPassword());

        // Hide all fields initially
        hideAllFields();

        // Determine the specific type of customer and set the text accordingly
        if (customer instanceof PolicyHolder) {
            customerTypeField.setText("Policy Holder");  // Set the text as per your requirement
            showPolicyHolderFields((PolicyHolder) customer);
        } else if (customer instanceof Dependent) {
            customerTypeField.setText("Dependent");
            showDependentFields((Dependent) customer);
        } else if (customer instanceof PolicyOwner) {
            customerTypeField.setText("Policy Owner");
            showPolicyOwnerFields((PolicyOwner) customer);
        } else {
            customerTypeField.setText("Customer");  // Default or other types, if there are any
        }
    }

    private void hideAllFields() {
        // Hide all optional fields
        setFieldVisibility(false, emailContainer, phoneContainer, addressContainer, basePremiumContainer, dependentRateContainer, cardNumberContainer, policyHolderContainer);
    }

    private void showPolicyHolderFields(PolicyHolder holder) {
        emailField.setText(holder.getEmail());
        phoneNumberField.setText(String.valueOf(holder.getPhoneNumber()));
        addressField.setText(holder.getAddress());

        setFieldVisibility(true, emailContainer, phoneContainer, addressContainer, customerTypeContainer);
    }

    private void showDependentFields(Dependent dependent) {
        policyHolderField.setText(String.valueOf(dependent.getPolicyHolder().getId()));
        cardNumberField.setText(String.valueOf(dependent.getInsuranceCard().getCardID()));

        setFieldVisibility(true, policyHolderContainer, cardNumberContainer, customerTypeContainer);
    }

    private void showPolicyOwnerFields(PolicyOwner owner) {
        basePremiumField.setText(String.format("%.2f", owner.getBasePremium()));
        dependentRateField.setText(String.format("%.2f", owner.getDependentRate()));

        setFieldVisibility(true, basePremiumContainer, dependentRateContainer);
    }

    private void setFieldVisibility(boolean visible, HBox... containers) {
        for (HBox container : containers) {
            if (container != null) {
                container.setManaged(visible);
                container.setVisible(visible);
            } else {
                System.out.println("One of the containers is null");
            }
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateCustomerFields(Customer customer) {
        customer.setFullName(nameField.getText());
        customer.setUsername(usernameField.getText());
        customer.setPassword(passwordField.getText());

        if (customer instanceof Beneficiary) {
            ((Beneficiary) customer).setEmail(emailField.getText());
            ((Beneficiary) customer).setPhoneNumber(Long.parseLong(phoneNumberField.getText()));
            ((Beneficiary) customer).setAddress(addressField.getText());

        } else if (customer instanceof PolicyOwner) {
            ((PolicyOwner) customer).setBasePremium(Long.parseLong(basePremiumField.getText()));
            ((PolicyOwner) customer).setDependentRate(Double.parseDouble(dependentRateField.getText()));
        }
    }

    @FXML
    private void toggleEdit() {
        boolean isEditable = !nameField.isEditable(); // Toggle the state
        nameField.setEditable(isEditable);
        usernameField.setEditable(isEditable);
        emailField.setEditable(isEditable && emailField.isVisible());
        phoneNumberField.setEditable(isEditable && phoneNumberField.isVisible());
        addressField.setEditable(isEditable && addressField.isVisible());
        editButton.setText(isEditable ? "Save" : "Edit");

        if (!isEditable) {
            // Here you might call a method to save the edited data
            saveCustomerDetails();
        }
    }

    private void closeWindow() {
        // Closes the current window assuming this method is triggered from a stage
        ((Stage) deleteButton.getScene().getWindow()).close();
    }

    @FXML
    private void handleEditAction() {
        setEditable(true);
        saveButton.setDisable(false);  // Enable the Save button
        cancelButton.setDisable(false);  // Enable the Cancel button
        editButton.setDisable(true);  // Disable the Edit button while editing
    }

    private void setEditable(boolean value) {
        nameField.setEditable(value);
        usernameField.setEditable(value);
        passwordField.setEditable(value);
        emailField.setEditable(value && emailField.isVisible());
        phoneNumberField.setEditable(value && phoneNumberField.isVisible());
        addressField.setEditable(value && addressField.isVisible());
        basePremiumField.setEditable(value && basePremiumField.isVisible());
        dependentRateField.setEditable(value && dependentRateField.isVisible());
        cardNumberField.setEditable(value && cardNumberField.isVisible());
        policyHolderField.setEditable(value && policyHolderField.isVisible());
    }

    @FXML
    private void handleSaveAction() {
        saveCustomerDetails();
        setEditable(false);
        saveButton.setDisable(true);  // Disable the Save button after saving
        cancelButton.setDisable(true);  // Disable the Cancel button after saving
        editButton.setDisable(false);  // Re-enable the Edit button after saving
    }

    private void saveCustomerDetails() {
        try {
            Customer customer = customerService.getCustomer(Long.parseLong(idField.getText()));
            updateCustomerFields(customer);
            customerService.updateCustomer(customer);
            showAlert("Success", "Customer details updated successfully.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to save customer details: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDeleteAction() {
        if (showConfirmationDialog()) {
            deleteCustomer();
            ((Stage) idField.getScene().getWindow()).close(); // Close the window after deletion
        }
    }

    private void deleteCustomer() {
        try {
            customerService.deleteCustomer(Long.parseLong(idField.getText()));
            showAlert("Deleted", "Customer has been deleted.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Unable to delete customer: " + e.getMessage(), Alert.AlertType.ERROR);
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
        setCustomer(customerService.getCustomer(Long.parseLong(idField.getText()))); // Re-fetch and reset the details
        setEditable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        editButton.setDisable(false);
    }

}
