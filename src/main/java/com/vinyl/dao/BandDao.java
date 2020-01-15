package com.vinyl.dao;

import com.vinyl.model.Band;

import java.util.List;

public interface BandDao {

	List<Band> getCurrentGroupsByArtistAlias(String artistAlias);

	List<Band> getPreviousGroupsByArtistAlias(String artistAlias);

}
