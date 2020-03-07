package com.vinyl.dao.impl;

import com.vinyl.dao.SupplierDao;
import com.vinyl.model.Supplier;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SupplierDaoImpl implements SupplierDao {

	@Value("${sql.supplier.create.supplier.query.path}")
	private String createSupplierQueryPath;
	@Value("${sql.supplier.get.supplier.by.edrpou.query.path}")
	private String getSupplierByEdrpouQueryPath;
	@Value("${sql.supplier.update.supplier.query.path}")
	private String updateSupplierQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Supplier> supplierRowMapper;

	@Override
	public void save(Supplier supplier) {
		String createSupplierQuery = QuerySupplier.getQuery(createSupplierQueryPath);
		jdbcTemplate.update(createSupplierQuery, supplier.getEdrpou(),
				supplier.getSupplierAddress(), supplier.getPhoneNumber());
	}

	@Override
	public Supplier getSupplierByEdrpou(String edrpou) {
		String getSupplierByEdrpouQuery = QuerySupplier.getQuery(getSupplierByEdrpouQueryPath);
		List<Supplier> queryResult = jdbcTemplate.query(getSupplierByEdrpouQuery, new Object[]{edrpou}, supplierRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Transactional
	@Override
	public List<Supplier> searchSuppliers(String query) {
		return jdbcTemplate.query(query, supplierRowMapper);
	}

	@Override
	public void update(Supplier supplier, String edrpou) {
		String updateSupplierQuery = QuerySupplier.getQuery(updateSupplierQueryPath);
		jdbcTemplate.update(updateSupplierQuery, supplier.getEdrpou(),
				supplier.getSupplierAddress(), supplier.getPhoneNumber(), edrpou);
	}
}
