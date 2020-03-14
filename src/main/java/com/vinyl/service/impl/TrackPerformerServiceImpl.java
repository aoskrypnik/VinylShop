package com.vinyl.service.impl;

import com.vinyl.dao.TrackPerformerDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackPerformerDto;
import com.vinyl.service.TrackPerformerService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TrackPerformerServiceImpl implements TrackPerformerService {

	private static final String ARTIST_TO_TRACK_TABLE_NAME = "artist2track";
	private static final String BAND_TO_TRACK_TABLE_NAME = "band2track";

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
	public TrackPerformerDto getTrackPerformerByTrackNameAndPerformerAlias(String trackAndArtistName, boolean isArtist) {
		String[] names = trackAndArtistName.split(",");
		String trackCatalogNum = names[0];
		String performerAlias = names[1];
		if (isArtist) {
			return trackPerformerDao.getTrackPerformerByTrackNameAndArtistAlias(trackCatalogNum, performerAlias);
		} else {
			return trackPerformerDao.getTrackPerformerByTrackNameAndBandAlias(trackCatalogNum, performerAlias);
		}
	}

	@Override
	public void deleteTrackPerformanceInstance(TrackPerformerDto trackPerformerDtoToDelete) {
		if (trackPerformerDtoToDelete.getIsArtist()) {
			trackPerformerDao.deleteTrackArtistInstance(trackPerformerDtoToDelete);
		} else {
			trackPerformerDao.deleteTrackBandInstance(trackPerformerDtoToDelete);
		}
	}

	@Override
	public List<TrackPerformerDto> searchArtistTrackPerformance(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), ARTIST_TO_TRACK_TABLE_NAME);
		return trackPerformerDao.searchTrackPerformance(query);
	}

	@Override
	public List<TrackPerformerDto> searchBandTrackPerformance(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), BAND_TO_TRACK_TABLE_NAME);
		return trackPerformerDao.searchTrackPerformance(query);
	}
}
