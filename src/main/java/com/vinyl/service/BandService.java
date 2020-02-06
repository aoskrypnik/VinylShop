package com.vinyl.service;

import com.vinyl.exception.ArtistExistException;
import com.vinyl.exception.BandExistException;
import com.vinyl.model.Band;

import java.util.List;

public interface BandService {

	String save(Band band) throws BandExistException;

	List<Band> getAll();

	Band getBandByAlias(String alias);

	List<Band> searchBands(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
						   List<String> joins, String sorting, String order);

	void deleteBand(String alias);

	void update(Band band, String alias);
}
