package com.vinyl.controller;

import com.vinyl.model.Band;
import com.vinyl.service.BandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/band")
public class BandController {

	@Resource
	private BandService bandService;

	@GetMapping
	public List<Band> getAllBands() {
		return bandService.getAll();
	}

	@PostMapping
	public ResponseEntity<?> saveBand(@RequestBody Band band) {
		bandService.save(band);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{alias}")
				.buildAndExpand(band.getBandAlias()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{alias}")
	public ResponseEntity<?> getBandByAlias(@PathVariable String alias) {
		Band foundBand = bandService.getBandByAlias(alias);
		if (isNull(foundBand)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundBand);
	}

	@GetMapping("/search")
	public ResponseEntity<?> getBandByCriteria(@RequestParam(value = "wheres", required = false) List<String> whereParams,
											   @RequestParam(value = "likes", required = false) List<String> likeParams,
											   @RequestParam(value = "betweens", required = false) List<String> betweenParams,
											   @RequestParam(value = "joins", required = false) List<String> joins,
											   @RequestParam(value = "sort", required = false) String sorting,
											   @RequestParam(value = "order", required = false) String order) {
		List<Band> bands = bandService.searchBands(whereParams, likeParams, betweenParams, joins, sorting, order);
		if (bands.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bands);
	}
}
