package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

@Entity
@Table(name = "insurance_card")
public class InsuranceCard implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_id_generator")
    @SequenceGenerator(name = "card_id_generator", sequenceName = "card_id_sequence", allocationSize = 1)
    private Long cardID;

    @OneToOne
    private Beneficiary cardHolder;

    @Column(name = "issued_date", nullable = false)
    private Date issuedDate;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;


    public InsuranceCard() {}

    public Long getCardID() {
        return cardID;
    }

    public void setCardID(Long cardID) {
        this.cardID = cardID;
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

    public Beneficiary getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Beneficiary cardHolder) {
        this.cardHolder = cardHolder;
    }

    @Override
    public String toString() {
        return cardID.toString();
    }
}
