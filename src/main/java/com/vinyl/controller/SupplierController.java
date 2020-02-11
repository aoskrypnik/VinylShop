package com.vinyl.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<?> updateRelease(@RequestBody Supplier supplier, @PathVariable String edrpou) {
		if (isNull(supplierService.getSupplierByEdrpou(edrpou))) {
			return ResponseEntity.notFound().build();
		}
		supplierService.update(supplier, edrpou);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getAlbumByCriteria(@RequestParam(value = "wheres", required = false) List<String> whereParams,
												@RequestParam(value = "likes", required = false) List<String> likeParams,
												@RequestParam(value = "betweens", required = false) List<String> betweenParams,
												@RequestParam(value = "joins", required = false) List<String> joins,
												@RequestParam(value = "sort", required = false) String sorting,
												@RequestParam(value = "order", required = false) String order) {
		List<Supplier> suppliers = supplierService.searchSuppliers(whereParams, likeParams, betweenParams, joins, sorting, order);
		if (suppliers.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(suppliers);
	}
}
