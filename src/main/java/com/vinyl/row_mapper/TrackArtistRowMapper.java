package com.vinyl.row_mapper;

import com.vinyl.dto.TrackArtistDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TrackPerformerRowMapper implements RowMapper<TrackArtistDto> {
	@Override
	public TrackArtistDto mapRow(ResultSet resultSet, int i) throws SQLException {
		String alias;
		boolean isArtist = false;
		try {
			alias = resultSet.getString("artist_alias");
			isArtist = true;
		} catch (SQLException e) {
			alias = resultSet.getString("band_alias");
		}
		return TrackArtistDto.builder()
				.trackCatalogNum(resultSet.getString("track_catalog_num"))
				.performerAlias(alias)
				.isArtist(isArtist)
				.isFeaturing(resultSet.getBoolean("featuring"))
				.build();
	}
}
