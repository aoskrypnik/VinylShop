package com.vinyl.dao.impl;

import com.vinyl.dao.ReleaseDao;
import com.vinyl.model.Release;
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
public class ReleaseDaoImpl implements ReleaseDao {

	@Value("${sql.release.create.release.query.path}")
	private String createReleaseQueryPath;
	@Value("${sql.release.get.all.releases.query.path}")
	private String getAllReleasesQueryPath;
	@Value("${sql.release.get.release.by.barcode.query.path}")
	private String getReleaseByBarcodeQueryPath;
	@Value("${sql.update.release.query.path}")
	private String updateReleaseQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private RowMapper<Release> releaseRowMapper;

	@Override
	public void save(Release release) {
		String createReleaseQuery = QuerySupplier.getQuery(createReleaseQueryPath);
		jdbcTemplate.update(createReleaseQuery, release.getReleaseBarcode(), release.getAlbumCatalogNum(), release.getReleaseCountryCode(),
				release.getReleaseDate(), release.getRecordSize(), release.getRecordSpeed(), release.getCopiesCount(),
				release.getIsRepress(), release.getLabel());
	}

	@Override
	public Release getReleaseByBarcode(String barcode) {
		String getReleaseByBarcodeQuery = QuerySupplier.getQuery(getReleaseByBarcodeQueryPath);
		List<Release> queryResult = jdbcTemplate.query(getReleaseByBarcodeQuery, new Object[]{barcode}, releaseRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Transactional
	@Override
	public List<Release> searchReleases(String query) {
		return jdbcTemplate.query(query, releaseRowMapper);
	}

	@Override
	public void update(Release release, String releaseBarCode) {
		String updateReleaseQuery = QuerySupplier.getQuery(updateReleaseQueryPath);

		String albumCatalogNum = release.getAlbumCatalogNum();
		String country = release.getReleaseCountryCode();
		Date releaseDate = release.getReleaseDate();
		int recordSize = release.getRecordSize();
		int recordSpeed = release.getRecordSpeed();
		int copiesCnt = release.getCopiesCount();
		boolean repress = release.getIsRepress();
		String label = release.getLabel();

		jdbcTemplate.update(updateReleaseQuery, albumCatalogNum, country, releaseDate, recordSize,
				recordSpeed, copiesCnt, repress, label, releaseBarCode);
	}
}
