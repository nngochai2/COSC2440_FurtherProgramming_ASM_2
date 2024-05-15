package org.nikisurance.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.service.interfaces.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Controller
public class PolicyHolderController extends ClaimController implements Initializable {

    @FXML
    private Button uploadDocumentsButton;

    @FXML


    @Autowired
    private ClaimService claimService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        super.initialize(url, resources);
    }



    @FXML
    private void handleDeleteClaimButtonAction(MouseEvent event) {
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null && selectedClaim.getStatus() == ClaimStatus.NEW) {
            claimService.deleteClaim(selectedClaim);
            populateTable();
        }
    }

    @FXML
    private void handleAddDependantButtonAction(MouseEvent event) {

    }

}
