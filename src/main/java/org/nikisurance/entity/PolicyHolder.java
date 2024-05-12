package org.nikisurance.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nguyen Ngoc Hai
 * This class represents the policy holder customers
 * Different from the previous assignment, PolicyHolder and Dependent class will not
 * inherit from an abstract class to avoid unwanted diffulites in the process of managing the database.
 */

@Entity
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
}
