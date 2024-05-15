package org.nikisurance.controller;

import jakarta.persistence.Persistence;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.nikisurance.entity.Dependent;
import org.nikisurance.entity.Person;
import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.service.interfaces.DependentService;
import org.nikisurance.service.interfaces.PolicyHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class PolicyHolderController extends ClaimController implements Initializable {

    @FXML
    protected TableView<Dependent> dependentTable;

    @FXML
    private TableColumn<Dependent, Long> dependentIdColumn;

    @FXML
    private TableColumn<Dependent, String> dependentNameColumn;

    private ObservableList<Dependent> dependentsData;

    @FXML
    private TextField dependentFullNameTextField;

    @FXML
    private TextField dependentUsernameTextField;

    @FXML
    private TextField dependentPasswordTextField;

    @FXML
    private TextField dependentEmailTextField;

    @FXML
    private TextField dependentPhoneTextField;

    @FXML
    private TextField dependentAddressTextField;

    @Autowired
    private PolicyHolderService policyHolderService;

    @Autowired
    private DependentService dependentService;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        super.initialize(url, resources);
        this.initializeDependentsTable();
        this.populateDependentTable();
    }

    private void initializeDependentsTable() {
        dependentIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        dependentNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
    }

    private void populateDependentTable() {
        PolicyHolder policyHolder = (PolicyHolder) UserSession.getInstance().getLoggedInPerson();
        if (policyHolder != null) {
            ObservableList<Dependent> dependents = FXCollections.observableArrayList();
            dependentsData = FXCollections.observableArrayList(dependents);
            dependentTable.setItems(dependentsData);
        }
    }

    @FXML
    private void handleUpdateDependentInfo() {
        Dependent selectedDependent = dependentTable.getSelectionModel().getSelectedItem();
        if (selectedDependent != null) {
            // Automatically enter the current information to the fields
            dependentFullNameTextField.setText(selectedDependent.getFullName());
            dependentUsernameTextField.setText(selectedDependent.getUsername());
            dependentPasswordTextField.setText(selectedDependent.getPassword());
            dependentEmailTextField.setText(selectedDependent.getEmail());
            dependentPhoneTextField.setText(String.valueOf(selectedDependent.getPhoneNumber()));
            dependentAddressTextField.setText(selectedDependent.getAddress());

            // Update with new data
            try {
                Long newPhoneNumber = Long.parseLong(dependentPhoneTextField.getText());
                selectedDependent.setPhoneNumber(newPhoneNumber);
            } catch (NumberFormatException e) {
                System.err.println("Invalid phone number");
                showAlert(Alert.AlertType.WARNING, "Invalid Phone Number", "Phone number is not a valid.");
                return;
            }
            selectedDependent.setFullName(dependentFullNameTextField.getText());
            selectedDependent.setUsername(dependentUsernameTextField.getText());
            selectedDependent.setPassword(dependentPasswordTextField.getText());
            selectedDependent.setEmail(dependentEmailTextField.getText());
            selectedDependent.setAddress(dependentAddressTextField.getText());

            dependentService.updateDependent(selectedDependent);
            claimTable.refresh();
            System.out.println("Dependent updated successfully.");
            showAlert(Alert.AlertType.INFORMATION, "Dependent updated successfully", "Dependent updated successfully.");
        }
    }


}
