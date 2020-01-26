package com.vinyl.service;

import com.vinyl.model.Track;

import java.util.List;

public interface TrackService {

	int save(Track track);

	Track getTrackByCatalogNum(String catalogNum);

	List<Track> getTrackByName(String name);

	void update(Track track);

	List<Track> getAll();

	void deleteByCatalogNum(String catalogNum);

	List<Track> findTrackByLanguage(String language);

	List<String> findAlbumsWithThisTrack(String catalogNum);

}
