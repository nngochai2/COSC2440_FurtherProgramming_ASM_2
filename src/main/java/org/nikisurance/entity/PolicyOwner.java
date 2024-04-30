package org.nikisurance.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Policy_Owner")
public class PolicyOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "policyOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PolicyHolder> policyHolders;

    public PolicyOwner() {}

    public PolicyOwner(Long id, String name, String password, List<PolicyHolder> policyHolders) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.policyHolders = policyHolders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PolicyHolder> getPolicyHolders() {
        return policyHolders;
    }

    public void setPolicyHolders(List<PolicyHolder> policyHolders) {
        this.policyHolders = policyHolders;
    }
}
