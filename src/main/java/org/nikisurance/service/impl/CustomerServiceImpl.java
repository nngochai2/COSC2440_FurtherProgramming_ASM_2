package org.nikisurance.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.nikisurance.entity.Customer;
import org.nikisurance.entity.InsuranceCard;
import org.nikisurance.service.interfaces.CustomerService;

import java.util.List;

public class CustomerServiceImpl extends EntityRepository implements CustomerService {

    @Override
    public void addCustomer(Customer customer) {
        performOperation(em -> em.persist(customer));
    }

    @Override
    public Customer getCustomer(Long id) {
        return performReturningOperation(em -> em.find(Customer.class, id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return performReturningOperation(em -> em.createQuery("select c from Customer c", Customer.class).getResultList());
    }

    @Override
    public void deleteCustomer(Long id) {
        performOperation(em -> {
            Customer managedCustomer = em.merge(em.find(Customer.class, id));
            if (managedCustomer != null) {
                em.remove(managedCustomer);
            } else {
                throw new EntityNotFoundException("Customer with id " + id + " not found");
            }
        });
    }

    @Override
    public void updateCustomer(Customer customer) {
        performOperation(em -> em.merge(em.find(Customer.class, customer.getId())));
    }
}