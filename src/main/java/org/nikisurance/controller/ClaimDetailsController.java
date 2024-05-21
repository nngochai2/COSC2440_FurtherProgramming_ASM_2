package org.nikisurance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.service.impl.ClaimServiceImpl;
import org.nikisurance.service.interfaces.ClaimService;

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

    private final ClaimService claimService;

    public ClaimDetailsController() {
        claimService = new ClaimServiceImpl();
    }
}
