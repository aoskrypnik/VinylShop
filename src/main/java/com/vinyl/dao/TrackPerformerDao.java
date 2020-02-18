package com.vinyl.dao;

import com.vinyl.dto.TrackPerformerDto;

public interface TrackPerformerDao {
	void saveTrackForArtist(TrackPerformerDto trackPerformerDto);

	void saveTrackForBand(TrackPerformerDto trackPerformerDto);

	void updateTrackForArtist(TrackPerformerDto trackPerformerDto);

	void updateTrackForBand(TrackPerformerDto trackPerformerDto);

	TrackPerformerDto getTrackPerformerByTrackNameAndPerformerAlias(String trackName, String performerAlias);

	void deleteTrackPerformanceInstance(TrackPerformerDto trackPerformerDto);
}
