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

    @Column(nullable = false)
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

    @ManyToOne
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
}
