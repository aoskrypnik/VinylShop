package com.vinyl.dao.impl;

import com.vinyl.dao.BandDao;
import com.vinyl.model.Band;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class BandDaoImpl implements BandDao {

	@Resource
	private RowMapper<Band> bandRowMapper;
	@Resource
	private JdbcTemplate jdbcTemplate;

}
