package com.vinyl.dao;

import com.vinyl.dto.ArtistBandDto;

import java.util.List;

public interface ArtistBandDao {

	void save (ArtistBandDto artistBandDto);

	List<ArtistBandDto> getAll();

	void update(ArtistBandDto artistBandDto);

}
