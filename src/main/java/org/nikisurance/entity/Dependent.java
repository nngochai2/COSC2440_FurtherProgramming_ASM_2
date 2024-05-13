package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@DiscriminatorValue("DEPENDENT")
@Table(name = "dependent")
public class Dependent extends Beneficiary implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_holder_id", nullable = false)
    private PolicyHolder policyHolder;

    public Dependent() {}

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
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
