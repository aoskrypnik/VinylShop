package com.vinyl.row_mapper;

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

	@Override
	public Track mapRow(ResultSet resultSet, int i) throws SQLException {
		String trackCatalogNum = resultSet.getString("catalog_num");
		List<String> composers = getComposersByTrackCatalogNum(trackCatalogNum);
		List<String> artistIds = getArtistsByTrackCatalogNum(trackCatalogNum);
		List<String> bandIds = getBandsByTrackCatalogNum(trackCatalogNum);
		List<String> featuringArtistIds = getFeaturingArtistsByTrackCatalogNum(trackCatalogNum);
		List<String> featuringBandIds = getFeaturingBandsByTrackCatalogNum(trackCatalogNum);
		List<String> albumIds = getAlbumsByTrackCatalogNum(trackCatalogNum);
		return Track.builder()
				.catalogNum(trackCatalogNum)
				.name(resultSet.getString("track_name"))
				.duration(resultSet.getInt("duration"))
				.composerIds(composers)
				.albumIds(albumIds)
				.artistIds(artistIds)
				.bandIds(bandIds)
				.featuringArtistIds(featuringArtistIds)
				.featuringBandIds(featuringBandIds)
				.build();
	}

	private List<String> getComposersByTrackCatalogNum(String trackCatalogNum) {
		String trackGetComposersQuery = QuerySupplier.getQuery(trackGetComposersQueryPath);
		return jdbcTemplate.queryForList(trackGetComposersQuery, new Object[]{trackCatalogNum}, String.class);
	}

	private List<String> getAlbumsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetAlbumsQuery = QuerySupplier.getQuery(trackGetAlbumsQueryPath);
		return jdbcTemplate.queryForList(trackGetAlbumsQuery, new Object[]{trackCatalogNum}, String.class);
	}

	private List<String> getArtistsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetArtistsQuery = QuerySupplier.getQuery(trackGetArtistsQueryPath);
		return jdbcTemplate.queryForList(trackGetArtistsQuery, new Object[]{trackCatalogNum}, String.class);
	}

	private List<String> getBandsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetBandsQuery = QuerySupplier.getQuery(trackGetBandsQueryPath);
		return jdbcTemplate.queryForList(trackGetBandsQuery, new Object[]{trackCatalogNum}, String.class);
	}

	private List<String> getFeaturingArtistsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetFeaturingArtistsQuery = QuerySupplier.getQuery(trackGetFeaturingArtistsQueryPath);
		return jdbcTemplate.queryForList(trackGetFeaturingArtistsQuery, new Object[]{trackCatalogNum}, String.class);
	}

	private List<String> getFeaturingBandsByTrackCatalogNum(String trackCatalogNum) {
		String trackGetFeaturingBandsQuery = QuerySupplier.getQuery(trackGetFeaturingBandsQueryPath);
		return jdbcTemplate.queryForList(trackGetFeaturingBandsQuery, new Object[]{trackCatalogNum}, String.class);
	}

}
