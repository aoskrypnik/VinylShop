package com.vinyl.dao.impl;

import com.vinyl.dao.ArtistDao;
import com.vinyl.model.Artist;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ArtistDaoImpl implements ArtistDao {

	@Value("${sql.artist.create.artist.query.path}")
	private String createArtistQueryPath;
	@Value("${sql.artist.get.artist.by.alias.query.path}")
	private String getArtistByAliasQueryPath;
	@Value("${sql.artist.get.all.artists.query.path}")
	private String getAllArtistsQueryPath;
	@Value("${sql.artist.update.artist.query.path}")
	private String updateArtistQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Artist> artistRowMapper;

	@Override
	public String save(Artist artist) {
		String createArtistQuery = QuerySupplier.getQuery(createArtistQueryPath);
		jdbcTemplate.update(createArtistQuery, artist.getAlias(), artist.getIsActive(), artist.getCountryCode(),
				artist.getName(), artist.getBirthDate(), artist.getDeathDate());
		return artist.getAlias();
	}

	@Override
	public Artist getArtistByAlias(String alias) {
		String getArtistByAliasQuery = QuerySupplier.getQuery(getArtistByAliasQueryPath);
		List<Artist> queryResult = jdbcTemplate.query(getArtistByAliasQuery, new Object[]{alias}, artistRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Override
	public void update(Artist artist) {
		String updateArtistQuery = QuerySupplier.getQuery(updateArtistQueryPath);
		jdbcTemplate.update(updateArtistQuery, artist.getIsActive(), artist.getCountryCode(),
				artist.getDeathDate(), artist.getAlias());
	}

	@Override
	public List<Artist> getAll() {
		String getAllArtistsQuery = QuerySupplier.getQuery(getAllArtistsQueryPath);
		return jdbcTemplate.query(getAllArtistsQuery, artistRowMapper);
	}

	@Override
	public List<Artist> searchArtists(String query) {
		return jdbcTemplate.query(query, artistRowMapper);
	}

}
