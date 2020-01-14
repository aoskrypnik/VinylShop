package com.vinyl.service.impl;

import com.vinyl.dao.RecordDao;
import com.vinyl.model.Record;
import com.vinyl.service.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

	@Resource
	private RecordDao recordDao;

	@Override
	public String save(Record record) {
		return recordDao.save(record);
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
}
