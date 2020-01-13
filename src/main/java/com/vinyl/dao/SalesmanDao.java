package com.vinyl.dao;

import com.vinyl.model.Salesman;

import java.util.List;

public interface SalesmanDao {

    int save(Salesman salesman);

    void update(Salesman salesmanNew);

    List<Salesman> getAll();

    Salesman getSalesmanByTabNum(int tabNum);

}
