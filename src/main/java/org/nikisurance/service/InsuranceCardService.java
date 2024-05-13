package org.nikisurance.service;

import org.nikisurance.entity.InsuranceCard;

public class InsuranceCardService {
    public InsuranceCard.Builder makeCard() {
        return new InsuranceCard.Builder();
    }
}
