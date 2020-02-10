package com.vinyl.dao.impl;

import com.vinyl.dao.CheckDao;
import com.vinyl.dao.CustomerDao;
import com.vinyl.model.Check;
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

@Repository
public class CheckDaoImpl implements CheckDao {

	@Value("${sql.check.get.check.by.num.query.path}")
	private String getCheckByNumQueryPath;
	@Value("${sql.check.save.check.query.path}")
	private String saveCheckQueryPath;
	@Value("${sql.check.populate.with.products.query.path}")
	private String populateCheckWithProductsQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private RowMapper<Check> checkRowMapper;

	@Override
	public Check getByNum(Integer num) {
		String getByNumQuery = QuerySupplier.getQuery(getCheckByNumQueryPath);
		List<Check> queryResult = jdbcTemplate.query(getByNumQuery, new Object[]{num}, checkRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Transactional
	@Override
	public Integer save(Check check) {
		String saveCheckQuery = QuerySupplier.getQuery(saveCheckQueryPath);
		String populateCheckWithProductsQuery = QuerySupplier.getQuery(populateCheckWithProductsQueryPath);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(saveCheckQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, check.getSalesmanTabNum());
			ps.setInt(2, check.getCustomerNum());
			ps.setTimestamp(3, check.getDateTime());
			ps.setInt(4, check.getOverallSum());
			ps.setInt(5, check.getCheckDiscount());
			return ps;
		}, keyHolder);

		Integer checkNum = KeyHolderUtils.extractInt(keyHolder, "check_num");
		check.getProductBarcodes().forEach(barcode ->
				jdbcTemplate.update(populateCheckWithProductsQuery, checkNum, false, barcode));

		return checkNum;
	}

	@Override
	public List<Check> searchChecks(String query) {
		return jdbcTemplate.query(query, checkRowMapper);
	}
}
