package com.vinyl.controller;

import com.vinyl.model.Salesman;
import com.vinyl.service.SalesmanService;
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
	public ResponseEntity<?> getSalesmanByCriteria(@RequestParam(value = "wheres", required = false) List<String> whereParams,
												   @RequestParam(value = "likes", required = false) List<String> likeParams,
												   @RequestParam(value = "betweens", required = false) List<String> betweenParams,
												   @RequestParam(value = "joins", required = false) List<String> joins,
												   @RequestParam(value = "sort", required = false) String sorting,
												   @RequestParam(value = "order", required = false) String order,
												   @RequestParam(value = "limit", required = false) Integer limit,
												   @RequestParam(value = "offset", required = false) Integer offset) {
		List<Salesman> salesmen = salesmanService
				.searchSalesman(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset);
		if (salesmen.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(salesmen);
	}

}
