package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "policy_owner")
public class PolicyOwner extends Customer implements Serializable {

    @OneToMany(mappedBy = "policyOwner", cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Beneficiary> beneficiaries;

    public PolicyOwner() {}

    public Set<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }
}
