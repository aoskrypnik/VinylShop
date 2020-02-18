package com.vinyl.service.impl;

import com.vinyl.dao.ArtistBandDao;
import com.vinyl.dto.ArtistBandDto;
import com.vinyl.service.ArtistBandService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArtistBandServiceImpl implements ArtistBandService {

	private static final String ARTIST2BAND_TABLE_NAME = "artist2band";
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
	public List<ArtistBandDto> searchArtistBands(List<String> whereParams, List<String> likeParams,
												 List<String> betweenParams, List<String> joins, String sorting,
												 String order, Integer limit, Integer offset) {
		String query = QueryBuilder
				.build(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset, ARTIST2BAND_TABLE_NAME);
		return artistBandDao.searchArtistBands(query);
	}

	@Override
	public void deleteByIds(String ids) {
		String id1 = ids.split(",")[0];
		String id2 = ids.split(",")[1];
		artistBandDao.deleteByIds(id1, id2);
	}

}
