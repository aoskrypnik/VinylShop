package com.vinyl.service.impl;

import com.vinyl.dao.ArtistDao;
import com.vinyl.model.Artist;
import com.vinyl.service.ArtistService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

	private static final String ARTIST_TABLE_NAME = "artist";

	@Resource
	private ArtistDao artistDao;

	@Override
	public void save(Artist artist) {
		artistDao.save(artist);
	}

	@Override
	public List<Artist> getAll() {
		return artistDao.getAll();
	}

	@Override
	public Artist getArtistByAlias(String alias) {
		return artistDao.getArtistByAlias(alias);
	}

	@Override
	public List<Artist> searchArtists(List<String> whereParams, List<String> likeParams, List<String> betweenParams, List<String> joins, String sorting, String order) {
		String query = QueryBuilder.build(whereParams, likeParams, betweenParams, joins, sorting, order, ARTIST_TABLE_NAME);
		return artistDao.searchArtists(query);
	}

	@Override
	public void deleteArtist(String alias) {
		artistDao.deleteArtist(alias);
	}
}
