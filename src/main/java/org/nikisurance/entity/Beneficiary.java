package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("BENEFICIARY")
@DiscriminatorColumn(name = "beneficiary_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "beneficiary")
public class Beneficiary extends Customer implements Serializable {
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "beneficiary_type", insertable = false, updatable = false)
    private String beneficiaryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private PolicyOwner policyOwner;

    @OneToOne(cascade = {
            CascadeType.PERSIST, // Persist a card when persist a beneficiary
            CascadeType.REMOVE},
            mappedBy = "cardHolder",
            fetch = FetchType.LAZY
    )
    private InsuranceCard insuranceCard;

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Claim> claims = new HashSet<>();

    public Beneficiary() {
        setCustomerType("BENEFICIARY");
    }

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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public Set<Claim> getClaims() {
        return claims;
    }

    public void setClaims(Set<Claim> claims) {
        this.claims = claims;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }
}
