package com.neu.dao;

import com.neu.pojo.Customer;

import java.util.ArrayList;

public interface ICustomerDao {
    boolean addCustomer(Customer customer);
    int deleteCustomer(Customer customer);
    boolean verify(Customer customer);
    ArrayList<Customer> getCustomers();
    Customer getCustomer(Customer customer);
    void setCurrentCustomer(Customer customer);
    Customer getCurrentCustomer();
    void storeCustomer();
}
