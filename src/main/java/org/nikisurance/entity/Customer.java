package org.nikisurance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Team 15
 */

@Entity
@Table
@PrimaryKeyJoinColumn
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
