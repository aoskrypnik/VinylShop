package com.vinyl.dao.impl;

import com.vinyl.dao.TrackDao;
import com.vinyl.model.Track;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class TrackDaoImpl implements TrackDao {

	@Value("${sql.track.get.track.by.catalog.num.query.path}")
	private String getTrackByCatalogNumQueryPath;
	@Value("${sql.track.create.track.query.path}")
	private String createTrackQueryPath;
	@Value("${sql.track.delete.query.path}")
	private String deleteTrackQueryPath;
	@Value("${sql.track.language.add.track.language.query.path}")
	private String addLanguagesToTrackQueryPath;
	@Value("${sql.track.delete.languages.from.track.query.path}")
	private String deleteLanguagesFromTrackQueryPath;
	@Value("${sql.track.add.albums.to.track}")
	private String addAlbumsToTrackQueryPath;
	@Value("${sql.track.delete.albums.from.track.query.path}")
	private String deleteAlbumsFromTrackQueryPath;
	@Value("${sql.track.add.composers.to.track}")
	private String addComposersToTrackQueryPath;
	@Value("${sql.track.delete.composers.from.track.query.path}")
	private String deleteComposersFromTrackQueryPath;
	@Value("${sql.track.update.track.query.path}")
	private String updateTrackQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Track> trackRowMapper;

	@Override
	public void save(Track track) {
		String trackCatalogNum = track.getTrackCatalogNum();

		String createTrackQuery = QuerySupplier.getQuery(createTrackQueryPath);
		String addLanguagesToTrackQuery = QuerySupplier.getQuery(addLanguagesToTrackQueryPath);
		String addAlbumsToTrackQuery = QuerySupplier.getQuery(addAlbumsToTrackQueryPath);
		String addComposersToTrackQuery = QuerySupplier.getQuery(addComposersToTrackQueryPath);

		jdbcTemplate.update(createTrackQuery, trackCatalogNum, track.getTrackName(), track.getDuration());
		performQueryOnListValuesForTrack(trackCatalogNum, track.getLanguages(), addLanguagesToTrackQuery);
		performQueryOnListValuesForTrack(trackCatalogNum, track.getAlbumIds(), addAlbumsToTrackQuery);
		performQueryOnListValuesForTrack(trackCatalogNum, track.getComposerIds(), addComposersToTrackQuery);
	}

	private void performQueryOnListValuesForTrack(String trackCatalogNum, List<String> listValues, String query) {
		if (isFalse(isEmpty(listValues))) {
			for (String value : listValues) {
				jdbcTemplate.update(query, trackCatalogNum, value);
			}
		}
	}

	@Transactional
	@Override
	public Track getTrackByCatalogNum(String catalogNum) {
		String getTrackByCatalogNumQuery = QuerySupplier.getQuery(getTrackByCatalogNumQueryPath);
		List<Track> tracks = jdbcTemplate.query(getTrackByCatalogNumQuery, new Object[]{catalogNum}, trackRowMapper);
		return isEmpty(tracks) ? null : tracks.get(0);
	}

	@Override
	public void update(Track track, String trackCatalogNum) {
		String updateTrackQuery = QuerySupplier.getQuery(updateTrackQueryPath);
		String deleteLanguagesFromTrackQuery = QuerySupplier.getQuery(deleteLanguagesFromTrackQueryPath);
		String deleteAlbumsFromTrackQuery = QuerySupplier.getQuery(deleteAlbumsFromTrackQueryPath);
		String deleteComposersFromTrackQuery = QuerySupplier.getQuery(deleteComposersFromTrackQueryPath);
		String addLanguagesToTrackQuery = QuerySupplier.getQuery(addLanguagesToTrackQueryPath);
		String addAlbumsToTrackQuery = QuerySupplier.getQuery(addAlbumsToTrackQueryPath);
		String addComposersToTrackQuery = QuerySupplier.getQuery(addComposersToTrackQueryPath);

		String trackName = track.getTrackName();
		int duration = track.getDuration();

		jdbcTemplate.update(updateTrackQuery, trackName, duration, trackCatalogNum);
		jdbcTemplate.update(deleteLanguagesFromTrackQuery, trackCatalogNum);
		jdbcTemplate.update(deleteAlbumsFromTrackQuery, trackCatalogNum);
		jdbcTemplate.update(deleteComposersFromTrackQuery, trackCatalogNum);
		performQueryOnListValuesForTrack(trackCatalogNum, track.getLanguages(), addLanguagesToTrackQuery);
		performQueryOnListValuesForTrack(trackCatalogNum, track.getAlbumIds(), addAlbumsToTrackQuery);
		performQueryOnListValuesForTrack(trackCatalogNum, track.getComposerIds(), addComposersToTrackQuery);

	}

	@Override
	public void deleteByCatalogNum(String catalogNum) {
		String deleteCustomerQuery = QuerySupplier.getQuery(deleteTrackQueryPath);
		jdbcTemplate.update(deleteCustomerQuery, catalogNum);
	}

	@Transactional
	@Override
	public List<Track> searchTracks(String query) {
		return jdbcTemplate.query(query, trackRowMapper);
	}

}
