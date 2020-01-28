package com.vinyl.service;

import com.vinyl.model.Album;

import java.util.List;

public interface AlbumService {

	String save(Album album);

	List<Album> getAll();

	Album getAlbumByCatalogNum(String catalogNum);

	List<Album> searchAlbums(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							 List<String> joins, String sorting, String order);

}
