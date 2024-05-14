package org.nikisurance.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.service.interfaces.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Controller
public class PolicyHolderController extends ClaimController {

    @FXML
    private TextField claimIdField;

    @FXML
    private TextField claimDateField;

    @FXML
    private TextField examDateField;

    @FXML
    private TextField claimAmountField;

    @FXML
    private TextField bankingInfoField;

    @Autowired
    private ClaimService claimService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        super.initialize(url, resources);
    }

    @FXML
    private void handleAddClaimButtonAction(MouseEvent event) {
        Claim newClaim = new Claim();
        newClaim.setClaimDate(LocalDate.parse(claimDateField.getText(), dateFormatter));
        newClaim.setExamDate(LocalDate.parse(examDateField.getText(), dateFormatter));
        newClaim.setClaimAmount(Double.parseDouble(claimAmountField.getText()));
        newClaim.setStatus(ClaimStatus.NEW);
        newClaim.setReceiverBankingInfo(null, bankingInfoField.getText());

        claimService.addClaim(newClaim);
        populateTable();
    }

    @FXML
    private void handleDeleteClaimButtonAction(MouseEvent event) {
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null && selectedClaim.getStatus() == ClaimStatus.NEW) {
            claimService.deleteClaim(selectedClaim);
            populateTable();
        }
    }

}
