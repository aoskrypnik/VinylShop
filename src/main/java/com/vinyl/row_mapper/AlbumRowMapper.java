package com.vinyl.row_mapper;

import com.vinyl.model.Album;
import com.vinyl.model.Track;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AlbumRowMapper implements RowMapper<Album> {

	@Value("${sql.album.get.tracks.by.catalog.num.query.path}")
	private String getTracksByAlbumCatalogNumQueryPath;
	@Value("${sql.get.album.genre.by.catalog.num.query.path}")
	private String getAlbumGenreByCatalogNumQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Track> trackRowMapper;

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
				.build();
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
