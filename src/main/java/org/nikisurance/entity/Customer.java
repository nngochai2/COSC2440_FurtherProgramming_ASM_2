package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * @author Team 15
 */

@Entity
//@DiscriminatorValue("CUSTOMER")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "customer_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "customer")
public class Customer extends Person implements Serializable {
    @Column(name = "customer_type", insertable = false, updatable = false)
    private String customerType;

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
