package org.nikisurance.service.impl;

import org.nikisurance.entity.Dependent;
import org.nikisurance.service.interfaces.DependentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class DependentServiceImpl implements DependentService {

    @Override
    public Dependent addDependent(Dependent dependent) {
        return null;
    }

    @Override
    public Dependent getDependent(Long id) {
        return null;
    }

    @Override
    public List<Dependent> getAllDependents() {
        return List.of();
    }

    @Override
    public void deleteDependent(Long id) {

    }

    @Override
    public Dependent updateDependent(Dependent dependent) {
        return null;
    }
}
