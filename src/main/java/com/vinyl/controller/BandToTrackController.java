package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackBandDto;
import com.vinyl.service.TrackPerformerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("band2track")
public class BandToTrackController {

	@Resource
	private TrackPerformerService trackPerformerService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody TrackBandDto trackBandDto) {
		trackPerformerService.save(trackBandDto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{trackAndBandAlias}")
	public ResponseEntity<?> update(@PathVariable String trackAndBandAlias, @RequestBody TrackBandDto trackBandDto) {
		TrackBandDto foundTrackBandDto = trackPerformerService.getTrackBandByTrackNumAndBandAlias(trackAndBandAlias);
		if (isNull(foundTrackBandDto)
				|| trackPerformerService.isNotEqualTrackNums(trackAndBandAlias, trackBandDto.getTrackCatalogNum())
				|| trackPerformerService.isNotEqualArtistAliases(trackAndBandAlias, trackBandDto.getBandAlias())) {
			return ResponseEntity.badRequest().build();
		}
		trackPerformerService.update(trackBandDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{trackAndBandAlias}")
	public ResponseEntity<?> getByTrackAndPerformerName(@PathVariable String trackAndBandAlias) {
		TrackBandDto trackBandDto = trackPerformerService.getTrackBandByTrackNumAndBandAlias(trackAndBandAlias);
		if (isNull(trackBandDto)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackBandDto);
	}

	@DeleteMapping("/{trackAndBandAlias}")
	public ResponseEntity<?> delete(@PathVariable String trackAndBandAlias) {
		TrackBandDto trackBandDto = trackPerformerService.getTrackBandByTrackNumAndBandAlias(trackAndBandAlias);
		if (isNull(trackBandDto)) {
			return ResponseEntity.notFound().build();
		}
		trackPerformerService.deleteTrackBand(trackBandDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getTrackBandByCriteria(SearchDto searchDto) {
		List<TrackBandDto> trackBandDtoList = trackPerformerService.searchBandTrackPerformance(searchDto);
		if (trackBandDtoList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackBandDtoList);
	}
}
