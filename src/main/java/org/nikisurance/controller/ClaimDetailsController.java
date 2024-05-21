package org.nikisurance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.entity.InsuranceSurveyor;
import org.nikisurance.service.impl.ClaimServiceImpl;
import org.nikisurance.service.impl.ProviderServiceImpl;
import org.nikisurance.service.interfaces.ClaimService;
import org.nikisurance.service.interfaces.ProviderService;

import java.util.Optional;

public class ClaimDetailsController {
    @FXML private TextField idField;

    @FXML private TextField examDateField;

    @FXML private TextField claimDateField;

    @FXML private TextField beneficiaryIDField;

    @FXML private TextField insuredPersonNameField;

    @FXML private TextField insuranceCardNumberField;

    @FXML private TextField claimAmountField;

    @FXML private ComboBox<ClaimStatus> claimStatusComboBox;

    @FXML private TextField bankingInformationField;

    @FXML private TextField documentPathField;

    @FXML private TextField documentNameField;

    @FXML private TextField surveyorID;

    @FXML private Button editButton, saveButton, deleteButton, cancelButton;

    private final ClaimService claimService;
    private final ProviderService providerService;

    public ClaimDetailsController() {
        claimService = new ClaimServiceImpl();
        providerService = new ProviderServiceImpl();
    }

    public void setClaim(Claim claim) {
        idField.setText(claim.getClaimId());
        claimDateField.setText(String.valueOf(claim.getClaimDate()));
        examDateField.setText(String.valueOf(claim.getExamDate()));
        beneficiaryIDField.setText(String.valueOf(claim.getBeneficiaryId()));
        insuredPersonNameField.setText(claim.getInsuredPerson());
        insuranceCardNumberField.setText(String.valueOf(insuranceCardNumberField));
        claimAmountField.setText(String.valueOf(claim.getClaimAmount()));
        documentPathField.setText(claim.getDocumentPath());
        documentNameField.setText(claim.getDocumentName());
        surveyorID.setText(String.valueOf(claim.getInsuranceSurveyor().getId()));
        bankingInformationField.setText(claim.getReceiverBankingInfo());

        this.setEditable(false);
    }

    private void setEditable(boolean editable) {
        idField.setEditable(editable);
        claimDateField.setEditable(editable);
        examDateField.setEditable(editable);
        beneficiaryIDField.setEditable(editable);
        insuredPersonNameField.setEditable(editable);
        insuranceCardNumberField.setEditable(editable);
        claimAmountField.setEditable(editable);
        documentPathField.setEditable(editable);
        documentNameField.setEditable(editable);
        surveyorID.setEditable(editable);
        bankingInformationField.setEditable(editable);
        claimStatusComboBox.setEditable(editable);
        bankingInformationField.setEditable(editable);
    }

    @FXML
    private void handleEditAction() {
        boolean editable = Boolean.parseBoolean(idField.getText());
        claimStatusComboBox.setEditable(true);
        documentPathField.setEditable(true);
        documentNameField.setEditable(true);
        surveyorID.setEditable(true);

        editButton.setDisable(true);
        saveButton.setDisable(false);
        deleteButton.setDisable(true);
        cancelButton.setDisable(false);
    }

    @FXML void handleCancelAction() {
        setClaim(claimService.getClaim(idField.getText()));
        setEditable(false);
        editButton.setDisable(false);
        saveButton.setDisable(true);
        deleteButton.setDisable(false);
        cancelButton.setDisable(true);
    }


    @FXML void handleSaveAction() {
        if (showSaveConfirmationDialog()) {
            try {
                this.saveClaimDetails();
                setEditable(false);
                ((Stage) idField.getScene().getWindow()).close();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error saving claim details", e.getMessage());
            }
        }
    }

    @FXML
    private void handleDeleteAction() {
        if(showDeleteConfirmationDialog()) {
            this.deleteClaim();
            ((Stage) idField.getScene().getWindow()).close();
        }
    }

    private boolean showSaveConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Edit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to edit this claim?");
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent() && action.get() == ButtonType.OK;
    }

    private void saveClaimDetails() {
        try {
            Claim claim = claimService.getClaim(idField.getText());
            claim.setStatus(claimStatusComboBox.getValue());
            claim.setDocumentPath(documentPathField.getText());
            claim.setDocumentName(documentNameField.getText());
            claim.setInsuranceSurveyor((InsuranceSurveyor) providerService.getProvider(Long.parseLong(surveyorID.getText())));
            claimService.updateClaim(claim);

            this.showAlert(Alert.AlertType.INFORMATION, "Success!", "Claim edited successfully!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error!", "Something went wrong!");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private void deleteClaim() {
        try {
            Claim claim = claimService.getClaim(idField.getText());
            if (claim.getStatus().equals(ClaimStatus.NEW)) {
                claimService.deleteClaim(claim);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error!", "You can only delete 'NEW' claims");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error!", "Something went wrong!");
        }
    }

    private boolean showDeleteConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this claim?");
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent() && action.get() == ButtonType.OK;
    }

}

