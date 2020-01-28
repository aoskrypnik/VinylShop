package com.vinyl.service.impl;

import com.vinyl.dao.BandDao;
import com.vinyl.model.Band;
import com.vinyl.service.BandService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BandServiceImpl implements BandService {

	private static final String BAND_TABLE_NAME = "band";
	@Resource
	private BandDao bandDao;

	@Override
	public void save(Band band) {
		bandDao.save(band);
	}

	@Override
	public List<Band> getAll() {
		return bandDao.getAll();
	}

	@Override
	public Band getBandByAlias(String alias) {
		return bandDao.getBandByAlias(alias);
	}

	@Override
	public List<Band> searchBands(List<String> whereParams, List<String> likeParams, List<String> betweenParams, List<String> joins, String sorting, String order) {
		String query = QueryBuilder.build(whereParams, likeParams, betweenParams, joins, sorting, order, BAND_TABLE_NAME);
		return bandDao.searchBands(query);
	}
}
