package org.nikisurance.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@DiscriminatorValue("dependent")
public class Dependent extends Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    public Dependent() {}

    public Dependent(String customerID, String fullName, String password) {
        super(customerID, fullName, password);
    }
}
