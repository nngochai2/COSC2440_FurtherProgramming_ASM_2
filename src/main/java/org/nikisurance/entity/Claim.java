package org.nikisurance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

@Entity
@Table
public class Claim implements Serializable {
    @Id
    @GenericGenerator(name = "claim_id", strategy = "/org/nikisurance/util/ClaimIdGenerator.java")
    @GeneratedValue(generator = "claim_id")
    private String claimId;
    private Date claimDate;
    private Long policyHolderId; // Foreign key
    @ManyToOne
    private PolicyHolder policyHolder;
    @Column(unique = true)
    private Date examDate;
    private String bankingInfo;
    private int claimAmount;
    private ClaimStatus status;

    public Claim() {}

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

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public String getBankingInfo() {
        return bankingInfo;
    }

    public void setBankingInfo(String bankingInfo) {
        this.bankingInfo = policyHolder.getBankName() + "-" + policyHolder.getBankNumber() + "-" + policyHolder.getFullName();
    }
}
