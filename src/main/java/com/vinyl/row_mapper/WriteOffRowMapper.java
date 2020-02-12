package com.vinyl.row_mapper;

import com.vinyl.model.WriteOff;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class WriteOffRowMapper implements RowMapper<WriteOff> {

	@Override
	public WriteOff mapRow(ResultSet resultSet, int i) throws SQLException {
		return WriteOff.builder()
				.writeOffNum(resultSet.getInt("write_off_num"))
				.offRecordBarcode(resultSet.getString("record_bar_code"))
				.salesmanNum(resultSet.getInt("salesman_num"))
				.writeOffDate(resultSet.getDate("write_off_date"))
				.fee(resultSet.getInt("fee"))
				.reason(resultSet.getString("reason"))
				.build();
	}
}
