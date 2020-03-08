package com.vinyl.row_mapper;

import com.vinyl.dto.StatisticsDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatisticsRowMapper implements RowMapper<StatisticsDto> {
	@Override
	public StatisticsDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return StatisticsDto.builder()
				.salesmanTabNum(resultSet.getInt("salesman_tab_num"))
				.statisticsResult(resultSet.getInt("statistics_res"))
				.build();
	}
}
