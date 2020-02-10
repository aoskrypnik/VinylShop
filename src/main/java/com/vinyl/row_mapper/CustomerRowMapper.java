package com.vinyl.row_mapper;

import com.vinyl.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
	@Override
	public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
		return Customer.builder()
				.num(resultSet.getInt("customer_num"))
				.name(resultSet.getString("customer_name"))
				.email(resultSet.getString("email"))
				.discount(resultSet.getShort("discount"))
				.build();
	}
}
