package com.vinyl.dao;

import com.vinyl.model.Salesman;

import java.util.List;

public interface SalesmanDao {

    int save(Salesman salesman);

    Salesman getSalesmanByTabNum(int tabNum);

    void update(Salesman salesmanNew);

	List<Salesman> searchSalesman(String query);
}
