package org.nikisurance.repository.repoInterface;

import org.nikisurance.entity.Dependent;

import java.util.List;

public interface IDependentRepository {
    void addDependent(Dependent dependent);
    void removeDependent(Dependent dependent);
    void updateDependent(Dependent dependent);
    Dependent getDependent(int id);
    List<Dependent> getAllDependents();
}
