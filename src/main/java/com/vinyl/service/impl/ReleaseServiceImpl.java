package com.vinyl.service.impl;

import com.vinyl.dao.ReleaseDao;
import com.vinyl.model.Release;
import com.vinyl.service.ReleaseService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {

	private static final String RELEASE_TABLE_NAME = "release";
	@Resource
	private ReleaseDao releaseDao;

	@Override
	public String save(Release release) {
		return releaseDao.save(release);
	}

	@Override
	public Release getReleaseByBarcode(String barcode) {
		return releaseDao.getReleaseByBarcode(barcode);
	}

	@Override
	public List<Release> getAll() {
		return releaseDao.getAll();
	}

	@Override
	public List<Release> searchReleases(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
										List<String> joins, String sorting, String order) {
		String query = QueryBuilder.build(whereParams, likeParams, betweenParams, joins, sorting, order, RELEASE_TABLE_NAME);
		return releaseDao.searchReleases(query);
	}
}
