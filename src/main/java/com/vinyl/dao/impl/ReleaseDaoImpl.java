package com.vinyl.dao.impl;

import com.vinyl.dao.ReleaseDao;
import com.vinyl.model.Release;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ReleaseDaoImpl implements ReleaseDao {

	@Value("${sql.release.create.release.query.path}")
	private String createReleaseQueryPath;
	@Value("${sql.release.get.all.releases.query.path}")
	private String getAllReleasesQueryPath;
	@Value("${sql.release.get.release.by.barcode.query.path}")
	private String getReleaseByBarcodeQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private RowMapper<Release> releaseRowMapper;

	@Override
	public String save(Release release) {
		String createReleaseQuery = QuerySupplier.getQuery(createReleaseQueryPath);
		jdbcTemplate.update(createReleaseQuery, release.getReleaseBarcode(), release.getAlbumCatalogNum(), release.getCountryCode(),
				release.getReleaseDate(), release.getRecordSize(), release.getRecordSpeed(), release.getCopiesCount(),
				release.getIsRepress(), release.getLabel());
		return release.getReleaseBarcode();
	}

	@Override
	public Release getReleaseByBarcode(String barcode) {
		String getReleaseByBarcodeQuery = QuerySupplier.getQuery(getReleaseByBarcodeQueryPath);
		List<Release> queryResult = jdbcTemplate.query(getReleaseByBarcodeQuery, new Object[]{barcode}, releaseRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Override
	public List<Release> getAll() {
		String getAllReleasesQuery = QuerySupplier.getQuery(getAllReleasesQueryPath);
		return jdbcTemplate.query(getAllReleasesQuery, releaseRowMapper);
	}

	@Override
	public List<Release> searchReleases(String query) {
		return jdbcTemplate.query(query, releaseRowMapper);
	}
}
