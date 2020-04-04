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
import static org.springframework.util.CollectionUtils.isEmpty;

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
	@Value("${sql.customer.update.discount.query.path}")
	private String updateDiscountQueryPath;
	@Value("${sql.customer.get.sum.for.all.purchases.query.path}")
	private String getSumForAllPurchasesQueryPath;
	@Value("${sql.customer.create.customer.phone.numbers.query.path}")
	private String createCustomerPhoneNumbersQueryPath;
	@Value("${sql.customer.delete.customer.phone.numbers.query.path}")
	private String deleteCustomerPhoneNumberQueryPath;
	@Value("${sql.customer.get.phone.numbers.by.customer.query.path}")
	private String getCustomerPhoneNumbersQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Customer> customerRowMapper;

	@Transactional
	@Override
	public int save(Customer customer) {
		String createCustomerQuery = QuerySupplier.getQuery(createCustomerQueryPath);
		String createCustomerPhoneNumbersQuery = QuerySupplier.getQuery(createCustomerPhoneNumbersQueryPath);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(createCustomerQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getCustomerName());
			ps.setString(2, customer.getCustomerEmail());
			ps.setInt(3, 0);
			return ps;
		}, keyHolder);

		List<String> customerPhoneNumbers = customer.getCustomerPhoneNumbers();
		int customerNum = KeyHolderUtils.extractInt(keyHolder, "customer_num");
		customerPhoneNumbers.forEach(phone -> jdbcTemplate.update(createCustomerPhoneNumbersQuery, phone, customerNum));

		return customerNum;
	}

	@Transactional
	@Override
	public Customer getCustomerByNum(int num) {
		String getGetCustomerByNumQuery = QuerySupplier.getQuery(getCustomerByNumQueryPath);
		List<Customer> customers = jdbcTemplate.query(getGetCustomerByNumQuery, new Object[]{num}, customerRowMapper);
		return isEmpty(customers) ? null : customers.get(0);
	}

	@Transactional
	@Override
	public void update(Customer customer) {
		String updateCustomerQuery = QuerySupplier.getQuery(updateCustomerQueryPath);
		String deleteCustomerPhoneNumbersQuery = QuerySupplier.getQuery(deleteCustomerPhoneNumberQueryPath);
		String createCustomerPhoneNumbersQuery = QuerySupplier.getQuery(createCustomerPhoneNumbersQueryPath);

		int num = customer.getCustomerNum();
		String name = customer.getCustomerName();
		String email = customer.getCustomerEmail();
		List<String> phoneNumbers = customer.getCustomerPhoneNumbers();

		jdbcTemplate.update(deleteCustomerPhoneNumbersQuery, num);
		jdbcTemplate.update(updateCustomerQuery, name, email, num);
		phoneNumbers.forEach(phone -> jdbcTemplate.update(createCustomerPhoneNumbersQuery, phone, customer.getCustomerNum()));
	}

	@Override
	public void deleteByNum(int num) {
		String deleteCustomerQuery = QuerySupplier.getQuery(deleteCustomerQueryPath);
		String deleteCustomerPhoneNumbersQuery = QuerySupplier.getQuery(deleteCustomerPhoneNumberQueryPath);
		jdbcTemplate.update(deleteCustomerPhoneNumbersQuery, num);
		jdbcTemplate.update(deleteCustomerQuery, num);
	}

	@Override
	public void updateDiscount(int num, short discount) {
		String updateDiscountQuery = QuerySupplier.getQuery(updateDiscountQueryPath);
		jdbcTemplate.update(updateDiscountQuery, discount, num);
	}

	@Transactional
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
