package org.nikisurance.entity;

import jakarta.persistence.*;

/**
 * @author Nguyen Ngoc Hai
 * This class represents the administrator of the application
 */

@Entity
@DiscriminatorValue("Admin")
public class Admin extends Person {
    public Admin() {}

    public Admin(Long id, String name, String passwordHash, AdminRole role) {
        super(id, name, passwordHash, role);
    }
}
