package org.nikisurance.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Beneficiary extends Customer {
    private String email;
    private String phoneNumber;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private PolicyOwner policyOwner;

    @OneToOne(cascade = {
            CascadeType.PERSIST, // Persist a card when persist a beneficiary
            CascadeType.REMOVE},
            mappedBy = "cardHolder",
            fetch = FetchType.LAZY
    )
    private InsuranceCard insuranceCard;

    public Beneficiary() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }
}
