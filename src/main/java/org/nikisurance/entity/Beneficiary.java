package org.nikisurance.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Beneficiary extends Customer {
    private String email;
    private String phoneNumber;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private PolicyOwner policyOwner;

    @OneToOne
    private InsuranceCard insuranceCard;

    public Beneficiary() {}
}
