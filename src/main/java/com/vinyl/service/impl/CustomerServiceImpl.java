package com.vinyl.service.impl;

import com.vinyl.dao.CustomerDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.model.Customer;
import com.vinyl.service.CustomerService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final String CUSTOMER_TABLE_NAME = "customer";
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
		Integer sumOfAllPurchases = customerDao.getSumForAllPurchases(num);
		Customer foundCustomer = customerDao.getCustomerByNum(num);
		foundCustomer.setSumOfAllPurchases(sumOfAllPurchases);
		return foundCustomer;
	}

	@Override
	public void update(Customer customer) {
		customerDao.update(customer);
	}

	@Override
	public void updateCustomerByNum(Customer customer, int num) {
		customer.setCustomerNum(num);
		update(customer);
	}

	@Override
	public void deleteByNum(int num) {
		customerDao.deleteByNum(num);
	}

	@Override
	public void updateDiscount(int num) {
		short discount;
		Integer sumForAllPurchases = customerDao.getSumForAllPurchases(num);
		if (sumForAllPurchases < SUM_10000) {
			discount = DISCOUNT_0;
		} else if (SUM_10000 < sumForAllPurchases && sumForAllPurchases < SUM_30000) {
			discount = DISCOUNT_5;
		} else {
			discount = DISCOUNT_10;
		}
		customerDao.updateDiscount(num, discount);
	}

	@Override
	public List<Customer> searchCustomer(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWhereParams(), searchDto.getLikeParams(), searchDto.getBetweenParams(),
						searchDto.getJoins(), searchDto.getSorting(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), CUSTOMER_TABLE_NAME);
		return customerDao.searchCustomer(query);
	}

}
