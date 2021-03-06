package com.vinyl.row_mapper;

import com.vinyl.model.Check;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CheckRowMapper implements RowMapper<Check> {

	@Value("${sql.check.get.all.product.barcodes.from.check}")
	private String getAllProductBarcodesByCheckNumQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public Check mapRow(ResultSet resultSet, int i) throws SQLException {
		Integer checkNum = resultSet.getInt("check_num");
		int customerNum = resultSet.getInt("customer_num");
		Integer customerNumObj = customerNum == 0 ? null : customerNum;
		return Check.builder()
				.checkNum(checkNum)
				.dateTime(resultSet.getTimestamp("date_time"))
				.customerNum(customerNumObj)
				.salesmanTabNum(resultSet.getInt("salesman_tab_num"))
				.overallSum(resultSet.getInt("overall_sum"))
				.checkDiscount(resultSet.getShort("check_discount"))
				.sumWithDiscount(resultSet.getInt("sum_with_discount"))
				.productBarcodes(getProductBarcodeByCheckNum(checkNum))
				.build();
	}

	private List<String> getProductBarcodeByCheckNum(Integer checkNum) {
		String getAllProductBarcodesByCheckNumQuery = QuerySupplier.getQuery(getAllProductBarcodesByCheckNumQueryPath);
		return jdbcTemplate.queryForList(getAllProductBarcodesByCheckNumQuery, new Object[]{checkNum}, String.class);
	}

}
