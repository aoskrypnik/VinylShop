package com.vinyl.service.impl;

import com.vinyl.dao.SupplierDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.exception.SupplierAlreadyExistException;
import com.vinyl.model.Supplier;
import com.vinyl.service.SupplierService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class SupplierServiceImpl implements SupplierService {

	private static final String SUPPLIER_TABLE_NAME = "supplier";

	@Resource
	private SupplierDao supplierDao;

	@Override
	public void save(Supplier supplier) throws SupplierAlreadyExistException {
		String edrpou = supplier.getEdrpou();
		if (nonNull(getSupplierByEdrpou(edrpou))) {
			throw new SupplierAlreadyExistException("Supplier already exist with edrpou" + edrpou);
		}
		supplierDao.save(supplier);
	}

	@Override
	public Supplier getSupplierByEdrpou(String edrpou) {
		return supplierDao.getSupplierByEdrpou(edrpou);
	}

	@Override
	public List<Supplier> searchSuppliers(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWhereParams(), searchDto.getLikeParams(), searchDto.getBetweenParams(),
						searchDto.getJoins(), searchDto.getSorting(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), SUPPLIER_TABLE_NAME);
		return supplierDao.searchSuppliers(query);
	}

	@Override
	public void update(Supplier supplier, String edrpou) {
		supplierDao.update(supplier, edrpou);
	}
}
