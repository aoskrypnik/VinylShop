package com.vinyl.service;

import com.vinyl.model.Salesman;

import java.util.List;

public interface SalesmanService {

    int save(Salesman salesman);

    void update(Salesman salesmanNew);

	void updateSalesmanByTabNum(Salesman salesman, int tabNum);

	List<Salesman> getAll();

	Salesman getSalesmanByTabNum(int tabNum);

}
