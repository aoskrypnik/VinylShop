package com.vinyl.row_mapper;

import com.vinyl.dto.StatisticsWithRecursiveDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatisticsWithRecursiveRowMapper implements RowMapper<StatisticsWithRecursiveDto> {
	@Override
	public StatisticsWithRecursiveDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return StatisticsWithRecursiveDto.builder()
				.checkNum(resultSet.getInt("check_num"))
				.income(resultSet.getInt("income"))
				.avgCheck(resultSet.getInt("avg_check"))
				.proceeds(resultSet.getInt("proceeds"))
				.build();
	}
}
