package com.vinyl.service;

import com.vinyl.model.Artist;

import java.util.List;

public interface ArtistService {

	void save(Artist artist);

	List<Artist> getAll();

	Artist getArtistByAlias(String alias);

	List<Artist> searchArtists(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							   List<String> joins, String sorting, String order);
}
