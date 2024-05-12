package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * @author Team 15
 */

@Entity
@DiscriminatorValue("CUSTOMER")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "customer_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "customer")
public class Customer extends User implements Serializable {
}
