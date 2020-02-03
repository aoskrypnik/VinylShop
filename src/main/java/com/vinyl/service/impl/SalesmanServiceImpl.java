package com.vinyl.service.impl;

import com.vinyl.dao.SalesmanDao;
import com.vinyl.model.Salesman;
import com.vinyl.service.SalesmanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SalesmanServiceImpl implements SalesmanService {

	@Resource
	private SalesmanDao salesmanDao;

	@Override
	public Salesman getSalesmanByTabNum(int tubNum) {
		return salesmanDao.getSalesmanByTabNum(tubNum);
	}

	@Override
	public List<Salesman> getAll() {
		return salesmanDao.getAll();
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

}
