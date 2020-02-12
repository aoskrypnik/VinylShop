package com.vinyl.service.impl;

import com.vinyl.dao.CheckDao;
import com.vinyl.model.Check;
import com.vinyl.service.CheckService;
import com.vinyl.service.CustomerService;
import com.vinyl.service.RecordService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class CheckServiceImpl implements CheckService {

	private static final String CHECK_TABLE_NAME = "cheq";

	@Resource
	private CustomerService customerService;
	@Resource
	private RecordService recordService;
	@Resource
	private CheckDao checkDao;

	@Override
	public Check getByNum(Integer num) {
		return checkDao.getByNum(num);
	}

	@Override
	public Integer save(Check check) {
		populateCheckWithOverallSumAndDiscount(check);
		Integer checkNum = checkDao.save(check);
		updateDiscountForCustomer(check);
		return checkNum;
	}

	private void updateDiscountForCustomer(Check check) {
		Integer customerNum = check.getCustomerNum();
		if (nonNull(customerNum)) {
			customerService.updateDiscount(customerNum);
		}
	}

	private void populateCheckWithOverallSumAndDiscount(Check check) {
		Integer registeredCustomerNum = check.getCustomerNum();
		if (isNull(registeredCustomerNum)) {
			check.setCheckDiscount((short) 0);
		} else {
			short checkDiscount = customerService.getCustomerByNum(registeredCustomerNum).getDiscount();
			check.setCheckDiscount(checkDiscount);
		}
		int overallSum = countCheckOverallSum(check.getProductBarcodes());
		check.setOverallSum(overallSum);
	}

	private int countCheckOverallSum(List<String> productBarcodes) {
		return productBarcodes.stream()
				.map(barcode -> recordService.getByBarcode(barcode).getSellPrice())
				.mapToInt(Integer::intValue)
				.sum();
	}

	@Override
	public List<Check> searchChecks(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
									List<String> joins, String sorting, String order, Integer limit, Integer offset) {
		String query = QueryBuilder
				.build(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset, CHECK_TABLE_NAME);
		return checkDao.searchChecks(query);
	}
}
