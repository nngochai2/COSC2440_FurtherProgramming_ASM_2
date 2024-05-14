package org.nikisurance.service.interfaces;

import org.nikisurance.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    Customer getCustomer(Long id);
    List<Customer> getAllCustomers();
    void deleteCustomer(Long id);
}
