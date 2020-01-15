package com.vinyl.dao.impl;

import com.vinyl.dao.RecordDao;
import com.vinyl.model.Record;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RecordDaoImpl implements RecordDao, RowMapper<Record> {

	private static final String RC_BARCODE_PREFIX = "RC";

	@Value("${sql.create.record.query.path}")
	private String createRecordQueryPath;
	@Value("${sql.get.barcode.sequence.next.value.quey.path}")
	private String getBarcodeSequenceNextValueQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public String save(Record record) {
		String fullRecordBarcode = getNewRecordBarcode();
		String createRecordQuery = QuerySupplier.getQuery(createRecordQueryPath);
		jdbcTemplate.update(createRecordQuery, fullRecordBarcode, record.getReleaseBarcode(), record.getCheckNum(),
				record.getSupplierEdrpou(), record.getPurchaseDate(), record.getPurchasePrice(), record.getSellPrice(),
				record.getAvailable(), record.getState(), record.getStateCheckDate());
		return fullRecordBarcode;
	}

	private String getNewRecordBarcode() {
		String getBarcodeSequenceNextValueQuery = QuerySupplier.getQuery(getBarcodeSequenceNextValueQueryPath);
		Integer nextIntBarcodeValue = jdbcTemplate.queryForObject(getBarcodeSequenceNextValueQuery, Integer.class);
		return RC_BARCODE_PREFIX + nextIntBarcodeValue;
	}

	@Override
	public Record getRecordByBarcode(String barcode) {
		return null;
	}

	@Override
	public void update(Record record) {

	}

	@Override
	public List<Record> getAll() {
		return null;
	}

	@Override
	public void deleteByBarcode(String barcode) {

	}

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