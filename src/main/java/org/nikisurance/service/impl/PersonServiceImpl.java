package org.nikisurance.service.impl;

import org.nikisurance.entity.Person;
import org.nikisurance.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.persistence.TypedQuery;

@Service
public class PersonServiceImpl extends EntityRepository implements PersonService {

    @Override
    public void addPerson(Person person) {
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    @Override
    public Person getPerson(Long id) {
        return em.find(Person.class, id);
    }

    @Override
    public List<Person> getAllPersons() {
        TypedQuery<Person> query = em.createQuery("from Person", Person.class);
        return query.getResultList();
    }

    @Override
    public void deletePerson(Long id) {
        Person person = getPerson(id);
        if (person != null) {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
        }
    }
}