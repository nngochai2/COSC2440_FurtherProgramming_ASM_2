package org.nikisurance.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.nikisurance.entity.*;
import org.nikisurance.service.interfaces.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ClaimController implements Initializable {

    private static final String UPLOAD_DIR = "C:/";

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ClaimService claimService;

    @FXML
    protected TableView<Claim> claimTable;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, String> claimDateColumn;

    @FXML
    private TableColumn<Claim, String> examDateColumn;

    @FXML
    private TableColumn<Claim, Double> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableColumn<Claim, String> bankingInfoColumn;

    @FXML
    private TextField filterTextField;

    @FXML
    private ComboBox<ClaimStatus> claimStatusComboBox;

    @FXML
    private Stage stage;

    @FXML
    private DatePicker examDatePicker;

    @FXML
    private TextField claimAmountTextField;

    @FXML
    private TextField claimIdField;

    @FXML
    private TextArea claimDetailsArea;

    protected ObservableList<Claim> claimsData;
    protected FilteredList<Claim> filteredClaims;
    protected SortedList<Claim> sortedClaims;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeClaimTable();
        setupFilteringAndSorting();
    }

    public void initializeClaimTable() {
        claimIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClaimId()));
        claimDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimDate()).asString());
        examDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExamDate()).asString());
        claimAmountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimAmount()));
        claimStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().name()));
        bankingInfoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReceiverBankingInfo()));

        populateTable();
    }

    public void populateTable() {
        getClaimsFromDB();
        filteredClaims = new FilteredList<>(claimsData, p -> true);
        sortedClaims = new SortedList<>(claimsData);
        sortedClaims.comparatorProperty().bind(claimTable.comparatorProperty());
        claimTable.setItems(claimsData);

        claimStatusComboBox.setItems(FXCollections.observableArrayList(ClaimStatus.values()));
    }

    public void setupFilteringAndSorting() {
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClaims.setPredicate(createPredicate());
        });

        claimStatusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredClaims.setPredicate(createPredicate());
        });
    }

    private Predicate<Claim> createPredicate() {
        String filterText = filterTextField.getText();
        ClaimStatus selectedStatus = claimStatusComboBox.getValue();

        return claim -> {
            boolean matchedFilter = (filterText == null || filterText.isEmpty() || claimMatchesFilter(claim, filterText));
            boolean matchStatus = (selectedStatus == null || claim.getStatus() == selectedStatus);
            return matchedFilter && matchStatus;
        };
    }

    private boolean claimMatchesFilter(Claim claim, String filterText) {
        String lowerCaseFilterText = filterText.toLowerCase();
        if (claim.getClaimId().toLowerCase().contains(lowerCaseFilterText)) {
            return true;
        } else if (claim.getClaimDate().toString().contains(lowerCaseFilterText)) {
            return true;
        } else if (claim.getExamDate().toString().contains(lowerCaseFilterText)) {
            return true;
        } else if (Double.toString(claim.getClaimAmount()).contains(lowerCaseFilterText)) {
            return true;
        } else if (claim.getStatus().toString().toLowerCase().contains(lowerCaseFilterText)) {
            return true;
        } else return claim.getReceiverBankingInfo().toLowerCase().contains(lowerCaseFilterText);
    }

    private void getClaimsFromDB() {
        List<Claim> claimList = claimService.getAllClaims();
        claimsData = FXCollections.observableArrayList(claimList);
    }

    @FXML
    private void handleUploadDocumentButtonClicked(ActionEvent event) {
        if (!isAuthorized("UPLOAD_DOCUMENT")) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have permission to upload documents.");
            return;
        }
        // Get the selected claim from the table
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();

        if (selectedClaim != null) {
            // Open file chooser dialog to select image files
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

            if (selectedFiles != null) {
                for (File file : selectedFiles) {
                    try {
                        uploadFile(file, selectedClaim.getClaimId());
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "IOException found.", e);
                    }
                }
            }
        } else {
            // Handle case when no claim is selected
            System.out.println("No claim selected.");
        }
    }

    private void uploadFile(File file, String claimId) throws IOException {
        String newFileName = this.generateFileName(file.getName(), claimId);

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdirs()) {
                throw new IOException("Failed to create upload directory: " + UPLOAD_DIR);
            }
        }

        File renamedFile = new File(uploadDir, newFileName);
        Files.copy(file.toPath(), renamedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private String generateFileName(String originalFilename, String claimId) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return claimId + "_" + timeStamp + "_" + originalFilename;
    }

    /**
     * This function handles the add claim procedure. Customer will have to enter the exam date and their desired claim amount.
     * @param event
     */
    @FXML
    private void handleAddClaim(ActionEvent event) {
        if (!isAuthorized("ADD_CLAIM")) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have permission to add claims.");
            return;
        }
        try {
            double claimAmount = Double.parseDouble(claimAmountTextField.getText());
            Date examDate = java.sql.Date.valueOf(examDatePicker.getValue());

            Claim newClaim = new Claim();
            newClaim.setClaimDate(new Date());
            newClaim.setExamDate(examDate);
            newClaim.setClaimAmount(claimAmount);

            claimService.addClaim(newClaim);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "");
        }
    }

    /**
     * This function handles the delete claim procedure
     * @param event
     */
    @FXML
    private void handleDeleteClaim(ActionEvent event) {
        if (!isAuthorized("DELETE_CLAIM")) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have permission to delete claims.");
            return;
        }
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            claimService.deleteClaim(selectedClaim);
            claimsData.remove(selectedClaim);
            System.out.println("Claim deleted successfully.");
        } else {
            System.out.println("No claim selected");
        }
    }

    /**
     * This function handles the update claim procedure
     * @param event
     */
    @FXML
    private void handleUpdateClaim(ActionEvent event) {
        if (!isAuthorized("UPDATE_CLAIM")) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have permission to update claims.");
            return;
        }
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            try {
                double claimAmount = Double.parseDouble(claimAmountTextField.getText());
                Date examDate = java.sql.Date.valueOf(examDatePicker.getValue());

                selectedClaim.setClaimAmount(claimAmount);
                selectedClaim.setExamDate(examDate);

                claimService.updateClaim(selectedClaim);
                claimTable.refresh();
                System.out.println("Claim updated successfully.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while updating claim", e);
            }
        } else {
            System.out.println("No claim selected.");
        }
    }

    /**
     * This function handles the require more information procedure
     * @param event
     */
    @FXML
    private void handleRequireMoreInfo(ActionEvent event) {
        if (!isAuthorized("REQUIRE_MORE_INFO")) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have permission to request more information.");
            return;
        }
        Claim selectedClaim = claimService.getClaim(claimIdField.getText());
        if (selectedClaim != null) {
            claimDetailsArea.setText(formatClaimDetails(selectedClaim));
            try {
                selectedClaim.setStatus(ClaimStatus.PROCESSING);
                claimService.updateClaim(selectedClaim);
                claimTable.refresh();
                System.out.println("Requested more information for the claim.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while requesting more information.", e);
            }
        } else {
            System.out.println("No claim found with ID: " + claimIdField.getText());
        }
    }

    private String formatClaimDetails(Claim claim) {
        return String.format("Claim ID: %s\nClaim Date: %s\nExam Date: %s\nClaim Amount: %.2f\nClaim Status: %s\nBanking Info: %s",
                claim.getClaimId(), claim.getClaimDate(), claim.getExamDate(), claim.getClaimAmount(), claim.getStatus(), claim.getReceiverBankingInfo());
    }

    /**
     * This function handles the propose claim procedure
     * @param event
     */
    @FXML
    private void handleProposeClaim(ActionEvent event) {
        if (!isAuthorized("PROPOSE_CLAIM")) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have permission to propose claims.");
            return;
        }
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null && selectedClaim.getStatus() == ClaimStatus.NEW) {
            selectedClaim.setStatus(ClaimStatus.PROCESSING);
            claimService.updateClaim(selectedClaim);
            claimTable.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Claim Proposed", "Claim ID: " + selectedClaim.getClaimId() + " proposed to managers.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Invalid Claim", "Only NEW claims can be proposed.");
        }
    }

    /**
     * This function handles the approve claim procedure
     * @param event
     */
    @FXML
    private void handleApproveClaim(ActionEvent event) {
        if (!isAuthorized("APPROVE_CLAIM")) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have permission to approve claims.");
            return;
        }
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null && selectedClaim.getStatus() == ClaimStatus.PROCESSING) {
            try {
                selectedClaim.setStatus(ClaimStatus.APPROVED);
                claimService.updateClaim(selectedClaim);
                claimTable.refresh();
                System.out.println("Claim approved.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while approving claim", e);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Invalid Claim", "Only PROCESSING claims can be approved.");
        }
    }

    /**
     * This function handles the reject claim procedure
     * @param event
     */
    @FXML
    private void handleRejectClaim(ActionEvent event) {
        if (!isAuthorized("REJECT_CLAIM")) {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have permission to reject claims.");
            return;
        }
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null && selectedClaim.getStatus() == ClaimStatus.PROCESSING) {
            try {
                selectedClaim.setStatus(ClaimStatus.REJECTED);
                claimService.updateClaim(selectedClaim);
                claimTable.refresh();
                System.out.println("Claim rejected.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while rejecting claim", e);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Invalid Claim", "Only PROCESSING claims can be rejected.");
        }
    }

    private boolean isAuthorized(String action) {
        Person loggedInPerson = UserSession.getInstance().getLoggedInPerson();

        switch (action) {
            case "ADD_CLAIM":
            case "RETRIEVE_CLAIM":
                return loggedInPerson instanceof PolicyHolder || loggedInPerson instanceof PolicyOwner;
            case "UPDATE_CLAIM":
            case "DELETE_CLAIM":
                return loggedInPerson instanceof PolicyOwner;
            case "REQUIRE_MORE_INFO":
            case "PROPOSE_CLAIM":
                return loggedInPerson instanceof InsuranceSurveyor;
            case "APPROVE_CLAIM":
            case "REJECT_CLAIM":
                return loggedInPerson instanceof InsuranceManager;
            case "UPLOAD_DOCUMENT":
                return loggedInPerson instanceof PolicyHolder || loggedInPerson instanceof PolicyOwner || loggedInPerson instanceof InsuranceSurveyor || loggedInPerson instanceof InsuranceManager;
            default:
                return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
