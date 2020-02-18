package com.vinyl.dao;

import com.vinyl.dto.ArtistBandDto;

import java.util.List;

public interface ArtistBandDao {

	void save(ArtistBandDto artistBandDto);

	void update(ArtistBandDto artistBandDto);

	ArtistBandDto getArtistBandByPks(String id1, String id2);

	List<ArtistBandDto> searchArtistBands(String query);

	void deleteByIds(String id1, String id2);
}
