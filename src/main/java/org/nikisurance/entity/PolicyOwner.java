package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "policy_owner")
public class PolicyOwner extends Customer implements Serializable {

    @OneToMany(mappedBy = "policyOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PolicyHolder> policyHolders;

    public PolicyOwner() {}
    public List<PolicyHolder> getPolicyHolders() {
        return policyHolders;
    }

    public void setPolicyHolders(List<PolicyHolder> policyHolders) {
        this.policyHolders = policyHolders;
    }

    public void addPolicyHolder(PolicyHolder policyHolder) {
        policyHolders.add(policyHolder);
    }

    public void removePolicyHolder(PolicyHolder policyHolder) {
        policyHolders.remove(policyHolder);
    }
}
