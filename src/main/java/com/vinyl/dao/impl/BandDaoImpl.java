package com.vinyl.dao.impl;

import com.vinyl.dao.BandDao;
import com.vinyl.model.Band;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class BandDaoImpl implements BandDao {

	@Value("${sql.band.create.band.query.path}")
	private String createBandQueryPath;
	@Value("${sql.band.get.all.bands.query.path}")
	private String getAllBandsQueryPath;
	@Value("${sql.band.get.band.by.alias.name.query.path}")
	private String getBandByAliasQueryPath;

	@Resource
	private RowMapper<Band> bandRowMapper;
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public String save(Band band) {
		String createBandQuery = QuerySupplier.getQuery(createBandQueryPath);
		jdbcTemplate.update(createBandQuery, band.getAlias(), band.getIsActive(), band.getCountryCode(),
				band.getStartYear(), band.getEndYear());
		return band.getAlias();
	}

	@Override
	public Band getBandByAlias(String alias) {
		String getBandByAliasQuery = QuerySupplier.getQuery(getBandByAliasQueryPath);
		List<Band> queryResult = jdbcTemplate.queryForList(getBandByAliasQuery, Band.class);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Override
	public List<Band> getAll() {
		String getAllBandsQuery = QuerySupplier.getQuery(getAllBandsQueryPath);
		return jdbcTemplate.queryForList(getAllBandsQuery, Band.class);
	}
}
