package com.vinyl.dao;

import com.vinyl.model.Customer;

import java.util.List;

public interface CustomerDao {

	int save(Customer customer);

	Customer getCustomerByNum(int num);

	void update(Customer customer);

	void deleteByNum(int num);

	void updateDiscount(int num, short discount);

	Integer getSumForAllPurchases(int num);

	List<Customer> searchCustomer(String query);
}
