package com.vinyl.dao.impl;

import com.vinyl.dao.ArtistDao;
import com.vinyl.model.Artist;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
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
	@Value("${sql.artist.delete.artist.by.alias.query.path}")
	private String deleteArtistByAliasQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Artist> artistRowMapper;

	@Override
	public void save(Artist artist) {
		String createArtistQuery = QuerySupplier.getQuery(createArtistQueryPath);
		jdbcTemplate.update(createArtistQuery, artist.getArtistAlias(), artist.getIsArtistActive(), artist.getArtistCountryCode(),
				artist.getArtistName(), artist.getArtistBirthDate(), artist.getArtistDeathDate());
	}

	@Override
	public Artist getArtistByAlias(String alias) {
		String getArtistByAliasQuery = QuerySupplier.getQuery(getArtistByAliasQueryPath);
		List<Artist> queryResult = jdbcTemplate.query(getArtistByAliasQuery, new Object[]{alias}, artistRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Override
	public void update(Artist artist, String alias) {
		String updateArtistQuery = QuerySupplier.getQuery(updateArtistQueryPath);

		boolean activity = artist.getIsArtistActive();
		String artistAlias = artist.getArtistAlias();
		String country = artist.getArtistCountryCode();
		String name = artist.getArtistName();
		Date birthdate = artist.getArtistBirthDate();
		Date deathdate = artist.getArtistDeathDate();

		jdbcTemplate.update(updateArtistQuery, artistAlias, activity, country, name, birthdate, deathdate, alias);
	}

	@Override
	public List<Artist> searchArtists(String query) {
		return jdbcTemplate.query(query, artistRowMapper);
	}

	@Override
	public void deleteArtist(String alias) {
		String deleteArtistByAliasQuery = QuerySupplier.getQuery(deleteArtistByAliasQueryPath);
		jdbcTemplate.update(deleteArtistByAliasQuery, alias);
	}

}
