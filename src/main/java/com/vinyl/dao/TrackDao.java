package com.vinyl.dao;

import com.vinyl.model.Track;

import java.util.List;

public interface TrackDao {

	String save(Track track);

	Track getTrackByCatalogNum(String catalogNum);

	void update(Track track, String catalogNum);

	List<Track> getAll();

	void deleteByCatalogNum(String catalogNum);

	List<Track> searchTracks(String query);
}
