package com.vinyl.service;

import com.vinyl.model.Salesman;

import java.util.List;

public interface SalesmanService {

    int save(Salesman salesman);

	Salesman getSalesmanByTabNum(int tabNum);

    void update(Salesman salesmanNew);

	void updateSalesmanByTabNum(Salesman salesman, int tabNum);

	List<Salesman> searchSalesman(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
								  List<String> joins, String sorting, String order, Integer limit, Integer offset);
}
