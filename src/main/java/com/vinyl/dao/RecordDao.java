package com.vinyl.dao;

import com.vinyl.model.Record;

import java.util.List;

public interface RecordDao {

	String save(Record record);

	Record getByBarcode(String barcode);

	void update(Record record);

	List<Record> getAll();

	List<Record> searchReleases(String query);
}
