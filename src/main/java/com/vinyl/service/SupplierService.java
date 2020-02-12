package com.vinyl.service;

import com.vinyl.exception.SupplierAlreadyExistException;
import com.vinyl.model.Supplier;

import java.util.List;

public interface SupplierService {
	void save(Supplier supplier) throws SupplierAlreadyExistException;

	Supplier getSupplierByEdrpou(String edrpou);

	List<Supplier> searchSuppliers(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
								   List<String> joins, String sorting, String order, Integer limit, Integer offset);

	void update(Supplier supplier, String edrpou);
}
