package org.nikisurance.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.entity.Customer;
import org.nikisurance.service.interfaces.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemAdminController implements Initializable {

    private final Logger logger = Logger.getLogger(SystemAdminController.class.getName());

    private ClaimService claimService;
    private CustomerService customerService;
    private DependentService dependentService;
    private PolicyHolderService policyHolderService;
    private ProviderService providerService;
    private PolicyOwnerService policyOwnerService;

    public SystemAdminController() {}

    public void setServices(ClaimService claimService, CustomerService customerService, DependentService dependentService, PolicyHolderService policyHolderService, ProviderService providerService, PolicyOwnerService policyOwnerService) {
        this.claimService = claimService;
        this.customerService = customerService;
        this.dependentService = dependentService;
        this.policyHolderService = policyHolderService;
        this.providerService = providerService;
        this.policyOwnerService = policyOwnerService;
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
    private Label totalCustomerLabel;

    @FXML
    private Label totalClaimsLabel;

    @FXML
    private Label totalProvidersLabel;

    @FXML
    private Label totalCustomersValue;

    @FXML
    private Label totalClaimsValue;

    @FXML
    private Label totalProvidersValue;

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
    private TableColumn<Claim, String> insuredPersonIdColumn;

    @FXML
    private TableColumn<Claim, String> surveyorNameColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerUsernameColumn;

    @FXML
    private TableColumn<Customer, String> customerPasswordColumn;

    @FXML
    private TableColumn<Customer, String> customerRoleColumn;

    private double x = 0, y = 0;

    private Stage stage;

    public SystemAdminController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Any initialization code
        sideBar.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        sideBar.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });

        this.loadDashboard();
    }

    private void initializeColumns() {
        // Claims table columns

    }

    // Method to load the dashboard for admin
    private void loadDashboard() {
        // Load total number of customers
        int totalCustomers = customerService.getAllCustomers().size();
        totalCustomersValue.setText(String.valueOf(totalCustomers));

        // Load total number of claims
        int totalClaims = claimService.getAllClaims().size();
        totalClaimsValue.setText(String.valueOf(totalClaims));

        // Load total number of providers
        int totalProviders = providerService.getAllProviders().size();
        totalProvidersValue.setText(String.valueOf(totalProviders));

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
        PieChart.Data slice5 = new PieChart.Data("Insurance Surveyors", providerService.countInsuranceProvider());
        customerPieChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);
    }

    @FXML
    private void retrieveInfo() {
        String id = entityIdField.getText();
        Customer customer = customerService.getCustomer(Long.parseLong(id));
        if (customer != null) {
            showAlert(AlertType.INFORMATION, "Customer Retrieved", "Customer ID: " + customer.getId() + ", Name: " + customer.getFullName());
        } else {
            showAlert(AlertType.ERROR, "Customer Not Found", "No customer found with ID: " + id);
        }
    }

    @FXML
    private void updateInfo() {
        // Logic to update information of entities (claims, customers, etc.)
    }

    @FXML
    private void deleteEntity() {
        String id = entityIdField.getText();
        customerService.deleteCustomer(Long.parseLong(id));
        showAlert(AlertType.INFORMATION, "Entity Deleted", "Entity with ID: " + id + " has been deleted.");
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
}