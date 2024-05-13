package org.nikisurance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.nikisurance.entity.Claim;
import org.nikisurance.repository.impl.ClaimRepositoryImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PolicyHolderController implements Initializable {
    private ObservableList<Claim> claimsData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void populateTable() {
        getClaimFromDatabase();

    }

    private void getClaimFromDatabase() {
        ClaimRepositoryImpl claimRepository = new ClaimRepositoryImpl();
        List<Claim> claims = claimRepository.getAllClaims();
        claimsData = FXCollections.observableArrayList(claims);
        claimRepository.close();
    }
}
