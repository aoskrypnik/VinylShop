package com.vinyl.dao;

import com.vinyl.model.Supplier;

import java.util.List;

public interface SupplierDao {
	void save(Supplier supplier);

	Supplier getSupplierByEdrpou(String edrpou);

	List<Supplier> searchSuppliers(String query);

	void update(Supplier supplier, String edrpou);
}
