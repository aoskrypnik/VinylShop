package com.vinyl.service;

import com.vinyl.dto.ArtistBandDto;

import java.util.List;

public interface ArtistBandService {

	void save(ArtistBandDto artistBandDto);

	List<ArtistBandDto> getAll();

	void update(ArtistBandDto artistBandDto);

}
