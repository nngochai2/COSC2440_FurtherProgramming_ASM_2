package org.nikisurance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
@Table(name = "Dependent")
public class Dependent implements Serializable {
    @Id
    @GenericGenerator(name = "customer_id", strategy = "/org/nikisurance/util/CustomerIdGenerator.java")
    @GeneratedValue(generator = "customer_id")
    @Column(name = "id")
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_holder_id", nullable = false)
    private PolicyHolder policyHolder;

    public Dependent() {}

    public Dependent(String fullName, String id, String password, PolicyHolder policyHolder) {
        this.fullName = fullName;
        this.id = id;
        this.password = password;
        this.policyHolder = policyHolder;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }
}
