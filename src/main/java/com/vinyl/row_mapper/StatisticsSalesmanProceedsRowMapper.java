package com.vinyl.row_mapper;


import com.vinyl.dto.StatisticsSalesmanProceedsDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatisticsSalesmanProceedsRowMapper implements RowMapper<StatisticsSalesmanProceedsDto> {
	@Override
	public StatisticsSalesmanProceedsDto mapRow(ResultSet resultSet, int i) throws SQLException {
		return StatisticsSalesmanProceedsDto.builder()
				.salesmanTabNum(resultSet.getInt("salesman_tab_num"))
				.proceeds(resultSet.getInt("proceeds"))
				.build();
	}
}
