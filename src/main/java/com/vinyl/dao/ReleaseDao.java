package com.vinyl.dao;

import com.vinyl.model.Release;

import java.util.List;

public interface ReleaseDao {

	String save(Release release);

	Release getReleaseByBarcode(String barcode);

	List<Release> searchReleases(String query);

	void update(Release release, String releaseBarCode);
}
