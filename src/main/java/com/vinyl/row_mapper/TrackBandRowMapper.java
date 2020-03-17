package com.vinyl.row_mapper;

import com.vinyl.dto.TrackArtistDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TrackArtistRowMapper implements RowMapper<TrackArtistDto> {
	@Override
	public TrackArtistDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return TrackArtistDto.builder()
				.trackCatalogNum(resultSet.getString("track_catalog_num"))
				.artistAlias(resultSet.getBoolean("artist_alias"))
				.isFeaturing(resultSet.getBoolean("featuring"))
				.build();
	}
}
