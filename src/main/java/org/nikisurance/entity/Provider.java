package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_role")
@Table(name = "provider")
public class Provider extends User implements Serializable {
    @Column(name = "person_role", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ProviderRole role;

    public Provider() {}
}
