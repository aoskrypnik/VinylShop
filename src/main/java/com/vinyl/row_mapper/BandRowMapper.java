package com.vinyl.row_mapper;

import com.vinyl.model.Band;
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
public class BandRowMapper implements RowMapper<Band> {

	@Value("${sql.band.get.current.artists.query.path}")
	private String bandGetCurrentArtistsQueryPath;
	@Value("${sql.band.get.previous.artists.query.path}")
	private String bandGetPreviousArtistsQueryPath;
	@Value("${sql.band.get.tracks.query.path}")
	private String bandGetTracksQueryPath;
	@Value("${sql.band.get.featuring.tracks.query.path}")
	private String bandGetFeaturingTracksQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public Band mapRow(ResultSet resultSet, int i) throws SQLException {
		String bandAlias = resultSet.getString("band_alias");
		return Band.builder()
				.bandAlias(bandAlias)
				.isBandActive(resultSet.getBoolean("activity"))
				.countryCode(resultSet.getString("country"))
				.startYear(resultSet.getDate("start_year"))
				.endYear(resultSet.getDate("end_year"))
				.currentArtistAliases(getCurrentArtistsByBandAlias(bandAlias))
				.previousArtistAliases(getPreviousArtistsByBandAlias(bandAlias))
				.trackCatalogNums(getTracksByBandAlias(bandAlias))
				.featuringTracksCatalogNums(getFeaturingTracksByBandAlias(bandAlias))
				.build();
	}

	private List<String> getCurrentArtistsByBandAlias(String bandAlias) {
		String artistGetCurrentBandsQuery = QuerySupplier.getQuery(bandGetCurrentArtistsQueryPath);
		return jdbcTemplate.queryForList(artistGetCurrentBandsQuery, new Object[]{bandAlias}, String.class);
	}

	private List<String> getPreviousArtistsByBandAlias(String bandAlias) {
		String artistGetPreviousBandsQuery = QuerySupplier.getQuery(bandGetPreviousArtistsQueryPath);
		return jdbcTemplate.queryForList(artistGetPreviousBandsQuery, new Object[]{bandAlias}, String.class);
	}

	private List<String> getTracksByBandAlias(String bandAlias) {
		String artistGetTracksQuery = QuerySupplier.getQuery(bandGetTracksQueryPath);
		return jdbcTemplate.queryForList(artistGetTracksQuery, new Object[]{bandAlias}, String.class);
	}

	private List<String> getFeaturingTracksByBandAlias(String bandAlias) {
		String artistGetFeaturingTracksQuery = QuerySupplier.getQuery(bandGetFeaturingTracksQueryPath);
		return jdbcTemplate.queryForList(artistGetFeaturingTracksQuery, new Object[]{bandAlias}, String.class);
	}

}
