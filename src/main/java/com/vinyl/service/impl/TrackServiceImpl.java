package com.vinyl.service.impl;

import com.vinyl.dao.TrackDao;
import com.vinyl.model.Track;
import com.vinyl.service.TrackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TrackServiceImpl implements TrackService {

	@Resource
	private TrackDao trackDao;

	@Override
	public int save(Track track) {
		return 0;
	}

	@Override
	public Track getTrackByCatalogNum(String catalogNum) {
		return trackDao.getTrackByCatalogNum(catalogNum);
	}

	@Override
	public List<Track> getTrackByName(String name) {
		return null;
	}

	@Override
	public List<Track> getAll() {
		return null;
	}

	@Override
	public void update(Track track) {

	}

	@Override
	public void deleteByCatalogNum(String catalogNum) {

	}

	@Override
	public List<Track> findTrackByLanguage(String language) {
		List<Track> foundTracks = trackDao.findTrackByLanguage(language);
		return (isNull(foundTracks)) ? null : foundTracks;
	}

	@Override
	public List<String> findAlbumsWithThisTrack(String catalogNum) {
		Track track = getTrackByCatalogNum(catalogNum);
		return isNull(track) ? null : track.getAlbumIds();
	}
}
