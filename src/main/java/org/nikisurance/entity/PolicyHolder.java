package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * @author Nguyen Ngoc Hai
 * This class represents the policy holder customers
 * Different from the previous assignment, PolicyHolder and Dependent class will not
 * inherit from an abstract class to avoid unwanted diffulites in the process of managing the database.
 * Instead, this class (and Dependent class) will implement the ICustomer interface.
 */

@Entity
public class PolicyHolder implements Serializable, ICustomer {
    private static final long serialVersionUID = 1L;
    @Id
    private String policyHolderID;
    private String fullName;
    private String bankName;
    private String bankNumber;

    @OneToOne(mappedBy = "policyHolder") // Mapped by refers to field in InsuranceCard
    private InsuranceCard insuranceCard;

    public String getPolicyHolderID() {
        return policyHolderID;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public void setPolicyHolderID(String policyHolderID) {
        this.policyHolderID = policyHolderID;
    }
}
