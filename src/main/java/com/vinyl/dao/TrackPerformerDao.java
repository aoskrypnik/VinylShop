package com.vinyl.dao;

import com.vinyl.dto.TrackPerformerDto;

import java.util.List;

public interface TrackPerformerDao {
	void saveTrackForArtist(TrackPerformerDto trackPerformerDto);

	void saveTrackForBand(TrackPerformerDto trackPerformerDto);

	void updateTrackForArtist(TrackPerformerDto trackPerformerDto);

	void updateTrackForBand(TrackPerformerDto trackPerformerDto);

	TrackPerformerDto getTrackPerformerByTrackNameAndArtistAlias(String trackName, String artistAlias);

	TrackPerformerDto getTrackPerformerByTrackNameAndBandAlias(String trackName, String bandAlias);

	void deleteTrackArtistInstance(TrackPerformerDto trackPerformerDto);

	void deleteTrackBandInstance(TrackPerformerDto trackPerformerDto);

	List<TrackPerformerDto> searchTrackPerformance(String query);
}
