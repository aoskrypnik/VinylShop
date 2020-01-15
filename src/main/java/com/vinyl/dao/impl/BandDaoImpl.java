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

	@Value("${sql.artist.get.current.bands.query.path}")
	private String artistGetCurrentBandsQueryPath;
	@Value("${sql.artist.get.previous.bands.query.path}")
	private String artistGetPreviousBandsQueryPath;

	@Resource
	private RowMapper<Band> bandRowMapper;
	@Resource
	private JdbcTemplate jdbcTemplate;

	public List<Band> getCurrentGroupsByArtistAlias(String artistAlias) {
		String artistGetCurrentBandsQuery = QuerySupplier.getQuery(artistGetCurrentBandsQueryPath);
		return jdbcTemplate.query(artistGetCurrentBandsQuery, new Object[]{artistAlias}, bandRowMapper);
	}

	public List<Band> getPreviousGroupsByArtistAlias(String artistAlias) {
		String artistGetPreviousBandsQuery = QuerySupplier.getQuery(artistGetPreviousBandsQueryPath);
		return jdbcTemplate.query(artistGetPreviousBandsQuery, new Object[]{artistAlias}, bandRowMapper);
	}

}
