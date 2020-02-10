package com.vinyl.dao;

import com.vinyl.model.Check;

import java.util.List;

public interface CheckDao {
	Check getByNum(Integer num);

	Integer save(Check check);

	List<Check> searchChecks(String query);
}
