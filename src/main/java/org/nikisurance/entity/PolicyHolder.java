package org.nikisurance.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Nguyen Ngoc Hai
 * This class represents the policy holder customers
 * Different from the previous assignment, PolicyHolder and Dependent class will not
 * inherit from an abstract class to avoid unwanted diffulites in the process of managing the database.
 */

@Entity
@DiscriminatorValue("POLICYHOLDER")
@Table(name = "policy_holder")
public class PolicyHolder extends Beneficiary implements Serializable {
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "bank_number", nullable = false)
    private Long bankNumber;

    @OneToMany(mappedBy = "policyHolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dependent> dependents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private PolicyOwner policyOwner;
    public PolicyHolder() {}

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(Long bankNumber) {
        this.bankNumber = bankNumber;
    }

    public List<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public void addDependent(Dependent dependent) {
        if (dependents == null) {
            dependents = new ArrayList<>();
            dependent.setPolicyHolder(this);
            dependents.add(dependent);
        }
    }

    public void removeDependent(Dependent dependent) {
        dependents.remove(dependent);
    }

    public void addClaim(Claim claim) {
        claim.setBeneficiaryId(this.getId());
        getClaims().add(claim);
    }

    public void removeClaim(Claim claim) {
        getClaims().remove(claim);
    }

    public void addClaimForDependent(Dependent dependent, Claim claim) {
        claim.setBeneficiaryId(dependent.getId());
        dependent.getClaims().add(claim);
    }

    public static class Builder {
        private String bankName;
        private Long bankNumber;
        private List<Dependent> dependents;
        private PolicyOwner policyOwner;
        // Inherited fields
        private String email;
        private Long phoneNumber;
        private String address;
        private InsuranceCard insuranceCard;
        private Set<Claim> claims;

        public Builder withBankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        public Builder withBankNumber(Long bankNumber) {
            this.bankNumber = bankNumber;
            return this;
        }

        public Builder withDependents(List<Dependent> dependents) {
            this.dependents = dependents == null ? new ArrayList<>() : new ArrayList<>(dependents);
            return this;
        }

        public Builder withPolicyOwner(PolicyOwner policyOwner) {
            this.policyOwner = policyOwner;
            return this;
        }

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

        public Builder withInsuranceCard(InsuranceCard insuranceCard) {
            this.insuranceCard = insuranceCard;
            return this;
        }

        public Builder withClaims(Set<Claim> claims) {
            this.claims = claims;
            return this;
        }

        public PolicyHolder build() {
            if (bankName == null || bankNumber == null) {
                throw new IllegalStateException("Missing required fields: bankName and bankNumber.");
            }
            PolicyHolder policyHolder = new PolicyHolder();
            policyHolder.setBankName(this.bankName);
            policyHolder.setBankNumber(this.bankNumber);
            policyHolder.setDependents(this.dependents);
            policyHolder.setPolicyOwner(this.policyOwner);
            policyHolder.setEmail(this.email);
            policyHolder.setPhoneNumber(this.phoneNumber);
            policyHolder.setAddress(this.address);
            policyHolder.setInsuranceCard(this.insuranceCard);
            policyHolder.setClaims(this.claims);
            return policyHolder;
        }
    }

}
