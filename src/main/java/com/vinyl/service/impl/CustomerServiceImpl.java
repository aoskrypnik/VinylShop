package com.vinyl.service.impl;

import com.vinyl.dao.CustomerDao;
import com.vinyl.model.Customer;
import com.vinyl.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Integer SUM_10000 = 1000000;
	private static final Integer SUM_30000 = 3000000;
	private static final Short DISCOUNT_0 = 0;
	private static final Short DISCOUNT_5 = 5;
	private static final Short DISCOUNT_10 = 10;

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
	public void update(Customer customer) {
		customerDao.update(customer);
	}

	@Override
	public void updateCustomerByNum(Customer customer, int num) {
		customer.setNum(num);
		update(customer);
	}

	@Override
	public void deleteByNum(int num) {
		customerDao.deleteByNum(num);
	}

	@Override
	public List<Customer> getAll() {
		return customerDao.getAll();
	}

	@Override
	public Short updateDiscount(int num) {
		short discount;
		Double sumForAllPurchases = customerDao.getSumForAllPurchases(num);
		if (sumForAllPurchases < SUM_10000) {
			discount = DISCOUNT_0;
		} else if (SUM_10000 < sumForAllPurchases && sumForAllPurchases < SUM_30000) {
			discount = DISCOUNT_5;
		} else {
			discount = DISCOUNT_10;
		}
		return customerDao.updateDiscount(num, discount);
	}

}
