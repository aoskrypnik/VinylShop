package com.vinyl.service;

import com.vinyl.dto.ArtistBandDto;
import com.vinyl.dto.SearchDto;

import java.util.List;

public interface ArtistBandService {

	void save(ArtistBandDto artistBandDto);

	void update(ArtistBandDto artistBandDto);

	ArtistBandDto getArtistBandByPks(String ids);

	List<ArtistBandDto> searchArtistBands(SearchDto searchDto);

	void deleteByIds(String ids);
}
