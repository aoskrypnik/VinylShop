package com.vinyl.service.impl;

import com.vinyl.dao.SupplierDao;
import com.vinyl.model.Supplier;
import com.vinyl.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Resource
	private SupplierDao supplierDao;

	@Override
	public void save(Supplier supplier) {
		supplierDao.save(supplier);
	}

	@Override
	public Supplier getSupplierByEdrpou(String edrpou) {
		return supplierDao.getSupplierByEdrpou(edrpou);
	}

	@Override
	public List<Supplier> searchSuppliers(List<String> whereParams, List<String> likeParams, List<String> betweenParams, List<String> joins, String sorting, String order) {
		return null;
	}

	@Override
	public void update(Supplier supplier, String edrpou) {
		supplierDao.update(supplier, edrpou);
	}
}
