package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.ReleaseAlreadyExistException;
import com.vinyl.model.Release;

import java.util.List;

public interface ReleaseService {

	void save(Release release) throws ReleaseAlreadyExistException;

	Release getReleaseByBarcode(String barcode);

	List<Release> searchReleases(SearchDto searchDto);

	void update(Release release, String releaseBarCode);
}
