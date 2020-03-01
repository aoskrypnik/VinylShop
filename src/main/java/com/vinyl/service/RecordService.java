package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.model.Record;

import java.util.List;

public interface RecordService {

	String save(Record record);

	Record getByBarcode(String barcode);

	void update(Record record);

	List<Record> searchRecords(SearchDto searchDto);
}
