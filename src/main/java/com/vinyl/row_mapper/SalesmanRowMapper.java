package com.vinyl.row_mapper;

import com.vinyl.model.Salesman;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SalesmanRowMapper implements RowMapper<Salesman> {
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
