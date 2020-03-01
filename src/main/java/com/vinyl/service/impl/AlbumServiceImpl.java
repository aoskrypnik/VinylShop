package com.vinyl.service.impl;

import com.vinyl.dao.AlbumDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.Album;
import com.vinyl.service.AlbumService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class AlbumServiceImpl implements AlbumService {

	private static final String ALBUM_TABLE_NAME = "album";
	public static final String ALBUM_ALREADY_EXIST_WITH_SUCH_CATALOG_NUM = "Album already exist with such catalogNum: ";

	@Resource
	private AlbumDao albumDao;

	@Override
	public void save(Album album) throws AlbumAlreadyExistException {
		String catalogNum = album.getAlbumCatalogNum();
		Album alreadyExistingAlbum = getAlbumByCatalogNum(catalogNum);
		if (nonNull(alreadyExistingAlbum)) {
			throw new AlbumAlreadyExistException(ALBUM_ALREADY_EXIST_WITH_SUCH_CATALOG_NUM + catalogNum);
		}
		albumDao.save(album);
	}

	@Override
	public Album getAlbumByCatalogNum(String catalogNum) {
		return albumDao.getAlbumByCatalogNum(catalogNum);
	}

	@Override
	public List<Album> searchAlbums(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), ALBUM_TABLE_NAME);
		return albumDao.searchAlbums(query);
	}

	@Override
	public void update(Album album, String catalogNum) {
		albumDao.update(album, catalogNum);
	}
}
