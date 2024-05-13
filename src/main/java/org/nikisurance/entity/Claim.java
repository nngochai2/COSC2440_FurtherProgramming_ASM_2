package org.nikisurance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.nikisurance.util.ClaimIdGenerator;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

@Entity
@Table(name = "claim")
public class Claim implements Serializable {
    @Id
    @GenericGenerator(
            name = "claim_id_generator",
            strategy = "org.nikisurance.util.ClaimIdGenerator",
            parameters = {
                    @Parameter(name = ClaimIdGenerator.VALUE_PREFIX_PARAMETER, value = "f-"),
                    @Parameter(name = ClaimIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "claim_id")
    @Column(name = "id")
    private String claimId;

    @Column(name = "claim_date", nullable = false)
    private Date claimDate;

    @Column(name = "beneficiary_id",nullable = false)
    private Long beneficiaryId; // Foreign key

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

    @Column(name = "surveyor_id")
    private Long surveyorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyor_id", insertable = false, updatable = false)
    private InsuranceSurveyor insuranceSurveyor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id", insertable = false, updatable = false)
    private Beneficiary beneficiary;

    public Claim() {}

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

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getReceiverBankingInfo() {
        return receiverBankingInfo;
    }

    public void setReceiverBankingInfo(Beneficiary beneficiary, String receiverBankingInfo) {
        if (beneficiary != null) {
            if (beneficiary instanceof PolicyHolder) {
                receiverBankingInfo = ((PolicyHolder) beneficiary).getBankName() + ((PolicyHolder) beneficiary).getBankNumber() + beneficiary.getFullName().toUpperCase();
            } else if (beneficiary instanceof Dependent) {
                receiverBankingInfo = ((Dependent) beneficiary).getPolicyHolder().getBankName() + ((Dependent) beneficiary).getPolicyHolder().getBankName() + ((Dependent) beneficiary).getPolicyHolder().getFullName().toUpperCase();
            }
        }
        this.receiverBankingInfo = receiverBankingInfo;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public void setInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Long getSurveyorId() {
        return surveyorId;
    }

    public void setSurveyorId(Long surveyorId) {
        this.surveyorId = surveyorId;
    }

    public static class Builder {
        private String claimId;
        private Date claimDate;
        private Long beneficiaryId;
        private String insuredPerson;
        private int cardNumber;
        private Date examDate;
        private int claimAmount;
        private ClaimStatus status;
        private String receiverBankingInfo;
        private Long surveyorId;
        private InsuranceSurveyor insuranceSurveyor;
        private Beneficiary beneficiary;

        public Builder withClaimId(String claimId) {
            this.claimId = claimId;
            return this;
        }

        public Builder withClaimDate(Date claimDate) {
            this.claimDate = claimDate;
            return this;
        }

        public Builder withInsuredPerson(String insuredPerson) {
            this.insuredPerson = insuredPerson;
            return this;
        }

        public Builder withCardNumber(int cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder withExamDate(Date examDate) {
            this.examDate = examDate;
            return this;
        }

        public Builder withClaimAmount(int claimAmount) {
            this.claimAmount = claimAmount;
            return this;
        }

        public Builder withStatus(ClaimStatus status) {
            this.status = status;
            return this;
        }

        public Builder withReceiverBankingInfo(String receiverBankingInfo) {
            this.receiverBankingInfo = receiverBankingInfo;
            return this;
        }

        public Builder withSurveyorId(Long surveyorId) {
            this.surveyorId = surveyorId;
            return this;
        }

        public Builder withInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
            this.insuranceSurveyor = insuranceSurveyor;
            return this;
        }

        public Builder withBeneficiary(Beneficiary beneficiary) {
            this.beneficiary = beneficiary;
            return this;
        }

        public Claim build() {
            if (claimDate == null || claimAmount <= 0 || insuredPerson == null || claimId == null || status == null) {
                throw new IllegalStateException("Missing required fields or field conditions not met.");
            }
            Claim claim = new Claim();
            claim.claimId = this.claimId;
            claim.claimDate = this.claimDate;
            claim.beneficiaryId = this.beneficiaryId;
            claim.insuredPerson = this.insuredPerson;
            claim.cardNumber = this.cardNumber;
            claim.examDate = this.examDate;
            claim.claimAmount = this.claimAmount;
            claim.status = this.status;
            claim.receiverBankingInfo = this.receiverBankingInfo;
            claim.surveyorId = this.surveyorId;
            claim.insuranceSurveyor = this.insuranceSurveyor;
            claim.beneficiary = this.beneficiary;
            return claim;
        }
    }
}

