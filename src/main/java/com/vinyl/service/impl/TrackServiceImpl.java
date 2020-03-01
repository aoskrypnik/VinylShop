package com.vinyl.service.impl;

import com.vinyl.dao.TrackDao;
import com.vinyl.dto.SearchDto;
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
	public List<Track> searchTracks(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWhereParams(), searchDto.getLikeParams(), searchDto.getBetweenParams(),
						searchDto.getJoins(), searchDto.getSorting(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), TRACK_TABLE_NAME);
		return trackDao.searchTracks(query);
	}

}
