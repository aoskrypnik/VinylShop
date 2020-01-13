package com.vinyl.service;

import com.vinyl.model.Customer;

import java.util.List;

public interface CustomerService {

	int save(Customer customer);

	Customer getCustomerByNum(int num);

	Customer update(Customer customer);

	void deleteByNum(int num);

	List<Customer> getAll();

}
