package org.nikisurance.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@DiscriminatorValue("policy_holder")
public class PolicyHolder extends Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    public PolicyHolder() {}

    public PolicyHolder(String customerID, String fullName, String password) {
        super(customerID, fullName, password);
    }
}
