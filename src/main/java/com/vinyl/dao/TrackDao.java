package com.vinyl.dao;

import com.vinyl.model.Track;

import java.util.List;

public interface TrackDao {

	String save(Track track);

	Track getRecordByCatalogNum(String catalogNum);

	void update(Track track);

	List<Track> getAll();

	void deleteByCatalogNum(String catalogNum);

}
