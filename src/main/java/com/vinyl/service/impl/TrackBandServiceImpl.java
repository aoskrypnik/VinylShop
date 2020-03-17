package com.vinyl.service.impl;

import com.vinyl.dao.TrackPerformerDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackArtistDto;
import com.vinyl.service.TrackArtistService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TrackArtistServiceImpl implements TrackArtistService {

	private static final String ARTIST_TO_TRACK_TABLE_NAME = "artist2track";
	private static final String BAND_TO_TRACK_TABLE_NAME = "band2track";

	@Resource
	private TrackPerformerDao trackPerformerDao;

	@Override
	public void save(TrackArtistDto trackPerformerDto) {
		if (trackPerformerDto.getIsArtist()) {
			trackPerformerDao.saveTrackForArtist(trackPerformerDto);
		} else {
			trackPerformerDao.saveTrackForBand(trackPerformerDto);
		}
	}

	@Override
	public void update(TrackArtistDto trackPerformerDto) {
		if (trackPerformerDto.getIsArtist()) {
			trackPerformerDao.updateTrackForArtist(trackPerformerDto);
		} else {
			trackPerformerDao.updateTrackForBand(trackPerformerDto);
		}
	}

	@Override
	public TrackArtistDto getTrackArtistByTrackNameAndPerformerAlias(String trackAndArtistName) {
		String[] names = trackAndArtistName.split(",");
		String trackCatalogNum = names[0];
		String performerAlias = names[1];
		return trackPerformerDao.getTrackPerformerByTrackNameAndArtistAlias(trackCatalogNum, performerAlias);
	}

	@Override
	public void deleteTrackArtistInstance(TrackArtistDto trackPerformerDtoToDelete) {
		trackPerformerDao.deleteTrackArtistInstance(trackPerformerDtoToDelete);
	}

	@Override
	public List<TrackArtistDto> searchArtistTrackPerformance(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), ARTIST_TO_TRACK_TABLE_NAME);
		return trackPerformerDao.searchTrackPerformance(query);
	}

	@Override
	public List<TrackArtistDto> searchBandTrackPerformance(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), BAND_TO_TRACK_TABLE_NAME);
		return trackPerformerDao.searchTrackPerformance(query);
	}
}
