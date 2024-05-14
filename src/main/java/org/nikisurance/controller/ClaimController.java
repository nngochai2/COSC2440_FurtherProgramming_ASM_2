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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.service.interfaces.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Controller
public class ClaimController implements Initializable {

    private static final String UPLOAD_DIR = "";

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

    protected ObservableList<Claim> claimsData;
    protected FilteredList<Claim> filteredClaims;
    protected SortedList<Claim> sortedClaims;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeClaimTable();
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
    private void handleUploadButtonClicked(ActionEvent event) {
        // Open file chooser dialog to select image files
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null) {
            for (File file : selectedFiles) {

            }
        }
    }

    private void uploadFile(File file, String claimId) {
        String newFileName = this.generateFileName(file.getName(), claimId);

        File renamedFile = new File(file.getParentFile(), newFileName);
        boolean success = file.renameTo(renamedFile);

        if (success) {

        } else {

        }
    }

    private String generateFileName(String originalFilename, String claimId) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return claimId + "_" + timeStamp + "_" + originalFilename;
    }
}
