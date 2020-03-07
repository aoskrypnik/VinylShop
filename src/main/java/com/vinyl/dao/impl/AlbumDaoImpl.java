package com.vinyl.dao.impl;

import com.vinyl.dao.AlbumDao;
import com.vinyl.model.Album;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class AlbumDaoImpl implements AlbumDao {

	@Value("${sql.album.get.album.by.catalog.num.query.path}")
	private String getAlbumByCatalogNumQueryPath;
	@Value("${sql.album.create.album.query.path}")
	private String createAlbumQueryPath;
	@Value("${sql.albumgenre.create.genre.query.path}")
	private String addAlbumGenreQueryPath;
	@Value("${sql.album.delete.genres.from.album.query.path}")
	private String deleteGenresFromAlbumQueryPath;
	@Value("${sql.album.update.album.query.path}")
	private String updateAlbumQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private RowMapper<Album> albumRowMapper;

	@Override
	public void save(Album album) {
		String createAlbumQuery = QuerySupplier.getQuery(createAlbumQueryPath);
		String addAlbumGenreQuery = QuerySupplier.getQuery(addAlbumGenreQueryPath);
		jdbcTemplate.update(createAlbumQuery, album.getAlbumCatalogNum(), album.getReleaseYear(), album.getAlbumName(),
				album.getVariousArtists());
		album.getAlbumGenres().forEach(genre -> jdbcTemplate.update(addAlbumGenreQuery, album.getAlbumCatalogNum(), genre));
	}

	@Override
	public Album getAlbumByCatalogNum(String catalogNum) {
		String getAlbumByCatalogNumQuery = QuerySupplier.getQuery(getAlbumByCatalogNumQueryPath);
		List<Album> queryResult = jdbcTemplate.query(getAlbumByCatalogNumQuery, new Object[]{catalogNum}, albumRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Transactional
	@Override
	public List<Album> searchAlbums(String query) {
		return jdbcTemplate.query(query, albumRowMapper);
	}

	@Override
	public void update(Album album, String catalogNum) {
		String updateAlbumQuery = QuerySupplier.getQuery(updateAlbumQueryPath);
		String deleteGenresFromAlbumQuery = QuerySupplier.getQuery(deleteGenresFromAlbumQueryPath);
		String addAlbumGenreQuery = QuerySupplier.getQuery(addAlbumGenreQueryPath);

		int releaseYear = album.getReleaseYear();
		String name = album.getAlbumName();
		boolean variousArtists = album.getVariousArtists();

		jdbcTemplate.update(updateAlbumQuery, releaseYear, name, variousArtists, catalogNum);
		jdbcTemplate.update(deleteGenresFromAlbumQuery, catalogNum);
		album.getAlbumGenres().forEach(genre -> jdbcTemplate.update(addAlbumGenreQuery, catalogNum, genre));

	}
}
