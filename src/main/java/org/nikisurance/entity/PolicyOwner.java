package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "policy_owner")
public class PolicyOwner extends Customer implements Serializable {

    @OneToMany(mappedBy = "policyOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Beneficiary> beneficiaries;

    public PolicyOwner() {}

    public Set<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public static class Builder extends Beneficiary.Builder {
        private PolicyHolder policyHolder;

        public Builder withPolicyHolder(PolicyHolder policyHolder) {
            this.policyHolder = policyHolder;
            return this;
        }

        public Dependent build() {
            Dependent dependent = new Dependent();
            dependent.setEmail(this.email);
            dependent.setPhoneNumber(this.phoneNumber);
            dependent.setAddress(this.address);
            dependent.setPolicyOwner(this.policyOwner);
            dependent.setInsuranceCard(this.insuranceCard);
            dependent.setClaims(this.claims);
            dependent.setPolicyHolder(this.policyHolder);
            return dependent;
        }
    }
}
