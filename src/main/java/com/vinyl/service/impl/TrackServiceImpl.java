package com.vinyl.service.impl;

import com.vinyl.dao.TrackDao;
import com.vinyl.model.Track;
import com.vinyl.service.TrackService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

	private static final String TRACK_TABLE_NAME = "track";

	@Resource
	private TrackDao trackDao;

	@Override
	public String save(Track track) {
		return trackDao.save(track);
	}

	@Override
	public Track getTrackByCatalogNum(String catalogNum) {
		return trackDao.getTrackByCatalogNum(catalogNum);
	}

	@Override
	public List<Track> getAll() {
		return trackDao.getAll();
	}

	@Override
	public void update(Track track) {
	}

	@Override
	public void deleteByCatalogNum(String catalogNum) {
		trackDao.deleteByCatalogNum(catalogNum);
	}

	@Override
	public List<Track> searchTracks(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
									List<String> joins, String sorting, String order) {
		String query = QueryBuilder.build(whereParams, likeParams, betweenParams, joins, sorting, order, TRACK_TABLE_NAME);
		return trackDao.searchTracks(query);
	}

}
