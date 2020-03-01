package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.SupplierAlreadyExistException;
import com.vinyl.model.Supplier;

import java.util.List;

public interface SupplierService {
	void save(Supplier supplier) throws SupplierAlreadyExistException;

	Supplier getSupplierByEdrpou(String edrpou);

	List<Supplier> searchSuppliers(SearchDto searchDto);

	void update(Supplier supplier, String edrpou);
}
