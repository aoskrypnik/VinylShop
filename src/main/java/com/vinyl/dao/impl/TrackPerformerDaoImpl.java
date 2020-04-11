package com.vinyl.dao.impl;

import com.vinyl.dao.TrackPerformerDao;
import com.vinyl.dto.TrackArtistDto;
import com.vinyl.dto.TrackBandDto;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class TrackPerformerDaoImpl implements TrackPerformerDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<TrackArtistDto> trackArtistDtoRowMapper;
	@Resource
	private RowMapper<TrackBandDto> trackBandDtoRowMapper;

	@Value("${sql.create.artist.track.performance.query.path}")
	private String createArtistTrackPerformanceQueryPath;
	@Value("${sql.create.band.track.performance.query.path}")
	private String createBandTrackPerformanceQueryPath;
	@Value("${sql.update.artist.track.performance.query.path}")
	private String updateArtistTrackPerformanceQueryPath;
	@Value("${sql.update.band.track.performance.query.path}")
	private String updateBandTrackPerformanceQueryPath;
	@Value("${sql.get.artist.track.performance.query.path}")
	private String getArtistTrackPerformanceQueryPath;
	@Value("${sql.get.band.track.performance.query.path}")
	private String getBandTrackPerformanceQueryPath;
	@Value("${sql.delete.artist.track.performance.query.path}")
	private String deleteArtistTrackPerformerQueryPath;
	@Value("${sql.delete.band.track.performance.query.path}")
	private String deleteBandTrackPerformerQueryPath;


	@Override
	public void save(TrackArtistDto trackArtistDto) {
		String query = QuerySupplier.getQuery(createArtistTrackPerformanceQueryPath);
		jdbcTemplate.update(query, trackArtistDto.getTrackCatalogNum(), trackArtistDto.getArtistAlias(),
				trackArtistDto.getIsFeaturing());
	}

	@Override
	public void save(TrackBandDto trackBandDto) {
		String query = QuerySupplier.getQuery(createBandTrackPerformanceQueryPath);
		jdbcTemplate.update(query, trackBandDto.getTrackCatalogNum(), trackBandDto.getBandAlias(),
				trackBandDto.getIsFeaturing());
	}

	@Override
	public void update(TrackArtistDto trackArtistDto) {
		String query = QuerySupplier.getQuery(updateArtistTrackPerformanceQueryPath);
		jdbcTemplate.update(query, trackArtistDto.getIsFeaturing(),
				trackArtistDto.getTrackCatalogNum(), trackArtistDto.getArtistAlias());
	}

	@Override
	public void update(TrackBandDto trackBandDto) {
		String query = QuerySupplier.getQuery(updateBandTrackPerformanceQueryPath);
		jdbcTemplate.update(query, trackBandDto.getIsFeaturing(),
				trackBandDto.getTrackCatalogNum(), trackBandDto.getBandAlias());
	}

	@Transactional
	@Override
	public TrackArtistDto getTrackArtistByTrackNameAndArtistAlias(String trackName, String artistAlias) {
		String getArtistTrackPerformanceQuery = QuerySupplier.getQuery(getArtistTrackPerformanceQueryPath);

		List<TrackArtistDto> trackArtistList = jdbcTemplate
				.query(getArtistTrackPerformanceQuery, new Object[]{trackName, artistAlias}, trackArtistDtoRowMapper);

		return isEmpty(trackArtistList) ? null : trackArtistList.get(0);
	}

	@Transactional
	@Override
	public TrackBandDto getTrackBandByTrackNameAndBandAlias(String trackName, String bandAlias) {
		String getBandTrackPerformanceQuery = QuerySupplier.getQuery(getBandTrackPerformanceQueryPath);

		List<TrackBandDto> trackBandList = jdbcTemplate
				.query(getBandTrackPerformanceQuery, new Object[]{trackName, bandAlias}, trackBandDtoRowMapper);

		return isEmpty(trackBandList) ? null : trackBandList.get(0);
	}

	@Override
	public void deleteTrackArtistInstance(TrackArtistDto trackArtistDto) {
		String deleteArtistTrackPerformerQuery = QuerySupplier.getQuery(deleteArtistTrackPerformerQueryPath);
		String trackCatalogNum = trackArtistDto.getTrackCatalogNum();
		String performerAlias = trackArtistDto.getArtistAlias();
		jdbcTemplate.update(deleteArtistTrackPerformerQuery, trackCatalogNum, performerAlias);
	}

	@Override
	public void deleteTrackBandInstance(TrackBandDto trackBandDto) {
		String deleteBandTrackPerformerQuery = QuerySupplier.getQuery(deleteBandTrackPerformerQueryPath);
		String trackCatalogNum = trackBandDto.getTrackCatalogNum();
		String performerAlias = trackBandDto.getBandAlias();
		jdbcTemplate.update(deleteBandTrackPerformerQuery, trackCatalogNum, performerAlias);
	}

	@Transactional
	@Override
	public List<TrackArtistDto> searchTrackArtist(String query) {
		return jdbcTemplate.query(query, trackArtistDtoRowMapper);
	}

	@Transactional
	@Override
	public List<TrackBandDto> searchTrackBand(String query) {
		return jdbcTemplate.query(query, trackBandDtoRowMapper);
	}

}
