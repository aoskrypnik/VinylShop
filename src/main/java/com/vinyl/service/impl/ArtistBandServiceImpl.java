package com.vinyl.service.impl;

import com.vinyl.dao.ArtistBandDao;
import com.vinyl.dto.ArtistBandDto;
import com.vinyl.service.ArtistBandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArtistBandServiceImpl implements ArtistBandService {

	@Resource
	private ArtistBandDao artistBandDao;

	@Override
	public void save(ArtistBandDto artistBandDto) {
		artistBandDao.save(artistBandDto);
	}

	@Override
	public List<ArtistBandDto> getAll() {
		return artistBandDao.getAll();
	}

	@Override
	public void update(ArtistBandDto artistBandDto) {
		artistBandDao.update(artistBandDto);
	}

}
