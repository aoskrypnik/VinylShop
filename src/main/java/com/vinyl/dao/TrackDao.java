package com.vinyl.dao;

import com.vinyl.model.Track;

import java.util.List;

public interface TrackDao {

	int save(Track track);

	Track getTrackByCatalogNum(String catalogNum);

	List<Track> getTrackByName(String name);

	void update(Track track);

	List<Track> getAll();

	void deleteByCatalogNum(String catalogNum);

	List<Track> findTrackByLanguage(String language);

}
