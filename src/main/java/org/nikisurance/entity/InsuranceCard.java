package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

@Entity
@Table(name = "Insurance_Card")
public class InsuranceCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String cardID;

    @Column(name = "card_number", unique = true)
    private int cardNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_holder_id")
    private PolicyHolder policyHolder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependent_id")
    private Dependent dependent;

    @Column(name = "issued_date", nullable = false)
    private Date issuedDate;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    public InsuranceCard() {}

    public InsuranceCard(String cardID, int cardNumber, Dependent dependent, Date expiryDate, Date issuedDate, PolicyHolder policyHolder) {
        this.cardID = cardID;
        this.cardNumber = cardNumber;
        this.dependent = dependent;
        this.expiryDate = expiryDate;
        this.issuedDate = issuedDate;
        this.policyHolder = policyHolder;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Dependent getDependent() {
        return dependent;
    }

    public void setDependent(Dependent dependent) {
        this.dependent = dependent;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }
}
