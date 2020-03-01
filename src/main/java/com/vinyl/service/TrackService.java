package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.TrackAlreadyExistException;
import com.vinyl.model.Track;

import java.util.List;

public interface TrackService {

	void save(Track track) throws TrackAlreadyExistException;

	Track getTrackByCatalogNum(String catalogNum);

	void update(Track track, String catalogNum);

	void deleteByCatalogNum(String catalogNum);

	List<Track> searchTracks(SearchDto searchDto);
}
