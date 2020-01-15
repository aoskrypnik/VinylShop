package com.vinyl.dao.impl;

import com.vinyl.dao.TrackDao;
import com.vinyl.model.Track;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TrackDaoImpl implements TrackDao, RowMapper<Track> {

	@Override
	public String save(Track track) {
		return null;
	}

	@Override
	public Track getRecordByCatalogNum(String catalogNum) {
		return null;
	}

	@Override
	public void update(Track track) {

	}

	@Override
	public List<Track> getAll() {
		return null;
	}

	@Override
	public void deleteByCatalogNum(String catalogNum) {

	}

	@Override
	public Track mapRow(ResultSet resultSet, int i) throws SQLException {
		return null;
	}

}
