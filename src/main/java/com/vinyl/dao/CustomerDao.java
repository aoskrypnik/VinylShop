package com.vinyl.dao;

import com.vinyl.exception.KeyHolderException;
import com.vinyl.model.Customer;

import java.util.List;

public interface CustomerDao {

	int save(Customer customer);

	Customer getCustomerByNum(int num);

	Customer update(Customer customer);

	void deleteByNum(int num);

	List<Customer> getAll();
}
