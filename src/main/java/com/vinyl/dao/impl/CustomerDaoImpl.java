package com.vinyl.dao.impl;

import com.vinyl.dao.CustomerDao;
import com.vinyl.model.Customer;
import com.vinyl.utils.KeyHolderUtils;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Value("${sql.customer.create.customer.query.path}")
	private String createCustomerQueryPath;
	@Value("${sql.customer.get.customer.by.num.query.path}")
	private String getCustomerByNumQueryPath;
	@Value("${sql.customer.update.customer.query.path}")
	private String updateCustomerQueryPath;
	@Value("${sql.customer.delete.customer.query.path}")
	private String deleteCustomerQueryPath;
	@Value("${sql.customer.get.all.customers.query.path}")
	private String getAllCustomersQueryPath;
	@Value("${sql.customer.update.discount.query.path}")
	private String updateDiscountQueryPath;
	@Value("${sql.customer.get.sum.for.all.purchases.query.path}")
	private String getSumForAllPurchasesQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Customer> customerRowMapper;

	@Override
	public int save(Customer customer) {
		String createCustomerQuery = QuerySupplier.getQuery(createCustomerQueryPath);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(createCustomerQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getCustomerName());
			ps.setString(2, customer.getCustomerEmail());
			ps.setInt(3, customer.getCustomerDiscount());
			return ps;
		}, keyHolder);

		return KeyHolderUtils.extractInt(keyHolder, "customer_num");
	}

	@Override
	public Customer getCustomerByNum(int num) {
		String getGetCustomerByNumQuery = QuerySupplier.getQuery(getCustomerByNumQueryPath);
		List<Customer> customers = jdbcTemplate.query(getGetCustomerByNumQuery, new Object[]{num}, customerRowMapper);
		return customers.size() == 0 ? null : customers.get(0);
	}

	@Override
	public void update(Customer customer) {
		String updateCustomerQuery = QuerySupplier.getQuery(updateCustomerQueryPath);

		int num = customer.getCustomerNum();
		String email = customer.getCustomerEmail();
		int discount = customer.getCustomerDiscount();

		jdbcTemplate.update(updateCustomerQuery, email, discount, num);
	}

	@Override
	public void deleteByNum(int num) {
		String deleteCustomerQuery = QuerySupplier.getQuery(deleteCustomerQueryPath);

		jdbcTemplate.update(deleteCustomerQuery, num);
	}

	@Override
	public void updateDiscount(int num, short discount) {
		String updateDiscountQuery = QuerySupplier.getQuery(updateDiscountQueryPath);
		jdbcTemplate.update(updateDiscountQuery, discount, num);
	}

	@Override
	public Integer getSumForAllPurchases(int num) {
		String getSumForAllPurchasesQuery = QuerySupplier.getQuery(getSumForAllPurchasesQueryPath);
		List<Integer> sum = jdbcTemplate.queryForList(getSumForAllPurchasesQuery, new Object[]{num}, Integer.class);
		return isNull(sum.get(0)) ? 0 : sum.get(0);
	}

	@Transactional
	@Override
	public List<Customer> searchCustomer(String query) {
		return jdbcTemplate.query(query, customerRowMapper);
	}

}
