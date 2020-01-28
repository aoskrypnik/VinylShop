package com.vinyl.row_mapper;

import com.vinyl.model.Artist;
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
public class ArtistRowMapper implements RowMapper<Artist> {

	@Value("${sql.artist.get.current.bands.query.path}")
	private String artistGetCurrentBandsQueryPath;
	@Value("${sql.artist.get.previous.bands.query.path}")
	private String artistGetPreviousBandsQueryPath;
	@Value("${sql.artist.get.tracks.query.path}")
	private String artistGetTracksQueryPath;
	@Value("${sql.artist.get.featuring.tracks.query.path}")
	private String artistGetFeaturingTracksQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public Artist mapRow(ResultSet resultSet, int i) throws SQLException {
		String artistAlias = resultSet.getString("artist_alias");
		return Artist.builder()
				.artistAlias(artistAlias)
				.isArtistActive(resultSet.getBoolean("activity"))
				.countryCode(resultSet.getString("country"))
				.artistName(resultSet.getString("artist_name"))
				.artistBirthDate(resultSet.getDate("birth_date"))
				.artistDeathDate(resultSet.getDate("death_date"))
				.currentBandAliases(getCurrentBandsByArtistAlias(artistAlias))
				.previousBandAliases(getPreviousBandsByArtistAlias(artistAlias))
				.trackCatalogNums(getTracksByArtistAlias(artistAlias))
				.featuringTrackCatalogNums(getFeaturingTracksByArtistAlias(artistAlias))
				.build();
	}

	private List<String> getCurrentBandsByArtistAlias(String artistAlias) {
		String artistGetCurrentBandsQuery = QuerySupplier.getQuery(artistGetCurrentBandsQueryPath);
		return jdbcTemplate.queryForList(artistGetCurrentBandsQuery, new Object[]{artistAlias}, String.class);
	}

	private List<String> getPreviousBandsByArtistAlias(String artistAlias) {
		String artistGetPreviousBandsQuery = QuerySupplier.getQuery(artistGetPreviousBandsQueryPath);
		return jdbcTemplate.queryForList(artistGetPreviousBandsQuery, new Object[]{artistAlias}, String.class);
	}

	private List<String> getTracksByArtistAlias(String artistAlias) {
		String artistGetTracksQuery = QuerySupplier.getQuery(artistGetTracksQueryPath);
		return jdbcTemplate.queryForList(artistGetTracksQuery, new Object[]{artistAlias}, String.class);
	}

	private List<String> getFeaturingTracksByArtistAlias(String artistAlias) {
		String artistGetFeaturingTracksQuery = QuerySupplier.getQuery(artistGetFeaturingTracksQueryPath);
		return jdbcTemplate.queryForList(artistGetFeaturingTracksQuery, new Object[]{artistAlias}, String.class);
	}

}
