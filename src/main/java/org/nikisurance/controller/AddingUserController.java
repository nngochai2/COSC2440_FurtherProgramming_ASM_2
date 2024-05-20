package org.nikisurance.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.usertype.UserType;
import org.nikisurance.entity.*;
import org.nikisurance.service.impl.*;
import org.nikisurance.service.interfaces.*;
import org.w3c.dom.Text;

public class AddingUserController {
    @FXML private TextField fullNameField;

    @FXML private TextField usernameField;

    @FXML private TextField passwordField;

    @FXML private VBox additionalFieldsVBox; // Container for additional fields

    @FXML private TextField emailField;

    @FXML private TextField phoneField;

    @FXML private TextField addressField;

    @FXML private TextField policyHolderIdField;

    @FXML private TextField basePremiumField;

    @FXML private TextField dependentRateField;

    @FXML private TextField insuranceManagerIdField;

    @FXML private Button saveButton;

    @FXML private Button cancelButton;

    @FXML private ComboBox<String> personTypeComboBox;

    private final PolicyHolderService policyHolderService;
    private final DependentService dependentService;
    private final PolicyOwnerService policyOwnerService;
    private final ProviderService providerService;

    public AddingUserController() {
        this.providerService = new ProviderServiceImpl();
        this.policyOwnerService = new PolicyOwnerServiceImpl();
        this.dependentService = new DependentServiceImpl();
        this.policyHolderService = new PolicyHolderServiceImpl();
    }

    @FXML
    private void initialize() {
        // Populate the ComboBox with types of entities users can create
        personTypeComboBox.setItems(FXCollections.observableArrayList("Policy Holder", "Dependent", "Policy Owner", "Insurance Surveyor", "Insurance Manager"));
        personTypeComboBox.valueProperty().addListener((observable, oldVal, newVal) -> updateFormFieldsBasedOnType(newVal));
    }

    private void updateFormFieldsBasedOnType(String type) {
        additionalFieldsVBox.getChildren().forEach(node -> {
            node.setVisible(false);
            node.setManaged(false);
        });

        switch (type) {
            case "Policy Holder" -> setVisibleAndManaged(emailField, phoneField, addressField);
            case "Dependent" -> setVisibleAndManaged(emailField, phoneField, addressField, policyHolderIdField);
            case "Policy Owner" -> setVisibleAndManaged(basePremiumField, dependentRateField);
            case "Insurance Surveyor" -> setVisibleAndManaged(insuranceManagerIdField);
        }
    }

    private void setVisibleAndManaged(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(true);
            node.setManaged(true);
        }
    }

    @FXML
    private void savePersonBasedOnType() {
        String type = personTypeComboBox.getValue();
        try {
            switch (type) {
                case "Policy Holder" -> {
                    PolicyHolder policyHolder = new PolicyHolder();
                    policyHolder.setFullName(fullNameField.getText());
                    policyHolder.setUsername(usernameField.getText());
                    policyHolder.setPassword(passwordField.getText());
                    policyHolder.setEmail(emailField.getText());
                    policyHolder.setPhoneNumber(Long.parseLong(phoneField.getText()));
                    policyHolder.setAddress(addressField.getText());
                    policyHolderService.addPolicyHolder(policyHolder);
                }
                case "Dependent" -> {
                    Dependent dependent = new Dependent();
                    dependent.setFullName(fullNameField.getText());
                    dependent.setUsername(usernameField.getText());
                    dependent.setPassword(passwordField.getText());
                    dependent.setEmail(emailField.getText());
                    dependent.setPhoneNumber(Long.parseLong(phoneField.getText()));
                    dependent.setPolicyHolder(policyHolderService.getPolicyHolder(Long.parseLong(policyHolderIdField.getText())));
                    dependentService.addDependent(dependent);
                }
                case "Policy Owner" -> {
                    PolicyOwner policyOwner = new PolicyOwner();
                    policyOwner.setFullName(fullNameField.getText());
                    policyOwner.setUsername(usernameField.getText());
                    policyOwner.setPassword(passwordField.getText());
                    policyOwner.setBasePremium(Double.parseDouble(basePremiumField.getText()));
                    policyOwner.setDependentRate(Double.parseDouble(dependentRateField.getText()));
                    policyOwnerService.addPolicyOwner(policyOwner);
                }
                case "Insurance Surveyor" -> {
                    InsuranceSurveyor insuranceSurveyor = new InsuranceSurveyor();
                    insuranceSurveyor.setFullName(fullNameField.getText());
                    insuranceSurveyor.setUsername(usernameField.getText());
                    insuranceSurveyor.setPassword(passwordField.getText());
                    insuranceSurveyor.setInsuranceManager((InsuranceManager) providerService.getProvider(Long.parseLong(insuranceManagerIdField.getText())));
                    providerService.addProvider(insuranceSurveyor);
                }
                case "Insurance Manager" -> {
                    InsuranceManager insuranceManager = new InsuranceManager();
                    insuranceManager.setFullName(fullNameField.getText());
                    insuranceManager.setUsername(usernameField.getText());
                    insuranceManager.setPassword(passwordField.getText());
                }
            }
            showAlert(Alert.AlertType.INFORMATION, "Success", "New person has been added successfully.");
            closeWindow();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
