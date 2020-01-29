package com.vinyl.dao.impl;

import com.vinyl.dao.ArtistBandDao;
import com.vinyl.dto.ArtistBandDto;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class ArtistBandDaoImpl implements ArtistBandDao {

	@Value("${sql.artist2band.create.artist.to.band.query.path}")
	private String createArtistToBandQueryPath;
	@Value("${sql.artist2band.get.all.artist.to.band.query.path}")
	private String getAllArtistToBandQueryPath;
	@Value("${sql.artist2band.update.artist.to.band.query.path}")
	private String updateArtistToBandQueryPath;

	@Resource
	private RowMapper<ArtistBandDto> artistToBandRowMapper;
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public void save(ArtistBandDto artistBandDto) {
		String createArtistToBandQuery = QuerySupplier.getQuery(createArtistToBandQueryPath);
		jdbcTemplate.update(createArtistToBandQuery, artistBandDto.getArtistAlias(),
				artistBandDto.getBandAlias(), artistBandDto.getJoinDate(), artistBandDto.getExitDate());
	}

	@Override
	public List<ArtistBandDto> getAll() {
		String getAllArtistToBandQuery = QuerySupplier.getQuery(getAllArtistToBandQueryPath);
		return jdbcTemplate.query(getAllArtistToBandQuery, artistToBandRowMapper);
	}

	@Override
	public void update(ArtistBandDto artistBandDto) {
		String updateArtistToBandQuery = QuerySupplier.getQuery(updateArtistToBandQueryPath);

		String artistAlias = artistBandDto.getArtistAlias();
		String bandAlias = artistBandDto.getBandAlias();
		Date joinDate = artistBandDto.getJoinDate();
		Date exitDate = artistBandDto.getExitDate();
		jdbcTemplate.update(updateArtistToBandQuery, exitDate, artistAlias, bandAlias, joinDate);
	}

}
