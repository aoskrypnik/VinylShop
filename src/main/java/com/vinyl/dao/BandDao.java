package com.vinyl.dao;

import com.vinyl.model.Band;

import java.util.List;

public interface BandDao {

	String save(Band band);

	Band getBandByAlias(String alias);

	void update(Band band);

	List<Band> getAll();

	List<Band> searchBands(String query);
}
