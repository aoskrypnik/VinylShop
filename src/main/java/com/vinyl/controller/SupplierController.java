package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.SupplierAlreadyExistException;
import com.vinyl.model.Supplier;
import com.vinyl.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Resource
	private SupplierService supplierService;

	@PostMapping
	public ResponseEntity<?> saveSupplier(@RequestBody Supplier supplier) {
		try {
			supplierService.save(supplier);
		} catch (SupplierAlreadyExistException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(406).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{edrpou}")
				.buildAndExpand(supplier.getEdrpou()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{edrpou}")
	public ResponseEntity<?> getSupplierByEdrpou(@PathVariable String edrpou) {
		Supplier foundSupplier = supplierService.getSupplierByEdrpou(edrpou);
		if (isNull(foundSupplier)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundSupplier);
	}

	@PutMapping("/{edrpou}")
	public ResponseEntity<?> updateSupplier(@RequestBody Supplier supplier, @PathVariable String edrpou) {
		if (isNull(supplierService.getSupplierByEdrpou(edrpou))) {
			return ResponseEntity.notFound().build();
		}
		supplierService.update(supplier, edrpou);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getSupplierByCriteria(SearchDto searchDto) {
		List<Supplier> suppliers = supplierService.searchSuppliers(searchDto);
		if (suppliers.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(suppliers);
	}
}
