package com.vinyl.service.impl;

import com.vinyl.dao.TrackPerformerDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackArtistDto;
import com.vinyl.dto.TrackBandDto;
import com.vinyl.service.TrackPerformerService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
public class TrackPerformerServiceImpl implements TrackPerformerService {

	private static final String ARTIST_TO_TRACK_TABLE_NAME = "artist2track";
	private static final String BAND_TO_TRACK_TABLE_NAME = "band2track";

	@Resource
	private TrackPerformerDao trackPerformerDao;

	@Override
	public void save(TrackArtistDto trackArtistDto) {
		trackPerformerDao.save(trackArtistDto);
	}

	@Override
	public void save(TrackBandDto trackBandDto) {
		trackPerformerDao.save(trackBandDto);
	}

	@Override
	public void update(TrackArtistDto trackArtistDto) {
		trackPerformerDao.update(trackArtistDto);
	}

	@Override
	public void update(TrackBandDto trackBandDto) {
		trackPerformerDao.update(trackBandDto);
	}

	@Override
	public TrackArtistDto getTrackArtistByTrackNumAndArtistAlias(String trackNumAndArtistAlias) {
		String[] names = trackNumAndArtistAlias.split("@");
		String trackCatalogNum = names[0];
		String artistAlias = names[1];
		return trackPerformerDao.getTrackArtistByTrackNameAndArtistAlias(trackCatalogNum, artistAlias);
	}

	@Override
	public TrackBandDto getTrackBandByTrackNumAndBandAlias(String trackNumAndBandAlias) {
		String[] names = trackNumAndBandAlias.split("@");
		String trackCatalogNum = names[0];
		String bandAlias = names[1];
		return trackPerformerDao.getTrackBandByTrackNameAndBandAlias(trackCatalogNum, bandAlias);
	}

	@Override
	public void deleteTrackArtist(TrackArtistDto trackArtistDto) {
		trackPerformerDao.deleteTrackArtistInstance(trackArtistDto);
	}

	@Override
	public void deleteTrackBand(TrackBandDto trackBandDto) {
		trackPerformerDao.deleteTrackBandInstance(trackBandDto);
	}

	@Override
	public List<TrackArtistDto> searchArtistTrackPerformance(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), ARTIST_TO_TRACK_TABLE_NAME);
		return trackPerformerDao.searchTrackArtist(query);
	}

	@Override
	public List<TrackBandDto> searchBandTrackPerformance(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), BAND_TO_TRACK_TABLE_NAME);
		return trackPerformerDao.searchTrackBand(query);
	}

	@Override
	public boolean isNotEqualTrackNums(String trackAndAlias, String trackNumFromDto) {
		String trackNum = trackAndAlias.split("@")[0];
		return isFalse(trackNum.equals(trackNumFromDto));
	}

	@Override
	public boolean isNotEqualArtistAliases(String trackAndAlias, String aliasFromDto) {
		String alias = trackAndAlias.split("@")[1];
		return isFalse(alias.equals(aliasFromDto));
	}
}
