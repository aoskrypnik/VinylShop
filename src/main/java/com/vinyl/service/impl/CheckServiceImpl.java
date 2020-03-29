package com.vinyl.service.impl;

import com.vinyl.dao.CheckDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.model.Check;
import com.vinyl.service.CheckService;
import com.vinyl.service.CustomerService;
import com.vinyl.service.RecordService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
		populateCheckProperties(check);
		Integer checkNum = checkDao.save(check);
		updateDiscountForCustomer(check);
		return checkNum;
	}

	@Override
	public List<Check> searchChecks(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), CHECK_TABLE_NAME);
		return checkDao.searchChecks(query);
	}

	@Override
	public boolean allRecordsAvailable(List<String> recordBarcodes) {
		return recordBarcodes.stream()
				.noneMatch(barcode -> recordService.recordIsSold(barcode));
	}

	private void setCheckDiscount(Check check) {
		Integer registeredCustomerNum = check.getCustomerNum();
		short checkDiscount = 0;
		if (nonNull(registeredCustomerNum)) {
			checkDiscount = customerService.getCustomerByNum(registeredCustomerNum).getCustomerDiscount();
		}
		check.setCheckDiscount(checkDiscount);
	}

	private void setCheckOverallSum(Check check) {
		int overallSum = check.getProductBarcodes().stream()
				.map(barcode -> recordService.getByBarcode(barcode).getSellPrice())
				.mapToInt(Integer::intValue)
				.sum();
		check.setOverallSum(overallSum);
	}

	private void setCheckSumWithDiscount(Check check, int overallSum, short discount) {
		int sumWithDiscount = (int) Math.floor(overallSum * ((100 - discount) * 0.01));
		check.setSumWithDiscount(sumWithDiscount);
	}

	private void updateDiscountForCustomer(Check check) {
		Integer customerNum = check.getCustomerNum();
		if (nonNull(customerNum)) {
			customerService.updateDiscount(customerNum);
		}
	}

	private void populateCheckProperties(Check check) {
		setCheckDiscount(check);
		setCheckOverallSum(check);
		setCheckSumWithDiscount(check, check.getOverallSum(), check.getCheckDiscount());
	}

}
