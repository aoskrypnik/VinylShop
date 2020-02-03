package com.vinyl.service;

import com.vinyl.exception.ReleaseAlreadyExistException;
import com.vinyl.model.Release;

import java.util.List;

public interface ReleaseService {

	String save(Release release) throws ReleaseAlreadyExistException;

	Release getReleaseByBarcode(String barcode);

	List<Release> getAll();

	List<Release> searchReleases(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
								 List<String> joins, String sorting, String order);

	public void update(Release release, String releaseBarCode);
}
