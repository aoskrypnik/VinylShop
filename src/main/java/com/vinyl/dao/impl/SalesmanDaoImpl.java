package com.vinyl.dao.impl;

import com.vinyl.dao.SalesmanDao;
import com.vinyl.model.Salesman;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SalesmanDaoImpl implements SalesmanDao, RowMapper<Salesman> {

    @Value("${sql.get.all.salesmen.query.path}")
    private String getGetAllSalesmenQueryPath;
    @Value("${sql.get.salesman.by.tab.num.query.path}")
    private String getGetSalesmanByTabNumQueryPath;
    @Value("${sql.create.salesman.query.path}")
    private String createSalesmanQueryPath;
    @Value("${sql.update.salesman.query.path}")
    private String updateSalesmanQueryPath;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Salesman getSalesmanByTabNum(int tabNum) {
        String getGetSalesmanByTabNumQuery = QuerySupplier.getQuery(getGetSalesmanByTabNumQueryPath);
        return jdbcTemplate.queryForObject(getGetSalesmanByTabNumQuery, new Object[]{tabNum}, this);
    }

    @Override
    public List<Salesman> getAllSalesmen() {
        String getGetAllSalesmenQuery = QuerySupplier.getQuery(getGetAllSalesmenQueryPath);
        return jdbcTemplate.query(getGetAllSalesmenQuery, this);
    }

    @Override
    public void save(Salesman salesman) {
        String createSalesmanQuery = QuerySupplier.getQuery(createSalesmanQueryPath);

        String name = salesman.getName();
        String passportNum = salesman.getPassportNum();
        String addressCity = salesman.getAddressCity();
        String addressStr = salesman.getAddressStr();
        String addressHome = salesman.getAddressHome();
        Integer addressApt = salesman.getAddressApt();
        String phoneNum = salesman.getPhoneNum();
        Date worksFrom = salesman.getWorksFrom();
        Date worksTo = salesman.getWorksTo();
        Integer salary = salesman.getSalary();
        String login = null;

        jdbcTemplate.update(createSalesmanQuery, name, passportNum, addressCity,
                addressStr, addressHome, addressApt, phoneNum, worksFrom, worksTo, salary, login);
    }

    @Override
    public void updateSalesman(Salesman salesmanNew) {
        String updateSalesmanQuery = QuerySupplier.getQuery(updateSalesmanQueryPath);
        jdbcTemplate.update(updateSalesmanQuery, salesmanNew.getAddressCity(), salesmanNew.getAddressStr(),
                salesmanNew.getAddressHome(), salesmanNew.getAddressApt(), salesmanNew.getPhoneNum(),
                salesmanNew.getWorksTo(), salesmanNew.getSalary(), salesmanNew.getTabNum());
    }


    @Override
    public Salesman mapRow(ResultSet resultSet, int i) throws SQLException {
        return Salesman.builder()
                .tabNum(resultSet.getInt("tab_num"))
                .name(resultSet.getString("salesman_name"))
                .passportNum(resultSet.getString("passport_num"))
                .addressCity(resultSet.getString("address_city"))
                .addressStr(resultSet.getString("address_str"))
                .addressHome(resultSet.getString("address_home"))
                .addressApt(resultSet.getInt("address_apt"))
                .phoneNum(resultSet.getString("phone_num"))
                .worksFrom(resultSet.getDate("works_from"))
                .worksTo(resultSet.getDate("works_to"))
                .salary(resultSet.getInt("salary"))
                .login(resultSet.getString("login"))
                .build();
    }
}
