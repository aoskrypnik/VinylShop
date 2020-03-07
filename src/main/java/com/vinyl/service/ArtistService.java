package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.ArtistExistException;
import com.vinyl.model.Artist;

import java.util.List;

public interface ArtistService {

	void save(Artist artist) throws ArtistExistException;

	Artist getArtistByAlias(String alias);

	List<Artist> searchArtists(SearchDto searchDto);

	void deleteArtist(String alias);

	void update(Artist artist, String alias);

	List<Artist> getArtistWhoseAlbumsWereSoldOut();

}
