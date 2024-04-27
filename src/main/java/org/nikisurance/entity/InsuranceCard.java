package org.nikisurance.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

public class InsuranceCard implements Serializable {
    private int cardNumber;
    private PolicyHolder policyHolder;
    private Customer cardHolder; // Every customer has their one and only insurance card
    private String policyOwner; // This is the one who provide the insurance policies
    private Date expirationDate;

    public InsuranceCard(int cardNumber, PolicyHolder policyHolder, Customer cardHolder, String policyOwner, Date expirationDate) {
        this.cardNumber = cardNumber;
        this.policyHolder = policyHolder;
        this.cardHolder = cardHolder;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public Customer getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Customer cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(String policyOwner) {
        this.policyOwner = policyOwner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "InsuranceCard{" +
                "cardNumber=" + cardNumber +
                ", policyHolder=" + policyHolder +
                ", cardHolder=" + cardHolder +
                ", policyOwner='" + policyOwner + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
