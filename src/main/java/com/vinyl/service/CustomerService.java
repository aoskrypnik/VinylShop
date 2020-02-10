package com.vinyl.service;

import com.vinyl.model.Customer;

import java.util.List;

public interface CustomerService {

	int save(Customer customer);

	Customer getCustomerByNum(int num);

	void update(Customer customer);

	void updateCustomerByNum(Customer customer, int num);

	void deleteByNum(int num);

	List<Customer> getAll();

	Short updateDiscount(int num);
}
