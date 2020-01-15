package com.vinyl.row_mapper;

import com.vinyl.model.Artist;
import com.vinyl.model.Band;
import com.vinyl.model.Track;
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
	private String getCurrentArtistsByBandAliasQueryPath;
	@Value("${sql.band.get.previous.artists.query.path}")
	private String getFormerArtistsByBandAliasQueryPath;
	@Value("${sql.band.get.tracks.query.path}")
	private String getTracksByBandAliasQueryPath;
	@Value("${sql.band.get.featuring.tracks.query.path}")
	private String getFeaturingTracksByBandAliasQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Artist> artistRowMapper;
	@Resource
	private RowMapper<Track> trackRowMapper;

	@Override
	public Band mapRow(ResultSet resultSet, int i) throws SQLException {
		String bandAlias = resultSet.getString("band_alias");
		return Band.builder()
				.alias(bandAlias)
				.isActive(resultSet.getBoolean("activity"))
				.countryCode(resultSet.getString("country"))
				.startYear(resultSet.getDate("start_year"))
				.endYear(resultSet.getDate("end_year"))
				.artists(getCurrentArtistsByBandAlias(bandAlias))
				.formerArtists(getPreviousArtistsByBandAlias(bandAlias))
				.tracks(getTracksByBandAlias(bandAlias))
				.featuringTracks(getFeaturingTracksByBandAlias(bandAlias))
				.build();
	}

	private List<Artist> getCurrentArtistsByBandAlias(String bandAlias) {
		String artistGetCurrentBandsQuery = QuerySupplier.getQuery(getCurrentArtistsByBandAliasQueryPath);
		return jdbcTemplate.query(artistGetCurrentBandsQuery, new Object[]{bandAlias}, artistRowMapper);
	}

	private List<Artist> getPreviousArtistsByBandAlias(String bandAlias) {
		String artistGetPreviousBandsQuery = QuerySupplier.getQuery(getFormerArtistsByBandAliasQueryPath);
		return jdbcTemplate.query(artistGetPreviousBandsQuery, new Object[]{bandAlias}, artistRowMapper);
	}

	private List<Track> getTracksByBandAlias(String bandAlias) {
		String artistGetTracksQuery = QuerySupplier.getQuery(getTracksByBandAliasQueryPath);
		return jdbcTemplate.query(artistGetTracksQuery, new Object[]{bandAlias}, trackRowMapper);
	}

	private List<Track> getFeaturingTracksByBandAlias(String bandAlias) {
		String artistGetFeaturingTracksQuery = QuerySupplier.getQuery(getFeaturingTracksByBandAliasQueryPath);
		return jdbcTemplate.query(artistGetFeaturingTracksQuery, new Object[]{bandAlias}, trackRowMapper);
	}

}
