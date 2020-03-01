package com.vinyl.service.impl;

import com.vinyl.dao.RecordDao;
import com.vinyl.dto.SearchDto;
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
	public List<Record> searchRecords(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), RECORD_TABLE_NAME);
		return recordDao.searchReleases(query);
	}

}
