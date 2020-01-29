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
	@Value("${sql.track.create.track.query.path}")
	private String createTrackQueryPath;
	@Value("${sql.track.delete.query.path}")
	private String deleteTrackQueryPath;
	@Value("${sql.track.language.add.track.language.query.path}")
	private String addLanguagesToTrackQueryPath;


	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Track> trackRowMapper;

	@Override
	public String save(Track track) {
		String createTrackQuery = QuerySupplier.getQuery(createTrackQueryPath);
		String addLanguagesToTrackQuery = QuerySupplier.getQuery(addLanguagesToTrackQueryPath);
		jdbcTemplate.update(createTrackQuery, track.getTrackCatalogNum(), track.getTrackName(), track.getDuration());
		List<String> languages = track.getLanguages();
		for (String language: languages) {
			jdbcTemplate.update(addLanguagesToTrackQuery, track.getTrackCatalogNum(), language);
		}
		return track.getTrackCatalogNum();
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
		String deleteCustomerQuery = QuerySupplier.getQuery(deleteTrackQueryPath);
		jdbcTemplate.update(deleteCustomerQuery, catalogNum);
	}

	@Override
	public List<Track> searchTracks(String query) {
		return jdbcTemplate.query(query, trackRowMapper);
	}

}
