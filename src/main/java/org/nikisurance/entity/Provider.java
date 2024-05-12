package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@DiscriminatorValue("PROVIDER") // Inheritance relationship with User class
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Inherited by Insurance Manager and Surveyor
@DiscriminatorColumn(name = "provider_role")
@Table(name = "provider")
public class Provider extends User implements Serializable {
    @Column(name = "provider_role", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ProviderRole role;

    public Provider() {}

    public ProviderRole getRole() {
        return role;
    }

    public void setRole(ProviderRole role) {
        this.role = role;
    }
}
