package org.nikisurance.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.entity.Dependent;
import org.nikisurance.service.impl.ClaimServiceImpl;
import org.nikisurance.service.interfaces.ClaimService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DependentController implements Initializable {
    private final Logger logger = Logger.getLogger(DependentController.class.getName());
    private final ClaimService claimService;

    @FXML private JFXButton btnDashboard;
    @FXML private JFXButton btnClaims;
    @FXML private JFXButton btnUsers;
    @FXML private JFXButton btnProviders;
    @FXML private JFXButton btnSettings;
    @FXML private FontAwesomeIconView closeButton;
    @FXML private Pane pnDashboard;
    @FXML private Pane pnClaims;
    @FXML private Pane pnUsers;
    @FXML private Pane pnProviders;
    @FXML private Pane pnSettings;
    @FXML private AnchorPane sideBar;
    @FXML private JFXButton btnSignOut;
    @FXML private BarChart<String, Number> claimsBarChart;
    @FXML private Label totalClaimsAmountValue;
    @FXML private Label totalSuccessfulClaims;
    @FXML private Label totalRejectedClaims;

    @FXML private TableView<Claim> claimTableView;
    @FXML private TableColumn<Claim, String> claimIdColumn;
    @FXML private TableColumn<Claim, Double> claimAmountColumn;
    @FXML private TableColumn<Claim, String> claimDateColumn;
    @FXML private TableColumn<Claim, String> examDateColumn;
    @FXML private TableColumn<Claim, String> claimStatusColumn;

    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField filterTextField;
    @FXML private FilteredList<Claim> filteredClaims;

    private Dependent currentDependent;
    private Stage stage;

    public DependentController() {
        this.claimService = new ClaimServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentDependent = (Dependent) UserSession.getInstance().getLoggedInPerson();
        if (currentDependent != null) {
            initializeColumns();
            loadClaims();
            populatePersonalInfo();
        } else {
            logger.severe("No Dependent found in session.");
        }
    }

    private void initializeColumns() {
        claimIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaimId()));
        claimAmountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimAmount()));
        claimDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getClaimDate())));
        examDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getExamDate())));
        claimStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    }

    private void loadClaims() {
        List<Claim> claims = claimService.getClaimsByPersonId(currentDependent.getId());
        ObservableList<Claim> claimData = FXCollections.observableArrayList(claims);
        claimTableView.setItems(claimData);
    }

    private void populatePersonalInfo() {
        emailField.setText(currentDependent.getEmail());
        phoneField.setText(String.valueOf(currentDependent.getPhoneNumber()));
        addressField.setText(currentDependent.getAddress());
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        // Reset all buttons to normal font weight
        btnDashboard.setFont(Font.font(btnDashboard.getFont().getFamily(), FontWeight.NORMAL, btnDashboard.getFont().getSize()));
        btnClaims.setFont(Font.font(btnClaims.getFont().getFamily(), FontWeight.NORMAL, btnClaims.getFont().getSize()));
        btnUsers.setFont(Font.font(btnUsers.getFont().getFamily(), FontWeight.NORMAL, btnUsers.getFont().getSize()));
        btnProviders.setFont(Font.font(btnProviders.getFont().getFamily(), FontWeight.NORMAL, btnProviders.getFont().getSize()));
        btnSettings.setFont(Font.font(btnSettings.getFont().getFamily(), FontWeight.NORMAL, btnSettings.getFont().getSize()));

        if (actionEvent.getSource() == btnDashboard) {
            pnDashboard.toFront();
            closeButton.toFront();
            btnDashboard.setFont(Font.font(btnDashboard.getFont().getFamily(), FontWeight.BOLD, btnDashboard.getFont().getSize()));

        }
        if (actionEvent.getSource() == btnClaims) {
            pnClaims.toFront();
            closeButton.toFront();
            btnClaims.setFont(Font.font(btnClaims.getFont().getFamily(), FontWeight.BOLD, btnClaims.getFont().getSize()));
        }
        if (actionEvent.getSource() == btnUsers) {
            pnUsers.toFront();
            closeButton.toFront();
            btnUsers.setFont(Font.font(btnUsers.getFont().getFamily(), FontWeight.BOLD, btnUsers.getFont().getSize()));
        }
        if(actionEvent.getSource()==btnProviders)
        {
            pnProviders.toFront();
            closeButton.toFront();
            btnProviders.setFont(Font.font(btnProviders.getFont().getFamily(), FontWeight.BOLD, btnProviders.getFont().getSize()));
        }
        if(actionEvent.getSource()==btnSettings)
        {
            pnSettings.toFront();
            closeButton.toFront();
            btnSettings.setFont(Font.font(btnSettings.getFont().getFamily(), FontWeight.BOLD, btnSettings.getFont().getSize()));
        }
    }

    @FXML
    private void closeProgram(javafx.scene.input.MouseEvent e) {
        stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // Method to load the dashboard for policy holder
    private void loadDashboard() {
        // Load total claim amount
        List<Claim> allClaims = claimService.getClaimsByPersonId(currentDependent.getId());
        double totalApprovedClaimsAmount = 0;
        for (Claim claim : allClaims) {
            if (claim.getStatus() == ClaimStatus.APPROVED) {
                totalApprovedClaimsAmount += claim.getClaimAmount();
            }
        }
        totalClaimsAmountValue.setText(String.format("%.2f", totalApprovedClaimsAmount));

        // Count the total successful claims
        double approvedClaimsNumber = 0;
        for (Claim claim : allClaims) {
            if (claim.getStatus() == ClaimStatus.APPROVED) {
                approvedClaimsNumber += 1;
            }
        }
        totalSuccessfulClaims.setText(String.format("%.2f", approvedClaimsNumber));

        double rejectedClaimsNumber = 0;
        for (Claim claim : allClaims) {
            if (claim.getStatus() == ClaimStatus.REJECTED) {
                rejectedClaimsNumber += 1;
            }
        }
        totalRejectedClaims.setText(String.format("%.2f", rejectedClaimsNumber));
    }

    public void setupFiltering() {
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (filteredClaims != null) {
                filteredClaims.setPredicate(claim -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilterText = newValue.toLowerCase();

                    return claim.getClaimId().toLowerCase().contains(lowerCaseFilterText) ||
                            claim.getClaimDate().toString().contains(lowerCaseFilterText) ||
                            (claim.getExamDate() != null && claim.getExamDate().toString().contains(lowerCaseFilterText)) ||
                            Double.toString(claim.getClaimAmount()).contains(lowerCaseFilterText) ||
                            claim.getStatus().toString().toLowerCase().contains(lowerCaseFilterText) ||
                            (claim.getReceiverBankingInfo() != null && claim.getReceiverBankingInfo().toLowerCase().contains(lowerCaseFilterText));
                });
            }
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredClaims);
        sortedData.comparatorProperty().bind(claimTableView.comparatorProperty());
        claimTableView.setItems(sortedData);
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
