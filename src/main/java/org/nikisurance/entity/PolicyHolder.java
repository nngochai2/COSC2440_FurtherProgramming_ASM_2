package org.nikisurance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.nikisurance.util.IDGenerator;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nguyen Ngoc Hai
 * This class represents the policy holder customers
 * Different from the previous assignment, PolicyHolder and Dependent class will not
 * inherit from an abstract class to avoid unwanted diffulites in the process of managing the database.
 * Instead, this class (and Dependent class) will implement the ICustomer interface.
 */

@Entity
@Table(name = "Policy_Holder")
public class PolicyHolder implements Serializable {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "customer_id", strategy = "/org/nikisurance/util/CustomerIdGenerator.java")
    @GeneratedValue(generator = "customer_id")
    private String id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "bank_number", nullable = false)
    private int bankNumber;

    @OneToMany(mappedBy = "policyHolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dependent> dependents;

    @OneToOne(mappedBy = "policyHolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InsuranceCard insuranceCard;

    public PolicyHolder(String customerId, String fullName, String password, String bankName, int bankNumber) {
        this.id = customerId;
        this.bankName = bankName;
        this.bankNumber = bankNumber;
    }
    @ManyToOne
    private Dependent dependent;

    public PolicyHolder() {}

    public int getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(int bandNumber) {
        this.bankNumber = bandNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Dependent getDependent() {
        return dependent;
    }

    public void setDependent(Dependent dependent) {
        this.dependent = dependent;
    }

    public List<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
