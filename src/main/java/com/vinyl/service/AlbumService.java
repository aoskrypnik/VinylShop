package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.Album;

import java.util.List;

public interface AlbumService {

	void save(Album album) throws AlbumAlreadyExistException;

	Album getAlbumByCatalogNum(String catalogNum);

	List<Album> searchAlbums(SearchDto searchDto);

	public void update(Album album, String catalogNum);

}
