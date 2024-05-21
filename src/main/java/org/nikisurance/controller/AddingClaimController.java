package org.nikisurance.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.nikisurance.entity.*;
import org.nikisurance.service.interfaces.ClaimService;
import org.nikisurance.service.impl.ClaimServiceImpl;

public class AddingClaimController {

    @FXML
    private TextField claimAmountField;
    @FXML
    private DatePicker examDatePicker;
    @FXML
    private ComboBox<Beneficiary> claimantComboBox;
    @FXML
    private TextField bankingInfoField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private final ClaimService claimService;

    public AddingClaimController() {
        this.claimService = new ClaimServiceImpl();
    }

    @FXML
    private void initialize() {
        setupClaimantComboBox();
    }

    private void setupClaimantComboBox() {
        Person currentUser = UserSession.getInstance().getLoggedInPerson();
        if (currentUser instanceof PolicyHolder) {
            // Assuming PolicyHolderService provides access to dependents
            claimantComboBox.setItems(FXCollections.observableArrayList((PolicyHolder) currentUser));
        } else if (currentUser instanceof PolicyOwner) {
            // Assuming PolicyOwnerService provides access to policy holders and their dependents
            // For simplicity, the services to fetch policy holders and dependents are not shown here
        }
        claimantComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleSaveAction() {
        try {
            Claim newClaim = new Claim();
            newClaim.setClaimAmount(Double.parseDouble(claimAmountField.getText()));
            newClaim.setExamDate(java.sql.Date.valueOf(examDatePicker.getValue()));
//            newClaim.setReceiverBankingInfo(bankingInfoField.getText());
            newClaim.setBeneficiary(claimantComboBox.getSelectionModel().getSelectedItem());

            claimService.addClaim(newClaim);

            closeWindow();
            showAlert("Success", "Claim added successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to add the claim: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String header, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelAction() {
        closeWindow();
    }
}
