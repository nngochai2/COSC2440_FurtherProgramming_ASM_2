package org.nikisurance.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class InsuranceManager implements Serializable {
    private String name;
    private String password;
    private ArrayList<InsuranceSurveyor> insuranceSurveyors;
    private ArrayList<Claim> claims;
    private ArrayList<Customer> customers;

    public InsuranceManager(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<InsuranceSurveyor> getInsuranceSurveyors() {
        return insuranceSurveyors;
    }

    public void setInsuranceSurveyors(ArrayList<InsuranceSurveyor> insuranceSurveyors) {
        this.insuranceSurveyors = insuranceSurveyors;
    }

    public ArrayList<Claim> getClaims() {
        return claims;
    }

    public void setClaims(ArrayList<Claim> claims) {
        this.claims = claims;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
}
