package com.vinyl.service.impl;

import com.vinyl.dao.RecordDao;
import com.vinyl.model.Record;
import com.vinyl.service.RecordService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

	private static final String RECORD_TABLE_NAME = "record";

	@Resource
	private RecordDao recordDao;

	@Override
	public String save(Record record) {
		return recordDao.save(record);
	}

	@Override
	public Record getByBarcode(String barcode) {
		return recordDao.getByBarcode(barcode);
	}

	@Override
	public void update(Record record) {
		recordDao.update(record);
	}

	@Override
	public List<Record> getAll() {
		return recordDao.getAll();
	}

	@Override
	public List<Record> searchRecords(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
									  List<String> joins, String sorting, String order,
									  Integer limit, Integer offset) {
		String query = QueryBuilder
				.build(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset, RECORD_TABLE_NAME);
		return recordDao.searchReleases(query);
	}

}
