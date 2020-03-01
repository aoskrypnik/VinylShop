package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackPerformerDto;

import java.util.List;

public interface TrackPerformerService {
	void save(TrackPerformerDto trackPerformerDto);

	void update(TrackPerformerDto trackPerformerDto);

	TrackPerformerDto getTrackPerformerByTrackNameAndPerformerAlias(String trackAndPerformerName);

	void deleteTrackPerformanceInstance(TrackPerformerDto trackPerformerDtoToDelete);

	List<TrackPerformerDto> searchTrackPerformance(SearchDto searchDto);
}
