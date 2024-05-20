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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemAdminController implements Initializable {

    private final Logger logger = Logger.getLogger(SystemAdminController.class.getName());

    private final ClaimService claimService;
    private final CustomerService customerService;
    private final DependentService dependentService;
    private final PolicyHolderService policyHolderService;
    private final ProviderService providerService;
    private final PolicyOwnerService policyOwnerService;
    private final BeneficiaryService beneficiaryService;

    public SystemAdminController() {
        claimService = new ClaimServiceImpl();
        customerService = new CustomerServiceImpl();
        dependentService = new DependentServiceImpl();
        policyHolderService = new PolicyHolderServiceImpl();
        providerService = new ProviderServiceImpl();
        policyOwnerService = new PolicyOwnerServiceImpl();
        beneficiaryService = new BeneficiaryServiceImpl();
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
    private JFXButton btnHistory;

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
    private PieChart customerPieChart;

    @FXML
    private Label totalCustomersValue;

    @FXML
    private Label totalClaimsAmountValue;

    @FXML
    private Label averageSuccessfulClaims;

    @FXML
    private Label lastAddedClaim;

    @FXML
    private TableView<Claim> claimTableView;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, Double> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimDateColumn;

    @FXML
    private TableColumn<Claim, String> insuredPersonNameColumn;

    @FXML
    private TableColumn<Claim, Long> insuredPersonIdColumn;

    @FXML
    private TableColumn<Claim, Long> surveyorIdColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Long> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerUsernameColumn;

    @FXML
    private TableColumn<Customer, String> customerPasswordColumn;

    @FXML
    private TableColumn<Customer, String> customerRoleColumn;

    @FXML
    private TableView<Provider> providerTableView;

    @FXML
    private TableColumn<Provider, Long> providerIdColumn;

    @FXML
    private TableColumn<Provider, String> providerNameColumn;

    @FXML
    private TableColumn<Provider, String> providerUsernameColumn;

    @FXML
    private TableColumn<Provider, String> providerPasswordColumn;

    @FXML
    private TableColumn<Provider, String> providerRoleColumn;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

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

    @FXML
    private TableView<Beneficiary> beneficiaryTable;

    @FXML
    private TableColumn<Beneficiary, Long> idColumn;
    @FXML
    private TableColumn<Beneficiary, String> nameColumn;
    @FXML
    private TableColumn<Beneficiary, String> usernameColumn;
    @FXML
    private TableColumn<Beneficiary, String> passwordColumn;
    @FXML
    private TableColumn<Beneficiary, String> emailColumn;
    @FXML
    private TableColumn<Beneficiary, Long> phoneNumberColumn;
    @FXML
    private TableColumn<Beneficiary, String> addressColumn;
    @FXML
    private TableColumn<Beneficiary, String> beneficiaryTypeColumn;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private CategoryAxis xAxis;

    private double x = 0, y = 0;

    private Stage stage;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();

        // Use a background task for database operations
        // Any initialization code
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

        populateTables();
    }

    private void populateTables() {
        List<Claim> claims = claimService.getAllClaims();
        ObservableList<Claim> claimObservableList = FXCollections.observableList(claims);
        filteredClaims = new FilteredList<>(claimObservableList, p -> true);

        beneficiaryTable.setItems(FXCollections.observableList(beneficiaryService.getAllBeneficiaries()));
        claimTableView.setItems(new SortedList<>(filteredClaims));
        // customerTableView.setItems(FXCollections.observableList(customerService.getAllCustomers()));
        providerTableView.setItems(FXCollections.observableList(providerService.getAllProviders()));
    }

    private void initializeColumns() {
        // Initialize columns for claims
        claimIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaimId()));
        claimAmountColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimAmount()));
        claimDateColumn.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimDate()).asString());
        insuredPersonNameColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInsuredPerson()));
        insuredPersonIdColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBeneficiaryId()));
        surveyorIdColumn
                .setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getBeneficiaryId()));
        claimStatusColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().name()));

        // Initialize columns for customers
        // customerIdColumn.setCellValueFactory(cellData -> new
        // SimpleObjectProperty<>(cellData.getValue().getId()));
        // customerNameColumn.setCellValueFactory(cellData -> new
        // SimpleStringProperty(cellData.getValue().getFullName()));
        // customerUsernameColumn.setCellValueFactory(cellData -> new
        // SimpleStringProperty(cellData.getValue().getUsername()));
        // customerPasswordColumn.setCellValueFactory(cellDate -> new
        // SimpleStringProperty(cellDate.getValue().getPassword()));
        // customerRoleColumn.setCellValueFactory(cellData -> new
        // SimpleStringProperty(cellData.getValue().getCustomerType()));

        // Initialize columns for providers
        providerIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        providerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        providerUsernameColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        providerPasswordColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        providerRoleColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRole())));

        // Set up the columns to map to the respective fields of the Beneficiary entity
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneNumberColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhoneNumber()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        beneficiaryTypeColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBeneficiaryType()));
    }

    // Method to load the dashboard for admin
    private void loadDashboard() {
        // Load total number of customers
        int totalCustomers = customerService.getAllCustomers().size();
        totalCustomersValue.setText(String.valueOf(totalCustomers));

        // Load total number of claims
        List<Claim> allClaims = claimService.getAllClaims();
        double totalApprovedClaimsAmount = 0;
        for (Claim claim : allClaims) {
            if (claim.getStatus() == ClaimStatus.APPROVED) {
                totalApprovedClaimsAmount += claim.getClaimAmount();
            }
        }

        totalClaimsAmountValue.setText(String.format("%.0f", totalApprovedClaimsAmount));
        lastAddedClaim.setText("Last Registered Claim:  " + allClaims.get(allClaims.size() - 1).getClaimId() + "  ("
                + allClaims.get(allClaims.size() - 1).getInsuredPerson() + ")");

        // Calculate percentage of approved claims
        int totalClaims = claimService.getAllClaims().size();
        long approveClaimsCount = claimService.getCountByStatus(ClaimStatus.APPROVED);
        double approvedClaimsPercentage = 0;
        if (totalClaims > 0) {
            approvedClaimsPercentage = (double) approveClaimsCount / totalClaims * 100;
        }

        averageSuccessfulClaims.setText(String.format("%.1f%%", approvedClaimsPercentage));

        // Load the bar chart for claims
        this.loadClaimsSummaryData();

        // Load the pie chart for customers
        this.loadCustomersSummaryData();
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
                            (claim.getExamDate() != null
                                    && claim.getExamDate().toString().contains(lowerCaseFilterText))
                            ||
                            Double.toString(claim.getClaimAmount()).contains(lowerCaseFilterText) ||
                            claim.getStatus().toString().toLowerCase().contains(lowerCaseFilterText) ||
                            (claim.getReceiverBankingInfo() != null
                                    && claim.getReceiverBankingInfo().toLowerCase().contains(lowerCaseFilterText));
                });
            }
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredClaims);
        sortedData.comparatorProperty().bind(claimTableView.comparatorProperty());
        claimTableView.setItems(sortedData);
    }

    // Method to load the claims data to a bar chart
    private void loadClaimsSummaryData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("New", claimService.getCountByStatus(ClaimStatus.NEW)));
        series.getData().add(new XYChart.Data<>("Processing", claimService.getCountByStatus(ClaimStatus.PROCESSING)));
        series.getData().add(new XYChart.Data<>("Approved", claimService.getCountByStatus(ClaimStatus.APPROVED)));
        series.getData().add(new XYChart.Data<>("Rejected", claimService.getCountByStatus(ClaimStatus.REJECTED)));
        xAxis.setCategories(FXCollections.observableArrayList("New", "Processing", "Approved", "Rejected"));
        claimsBarChart.getData().add(series);
        claimsBarChart.setLegendVisible(false);
    }

    // Method to load the users data to a pie chart
    private void loadCustomersSummaryData() {
        PieChart.Data slice1 = new PieChart.Data("DEP", dependentService.getAllDependents().size());
        PieChart.Data slice2 = new PieChart.Data("PH", policyHolderService.getAllPolicyHolders().size());
        PieChart.Data slice3 = new PieChart.Data("PO", policyOwnerService.getAllPolicyOwners().size());
        PieChart.Data slice4 = new PieChart.Data("IM", providerService.countInsuranceManagers());
        PieChart.Data slice5 = new PieChart.Data("IS", providerService.countInsuranceSurveyors());
        customerPieChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);
        customerPieChart.setLabelsVisible(false);
        customerPieChart.setLegendVisible(true);

        // Calculate the total of all slices
        double total = slice1.getPieValue() + slice2.getPieValue() + slice3.getPieValue() + slice4.getPieValue()
                + slice5.getPieValue();

        // Set the labels of the PieChart.Data objects to display the percentage of the
        // total
        slice1.setName(slice1.getName() + ": " + String.format("%.1f%%", 100 * slice1.getPieValue() / total));
        slice2.setName(slice2.getName() + ": " + String.format("%.1f%%", 100 * slice2.getPieValue() / total));
        slice3.setName(slice3.getName() + ": " + String.format("%.1f%%", 100 * slice3.getPieValue() / total));
        slice4.setName(slice4.getName() + ": " + String.format("%.1f%%", 100 * slice4.getPieValue() / total));
        slice5.setName(slice5.getName() + ": " + String.format("%.1f%%", 100 * slice5.getPieValue() / total));
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public void handleClicks(ActionEvent actionEvent) {
        // Reset all buttons to normal font weight
        btnDashboard.setFont(
                Font.font(btnDashboard.getFont().getFamily(), FontWeight.NORMAL, btnDashboard.getFont().getSize()));
        btnClaims.setFont(Font.font(btnClaims.getFont().getFamily(), FontWeight.NORMAL, btnClaims.getFont().getSize()));
        btnUsers.setFont(Font.font(btnUsers.getFont().getFamily(), FontWeight.NORMAL, btnUsers.getFont().getSize()));
        btnProviders.setFont(
                Font.font(btnProviders.getFont().getFamily(), FontWeight.NORMAL, btnProviders.getFont().getSize()));
        btnSettings.setFont(
                Font.font(btnSettings.getFont().getFamily(), FontWeight.NORMAL, btnSettings.getFont().getSize()));

        if (actionEvent.getSource() == btnDashboard) {
            pnDashboard.toFront();
            closeButton.toFront();
            btnDashboard.setFont(
                    Font.font(btnDashboard.getFont().getFamily(), FontWeight.BOLD, btnDashboard.getFont().getSize()));

        }
        if (actionEvent.getSource() == btnClaims) {
            pnClaims.toFront();
            closeButton.toFront();
            btnClaims.setFont(
                    Font.font(btnClaims.getFont().getFamily(), FontWeight.BOLD, btnClaims.getFont().getSize()));
        }
        if (actionEvent.getSource() == btnUsers) {
            pnUsers.toFront();
            closeButton.toFront();
            btnUsers.setFont(Font.font(btnUsers.getFont().getFamily(), FontWeight.BOLD, btnUsers.getFont().getSize()));
        }
        if (actionEvent.getSource() == btnProviders) {
            pnProviders.toFront();
            closeButton.toFront();
            btnProviders.setFont(
                    Font.font(btnProviders.getFont().getFamily(), FontWeight.BOLD, btnProviders.getFont().getSize()));
        }
        if (actionEvent.getSource() == btnSettings) {
            pnSettings.toFront();
            closeButton.toFront();
            btnSettings.setFont(
                    Font.font(btnSettings.getFont().getFamily(), FontWeight.BOLD, btnSettings.getFont().getSize()));
        }
    }

    @FXML
    private void closeProgram(javafx.scene.input.MouseEvent e) {
        stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void refreshBeneficiaryTable() {
        List<Beneficiary> updatedList = beneficiaryService.getAllBeneficiaries();
        beneficiaryTable.getItems().setAll(updatedList);
//        beneficiaryTable.setItems(FXCollections.observableArrayList(beneficiaryService.getAllBeneficiaries()));
    }

    @FXML
    private void handleCustomerClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double click
            Beneficiary selectedBeneficiary = beneficiaryTable.getSelectionModel().getSelectedItem();
            if (selectedBeneficiary != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/com/nikisurance/fxml/BeneficiaryDetails.fxml"));
                    Parent root = loader.load();

                    BeneficiaryDetailsController controller = loader.getController();
                    controller.setSystemAdminController(this);
                    controller.setBeneficiary(selectedBeneficiary); // Corrected method name

                    Stage stage = new Stage();
                    stage.setTitle("Customer Details");
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

    @FXML
    private void addPolicyHolder() {
        try {
            PolicyHolder policyHolder = new PolicyHolder();
            policyHolder.setFullName(nameField.getText());
            policyHolder.setUsername(usernameField.getText());
            policyHolder.setPassword(passwordField.getText());
            policyHolder.setEmail(policyHolderEmailField.getText());
            policyHolder.setPhoneNumber(Long.valueOf(policyHolderPhoneNumberField.getText()));
            policyHolder.setAddress(policyHolderAddressField.getText());

            this.showAlert(AlertType.INFORMATION, "Policy Added Successfully", "Policy has been added successfully.");
            this.refreshBeneficiaryTable();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to add policy holder: " + e.getMessage());
        }
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
}