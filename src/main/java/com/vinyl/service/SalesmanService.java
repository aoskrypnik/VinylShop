package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.model.Salesman;

import java.util.List;

public interface SalesmanService {

	int save(Salesman salesman);

	Salesman getSalesmanByTabNum(int tabNum);

	void update(Salesman salesmanNew);

	void updateSalesmanByTabNum(Salesman salesman, int tabNum);

	List<Salesman> searchSalesman(SearchDto searchDto);
}
