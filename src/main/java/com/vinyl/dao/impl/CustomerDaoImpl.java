package com.vinyl.dao.impl;

import com.vinyl.dao.CustomerDao;
import com.vinyl.exception.KeyHolderException;
import com.vinyl.model.Customer;
import com.vinyl.utils.KeyHolderUtils;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomerDaoImpl implements CustomerDao, RowMapper<Customer> {

	@Value("${sql.create.customer.query.path}")
	private String createCustomerQueryPath;
	@Value("${sql.get.customer.by.num.query.path}")
	private String getCustomerByNumQueryPath;
	@Value("${sql.update.customer.query.path}")
	private String updateCustomerQueryPath;
	@Value("{sql.delete.customer.query.path}")
	private String deleteCustomerQueryPath;
	@Value("${sql.get.all.customers.query.path}")
	private String getAllCustomersQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(Customer customer) {
		String createCustomerQuery = QuerySupplier.getQuery(createCustomerQueryPath);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(createCustomerQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getName());
			ps.setString(2, customer.getEmail());
			ps.setInt(3, customer.getDiscount());
			return ps;
		}, keyHolder);

		return KeyHolderUtils.extractInt(keyHolder, "customer_num");
	}

	@Override
	public Customer getCustomerByNum(int num) {
		String getGetCustomerByNumQuery = QuerySupplier.getQuery(getCustomerByNumQueryPath);
		List<Customer> customers = jdbcTemplate.query(getGetCustomerByNumQuery, new Object[]{num}, this);
		return customers.size() == 0 ? null : customers.get(0);
	}

	@Override
	public Customer update(Customer customer) {
		String updateCustomerQuery = QuerySupplier.getQuery(updateCustomerQueryPath);

		int num = customer.getNum();
		String email = customer.getEmail();
		int discount = customer.getDiscount();

		List<Customer> customers = jdbcTemplate.query(updateCustomerQuery, new Object[]{email, discount, num}, this);
		return customers.size() == 0 ? null : customers.get(0);
	}

	@Override
	public void deleteByNum(int num) {
		String deleteCustomerQuery = QuerySupplier.getQuery(deleteCustomerQueryPath);

		jdbcTemplate.update(deleteCustomerQuery, num);
	}

	@Override
	public List<Customer> getAll() {
		String getAllCustomersQuery = QuerySupplier.getQuery(getAllCustomersQueryPath);
		return jdbcTemplate.query(getAllCustomersQuery, this);
	}

	@Override
	public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
		return Customer.builder()
				.num(resultSet.getInt("customer_num"))
				.name(resultSet.getString("customer_name"))
				.email(resultSet.getString("email"))
				.discount(resultSet.getInt("discount"))
				.build();
	}

}
