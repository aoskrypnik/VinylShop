package com.vinyl.row_mapper;

import com.vinyl.model.Album;
import com.vinyl.model.Release;
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
public class AlbumRowMapper implements RowMapper<Album> {

	@Value("${sql.album.get.tracks.by.catalog.num.query.path}")
	private String getTracksByAlbumCatalogNumQueryPath;
	@Value("${sql.get.album.genre.by.catalog.num.query.path}")
	private String getAlbumGenreByCatalogNumQueryPath;
	@Value("${sql.get.releases.by.catalog.num.query.path}")
	private String getReleasesByAlbumCatalogNumQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Track> trackRowMapper;
	@Resource
	private RowMapper<Release> releaseRowMapper;

	@Override
	public Album mapRow(ResultSet resultSet, int i) throws SQLException {
		String catalogNum = resultSet.getString("catalog_num");
		return Album.builder()
				.catalogNum(catalogNum)
				.name(resultSet.getString("album_name"))
				.genre(getAlbumGenreByCatalogNum(catalogNum))
				.releaseYear(resultSet.getInt("release_year"))
				.variousArtists(resultSet.getBoolean("various_artists"))
				.tracks(getTracksByAlbumCatalogNum(catalogNum))
				.releases(getReleasesByAlbumCatalogNum(catalogNum))
				.build();
	}

	private List<Release> getReleasesByAlbumCatalogNum(String catalogNum) {
		String getReleasesByAlbumCatalogNumQuery = QuerySupplier.getQuery(getReleasesByAlbumCatalogNumQueryPath);
		return jdbcTemplate.query(getReleasesByAlbumCatalogNumQuery, new Object[]{catalogNum}, releaseRowMapper);
	}

	private List<Track> getTracksByAlbumCatalogNum(String catalogNum) {
		String getTracksByAlbumCatalogNumQuery = QuerySupplier.getQuery(getTracksByAlbumCatalogNumQueryPath);
		return jdbcTemplate.query(getTracksByAlbumCatalogNumQuery, new Object[]{catalogNum}, trackRowMapper);
	}

	private String getAlbumGenreByCatalogNum(String catalogNum) {
		String getAlbumGenreByCatalogNumQuery = QuerySupplier.getQuery(getAlbumGenreByCatalogNumQueryPath);
		return jdbcTemplate.queryForObject(getAlbumGenreByCatalogNumQuery, new Object[]{catalogNum}, String.class);
	}

}
