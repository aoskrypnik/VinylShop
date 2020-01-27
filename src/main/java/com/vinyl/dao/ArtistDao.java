package com.vinyl.dao;

import com.vinyl.dto.ArtistBandMembershipDto;
import com.vinyl.model.Artist;
import com.vinyl.model.Band;

import java.util.List;

public interface ArtistDao {
	String save(Artist artist);

	Artist getArtistByAlias(String alias);

	void update(Artist artist);

	List<Artist> getAll();

	List<Band> getBandsArtistWasMemberInCertainPeriod(ArtistBandMembershipDto artistBandMembershipDto);

	List<Artist> getArtistsByCountryCode(String countryCode);

	List<Artist> searchArtists(String query);
}
