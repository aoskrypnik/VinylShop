package com.vinyl.service.impl;

import com.vinyl.dao.TrackPerformerDao;
import com.vinyl.dto.TrackPerformerDto;
import com.vinyl.service.TrackPerformerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TrackPerformerServiceImpl implements TrackPerformerService {

	@Resource
	private TrackPerformerDao trackPerformerDao;

	@Override
	public void save(TrackPerformerDto trackPerformerDto) {
		if (trackPerformerDto.getIsArtist()) {
			trackPerformerDao.saveTrackForArtist(trackPerformerDto);
		} else {
			trackPerformerDao.saveTrackForBand(trackPerformerDto);
		}
	}

	@Override
	public void update(TrackPerformerDto trackPerformerDto) {
		if (trackPerformerDto.getIsArtist()) {
			trackPerformerDao.updateTrackForArtist(trackPerformerDto);
		} else {
			trackPerformerDao.updateTrackForBand(trackPerformerDto);
		}
	}
}
