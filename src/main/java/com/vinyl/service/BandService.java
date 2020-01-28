package com.vinyl.service;

import com.vinyl.model.Band;

import java.util.List;

public interface BandService {

	void save(Band band);

	List<Band> getAll();

	Band getBandByAlias(String alias);

	List<Band> searchBands(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
						   List<String> joins, String sorting, String order);

	void deleteBand(String alias);
}
