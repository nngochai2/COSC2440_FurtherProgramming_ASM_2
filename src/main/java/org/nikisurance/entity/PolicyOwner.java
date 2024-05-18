package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "policy_owner")
public class PolicyOwner extends Customer implements Serializable {

    @Column(name = "base_premium", nullable = false)
    private Long basePremium;

    @Column(name = "dependent_rate", nullable = false)
    private double dependentRate;

    @OneToMany(mappedBy = "policyOwner", cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Beneficiary> beneficiaries;

    public PolicyOwner() {}

    public Set<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public Long getBasePremium() {
        return basePremium;
    }

    public void setBasePremium(Long basePremium) {
        this.basePremium = basePremium;
    }

    public double getDependentRate() {
        return dependentRate;
    }

    public void setDependentRate(double dependentRate) {
        this.dependentRate = dependentRate;
    }

    public void addBeneficiary(Beneficiary beneficiary) {
        beneficiaries.add(beneficiary);
    }

    public void removeBeneficiary(Beneficiary beneficiary) {
        beneficiaries.remove(beneficiary);
    }
}
