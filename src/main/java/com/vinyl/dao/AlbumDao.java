package com.vinyl.dao;

import com.vinyl.model.Album;

import java.util.List;

public interface AlbumDao {

	String save(Album album);

	Album getAlbumByCatalogNum(String catalogNum);

	List<Album> searchAlbums(String query);

	void update(Album album, String catalogNum);
}
