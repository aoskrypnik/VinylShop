package com.vinyl.service.impl;

import com.vinyl.dao.ArtistBandDao;
import com.vinyl.dto.ArtistBandDto;
import com.vinyl.dto.SearchDto;
import com.vinyl.model.Artist;
import com.vinyl.model.Band;
import com.vinyl.service.ArtistBandService;
import com.vinyl.service.ArtistService;
import com.vinyl.service.BandService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
public class ArtistBandServiceImpl implements ArtistBandService {

	private static final String ARTIST2BAND_TABLE_NAME = "artist2band";

	@Resource
	private ArtistService artistService;
	@Resource
	private BandService bandService;
	@Resource
	private ArtistBandDao artistBandDao;

	@Override
	public void save(ArtistBandDto artistBandDto) {
		artistBandDao.save(artistBandDto);
	}

	@Override
	public void update(ArtistBandDto artistBandDto) {
		artistBandDao.update(artistBandDto);
	}

	@Override
	public ArtistBandDto getArtistBandByPks(String ids) {
		String id1 = ids.split(",")[0];
		String id2 = ids.split(",")[1];
		return artistBandDao.getArtistBandByPks(id1, id2);
	}

	@Override
	public List<ArtistBandDto> searchArtistBands(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), ARTIST2BAND_TABLE_NAME);
		return artistBandDao.searchArtistBands(query);
	}

	@Override
	public void deleteByIds(String ids) {
		String id1 = ids.split(",")[0];
		String id2 = ids.split(",")[1];
		artistBandDao.deleteByIds(id1, id2);
	}

	@Override
	public boolean validateArtistBand(ArtistBandDto artistBandDto) {
		Artist artist = artistService.getArtistByAlias(artistBandDto.getParticipationArtistAlias());
		Band band = bandService.getBandByAlias(artistBandDto.getParticipationBandAlias());
		Date joinDate = artistBandDto.getJoinDate();
		Date exitDate = artistBandDto.getExitDate();
		Date artistBirthDate = artist.getArtistBirthDate();
		Date artistDeathDate = artist.getArtistDeathDate();
		Date bandStartYear = band.getStartYear();
		Date bandEndYear = band.getEndYear();

		return isTrue(isNull(joinDate) || artistBirthDate.before(joinDate)) &&
				isTrue(isNull(joinDate) || bandStartYear.before(joinDate)) &&
				isTrue(isNull(artistDeathDate) || isNull(exitDate) || artistDeathDate.after(exitDate)) &&
				isTrue(isNull(bandEndYear) || isNull(exitDate) || bandEndYear.after(exitDate));
	}

}
