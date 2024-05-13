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

    @ManyToOne(fetch = FetchType.LAZY)
    private PolicyOwner policyOwner;

    @OneToOne(cascade = {
            CascadeType.PERSIST, // Persist a card when persist a beneficiary
            CascadeType.REMOVE},
            mappedBy = "cardHolder",
            fetch = FetchType.LAZY
    )
    private InsuranceCard insuranceCard;

    @OneToMany
    private Set<Claim> claims = new HashSet<>();

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

    public static class Builder {
        private String email;
        private Long phoneNumber;
        private String address;
        private PolicyOwner policyOwner;
        private InsuranceCard insuranceCard;
        private Set<Claim> claims;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhoneNumber(Long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withPolicyOwner(PolicyOwner policyOwner) {
            this.policyOwner = policyOwner;
            return this;
        }

        public Builder withInsuranceCard(InsuranceCard insuranceCard) {
            this.insuranceCard = insuranceCard;
            return this;
        }

        public Builder withClaims(Set<Claim> claims) {
            this.claims = claims;
            return this;
        }

        public Beneficiary build() {
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setEmail(this.email);
            beneficiary.setPhoneNumber(this.phoneNumber);
            beneficiary.setAddress(this.address);
            beneficiary.setPolicyOwner(this.policyOwner);
            beneficiary.setInsuranceCard(this.insuranceCard);
            beneficiary.setClaims(this.claims);
            return beneficiary;
        }
    }
}
