package org.nikisurance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.Dependent;
import org.nikisurance.service.impl.ClaimServiceImpl;
import org.nikisurance.service.interfaces.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DependentController implements Initializable {

    private ClaimService claimService;

    @FXML
    private Label dependentInsuranceCard;

    @FXML
    private Label dependentEmail;

    @FXML
    private Label dependentAddress;

    @FXML
    private Label dependentPhone;

    @FXML
    private TableView<Claim> dependentTable;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, LocalDate> claimDateColumn;

    @FXML
    private TableColumn<Claim, LocalDate> examDateColumn;

    @FXML
    private TableColumn<Claim, Integer> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableColumn<Claim, String> bankingInfoColumn;

    private ObservableList<Claim> claimsData;

    public DependentController() {
        this.claimService = new ClaimServiceImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        claimIdColumn.setCellValueFactory(new PropertyValueFactory<>("claimId"));
        claimDateColumn.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDateColumn.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimAmountColumn.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        claimStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        bankingInfoColumn.setCellValueFactory(new PropertyValueFactory<>("receiverBankingInfo"));

        populateDependentFields();
        populateTable();
    }

    public void populateDependentFields() {
        // Populate dependent information
        Dependent dependent = getCurrentDependent();
        dependentAddress.setText(dependent.getAddress());
        dependentEmail.setText(dependent.getEmail());
        dependentPhone.setText(dependent.getPhoneNumber().toString());
        dependentInsuranceCard.setText(dependent.getInsuranceCard().toString());
    }

    public void populateTable() {
        List<Claim> claimList = claimService.getAllClaims();
        claimsData = FXCollections.observableArrayList(claimList);
        dependentTable.setItems(claimsData);
    }

    private Dependent getCurrentDependent() {
        // Logic to get the current logged-in dependent
        // This is a placeholder method, replace with actual logic
        return new Dependent();
    }
}
