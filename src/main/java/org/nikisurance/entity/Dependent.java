package org.nikisurance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.nikisurance.util.StringPrefixedSequenceGenerator;

import java.io.Serializable;
import java.util.List;

@Entity
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
}
