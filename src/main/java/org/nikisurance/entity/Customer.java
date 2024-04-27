package org.nikisurance.entity;

import jakarta.persistence.*;

@Entity

// Enable single table inheritance for this class and its subclasses
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

// Define the column "discriminator" to differentiate subclasses in the single table
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@Table(name = "customer")
public abstract class Customer {
    private static int lastAssignedID = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "CUSTOMER_ID_SEQ")
    @Column(name = "id")
    private String customerID;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "password")
    private String password;

    private String discriminator;

    public Customer() {}

    public Customer(String customerID, String fullName, String password) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.password = password;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }


    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static int getLastAssignedID() {
        return lastAssignedID;
    }

    public static void setLastAssignedID(int lastAssignedID) {
        Customer.lastAssignedID = lastAssignedID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID='" + customerID + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
