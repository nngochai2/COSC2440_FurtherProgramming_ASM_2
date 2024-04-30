package org.nikisurance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

@Entity
@Table(name = "Claim")
public class Claim implements Serializable {
    @Id
    @GenericGenerator(name = "claim_id", strategy = "/org/nikisurance/util/ClaimIdGenerator.java")
    @GeneratedValue(generator = "claim_id")
    @Column(name = "id")
    private String claimId;

    @Column(name = "claim_date", nullable = false)
    private Date claimDate;

    @Column(name = "policy_holder_id", nullable = false)
    private Long policyHolderId; // Foreign key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_holder_id")
    private PolicyHolder policyHolder;

    @Column(name = "insured_person", nullable = false)
    private String insuredPerson;

    @Column(name = "card_number", nullable = false)
    private int cardNumber;

    @Column(name = "exam_date", nullable = false)
    private Date examDate;

    @Column(name = "claim_amount", nullable = false)
    private int claimAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ClaimStatus status;

    @Column(name = "receiver_bank_info", nullable = false)
    private String receiverBankingInfo;

    public Claim() {}

    public Claim(int cardNumber, int claimAmount, Date claimDate, String claimId, Date examDate, String insuredPerson, PolicyHolder policyHolder, Long policyHolderId, String receiverBankingInfo, ClaimStatus status) {
        this.cardNumber = cardNumber;
        this.claimAmount = claimAmount;
        this.claimDate = claimDate;
        this.claimId = claimId;
        this.examDate = examDate;
        this.insuredPerson = insuredPerson;
        this.policyHolder = policyHolder;
        this.policyHolderId = policyHolderId;
        this.receiverBankingInfo = receiverBankingInfo;
        this.status = status;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public Long getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(Long policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    public String getReceiverBankingInfo() {
        return receiverBankingInfo;
    }

    public void setReceiverBankingInfo(String receiverBankingInfo) {
        this.receiverBankingInfo = receiverBankingInfo;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }
}
