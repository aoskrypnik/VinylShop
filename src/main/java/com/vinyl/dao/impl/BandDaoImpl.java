package com.vinyl.dao.impl;

import com.vinyl.dao.BandDao;
import com.vinyl.model.Band;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class BandDaoImpl implements BandDao {

	@Value("${sql.band.create.band.query.path}")
	private String createBandQueryPath;
	@Value("${sql.band.get.all.bands.query.path}")
	private String getAllBandsQueryPath;
	@Value("${sql.band.get.band.by.alias.name.query.path}")
	private String getBandByAliasQueryPath;
	@Value("${sql.band.delete.band.by.alias.query.path}")
	private String deleteBandQueryPath;
	@Value("${sql.band.update.band.query.path}")
	private String updateBandQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Band> bandRowMapper;

	@Override
	public void save(Band band) {
		String createBandQuery = QuerySupplier.getQuery(createBandQueryPath);
		jdbcTemplate.update(createBandQuery, band.getBandAlias(), band.getIsBandActive(), band.getCountryCode(),
				band.getStartYear(), band.getEndYear());
	}

	@Override
	public Band getBandByAlias(String alias) {
		String getBandByAliasQuery = QuerySupplier.getQuery(getBandByAliasQueryPath);
		List<Band> queryResult = jdbcTemplate.query(getBandByAliasQuery, new Object[]{alias}, bandRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Override
	public void update(Band band, String alias) {
		String updateBandQuery = QuerySupplier.getQuery(updateBandQueryPath);

		boolean activity = band.getIsBandActive();
		String bandAlias = band.getBandAlias();
		String country = band.getCountryCode();
		Date startYear = band.getStartYear();
		Date endYear = band.getEndYear();

		jdbcTemplate.update(updateBandQuery, bandAlias, activity, country, startYear, endYear, alias);

	}

	@Override
	public List<Band> searchBands(String query) {
		return jdbcTemplate.query(query, bandRowMapper);
	}

	@Override
	public void deleteBand(String alias) {
		String deleteBandQuery = QuerySupplier.getQuery(deleteBandQueryPath);
		jdbcTemplate.update(deleteBandQuery, alias);
	}

}
