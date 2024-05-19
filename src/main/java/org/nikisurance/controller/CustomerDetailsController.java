package org.nikisurance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.nikisurance.entity.Customer;
import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.service.impl.CustomerServiceImpl;
import org.nikisurance.service.interfaces.CustomerService;

public class CustomerDetailsController {
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField addressField;
    @FXML private HBox emailContainer;
    @FXML private HBox phoneContainer;
    @FXML private HBox addressContainer;
    @FXML private Button editButton;

    private final CustomerService customerService;
    public CustomerDetailsController() {
        this.customerService = new CustomerServiceImpl();
    }

    public void setCustomer(Customer customer) {
        idField.setText(Long.toString(customer.getId()));
        nameField.setText(customer.getFullName());
        usernameField.setText(customer.getUsername());

        if (customer instanceof PolicyHolder) {
            PolicyHolder holder = (PolicyHolder) customer;
            emailField.setText(holder.getEmail());
            phoneNumberField.setText(String.valueOf(holder.getPhoneNumber()));
            addressField.setText(holder.getAddress());

            emailContainer.setManaged(true);
            emailContainer.setVisible(true);
            phoneContainer.setManaged(true);
            phoneContainer.setVisible(true);
            addressContainer.setManaged(true);
            addressContainer.setVisible(true);
        }
    }

    private void saveCustomer() {
        // Assuming you have a method in the service to update customers

        Customer customer = customerService.getCustomer(Long.parseLong(idField.getText()));

        if (customer instanceof PolicyHolder) {
            customer.setFullName(nameField.getText());
            customer.setUsername(usernameField.getText());
            customer.setPassword(passwordField.getText());

            ((PolicyHolder) customer).setEmail(emailField.getText());
            ((PolicyHolder) customer).setPhoneNumber(Long.parseLong(phoneNumberField.getText()));
            ((PolicyHolder) customer).setAddress(addressField.getText());
        }

        customerService.updateCustomer(customer);

        // Optionally, disable fields after saving
        nameField.setEditable(false);
        usernameField.setEditable(false);
        passwordField.setEditable(false);
        emailField.setEditable(false);
        phoneNumberField.setEditable(false);
        addressField.setEditable(false);
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

    private void saveCustomerDetails() {
        // Save logic, potentially updating the database or model
        System.out.println("Details saved!");
    }
}
