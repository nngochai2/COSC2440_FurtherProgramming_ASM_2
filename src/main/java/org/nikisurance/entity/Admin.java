package org.nikisurance.entity;

import jakarta.persistence.*;

/**
 * @author Nguyen Ngoc Hai
 * This class represents the administrator of the application
 */

@Entity
@DiscriminatorValue("ADMIN")
@Table(name = "admin")
public class Admin extends User {
    public Admin() {}
}
