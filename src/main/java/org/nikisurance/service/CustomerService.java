package org.nikisurance.service;

import org.nikisurance.entity.Dependent;
import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.entity.PolicyOwner;

public class CustomerService {
    public Dependent.Builder makeDependant() {
        return new Dependent.Builder();
    }

    public PolicyHolder.Builder makePolicyHolder() {
        return new PolicyHolder.Builder();
    }

    public PolicyOwner.Builder makePolicyOwner() {
        return new PolicyOwner.Builder();
    }

}
