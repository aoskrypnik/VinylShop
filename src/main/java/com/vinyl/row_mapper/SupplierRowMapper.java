package com.vinyl.row_mapper;

import com.vinyl.model.Supplier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SupplierRowMapper implements RowMapper<Supplier> {
	@Override
	public Supplier mapRow(ResultSet resultSet, int i) throws SQLException {
		return Supplier.builder()
				.edrpou(resultSet.getString("edrpou"))
				.supplierAddress(resultSet.getString("address"))
				.phoneNumber(resultSet.getString("phone_num"))
				.build();
	}
}
