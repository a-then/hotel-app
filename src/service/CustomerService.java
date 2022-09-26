package service;

import models.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private CustomerService() {}
    private static final CustomerService customerService = new CustomerService();
    public static CustomerService getInstance() {return customerService;}

    private final Map<String,Customer> customersList = new HashMap<>();

    public void addCustomer(String firstName, String lastName,String email){
        Customer customer = new Customer(firstName, lastName, email);
        customersList.put(email,customer);
    }
    public Customer getCustomer(String customerEmail) {
         return customersList.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customersList.values();
    }
}
