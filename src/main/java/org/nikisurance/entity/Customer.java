package org.nikisurance.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Team 15
 */

@Entity
@DiscriminatorValue("CUSTOMER")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "customer_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "customer")
public class Customer extends User {
    // Relationship with claims
    @OneToMany
    private Set<Claim> claims = new HashSet<>();

    public Set<Claim> getClaims() {
        return claims;
    }

    public void setClaims(Set<Claim> claims) {
        this.claims = claims;
    }
}
