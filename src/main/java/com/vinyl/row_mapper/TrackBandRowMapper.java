package com.vinyl.row_mapper;

import com.vinyl.dto.TrackBandDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TrackBandRowMapper implements RowMapper<TrackBandDto> {
	@Override
	public TrackBandDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return TrackBandDto.builder()
				.trackCatalogNum(resultSet.getString("track_catalog_num"))
				.bandAlias(resultSet.getString("band_alias"))
				.isFeaturing(resultSet.getBoolean("featuring"))
				.build();
	}
}
