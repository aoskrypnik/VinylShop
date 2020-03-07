package com.vinyl.row_mapper;

import com.vinyl.dto.StatisticsSalesmanChecksDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class StatisticsSalesmanChecksRowMapper implements RowMapper<StatisticsSalesmanChecksDto> {
	@Override
	public StatisticsSalesmanChecksDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return StatisticsSalesmanChecksDto.builder()
				.salesmanTabNum(resultSet.getInt("salesman_tab_num"))
				.numberOfChecks(resultSet.getInt("number_of_checks"))
				.build();
	}
}
