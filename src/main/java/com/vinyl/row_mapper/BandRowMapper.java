package com.vinyl.row_mapper;

import com.vinyl.model.Band;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BandRowMapper implements RowMapper<Band> {

	@Override
	public Band mapRow(ResultSet resultSet, int i) throws SQLException {
		return null;
	}

}
