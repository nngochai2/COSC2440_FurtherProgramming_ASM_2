package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

@Entity
public class InsuranceCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardID;
    @Column(unique = true)
    private int cardNumber;
    private String policyHolderId; // Foreign key
    @OneToOne
    private PolicyHolder policyHolder;
    private Date issuedDate;
    private Date expiryDate;

    public InsuranceCard() {}

    public long getCardID() {
        return cardID;
    }

    public void setCardID(long cardID) {
        this.cardID = cardID;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(String policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    @Override
    public String toString() {
        return "InsuranceCard{" +
                "cardID=" + cardID +
                ", cardNumber=" + cardNumber +
                ", policyHolderId='" + policyHolderId + '\'' +
                ", policyHolder=" + policyHolder +
                ", issuedDate=" + issuedDate +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
