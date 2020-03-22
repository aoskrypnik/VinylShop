package com.vinyl.dao.impl;

import com.vinyl.dao.RecordDao;
import com.vinyl.model.Record;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Repository
public class RecordDaoImpl implements RecordDao {

	private static final String RC_BARCODE_PREFIX = "RC";

	@Value("${sql.record.create.record.query.path}")
	private String createRecordQueryPath;
	@Value("${sql.record.get.record.by.barcode.query.path}")
	private String getRecordByBarcodeQueryPath;
	@Value("${sql.get.barcode.sequence.next.value.query.path}")
	private String getBarcodeSequenceNextValueQueryPath;
	@Value("${sql.record.update.record.query.path}")
	private String updateRecordQueryPath;
	@Value("${sql.record.check.if.record.is.available.query.path}")
	private String checkIfRecordIsAvailableQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<Record> recordRowMapper;

	@Override
	public String save(Record record) {
		String fullRecordBarcode = getNewRecordBarcode();
		String createRecordQuery = QuerySupplier.getQuery(createRecordQueryPath);
		jdbcTemplate.update(createRecordQuery, fullRecordBarcode, record.getReleaseBarcodeFk(), record.getCheckNum(),
				record.getSupplierEdrpou(), record.getPurchaseDate(), record.getPurchasePrice(), record.getSellPrice(),
				record.getAvailable(), record.getRecordState(), record.getStateCheckDate());
		return fullRecordBarcode;
	}

	private String getNewRecordBarcode() {
		String getBarcodeSequenceNextValueQuery = QuerySupplier.getQuery(getBarcodeSequenceNextValueQueryPath);
		Integer nextIntBarcodeValue = jdbcTemplate.queryForObject(getBarcodeSequenceNextValueQuery, Integer.class);
		return RC_BARCODE_PREFIX + nextIntBarcodeValue;
	}

	@Transactional
	@Override
	public Record getByBarcode(String barcode) {
		String getRecordByBarcodeQuery = QuerySupplier.getQuery(getRecordByBarcodeQueryPath);
		List<Record> queryResult = jdbcTemplate.query(getRecordByBarcodeQuery, new Object[]{barcode}, recordRowMapper);
		return queryResult.size() == 0 ? null : queryResult.get(0);
	}

	@Override
	public void update(Record record) {
		String updateRecordQuery = QuerySupplier.getQuery(updateRecordQueryPath);
		jdbcTemplate.update(updateRecordQuery, record.getReleaseBarcodeFk(), record.getCheckNum(),
				record.getSupplierEdrpou(), record.getPurchaseDate(), record.getPurchasePrice(), record.getSellPrice(),
				record.getAvailable(), record.getRecordState(), record.getStateCheckDate(), record.getRecordBarcode());
	}

	@Transactional
	@Override
	public List<Record> searchRecords(String query) {
		return jdbcTemplate.query(query, recordRowMapper);
	}

	@Override
	public boolean recordIsSold(String barcode) {
		String checkIfRecordIsAvailableQuery = QuerySupplier.getQuery(checkIfRecordIsAvailableQueryPath);
		List<Boolean> isRecordAvailableResult = jdbcTemplate
				.queryForList(checkIfRecordIsAvailableQuery, new Object[]{barcode}, Boolean.class);
		return isFalse(isRecordAvailableResult.get(0));
	}

}
