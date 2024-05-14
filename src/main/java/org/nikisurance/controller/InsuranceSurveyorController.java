package org.nikisurance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nikisurance.entity.Claim;
import org.nikisurance.service.interfaces.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class InsuranceSurveyorController implements Initializable {

    @Autowired
    private ClaimService claimService;

    @FXML
    private TableView<Claim> claimTable;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, String> claimDateColumn;

    @FXML
    private TableColumn<Claim, String> examDateColumn;

    @FXML
    private TableColumn<Claim, Integer> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableColumn<Claim, String> bankingInfoColumn;

    @FXML
    private TextField claimIdField;
    @FXML
    private TextField additionalInfoField;

    private ObservableList<Claim> claimsData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        claimIdColumn.setCellValueFactory(new PropertyValueFactory<>("claimId"));
        claimDateColumn.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDateColumn.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimAmountColumn.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        claimStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        bankingInfoColumn.setCellValueFactory(new PropertyValueFactory<>("receiverBankingInfo"));

        populateTable();
    }

    public void populateTable() {
        List<Claim> claimList = claimService.getAllClaims();
        claimsData = FXCollections.observableArrayList(claimList);
        claimTable.setItems(claimsData);
    }

    @FXML
    private void requireMoreInfo() {
        String id = claimIdField.getText();
        String additionalInfo = additionalInfoField.getText();
        // Logic to require more information for the claim
        showAlert(AlertType.INFORMATION, "Request Sent", "Additional information required for claim ID: " + id);
    }

    @FXML
    private void proposeClaim() {
        String id = claimIdField.getText();
        // Logic to propose claim to managers
        showAlert(AlertType.INFORMATION, "Claim Proposed", "Claim ID: " + id + " proposed to managers.");
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}