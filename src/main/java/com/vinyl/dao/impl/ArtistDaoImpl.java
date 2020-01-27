package com.vinyl.dao.impl;

import com.vinyl.dao.ArtistDao;
import com.vinyl.dto.ArtistBandMembershipDto;
import com.vinyl.model.Artist;
import com.vinyl.model.Band;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class ArtistDaoImpl implements ArtistDao {

	@Value("${sql.artist.create.artist.query.path}")
	private String createArtistQueryPath;
	@Value("${sql.artist.get.artist.by.alias.query.path}")
	private String getArtistByAliasQueryPath;
	@Value("${sql.artist.get.all.artists.query.path}")
	private String getAllArtistsQueryPath;
	@Value("${sql.artist.update.artist.query.path}")
	private String updateArtistQueryPath;
	@Value("${sql.artist.get.all.bands.in.certain.period.membership}")
	private String getAllBandsInCertainPeriodMembershipQueryPath;
	@Value("${sql.artist.get.artists.by.country.query.path}")
	private String getArtistsByCountryQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Band> bandRowMapper;
	@Resource
	private RowMapper<Artist> artistRowMapper;

	@Override
	public String save(Artist artist) {
		String createArtistQuery = QuerySupplier.getQuery(createArtistQueryPath);
		jdbcTemplate.update(createArtistQuery, artist.getAlias(), artist.getIsActive(), artist.getCountryCode(),
				artist.getName(), artist.getBirthDate(), artist.getDeathDate());
		return artist.getAlias();
	}

	@Override
	public Artist getArtistByAlias(String alias) {
		String getArtistByAliasQuery = QuerySupplier.getQuery(getArtistByAliasQueryPath);
		List<Artist> queryResult = jdbcTemplate.query(getArtistByAliasQuery, new Object[]{alias}, artistRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Override
	public void update(Artist artist) {
		String updateArtistQuery = QuerySupplier.getQuery(updateArtistQueryPath);
		jdbcTemplate.update(updateArtistQuery, artist.getIsActive(), artist.getCountryCode(),
				artist.getDeathDate(), artist.getAlias());
	}

	@Override
	public List<Artist> getAll() {
		String getAllArtistsQuery = QuerySupplier.getQuery(getAllArtistsQueryPath);
		return jdbcTemplate.queryForList(getAllArtistsQuery, Artist.class);
	}

	@Override
	public List<Band> getBandsArtistWasMemberInCertainPeriod(ArtistBandMembershipDto artistBandMembershipDto) {
		String getBandsInCertainPeriodMembershipQuery =
				QuerySupplier.getQuery(getAllBandsInCertainPeriodMembershipQueryPath);

		String alias = artistBandMembershipDto.getArtistAlias();
		Date startDate = artistBandMembershipDto.getStartMembership();
		Date endDate = artistBandMembershipDto.getEndMembership();

		return jdbcTemplate.query(getBandsInCertainPeriodMembershipQuery,
				new Object[]{alias, startDate, endDate}, bandRowMapper);
	}

	@Override
	public List<Artist> getArtistsByCountryCode(String countryCode) {
		String getArtistsByCountryQuery = QuerySupplier.getQuery(getArtistsByCountryQueryPath);
		return jdbcTemplate.query(getArtistsByCountryQuery, new Object[]{countryCode}, artistRowMapper);
	}

	@Override
	public List<Artist> searchArtists(String query) {
		return jdbcTemplate.queryForList(query, Artist.class);
	}

}
