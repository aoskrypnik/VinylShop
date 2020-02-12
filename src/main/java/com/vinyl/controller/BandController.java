package com.vinyl.controller;

import com.vinyl.exception.BandExistException;
import com.vinyl.model.Band;
import com.vinyl.service.BandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.util.CollectionUtils.isEmpty;

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
		String bandAlias;
		try {
			bandAlias = bandService.save(band);
		} catch (BandExistException e) {
			return ResponseEntity.status(406).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{alias}")
				.buildAndExpand(bandAlias).toUri();

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
											   @RequestParam(value = "order", required = false) String order,
											   @RequestParam(value = "limit", required = false) Integer limit,
											   @RequestParam(value = "offset", required = false) Integer offset) {
		List<Band> bands = bandService
				.searchBands(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset);
		if (bands.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bands);
	}

	@PutMapping("/{alias}")
	public ResponseEntity<?> updateArtist(@RequestBody Band band, @PathVariable String alias) {
		if (isNull(bandService.getBandByAlias(alias))) {
			return ResponseEntity.notFound().build();
		}
		bandService.update(band, alias);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{alias}")
	public ResponseEntity<?> deleteBand(@PathVariable String alias) {
		Band foundBand = bandService.getBandByAlias(alias);
		if (isNull(foundBand)) {
			return ResponseEntity.notFound().build();
		}

		if (isFalse(isEmpty(foundBand.getCurrentArtistAliases())) || isFalse(isEmpty(foundBand.getPreviousArtistAliases()))
				|| isFalse(isEmpty(foundBand.getTrackCatalogNums())) || isFalse(isEmpty(foundBand.getFeaturingTracksCatalogNums()))) {
			return ResponseEntity.status(403).build();
		}
		bandService.deleteBand(alias);
		return ResponseEntity.ok().build();
	}
}
