package com.vinyl.service;

import com.vinyl.dto.TrackPerformerDto;

public interface TrackPerformerService {
	void save (TrackPerformerDto trackPerformerDto);

	void update(TrackPerformerDto trackPerformerDto);

	TrackPerformerDto getTrackPerformerByTrackNameAndPerformerAlias(String trackAndPerformerName);

	void deleteTrackPerformanceInstance(TrackPerformerDto trackPerformerDtoToDelete);
}
