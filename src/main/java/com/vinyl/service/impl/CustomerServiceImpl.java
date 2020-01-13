package com.vinyl.service.impl;

import com.vinyl.dao.CustomerDao;
import com.vinyl.model.Customer;
import com.vinyl.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;

	@Override
	public int save(Customer customer) {
		return customerDao.save(customer);
	}

	@Override
	public Customer getCustomerByNum(int num) {
		return customerDao.getCustomerByNum(num);
	}

	@Override
	public Customer update(Customer customer) {
		return customerDao.update(customer);
	}

	@Override
	public void deleteByNum(int num) {
		customerDao.deleteByNum(num);
	}

	@Override
	public List<Customer> getAll() {
		return customerDao.getAll();
	}

}
