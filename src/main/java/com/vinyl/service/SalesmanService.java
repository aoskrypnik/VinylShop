package com.vinyl.service;

import com.vinyl.model.Salesman;

import java.util.List;

public interface SalesmanService {

    int save(Salesman salesman);

	Salesman getSalesmanByTabNum(int tabNum);

	List<Salesman> getAll();

    void update(Salesman salesmanNew);

	void updateSalesmanByTabNum(Salesman salesman, int tabNum);

}
