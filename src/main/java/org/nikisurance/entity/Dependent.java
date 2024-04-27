package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table
public class Dependent implements Serializable, ICustomer {
    private static final long serialVersionUID = 1L;

    @Id
    private String dependentId;
    private String fullName;
    private String policyHolderId; // Foreign key referencing PolicyHolder

    @ManyToOne
    private PolicyHolder policyHolder;

    public Dependent() {}

    @Override
    public String getFullName() {
        return "";
    }

    @Override
    public void setFullName(String fullName) {

    }
}
