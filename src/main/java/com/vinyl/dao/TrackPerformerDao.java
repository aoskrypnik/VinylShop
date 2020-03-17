package com.vinyl.dao;

import com.vinyl.dto.TrackArtistDto;
import com.vinyl.dto.TrackBandDto;

import java.util.List;

public interface TrackPerformerDao {
	void save(TrackArtistDto trackArtistDto);

	void save(TrackBandDto trackBandDto);

	void update(TrackArtistDto trackArtistDto);

	void update(TrackBandDto trackBandDto);

	TrackArtistDto getTrackArtistByTrackNameAndArtistAlias(String trackName, String artistAlias);

	TrackBandDto getTrackBandByTrackNameAndBandAlias(String trackName, String bandAlias);

	void deleteTrackArtistInstance(TrackArtistDto trackArtistDto);

	void deleteTrackBandInstance(TrackBandDto trackBandDto);

	List<TrackArtistDto> searchTrackArtist(String query);

	List<TrackBandDto> searchTrackBand(String query);
}
