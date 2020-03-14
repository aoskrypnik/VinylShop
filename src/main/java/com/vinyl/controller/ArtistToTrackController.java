package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
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
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/artist2track")
public class ArtistToTrackController {

	private static final Boolean IS_ARTIST = true;

	@Resource
	private TrackPerformerService trackPerformerService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody TrackPerformerDto trackPerformerDto) {
		trackPerformerDto.setIsArtist(IS_ARTIST);
		trackPerformerService.save(trackPerformerDto);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody TrackPerformerDto trackPerformerDto) {
		trackPerformerDto.setIsArtist(IS_ARTIST);
		trackPerformerService.update(trackPerformerDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{trackAndArtistAlias}")
	public ResponseEntity<?> getByTrackAndPerformerName(@PathVariable String trackAndArtistAlias) {
		TrackPerformerDto foundTrackPerformer = trackPerformerService
				.getTrackPerformerByTrackNameAndPerformerAlias(trackAndArtistAlias, IS_ARTIST);
		if (isNull(foundTrackPerformer)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundTrackPerformer);
	}

	@DeleteMapping("/{trackAndArtistAlias}")
	public ResponseEntity<?> delete(@PathVariable String trackAndArtistAlias) {
		TrackPerformerDto trackPerformerDtoToDelete = trackPerformerService
				.getTrackPerformerByTrackNameAndPerformerAlias(trackAndArtistAlias, IS_ARTIST);
		if (isNull(trackPerformerDtoToDelete)) {
			return ResponseEntity.notFound().build();
		}
		trackPerformerService.deleteTrackPerformanceInstance(trackPerformerDtoToDelete);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getSupplierByCriteria(SearchDto searchDto) {
		List<TrackPerformerDto> trackPerformerDtoList = trackPerformerService.searchArtistTrackPerformance(searchDto);
		if (trackPerformerDtoList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackPerformerDtoList);
	}
}
