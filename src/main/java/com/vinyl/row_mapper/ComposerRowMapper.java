package com.vinyl.row_mapper;

import com.vinyl.model.Composer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ComposerRowMapper implements RowMapper<Composer> {
	@Override
	public Composer mapRow(ResultSet resultSet, int i) throws SQLException {
		return Composer.builder()
				.name(resultSet.getString("composer_name"))
				.country(resultSet.getString("country"))
				.activityStart(resultSet.getDate("activity_start"))
				.activityEnd(resultSet.getDate("activity_end"))
				.build();
	}
}
