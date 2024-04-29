//package org.nikisurance.entity;
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.GenericGenerator;
//import org.nikisurance.util.IDGenerator;
//
//@MappedSuperclass
//public abstract class Customer {
//    @Id
//    @Column(name = "id")
//    @GenericGenerator(name = "customer_id", strategy = "/org/nikisurance/util/CustomerIdGenerator.java")
//    @GeneratedValue(generator = "customer_id")
//    private String customerId;
//    @Column(name = "full_name", nullable = false)
//    private String fullName;
//    @Column(name = "password", nullable = false)
//    private String password;
//    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private InsuranceCard insuranceCard;
//
//    public Customer(String customerId, String fullName, String password) {
//        this.customerId = IDGenerator.generateCustomerID();
//        this.fullName = fullName;
//        this.password = password;
//    }
//
//    public Customer() {}
//
//    public String getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(String customerId) {
//        this.customerId = customerId;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//
//    public InsuranceCard getInsuranceCard() {
//        return insuranceCard;
//    }
//
//    public void setInsuranceCard(InsuranceCard insuranceCard) {
//        this.insuranceCard = insuranceCard;
//    }
//}
