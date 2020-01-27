package com.vinyl.dao;

import com.vinyl.model.Artist;

import java.util.List;

public interface ArtistDao {

	String save(Artist artist);

	Artist getArtistByAlias(String alias);

	void update(Artist artist);

	List<Artist> getAll();

	List<Artist> searchArtists(String query);
}
