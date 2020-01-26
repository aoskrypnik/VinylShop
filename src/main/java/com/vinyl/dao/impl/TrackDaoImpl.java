package com.vinyl.dao.impl;

import com.vinyl.dao.TrackDao;
import com.vinyl.model.Track;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TrackDaoImpl implements TrackDao {

	@Value("${sql.track.find.tracks.by.language.query.path}")
	private String findTracksByLanguageQueryPath;
	@Value("${sql.track.get.track.by.catalog.num.query.path}")
	private String getTrackByCatalogNumQueryPath;
	@Value("${sql.track.get.all.tracks.query.path}")
	private String getAllTracksQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Track> trackRowMapper;

	@Override
	public int save(Track track) {
		return 0;
	}

	@Override
	public List<Track> getTrackByName(String name) {
		return null;
	}

	@Override
	public Track getTrackByCatalogNum(String catalogNum) {
		String getTrackByCatalogNumQuery = QuerySupplier.getQuery(getTrackByCatalogNumQueryPath);
		List<Track> tracks = jdbcTemplate.query(getTrackByCatalogNumQuery, new Object[]{catalogNum}, trackRowMapper);
		return tracks.size() == 0 ? null : tracks.get(0);
	}

	@Override
	public List<Track> getAll() {
		String getAllTracksQuery = QuerySupplier.getQuery(getAllTracksQueryPath);
		return jdbcTemplate.query(getAllTracksQuery, trackRowMapper);
	}


	@Override
	public void update(Track track) {

	}

	@Override
	public void deleteByCatalogNum(String catalogNum) {

	}

	@Override
	public List<Track> findTrackByLanguage(String language) {
		String findTracksByLanguageQuery = QuerySupplier.getQuery(findTracksByLanguageQueryPath);
		jdbcTemplate.query(findTracksByLanguageQuery, new Object[]{language}, trackRowMapper);
		return null;
	}
//оновити трек

}
