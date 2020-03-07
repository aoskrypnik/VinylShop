package com.vinyl.dao.impl;

import com.vinyl.dao.TrackPerformerDao;
import com.vinyl.dto.TrackPerformerDto;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TrackPerformerDaoImpl implements TrackPerformerDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<TrackPerformerDto> trackPerformerRowMapper;

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
	public void saveTrackForArtist(TrackPerformerDto trackPerformerDto) {
		String query = QuerySupplier.getQuery(createArtistTrackPerformanceQueryPath);
		jdbcTemplate.update(query, trackPerformerDto.getTrackCatalogNum(), trackPerformerDto.getPerformerAlias(),
				trackPerformerDto.getIsFeaturing());
	}

	@Override
	public void saveTrackForBand(TrackPerformerDto trackPerformerDto) {
		String query = QuerySupplier.getQuery(createBandTrackPerformanceQueryPath);
		jdbcTemplate.update(query, trackPerformerDto.getTrackCatalogNum(), trackPerformerDto.getPerformerAlias(),
				trackPerformerDto.getIsFeaturing());
	}

	@Override
	public void updateTrackForArtist(TrackPerformerDto trackPerformerDto) {
		String updateArtistTrackPerformanceQuery = QuerySupplier.getQuery(updateArtistTrackPerformanceQueryPath);
		jdbcTemplate.update(updateArtistTrackPerformanceQuery, trackPerformerDto.getIsFeaturing(), trackPerformerDto.getPerformerAlias());
	}

	@Override
	public void updateTrackForBand(TrackPerformerDto trackPerformerDto) {
		String updateBandTrackPerformanceQuery = QuerySupplier.getQuery(updateBandTrackPerformanceQueryPath);
		jdbcTemplate.update(updateBandTrackPerformanceQuery, trackPerformerDto.getIsFeaturing(), trackPerformerDto.getPerformerAlias());
	}

	@Override
	public TrackPerformerDto getTrackPerformerByTrackNameAndPerformerAlias(String trackName, String performerAlias) {
		String getArtistTrackPerformanceQuery = QuerySupplier.getQuery(getArtistTrackPerformanceQueryPath);
		String getBandTrackPerformanceQuery = QuerySupplier.getQuery(getBandTrackPerformanceQueryPath);

		List<TrackPerformerDto> trackPerformanceInstances =
				getTrackPerformerInstancesByName(getArtistTrackPerformanceQuery, trackName, performerAlias);

		if (trackPerformanceInstances.size() == 0) {
			trackPerformanceInstances = getTrackPerformerInstancesByName(getBandTrackPerformanceQuery, trackName, performerAlias);
			return trackPerformanceInstances.size() == 0 ? null : trackPerformanceInstances.get(0);
		} else {
			return trackPerformanceInstances.get(0);
		}
	}

	@Override
	public void deleteTrackPerformanceInstance(TrackPerformerDto trackPerformerDto) {
		String deleteArtistTrackPerformerQuery = QuerySupplier.getQuery(deleteArtistTrackPerformerQueryPath);
		String deleteBandTrackPerformerQuery = QuerySupplier.getQuery(deleteBandTrackPerformerQueryPath);

		String trackCatalogNum = trackPerformerDto.getTrackCatalogNum();
		String performerAlias = trackPerformerDto.getPerformerAlias();

		if (trackPerformerDto.getIsArtist()) {
			jdbcTemplate.update(deleteArtistTrackPerformerQuery, trackCatalogNum, performerAlias);
		} else {
			jdbcTemplate.update(deleteBandTrackPerformerQuery, trackCatalogNum, performerAlias);
		}
	}

	@Transactional
	@Override
	public List<TrackPerformerDto> searchTrackPerformance(String query) {
		return jdbcTemplate.query(query, trackPerformerRowMapper);
	}

	private List<TrackPerformerDto> getTrackPerformerInstancesByName(String query, String trackName, String performerAlias) {
		return jdbcTemplate.query(query, new Object[]{trackName, performerAlias}, trackPerformerRowMapper);
	}

}
