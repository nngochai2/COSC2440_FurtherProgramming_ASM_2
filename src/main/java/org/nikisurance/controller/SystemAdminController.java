package org.nikisurance.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public SystemAdminController() {
        claimService = new ClaimServiceImpl();
        customerService = new CustomerServiceImpl();
        dependentService = new DependentServiceImpl();
        policyHolderService = new PolicyHolderServiceImpl();
        providerService = new ProviderServiceImpl();
        policyOwnerService = new PolicyOwnerServiceImpl();
    }

    @FXML
    private TextField entityIdField;

    @FXML
    private Button updateInfoButton;

    @FXML
    private Button deleteEntityButton;

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
    private PieChart customerPieChart;

    @FXML
    private Label totalCustomersValue;

    @FXML
    private Label totalClaimsValue;

    @FXML
    private Label averageSuccessfulClaims;

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
    private TextField policyHolderIdField;

    @FXML
    private TextField policyHolderNameField;

    @FXML
    private TextField policyHolderUsernameField;

    @FXML
    private TextField policyHolderPasswordField;

    @FXML
    private TextField policyHolderEmailField;

    @FXML
    private TextField policyHolderPhoneNumberField;

    @FXML
    private TextField policyHolderAddressField;

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
//            stage.initStyle(StageStyle.TRANSPARENT);
            loadDashboard();
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
        claimTableView.setItems(FXCollections.observableList(claimService.getAllClaims()));
        customerTableView.setItems(FXCollections.observableList(customerService.getAllCustomers()));
        providerTableView.setItems(FXCollections.observableList(providerService.getAllProviders()));
    }

    private void initializeColumns() {
        // Initialize columns for claims
        claimIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaimId()));
        claimAmountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimAmount()));
        claimDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimDate()).asString());
        insuredPersonNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInsuredPerson()));
        insuredPersonIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBeneficiaryId()));
        surveyorIdColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getBeneficiaryId()));
        claimStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().name()));

        // Initialize columns for customers
        customerIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        customerUsernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        customerPasswordColumn.setCellValueFactory(cellDate -> new SimpleStringProperty(cellDate.getValue().getPassword()));
        customerRoleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerType()));

        // Initialize columns for providers
        providerIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        providerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        providerUsernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        providerPasswordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        providerRoleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRole())));

        populateTables();
    }

    // Method to load the dashboard for admin
    private void loadDashboard() {
        // Load total number of customers
        int totalCustomers = customerService.getAllCustomers().size();
        totalCustomersValue.setText(String.valueOf(totalCustomers));

        // Load total number of claims
        int totalClaims = claimService.getAllClaims().size();
        totalClaimsValue.setText(String.valueOf(totalClaims));

        // Calculate percentage of approved claims
        long approveClaimsCount = claimService.getCountByStatus(ClaimStatus.APPROVED);
        double approvedClaimsPercentage = 0;
        if (totalClaims > 0) {
            approvedClaimsPercentage = (double) approveClaimsCount / totalClaims * 100;
        }

        averageSuccessfulClaims.setText(String.format("%.2f%%", approvedClaimsPercentage));

        // Load the bar chart for claims
        this.loadClaimsSummaryData();

        // Load the pie chart for customers
        this.loadCustomersSummaryData();
    }

    // Method to load the claims data to a bar chart
    private void loadClaimsSummaryData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("New", claimService.getCountByStatus(ClaimStatus.NEW)));
        series.getData().add(new XYChart.Data<>("Processing", claimService.getCountByStatus(ClaimStatus.PROCESSING)));
        series.getData().add(new XYChart.Data<>("Approved", claimService.getCountByStatus(ClaimStatus.APPROVED)));
        series.getData().add(new XYChart.Data<>("Rejected", claimService.getCountByStatus(ClaimStatus.REJECTED)));
        claimsBarChart.getData().add(series);
    }

    // Method to load the users data to a pie chart
    private void loadCustomersSummaryData() {
        PieChart.Data slice1 = new PieChart.Data("Dependents", dependentService.getAllDependents().size());
        PieChart.Data slice2 = new PieChart.Data("Policy Holders", policyHolderService.getAllPolicyHolders().size());
        PieChart.Data slice3 = new PieChart.Data("Policy Owners", policyOwnerService.getAllPolicyOwners().size());
        PieChart.Data slice4 = new PieChart.Data("Insurance Managers", providerService.countInsuranceManagers());
        PieChart.Data slice5 = new PieChart.Data("Insurance Surveyors", providerService.countInsuranceSurveyors());
        customerPieChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);
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
    private void addPolicyHolder() {
        try {
            PolicyHolder policyHolder = new PolicyHolder();
            policyHolder.setFullName(policyHolderNameField.getText());
            policyHolder.setUsername(policyHolderUsernameField.getText());
            policyHolder.setPassword(policyHolderPasswordField.getText());
            policyHolder.setEmail(policyHolderEmailField.getText());
            policyHolder.setPhoneNumber(Long.valueOf(policyHolderPhoneNumberField.getText()));
            policyHolder.setAddress(policyHolderAddressField.getText());
            this.
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to add policy holder: " + e.getMessage());
        }
    }
}