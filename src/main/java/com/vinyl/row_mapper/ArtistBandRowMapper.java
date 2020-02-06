package com.vinyl.row_mapper;

import com.vinyl.dto.ArtistBandDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArtistBandRowMapper implements RowMapper<ArtistBandDto> {

	@Override
	public ArtistBandDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return ArtistBandDto.builder()
				.artistAlias(resultSet.getString("artist_alias"))
				.bandAlias(resultSet.getString("band_alias"))
				.joinDate(resultSet.getDate("join_date"))
				.exitDate(resultSet.getDate("exit_date"))
				.build();
	}

}
