package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackPerformerDto;

import java.util.List;

public interface TrackPerformerService {
	void save(TrackPerformerDto trackPerformerDto);

	void update(TrackPerformerDto trackPerformerDto);

	TrackPerformerDto getTrackPerformerByTrackNameAndPerformerAlias(String trackAndPerformerName, boolean isArtist);

	void deleteTrackPerformanceInstance(TrackPerformerDto trackPerformerDtoToDelete);

	List<TrackPerformerDto> searchArtistTrackPerformance(SearchDto searchDto);

	List<TrackPerformerDto> searchBandTrackPerformance(SearchDto searchDto);
}
