package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.model.Check;

import java.util.List;

public interface CheckService {
	Check getByNum(Integer num);

	Integer save(Check check);

	List<Check> searchChecks(SearchDto searchDto);

	boolean allRecordsAvailable(List<String> recordBarcodes);
}
