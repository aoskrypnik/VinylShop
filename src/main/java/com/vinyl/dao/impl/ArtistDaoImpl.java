package com.vinyl.dao.impl;

import com.vinyl.dao.ArtistDao;
import com.vinyl.model.Artist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ArtistDaoImpl implements ArtistDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(Artist artist) {
		return 0;
	}

}
