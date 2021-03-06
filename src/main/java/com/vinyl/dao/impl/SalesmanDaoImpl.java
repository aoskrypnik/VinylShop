package com.vinyl.dao.impl;

import com.vinyl.dao.SalesmanDao;
import com.vinyl.model.Salesman;
import com.vinyl.utils.KeyHolderUtils;
import com.vinyl.utils.QuerySupplier;
import lombok.extern.slf4j.Slf4j;
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
import java.sql.Types;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Repository
public class SalesmanDaoImpl implements SalesmanDao {

	@Value("${sql.salesmen.get.salesman.by.tab.num.query.path}")
	private String getGetSalesmanByTabNumQueryPath;
	@Value("${sql.salesmen.create.salesman.query.path}")
	private String createSalesmanQueryPath;
	@Value("${sql.salesmen.update.salesman.query.path}")
	private String updateSalesmanQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Salesman> salesmanRowMapper;

	@Transactional
	@Override
	public Salesman getSalesmanByTabNum(int tabNum) {
		String getGetSalesmanByTabNumQuery = QuerySupplier.getQuery(getGetSalesmanByTabNumQueryPath);
		List<Salesman> salesmen = jdbcTemplate.query(getGetSalesmanByTabNumQuery, new Object[]{tabNum}, salesmanRowMapper);
		return salesmen.size() == 0 ? null : salesmen.get(0);
	}

	@Override
	public int save(Salesman salesman) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String createSalesmanQuery = QuerySupplier.getQuery(createSalesmanQueryPath);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(createSalesmanQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, salesman.getSalesmanName());
			ps.setString(2, salesman.getPassportNum());
			ps.setString(3, salesman.getAddressCity());
			ps.setString(4, salesman.getAddressStr());
			ps.setString(5, salesman.getAddressHome());
			if (nonNull(salesman.getAddressApt())) {
				ps.setInt(6, salesman.getAddressApt());
			} else {
				ps.setNull(6, Types.INTEGER);
			}
			ps.setString(7, salesman.getSalesmanPhoneNum());
			ps.setDate(8, salesman.getWorksFrom());
			ps.setDate(9, salesman.getWorksTo());
			ps.setInt(10, salesman.getSalary());
			ps.setString(11, null);
			return ps;
		}, keyHolder);
		return KeyHolderUtils.extractInt(keyHolder, "tab_num");
	}

	@Override
	public void update(Salesman salesmanNew) {
		String updateSalesmanQuery = QuerySupplier.getQuery(updateSalesmanQueryPath);
		jdbcTemplate.update(updateSalesmanQuery, salesmanNew.getAddressCity(), salesmanNew.getAddressStr(),
				salesmanNew.getAddressHome(), salesmanNew.getAddressApt(), salesmanNew.getSalesmanPhoneNum(),
				salesmanNew.getWorksTo(), salesmanNew.getSalary(), salesmanNew.getTabNum());
	}

	@Transactional
	@Override
	public List<Salesman> searchSalesman(String query) {
		return jdbcTemplate.query(query, salesmanRowMapper);
	}

}
