package com.vinyl.service;

import com.vinyl.exception.ReleaseAlreadyExistException;
import com.vinyl.model.Release;

import java.util.List;

public interface ReleaseService {

	void save(Release release) throws ReleaseAlreadyExistException;

	Release getReleaseByBarcode(String barcode);

	List<Release> searchReleases(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
								 List<String> joins, String sorting, String order, Integer limit, Integer offset);

	public void update(Release release, String releaseBarCode);
}
