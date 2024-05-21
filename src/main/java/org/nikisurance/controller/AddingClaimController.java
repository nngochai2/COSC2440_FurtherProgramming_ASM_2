package org.nikisurance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.nikisurance.entity.*;
import org.nikisurance.service.impl.ClaimServiceImpl;
import org.nikisurance.service.impl.PolicyOwnerServiceImpl;
import org.nikisurance.service.interfaces.ClaimService;
import org.nikisurance.service.interfaces.DependentService;
import org.nikisurance.service.interfaces.PolicyHolderService;
import org.nikisurance.service.interfaces.PolicyOwnerService;

import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddingClaimController implements Initializable {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final ClaimService claimService;

    @FXML private TextField claimAmountTextField;

    @FXML private DatePicker claimDatePicker;

    @FXML private DatePicker examDatePicker;

    @FXML private TextField claimAmountField;

    @FXML private TextField claimStatusField;

    @FXML private TextField bankingInfoField;

    @FXML private TextField filterTextField;

    @FXML private ComboBox<ClaimStatus> claimStatusComboBox;

    @FXML private Button submitButton;

    // Allow the policy holder to choose to file a claim for themselves or for their dependent
    private ComboBox<String> claimantComboBox;

    public AddingClaimController() {
        this.claimService = new ClaimServiceImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupClaimantComboBox();
    }

    private void setupClaimantComboBox() {

    }
}
