package com.vinyl.service.impl;

import com.vinyl.dao.TrackPerformerDao;
import com.vinyl.dto.TrackPerformerDto;
import com.vinyl.service.TrackPerformerService;
import com.vinyl.utils.QueryBuilder;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TrackPerformerServiceImpl implements TrackPerformerService {

	@Value("${sql.union.track2artist.with.track2band.query.path}")
	private String unionTrack2ArtistWithTrack2BandQueryPath;
	@Resource
	private TrackPerformerDao trackPerformerDao;

	@Override
	public void save(TrackPerformerDto trackPerformerDto) {
		if (trackPerformerDto.getIsArtist()) {
			trackPerformerDao.saveTrackForArtist(trackPerformerDto);
		} else {
			trackPerformerDao.saveTrackForBand(trackPerformerDto);
		}
	}

	@Override
	public void update(TrackPerformerDto trackPerformerDto) {
		if (trackPerformerDto.getIsArtist()) {
			trackPerformerDao.updateTrackForArtist(trackPerformerDto);
		} else {
			trackPerformerDao.updateTrackForBand(trackPerformerDto);
		}
	}

	@Override
	public TrackPerformerDto getTrackPerformerByTrackNameAndPerformerAlias(String trackAndPerformerName) {
		String[] names = trackAndPerformerName.split(",");
		String trackCatalogNum = names[0];
		String performerAlias = names[1];
		return trackPerformerDao.getTrackPerformerByTrackNameAndPerformerAlias(trackCatalogNum, performerAlias);
	}

	@Override
	public void deleteTrackPerformanceInstance(TrackPerformerDto trackPerformerDtoToDelete) {
		trackPerformerDao.deleteTrackPerformanceInstance(trackPerformerDtoToDelete);
	}

	@Override
	public List<TrackPerformerDto> searchTrackPerformance(List<String> whereParams, List<String> likeParams,
														  List<String> betweenParams, List<String> joins, String sorting,
														  String order, Integer limit, Integer offset) {
		String unionTable = QuerySupplier.getQuery(unionTrack2ArtistWithTrack2BandQueryPath);
		String query = QueryBuilder
				.build(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset, unionTable);
		return trackPerformerDao.searchTrackPerformance(query);
	}
}
