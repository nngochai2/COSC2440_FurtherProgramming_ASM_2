package org.nikisurance.service.interfaces;

import org.nikisurance.entity.Customer;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);
    Customer getCustomer(Long id);
    List<Customer> getAllCustomers();
    void deleteCustomer(Long id);
    void updateCustomer(Customer customer);
}
