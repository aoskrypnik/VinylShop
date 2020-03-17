package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackArtistDto;

import java.util.List;

public interface TrackArtistService {
	void save(TrackArtistDto trackPerformerDto);

	void update(TrackArtistDto trackPerformerDto);

	TrackArtistDto getTrackArtistByTrackNameAndPerformerAlias(String trackAndArtistName);

	void deleteTrackArtistInstance(TrackArtistDto trackPerformerDtoToDelete);

	List<TrackArtistDto> searchArtistTrackPerformance(SearchDto searchDto);

	List<TrackArtistDto> searchBandTrackPerformance(SearchDto searchDto);
}
