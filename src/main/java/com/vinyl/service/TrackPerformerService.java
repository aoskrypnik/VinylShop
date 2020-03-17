package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackArtistDto;
import com.vinyl.dto.TrackBandDto;

import java.util.List;

public interface TrackPerformerService {
	void save(TrackArtistDto trackArtistDto);

	void save(TrackBandDto trackBandDto);

	void update(TrackArtistDto trackArtistDto);

	void update(TrackBandDto trackBandDto);

	TrackArtistDto getTrackArtistByTrackNumAndArtistAlias(String trackNumAndArtistAlias);

	TrackBandDto getTrackBandByTrackNumAndBandAlias(String trackNumAndBandAlias);

	void deleteTrackArtist(TrackArtistDto trackArtistDto);

	void deleteTrackBand(TrackBandDto trackBandDto);

	List<TrackArtistDto> searchArtistTrackPerformance(SearchDto searchDto);

	List<TrackBandDto> searchBandTrackPerformance(SearchDto searchDto);

	boolean isNotEqualTrackNums(String trackAndAlias, String trackNumFromDto);

	boolean isNotEqualArtistAliases(String trackAndAlias, String aliasFromDto);
}
