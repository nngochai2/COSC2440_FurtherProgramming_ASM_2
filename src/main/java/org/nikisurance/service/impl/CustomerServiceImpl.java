package org.nikisurance.service.impl;

import org.nikisurance.entity.Customer;
import org.nikisurance.service.interfaces.CustomerService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class CustomerServiceImpl extends EntityRepository implements CustomerService {

    @Override
    public void addCustomer(Customer customer) {
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
    }

    @Override
    public Customer getCustomer(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        TypedQuery<Customer> query = em.createQuery("from Customer", Customer.class);
        return query.getResultList();
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = getCustomer(id);
        if (customer != null) {
            em.getTransaction().begin();
            em.remove(customer);
            em.getTransaction().commit();
        }
    }
}