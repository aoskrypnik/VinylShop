package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
import com.vinyl.model.Salesman;
import com.vinyl.service.SalesmanService;
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

@RestController
@RequestMapping("/salesman")
public class SalesmanController {

	@Resource
	private SalesmanService salesmanService;

	@GetMapping("/{tabNum}")
	public ResponseEntity<?> getSalesmanByTabNum(@PathVariable Integer tabNum) {
		Salesman foundSalesman = salesmanService.getSalesmanByTabNum(tabNum);
		if (isNull(foundSalesman)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundSalesman);
	}

	@PostMapping
	public ResponseEntity<?> saveSalesman(@RequestBody Salesman salesman) {
		int generatedKey = salesmanService.save(salesman);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tabNum}")
				.buildAndExpand(generatedKey).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{tabNum}")
	public ResponseEntity<?> updateSalesman(@RequestBody Salesman salesman, @PathVariable int tabNum) {
		if (isNull(salesmanService.getSalesmanByTabNum(tabNum))) {
			return ResponseEntity.notFound().build();
		}
		salesmanService.updateSalesmanByTabNum(salesman, tabNum);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getSalesmanByCriteria(SearchDto searchDto) {
		List<Salesman> salesmen = salesmanService.searchSalesman(searchDto);
		if (salesmen.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(salesmen);
	}

}
