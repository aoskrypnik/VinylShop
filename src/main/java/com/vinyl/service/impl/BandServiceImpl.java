package com.vinyl.service.impl;

import com.vinyl.dao.BandDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.exception.BandExistException;
import com.vinyl.model.Band;
import com.vinyl.service.BandService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class BandServiceImpl implements BandService {

	private static final String BAND_TABLE_NAME = "band";
	private static final String BAND_ALREADY_EXIST = "Band already exist with such alias: ";

	@Resource
	private BandDao bandDao;

	@Override
	public void save(Band band) throws BandExistException {
		String bandAlias = band.getBandAlias();
		Band foundBand = bandDao.getBandByAlias(bandAlias);
		if (nonNull(foundBand)) {
			throw new BandExistException(BAND_ALREADY_EXIST + bandAlias);
		}
		bandDao.save(band);
	}

	@Override
	public Band getBandByAlias(String alias) {
		return bandDao.getBandByAlias(alias);
	}

	@Override
	public List<Band> searchBands(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWhereParams(), searchDto.getLikeParams(), searchDto.getBetweenParams(),
						searchDto.getJoins(), searchDto.getSorting(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), BAND_TABLE_NAME);
		return bandDao.searchBands(query);
	}

	@Override
	public void deleteBand(String alias) {
		bandDao.deleteBand(alias);
	}

	@Override
	public void update(Band band, String alias) {
		bandDao.update(band, alias);
	}
}
