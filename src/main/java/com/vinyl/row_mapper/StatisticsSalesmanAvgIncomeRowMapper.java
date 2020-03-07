package com.vinyl.row_mapper;

import com.vinyl.dto.StatisticsSalesmanAvgIncomeDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class StatisticsSalesmanAvgIncomeRowMapper implements RowMapper<StatisticsSalesmanAvgIncomeDto> {
	@Override
	public StatisticsSalesmanAvgIncomeDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return StatisticsSalesmanAvgIncomeDto.builder()
				.salesmanTabNum(resultSet.getInt("salesman_tab_num"))
				.averageIncome(resultSet.getDouble("avg_income"))
				.build();
	}
}
