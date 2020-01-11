package com.vinyl.dao;

import com.vinyl.model.Salesman;

import java.util.List;

public interface SalesmanDao {

    Salesman getSalesmanByTabNum(int tabNum);

    List<Salesman> getAllSalesmen();

    void save(Salesman salesman);

    void updateSalesman(Salesman salesmanNew);
}
