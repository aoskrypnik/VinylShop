package com.vinyl.row_mapper;

import com.vinyl.model.Record;
import com.vinyl.model.Release;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ReleaseRowMapper implements RowMapper<Release> {

	@Value("${sql.release.get.records.query.path}")
	private String getRecordsByReleaseBarcodeQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Record> recordRowMapper;

	@Override
	public Release mapRow(ResultSet resultSet, int i) throws SQLException {
		String barcode = resultSet.getString("bar_code");
		return Release.builder()
				.barcode(barcode)
				.albumCatalogNum(resultSet.getString("album_catalog_num"))
				.copiesCount(resultSet.getInt("copies_cnt"))
				.countryCode(resultSet.getString("country"))
				.isRepress(resultSet.getBoolean("repress"))
				.label(resultSet.getString("label"))
				.records(getRecordsByReleaseBarcode(barcode))
				.recordSize(resultSet.getInt("record_size"))
				.recordSpeed(resultSet.getInt("record_speed"))
				.releaseDate(resultSet.getDate("release_date"))
				.build();
	}

	private List<Record> getRecordsByReleaseBarcode(String barcode) {
		String getRecordsByReleaseBarcodeQuery = QuerySupplier.getQuery(getRecordsByReleaseBarcodeQueryPath);
		return jdbcTemplate.query(getRecordsByReleaseBarcodeQuery, new Object[] {barcode}, recordRowMapper);
	}

}