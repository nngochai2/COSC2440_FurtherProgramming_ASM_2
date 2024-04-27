package org.nikisurance.entity;

public class Dependent extends PolicyHolder {
    public Dependent(String customerID, String fullName, String password, InsuranceCard insuranceCard) {
        super(customerID, fullName, password, insuranceCard);
    }
}
