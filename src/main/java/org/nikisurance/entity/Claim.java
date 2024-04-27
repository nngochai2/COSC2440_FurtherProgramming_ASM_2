package org.nikisurance.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

public class Claim implements Serializable {
    private String claimID;
    private Date claimDate;
    private Customer insuredPerson;
    private int cardNumber;
    private Date examDate;
    private List<String> documents;
    private int claimAmount;
    private Status status;
    private String receiverBankingInfo;

    public Claim(String claimID, Date claimDate, Customer insuredPerson, int cardNumber, Date examDate, List<String> documents, int claimAmount, Status status, String receiverBankingInfo) {
        this.claimID = claimID;
        this.claimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingInfo = receiverBankingInfo;
    }

    public enum Status {
        NEW,
        PROCESSING,
        DONE,
    }

    public String getClaimID() {
        return claimID;
    }

    public void setClaimID(String claimID) {
        this.claimID = claimID;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public Customer getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(Customer insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public int getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReceiverBankingInfo() {
        return receiverBankingInfo;
    }

    public void setReceiverBankingInfo(String receiverBankingInfo) {
        this.receiverBankingInfo = receiverBankingInfo;
    }
}
