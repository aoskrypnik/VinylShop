package com.vinyl.service.impl;

import com.vinyl.dao.AlbumDao;
import com.vinyl.model.Album;
import com.vinyl.service.AlbumService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

	private static final String ALBUM_TABLE_NAME = "album";

	@Resource
	private AlbumDao albumDao;

	@Override
	public String save(Album album) {
		return albumDao.save(album);
	}

	@Override
	public List<Album> getAll() {
		return albumDao.getAll();
	}

	@Override
	public Album getAlbumByCatalogNum(String catalogNum) {
		return albumDao.getAlbumByCatalogNum(catalogNum);
	}

	@Override
	public List<Album> searchAlbums(List<String> whereParams, List<String> likeParams, List<String> betweenParams, List<String> joins, String sorting, String order) {
		String query = QueryBuilder.build(whereParams, likeParams, betweenParams, joins, sorting, order, ALBUM_TABLE_NAME);
		return albumDao.searchAlbums(query);
	}
}
