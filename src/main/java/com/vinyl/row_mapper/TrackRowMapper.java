package com.vinyl.row_mapper;

import com.vinyl.model.Album;
import com.vinyl.model.Artist;
import com.vinyl.model.Band;
import com.vinyl.model.Composer;
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
public class TrackRowMapper implements RowMapper<Track> {

	@Value("${sql.track.get.composers.query.path}")
	private String trackGetComposersQueryPath;
	@Value("${sql.track.get.artists.query.path}")
	private String trackGetArtistsQueryPath;
	@Value("${sql.track.get.bands.query.path}")
	private String trackGetBandsQueryPath;
	@Value("${sql.track.get.featuring.artists.query.path}")
	private String trackGetFeaturingArtistsQueryPath;
	@Value("${sql.track.get.featuring.bands.query.path}")
	private String trackGetFeaturingBandsQueryPath;
	@Value("${sql.track.get.albums.query.path}")
	private String trackGetAlbumsQueryPath;


	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Composer> composerRowMapper;
	@Resource
	private RowMapper<Artist> artistRowMapper;
	@Resource
	private RowMapper<Band> bandRowMapper;
	@Resource
	private RowMapper<Album> albumRowMapper;

	@Override
	public Track mapRow(ResultSet resultSet, int i) throws SQLException {
		String trackCatalogNum = resultSet.getString("catalog_num");
		List<String> composers = getComposersByTrackCatalogNum(trackCatalogNum);
		List<Artist> artists = getArtistsByTrackCatalogNum(trackCatalogNum);
		List<Band> bands = getBandsByTrackCatalogNum(trackCatalogNum);
		List<Artist> featuringArtists = getFeaturingArtistsByTrackCatalogNum(trackCatalogNum);
		List<Band> featuringBands = getFeaturingBandsByTrackCatalogNum(trackCatalogNum);
		List<Album> albums = getAlbumsByTrackCatalogNum(trackCatalogNum);
		return Track.builder()
				.catalogNum(trackCatalogNum)
				.name(resultSet.getString("track_name"))
				.duration(resultSet.getInt("duration"))
				.composerIds(composers)
				.albums(albums)
				.artists(artists)
				.bands(bands)
				.featuring_artists(featuringArtists)
				.featuring_bands(featuringBands)
				.build();
	}

	private List<String> getComposersByTrackCatalogNum(String trackCatalogNum) {
		String trackGetComposersQuery = QuerySupplier.getQuery(trackGetComposersQueryPath);
		return jdbcTemplate.queryForList(trackGetComposersQuery, new Object[]{trackCatalogNum}, String.class);
	}

	private List<Album> getAlbumsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetAlbumsQuery = QuerySupplier.getQuery(trackGetAlbumsQueryPath);
		return jdbcTemplate.query(trackGetAlbumsQuery, new Object[]{trackCatalogNum}, albumRowMapper);
	}

	private List<Artist> getArtistsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetArtistsQuery = QuerySupplier.getQuery(trackGetArtistsQueryPath);
		return jdbcTemplate.query(trackGetArtistsQuery, new Object[]{trackCatalogNum}, artistRowMapper);
	}

	private List<Band> getBandsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetBandsQuery = QuerySupplier.getQuery(trackGetBandsQueryPath);
		return jdbcTemplate.query(trackGetBandsQuery, new Object[]{trackCatalogNum}, bandRowMapper);
	}

	private List<Artist> getFeaturingArtistsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetFeaturingArtistsQuery = QuerySupplier.getQuery(trackGetFeaturingArtistsQueryPath);
		return jdbcTemplate.query(trackGetFeaturingArtistsQuery, new Object[]{trackCatalogNum}, artistRowMapper);
	}

	private List<Band> getFeaturingBandsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetFeaturingBandsQuery = QuerySupplier.getQuery(trackGetFeaturingBandsQueryPath);
		return jdbcTemplate.query(trackGetFeaturingBandsQuery, new Object[]{trackCatalogNum}, bandRowMapper);
	}

}
