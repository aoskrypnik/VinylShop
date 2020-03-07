package com.vinyl.row_mapper;

import com.vinyl.dto.StatisticsSalesmanIncomeDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatisticsSalesmanIncomeRowMapper implements RowMapper<StatisticsSalesmanIncomeDto> {

	@Override
	public StatisticsSalesmanIncomeDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return StatisticsSalesmanIncomeDto.builder()
				.salesmanTabNum(resultSet.getInt("salesman_tab_num"))
				.salesmanIncome(resultSet.getInt("income"))
				.build();
	}

}
