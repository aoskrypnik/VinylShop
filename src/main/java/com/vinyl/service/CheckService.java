package com.vinyl.service;

import com.vinyl.model.Check;

import java.util.List;

public interface CheckService {
	Check getByNum(Integer num);

	Integer save(Check check);

	List<Check> searchChecks(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							 List<String> joins, String sorting, String order);
}
