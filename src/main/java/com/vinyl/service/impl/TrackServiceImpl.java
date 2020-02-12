package com.vinyl.service.impl;

import com.vinyl.dao.TrackDao;
import com.vinyl.exception.TrackAlreadyExistException;
import com.vinyl.model.Track;
import com.vinyl.service.TrackService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class TrackServiceImpl implements TrackService {

	private static final String TRACK_TABLE_NAME = "track";

	@Resource
	private TrackDao trackDao;

	@Override
	public void save(Track track) throws TrackAlreadyExistException {
		String catalogNum = track.getTrackCatalogNum();
		Track alreadyExistingTrack = getTrackByCatalogNum(catalogNum);
		if (nonNull(alreadyExistingTrack)) {
			throw new TrackAlreadyExistException("Track already exist with such catalogNum: " + catalogNum);
		}
		trackDao.save(track);
	}

	@Override
	public Track getTrackByCatalogNum(String catalogNum) {
		return trackDao.getTrackByCatalogNum(catalogNum);
	}

	@Override
	public void update(Track track, String catalogNum) {
		trackDao.update(track, catalogNum);
	}

	@Override
	public void deleteByCatalogNum(String catalogNum) {
		trackDao.deleteByCatalogNum(catalogNum);
	}

	@Override
	public List<Track> searchTracks(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
									List<String> joins, String sorting, String order, Integer limit, Integer offset) {
		String query = QueryBuilder
				.build(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset, TRACK_TABLE_NAME);
		return trackDao.searchTracks(query);
	}

}
