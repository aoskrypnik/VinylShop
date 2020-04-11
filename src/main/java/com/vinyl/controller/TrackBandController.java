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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("band2track")
public class TrackBandController {

	@Resource
	private TrackPerformerService trackPerformerService;

	/**
	 * 12345@Vremya%20and%20Steklo
	 */
	@GetMapping("/{trackAndBandAlias}")
	public ResponseEntity<TrackBandDto> getByTrackAndPerformerName(@PathVariable String trackAndBandAlias) {
		TrackBandDto trackBandDto = trackPerformerService.getTrackBandByTrackNumAndBandAlias(trackAndBandAlias);
		if (isNull(trackBandDto)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackBandDto);
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody TrackBandDto trackBandDto) {
		String trackCatalogNum = trackBandDto.getTrackCatalogNum();
		String bandAlias = trackBandDto.getBandAlias();
		String potentialLocationPath = trackCatalogNum + "@" + bandAlias;

		trackPerformerService.save(trackBandDto);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{ids}")
				.buildAndExpand(potentialLocationPath)
				.toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 12345@Vremya%20and%20Steklo
	 */
	@PutMapping("/{trackAndBandAlias}")
	public ResponseEntity<Object> update(@PathVariable String trackAndBandAlias, @RequestBody TrackBandDto trackBandDto) {
		TrackBandDto foundTrackBandDto = trackPerformerService.getTrackBandByTrackNumAndBandAlias(trackAndBandAlias);
		if (isNull(foundTrackBandDto)
				|| trackPerformerService.isNotEqualTrackNums(trackAndBandAlias, trackBandDto.getTrackCatalogNum())
				|| trackPerformerService.isNotEqualArtistAliases(trackAndBandAlias, trackBandDto.getBandAlias())) {
			return ResponseEntity.badRequest().build();
		}
		trackPerformerService.update(trackBandDto);
		return ResponseEntity.ok().build();
	}

	/**
	 * 12345@Vremya%20and%20Steklo
	 */
	@DeleteMapping("/{trackAndBandAlias}")
	public ResponseEntity<Object> delete(@PathVariable String trackAndBandAlias) {
		TrackBandDto trackBandDto = trackPerformerService.getTrackBandByTrackNumAndBandAlias(trackAndBandAlias);
		if (isNull(trackBandDto)) {
			return ResponseEntity.notFound().build();
		}
		trackPerformerService.deleteTrackBand(trackBandDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<TrackBandDto>> getTrackBandByCriteria(SearchDto searchDto) {
		List<TrackBandDto> trackBandDtoList = trackPerformerService.searchBandTrackPerformance(searchDto);
		if (trackBandDtoList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackBandDtoList);
	}
}
