package com.vinyl.service.impl;

import com.vinyl.dao.SalesmanDao;
import com.vinyl.model.Salesman;
import com.vinyl.service.SalesmanService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SalesmanServiceImpl implements SalesmanService {

	private static final String SALESMAN_TABLE_NAME = "salesman";

	@Resource
	private SalesmanDao salesmanDao;

	@Override
	public Salesman getSalesmanByTabNum(int tubNum) {
		return salesmanDao.getSalesmanByTabNum(tubNum);
	}

	@Override
	public int save(Salesman salesman) {
		return salesmanDao.save(salesman);
	}

	@Override
	public void update(Salesman salesmanNew) {
		salesmanDao.update(salesmanNew);
	}

	@Override
	public void updateSalesmanByTabNum(Salesman salesman, int tabNum) {
		salesman.setTabNum(tabNum);
		update(salesman);
	}

	@Override
	public List<Salesman> searchSalesman(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
										 List<String> joins, String sorting, String order, Integer limit, Integer offset) {
		String query = QueryBuilder
				.build(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset, SALESMAN_TABLE_NAME);
		return salesmanDao.searchSalesman(query);
	}

}
