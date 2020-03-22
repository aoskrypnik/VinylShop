package com.vinyl.dao.impl;

import com.vinyl.dao.ComposerDao;
import com.vinyl.model.Composer;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class ComposerDaoImpl implements ComposerDao {

	@Value("${sql.composer.create.composer.query.path}")
	private String createComposerQueryPath;
	@Value("${sql.composer.get.composer.by.name.query.path}")
	private String getComposerByNameQueryPath;
	@Value("${sql.composer.update.composer.query.path}")
	private String updateComposerQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Composer> composerRowMapper;

	@Override
	public void save(Composer composer) {
		String createComposerQuery = QuerySupplier.getQuery(createComposerQueryPath);

		String name = composer.getComposerName();
		String country = composer.getComposerCountryCode();
		Date activityStart = composer.getActivityStart();
		Date activityEnd = composer.getActivityEnd();

		jdbcTemplate.update(createComposerQuery, name, country, activityStart, activityEnd);
	}

	@Transactional
	@Override
	public Composer getComposerByName(String composerName) {
		String getComposerByNameQuery = QuerySupplier.getQuery(getComposerByNameQueryPath);
		List<Composer> composers = jdbcTemplate.query(getComposerByNameQuery, new Object[]{composerName}, composerRowMapper);
		return composers.size() == 0 ? null : composers.get(0);
	}

	@Override
	public void update(Composer composer, String composerName) {
		String updateComposerQuery = QuerySupplier.getQuery(updateComposerQueryPath);

		String country = composer.getComposerCountryCode();
		Date activityStart = composer.getActivityStart();
		Date activityEnd = composer.getActivityEnd();
		jdbcTemplate.update(updateComposerQuery, country, activityStart, activityEnd, composerName);
	}

	@Transactional
	@Override
	public List<Composer> searchComposers(String query) {
		return jdbcTemplate.query(query, composerRowMapper);
	}
}
