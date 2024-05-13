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

    @Override
    public String toString() {
        return cardID.toString();
    }

    public static class Builder {
        private Long cardID;
        private Beneficiary cardHolder;
        private Date issuedDate;
        private Date expiryDate;

        public Builder withCardID(Long cardID) {
            this.cardID = cardID;
            return this;
        }

        public Builder withCardHolder(Beneficiary cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public Builder withIssuedDate(Date issuedDate) {
            this.issuedDate = issuedDate;
            return this;
        }

        public Builder withExpiryDate(Date expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public InsuranceCard build() {
            if (issuedDate == null || expiryDate == null) {
                throw new IllegalStateException("Missing required fields.");
            }
            InsuranceCard card = new InsuranceCard();
            card.cardID = this.cardID;
            card.cardHolder = this.cardHolder;
            card.issuedDate = this.issuedDate;
            card.expiryDate = this.expiryDate;
            return card;
        }
    }

}
