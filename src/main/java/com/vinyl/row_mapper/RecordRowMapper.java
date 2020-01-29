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
		return Record.builder()
				.barcode(resultSet.getString("bar_code"))
				.releaseBarcode(resultSet.getString("release_bar_code"))
				.checkNum(resultSet.getInt("check_num"))
				.supplierEdrpou(resultSet.getString("supplier_edrpou"))
				.purchaseDate(resultSet.getDate("purchase_date"))
				.purchasePrice(resultSet.getInt("purchase_price"))
				.sellPrice(resultSet.getInt("sell_price"))
				.available(resultSet.getBoolean("available"))
				.state(resultSet.getString("state"))
				.stateCheckDate(resultSet.getDate("state_check_date"))
				.build();
	}
}
