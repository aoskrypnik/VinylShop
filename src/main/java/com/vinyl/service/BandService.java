package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.BandExistException;
import com.vinyl.model.Band;

import java.util.List;

public interface BandService {

	void save(Band band) throws BandExistException;

	Band getBandByAlias(String alias);

	List<Band> searchBands(SearchDto searchDto);

	void deleteBand(String alias);

	void update(Band band, String alias);
}
