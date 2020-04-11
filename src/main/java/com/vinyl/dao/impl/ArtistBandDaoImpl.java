package com.vinyl.dao.impl;

import com.vinyl.dao.ArtistBandDao;
import com.vinyl.dto.ArtistBandDto;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class ArtistBandDaoImpl implements ArtistBandDao {

	@Value("${sql.artist2band.create.artist.to.band.query.path}")
	private String createArtistToBandQueryPath;
	@Value("${sql.artist2band.update.artist.to.band.query.path}")
	private String updateArtistToBandQueryPath;
	@Value("${sql.artist2band.get.artist.to.band.query.path}")
	private String getArtistBandByIdsQueryPath;
	@Value("${sql.artist2band.delete.artist.to.band.query.path}")
	private String deleteArtistBandQueryPath;

	@Resource
	private RowMapper<ArtistBandDto> artistToBandRowMapper;
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public void save(ArtistBandDto artistBandDto) {
		String createArtistToBandQuery = QuerySupplier.getQuery(createArtistToBandQueryPath);
		jdbcTemplate.update(createArtistToBandQuery, artistBandDto.getParticipationArtistAlias(),
				artistBandDto.getParticipationBandAlias(), artistBandDto.getJoinDate(), artistBandDto.getExitDate());
	}

	@Override
	public void update(ArtistBandDto artistBandDto) {
		String updateArtistToBandQuery = QuerySupplier.getQuery(updateArtistToBandQueryPath);

		String artistAlias = artistBandDto.getParticipationArtistAlias();
		String bandAlias = artistBandDto.getParticipationBandAlias();
		Date joinDate = artistBandDto.getJoinDate();
		Date exitDate = artistBandDto.getExitDate();

		jdbcTemplate.update(updateArtistToBandQuery, joinDate, exitDate, artistAlias, bandAlias);
	}

	@Transactional
	@Override
	public ArtistBandDto getArtistBandByPks(String artistAlias, String bandAlias) {
		String getArtistBandByIdsQuery = QuerySupplier.getQuery(getArtistBandByIdsQueryPath);
		List<ArtistBandDto> queryResult = jdbcTemplate
				.query(getArtistBandByIdsQuery, new Object[]{artistAlias, bandAlias}, artistToBandRowMapper);
		return isEmpty(queryResult) ? null : queryResult.get(0);
	}

	@Transactional
	@Override
	public List<ArtistBandDto> searchArtistBands(String query) {
		return jdbcTemplate.query(query, artistToBandRowMapper);
	}

	@Override
	public void deleteByIds(String id1, String id2) {
		String deleteArtistBandQuery = QuerySupplier.getQuery(deleteArtistBandQueryPath);

		jdbcTemplate.update(deleteArtistBandQuery, id1, id2);
	}

}
