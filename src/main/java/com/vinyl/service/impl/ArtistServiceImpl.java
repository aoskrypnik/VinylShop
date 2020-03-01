package com.vinyl.service.impl;

import com.vinyl.dao.ArtistDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.exception.ArtistExistException;
import com.vinyl.model.Artist;
import com.vinyl.service.ArtistService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class ArtistServiceImpl implements ArtistService {

	private static final String ARTIST_TABLE_NAME = "artist";
	public static final String ARTIST_ALREADY_EXIST = "Artist already exist with such alias: ";

	@Resource
	private ArtistDao artistDao;

	@Override
	public void save(Artist artist) throws ArtistExistException {
		String artistAlias = artist.getArtistAlias();
		Artist foundArtist = artistDao.getArtistByAlias(artistAlias);
		if (nonNull(foundArtist)) {
			throw new ArtistExistException(ARTIST_ALREADY_EXIST + artistAlias);
		}
		artistDao.save(artist);
	}

	@Override
	public Artist getArtistByAlias(String alias) {
		return artistDao.getArtistByAlias(alias);
	}

	@Override
	public List<Artist> searchArtists(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), ARTIST_TABLE_NAME);
		return artistDao.searchArtists(query);
	}

	@Override
	public void deleteArtist(String alias) {
		artistDao.deleteArtist(alias);
	}

	@Override
	public void update(Artist artist, String alias) {
		artistDao.update(artist, alias);
	}
}
