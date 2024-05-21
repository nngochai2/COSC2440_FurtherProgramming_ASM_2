package org.nikisurance.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nikisurance.entity.Claim;
import org.nikisurance.service.interfaces.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class InsuranceSurveyorController implements Initializable {


    @FXML
    private TableView<Claim> claimTable;

    @FXML
    private JFXButton btnSignOut;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, String> claimDateColumn;

    @FXML
    private TableColumn<Claim, String> examDateColumn;

    @FXML
    private TableColumn<Claim, Integer> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableColumn<Claim, String> bankingInfoColumn;

    @FXML
    private TextField claimIdField;

    @FXML
    private TextField additionalInfoField;

    @FXML
    private Stage stage;

    @FXML
    private AnchorPane sideBar;
    private double x, y = 0;

    private ObservableList<Claim> claimsData;

    public InsuranceSurveyorController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            stage = (Stage) sideBar.getScene().getWindow();
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
        }
    }
}