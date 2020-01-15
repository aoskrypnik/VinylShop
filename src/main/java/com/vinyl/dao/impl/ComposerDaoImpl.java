package com.vinyl.dao.impl;

import com.vinyl.dao.ComposerDao;
import com.vinyl.model.Composer;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ComposerDaoImpl implements ComposerDao{

	@Value("${sql.create.composer.query.path}")
	private String createComposerQueryPath;
	@Value("${sql.get.composer.by.name.query.path}")
	private String getComposerByNameQueryPath;
	@Value("${sql.find.composers.by.country.query.path}")
	private String findComposerByCountryQueryPath;
	@Value("${sql.find.composers.by.activity.period.query.path}")
	private String findComposersByActivityPeriodQueryPath;
	@Value("${sql.update.composer.query.path}")
	private String updateComposerQueryPath;
	@Value("${sql.get.all.composers.query.path}")
	private String getAllComposersQueryPath;
	@Value("${sql.find.composer.by.multiply.criteria.query.path}")
	private String findComposerByMultiplyCriteriaQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private NamedParameterJdbcOperations namedParameterJdbcTemplate;
	@Resource
	private RowMapper<Composer> composerRowMapper;

	@Override
	public void save(Composer composer) {
		String createComposerQuery = QuerySupplier.getQuery(createComposerQueryPath);

		String name = composer.getName();
		String country = composer.getCountry();
		Date activityStart = composer.getActivityStart();
		Date activityEnd = composer.getActivityEnd();

		jdbcTemplate.update(createComposerQuery, name, country, activityStart, activityEnd);
	}

	@Override
	public Composer getComposerByName(String composerName) {
		String getComposerByNameQuery = QuerySupplier.getQuery(getComposerByNameQueryPath);
		List<Composer> composers = jdbcTemplate.query(getComposerByNameQuery, new Object[]{composerName}, composerRowMapper);
		return composers.size() == 0 ? null : composers.get(0);
	}

	@Override
	public List<Composer> getAll() {
		String getAllComposersQuery = QuerySupplier.getQuery(getAllComposersQueryPath);
		return jdbcTemplate.query(getAllComposersQuery, composerRowMapper);
	}

	@Override
	public void update(Composer composer, String composerName) {
		String updateComposerQuery = QuerySupplier.getQuery(updateComposerQueryPath);
		jdbcTemplate.update(updateComposerQuery, composer.getActivityEnd(), composerName);
	}

	@Override
	public List<Composer> findComposersByCountry(String country) {
		String findComposerByCountryQuery = QuerySupplier.getQuery(findComposerByCountryQueryPath);
		return jdbcTemplate.query(findComposerByCountryQuery, new Object[]{country}, composerRowMapper);
	}

	@Override
	public List<Composer> findComposersByActivityPeriod(Date activityStart, Date activityEnd) {
		String findComposersByActivityPeriodQuery = QuerySupplier.getQuery(findComposersByActivityPeriodQueryPath);
		return jdbcTemplate.query(findComposersByActivityPeriodQuery, new Object[]{activityStart, activityEnd}, composerRowMapper);
	}

	@Override
	public List<Composer> findComposersByMultiplyCriteria(String countryCode, Date activityStart, Date activityEnd) {
		String findComposerByMultiplyCriteriaQuery = QuerySupplier.getQuery(findComposerByMultiplyCriteriaQueryPath);
		return namedParameterJdbcTemplate.query(findComposerByMultiplyCriteriaQuery,
				new MapSqlParameterSource()
						.addValue("country", countryCode)
						.addValue("activity_start", activityStart)
						.addValue("activity_end", activityEnd),
				composerRowMapper);
	}


}
