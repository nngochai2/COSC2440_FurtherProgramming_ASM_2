package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@DiscriminatorValue("PROVIDER") // Inheritance relationship with Person class
@Inheritance(strategy = InheritanceType.JOINED) // Inherited by Insurance Manager and Surveyor
@DiscriminatorColumn(name = "provider_role", discriminatorType = DiscriminatorType.STRING)
@Table(name = "provider")
public class Provider extends Person implements Serializable {
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
