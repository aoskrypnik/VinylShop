package com.vinyl.row_mapper;

import com.vinyl.model.Record;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RecordRowMapper implements RowMapper<Record> {
	@Override
	public Record mapRow(ResultSet resultSet, int i) throws SQLException {
		return null;
	}
}
