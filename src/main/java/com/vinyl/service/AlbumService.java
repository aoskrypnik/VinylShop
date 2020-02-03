package com.vinyl.service;

import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.Album;

import java.util.List;

public interface AlbumService {

	String save(Album album) throws AlbumAlreadyExistException;

	List<Album> getAll();

	Album getAlbumByCatalogNum(String catalogNum);

	List<Album> searchAlbums(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							 List<String> joins, String sorting, String order);

	public void update(Album album, String catalogNum);

}
