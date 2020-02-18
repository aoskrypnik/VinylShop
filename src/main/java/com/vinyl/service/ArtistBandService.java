package com.vinyl.service;

import com.vinyl.dto.ArtistBandDto;

import java.util.List;

public interface ArtistBandService {

	void save(ArtistBandDto artistBandDto);

	void update(ArtistBandDto artistBandDto);

	ArtistBandDto getArtistBandByPks(String ids);

	List<ArtistBandDto> searchArtistBands(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
										  List<String> joins, String sorting, String order, Integer limit, Integer offset);

	void deleteByIds(String ids);
}
