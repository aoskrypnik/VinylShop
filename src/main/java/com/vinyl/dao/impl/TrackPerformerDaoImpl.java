package com.vinyl.dao.impl;

import com.vinyl.dao.TrackPerformerDao;
import com.vinyl.dto.TrackPerformerDto;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TrackPerformerDaoImpl implements TrackPerformerDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Value("${sql.create.artist.track.performance.query.path}")
	private String createArtistTrackPerformanceQueryPath;
	@Value("${sql.create.band.track.performance.query.path}")
	private String createBandTrackPerformanceQueryPath;
	@Value("${sql.update.artist.track.performance.query.path}")
	private String updateArtistTrackPerformanceQueryPath;
	@Value("${sql.update.band.track.performance.query.path}")
	private String updateBandTrackPerformanceQueryPath;

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


}
