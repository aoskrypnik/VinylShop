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
    public Salesman getSalesman(int tubNum) {
        return salesmanDao.getSalesmanByTabNum(tubNum);
    }

    @Override
    public List<Salesman> getAllSalesmen() {
        return salesmanDao.getAllSalesmen();
    }

    @Override
    public void save(Salesman salesman) {
        salesmanDao.save(salesman);
    }

    @Override
    public void updateSalesman(Salesman salesmanNew) {
        salesmanDao.updateSalesman(salesmanNew);
    }
}
