package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@DiscriminatorValue("InsuranceSurveyor")
public class InsuranceSurveyor extends User implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    private InsuranceSurveyor insuranceSurveyor;

    public InsuranceSurveyor() {}

    public InsuranceSurveyor(Long id, String name, String passwordHash, AdminRole role) {
        super(id, name, passwordHash, role);
    }
}
