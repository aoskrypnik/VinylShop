package com.vinyl.controller;

import com.vinyl.dto.TrackPerformerDto;
import com.vinyl.service.TrackPerformerService;
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

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/track/performer")
public class TrackPerformerController {

	@Resource
	private TrackPerformerService trackPerformerService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody TrackPerformerDto trackPerformerDto) {
		trackPerformerService.save(trackPerformerDto);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody TrackPerformerDto trackPerformerDto) {
		trackPerformerService.update(trackPerformerDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{trackAndPerformerName}")
	public ResponseEntity<?> getByTrackAndPerformerName(@PathVariable String trackAndPerformerName) {
		TrackPerformerDto foundTrackPerformer = trackPerformerService
				.getTrackPerformerByTrackNameAndPerformerAlias(trackAndPerformerName);

		if (isNull(foundTrackPerformer)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(foundTrackPerformer);
	}

	@DeleteMapping("/{trackAndPerformerName}")
	public ResponseEntity<?> delete(@PathVariable String trackAndPerformerName) {
		TrackPerformerDto trackPerformerDtoToDelete = trackPerformerService
				.getTrackPerformerByTrackNameAndPerformerAlias(trackAndPerformerName);
		if (isNull(trackPerformerDtoToDelete)) {
			return ResponseEntity.notFound().build();
		}
		trackPerformerService.deleteTrackPerformanceInstance(trackPerformerDtoToDelete);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getSupplierByCriteria(@RequestParam(value = "wheres", required = false) List<String> whereParams,
												   @RequestParam(value = "likes", required = false) List<String> likeParams,
												   @RequestParam(value = "betweens", required = false) List<String> betweenParams,
												   @RequestParam(value = "joins", required = false) List<String> joins,
												   @RequestParam(value = "sort", required = false) String sorting,
												   @RequestParam(value = "order", required = false) String order,
												   @RequestParam(value = "limit", required = false) Integer limit,
												   @RequestParam(value = "offset", required = false) Integer offset) {
		List<TrackPerformerDto> trackPerformerDtoList = trackPerformerService
				.searchTrackPerformance(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset);
		if (trackPerformerDtoList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackPerformerDtoList);
	}

}
