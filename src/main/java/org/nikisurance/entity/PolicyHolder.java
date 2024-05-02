package org.nikisurance.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;
import java.util.List;
import org.hibernate.annotations.Parameter;
import org.nikisurance.util.StringPrefixedSequenceGenerator;

/**
 * @author Nguyen Ngoc Hai
 * This class represents the policy holder customers
 * Different from the previous assignment, PolicyHolder and Dependent class will not
 * inherit from an abstract class to avoid unwanted diffulites in the process of managing the database.
 */

@Entity
@Table(name = "policy_holder")
public class PolicyHolder implements Serializable {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "policy_holder_id_generator", strategy = "org.nikisurance.util.StringPrefixedSequenceGenerator",
    parameters = {
            @Parameter(name = StringPrefixedSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "c-"),
            @Parameter(name = StringPrefixedSequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%07d")
    })
    @GeneratedValue(generator = "policy_holder_id_generator")
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

    @OneToMany(mappedBy = "policyHolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Claim> claims;

    @OneToOne(mappedBy = "policyHolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InsuranceCard insuranceCard;

    @Column(name = "owner_id", nullable = false)
    private int policyOwnerId;

    @ManyToOne
    private PolicyOwner policyOwner;

    public PolicyHolder() {}

    public PolicyHolder(String customerId, String fullName, String password, String bankName, int bankNumber) {
        this.id = customerId;
        this.bankName = bankName;
        this.bankNumber = bankNumber;
    }
    @OneToMany
    private List<Dependent> dependent;

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

    public List<Dependent> getDependent() {
        return dependent;
    }

    public void setDependent(List<Dependent> dependent) {
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

    public List<Claim> getClaims() {
        return claims;
    }

    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public int getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(int policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    @Override
    public String toString() {
        return "PolicyHolder{" +
                "bankName='" + bankName + '\'' +
                ", id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", bankNumber=" + bankNumber +
                ", dependents=" + dependents +
                ", claims=" + claims +
                ", insuranceCard=" + insuranceCard +
                ", policyOwnerId=" + policyOwnerId +
                ", policyOwner=" + policyOwner +
                ", dependent=" + dependent +
                '}';
    }
}
