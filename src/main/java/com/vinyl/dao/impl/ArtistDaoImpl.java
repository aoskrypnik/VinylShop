package com.vinyl.dao.impl;

import com.vinyl.dao.ArtistDao;
import com.vinyl.model.Artist;
import com.vinyl.model.Band;
import com.vinyl.model.Track;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArtistDaoImpl implements ArtistDao, RowMapper<Artist> {

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
	@Resource
	private RowMapper<Band> bandRowMapper;
	@Resource
	private RowMapper<Track> trackRowMapper;

	@Override
	public Artist mapRow(ResultSet resultSet, int i) throws SQLException {
		String artistAlias = resultSet.getString("artist_alias");
		List<Band> currentBands = getCurrentBandsByArtistAlias(artistAlias);
		List<Band> previousBands = getPreviousBandsByArtistAlias(artistAlias);
		List<Track> tracks = getTracksByArtistAlias(artistAlias);
		List<Track> featuringTracks = getFeaturingTracksByArtistAlias(artistAlias);
		return Artist.builder()
				.alias(artistAlias)
				.isActive(resultSet.getBoolean("activity"))
				.countryCode(resultSet.getString("country"))
				.name(resultSet.getString("artist_name"))
				.birthDate(resultSet.getDate("birth_date"))
				.deathDate(resultSet.getDate("death_date"))
				.currentBands(currentBands)
				.previousBands(previousBands)
				.tracks(tracks)
				.featuringTracks(featuringTracks)
				.build();
	}

	private List<Band> getCurrentBandsByArtistAlias(String artistAlias) {
		String artistGetCurrentBandsQuery = QuerySupplier.getQuery(artistGetCurrentBandsQueryPath);
		return jdbcTemplate.query(artistGetCurrentBandsQuery, new Object[]{artistAlias}, bandRowMapper);
	}

	private List<Band> getPreviousBandsByArtistAlias(String artistAlias) {
		String artistGetPreviousBandsQuery = QuerySupplier.getQuery(artistGetPreviousBandsQueryPath);
		return jdbcTemplate.query(artistGetPreviousBandsQuery, new Object[]{artistAlias}, bandRowMapper);
	}

	private List<Track> getTracksByArtistAlias(String artistAlias) {
		String artistGetTracksQuery = QuerySupplier.getQuery(artistGetTracksQueryPath);
		return jdbcTemplate.query(artistGetTracksQuery, new Object[]{artistAlias}, trackRowMapper);
	}

	private List<Track> getFeaturingTracksByArtistAlias(String artistAlias) {
		String artistGetFeaturingTracksQuery = QuerySupplier.getQuery(artistGetFeaturingTracksQueryPath);
		return jdbcTemplate.query(artistGetFeaturingTracksQuery, new Object[]{artistAlias}, trackRowMapper);
	}

}
