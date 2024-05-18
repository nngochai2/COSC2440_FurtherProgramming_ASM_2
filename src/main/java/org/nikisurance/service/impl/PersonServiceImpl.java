package org.nikisurance.service.impl;

import org.nikisurance.entity.Person;
import org.nikisurance.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

public class PersonServiceImpl extends EntityRepository implements PersonService {

    @Override
    public void addPerson(Person person) {
        performOperation(em -> em.persist(person));
    }

    @Override
    public Person getPerson(Long id) {
        return performReturningOperation(em -> em.find(Person.class, id));
    }

    @Override
    public List<Person> getAllPersons() {
        return performReturningOperation(em -> em.createQuery("from Person").getResultList());
    }

    @Override
    public void deletePerson(Long id) {
        performOperation(em -> {
            Person person = em.find(Person.class, id);
            if (person != null) {
                em.remove(person);
            } else {
                throw new IllegalArgumentException("No person with id " + id + " exists");
            }
        });
    }

    @Override
    public void updatePerson(Person person) {
        performOperation(em -> em.merge(person));
    }
}