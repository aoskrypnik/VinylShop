package com.vinyl.service;

import com.vinyl.model.Track;

import java.util.List;

public interface TrackService {

	String save(Track track);

	Track getTrackByCatalogNum(String catalogNum);

	void update(Track track);

	List<Track> getAll();

	void deleteByCatalogNum(String catalogNum);

	List<Track> searchTracks(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							 List<String> joins, String sorting, String order);
}
