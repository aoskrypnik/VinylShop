package com.vinyl.dao;

import com.vinyl.model.Record;

import java.util.List;

public interface RecordDao {

	String save(Record record);

	Record getRecordByBarcode(String barcode);

	void update(Record record);

	List<Record> getAll();

	void deleteByBarcode(String  barcode);
}
