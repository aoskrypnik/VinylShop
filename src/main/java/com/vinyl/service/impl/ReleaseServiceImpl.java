package com.vinyl.service.impl;

import com.vinyl.dao.ReleaseDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.exception.ReleaseAlreadyExistException;
import com.vinyl.model.Release;
import com.vinyl.service.ReleaseService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class ReleaseServiceImpl implements ReleaseService {

	private static final String RELEASE_TABLE_NAME = "release";

	@Resource
	private ReleaseDao releaseDao;

	@Override
	public void save(Release release) throws ReleaseAlreadyExistException {
		String barcode = release.getReleaseBarcode();
		Release alreadyExistingRelease = getReleaseByBarcode(barcode);
		if (nonNull(alreadyExistingRelease)) {
			throw new ReleaseAlreadyExistException("Release already exist with such barcode: " + barcode);
		}
		releaseDao.save(release);
	}

	@Override
	public Release getReleaseByBarcode(String barcode) {
		return releaseDao.getReleaseByBarcode(barcode);
	}

	@Override
	public List<Release> searchReleases(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), RELEASE_TABLE_NAME);
		return releaseDao.searchReleases(query);
	}

	@Override
	public void update(Release release, String releaseBarCode) {
		releaseDao.update(release, releaseBarCode);
	}
}
