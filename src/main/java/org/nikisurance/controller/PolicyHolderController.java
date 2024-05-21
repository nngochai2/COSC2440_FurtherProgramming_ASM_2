package org.nikisurance.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nikisurance.entity.*;
import org.nikisurance.service.impl.*;
import org.nikisurance.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PolicyHolderController extends ClaimController implements Initializable {

    private final Logger logger = Logger.getLogger(SystemAdminController.class.getName());

    private final ClaimService claimService;
    private DependentService dependentService;
    private PolicyHolderService policyHolderService;

    public PolicyHolderController() {
        claimService = new ClaimServiceImpl();
        dependentService = new DependentServiceImpl();
        policyHolderService = new PolicyHolderServiceImpl();
    }

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnClaims;

    @FXML
    private JFXButton btnUsers;

    @FXML
    private JFXButton btnProviders;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private Pane pnDashboard;

    @FXML
    private Pane pnClaims;

    @FXML
    private Pane pnUsers;

    @FXML
    private Pane pnProviders;

    @FXML
    private Pane pnSettings;

    @FXML
    private AnchorPane sideBar;

    @FXML
    private JFXButton btnSignOut;

    @FXML
    private BarChart<String, Number> claimsBarChart;

    @FXML
    private Label totalClaimsAmountValue;

    @FXML private Label totalSuccessfulClaims;

    @FXML private Label totalRejectedClaims;

    @FXML
    private TableView<Claim> claimTableView;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, Double> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimDateColumn;

    @FXML
    private TableColumn<Claim, String> examDateColumn;

    @FXML
    private TableColumn<Claim, String> insuredPersonNameColumn;

    @FXML
    private TableColumn<Claim, Long> insuredPersonIdColumn;

    @FXML
    private TableColumn<Claim, Long> surveyorIdColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML private TableView<Dependent> dependentTableView;

    @FXML private TableColumn<Dependent, Long> dependentIdColumn;

    @FXML private TableColumn<Dependent, String> dependentNameColumn;

    @FXML private TableColumn<Dependent, String> dependentUsernameColumn;

    @FXML private TableColumn<Dependent, String> dependentEmailColumn;

    @FXML private TableColumn<Dependent, Long> dependentPhoneColumn;

    @FXML private TableColumn<Dependent, String> dependentAddressColumn;

    @FXML
    private TextField policyHolderEmailField;

    @FXML
    private TextField policyHolderPhoneNumberField;

    @FXML
    private TextField policyHolderAddressField;

    @FXML
    private TextField filterTextField;

    @FXML
    private FilteredList<Claim> filteredClaims;

    private double x = 0, y = 0;

    private Stage stage;

    private PolicyHolder currentPolicyHolder;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Person person = UserSession.getInstance().getLoggedInPerson();
        person = currentPolicyHolder;

        initializeColumns();

        Platform.runLater(() -> {
            stage = (Stage) sideBar.getScene().getWindow();
            loadDashboard();
            setupFiltering();
        });
        sideBar.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        sideBar.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }

    private void populateTables() {
        List<Claim> claims = claimService.getClaimsForPolicyHolderAndDependents(currentPolicyHolder.getId());
        ObservableList<Claim> claimObservableList = FXCollections.observableList(claims);
        filteredClaims = new FilteredList<>(claimObservableList, p -> true);

        claimTableView.setItems(new SortedList<>(filteredClaims));

        dependentTableView.setItems(FXCollections.observableList(dependentService.getDependentsByPolicyHolderId(currentPolicyHolder.getId())));

    }

    private void initializeColumns() {
        // Initialize columns for claims
        claimIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaimId()));
        claimAmountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimAmount()));
        claimDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimDate()).asString());
        examDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExamDate()).asString());
        insuredPersonNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInsuredPerson()));
        insuredPersonIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBeneficiaryId()));
        surveyorIdColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getBeneficiaryId()));
        claimStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().name()));

        // Initialize columns for dependents
        dependentIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        dependentNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        dependentUsernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        dependentEmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        dependentPhoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhoneNumber()));
        dependentAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        populateTables();
    }

    // Method to load the dashboard for policy holder
    private void loadDashboard() {
        // Load total claim amount
        List<Claim> allClaims = claimService.getClaimsByPersonId(currentPolicyHolder.getId());
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

    public void refreshDependentTable() {
        List<Dependent> updatedList = dependentService.getDependentsByPolicyHolderId(currentPolicyHolder.getId());
        dependentTableView.getItems().setAll(updatedList);
    }

    @FXML
    private void signOut() {
        try {
            // Load the Main.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikisurance/fxml/Main.fxml"));
            Parent root = loader.load();

            // Create a new scene and display it
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Nikisurance");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) btnSignOut.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException found.");
        }
    }

    @FXML
    private void handleDependentClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double click
            Dependent selectedDependent = dependentTableView.getSelectionModel().getSelectedItem();
            if (selectedDependent != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nikisurance/fxml/BeneficiaryDetails.fxml"));
                    Parent root = loader.load();

                    BeneficiaryDetailsController controller = loader.getController();
                    controller.setBeneficiary(selectedDependent); // Corrected method name

                    Stage stage = new Stage();
                    stage.setTitle("Dependent Details");
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Failed to load customer details view", e);
                    showAlert(Alert.AlertType.ERROR, "Error", "Cannot load the customer details view.");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "No Selection", "No customer selected.");
            }
        }
    }
}
