package com.vinyl.dao;

import com.vinyl.dto.TrackPerformerDto;

public interface TrackPerformerDao {
	void saveTrackForArtist(TrackPerformerDto trackPerformerDto);

	void saveTrackForBand(TrackPerformerDto trackPerformerDto);
}
