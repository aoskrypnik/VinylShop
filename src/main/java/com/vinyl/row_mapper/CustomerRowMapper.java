package com.vinyl.row_mapper;

import com.vinyl.model.Customer;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Value("${sql.customer.get.phone.numbers.by.customer.query.path}")
	private String getCustomerPhoneNumbersQueryPath;

	@Override
	public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
		String getCustomerPhoneNumbersQuery = QuerySupplier.getQuery(getCustomerPhoneNumbersQueryPath);
		int customerNum = resultSet.getInt("customer_num");
		List<String> phoneNumbers = jdbcTemplate.queryForList(getCustomerPhoneNumbersQuery, new Object[]{customerNum}, String.class);
		return Customer.builder()
				.customerNum(customerNum)
				.customerName(resultSet.getString("customer_name"))
				.customerEmail(resultSet.getString("email"))
				.customerDiscount(resultSet.getShort("discount"))
				.customerPhoneNumbers(phoneNumbers)
				.build();
	}
}
