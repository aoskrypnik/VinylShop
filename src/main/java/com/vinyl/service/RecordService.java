package com.vinyl.service;

import com.vinyl.model.Record;

import java.util.List;

public interface RecordService {

	String save(Record record);

	Record getByBarcode(String barcode);

	void update(Record record);

	List<Record> searchRecords(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							   List<String> joins, String sorting, String order, Integer limit, Integer offset);
}
