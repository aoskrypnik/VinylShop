package com.vinyl.row_mapper;

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

	@Override
	public Release mapRow(ResultSet resultSet, int i) throws SQLException {
		String barcode = resultSet.getString("bar_code");
		return Release.builder()
				.releaseBarcode(barcode)
				.albumCatalogNum(resultSet.getString("album_catalog_num"))
				.copiesCount(resultSet.getInt("copies_cnt"))
				.releaseCountryCode(resultSet.getString("country"))
				.isRepress(resultSet.getBoolean("repress"))
				.label(resultSet.getString("label"))
				.recordBarcodes(getRecordsByReleaseBarcode(barcode))
				.recordSize(resultSet.getInt("record_size"))
				.recordSpeed(resultSet.getInt("record_speed"))
				.releaseDate(resultSet.getDate("release_date"))
				.build();
	}

	private List<String> getRecordsByReleaseBarcode(String barcode) {
		String getRecordsByReleaseBarcodeQuery = QuerySupplier.getQuery(getRecordsByReleaseBarcodeQueryPath);
		return jdbcTemplate.queryForList(getRecordsByReleaseBarcodeQuery, new Object[]{barcode}, String.class);
	}

}
