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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.entity.Dependent;
import org.nikisurance.service.impl.ClaimServiceImpl;
import org.nikisurance.service.interfaces.ClaimService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DependentController implements Initializable {
    private final Logger logger = Logger.getLogger(DependentController.class.getName());
    private final ClaimService claimService;

    @FXML private JFXButton btnDashboard;
    @FXML private JFXButton btnClaims;
    @FXML private JFXButton btnPolicyHolder;
    @FXML private JFXButton btnSettings;
    @FXML private FontAwesomeIconView closeButton;
    @FXML private Pane pnDashboard;
    @FXML private Pane pnClaims;
    @FXML private Pane pnSettings;
    @FXML private AnchorPane sideBar;
    @FXML private JFXButton btnSignOut;
    @FXML private BarChart<String, Number> claimsBarChart;
    @FXML private Label totalClaimsAmountValue;
    @FXML private Label totalSuccessfulClaims;
    @FXML private Label totalRejectedClaims;
    @FXML private CategoryAxis xAxis;

    @FXML private TableView<Claim> claimTableView;
    @FXML private TableColumn<Claim, String> claimIdColumn;
    @FXML private TableColumn<Claim, Double> claimAmountColumn;
    @FXML private TableColumn<Claim, String> claimDateColumn;
    @FXML private TableColumn<Claim, String> examDateColumn;
    @FXML private TableColumn<Claim, String> claimStatusColumn;

    @FXML private TextField idField;
    @FXML private TextField fullNameField;
    @FXML private TextField passwordField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField filterTextField;
    @FXML private FilteredList<Claim> filteredClaims;

    private Dependent currentDependent;
    private Stage stage;
    private double x = 0, y = 0;

    public DependentController() {
        this.claimService = new ClaimServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentDependent = (Dependent) UserSession.getInstance().getLoggedInPerson();

        initializeColumns();

        Platform.runLater(() -> {
            stage = (Stage) sideBar.getScene().getWindow();
            loadDashboard();
            setupFiltering();
            populatePersonalInfo();
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
        idField.setText(String.valueOf(currentDependent.getId()));
        fullNameField.setText(currentDependent.getFullName());
        passwordField.setText(currentDependent.getPassword());
        emailField.setText(currentDependent.getEmail());
        phoneField.setText(String.valueOf(currentDependent.getPhoneNumber()));
        addressField.setText(currentDependent.getAddress());
    }

    private void loadClaimsSummaryData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Approved", claimService.getCountByStatus(ClaimStatus.NEW)));
        series.getData().add(new XYChart.Data<>("Processing", claimService.getCountByStatus(ClaimStatus.PROCESSING)));
        series.getData().add(new XYChart.Data<>("Approved", claimService.getCountByStatus(ClaimStatus.APPROVED)));
        series.getData().add(new XYChart.Data<>("Rejected", claimService.getCountByStatus(ClaimStatus.REJECTED)));
        xAxis.setCategories(FXCollections.observableArrayList("New", "Processing", "Approved", "Rejected"));
        claimsBarChart.getData().add(series);
        claimsBarChart.setLegendVisible(false);
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        // Reset all buttons to normal font weight
        btnDashboard.setFont(Font.font(btnDashboard.getFont().getFamily(), FontWeight.NORMAL, btnDashboard.getFont().getSize()));
        btnClaims.setFont(Font.font(btnClaims.getFont().getFamily(), FontWeight.NORMAL, btnClaims.getFont().getSize()));
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
        if (actionEvent.getSource()==btnSettings)
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

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
