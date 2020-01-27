package com.vinyl.dao.impl;

import com.vinyl.dao.AlbumDao;
import com.vinyl.model.Album;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class AlbumDaoImpl implements AlbumDao {

	@Value("${sql.album.get.album.by.catalog.num.query.path}")
	private String getAlbumByCatalogNumQueryPath;
	@Value("${sql.album.create.album.query.path}")
	private String createAlbumQueryPath;
	@Value("${sql.album.get.all.albums.query.path}")
	private String getAllAlbumsQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public String save(Album album) {
		String createAlbumQuery = QuerySupplier.getQuery(createAlbumQueryPath);
		jdbcTemplate.update(createAlbumQuery, album.getCatalogNum(), album.getReleaseYear(), album.getName(),
				album.getVariousArtists());
		return album.getCatalogNum();
	}

	@Override
	public Album getAlbumByCatalogNum(String catalogNum) {
		String getAlbumByCatalogNumQuery = QuerySupplier.getQuery(getAlbumByCatalogNumQueryPath);
		List<Album> queryResult = jdbcTemplate.queryForList(getAlbumByCatalogNumQuery, Album.class);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Override
	public List<Album> getAll() {
		String getAllAlbumsQuery = QuerySupplier.getQuery(getAllAlbumsQueryPath);
		return jdbcTemplate.queryForList(getAllAlbumsQuery, Album.class);
	}
}
