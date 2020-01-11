package com.vinyl.service;

import com.vinyl.model.Salesman;

import java.util.List;

public interface SalesmanService {

    Salesman getSalesman(int tubNum);

    List<Salesman> getAllSalesmen();

    void save(Salesman salesman);

    void updateSalesman(Salesman salesmanNew);
}
